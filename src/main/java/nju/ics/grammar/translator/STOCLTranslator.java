package nju.ics.grammar.translator;


import java.util.*;
import java.util.stream.Collectors;

import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.JoinRelType;

import org.apache.calcite.rex.*;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Table;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.fun.SqlLibrary;
import org.apache.calcite.sql.fun.SqlLibraryOperatorTableFactory;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.validate.SqlNameMatcher;
import org.apache.calcite.sql.validate.SqlNameMatchers;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.RelBuilder;

import nju.ics.grammar.stocl.PrimitiveType;
import nju.ics.grammar.stocl.STOCLParser;
import nju.ics.grammar.translator.elements.*;
import nju.ics.model.schema.UMLSchema;
import nju.ics.model.uml.UMLClassDiagram;
import org.apache.calcite.util.TimestampString;


public class STOCLTranslator extends OCLTranslator {

    SqlOperatorTable opTab;


    public STOCLTranslator(UMLClassDiagram cd) {
        this.cd = cd;

        // UMLSchemaFactory schemaFactory = new UMLSchemaFactory(cd);
        Schema schema = new UMLSchema(cd);

        // 1) create root schema
        SchemaPlus root = Frameworks.createRootSchema(true);

        // 复制所有表到 root
        for (String tableName : schema.getTableNames()) {
            Table table = schema.getTable(tableName);
            root.add(tableName, table);
        }

        opTab = SqlLibraryOperatorTableFactory.INSTANCE
            .getOperatorTable(EnumSet.of(SqlLibrary.STANDARD, SqlLibrary.SPATIAL));


        // 4)set schema to default schema
        FrameworkConfig config = Frameworks.newConfigBuilder()
                                .operatorTable(opTab) // 加上空间函数
                                .defaultSchema(root)
                                .build();

        // 3) use RelBuilder to build RA tree
        this.builder = RelBuilder.create(config);



    }



    // 根据函数名和参数个数查找 SqlOperator
    public SqlOperator findOp(SqlOperatorTable tab, String name, int arity) {
        List<SqlOperator> list = new ArrayList<>();
        SqlNameMatcher matcher = SqlNameMatchers.withCaseSensitive(false);

        tab.lookupOperatorOverloads(
            new SqlIdentifier(name, SqlParserPos.ZERO),
            SqlFunctionCategory.USER_DEFINED_FUNCTION,
            SqlSyntax.FUNCTION,
            list,
            matcher
        );
        // 也可按 arity 过滤；有些函数有多重载
        return list.stream().filter(op -> op.getOperandTypeChecker() == null || true).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No operator: " + name));
    }



    @Override
    public OCLElement visitOclBoolSpatialPredicate(STOCLParser.OclBoolSpatialPredicateContext ctx) {

        SpatialPredicate spatialPredicate = (SpatialPredicate) visit(ctx.spatialPredicate());


        // 处理返回的OCLBool包含的列
        // get whole set
        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());

        builder.push(spatialPredicate.getRelNode());
        List<RexInputRef> proj = universalSet.getRelNode().getRowType().getFieldNames()
                .stream()
                .map(f -> builder.field(f))
                .toList();

        //进行投影并去重，使返回的oclBool具有和universalSet相同的列
        RelNode projectAndDistinct = builder.project(proj)
                        // .distinct()
                        .build();


        return new OCLBool(projectAndDistinct);

        
    }





    @Override
    public OCLElement visitOclBoolPeriodPredicate(STOCLParser.OclBoolPeriodPredicateContext ctx) {

        PeriodPredicate periodPredicate = (PeriodPredicate) visit(ctx.periodPredicate());


        // 处理返回的OCLBool包含的列
        // get whole set
        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());

        builder.push(periodPredicate.getRelNode());
        List<RexInputRef> proj = universalSet.getRelNode().getRowType().getFieldNames()
                .stream()
                .map(f -> builder.field(f))
                .toList();

        //进行投影并去重，使返回的oclBool具有和universalSet相同的列
        RelNode projectAndDistinct = builder.project(proj)
                // .distinct()
                .build();


        return new OCLBool(projectAndDistinct);


    }



    @Override
    public OCLElement visitEqualityExprObjAttrValue(STOCLParser.EqualityExprObjAttrValueContext ctx) {

        Map<String, SqlBinaryOperator> compOpMap = Map.of(
                "<", SqlStdOperatorTable.LESS_THAN,
                "<=", SqlStdOperatorTable.LESS_THAN_OR_EQUAL,
                "=", SqlStdOperatorTable.EQUALS,
                ">=", SqlStdOperatorTable.GREATER_THAN_OR_EQUAL,
                ">", SqlStdOperatorTable.GREATER_THAN,
                "<>", SqlStdOperatorTable.NOT_EQUALS
        );


        OCLElement left =  visit(ctx.objAttrValue(0));
        OCLElement right = visit(ctx.objAttrValue(1));
        String compOp = ctx.compOp.getText();

        if (left instanceof ArithExpr && right instanceof ArithExpr) {
            ArithExpr leftExpr = (ArithExpr) left;
            ArithExpr rightExpr = (ArithExpr) right;

            // 相关集合为值集合，值的类型为int或real
            if (!leftExpr.getRefSet().isValueSet() ){
                throw new RuntimeException(" Left side of comparison must be a value set.");
            }

            if (!rightExpr.getRefSet().isValueSet() ){
                throw new RuntimeException(" Right side of comparison must be a value set.");
            }

            List<String> lFieldNames = new ArrayList<>( leftExpr.getRelNode().getRowType().getFieldNames());
            List<String> rFieldNames = new ArrayList<>( rightExpr.getRelNode().getRowType().getFieldNames());
            int leftIndex = lFieldNames.size()-1;
            int rightIndex = rFieldNames.size()-1;


            builder.push(leftExpr.getRelNode())
                    .push(rightExpr.getRelNode());

            // 取列交集，并制作等值条件
            List<RexNode> conditions = new ArrayList<>();
            for(int i=0; i<rightIndex; i++) {
                String colString = rFieldNames.get(i);
                if(lFieldNames.contains(colString)){
                    conditions.add(
                        builder.equals(
                            builder.field(2, 0, colString),
                            builder.field(2, 1, colString)
                        )
                    );

                }
            }
            // 取最后一列，并制作条件
            conditions.add(
                builder.call(
                    compOpMap.get(compOp),
                    builder.field(2, 0, leftIndex),
                    builder.field(2, 1, rightIndex)
                )
            );

            RexNode condition = builder.and(conditions);

            RelNode res = builder.join(JoinRelType.INNER, condition)
                                .build();


            return new EqualExpr(res);

        } else if ( left instanceof StrValue && right instanceof StrValue) {
            StrValue leftExpr = (StrValue) left;
            StrValue rightExpr = (StrValue) right;


            if (!leftExpr.getRefSet().isValueSet() ){
                throw new RuntimeException(" Left side of comparison must be a value set.");
            }

            if (!rightExpr.getRefSet().isValueSet() ){
                throw new RuntimeException(" Right side of comparison must be a value set.");
            }


            // semiJoin

            List<String> lFieldNames = new ArrayList<>( leftExpr.getRelNode().getRowType().getFieldNames());
            List<String> rFieldNames = new ArrayList<>( rightExpr.getRelNode().getRowType().getFieldNames());
            int leftIndex = lFieldNames.size()-1;
            int rightIndex = rFieldNames.size()-1;


            builder.push(leftExpr.getRelNode())
                    .push(rightExpr.getRelNode());

            // 取列交集，并制作等值条件
            List<RexNode> conditions = new ArrayList<>();
            for(int i=0; i<rightIndex; i++) {
                String colString = rFieldNames.get(i);
                if(lFieldNames.contains(colString)){
                    conditions.add(
                        builder.equals(
                            builder.field(2, 0, colString),
                            builder.field(2, 1, colString)
                        )
                    );

                }
            }
            // 取最后一列，并制作条件
            conditions.add(
                builder.call(
                    compOpMap.get(compOp),
                    builder.field(2, 0, leftIndex),
                    builder.field(2, 1, rightIndex)
                )
            );

            RexNode condition = builder.and(conditions);

            RelNode res = builder.join(JoinRelType.INNER, condition)
                                .build();

            return new EqualExpr(res);
            

        } else if ( left instanceof OCLTimestamp && right instanceof OCLTimestamp){
            OCLTimestamp leftVal = (OCLTimestamp) left;
            OCLTimestamp rightVal = (OCLTimestamp) right;

            return new EqualExpr(timestampCompare(leftVal, rightVal, compOp));
        } else {
            throw new RuntimeException(" Both sides of equality expression must be literals.");
        }


    }







    @Override
    public OCLElement visitObjAttrValueFAttr(STOCLParser.ObjAttrValueFAttrContext ctx) {
        OCLObj obj = (OCLObj) visit(ctx.oclObj());
        String attr = ctx.attr().getText();

        if (!cd.hasAttr(obj.getClassName(), attr)){
            throw new RuntimeException("Class " + obj.getClassName() +
                    " has no attribute named " + attr);
        }

        PrimitiveType attrType = cd.getAttrType(obj.getClassName(), attr);
        if (attrType == PrimitiveType.INTEGER  ||  attrType == PrimitiveType.REAL){
            return new ArithExpr(getObjAttr(obj, attr));
        } else if (attrType == PrimitiveType.STRING){
            return new StrValue(getObjAttr(obj, attr));
        } else if (attrType == PrimitiveType.TIMESTAMP){
            return new OCLTimestamp(getObjAttr(obj, attr));
        } else if (attrType == PrimitiveType.GEOMETRY){
            return new Geometry(getObjAttr(obj, attr));
        } else {
            throw new RuntimeException("Attribute " + attr + " of class " + obj.getClassName() +
                    " is not of type Integer, Real, String, Timestamp or Geometry.");
        }


    }



    @Override
    public OCLElement visitObjAttrValueVar(STOCLParser.ObjAttrValueVarContext ctx) {

        Var var = (Var) visit(ctx.var());
        OCLBag set = this.varEnv.resolve(var.getVarName());
        if (!set.isValueSet()){
            throw new RuntimeException(" Variable " + var.getVarName() + " must be a value set.");
        }

        PrimitiveType varType = set.getSetElementType();
        if (varType == PrimitiveType.INTEGER || varType == PrimitiveType.REAL){
            return new ArithExpr(set);
        } else if (varType == PrimitiveType.STRING){
            return new StrValue(set);
        } else if (varType == PrimitiveType.TIMESTAMP){
            return new OCLTimestamp(set);
        } else if (varType == PrimitiveType.GEOMETRY){
            return new Geometry(set);
        } else {
            throw new RuntimeException(" Variable " + var.getVarName() + " must be of type Integer, Real, String, Timestamp or Geometry.");
        }

    }






    @Override
    public OCLElement visitGeometryDistance(STOCLParser.GeometryDistanceContext ctx) {

        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));


        SqlOperator stDistance = findOp(opTab, "ST_Distance", 2);

        

        if (geom1.isRelation() && geom2.isRelation()) {

            RelNode res;
            int n = geom1.getRelNode().getRowType().getFieldCount();
            String leftLastCol = geom1.getRelNode().getRowType().getFieldNames().get(n-1);

            n = geom2.getRelNode().getRowType().getFieldCount();
            String rightLastCol = geom1.getRelNode().getRowType().getFieldNames().get(n-1);

            if (!new HashSet<>(geom1.getRefSet().getGroupKeys()).equals(new HashSet<>(geom2.getRefSet().getGroupKeys()))){
                throw new RuntimeException(" Both sides of geom distance operation must have the same group keys.");
            }


            builder.push(geom1.getRelNode())
                .push(geom2.getRelNode());

            // 构造join condition: refSet 的groupKeys相等
            List<RexNode> joinFields = geom1.getRefSet().getGroupKeys().stream()
                                        .map(f-> builder.equals(
                                                builder.field(2, 0, f),
                                                builder.field(2, 1, f)
                                        ))
                                        .toList();
            RexNode joinCondition = builder.and(joinFields);

            RelNode joined = builder
                            .join(JoinRelType.INNER, joinCondition)
                            .build();      


            // 投影：groupKeys 列和最后一列

            builder.push(joined);
            List<RexNode> proj = new ArrayList<>();
            for (String key : geom1.getRefSet().getGroupKeys()) proj.add(builder.field(key));



            RexNode disLast = builder.call(
                                stDistance,
                                builder.field(leftLastCol),
                                builder.field(rightLastCol)
            );
            proj.add(disLast);

            // 列别名
            List<String> alias = new ArrayList<>(geom1.getRefSet().getGroupKeys());
            alias.add("distance");

            res = builder.project(proj, alias).build();


            return new ArithExpr(new OCLBag(res,
                                            geom1.getRefSet().getClassName(), 
                                            PrimitiveType.REAL, 
                                            geom1.getRefSet().getSetType(), 
                                            geom1.getRefSet().getGroupKeys()));




        } else if (geom1.isRelation() && !geom2.isRelation()) {

            RelNode res;
            int n = geom1.getRelNode().getRowType().getFieldCount();


            builder.push(geom1.getRelNode());
            // 投影：前 n-1 列来自左表 + (A.n + rightLiteral)列
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));


            RexNode g2 = geom2.isLiteral() ? geom2.getLiteral() : geom2.getRexNode();

            // scalar
            RexNode disLast = builder.call(
                    stDistance,
                    builder.field(n - 1),
                    g2
            );
            proj.add(disLast);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(geom1.getRelNode().getRowType().getFieldNames().get(i));
            alias.add("distance");


            res = builder.project(proj, alias).build();

                

            return new ArithExpr(new OCLBag(res,
                                            geom1.getRefSet().getClassName(), 
                                            PrimitiveType.REAL, 
                                            geom1.getRefSet().getSetType(), 
                                            geom1.getRefSet().getGroupKeys()));


        } else if (!geom1.isRelation() && geom2.isRelation()) {

            RelNode res;
            int n = geom2.getRelNode().getRowType().getFieldCount();


            builder.push(geom2.getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode g1 = geom1.isLiteral() ? geom1.getLiteral() : geom1.getRexNode();

            RexNode disLast = builder.call(
                    stDistance,
                    g1,
                    builder.field(n - 1)
            );
            proj.add(disLast);


            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(geom2.getRelNode().getRowType().getFieldNames().get(i));
            alias.add("distance");



            res = builder.project(proj, alias).build();

            return new ArithExpr(new OCLBag(res,
                                            geom2.getRefSet().getClassName(), 
                                            PrimitiveType.REAL, 
                                            geom2.getRefSet().getSetType(), 
                                            geom2.getRefSet().getGroupKeys()));



        } else {


            if (geom1.isLiteral() && geom2.isLiteral()) {
                RexNode disLast = builder.call(
                        stDistance,
                        geom1.getLiteral(),
                        geom2.getLiteral()
                );
                return new ArithExpr(disLast);

            } else if (geom1.isLiteral() && geom2.isRexNode()){
                RexNode disLast = builder.call(
                        stDistance,
                        geom1.getLiteral(),
                        geom2.getRexNode()
                );
                return new ArithExpr(disLast);

            } else if (geom1.isRexNode() && geom2.isLiteral()){
                RexNode disLast = builder.call(
                        stDistance,
                        geom1.getRexNode(),
                        geom2.getLiteral()
                );
                return new ArithExpr(disLast);

            } else if (geom1.isRexNode() && geom2.isRexNode()) {
                RexNode disLast = builder.call(
                        stDistance,
                        geom1.getRexNode(),
                        geom2.getRexNode()
                );
                return new ArithExpr(disLast);
            } else {
                throw new RuntimeException();
            }


        }

    }







    @Override
    public OCLElement visitSTContains(STOCLParser.STContainsContext ctx) {

        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_Contains", 2);
        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "contains");
        return new SpatialPredicate(geoBoolFunc(result));

    }






    public RelNode geoBoolFunc(Either<RelNode, RexNode> result){

        if (result.isLeft()){
            int n = result.getLeft().getRowType().getFieldCount();
            return builder.push(result.getLeft())
                    .filter(builder.equals(builder.field(n-1),
                            builder.literal(true)))
                    .build();

        } else {
            RexNode rex = result.getRight();

            // 获取当前环境中的对象集合A，若结果为true，返回A，否则返回empty
            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();

            return builder.push(contextObjects)
                    .filter(builder.equals(rex, builder.literal(true)))
                    .build();

        }


    }





    public Either<RelNode, RexNode> geoOpWithOneParams(Geometry geom1, Geometry geom2, SqlOperator stOp, String opStr) {

        if (geom1.isRelation() && geom2.isRelation()) {

            RelNode res;
            int ln = geom1.getRelNode().getRowType().getFieldCount();
            List<String> lfields = new ArrayList<>(geom1.getRelNode().getRowType().getFieldNames());
            lfields.remove(ln-1);

            int rn = geom2.getRelNode().getRowType().getFieldCount();
            List<String> rfields = new ArrayList<>(geom2.getRelNode().getRowType().getFieldNames());
            rfields.remove(rn-1);

            List<String> common = new ArrayList<>(lfields);
            common.retainAll(rfields);

            if (common.isEmpty()){
                throw new RuntimeException("Common fields is empty.");
            }


            builder.push(geom1.getRelNode())
                    .push(geom2.getRelNode());


            // join condition : each common field equal
            List<RexNode> joinFields = common.stream()
                    .map(f-> builder.equals(
                            builder.field(2, 0, f),
                            builder.field(2, 1, f)
                    ))
                    .collect(Collectors.toList());

            RexNode joinCondition = builder.and(joinFields);

            RelNode joined = builder
                    .join(JoinRelType.INNER, joinCondition)
                    .build();


            // 投影：lfields 列和最后一列

            builder.push(joined);
            List<RexNode> proj = new ArrayList<>();
            for (String key : lfields) proj.add(builder.field(key));

            RexNode rex = builder.call(
                    stOp,
                    builder.field(ln-1), // leftLastCol
                    builder.field(ln+rn-1) // rightLastCol
            );

            proj.add(rex);

            // 列别名
            List<String> alias = new ArrayList<>(lfields);
            alias.add(opStr);

            res = builder.project(proj, alias)
                    .filter(builder.equals(builder.field(proj.size()-1),
                                        builder.literal(true)))
                    .build();


            return Either.left(res);


        } else if (geom1.isRelation() && !geom2.isRelation()) {

            RelNode res;
            int n = geom1.getRelNode().getRowType().getFieldCount();


            builder.push(geom1.getRelNode());
            // 投影：前 n-1 列来自左表 + (A.n + rightLiteral)列
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));


            RexNode g2 = geom2.isLiteral() ? geom2.getLiteral() : geom2.getRexNode();

            // scalar
            RexNode disLast = builder.call(
                    stOp,
                    builder.field(n - 1),
                    g2
            );
            proj.add(disLast);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(geom1.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias).build();

            return Either.left(res);


        } else if (!geom1.isRelation() && geom2.isRelation()) {

            RelNode res;
            int n = geom2.getRelNode().getRowType().getFieldCount();


            builder.push(geom2.getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode g1 = geom1.isLiteral() ? geom1.getLiteral() : geom1.getRexNode();

            RexNode disLast = builder.call(
                    stOp,
                    g1,
                    builder.field(n - 1)
            );
            proj.add(disLast);


            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(geom2.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias).build();

            return Either.left(res);

        } else {

            if (geom1.isLiteral() && geom2.isLiteral()) {
                RexNode rex = builder.call(
                        stOp,
                        geom1.getLiteral(),
                        geom2.getLiteral()
                );
                return Either.right(rex);

            } else if (geom1.isLiteral() && geom2.isRexNode()){
                RexNode rex = builder.call(
                        stOp,
                        geom1.getLiteral(),
                        geom2.getRexNode()
                );
                return Either.right(rex);

            } else if (geom1.isRexNode() && geom2.isLiteral()){
                RexNode rex = builder.call(
                        stOp,
                        geom1.getRexNode(),
                        geom2.getLiteral()
                );
                return Either.right(rex);

            } else if (geom1.isRexNode() && geom2.isRexNode()) {
                RexNode rex = builder.call(
                        stOp,
                        geom1.getRexNode(),
                        geom2.getRexNode()
                );
                return Either.right(rex);
            } else {
                throw new RuntimeException();
            }
        }


    }




        @Override
    public OCLElement visitSTContainsProperly(STOCLParser.STContainsProperlyContext ctx) {

        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_ContainsProperly", 2);

        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "containsProperly");
        return new SpatialPredicate(geoBoolFunc(result));
    }

    @Override
    public OCLElement visitSTCoveredBy(STOCLParser.STCoveredByContext ctx) {
        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_CoveredBy", 2);

        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "coveredBy");
        return new SpatialPredicate(geoBoolFunc(result));

    }

    @Override
    public OCLElement visitSTCovers(STOCLParser.STCoversContext ctx) {
        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_Covers", 2);

        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "covers");
        return new SpatialPredicate(geoBoolFunc(result));
    }

    @Override
    public OCLElement visitSTCrosses(STOCLParser.STCrossesContext ctx) {
        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_Crosses", 2);

        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "crosses");
        return new SpatialPredicate(geoBoolFunc(result));
    }

    @Override
    public OCLElement visitSTDisjoint(STOCLParser.STDisjointContext ctx) {
        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_Disjoint", 2);

        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "disjoint");
        return new SpatialPredicate(geoBoolFunc(result));
    }



    @Override
    public OCLElement visitSTEquals(STOCLParser.STEqualsContext ctx) {
        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_Equals", 2);

        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "equals");
        return new SpatialPredicate(geoBoolFunc(result));
    }

    @Override
    public OCLElement visitSTIntersects(STOCLParser.STIntersectsContext ctx) {
        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_Intersects", 2);

        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "intersects");
        return new SpatialPredicate(geoBoolFunc(result));
    }

    @Override
    public OCLElement visitSTOverlaps(STOCLParser.STOverlapsContext ctx) {
        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_Overlaps", 2);

        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "overlaps");
        return new SpatialPredicate(geoBoolFunc(result));
    }

    @Override
    public OCLElement visitSTTouches(STOCLParser.STTouchesContext ctx) {
        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_Touches", 2);

        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "touches");
        return new SpatialPredicate(geoBoolFunc(result));
    }

    @Override
    public OCLElement visitSTWithin(STOCLParser.STWithinContext ctx) {
        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_Within", 2);

        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "within");
        return new SpatialPredicate(geoBoolFunc(result));
    }


    @Override
    public OCLElement visitSTDWithin(STOCLParser.STDWithinContext ctx) {

        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        Double dist = Double.valueOf(ctx.num.getText());

        SqlOperator stOp = findOp(opTab, "ST_DWithin", 3);

        Either<RelNode, RexNode> result;

        if (geom1.isRelation() && geom2.isRelation()) {

            RelNode res;
            int ln = geom1.getRelNode().getRowType().getFieldCount();
            List<String> lfields = new ArrayList<>(geom1.getRelNode().getRowType().getFieldNames());
            lfields.remove(ln-1);

            int rn = geom2.getRelNode().getRowType().getFieldCount();
            List<String> rfields = new ArrayList<>(geom2.getRelNode().getRowType().getFieldNames());
            rfields.remove(rn-1);

            List<String> common = new ArrayList<>(lfields);
            common.retainAll(rfields);

            if (common.isEmpty()){
                throw new RuntimeException("Common fields is empty.");
            }


            builder.push(geom1.getRelNode())
                    .push(geom2.getRelNode());


            // join condition : each common field equal
            List<RexNode> joinFields = common.stream()
                    .map(f-> builder.equals(
                            builder.field(2, 0, f),
                            builder.field(2, 1, f)
                    ))
                    .collect(Collectors.toList());

            RexNode joinCondition = builder.and(joinFields);

            RelNode joined = builder
                    .join(JoinRelType.INNER, joinCondition)
                    .build();


            // 投影：lfields 列和最后一列

            builder.push(joined);
            List<RexNode> proj = new ArrayList<>();
            for (String key : lfields) proj.add(builder.field(key));

            RexNode rex = builder.call(
                    stOp,
                    builder.field(ln-1), // leftLastCol
                    builder.field(ln+rn-1), // rightLastCol
                    builder.literal(dist)
            );

            proj.add(rex);

            // 列别名
            List<String> alias = new ArrayList<>(lfields);
            alias.add("dWithin");

            res = builder.project(proj, alias)
                    .filter(builder.equals(builder.field(proj.size()-1),
                            builder.literal(true)))
                    .build();


            result = Either.left(res);


        } else if (geom1.isRelation() && !geom2.isRelation()) {

            RelNode res;
            int n = geom1.getRelNode().getRowType().getFieldCount();


            builder.push(geom1.getRelNode());
            // 投影：前 n-1 列来自左表 + (A.n + rightLiteral)列
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));


            RexNode g2 = geom2.isLiteral() ? geom2.getLiteral() : geom2.getRexNode();

            // scalar
            RexNode disLast = builder.call(
                    stOp,
                    builder.field(n - 1),
                    g2,
                    builder.literal(dist)
            );
            proj.add(disLast);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(geom1.getRelNode().getRowType().getFieldNames().get(i));
            alias.add("dWithin");

            res = builder.project(proj, alias).build();

            result = Either.left(res);


        } else if (!geom1.isRelation() && geom2.isRelation()) {

            RelNode res;
            int n = geom2.getRelNode().getRowType().getFieldCount();


            builder.push(geom2.getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode g1 = geom1.isLiteral() ? geom1.getLiteral() : geom1.getRexNode();

            RexNode disLast = builder.call(
                    stOp,
                    g1,
                    builder.field(n - 1),
                    builder.literal(dist)
            );
            proj.add(disLast);


            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(geom2.getRelNode().getRowType().getFieldNames().get(i));
            alias.add("dWithin");

            res = builder.project(proj, alias).build();

            result = Either.left(res);

        } else {

            if (geom1.isLiteral() && geom2.isLiteral()) {
                RexNode rex = builder.call(
                        stOp,
                        geom1.getLiteral(),
                        geom2.getLiteral(),
                        builder.literal(dist)
                );
                result = Either.right(rex);

            } else if (geom1.isLiteral() && geom2.isRexNode()){
                RexNode rex = builder.call(
                        stOp,
                        geom1.getLiteral(),
                        geom2.getRexNode(),
                        builder.literal(dist)
                );
                result = Either.right(rex);

            } else if (geom1.isRexNode() && geom2.isLiteral()){
                RexNode rex = builder.call(
                        stOp,
                        geom1.getRexNode(),
                        geom2.getLiteral(),
                        builder.literal(dist)
                );
                result = Either.right(rex);

            } else if (geom1.isRexNode() && geom2.isRexNode()) {
                RexNode rex = builder.call(
                        stOp,
                        geom1.getRexNode(),
                        geom2.getRexNode(),
                        builder.literal(dist)
                );
                result = Either.right(rex);
            } else {
                throw new RuntimeException();
            }
        }

        return new SpatialPredicate(geoBoolFunc(result));
    }




    @Override
    public OCLElement visitSTRelateWithGivenIMatrix(STOCLParser.STRelateWithGivenIMatrixContext ctx) {

        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        // iMatrix is a DE-9IM intersection matrix
        // https://en.wikipedia.org/wiki/DE-9IM
        String raw = ctx.STRING_LITERAL().getText();
        String iMatrix = raw.substring(1, raw.length() - 1);

        if (iMatrix.length() != 9) {
            throw new RuntimeException("The length of iMatrix is not 9.");
        }

        SqlOperator stOp = findOp(opTab, "ST_Relate", 3);

        Either<RelNode, RexNode> result;

        if (geom1.isRelation() && geom2.isRelation()) {

            RelNode res;
            int ln = geom1.getRelNode().getRowType().getFieldCount();
            List<String> lfields = new ArrayList<>(geom1.getRelNode().getRowType().getFieldNames());
            lfields.remove(ln-1);

            int rn = geom2.getRelNode().getRowType().getFieldCount();
            List<String> rfields = new ArrayList<>(geom2.getRelNode().getRowType().getFieldNames());
            rfields.remove(rn-1);

            List<String> common = new ArrayList<>(lfields);
            common.retainAll(rfields);

            if (common.isEmpty()){
                throw new RuntimeException("Common fields is empty.");
            }


            builder.push(geom1.getRelNode())
                    .push(geom2.getRelNode());


            // join condition : each common field equal
            List<RexNode> joinFields = common.stream()
                    .map(f-> builder.equals(
                            builder.field(2, 0, f),
                            builder.field(2, 1, f)
                    ))
                    .collect(Collectors.toList());

            RexNode joinCondition = builder.and(joinFields);

            RelNode joined = builder
                    .join(JoinRelType.INNER, joinCondition)
                    .build();


            // 投影：lfields 列和最后一列
            builder.push(joined);
            List<RexNode> proj = new ArrayList<>();
            for (String key : lfields) proj.add(builder.field(key));

            RexNode rex = builder.call(
                    stOp,
                    builder.field(ln-1), // leftLastCol
                    builder.field(ln+rn-1), // rightLastCol
                    builder.literal(iMatrix)
            );

            proj.add(rex);

            // 列别名
            List<String> alias = new ArrayList<>(lfields);
            alias.add("dWithin");

            res = builder.project(proj, alias)
                    .filter(builder.equals(builder.field(proj.size()-1),
                            builder.literal(true)))
                    .build();

            result = Either.left(res);

        } else if (geom1.isRelation() && !geom2.isRelation()) {

            RelNode res;
            int n = geom1.getRelNode().getRowType().getFieldCount();

            builder.push(geom1.getRelNode());
            // 投影：前 n-1 列来自左表 + (A.n + rightLiteral)列
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode g2 = geom2.isLiteral() ? geom2.getLiteral() : geom2.getRexNode();

            // scalar
            RexNode disLast = builder.call(
                    stOp,
                    builder.field(n - 1),
                    g2,
                    builder.literal(iMatrix)
            );
            proj.add(disLast);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(geom1.getRelNode().getRowType().getFieldNames().get(i));
            alias.add("dWithin");

            res = builder.project(proj, alias).build();

            result = Either.left(res);


        } else if (!geom1.isRelation() && geom2.isRelation()) {

            RelNode res;
            int n = geom2.getRelNode().getRowType().getFieldCount();


            builder.push(geom2.getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode g1 = geom1.isLiteral() ? geom1.getLiteral() : geom1.getRexNode();

            RexNode disLast = builder.call(
                    stOp,
                    g1,
                    builder.field(n - 1),
                    builder.literal(iMatrix)
            );
            proj.add(disLast);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(geom2.getRelNode().getRowType().getFieldNames().get(i));
            alias.add("dWithin");

            res = builder.project(proj, alias).build();

            result = Either.left(res);

        } else {

            if (geom1.isLiteral() && geom2.isLiteral()) {
                RexNode rex = builder.call(
                        stOp,
                        geom1.getLiteral(),
                        geom2.getLiteral(),
                        builder.literal(iMatrix)
                );
                result = Either.right(rex);

            } else if (geom1.isLiteral() && geom2.isRexNode()){
                RexNode rex = builder.call(
                        stOp,
                        geom1.getLiteral(),
                        geom2.getRexNode(),
                        builder.literal(iMatrix)
                );
                result = Either.right(rex);

            } else if (geom1.isRexNode() && geom2.isLiteral()){
                RexNode rex = builder.call(
                        stOp,
                        geom1.getRexNode(),
                        geom2.getLiteral(),
                        builder.literal(iMatrix)
                );
                result = Either.right(rex);

            } else if (geom1.isRexNode() && geom2.isRexNode()) {
                RexNode rex = builder.call(
                        stOp,
                        geom1.getRexNode(),
                        geom2.getRexNode(),
                        builder.literal(iMatrix)
                );
                result = Either.right(rex);
            } else {
                throw new RuntimeException();
            }
        }

        return new SpatialPredicate(geoBoolFunc(result));
    }





    @Override
    public OCLElement visitGeometryPoint(STOCLParser.GeometryPointContext ctx) {
        String wkt = ctx.POINT().getText();
        return new Geometry(getGeomLiteral(wkt));
    }


    public RexNode getGeomLiteral(String wkt) {
        RexBuilder rexBuilder = builder.getCluster().getRexBuilder();
        // 先作为 字面量存起来
        RexLiteral wktLiteral = (RexLiteral) rexBuilder.makeLiteral(wkt);
        SqlOperator stOp = findOp(opTab, "ST_GeomFromText", 1);
        // 再把它喂给 ST_GeomFromText(...) 得到真正几何表达式
        return rexBuilder.makeCall(
                stOp,
                wktLiteral
        );
    }


    @Override
    public OCLElement visitGeometryLinestring(STOCLParser.GeometryLinestringContext ctx) {
        String wkt = ctx.LINESTRING().getText();
        return new Geometry(getGeomLiteral(wkt));
    }



    @Override
    public OCLElement visitGeometryPolygon(STOCLParser.GeometryPolygonContext ctx) {
        String wkt = ctx.POLYGON().getText();
        return new Geometry(getGeomLiteral(wkt));
    }



    @Override
    public OCLElement visitGeometryValueObjAttrValue(STOCLParser.GeometryValueObjAttrValueContext ctx) {
        OCLElement geom = visit(ctx.objAttrValue());
        if (!(geom instanceof Geometry)){
            throw new RuntimeException("The attribute is not of type geometry.");
        }

        return (Geometry) geom;

    }




    @Override
    public OCLElement visitGeometryBuffer(STOCLParser.GeometryBufferContext ctx) {
        Geometry geom = (Geometry) visit(ctx.geom());
        Double bufferDist = Double.parseDouble(ctx.num.getText());

        SqlOperator stBuffer = findOp(opTab, "ST_Buffer", 2);

        if (geom.isRelation()) {

            RelNode res;
            int n = geom.getRelNode().getRowType().getFieldCount();


            builder.push(geom.getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));


            RexNode rex = builder.call(
                    stBuffer,
                    builder.field(n-1),
                    builder.literal(bufferDist)
            );
            proj.add(rex);


            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(geom.getRelNode().getRowType().getFieldNames().get(i));
            alias.add("buffer");



            res = builder.project(proj, alias).build();

            return new Geometry(new OCLBag(res,
                                        geom.getRefSet().getClassName(),
                                        PrimitiveType.GEOMETRY,
                                        geom.getRefSet().getSetType(),
                                        geom.getRefSet().getGroupKeys()));


        } else {
            if (geom.isLiteral()) {
                RexNode rex = builder.call(
                        stBuffer,
                        geom.getLiteral(),
                        builder.literal(bufferDist)
                );
                return new Geometry(rex);


            } else if (geom.isRexNode()) {
                RexNode rex = builder.call(
                        stBuffer,
                        geom.getRexNode(),
                        builder.literal(bufferDist)
                );
                return new Geometry(rex);

            } else {
                throw new RuntimeException(" Geometry must be either literal or RexNode.");
            }


        }

    }




    @Override
    public OCLElement visitGeometryConvexHull(STOCLParser.GeometryConvexHullContext ctx) {
        Geometry geom = (Geometry) visit(ctx.geom());
        SqlOperator stOp = findOp(opTab, "ST_ConvexHull", 1);

        return geoOpWithoutParams(geom, stOp, "convexHull");
    }


    @Override
    public OCLElement visitGeometryCentroid(STOCLParser.GeometryCentroidContext ctx) {
        Geometry geom = (Geometry) visit(ctx.geom());
        SqlOperator stOp = findOp(opTab, "ST_Centroid", 1);

        return geoOpWithoutParams(geom, stOp, "centroid");
    }



    @Override
    public OCLElement visitGeometryEnvelope(STOCLParser.GeometryEnvelopeContext ctx) {
        Geometry geom = (Geometry) visit(ctx.geom());
        SqlOperator stOp = findOp(opTab, "ST_Envelope", 1);

        return geoOpWithoutParams(geom, stOp, "envelope");
    }




    public Geometry geoOpWithoutParams(Geometry geom, SqlOperator stOp, String opStr) {

        if (geom.isRelation()) {

            RelNode res;
            int n = geom.getRelNode().getRowType().getFieldCount();

            builder.push(geom.getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode rex = builder.call(
                    stOp,
                    builder.field(n-1)
            );
            proj.add(rex);


            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(geom.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);


            res = builder.project(proj, alias).build();

            return new Geometry(new OCLBag(res,
                    geom.getRefSet().getClassName(),
                    PrimitiveType.GEOMETRY,
                    geom.getRefSet().getSetType(),
                    geom.getRefSet().getGroupKeys()));

        } else {
            if (geom.isLiteral()) {
                RexNode rex = builder.call(
                        stOp,
                        geom.getLiteral()
                );
                return new Geometry(rex);


            } else if (geom.isRexNode()) {
                RexNode rex = builder.call(
                        stOp,
                        geom.getRexNode()
                );
                return new Geometry(rex);

            } else {
                throw new RuntimeException(" Geometry must be either literal or RexNode.");
            }


        }
    }




    @Override
    public OCLElement visitGeometryUnion(STOCLParser.GeometryUnionContext ctx) {
        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_Union", 2);

        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "union");

        if (result.isLeft()) {
            return new Geometry(new OCLBag(result.getLeft(),
                    geom1.getRefSet().getClassName(),
                    PrimitiveType.REAL,
                    geom1.getRefSet().getSetType(),
                    geom1.getRefSet().getGroupKeys()));
        } else {
            return new Geometry(result.getRight());
        }

    }

    @Override
    public OCLElement visitGeometryIntersection(STOCLParser.GeometryIntersectionContext ctx) {
        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_Intersection", 2);

        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "intersection");

        if (result.isLeft()) {
            return new Geometry(new OCLBag(result.getLeft(),
                    geom1.getRefSet().getClassName(),
                    PrimitiveType.REAL,
                    geom1.getRefSet().getSetType(),
                    geom1.getRefSet().getGroupKeys()));
        } else {
            return new Geometry(result.getRight());
        }
    }

    @Override
    public OCLElement visitGeometryDifference(STOCLParser.GeometryDifferenceContext ctx) {
        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_Difference", 2);
        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "difference");

        if (result.isLeft()) {
            return new Geometry(new OCLBag(result.getLeft(),
                    geom1.getRefSet().getClassName(),
                    PrimitiveType.REAL,
                    geom1.getRefSet().getSetType(),
                    geom1.getRefSet().getGroupKeys()));
        } else {
            return new Geometry(result.getRight());
        }
    }


    @Override
    public OCLElement visitGeometrySymDifference(STOCLParser.GeometrySymDifferenceContext ctx) {
        Geometry geom1 = (Geometry) visit(ctx.geom(0));
        Geometry geom2 = (Geometry) visit(ctx.geom(1));
        SqlOperator stOp = findOp(opTab, "ST_SymDifference", 2);
        Either<RelNode, RexNode> result = geoOpWithOneParams(geom1, geom2, stOp, "symDifference");

        if (result.isLeft()) {
            return new Geometry(new OCLBag(result.getLeft(),
                    geom1.getRefSet().getClassName(),
                    PrimitiveType.REAL,
                    geom1.getRefSet().getSetType(),
                    geom1.getRefSet().getGroupKeys()));
        } else {
            return new Geometry(result.getRight());
        }

    }


    @Override
    public OCLElement visitPeriodContainsTimestamp(STOCLParser.PeriodContainsTimestampContext ctx) {

        Period period = (Period) visit(ctx.period());
        OCLTimestamp timestamp = (OCLTimestamp) visit(ctx.timestamp());

        // e.g., period.left <= timestamp <= period.right
        String lOp = period.isLeftClosed() ? "<=" : "<";
        String rOp = period.isRightClosed() ? ">=" : ">";

        RelNode res = builder.push(timestampCompare(period.getLeftTimestamp(), timestamp, lOp))
                        .push(timestampCompare(period.getRightTimestamp(), timestamp, rOp))
                        .intersect(false)
                        .build();

        return new PeriodPredicate(res);

    }



    public RelNode timestampCompare(OCLTimestamp left, OCLTimestamp right, String compOp) {

        RelNode res;
        Map<String, SqlBinaryOperator> compOpMap = Map.of(
                "<", SqlStdOperatorTable.LESS_THAN,
                "<=", SqlStdOperatorTable.LESS_THAN_OR_EQUAL,
                "=", SqlStdOperatorTable.EQUALS,
                ">=", SqlStdOperatorTable.GREATER_THAN_OR_EQUAL,
                ">", SqlStdOperatorTable.GREATER_THAN,
                "<>", SqlStdOperatorTable.NOT_EQUALS
        );

        if (left.isRelation() && right.isRelation()) {

            // 相关集合为值集合，值的类型为int或real
            if (!left.getRefSet().isValueSet() ){
                throw new RuntimeException(" Left side of comparison must be a value set.");
            }
            if (left.getRefSet().getSetElementType() != PrimitiveType.TIMESTAMP){
                throw new RuntimeException(" Left side of comparison must be a value set of type TIMESTAMP.");
            }

            if (!right.getRefSet().isValueSet() ){
                throw new RuntimeException(" Right side of comparison must be a value set.");
            }
            if (right.getRefSet().getSetElementType() != PrimitiveType.TIMESTAMP ){
                throw new RuntimeException(" Right side of comparison must be a value set of type TIMESTAMP.");
            }

            // join 条件为：
            // left和right中所有相同列取相等并使用and连接，最后一列取compOp并用and连接
            // 如，A:(a,b,c,d), B:(a,b,e), comOp = "<"
            // condition为: A.a=b.a and A.b=B.b and A.d<B.e
            List<String> lFieldNames = new ArrayList<>( left.getRelNode().getRowType().getFieldNames());
            List<String> rFieldNames = new ArrayList<>( right.getRelNode().getRowType().getFieldNames());
            int leftIndex = lFieldNames.size()-1;
            int rightIndex = rFieldNames.size()-1;


            builder.push(left.getRelNode())
                    .push(right.getRelNode());

            // 取列交集，并制作等值条件
            List<RexNode> conditions = new ArrayList<>();
            for(int i=0; i<rightIndex; i++) {
                String colString = rFieldNames.get(i);
                if(lFieldNames.contains(colString)){
                    conditions.add(
                            builder.equals(
                                    builder.field(2, 0, colString),
                                    builder.field(2, 1, colString)
                            )
                    );

                }
            }
            // 取最后一列，并制作条件
            conditions.add(
                    builder.call(
                            compOpMap.get(compOp),
                            builder.field(2, 0, leftIndex),
                            builder.field(2, 1, rightIndex)
                    )
            );

            RexNode condition = builder.and(conditions);

            res = builder.join(JoinRelType.INNER, condition)
                    .build();

        } else if (left.isRelation() && !right.isRelation()) {

            // 相关集合为值集合，值的类型为timestamp
            if (!left.getRefSet().isValueSet() ){
                throw new RuntimeException(" Left side of comparison must be a value set.");
            }
            if (left.getRefSet().getSetElementType() != PrimitiveType.TIMESTAMP){
                throw new RuntimeException(" Left side of comparison must be a value set of type TIMESTAMP.");
            }

            if ( right.getLiteralType() != PrimitiveType.TIMESTAMP){
                throw new RuntimeException(" Right side of comparison must be a value of type TIMESTAMP.");
            }

            int leftIndex = left.getRelNode().getRowType().getFieldCount()-1;

            // left is relation, right is literal
            res = builder.push(left.getRelNode())
                    .filter(builder.call(compOpMap.get(compOp),
                            builder.field(leftIndex),
                            right.getLiteral()))
                    .build();

        } else if (!left.isRelation() && right.isRelation()) {

            // 相关集合为值集合，值的类型为TIMESTAMP
            if ( left.getLiteralType() != PrimitiveType.TIMESTAMP){
                throw new RuntimeException(" Left  must be a value of type TIMESTAMP.");
            }


            if (!right.getRefSet().isValueSet() ){
                throw new RuntimeException(" Right side of comparison must be a value set.");
            }
            if (right.getRefSet().getSetElementType() != PrimitiveType.TIMESTAMP){
                throw new RuntimeException(" Right side of comparison must be a value set of type TIMESTAMP.");
            }

            int rightIndex = right.getRelNode().getRowType().getFieldCount()-1;

            // right is relation, left is literal
            res = builder.push(right.getRelNode())
                    .filter(builder.call(compOpMap.get(compOp),
                            left.getLiteral(),
                            builder.field(2, 0, rightIndex)))
                    .build();

        } else {
            throw new RuntimeException("Both sides of equality cannot be literal: " +
                    left.getLiteral() + " " + compOp + " " + right.getLiteral());
        }

        return res;

    }






    @Override
    public OCLElement visitPeriodContainsPeriod(STOCLParser.PeriodContainsPeriodContext ctx) {
        Period p1 = (Period) visit(ctx.period(0));
        Period p2 = (Period) visit(ctx.period(1));

        // e.g., p1.l <= p2.l <= p2.r <= p1.r
        String lOp = !p1.isLeftClosed() && p2.isLeftClosed() ? "<" : "<=";
        String rOp = !p1.isRightClosed() && p2.isRightClosed()  ? ">" : ">=";

        RelNode res = builder.push(timestampCompare(p1.getLeftTimestamp(), p2.getLeftTimestamp(), lOp))
                .push(timestampCompare(p1.getRightTimestamp(), p2.getRightTimestamp(), rOp))
                .intersect(false)
                .build();

        return new PeriodPredicate(res);
    }



    @Override
    public OCLElement visitPeriodOverlaps(STOCLParser.PeriodOverlapsContext ctx) {
        Period p1 = (Period) visit(ctx.period(0));
        Period p2 = (Period) visit(ctx.period(1));

        // e.g., p1.l <= p2.r and p2.l <= p1.r
        String op1 = p1.isLeftClosed() && p2.isRightClosed() ? "<=" : "<";
        String op2 = p2.isLeftClosed() && p1.isRightClosed()  ? "<=" : "<";

        RelNode res = builder.push(timestampCompare(p1.getLeftTimestamp(), p2.getRightTimestamp(), op1))
                .push(timestampCompare(p2.getLeftTimestamp(), p1.getRightTimestamp(), op2))
                .intersect(false)
                .build();

        return new PeriodPredicate(res);
    }




    @Override
    public OCLElement visitPeriodEquals(STOCLParser.PeriodEqualsContext ctx) {
        Period p1 = (Period) visit(ctx.period(0));
        Period p2 = (Period) visit(ctx.period(1));

        // e.g., p1.l = p2.l and p1.r = p2.r
        RelNode res;
        if (p1.isLeftClosed() == p2.isLeftClosed() && p1.isRightClosed() == p2.isRightClosed()) {
            res = builder.push(timestampCompare(p1.getLeftTimestamp(), p2.getRightTimestamp(), "="))
                    .push(timestampCompare(p2.getLeftTimestamp(), p1.getRightTimestamp(), "="))
                    .intersect(false)
                    .build();

        } else {
            res = builder.push(timestampCompare(p1.getLeftTimestamp(), p2.getRightTimestamp(), "="))
                    .push(timestampCompare(p2.getLeftTimestamp(), p1.getRightTimestamp(), "="))
                    .intersect(false)
                    .filter(builder.literal(false))
                    .build();
        }

        return new PeriodPredicate(res);
    }

    @Override
    public OCLElement visitPeriodPrecedes(STOCLParser.PeriodPrecedesContext ctx) {
        Period p1 = (Period) visit(ctx.period(0));
        Period p2 = (Period) visit(ctx.period(1));

        // e.g., p1.r <= p2.l
        RelNode res = builder.push(timestampCompare(p1.getRightTimestamp(), p2.getLeftTimestamp(), "<="))
                .build();

        return new PeriodPredicate(res);
    }

    @Override
    public OCLElement visitPeriodImmediatelyPrecedes(STOCLParser.PeriodImmediatelyPrecedesContext ctx) {
        Period p1 = (Period) visit(ctx.period(0));
        Period p2 = (Period) visit(ctx.period(1));

        // e.g., p1.r <= p2.l
        RelNode res = builder.push(timestampCompare(p1.getRightTimestamp(), p2.getLeftTimestamp(), "="))
                .build();

        return new PeriodPredicate(res);
    }

    @Override
    public OCLElement visitPeriodSucceeds(STOCLParser.PeriodSucceedsContext ctx) {
        Period p1 = (Period) visit(ctx.period(0));
        Period p2 = (Period) visit(ctx.period(1));

        // e.g., p1.r <= p2.l
        RelNode res = builder.push(timestampCompare(p1.getLeftTimestamp(), p2.getRightTimestamp(), ">="))
                .build();

        return new PeriodPredicate(res);
    }

    @Override
    public OCLElement visitPeriodImmediatelySucceeds(STOCLParser.PeriodImmediatelySucceedsContext ctx) {
        Period p1 = (Period) visit(ctx.period(0));
        Period p2 = (Period) visit(ctx.period(1));

        // e.g., p1.r <= p2.l
        RelNode res = builder.push(timestampCompare(p1.getLeftTimestamp(), p2.getRightTimestamp(), ">"))
                .build();

        return new PeriodPredicate(res);
    }

    @Override
    public OCLElement visitPeriodTimestamp(STOCLParser.PeriodTimestampContext ctx) {
        OCLTimestamp lt = (OCLTimestamp) visit(ctx.timestamp(0));
        OCLTimestamp rt = (OCLTimestamp) visit(ctx.timestamp(1));
        boolean isLeftClosed = "[".equals(ctx.lp.getText());
        boolean isRightClosed = "]".equals(ctx.rp.getText());

        return new Period(lt, rt, isLeftClosed, isRightClosed );

    }




    @Override
    public OCLElement visitTimestampLiteral(STOCLParser.TimestampLiteralContext ctx) {

        String raw = ctx.TIMESTAMP_LITERAL().getText();
        String tsStr = raw.substring(1, raw.length() - 1);
        int precision = 0;
        try {
            RexLiteral tsLiteral = (RexLiteral) builder.getRexBuilder()
                    .makeTimestampLiteral(new TimestampString(tsStr), precision);

            return new OCLTimestamp(new Literal(tsLiteral));
        } catch (Exception e) {
            throw new RuntimeException("Invalid timestamp string.");
        }

    }



    @Override
    public OCLElement visitTimestampValueObjAttrValue(STOCLParser.TimestampValueObjAttrValueContext ctx) {
        OCLElement elem = visit(ctx.objAttrValue());
        if (!(elem instanceof OCLTimestamp)){
            throw new RuntimeException(" The objAttrValue is not of type timestamp.");
        }
        return (OCLTimestamp) elem;
    }






    @Override
    public EqualExpr visitEqualityExprTimestamp(STOCLParser.EqualityExprTimestampContext ctx) {


        OCLTimestamp lt = (OCLTimestamp) visit(ctx.timestamp(0));
        OCLTimestamp rt = (OCLTimestamp) visit(ctx.timestamp(1));
        String compOp = ctx.compOp.getText();

        return new EqualExpr(timestampCompare(lt, rt, compOp));



    }





    }
