package nju.ics.grammar.translator;


import com.google.common.collect.ImmutableList;
import nju.ics.grammar.stocl.PrimitiveType;
import nju.ics.grammar.stocl.STOCLBaseVisitor;
import nju.ics.grammar.stocl.STOCLParser;
import nju.ics.grammar.stocl.VarEnv;
import nju.ics.grammar.translator.elements.*;
import nju.ics.model.schema.UMLSchema;
import nju.ics.model.uml.UMLClassDiagram;

import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.JoinRelType;
import org.apache.calcite.rel.logical.LogicalValues;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rex.RexInputRef;
import org.apache.calcite.rex.RexLiteral;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.rex.RexSubQuery;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlBinaryOperator;
import org.apache.calcite.sql.SqlOperator;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.RelBuilder;
import org.apache.calcite.schema.Table;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class OCLTranslator extends STOCLBaseVisitor<OCLElement> {

    UMLClassDiagram cd;
    RelBuilder builder;


    VarEnv<OCLBag> varEnv = new VarEnv<>();

    public OCLTranslator() {
    }

    public OCLTranslator(UMLClassDiagram cd) {
        this.cd = cd;

        // UMLSchemaFactory schemaFactory = new UMLSchemaFactory(cd);
        Schema schema = new UMLSchema(cd);

        // 1) create root schema
        SchemaPlus root = Frameworks.createRootSchema(true);

        // create sub-schema in root schema
        // return schema object
        // SchemaPlus hw = root.add(cd.getName(), schema);

        // 复制所有表到 root
        for (String tableName : schema.getTableNames()) {
            Table table = schema.getTable(tableName);
            root.add(tableName, table);
        }

        // 4)set schema to default schema
        FrameworkConfig config = Frameworks.newConfigBuilder().defaultSchema(root).build();

        // 3) use RelBuilder to build RA tree
        this.builder = RelBuilder.create(config);


    }



    /**
     * specification: 'Model' ID ':' context* EOF
     * 
     * @param ctx the parse tree
     * @return Specification instance
     */
    @Override
    public OCLElement visitSpecification(STOCLParser.SpecificationContext ctx) {
        List<STOCLParser.ContextContext> contextContexts = ctx.context();
        String id = ctx.ID().getText();


        if (!id.equals(this.cd.getName())) {
            throw new RuntimeException("Specification name " + id + " does not match UML class diagram name " + this.cd.getName());
        }

        List<Context> contexts = new ArrayList<>();

        for (STOCLParser.ContextContext context : contextContexts) {
            contexts.add((Context) visit(context));
        }

        return new Specification(id, contexts);
    }



    /**
     * context: 'context' ID (inv)+
     * 
     * @param ctx the parse tree
     * @return Context instance
     */
    @Override
    public OCLElement visitContext(STOCLParser.ContextContext ctx) {

        String className = ctx.ID().getText();

        // add var 'self' to varMap
        RelNode var = builder.scan(className)
                .project(builder.field(UMLClassDiagram.getObjectIDColumn(className)))
                .build();

        OCLBag selfSet = new OCLBag(var, className, PrimitiveType.OBJECT, OCLBag.SetType.SINGLE_SET, new ArrayList<>());

        // 创建全局作用域，放入变量self对应的对象集合
        this.varEnv.pushScope();
        this.varEnv.put("self", selfSet);


        List<STOCLParser.InvContext> inv = ctx.inv();
        List<Inv> invs = new ArrayList<>();

        for (STOCLParser.InvContext invContext : inv) {
            invs.add((Inv) visit(invContext));
        }

        // 删除全局作用域
        this.varEnv.popScope();

        return new Context(className, invs);

    }



    /**
     * inv : 'inv' ID? ':' oclBool
     * - oclBool为relation，表示满足约束的对象集合，列为当前环境中的变量列
     * - 对oclBool投影出id列
     * - 使用全集与oclBool进行差集操作，得到不满足条件的对象集合
     * - 若集合为空，则表示所有对象都满足不变式，不为空则表示存在对象不满足不变式
     * 
     * @param ctx the parse tree
     * @return OCLBool instance
     */
    @Override
    public OCLElement visitInv(STOCLParser.InvContext ctx) {


        String invName = ctx.ID() != null ? ctx.ID().getText() : null;
        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());

        // get whole set
        OCLBag universalSet = varEnv.resolve("self");


        //oclBool具有和universalSet相同的列
        if (!new HashSet<>(universalSet.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }


        // inv = wholeSet - oclBool
        RelNode inv = builder.push(universalSet.getRelNode())
                .push(oclBool.getRelNode())
                .minus(false, 2)
                .aggregate(
                        builder.groupKey(), builder.countStar("cnt"))
                .project(
                        List.of(builder.call(SqlStdOperatorTable.EQUALS,
                                builder.field("cnt"), builder.literal(0))), // fields
                        List.of("satisfied")) // alias
                .build();


        return new Inv(invName, ctx.getText(), inv);


    }



    /**
     * oclBool : 'not' oclBool
     * - oclBool须为relation，表示满足约束的对象集合，与当前环境变量的列相同
     * - 使用全集与oclBool进行差集操作
     * 
     * @param ctx the parse tree
     * @return OCLBool instance
     */
    @Override
    public OCLElement visitOclBoolNot(STOCLParser.OclBoolNotContext ctx) {

        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());

        // get whole set
        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());

        // oclBool具有和universalSet相同的列
        if (!new HashSet<>(universalSet.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }

        // 使用全集与oclBool进行差集操作
        RelNode res = builder.push(universalSet.getRelNode())
                .push(oclBool.getRelNode())
                .minus(false, 2)
                .build();


        return new OCLBool(res);

    }



    /**
     * oclBool boolOp=('and'|'or') oclBool
     * - 两个oclBool均须为满足约束的对象集合 relation
     * - 取当前栈顶中的var对应的universalSet
     * - 对两个oclBool进行投影，列为universalSet的列
     *      - 若 boolOp为'and'，进行intersection操作
     *      - 若 boolOp 为'or'，进行union操作
     * 
     * @param ctx the parse tree
     * @return OCLBool instance
     */
    @Override
    public OCLElement visitOclBoolAndOr(STOCLParser.OclBoolAndOrContext ctx) {


        OCLBool bool1 = (OCLBool) visit(ctx.oclBool(0));
        OCLBool bool2 = (OCLBool) visit(ctx.oclBool(1));

        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());


        // bool1具有和universalSet相同的列
        // bool2具有和universalSet相同的列
        if (!new HashSet<>(universalSet.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(bool1.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }
        if (!new HashSet<>(universalSet.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(bool2.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }

        RelNode left = bool1.getRelNode();
        RelNode right = bool2.getRelNode();

        OCLBool resultBool;


        if (ctx.boolOp.getText().equals("and")) {
            // intersection
            RelNode and = builder.push(left)
                    .push(right)
                    .intersect(false)
                    .build();
            resultBool = new OCLBool(and);
        } else {
            // union
            RelNode or = builder.push(left)
                    .push(right)
                    .union(false)
                    .build();
            resultBool = new OCLBool(or);
        }


        return resultBool;


    }



    /**
     * oclBool boolOp=('implies'|'xor') oclBool
     * - 两个oclBool均须为满足约束的对象集合 relation
     * - 取当前栈顶中的var对应的universalSet
     * - 对两个oclBool进行投影，列为universalSet的列
     *      - 若 boolOp为'implies'，进行¬A∨B操作
     *      - 若 boolOp 为'xor'，进行(A∖B)∪(B∖A)操作
     *
     * @param ctx the parse tree
     * @return OCLBool instance
     */
    @Override
    public OCLElement visitOclBoolImpliesXor(STOCLParser.OclBoolImpliesXorContext ctx) {

        OCLBool bool1 = (OCLBool) visit(ctx.oclBool(0));
        OCLBool bool2 = (OCLBool) visit(ctx.oclBool(1));

        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());


        // bool1具有和universalSet相同的列
        // bool2具有和universalSet相同的列
        if (!new HashSet<>(universalSet.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(bool1.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }
        if (!new HashSet<>(universalSet.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(bool2.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }

        RelNode left = bool1.getRelNode();
        RelNode right = bool2.getRelNode();

        OCLBool resultBool;

        // both are relation
        if (ctx.boolOp.getText().equals("implies")) {
            // implies : A→B≡¬A∨B
            RelNode implies = builder.push(universalSet.getRelNode())
                    .push(left)
                    .minus(false, 2)
                    .push(right)
                    .union(false,2)
                    .build();

            resultBool = new OCLBool(implies);


        } else {
            // xor : (A∖B)∪(B∖A)
            RelNode A = builder.push(left)
                    .push(right)
                    .minus(false, 2)
                    .build();

            RelNode B = builder.push(right)
                    .push(left)
                    .minus(false, 2)
                    .build();

            RelNode xor = builder.push(A).push(B).union(false, 2)
                    .build();
            resultBool = new OCLBool(xor);
        }


        return resultBool;


    }


    /**
     * equalExpr
     *
     * @param ctx the parse tree
     * @return OCLBool instance
     */
    @Override
    public OCLElement visitOclBoolEqualityExpr(STOCLParser.OclBoolEqualityExprContext ctx) {

        EqualExpr equalExpr = (EqualExpr) visit(ctx.equalExpr());


        // 处理返回的OCLBool包含的列
        // get whole set
        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());

        builder.push(equalExpr.getRelNode());

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


    /**
     * oclObj '.' bAttr
     * - 与obj所属类进行自然连接
     * - 选择bAttr为真的对象
     *
     * @param ctx the parse tree
     * @return OCLBool instance
     */
    @Override
    public OCLElement visitOclBoolBAttr(STOCLParser.OclBoolBAttrContext ctx) {

        String bAttr = ctx.bAttr().getText();

        // obj 对应的集合可能是整体集合也可能是分组集合
        OCLObj oclObj = (OCLObj) visit(ctx.oclObj());


        String objTableName = oclObj.getClassName();    // obj对应的class
        String idColName = UMLClassDiagram.getObjectIDColumn(objTableName); // obj对应的class的id列
        RelNode objTable = builder.scan(objTableName)       // 扫描class表并投影出id、attr列
                .project(builder.field(idColName),  // field
                        builder.field(bAttr)) // field
                .build();

        builder.push(oclObj.getRelNode())
                .push(objTable)
                .join(JoinRelType.INNER,
                        builder.equals(
                                builder.field(2, 0, idColName),
                                builder.field(2, 1, idColName))); // 根据id=id进行inner join

        List<RexInputRef> fields = oclObj.getRelNode()
                                                    .getRowType()
                                                    .getFieldNames()
                                                    .stream()
                                                    .map(f -> builder.field(f)).collect(Collectors.toList());
        fields.add(builder.field(bAttr));


        
        // 投影出obj对象所属集合的所有列，以及bAttr列
        RelNode project = builder.project(fields)
                                // .distinct()
                                .build(); // field

        // 选择出bAttr为true的行
        RelNode filter = builder.push(project)
                .filter(builder.equals(builder.field(bAttr),
                        builder.literal(true)))
                .build();



        // 处理返回的OCLBool包含的列
        // get whole set
        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());

        builder.push(filter);

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


    /**
     * SetPredicate
     *
     * @param ctx the parse tree
     * @return OCLBool instance
     */
    @Override
    public OCLElement visitOclBoolBagPredicate(STOCLParser.OclBoolBagPredicateContext ctx) {
        BagPredicate bagPredicate = (BagPredicate) visit(ctx.bagPredicate());

        // 处理返回的OCLBool包含的列
        // get whole set
        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());

        builder.push(bagPredicate.getRelNode());
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


    /**
     * '(' oclBool ')'
     *
     * @param ctx the parse tree
     * @return OCLBool instance
     */
    @Override
    public OCLElement visitOclBoolParen(STOCLParser.OclBoolParenContext ctx) {

        return visit(ctx.oclBool());
    }


    /**
     * oclBag '->includesAll(' oclBag ')'
     *  - 根据两个set的类型分情况讨论
     *
     * @param ctx the parse tree
     * @return SetPredicate instance
     */
    @Override
    public OCLElement visitIncludesAll(STOCLParser.IncludesAllContext ctx) {



        OCLBag set1 = (OCLBag) visit(ctx.oclBag(0));
        OCLBag set2 = (OCLBag) visit(ctx.oclBag(1));

        // 获取当前环境中的groupKeys
        // List<String> contextGroupkeys = varMap.get(currentVarName.peek()).getGroupKeys();

        // set1和set2分别可能是'单个集合'或'分组集合'（集合的集合，每个对象对应一个集合）
        // - 单个集合: 整体作为一个集合，可能有多列，最后一列通过对象集合 Class.allInstances()，或集合字面值 Set{1， 3，5}，或oclBag '.' roleOrAttr 得到
        // - 分组集合: 通过分组形成每个组为一个集合，只能通过multi-role得到，set(set(), set(), set())
        // 因此set1和set2之间的操作分为四种情况讨论
        // 1. '单个集合' 与 '单个集合'
        // 2. '单个集合' 与 '分组集合'
        // 3. '分组集合' 与 '单个集合'
        // 4. '分组集合' 与 '分组集合'
        RelNode res;
        if (set1.isSingleSet() ){
            if (set2.isSingleSet()){
                // 1. '单个集合' 与 '单个集合' (全局不变式)
                // 如: context Car inv: Car.allInstances()->includesAll(Set{})
                // 此时语义为：是否左侧单个集合包含右侧单个集合中的所有元素
                // 若 '单个集合' 包含 '单个集合'，则返回当前环境所有对象，否则返回empty

                // 计算set2的基数
                RelNode set2Count = builder.push(set2.getRelNode())
                        .aggregate(builder.groupKey(), builder.count(false, "b_count"))
                        .build();
                // 计算set1交set2的基数
                RelNode interCount = builder.push(set1.getRelNode())
                        .push(set2.getRelNode())
                        .intersect(false, 2)
                        .aggregate(builder.groupKey(), builder.count(false, "i_count"))
                        .build();
                // 若set1交set2的基数 与 set2的基数相等，则结果为true，否则为false
                RelNode countJoin = builder.push(set2Count).push(interCount)
                        .join(JoinRelType.INNER,
                                builder.equals(builder.field(2, 0, "b_count"),
                                                builder.field(2, 1, "i_count")))
                        .build();



                // 获取当前环境中的对象集合A，若结果为true，返回A，否则返回empty
                RexNode condition = builder.scalarQuery(b -> {
                        b.push(countJoin)
                            .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                            .project(
                                    List.of(builder.equals(builder.field("cnt"), builder.literal(1))), // fields
                                    List.of("satisfied") // alias
                            );
                        return b.build();
                });

                RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
                res = builder.push(contextObjects)
                        .filter(condition)
                        .build();


            } else {
                // 2. '单个集合' 与 '分组集合'
                // 如：context Person inv: Car.allInstances()->select(c| c.type=1)->includesAll(self.ownCar)
                // Bag {1,2,3}->includesAll(self.nrole)
                // 此时语义为：分组集合中集合中的所有元素是否被单个集合包含
                // 返回 '单个集合' 包含 '分组集合' 中的集合的分组


                // set2 分组聚合
                RelNode set2GroupAndCount = builder.push(set2.getRelNode())
                        .aggregate(builder.groupKey(set2.getGroupKeys().stream().map(f->builder.field(f)).toList())
                                ,builder.count(false, "b_count"))
                        .build();

                // 单个集合与分组集合内连接，等于取交集，以当前环境中的groupKeys进行聚合
                RelNode innerJoin = builder.push(set1.getRelNode())
                        .push(set2.getRelNode())
                        .join(JoinRelType.INNER,
                                builder.equals(
                                        builder.field(2, 0, set1.getRelNode().getRowType().getFieldCount()-1),
                                        builder.field(2, 1, set2.getRelNode().getRowType().getFieldCount()-1)
                                )
                        ).aggregate(builder.groupKey(set2.getGroupKeys().stream().map(f->builder.field(f)).toList()),
                                                        builder.count(false, "i_count"))
                        .build();


                // 若innerJoin 与 set2GroupAndCount相等，则保留该行，表示该组满足被包含关系
                // 返回满足被单个集合包含的分组
                builder.push(innerJoin).push(set2GroupAndCount);

                // join condition : each groupkey equal, and i_count equals to b_count
                List<RexNode> joinFields = set2.getGroupKeys().stream()
                                                .map(f-> builder.equals(
                                                        builder.field(2, 0, f),
                                                        builder.field(2, 1, f)
                                                ))
                                                .collect(Collectors.toList());

                joinFields.add(builder.equals(
                                        builder.field(2, 0, "i_count"),
                                        builder.field(2, 1, "b_count")
                                                ));
                RexNode joinCondition = builder.and(joinFields);

                res = builder.join(JoinRelType.INNER, joinCondition)
                            .build();



            }

        } else {
            if(set2.isSingleSet()) {
                // 3. '分组集合' 与 '单个集合'
                // 如: context Student inv : self.selectedCourse->includesAll( Set {'语文', '数学'} )
                // 如: context Student inv : self.academicYear.selectedCourse->includesAll( Set {'语文', '数学'} )
                // 多个1对多的role会被collect+flatten，所以分组键为第一列（即对象列）
                // 此时语义为：分组集合中的每个集合是否包含单个集合中的所有元素
                // 返回 '分组集合' 包含 '单个集合' 中所有元素的分组

                // 计算单个集合基数为标量
                RexNode set2Count = builder.scalarQuery(b -> {
                    b.push(set2.getRelNode());
                    b.aggregate(builder.groupKey(), builder.count(false, "b_count"));
                    return b.build();
                });


                // 分组集合与单个集合内连接，等于取交集
                RelNode innerJoin = builder.push(set1.getRelNode())
                        .push(set2.getRelNode())
                        .join(JoinRelType.INNER,
                                builder.equals(
                                        builder.field(2, 0, set1.getRelNode().getRowType().getFieldCount()-1),
                                        builder.field(2, 1, set2.getRelNode().getRowType().getFieldCount()-1)
                                )
                        ).build();




                // 以当前环境中的groupKeys对分组集合进行聚合，统计每个集合包含单个集合元素的数量是否等于单个集合基数，过滤不相等的分组
                // 返回包含单个集合的分组
                res = builder.push(innerJoin)
                        .aggregate(builder.groupKey(set1.getGroupKeys().stream().map(f->builder.field(f)).toList()),
                                            builder.count(false, "i_count"))
                        .filter(builder.equals(
                                builder.field("i_count"),
                                set2Count
                        ))
                        .build();



            } else {

                if (!new HashSet<>(set1.getGroupKeys()).equals(new HashSet<>(set1.getGroupKeys()))){
                    throw new RuntimeException(" Group keys of two oclBags should be the same for includesAll operation.");
                }


                // 4. '分组集合' 与 '分组集合'
                // 如每个学生所有学年选课的集合应包含所属学位的必修课的集合: context Student inv : self.academicYear.selectedCourse->includesAll(self.degree.mandatoryCourse)
                // 其中academicYear和selectedCourse都为1对多导航；degree为1对1导航，mandatoryCourse为1对多导航
                // 此时语义为：对于两个分组集合中的相同分组，是否左侧分组集合中所有组集合包含右侧中对应组的所有元素
                // 返回 满足包含关系的分组

                RelNode set2GroupAndCount = builder.push(set2.getRelNode())
                        .aggregate(builder.groupKey(set1.getGroupKeys().stream().map(f->builder.field(f)).toList())
                                                    ,builder.count(false, "b_count"))
                        .build();

                // 分组集合与分组集合内连接，等于取交集
                builder.push(set1.getRelNode())
                        .push(set2.getRelNode());


                // join condition : each groupkey equal, and last cole equals to last col
                List<RexNode> joinFields = set1.getGroupKeys().stream()
                                                .map(f-> builder.equals(
                                                        builder.field(2, 0, f),
                                                        builder.field(2, 1, f)
                                                ))
                                                .collect(Collectors.toList());

                joinFields.add(builder.equals(
                                        builder.field(2, 0, set1.getRelNode().getRowType().getFieldCount()-1),
                                        builder.field(2, 1, set2.getRelNode().getRowType().getFieldCount()-1)
                                                ));
                RexNode joinCondition1 = builder.and(joinFields);


                RelNode innerJoin = builder.join(JoinRelType.INNER, joinCondition1)
                                            .aggregate(builder.groupKey(set1.getGroupKeys().stream().map(f->builder.field(f)).toList())
                                                    ,builder.count(false, "i_count"))
                                            .build();


                // join 条件为所有groupKey相等，i_count和 b_count相等                    
                builder.push(innerJoin)
                        .push(set2GroupAndCount);

                joinFields.remove(joinFields.size()-1);
                joinFields.add(builder.equals(builder.field(2, 0, "i_count"),
                                             builder.field(2, 1, "b_count")));
                RexNode joinCondition2 = builder.and(joinFields);

                // 若innerJoin 与 set2GroupAndCount相等，则保留该行
                res = builder.join(JoinRelType.INNER, joinCondition2)
                        .build();

            }
        }


        return new BagPredicate(res);


    }


    /**
     * oclBag '->excludesAll(' oclBag ')'
     *  - 根据两个set的类型分情况讨论，参考 "includesAll"
     *
     * @param ctx the parse tree
     * @return SetPredicate instance
     */
    @Override
    public OCLElement visitExcludesAll(STOCLParser.ExcludesAllContext ctx) {
        OCLBag set1 = (OCLBag) visit(ctx.oclBag(0));
        OCLBag set2 = (OCLBag) visit(ctx.oclBag(1));

        // 获取当前环境中的groupKeys
        // List<String> contextGroupkeys = varMap.get(currentVarName.peek()).getGroupKeys();

        // set1和set2分别可能是'单个集合'或'分组集合', 因此set1和set2之间的操作分为四种情况讨论
        // 1. '单个集合' 与 '单个集合'
        // 2. '单个集合' 与 '分组集合'
        // 3. '分组集合' 与 '单个集合'
        // 4. '分组集合' 与 '分组集合'
        RelNode res;
        if (set1.isSingleSet() ){
            if (set2.isSingleSet()){
                // 1. '单个集合' 与 '单个集合' (全局不变式)
                // 此时语义为：是否左侧单个集合不包含右侧单个集合中的任一元素
                // 若 '单个集合' 不包含 '单个集合'任一元素，则返回当前环境所有对象，否则返回empty


                // 计算set1交set2的基数
                RelNode interCount = builder.push(set1.getRelNode())
                        .push(set2.getRelNode())
                        .intersect(false, 2)
                        .aggregate(builder.groupKey(), builder.count(false, "i_count"))
                        .build();


                // 获取当前环境中的对象集合A，若interCount的基数=0，结果为true，返回A，否则返回empty
                RexNode condition = builder.scalarQuery(b -> {
                    b.push(interCount)
                        .project(
                                List.of(b.equals(b.field("i_count"), b.literal(0))), // fields
                                List.of("satisfied") // alias
                        );
                    return b.build();
                });

                RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
                res = builder.push(contextObjects)
                        .filter(condition)
                        .build();


            } else {
                // 2. '单个集合' 与 '分组集合'
                // 此时语义为：分组集合中集合中的任一元素是否被单个集合包含
                // 返回 '单个集合' 不包含 '分组集合' 中的任一元素的集合的分组

                RelNode contextObjects =
                varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
                
                List<String> gks = set2.getGroupKeys();
                
                /* 1) 所有可能的分组（来自上下文，而不是 B 本身） */
                RelNode allGroups =
                    builder.push(contextObjects)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();
                
                /* 2) A 与 B 的交集（命中行） */
                RelNode hitRows =
                    builder.push(set1.getRelNode())
                        .push(set2.getRelNode())
                        .join(
                            JoinRelType.INNER,
                            builder.equals(
                                builder.field(2, 0,
                                    set1.getRelNode().getRowType().getFieldCount() - 1),
                                builder.field(2, 1,
                                    set2.getRelNode().getRowType().getFieldCount() - 1)
                            )
                        )
                        .build();
                
                /* 3) 命中分组（存在交集的分组） */
                RelNode hitGroups =
                    builder.push(hitRows)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();
                
                /* 4) excludesAll 结果：AllGroups − HitGroups */
                res = builder.push(allGroups)
                            .push(hitGroups)
                            .minus(false, 2)
                            .build();


            }

        } else {
            if(set2.isSingleSet()) {
                // 3. '分组集合' 与 '单个集合'
                // 此时语义为：分组集合中的每个集合是否不包含单个集合中的任一元素
                // 返回 '分组集合' 不包含 '单个集合' 中任一元素的分组


                RelNode contextObjects =
                    varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();

                List<String> gks = set1.getGroupKeys();

                /* 1) 所有可能的分组（来自上下文，而不是来自 set1 本身） */
                RelNode allGroups =
                    builder.push(contextObjects)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();

                /* 2) A 与 B 的交集（命中行） */
                RelNode hitRows =
                    builder.push(set1.getRelNode())
                        .push(set2.getRelNode())
                        .join(
                            JoinRelType.INNER,
                            builder.equals(
                                builder.field(2, 0,
                                    set1.getRelNode().getRowType().getFieldCount() - 1),
                                builder.field(2, 1,
                                    set2.getRelNode().getRowType().getFieldCount() - 1)
                            )
                        )
                        .build();

                /* 3) 命中分组（存在交集的分组） */
                RelNode hitGroups =
                    builder.push(hitRows)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();

                /* 4) excludesAll 结果：AllGroups − HitGroups */
                res = builder.push(allGroups)
                            .push(hitGroups)
                            .minus(false, 2)
                            .build();



            } else {
                if (!new HashSet<>(set1.getGroupKeys()).equals(new HashSet<>(set1.getGroupKeys()))){
                    throw new RuntimeException(" Group keys of two oclBags should be the same for includesAll operation.");
                }

                // 4. '分组集合' 与 '分组集合'
                // 此时语义为：对于两个分组集合中的相同分组，是否左侧分组集合不包含右侧中对应分组集合的任一元素
                // 返回 满足不包含关系的分组

                RelNode contextObjects =
                    varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();

                List<String> gks = set1.getGroupKeys(); // 假设 set1/set2 的 GK 相同

                /* 1) 所有可能的分组（来自上下文全集） */
                RelNode allGroups =
                    builder.push(contextObjects)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();

                /* 2) A 与 B 在“同一分组 + 元素相等”的交集行 */
                builder.push(set1.getRelNode())
                    .push(set2.getRelNode());

                // join condition: GK 相等 ∧ elem 相等
                List<RexNode> joinConds =
                    gks.stream()
                    .map(f -> builder.equals(
                        builder.field(2, 0, f),
                        builder.field(2, 1, f)))
                    .collect(Collectors.toList());

                joinConds.add(builder.equals(
                    builder.field(2, 0,
                        set1.getRelNode().getRowType().getFieldCount() - 1),
                    builder.field(2, 1,
                        set2.getRelNode().getRowType().getFieldCount() - 1)
                ));

                RexNode joinCondition = builder.and(joinConds);

                RelNode hitRows =
                    builder.join(JoinRelType.INNER, joinCondition)
                        .build();

                /* 3) 有交集的分组 */
                RelNode hitGroups =
                    builder.push(hitRows)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();

                /* 4) excludesAll 结果：AllGroups − HitGroups */
                res = builder.push(allGroups)
                            .push(hitGroups)
                            .minus(false, 2)
                            .build();


            }
        }

        return new BagPredicate(res);


    }


    /**
     * oclBag '->includes(' literal ')'
     * - 根据set的类型分情况讨论
     *
     * @param ctx the parse tree
     * @return SetPredicate instance
     */
    @Override
    public OCLElement visitIncludes(STOCLParser.IncludesContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        Literal literal = (Literal) visit(ctx.literal());



        // set可能是'单个集合'或'分组集合', 因此set和literal之间的操作分为二种情况讨论
        // 1. '单个集合' 与 'literal'
        // 2. '分组集合' 与 'literal'

        RelNode res;

        if (set.isSingleSet()) {
            // 1. '单个集合' 与 'literal'
            // 如: context Car inv: Car.allInstances().plate->includes("A0000")
            // 若单个集合包含literal，则为true，返回当前环境所有对象，否则返回empty

            // 过滤相等的元素
            RelNode filter = builder.push(set.getRelNode())
                    .filter(builder.equals(
                            builder.field(set.getRelNode().getRowType().getFieldCount()-1),
                            literal.getValue()
                    ))
                    .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                    .build();

            // 获取当前环境中的对象集合A，若interCount的基数>0，结果为true，返回A，否则返回empty
            RexNode condition = builder.scalarQuery(b -> {
                b.push(filter)
                    .project(
                        List.of(b.greaterThan(
                                b.field("cnt"),
                                b.literal(0))), // fields
                        List.of("satisfied") // alias
                    );
                return b.build();
            });

            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();

        } else {
            // 1. '分组集合' 与 'literal'
            // 如: context Person inv: self.ownCar.plate->includes("A0000")
            // 若分组集合中包含literal的组

            // 过滤包含literal的组
            res = builder.push(set.getRelNode())
                .filter(builder.equals(
                        builder.field(set.getRelNode().getRowType().getFieldCount()-1),
                        literal.getValue()
                ))
                .project(
                    set.getGroupKeys().stream()
                        .map(builder::field)
                        .toList(),
                    set.getGroupKeys()
                )
                .build();

        }


        return new BagPredicate(res);
    }


    /**
     * oclBag '->excludes(' literal ')'
     * - 根据set的类型分情况讨论，参考includes
     *
     * @param ctx the parse tree
     * @return SetPredicate instance
     */
    @Override
    public OCLElement visitExcludes(STOCLParser.ExcludesContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        Literal literal = (Literal) visit(ctx.literal());

        // set可能是'单个集合'或'分组集合', 因此set和literal之间的操作分为二种情况讨论
        // 1. '单个集合' 与 'literal'
        // 2. '分组集合' 与 'literal'

        RelNode res;

        if (set.isSingleSet()) {
            // 1. '单个集合' 与 'literal'
            // 若单个集合不包含literal，则为true，返回当前环境所有对象，否则返回empty

            // 过滤相等的元素
            RelNode filter = builder.push(set.getRelNode())
                    .filter(builder.equals(
                            builder.field(set.getRelNode().getRowType().getFieldCount()-1),
                            literal.getValue()
                    ))
                    .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                    .build();

            // 获取当前环境中的对象集合A，若interCount的基数=0，结果为true，返回A，否则返回empty
            RexNode condition = builder.scalarQuery(b -> {
                b.push(filter)
                    .project(
                        List.of(b.equals(
                                b.field("cnt"),
                                b.literal(0))), // fields
                        List.of("satisfied") // alias
                    );
                return b.build();
            });

            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();

        } else {
            // 1. '分组集合' 与 'literal'
            // 若分组集合中不包含literal的组


            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            List<String> gks = set.getGroupKeys();

            // 1) 所有分组（来自上下文全集，而不是来自 set 本身）
            RelNode allGroups =
                builder.push(contextObjects)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();
        
            // 2) 命中 literal 的行
            RelNode hitRows =
                builder.push(set.getRelNode())
                        .filter(builder.equals(
                            builder.field(set.getRelNode().getRowType().getFieldCount() - 1),
                            literal.getValue()))
                        .build();
        
            // 3) 命中 literal 的分组
            RelNode hitGroups =
                builder.push(hitRows)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();
        
            // 4) 不命中的分组：All − Hit
            res = builder.push(allGroups)
                        .push(hitGroups)
                        .minus( false, 2) 
                        .build();


        }

        return new BagPredicate(res);
    }




    /**
     * oclBag '->isEmpty()'
     * - 根据set为单个集合或分组集合分情况讨论
     *
     * @param ctx the parse tree
     * @return SetPredicate instance
     */
    @Override
    public OCLElement visitIsEmpty(STOCLParser.IsEmptyContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());


        RelNode res;

        // 若set为单个集合
        // 对set进行计数，记为cnt
        // 若cnt = 0，则为true，返回当前环境中的对象集合，否则返回empty
        if (set.isSingleSet()) {
            
            // 判断set的是否为空
            RexNode condition = builder.scalarQuery(b -> {
                    b.push(set.getRelNode())
                        .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                        .project(
                                List.of(builder.equals(builder.field("cnt"), builder.literal(0))), // fields
                                List.of("satisfied") // alias
                        );
                    return b.build();
            });

            // 获取当前环境中的对象集合A，若结果为true，返回A，否则返回empty
            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();


        } else {
            // 若set为分组集合
            // 对set进行分组计数
            // 若cnt = 0，保留该分组

            // 1. 所有上下文中的分组
            RelNode contextObjects =
                varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();

            RelNode allGroups =
                builder.push(contextObjects)
                        .project(
                            set.getGroupKeys().stream()
                                .map(builder::field)
                                .toList(),
                            set.getGroupKeys()
                        )
                        .build();

            // 2. 非空分组（在 set 中出现过的分组）
            RelNode nonEmptyGroups =
                builder.push(set.getRelNode())
                        .project(
                            set.getGroupKeys().stream()
                                .map(builder::field)
                                .toList(),
                            set.getGroupKeys()
                        )
                        .build();


            // 3. 空分组 = All − NonEmpty
            res = builder.push(allGroups)
                        .push(nonEmptyGroups)
                        .minus(false, 2)
                        .build();

                }


        return new BagPredicate(res);

    }



    /**
     * oclBag '->notEmpty()'
     * - 根据set为单个集合或分组集合分情况讨论
     *
     * @param ctx the parse tree
     * @return SetPredicate instance
     */
    @Override
    public OCLElement visitNotEmpty(STOCLParser.NotEmptyContext ctx) {
        OCLBag set = (OCLBag) visit(ctx.oclBag());


        RelNode res;

        // 若set为单个集合
        // 对set进行计数，记为cnt
        // 若cnt > 0，则为true，返回当前环境中的对象集合，否则返回empty
        if (set.isSingleSet()) {
            
            // 判断set是否为空
            RexNode condition = builder.scalarQuery(b -> {
                    b.push(set.getRelNode())
                        .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                        .project(
                                List.of(builder.greaterThan(builder.field("cnt"), builder.literal(0))), // fields
                                List.of("satisfied") // alias
                        );
                    return b.build();
            });

            // 获取当前环境中的对象集合A，若结果为true，返回A，否则返回empty
            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();


        } else {
            // 若set为分组集合
            // 若 set 为分组集合
            // notEmpty ⇔ 分组在 set 中出现过
            res = builder.push(set.getRelNode())
                    .project(
                        set.getGroupKeys().stream()
                            .map(builder::field)
                            .toList(),
                        set.getGroupKeys()
                    )
                    .build();

        }


        return new BagPredicate(res);
    }





    /**
     * oclBag '->forAll(' varList '|' oclBool ')'
     * - 在varMap中添加varList，每个var对应oclBag
     * - 计算oclBool，oclBool具有和set相同的列
     * - 根据set为单个集合和分组集合分情况讨论
     * - 释放varMap中varList的映射
     *
     * @param ctx the parse tree
     * @return SetPredicate instance
     */
    @Override
    public OCLElement visitForAll(STOCLParser.ForAllContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        VarList varList = (VarList) visit(ctx.varList());

        // add new scope for varEnv
        this.varEnv.pushScope();
        // add var to new scope
        for (String varName : varList.getVarNames()) {
            this.varEnv.put(varName, set);
        }

        // 得到满足条件的对象
        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());


        // del scope
        varEnv.popScope();


        // 获取当前环境中的groupKeys
        List<String> contextGroupkeys = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getGroupKeys();

        // oclBool具有和set相同的列
        if (!new HashSet<>(set.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }

        // set和oclBool都包含当前环境对象集合的groupKeys

        if (!set.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" set does not contain all group keys.");
        }
        if (!oclBool.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" oclBool does not contain all group keys.");
        }


        RelNode res;

        // 若set为单个集合
        // 此时的语义为：单个集合中的所有元素都须满足条件，即oclBool包含set中的所有元素
        // 若oclBool=set为true，则返回当前环境变量集合，否则返回empty

        if(set.isSingleSet()){


            // 计算bool的基数
            RelNode boolCount = builder.push(oclBool.getRelNode())
                    .aggregate(builder.groupKey(), builder.count(false, "b_count"))
                    .build();
            // 计算set的基数
            RelNode setCount = builder.push(set.getRelNode())
                    .aggregate(builder.groupKey(), builder.count(false, "s_count"))
                    .build();

            // 若bool的基数 与 set的基数相等，则结果为true，否则为false
            RelNode countJoin = builder.push(boolCount).push(setCount)
                    .join(JoinRelType.INNER,
                            builder.equals(builder.field(2, 0, "b_count"),
                                            builder.field(2, 1, "s_count")))
                    .build();




            RexNode condition = builder.scalarQuery(b -> {
                    b.push(countJoin)
                        .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                        .project(
                                List.of(builder.equals(builder.field("cnt"), builder.literal(1))), // fields
                                List.of("satisfied") // alias
                        );
                    return b.build();
            });

            // 获取当前环境中的对象集合A，若结果为true，返回A，否则返回empty
            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();



        } else {
        // 若set为分组集合

            List<String> groupKeys = set.getGroupKeys();

            // 对bool进行分组计数，记为b_count
            RelNode boolCount = builder.push(oclBool.getRelNode())
                    .aggregate(builder.groupKey(groupKeys.stream().map(f->builder.field(f)).toList()),
                                                            builder.count(false, "b_count"))
                    .build();

            // 对oclBag进行分组计数，记为s_count
            RelNode setCount = builder.push(set.getRelNode())
                    .aggregate(builder.groupKey(groupKeys.stream().map(f->builder.field(f)).toList()), builder.count(false, "s_count"))
                    .build();


            builder.push(boolCount).push(setCount);

            // join condition : each groupkey equal, and b_count equals to s_count
            List<RexNode> joinFields = groupKeys.stream()
                                            .map(f-> builder.equals(
                                                    builder.field(2, 0, f),
                                                    builder.field(2, 1, f)
                                            ))
                                            .collect(Collectors.toList());

            joinFields.add(builder.equals(
                                    builder.field(2, 0, "b_count"),
                                    builder.field(2, 1, "s_count")
                                            ));
            RexNode joinCondition = builder.and(joinFields);

            // 若s_count = b_count，保留该分组，保留满足forAll的分组
            res = builder.join(JoinRelType.INNER, joinCondition).build();


        }




        return new BagPredicate(res);

    }




    /**
     * oclBag '->exists(' varList '|' oclBool ')'
     * - 在varMap中添加varList，每个var对应oclBag
     * - 计算oclBool，oclBool具有和set相同的列
     * - 根据set为单个集合和分组集合分情况讨论
     * - 释放varMap中varList的映射
     * 
     *
     * @param ctx the parse tree
     * @return SetPredicate instance
     */
    @Override
    public OCLElement visitExists(STOCLParser.ExistsContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        VarList varList = (VarList) visit(ctx.varList());

        // add Scope
        this.varEnv.pushScope();
        for (String varName : varList.getVarNames()) {
            this.varEnv.put(varName, set);
        }

        // 得到满足条件的对象
        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());

        // del Scope
        this.varEnv.popScope();


        // 获取当前环境中的groupKeys
        List<String> contextGroupkeys = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getGroupKeys();

        // oclBool具有和set相同的列
        if (!new HashSet<>(set.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }

        // set和oclBool都包含当前环境对象集合的groupKeys
        if (!set.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" set does not contain all group keys.");
        }
        if (!oclBool.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" oclBool does not contain all group keys.");
        }


        RelNode res;

        // 若set为单个集合
        // 此时的语义为：单个集合中存在元素满足条件，即oclBool不为空
        // 若oclBool不为空为true，则返回当前环境变量集合，否则返回empty
        if(set.isSingleSet()){

            // 判断bool的是否为空
            RexNode condition = builder.scalarQuery(b -> {
                    b.push(oclBool.getRelNode())
                        .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                        .project(
                                List.of(builder.greaterThan(builder.field("cnt"), builder.literal(0))), // fields
                                List.of("satisfied") // alias
                        );
                    return b.build();
            });

            // 获取当前环境中的对象集合A，若结果为true，返回A，否则返回empty
            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();


        } else {
        // 若set为分组集合

            // 对bool进行分组计数，记为cnt
            // 若cnt > 0，保留该分组
            res = builder.push(oclBool.getRelNode())
                    .aggregate(builder.groupKey(set.getGroupKeys().stream().map(f->builder.field(f)).toList()), builder.count(false, "cnt"))
                    .filter(builder.greaterThan(builder.field("cnt"), builder.literal(0)))
                    .build();

        }



        return new BagPredicate(res);
    }





    /**
     * oclBag '->one(' varList '|' oclBool ')'
     * - 在varMap中添加varList，每个var对应oclBag
     * - 计算oclBool，oclBool具有和set相同的列
     * - 根据set为单个集合和分组集合分情况讨论(参考->exists())
     * - 释放varMap中varList的映射
     *
     * @param ctx the parse tree
     * @return SetPredicate instance
     */
    @Override
    public OCLElement visitOne(STOCLParser.OneContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        Var var = (Var) visit(ctx.var());

        // add scope
        this.varEnv.pushScope();
        this.varEnv.put(var.getVarName(), set);

        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());

        // del scope
        this.varEnv.popScope();


        // 获取当前环境中的groupKeys
        List<String> contextGroupkeys = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getGroupKeys();

        // oclBool具有和set相同的列
        if (!new HashSet<>(set.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }

        // set和oclBool都包含当前环境对象集合的groupKeys
        if (!set.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" set does not contain all group keys.");
        }
        if (!oclBool.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" oclBool does not contain all group keys.");
        }

        RelNode res;

        // 若set为单个集合
        // 此时的语义为：单个集合中仅存在一个元素满足条件，即oclBool元素个数为1
        // 若oclBool元素个数为1为true，则返回当前环境变量集合，否则返回empty
        if(set.isSingleSet()){

            // 判断bool是否元素个数为1
            RexNode condition = builder.scalarQuery(b -> {
                    b.push(oclBool.getRelNode())
                        .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                        .project(
                                List.of(builder.equals(builder.field("cnt"), builder.literal(1))), // fields
                                List.of("satisfied") // alias
                        );
                    return b.build();
            });

            // 获取当前环境中的对象集合A，若结果为true，返回A，否则返回empty
            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();


        } else {
        // 若set为分组集合

            // 对bool进行分组计数，记为cnt
            // 若cnt = 0，保留该分组
            res = builder.push(oclBool.getRelNode())
                    .aggregate(builder.groupKey(set.getGroupKeys().stream().map(f->builder.field(f)).toList()), builder.count(false, "cnt"))
                    .filter(builder.equals(builder.field("cnt"), builder.literal(1)))
                    .build();

        }




        return new BagPredicate(res);
    }




    /**
     * oclBag '->isUnique(' attr ')'
     * - 根据set为单个集合和分组集合分情况讨论(参考->exists())
     *
     *
     * @param ctx the parse tree
     * @return SetPredicate instance
     */
    @Override
    public OCLElement visitIsUnique(STOCLParser.IsUniqueContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());

        String attr = ctx.attr().getText();


        // 对象集合才能取attr

        if (!set.isObjectSet()){
            throw new RuntimeException(" only object set can call isUnique(attr) ");
        }

        String className = set.getClassName();
        String classId = UMLClassDiagram.getObjectIDColumn(className);

        RelNode classTable = builder.scan(className)
                                    .project(builder.field(classId), builder.field(attr)) 
                                    // .distinct()
                                    .build();

        RelNode joinTable = builder.push(set.getRelNode())
                                    .push(classTable)
                                    .join(JoinRelType.INNER, builder.equals(
                                                    builder.field(2, 0, classId),
                                                    builder.field(2, 1, classId)))
                                    .build();

        RelNode res;

        // 若set是单个集合
        // 对attr列进行去重，并计数，记为u_count
        // 对set进行计数，记为s_count
        // 若u_count = s_count，则返回true，否则返回false
        if(set.isSingleSet()) {

            // 对attr列进行去重，并计数，记为u_count
            RelNode distinctAttrCount = builder.push(joinTable)
                    .aggregate(builder.groupKey(), builder.count(true, "u_count", builder.field(attr)))
                    .build();

            // 对set进行计数，记为s_count
            RelNode setCount = builder.push(set.getRelNode())
                    .aggregate(builder.groupKey(), builder.count(false, "s_count"))
                    .build();


            RelNode countJoin = builder.push(distinctAttrCount).push(setCount)
                    .join(JoinRelType.INNER,
                            builder.equals(builder.field(2, 0, "u_count"),
                                            builder.field(2, 1, "s_count")))
                    .build();

            // 若attr去重的基数 与 set的基数相等，则结果为true，否则为false
            RexNode condition = builder.scalarQuery(b -> {
                    b.push(countJoin)
                        .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                        .project(
                                List.of(builder.equals(builder.field("cnt"), builder.literal(1))), // fields
                                List.of("satisfied") // alias
                        );
                    return b.build();
            });

            // 获取当前环境中的对象集合A，若结果为true，返回A，否则返回empty
            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();


        } else {
        // 若set是分组集合
        // 分组统计元素个数和元素attr的不重复个数
        // 若相等，则表示该分组满足unique，保留该分组
            
            // 获取当前环境中的groupKeys
            List<String> groupKeys = set.getGroupKeys();

            // 对attr进行分组计数，distinct为true, 记为b_count
            RelNode boolCount = builder.push(joinTable)
                    .aggregate(builder.groupKey(groupKeys.stream().map(f->builder.field(f)).toList()),
                                builder.count(true, "b_count", builder.field(attr)))
                    .build();

            // 对oclBag进行分组计数，记为s_count
            RelNode setCount = builder.push(set.getRelNode())
                    .aggregate(builder.groupKey(groupKeys.stream().map(f->builder.field(f)).toList()), builder.count(false, "s_count"))
                    .build();


            builder.push(boolCount).push(setCount);

            // join condition : each groupkey equal, and b_count equals to s_count
            List<RexNode> joinFields = set.getGroupKeys().stream()
                                            .map(f-> builder.equals(
                                                    builder.field(2, 0, f),
                                                    builder.field(2, 1, f)
                                            ))
                                            .collect(Collectors.toList());

            joinFields.add(builder.equals(
                                    builder.field(2, 0, "b_count"),
                                    builder.field(2, 1, "s_count")
                                            ));
            RexNode joinCondition = builder.and(joinFields);

            // 若s_count = b_count，表示分组中的元素都唯一，保留该分组
            res = builder.join(JoinRelType.INNER, joinCondition).build();


        }



        return new BagPredicate(res);

    }





    private OCLBag setOperation(OCLBag set1, OCLBag set2, String op){

        String set1FieldName = set1.getRelNode().getRowType().getFieldNames().get(set1.getRelNode().getRowType().getFieldCount()-1);
        String set2FieldName = set2.getRelNode().getRowType().getFieldNames().get(set2.getRelNode().getRowType().getFieldCount()-1);

        // set1和set2的setElementType相同
        if (set1.getSetElementType() != set2.getSetElementType()) {
            throw new RuntimeException(" Set element types are not the same for set operation.");
        }


        // set1和set2分别可能是'单个集合'或'分组集合'（集合的集合，每个对象对应一个集合）
        // - 单个集合: 整体作为一个集合，可能有多列，最后一列通过对象集合 Class.allInstances()，或集合字面值 Set{1， 3，5}，或oclBag '.' roleOrAttr 得到
        // - 分组集合: 通过分组形成每个组为一个集合，只能通过multi-role得到，set(set(), set(), set())
        // 因此set1和set2之间的操作分为四种情况讨论
        // 1. '单个集合' 与 '单个集合'
        // 2. '单个集合' 与 '分组集合'
        // 3. '分组集合' 与 '单个集合'
        // 4. '分组集合' 与 '分组集合'

        if(set1.isSingleSet()){
            if(set2.isSingleSet()){
            // 1. '单个集合' 与 '单个集合'
            // 此时语义为：两个集合的并集（去重）
            // 分别投影两个集合的最后一列后进行并操作

                RelNode left = builder.push(set1.getRelNode())
                                .project(builder.field(set1FieldName))
                                // .distinct()
                                .build();


                RelNode right = builder.push(set2.getRelNode())
                                .project(builder.field(set2FieldName))
                                // .distinct()
                                .build();


                RelNode res;
                switch(op){
                    case "union":
                        res = builder.push(left)
                                .push(right)
                                .union(false, 2)
                                .build();
                        break;
                    case "intersection":
                        res = builder.push(left)
                                .push(right)
                                .intersect(false, 2)
                                .build();
                        break;
                    case "difference":
                        res = builder.push(left)
                                .push(right)
                                .minus(false, 2)
                                .build();
                        break;
                    case "symmetricDifference":
                        // (A∪B)∖(A∩B)
                        RelNode union = builder.push(left)
                                .push(right)
                                .union(false, 2)
                                .build();

                        RelNode inter = builder.push(left)
                                .push(right)
                                .intersect(false, 2)
                                .build();

                        res = builder.push(union)
                                .push(inter)
                                .minus(false, 2)
                                .build();
                        break;
                    default:
                        throw new RuntimeException("Unsupported set operation.");
                }


                // set1和set2的setElementType相同，className相同，SetType为单一集合
                return new OCLBag(res, set1.getClassName(), set1.getSetElementType(), OCLBag.SetType.SINGLE_SET, null);

            } else {
            // 2. '单个集合' 与 '分组集合'
            // 此时语义为：对每个分组集合中的集合，与单个集合执行并操作
            // 投影出分组集合的groupKeys列，投影出单个集合的最后一列，进行笛卡尔积
            // 投影出分组集合的groupKeys列，和最后一列
            // 将笛卡尔积与分组集合去并集（去重）

                // 投影出单个集合的最后一列
                int fieldIndex = set1.getRelNode().getRowType().getFieldCount()-1;
                RelNode singleProject = builder.push(set1.getRelNode())
                        .project(builder.field(fieldIndex))
                        // .distinct()
                        .build();

                // 投影出分组集合的groupKeys列
                builder.push(set2.getRelNode());


                RelNode groupProject = builder.project(set2.getGroupKeys().stream().map(f->builder.field(f)).toList())
                                                // .distinct()
                                                .build();

                // 进行笛卡尔积
                RelNode product = builder.push(groupProject)
                                        .push(singleProject)
                                        .join(JoinRelType.INNER, builder.literal(true))
                                        .build();


                builder.push(set2.getRelNode());
                String fieldName = set2.getRelNode().getRowType().getFieldNames().get(set2.getRelNode().getRowType().getFieldCount()-1);

                List<RexInputRef> proj = new ArrayList<>(set2.getGroupKeys().stream().map(f->builder.field(f)).toList());
                proj.add(builder.field(fieldName));

                // 投影出分组集合的groupKeys列，和最后一列
                RelNode groupProjectWithLastCol = builder.project(proj)
                                                        // .distinct()
                                                        .build();

                                        
                // 将笛卡尔积与分组集合去并集（去重）               

                RelNode res;
                switch(op){
                    case "union":
                        res = builder.push(groupProjectWithLastCol)
                                .push(product)
                                .union(false, 2)
                                .build();
                        break;
                    case "intersection":
                        res = builder.push(groupProjectWithLastCol)
                                .push(product)
                                .intersect(false, 2)
                                .build();
                        break;
                    case "difference":
                        res = builder.push(groupProjectWithLastCol)
                                .push(product)
                                .minus(false, 2)
                                .build();
                        break;
                    case "symmetricDifference":
                        // (A∪B)∖(A∩B)
                        RelNode union = builder.push(groupProjectWithLastCol)
                                .push(product)
                                .union(false, 2)
                                .build();

                        RelNode inter = builder.push(groupProjectWithLastCol)
                                .push(product)
                                .intersect(false, 2)
                                .build();

                        res = builder.push(union)
                                .push(inter)
                                .minus(false, 2)
                                .build();
                        break;
                    default:
                        res = null;

                }

                // set1和set2的setElementType相同，className相同，SetType为分组集合
                return new OCLBag(res, set2.getClassName(), set2.getSetElementType(), OCLBag.SetType.POWER_SET, set2.getGroupKeys());
            }

            

        } else {
            if(set2.isSingleSet()) {
            // 3. '分组集合' 与 '单个集合'
            // 此时语义为：对每个分组集合中的集合，与单个集合执行并操作
            // 投影出分组集合的groupKeys列，投影出单个集合的最后一列，进行笛卡尔积
            // 投影出分组集合的groupKeys列，和最后一列
            // 将笛卡尔积与分组集合去并集（去重）

                // 投影出单个集合的最后一列
                int fieldIndex = set2.getRelNode().getRowType().getFieldCount()-1;
                RelNode singleProject = builder.push(set2.getRelNode())
                        .project(builder.field(fieldIndex))
                        // .distinct()
                        .build();

                // 投影出分组集合的groupKeys列
                builder.push(set1.getRelNode());


                RelNode groupProject = builder.project(set1.getGroupKeys().stream().map(f->builder.field(f)).toList())
                                                                                                        // .distinct()
                                                                                                        .build();

                // 进行笛卡尔积
                RelNode product = builder.push(groupProject)
                                        .push(singleProject)
                                        .join(JoinRelType.INNER, builder.literal(true))
                                        .build();


                builder.push(set1.getRelNode());
                String fieldName = set1.getRelNode().getRowType().getFieldNames().get(set1.getRelNode().getRowType().getFieldCount()-1);
                List<RexInputRef> proj = set1.getGroupKeys().stream().map(f->builder.field(f)).collect(Collectors.toList());
                proj.add(builder.field(fieldName));

                // 投影出分组集合的groupKeys列，和最后一列
                RelNode groupProjectWithLastCol = builder.project(proj)
                                                            // .distinct()
                                                            .build();

                // 将笛卡尔积与分组集合取并集（去重）               
                RelNode res;
                switch(op){
                    case "union":
                        res = builder.push(groupProjectWithLastCol)
                                .push(product)
                                .union(false, 2)
                                .build();
                        break;
                    case "intersection":
                        res = builder.push(groupProjectWithLastCol)
                                .push(product)
                                .intersect(false, 2)
                                .build();
                        break;
                    case "difference":
                        res = builder.push(groupProjectWithLastCol)
                                .push(product)
                                .minus(false, 2)
                                .build();
                        break;
                    case "symmetricDifference":
                        // (A∪B)∖(A∩B)
                        RelNode union = builder.push(groupProjectWithLastCol)
                                .push(product)
                                .union(false, 2)
                                .build();

                        RelNode inter = builder.push(groupProjectWithLastCol)
                                .push(product)
                                .intersect(false, 2)
                                .build();

                        res = builder.push(union)
                                .push(inter)
                                .minus(false, 2)
                                .build();
                        break;
                    default:
                        res = null;

                }

                // set1和set2的setElementType相同，className相同，SetType为分组集合
                return new OCLBag(res, set1.getClassName(), set1.getSetElementType(), OCLBag.SetType.POWER_SET, set1.getGroupKeys());

            } else {
            // 4. '分组集合' 与 '分组集合'
            // 此时语义为：对每个分组，执行集合并操作
            // 投影出分组集合的groupKeys列，和最后一列
            // 进行并操作（去重）

                // set1和set2groupKeys相同
                if (!new HashSet<>(set1.getGroupKeys()).equals(new HashSet<>(set2.getGroupKeys()))) {
                    throw new RuntimeException(" set1 does not have the same group keys as set2.");
                }


                // 投影出分组集合的groupKeys列
                builder.push(set1.getRelNode());
                List<RexInputRef> proj = set1.getGroupKeys().stream().map(f->builder.field(f)).collect(Collectors.toList());
                String fieldName = set1.getRelNode().getRowType().getFieldNames().get(set1.getRelNode().getRowType().getFieldCount()-1);
                proj.add(builder.field(fieldName));

                RelNode left = builder.project(proj)
                                    // .distinct()
                                    .build();

                builder.push(set2.getRelNode());
                proj = set1.getGroupKeys().stream().map(f->builder.field(f)).collect(Collectors.toList());
                proj.add(builder.field(fieldName));
                RelNode right = builder.project(proj)
                                        // .distinct()
                                        .build();

                // 取并集（去重）               
                RelNode res;
                switch(op){
                    case "union":
                        res = builder.push(left)
                                .push(right)
                                .union(false, 2)
                                .build();
                        break;
                    case "intersection":
                        res = builder.push(left)
                                .push(right)
                                .intersect(false, 2)
                                .build();
                        break;
                    case "difference":
                        res = builder.push(left)
                                .push(right)
                                .minus(false, 2)
                                .build();
                        break;
                    case "symmetricDifference":
                        // (A∪B)∖(A∩B)
                        RelNode union = builder.push(left)
                                .push(right)
                                .union(false, 2)
                                .build();

                        RelNode inter = builder.push(left)
                                .push(right)
                                .intersect(false, 2)
                                .build();

                        res = builder.push(union)
                                .push(inter)
                                .minus(false, 2)
                                .build();
                        break;
                    default:
                        res = null;

                }

                // set1和set2的setElementType相同，className相同，SetType为分组集合
                return new OCLBag(res, set1.getClassName(), set1.getSetElementType(), OCLBag.SetType.POWER_SET, set1.getGroupKeys());

            }


        }
    }







    /**
     * oclBag '->union(' oclBag ')'
     *  - 根据两个set的类型分情况讨论
     *
     * @param ctx the parse tree
     * @return oclBag instance
     */
    @Override
    public OCLElement visitUnion(STOCLParser.UnionContext ctx) {
        OCLBag set1 = (OCLBag) visit(ctx.oclBag(0));
        OCLBag set2 = (OCLBag) visit(ctx.oclBag(1));

        return setOperation(set1, set2, "union");

    }


    /**
     * oclBag '->intersection(' oclBag ')'
     * - 对两个oclBag进行交集操作（参考union）
     *
     * @param ctx the parse tree
     * @return oclBag instance
     */
    @Override
    public OCLElement visitIntersection(STOCLParser.IntersectionContext ctx) {

        OCLBag set1 = (OCLBag) visit(ctx.oclBag(0));
        OCLBag set2 = (OCLBag) visit(ctx.oclBag(1));

        return setOperation(set1, set2, "intersection");

    }



    /**
     * oclBag '->difference(' oclBag ')'
     * - 对两个oclBag进行差集操作
     *
     * @param ctx the parse tree
     * @return oclBag instance
     */
    @Override
    public OCLElement visitDifference(STOCLParser.DifferenceContext ctx) {

        OCLBag set1 = (OCLBag) visit(ctx.oclBag(0));
        OCLBag set2 = (OCLBag) visit(ctx.oclBag(1));

        return setOperation(set1, set2, "difference");

    }


    /**
     * oclBag '->symmetricDifference(' oclBag ')'
     * - 对两个oclBag进行对称差集操作 (A∪B)∖(A∩B)
     *
     * @param ctx the parse tree
     * @return oclBag instance
     */
    @Override
    public OCLElement visitSymmetricDifference(STOCLParser.SymmetricDifferenceContext ctx) {

        OCLBag set1 = (OCLBag) visit(ctx.oclBag(0));
        OCLBag set2 = (OCLBag) visit(ctx.oclBag(1));

        return setOperation(set1, set2, "symmetricDifference");

    }



    /**
     * oclBag '->select(' varList '|' oclBool ')'
     * - 在varMap中添加varList，每个var对应oclBag
     * - 计算oclBool
     * - 释放varMap中varList的映射
     * - 返回bool进行
     *
     * @param ctx the parse tree
     * @return oclBag instance
     */
    @Override
    public OCLElement visitSelect(STOCLParser.SelectContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        Var var = (Var) visit(ctx.var());

        // add scope
        this.varEnv.pushScope();
        this.varEnv.put(var.getVarName(), set);

        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());

        // del scope
        this.varEnv.popScope();


        // 获取当前环境中的groupKeys
        List<String> contextGroupkeys = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getGroupKeys();

        // oclBool具有和set相同的列
        if (!new HashSet<>(set.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }

        // set和oclBool都包含当前环境对象集合的groupKeys

        if (!set.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" set does not contain all group keys.");
        }
        if (!oclBool.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" oclBool does not contain all group keys.");
        }




        // 若set为单个集合或分组集合
        RelNode res = oclBool.getRelNode();
        if(set.isSingleSet()){
            return new OCLBag(res, set.getClassName(), set.getSetElementType(), OCLBag.SetType.SINGLE_SET, null);
        } else {
            return new OCLBag(res, set.getClassName(), set.getSetElementType(), OCLBag.SetType.POWER_SET, set.getGroupKeys());
        }



    }


    /**
     * oclBag '->reject(' varList '|' oclBool ')'
     * - 在varMap中添加varList，每个var对应oclBag
     * - 计算oclBool
     * - 释放varMap中varList的映射
     * - 返回差集
     *
     * @param ctx the parse tree
     * @return oclBag instance
     */
    @Override
    public OCLElement visitReject(STOCLParser.RejectContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        Var var = (Var) visit(ctx.var());

        // add scope
        this.varEnv.pushScope();
        this.varEnv.put(var.getVarName(), set);

        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());

        // del scope
        this.varEnv.popScope();


        // 获取当前环境中的groupKeys
        List<String> contextGroupkeys = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getGroupKeys();

        // oclBool具有和set相同的列
        if (!new HashSet<>(set.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }
        // set和oclBool都包含当前环境对象集合的groupKeys
        if (!set.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" set does not contain all group keys.");
        }
        if (!oclBool.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" oclBool does not contain all group keys.");
        }

        // 若set为单个集合或分组集合
        RelNode res = builder.push(set.getRelNode())
                    .push(oclBool.getRelNode())
                    .minus(false, 2)
                    .build();

        if(set.isSingleSet()){
            return new OCLBag(res, set.getClassName(), set.getSetElementType(), OCLBag.SetType.SINGLE_SET, null);
        } else {
            return new OCLBag(res, set.getClassName(), set.getSetElementType(), OCLBag.SetType.POWER_SET, set.getGroupKeys());
        }

    }




    /**
     * oclBag '.' roleOrAttr
     * - oclBag需为对象集合
     * - 判断roleOrAttr是属性还是关联
     * - 若为属性，与oclBag所属类进行连接，投影oclBag所有列 + roleOrAttr列，结果为valueSet
     * - 若为关联，与role所属类进行连接，投影oclBag所有列 + 关联类的标识列，结果为objectSet
     *
     * @param ctx the parse tree
     * @return oclBag instance
     */
    @Override
    public OCLElement visitBagRoleOrAttr(STOCLParser.BagRoleOrAttrContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        String roleOrAttr = ctx.roleOrAttr().getText();

        // must be object set
        if (set.isValueSet()) {
            throw new RuntimeException(" Only object set can access role or attribute.");
        }

        // attr or role must exist
        if (!cd.hasAttr(set.getClassName(), roleOrAttr) && !cd.hasRole(set.getClassName(), roleOrAttr)) {
            throw new RuntimeException(" Class " + set.getClassName() +
                    " has no attribute or role named " + roleOrAttr);
        }


        // roleOrAttr 为 attr (单值）
        if(cd.hasAttr(set.getClassName(), roleOrAttr) ) {

            // set为单个集合或分组集合
            String className = set.getClassName();

            int n = set.getRelNode().getRowType().getFieldCount();

            // 与set对应class的表join
            builder.push(set.getRelNode())
                    .scan(className)
                    .join(JoinRelType.INNER,
                            builder.equals(
                                    builder.field(2, 0, n-1),
                                    builder.field(2, 1, UMLClassDiagram.getObjectIDColumn(className))
                            ));

            // 投影出set所有列和attr列
            List<RexInputRef> proj = set.getRelNode()
                    .getRowType()
                    .getFieldNames()
                    .stream()
                    .map(f -> builder.field(f))
                    .collect(Collectors.toList());

            proj.add(builder.field(roleOrAttr));

            RelNode res = builder.project(proj) // fields
                    // .distinct()
                    .build();

            if(set.isSingleSet()){
                return new OCLBag(res, className, cd.getAttrType(className, roleOrAttr), OCLBag.SetType.SINGLE_SET, null);
            } else {
                return new OCLBag(res, className, cd.getAttrType(className, roleOrAttr), OCLBag.SetType.POWER_SET, set.getGroupKeys());
            }


        // 若为role
        } else if (cd.hasRole(set.getClassName(), roleOrAttr)) {

            String className = set.getClassName();
            String classCol = UMLClassDiagram.getObjectIDColumn(className);
            // 找到关联表表名
            String assoClassName = cd.getAssoClassWithRole(set.getClassName(), roleOrAttr);
            String assoEndClassName = cd.getAssoEndClassWithRole(set.getClassName(), roleOrAttr);

            String roleCol = UMLClassDiagram.getObjectIDColumn(assoEndClassName);

            int n = set.getRelNode().getRowType().getFieldCount();

            builder.push(set.getRelNode())
                    .scan(assoClassName)
                    .join(JoinRelType.INNER,
                            builder.equals(
                                    builder.field(2, 0, n-1),
                                    builder.field(2, 1, classCol)
                            ));

            List<RexInputRef> proj = set.getRelNode()
                    .getRowType()
                    .getFieldNames()
                    .stream()
                    .map(f -> builder.field(f))
                    .collect(Collectors.toList());

            proj.add(builder.field(roleCol));

            RelNode res = builder.project(proj) // fields
                    // .distinct()
                    .build();

            // result is object set
            if(set.isSingleSet()){
                return new OCLBag(res, assoEndClassName, PrimitiveType.OBJECT, OCLBag.SetType.SINGLE_SET, null);
            } else {
                return new OCLBag(res,  assoEndClassName, PrimitiveType.OBJECT, OCLBag.SetType.POWER_SET, new ArrayList<>(set.getGroupKeys()));
                // if(cd.isSingleRole(set.getClassName(), roleOrAttr)) {
                //     // role 的multiplicity 为1
                //     return new OCLBag(res,  assoEndClassName, PrimitiveType.OBJECT, OCLBag.SetType.POWER_SET, new ArrayList<>(set.getGroupKeys()));
                // } else {
                //     // role 的multiplicity 为n
                //     List<String> groupKeys = new ArrayList<>(set.getGroupKeys());
                //     if(!groupKeys.contains(classCol))
                //     {
                //         groupKeys.add(classCol);
                //     }

                //     return new OCLBag(res,  assoEndClassName, PrimitiveType.OBJECT, OCLBag.SetType.POWER_SET, groupKeys); // result is object set
                // }
            }


        } else {
            throw new RuntimeException("Class " + set.getClassName() +
                    " has no attribute or role named " + roleOrAttr);
        }



    }




    /**
     * ID ('.'|'::') 'allInstances()'
     * - ID对应的类名需存在于cd中
     * - 扫描该类的表，结果为objectSet
     *
     * @param ctx the parse tree
     * @return oclBag instance
     */
    @Override
    public OCLElement visitAllInstances(STOCLParser.AllInstancesContext ctx) {

        String className = ctx.ID().getText();

        // uml中存在class
        if (!cd.hasClass(className)) {
            throw new RuntimeException(" Class " + className + " does not exist in the UML class diagram.");
        }

        RelNode rel = builder.scan(className)
                        .project(builder.field(UMLClassDiagram.getObjectIDColumn(className)))
                        // .distinct()
                        .build();

        // 单一集合，无groupKeys
        return new OCLBag(rel, className, PrimitiveType.OBJECT, OCLBag.SetType.SINGLE_SET, new ArrayList<>()); // object set
    }




    /**
     * oclObj '.' role
     * - ID对应的类名需存在于cd中
     * - 扫描该类的表，结果为objectSet
     *
     * @param ctx the parse tree
     * @return oclBag instance
     */
    @Override
    public OCLElement visitMultipleRole(STOCLParser.MultipleRoleContext ctx) {
        OCLObj obj = (OCLObj) visit(ctx.oclObj());
        String role = ctx.role().getText();

        // role 须为multi-role，即multiplicity为n
        if (cd.isSingleRole(obj.getClassName(), role)) {
            throw new RuntimeException(" Role " + role + " of class " + obj.getClassName() + " is not a multi-role.");
        }

        String className = obj.getClassName();
        String classCol = UMLClassDiagram.getObjectIDColumn(className);

        String assoClassName = cd.getAssoClassWithRole(obj.getClassName(), role);
        String assoEndClassName = cd.getAssoEndClassWithRole(obj.getClassName(), role);

        String roleCol = UMLClassDiagram.getObjectIDColumn(assoEndClassName);

        int n = obj.getRelNode().getRowType().getFieldCount();

        builder.push(obj.getRelNode())
                .scan(assoClassName)
                .join(JoinRelType.INNER,
                        builder.equals(
                                builder.field(2, 0, n-1),
                                builder.field(2, 1, classCol)
                        ));

        List<RexInputRef> proj = obj.getRelNode()
                .getRowType()
                .getFieldNames()
                .stream()
                .map(f -> builder.field(f))
                .collect(Collectors.toList());

        proj.add(builder.field(roleCol));

        RelNode res = builder.project(proj)
                // .distinct() // fields
                .build();

        // // groupKeys更新为obj对应的RelNode的所有列

        // // 我的语法中的groupkeys的更新时机只在obj.nrole规则出，此规则使得对每个obj变化为对应的bag。
        // // 此后若进行聚合操作要按照每个obj为分组进行操作。
        // // 此后若再进行attr，role等1：1变换，分组仍不变；若进行nrole的1：n变换，分组仍不变，对结果进行flatten

        // List<String> groupKeys = new ArrayList<>(obj.getRelNode()
        //                                             .getRowType()
        //                                             .getFieldNames());

        List<String> groupKeys;


        // 若obj相关集合为单个集合
        if (obj.getRefSet().isSingleSet()) {
            groupKeys = new ArrayList<>();
            groupKeys.add(classCol);

        } else {
        // 若obj相关集合为分组集合
            groupKeys = new ArrayList<>(obj.getRefSet().getGroupKeys());
            if(!groupKeys.contains(classCol))
            {
                groupKeys.add(classCol);
            }
        }

        return new OCLBag(res, assoEndClassName, PrimitiveType.OBJECT, OCLBag.SetType.POWER_SET, groupKeys);
    


    }


    @Override
    public OCLElement visitBagElementsLiteral(STOCLParser.BagElementsLiteralContext ctx) {
        List<Literal> literals = ctx.literal().stream()
                .map(litCtx -> (Literal) visit(litCtx))
                .toList();

        SqlTypeName literalType = literals.getFirst().getType() ;
        String literalTypeString = literals.getFirst().getTypeString();

        if ("DECIMAL".equals(literalTypeString)) {
            literalTypeString = "INTEGER";
        } else if ("DOUBLE".equals(literalTypeString)) {
            literalTypeString = "REAL";
        } else if ("CHAR".equals(literalTypeString)) {
            literalTypeString = "STRING";
        } else if ("BOOLEAN".equals(literalTypeString)) {
            literalTypeString = "BOOLEAN";
        }



        for (Literal lit : literals) {
            if (lit.getType() != literalType) {
                throw new RuntimeException(" All literals in the bag literal must have the same type.");
            }
        }

        RelOptCluster cluster = builder.getCluster();
        RelDataTypeFactory typeFactory = cluster.getTypeFactory();
        RelDataType rowType = typeFactory.createStructType(
                                                        List.of(typeFactory.createSqlType(literalType)),
                                                        List.of("elem")
                                                        );

        ImmutableList<ImmutableList<RexLiteral>> tuples = literals.stream()
                .map(Literal::getValue)
                .map(ImmutableList::of)
                .collect(ImmutableList.toImmutableList());

        RelNode rel = LogicalValues.create(
                cluster,
                rowType,
                tuples
        );

        return new OCLBag(rel, null, PrimitiveType.valueOf(literalTypeString), OCLBag.SetType.SINGLE_SET, null);

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
            StrValue leftVal = (StrValue) left;
            StrValue rightVal = (StrValue) right;


            if (!leftVal.getRefSet().isValueSet() ){
                throw new RuntimeException(" Left side of comparison must be a value set.");
            }

            if (!rightVal.getRefSet().isValueSet() ){
                throw new RuntimeException(" Right side of comparison must be a value set.");
            }


            int leftIndex = leftVal.getRelNode().getRowType().getFieldCount()-1;
            int rightIndex = rightVal.getRelNode().getRowType().getFieldCount()-1;


            // both are relation
            RelNode res = builder.push(leftVal.getRelNode())
                    .push(rightVal.getRelNode())
                    .semiJoin(builder.call(compOpMap.get(compOp),
                            builder.field(2, 0, leftIndex),
                            builder.field(2, 1, rightIndex)))
                    .build();

            return new EqualExpr(res);

        } else {
            throw new RuntimeException(" Both sides of equality expression must be literals.");
        }


    }




    /**
     * arithExpr compOp=('<' | '<=' | '=' | '>=' | '>' | '<>') arithExpr
     * - 若两边均为relation，则进行半连接操作，得到满足条件的对象
     * - 若左边为relation，右边为literal，则对relation进行过滤，选出满足条件的对象
     * - 若左边为literal，右边为relation，则对relation进行过滤，选出满足条件的对象
     *
     * @param ctx the parse tree
     * @return EqualExpr instance
     */
    @Override
    public OCLElement visitEqualityExprArithmetic(STOCLParser.EqualityExprArithmeticContext ctx) {

        Map<String, SqlBinaryOperator> compOpMap = Map.of(
                "<", SqlStdOperatorTable.LESS_THAN,
                "<=", SqlStdOperatorTable.LESS_THAN_OR_EQUAL,
                "=", SqlStdOperatorTable.EQUALS,
                ">=", SqlStdOperatorTable.GREATER_THAN_OR_EQUAL,
                ">", SqlStdOperatorTable.GREATER_THAN,
                "<>", SqlStdOperatorTable.NOT_EQUALS
        );



        ArithExpr left = (ArithExpr) visit(ctx.arithExpr(0));
        ArithExpr right = (ArithExpr) visit(ctx.arithExpr(1));

        String compOp = ctx.compOp.getText();




        if (left.isRelation() && right.isRelation()) {

            // 相关集合为值集合，值的类型为int或real
            if (!left.getRefSet().isValueSet() ){
                throw new RuntimeException(" Left side of comparison must be a value set.");
            }
            if (left.getRefSet().getSetElementType() != PrimitiveType.INTEGER && left.getRefSet().getSetElementType() != PrimitiveType.REAL){
                throw new RuntimeException(" Left side of comparison must be a value set of type Integer or Real.");
            }


            if (!right.getRefSet().isValueSet() ){
                throw new RuntimeException(" Right side of comparison must be a value set.");
            }
            if (right.getRefSet().getSetElementType() != PrimitiveType.INTEGER && right.getRefSet().getSetElementType() != PrimitiveType.REAL){
                throw new RuntimeException(" Right side of comparison must be a value set of type Integer or Real.");
            }

            //context Person inv: self.ownCar->forAll(c | c.type = self.allowedType)
            // both are relation

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

            RelNode res = builder.join(JoinRelType.INNER, condition)
                                .build();


            return new EqualExpr(res);

        } else if (left.isRelation() && !right.isRelation()) {


            // 相关集合为值集合，值的类型为int或real
            if (!left.getRefSet().isValueSet() ){
                throw new RuntimeException(" Left side of comparison must be a value set.");
            }
            if (left.getRefSet().getSetElementType() != PrimitiveType.INTEGER && left.getRefSet().getSetElementType() != PrimitiveType.REAL){
                throw new RuntimeException(" Left side of comparison must be a value set of type Integer or Real.");
            }

            

            if (right.isLiteral()){
                if ( right.getLiteralType() != PrimitiveType.INTEGER && right.getLiteralType() != PrimitiveType.REAL){
                    throw new RuntimeException(" Right side of comparison must be a value of type Integer or Real.");
                }

            }

            int leftIndex = left.getRelNode().getRowType().getFieldCount()-1;

            // left is relation, right is literal
            RelNode rel = builder.push(left.getRelNode())
                    .filter(builder.call(compOpMap.get(compOp),
                            builder.field(leftIndex),
                            right.getRexNode()))
                    .build();

            return new EqualExpr(rel);

        } else if (!left.isRelation() && right.isRelation()) {

            // 相关集合为值集合，值的类型为int或real
            if (left.isLiteral()){
                if ( left.getLiteralType() != PrimitiveType.INTEGER && left.getLiteralType() != PrimitiveType.REAL){
                    throw new RuntimeException(" Left  must be a value of type Integer or Real.");
                }
            }

            if (!right.getRefSet().isValueSet() ){
                throw new RuntimeException(" Right side of comparison must be a value set.");
            }
            if (right.getRefSet().getSetElementType() != PrimitiveType.INTEGER && right.getRefSet().getSetElementType() != PrimitiveType.REAL){
                throw new RuntimeException(" Right side of comparison must be a value set of type Integer or Real.");
            }

            int rightIndex = right.getRelNode().getRowType().getFieldCount()-1;

            // right is relation, left is literal
            RelNode rel = builder.push(right.getRelNode())
                    .filter(builder.call(compOpMap.get(compOp),
                            left.getRexNode(),
                            builder.field(2, 0, rightIndex)))
                    .build();

            return new EqualExpr(rel);


        } else {


            if (left.isLiteral() && right.isLiteral()){
                // both are literal
                throw new RuntimeException("Both sides of equality cannot be literal: " +
                    left.getRexNode() + " " + compOp + " " + right.getRexNode());
            }

            // 若为subQuery和literal的比较
            // 获取当前环境中的变量的集合，若为subQuery和literal的比较为真，则返回当前环境中的变量的集合，否则返回空集
            // 获取当前环境中的对象集合A，若结果为true，返回A，否则返回empty
            RexNode condition = builder.call(compOpMap.get(compOp),left.getRexNode(), right.getRexNode());

            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            RelNode res = builder.push(contextObjects)
                    .filter(condition)
                    .build();

            return new EqualExpr(res);

        }



    }



    /**
     * strValue compOp=('=' | '<>') strValue
     * - 若两边均为relation，则进行半连接操作，得到满足条件的对象
     * - 若左边为relation，右边为literal，则对relation进行过滤，选出满足条件的对象
     * - 若左边为literal，右边为relation，则对relation进行过滤，选出满足条件的对象
     *
     * @param ctx the parse tree
     * @return EqualExpr instance
     */
    @Override
    public OCLElement visitEqualityExprString(STOCLParser.EqualityExprStringContext ctx) {

        Map<String, SqlBinaryOperator> compOpMap = Map.of(
                "=", SqlStdOperatorTable.EQUALS,
                "<>", SqlStdOperatorTable.NOT_EQUALS
        );



        StrValue left = (StrValue) visit(ctx.strValue(0));
        StrValue right = (StrValue) visit(ctx.strValue(1));

        String compOp = ctx.compOp.getText();



        if (left.isRelation() && right.isRelation()) {
            // 相关集合为值集合，值的类型为string
            if (!left.getRefSet().isValueSet() ){
                throw new RuntimeException(" Left side of comparison must be a value set.");
            }
            if (left.getRefSet().getSetElementType() != PrimitiveType.STRING){
                throw new RuntimeException(" Left side of comparison must be a value set of type String.");
            }

            if (!right.getRefSet().isValueSet() ){
                throw new RuntimeException(" Right side of comparison must be a value set.");
            }
            if (right.getRefSet().getSetElementType() != PrimitiveType.STRING){
                throw new RuntimeException(" Right side of comparison must be a value set of type String.");
            }

            int leftIndex = left.getRelNode().getRowType().getFieldCount()-1;
            int rightIndex = right.getRelNode().getRowType().getFieldCount()-1;


            // both are relation
            RelNode res = builder.push(left.getRelNode())
                    .push(right.getRelNode())
                    .semiJoin(builder.call(compOpMap.get(compOp),
                            builder.field(2, 0, leftIndex),
                            builder.field(2, 1, rightIndex)))
                    .build();

            return new EqualExpr(res);

        } else if (left.isRelation() && !right.isRelation()) {

            // 相关集合为值集合，值的类型为string
            if (!left.getRefSet().isValueSet() ){
                throw new RuntimeException(" Left side of comparison must be a value set.");
            }
            if (left.getRefSet().getSetElementType() != PrimitiveType.STRING){
                throw new RuntimeException(" Left side of comparison must be a value set of type String.");
            }

            if (right.getLiteralType() != PrimitiveType.STRING){
                throw new RuntimeException(" Right side of comparison must be a value of type String.");
            }


            int leftIndex = left.getRelNode().getRowType().getFieldCount()-1;

            // left is relation, right is literal
            RelNode rel = builder.push(left.getRelNode())
                    .filter(builder.call(compOpMap.get(compOp),
                            builder.field(leftIndex),
                            right.getLiteral()))
                    .build();

            return new EqualExpr(rel);

        } else if (!left.isRelation() && right.isRelation()) {

            // 相关集合为值集合，值的类型为string
            if (left.getLiteralType() != PrimitiveType.STRING){
                throw new RuntimeException(" Left side of comparison must be a value of type String.");
            }

            if (!right.getRefSet().isValueSet() ){
                throw new RuntimeException(" Right side of comparison must be a value set.");
            }
            if (right.getRefSet().getSetElementType() != PrimitiveType.STRING){
                throw new RuntimeException(" Right side of comparison must be a value set of type String.");
            }


            int rightIndex = right.getRelNode().getRowType().getFieldCount()-1;

            // right is relation, left is literal
            RelNode rel = builder.push(right.getRelNode())
                    .filter(builder.call(compOpMap.get(compOp),
                            left.getLiteral(),
                            builder.field( rightIndex)))
                    .build();

            return new EqualExpr(rel);


        } else {
            // get whole set
            OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());

            // 使用全集与oclBool进行差集操作
            RelNode res = builder.push(universalSet.getRelNode())
                    .filter(builder.call(compOpMap.get(compOp),
                            left.getLiteral(),
                            right.getLiteral()))
                    .build();

            return new EqualExpr(res);
//            throw new RuntimeException("Both sides of equality cannot be literal: " +
//                    left.getLiteral() + " " + compOp + " " + right.getLiteral());
        }


    }


   /**
    * oclObj compOp=('=' | '<>') oclObj
    * - 则进行半连接操作，得到满足条件的对象
    *
    * @param ctx the parse tree
    * @return EqualExpr instance
    */
   @Override
   public OCLElement visitEqualityExprObject(STOCLParser.EqualityExprObjectContext ctx) {

       Map<String, SqlBinaryOperator> compOpMap = Map.of(
               "=", SqlStdOperatorTable.EQUALS,
               "<>", SqlStdOperatorTable.NOT_EQUALS
       );

       OCLObj left = (OCLObj) visit(ctx.oclObj(0));
       OCLObj right = (OCLObj) visit(ctx.oclObj(1));

       String compOp = ctx.compOp.getText();

        // 相关集合为对象集合
        if (!left.getRefSet().isObjectSet()){
            throw new RuntimeException(" Left side of comparison must be an object set.");
        }
        if (!right.getRefSet().isObjectSet()){
            throw new RuntimeException(" Right side of comparison must be an object set.");
        }


       int leftIndex = left.getRelNode().getRowType().getFieldCount()-1;
       int rightIndex = right.getRelNode().getRowType().getFieldCount()-1;
       // both are relation
       RelNode res = builder.push(left.getRelNode())
               .push(right.getRelNode())
               .semiJoin(builder.call(compOpMap.get(compOp),
                       builder.field(2, 0, leftIndex),
                       builder.field(2, 1, rightIndex)))
               .build();

       return new EqualExpr(res);

   }





    /**
     * '-' arithExpr
     * - 若arithExpr为relation，则对其进行投影，结果列为-literal
     * - 若arithExpr为literal，则直接取负
     * @param ctx the parse tree
     * @return ArithExpr instance
     */
    @Override
    public OCLElement visitArithUnaryMinus(STOCLParser.ArithUnaryMinusContext ctx) {
        ArithExpr arithExpr = (ArithExpr) visit(ctx.arithExpr());


        if (arithExpr.isRelation()) {

            // arithExpr 为值集合
            if (!arithExpr.getRefSet().isValueSet()){
                throw new RuntimeException(" Unary minus operation can only be applied to value sets.");
            }
            if (arithExpr.getRefSet().getSetElementType() != PrimitiveType.INTEGER &&
                arithExpr.getRefSet().getSetElementType() != PrimitiveType.REAL){
                throw new RuntimeException(" Unary minus operation can only be applied to value sets of type Integer or Real.");
            }

            int count = arithExpr.getRelNode().getRowType().getFieldCount();

            builder.push(arithExpr.getRelNode());

            RexNode lastCol = builder.field(count - 1);
            RexNode negLastCol = builder.call(SqlStdOperatorTable.UNARY_MINUS, lastCol);

            List<RexNode> projects = new ArrayList<>();
            for (int i = 0; i < count - 1; i++) {
                projects.add(builder.field(i));  // 原样保留前面的列
            }
            projects.add(negLastCol);             // 最后一列取负

           // 列别名
            List<String> alias = new ArrayList<>(arithExpr.getRelNode().getRowType().getFieldNames());
            String lastColStr = "-" + alias.get(count);
            alias.remove(count);
            alias.add(lastColStr);


            RelNode res = builder.project(projects, alias) // fields
                                // .distinct()
                                .build();


            return new ArithExpr(new OCLBag(res, arithExpr.getRefSet().getClassName(), arithExpr.getRefSet().getSetElementType(), arithExpr.getRefSet().getSetType(), arithExpr.getRefSet().getGroupKeys()));

        } else {
            // arithExpr 为literal, DECIMAL, DOUBLE
            Object value = arithExpr.getLiteral().getValue2();

            RexLiteral negatedLiteral = null;
            if (value instanceof BigDecimal){
                negatedLiteral = builder.literal(((BigDecimal) value).negate());

            } else if(value instanceof Double){
                negatedLiteral = builder.literal(-((Double) value));
            } else {
                throw new IllegalArgumentException("取负操作仅适用于数值类型: " + value);
            }

            return new ArithExpr(new Literal(negatedLiteral));
        }

    }


    /**
     * arithExpr op=('*' | '/' ) arithExpr
     * - 若两边均为relation，则进行连接操作，连接条件为groupKeys列相等，结果列为最后一列进行相应的算术运算
     * - 若左边为relation，右边为literal，则对relation进行投影，结果列为最后一列与literal进行相应的算术运算
     * - 若左边为literal，右边为relation，则对relation进行投影，结果列为literal与最后一列进行相应的算术运算
     * - 若两边均为literal，则直接进行相应的算术运算
     *
     *
     * @param ctx the parse tree
     * @return ArithExpr instance
     */
    @Override
    public OCLElement visitArithMultDiv(STOCLParser.ArithMultDivContext ctx) {


        ArithExpr left = (ArithExpr) visit(ctx.arithExpr(0));
        ArithExpr right = (ArithExpr) visit(ctx.arithExpr(1));
        String op = ctx.op.getText();

        return arithExprBinaryOp(left, right, op);

    }





    /**
     * arithExpr op=('+' | '-' ) arithExpr
     * - 若两边均为relation，则进行连接操作，连接条件为groupKeys列相等，结果列为最后一列进行相应的算术运算
     * - 若左边为relation，右边为literal，则对relation进行投影，结果列为最后一列与literal进行相应的算术运算
     * - 若左边为literal，右边为relation，则对relation进行投影，结果列为literal与最后一列进行相应的算术运算
     * - 若两边均为literal，则直接进行相应的算术运算
     *
     * @param ctx the parse tree
     * @return ArithExpr instance
     */
    @Override
    public OCLElement visitArithAddSub(STOCLParser.ArithAddSubContext ctx) {
        ArithExpr left = (ArithExpr) visit(ctx.arithExpr(0));
        ArithExpr right = (ArithExpr) visit(ctx.arithExpr(1));
        String op = ctx.op.getText();

        return arithExprBinaryOp(left, right, op);
    }



    /**
     * '(' arithExpr ')'
     * - 直接返回arithExpr
     *
     * @param ctx the parse tree
     * @return ArithExpr instance
     */
    @Override
    public OCLElement visitArithParen(STOCLParser.ArithParenContext ctx) {
        return visit(ctx.arithExpr());
    }




    /**
     * INT_LITERAL
     *
     * @param ctx the parse tree
     * @return ArithValue instance
     */
    @Override
    public OCLElement visitArithValueIntLiteral(STOCLParser.ArithValueIntLiteralContext ctx) {

        int val = Integer.parseInt(ctx.INT_LITERAL().getText());
        RexLiteral literal = builder.literal(val);

        return new ArithExpr(new Literal(literal));
    }


    /**
     * REAL_LITERAL
     *
     * @param ctx the parse tree
     * @return ArithValue instance
     */
    @Override
    public OCLElement visitArithValueRealLiteral(STOCLParser.ArithValueRealLiteralContext ctx) {
        double val = Double.parseDouble(ctx.REAL_LITERAL().getText());
        RexLiteral literal = builder.literal(val);

        return new ArithExpr(new Literal(literal));
    }





    public OCLBag getObjAttr(OCLObj obj, String attr) {



        String tableName = obj.getClassName();

        int n = obj.getRelNode().getRowType().getFieldCount();

        builder.push(obj.getRelNode())
                .scan(tableName)
                .join(JoinRelType.INNER,
                        builder.equals(
                                builder.field(2, 0, n-1),
                                builder.field(2, 1, UMLClassDiagram.getObjectIDColumn(tableName))  // 假设标识列名为 id
                        ));



        List<RexInputRef> proj = obj.getRelNode()
                .getRowType()
                .getFieldNames()
                .stream()
                .map(f -> builder.field(f))
                .collect(Collectors.toList());

        proj.add(builder.field(attr));


        RelNode res = builder.project(proj)
                // .distinct()  此处不需要进行distinct
                .build();


        return new OCLBag(res,
                tableName,
                cd.getAttrType(obj.getClassName(), attr),
                obj.getRefSet().getSetType(),
                obj.getRefSet().getGroupKeys());

    }




    @Override
    public OCLElement visitArithValueObjAttrValue(STOCLParser.ArithValueObjAttrValueContext ctx) {
        OCLElement arithExpr = (OCLElement) visit(ctx.objAttrValue());

        if(!(arithExpr instanceof ArithExpr)){
            throw new RuntimeException(" Object attribute value must be an arithmetic expression.");
        }

        return (ArithExpr) arithExpr;
    }






    /**
     * oclBag aggFunc=('->min()'|'->max()'|'->size()'|'->sum()'|'->avg()')
     * - 若aggFunc为size，oclBag可为objectSet或valueSet
     * - 若aggFunc为min/max/sum/avg，oclBag需为valueSet
     * - 若set为单一集合，聚合结果为RexSubQuery(继承RexNode)
     * - 若set为分组集合，聚合结果为oclBag
     *
     * @param ctx the parse tree
     * @return ArithValue instance
     */
    @Override
    public OCLElement visitArithValueAggFunc(STOCLParser.ArithValueAggFuncContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());

        String aggOp = ctx.aggFunc.getText();



        if (List.of("->min()", "->max()", "->sum()", "->avg()").contains(aggOp)) {
            if (!set.isValueSet()){
                throw new RuntimeException(" Aggregate function " + aggOp + " can only be applied to value sets.");
            }
        }


        int lastColIndex = set.getRelNode().getRowType().getFieldCount() - 1;

        String lastCol = set.getRelNode().getRowType().getFieldNames().get(lastColIndex);

        if (set.isSingleSet()) {



            RexSubQuery scalar = builder.scalarQuery(sub -> {
                sub.push(set.getRelNode());

                switch (aggOp) {
                    case "->min()":
                        sub.aggregate(sub.groupKey(), sub.min("min(" + lastCol + ")", sub.field(lastColIndex)));
                        break;
                    case "->max()":
                        sub.aggregate(sub.groupKey(), sub.max("max(" + lastCol + ")", sub.field(lastColIndex)));
                        break;
                    case "->sum()":
                        sub.aggregate(sub.groupKey(), sub.sum(false, "sum(" + lastCol + ")", sub.field(lastColIndex)));
                        break;
                    case "->avg()":
                        sub.aggregate(sub.groupKey(), sub.avg(false, "avg(" + lastCol + ")", sub.field(lastColIndex)));
                        break;
                    case "->size()":
                        sub.aggregate(sub.groupKey(), sub.count(false, "cnt(" + lastCol + ")"));          // COUNT(*)
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported agg: " + aggOp);
                }
                return sub.build();
            });


            return new ArithExpr(scalar);


        } else {


            builder.push(set.getRelNode());

            List<RexInputRef> groupKeys = set.getGroupKeys().stream().map(f->builder.field(f)).toList();

            switch (aggOp) {
                case "->min()":
                    builder.aggregate(builder.groupKey(groupKeys), builder.min("min(" + lastCol + ")", builder.field(lastColIndex)));
                    break;
                case "->max()":
                    builder.aggregate(builder.groupKey(groupKeys), builder.max("max(" + lastCol + ")", builder.field(lastColIndex)));
                    break;
                case "->sum()":
                    builder.aggregate(builder.groupKey(groupKeys), builder.sum(false, "sum(" + lastCol + ")", builder.field(lastColIndex)));
                    break;
                case "->avg()":
                    builder.aggregate(builder.groupKey(groupKeys), builder.avg(false, "avg(" + lastCol + ")", builder.field(lastColIndex)));
                    break;
                case "->size()":
                    builder.aggregate(builder.groupKey(groupKeys), builder.count(false, "cnt(" + lastCol + ")"));          // COUNT(*)
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported agg: " + aggOp);
            }

            RelNode res = builder.build();

            return new ArithExpr(new OCLBag(res, set.getClassName(), PrimitiveType.REAL, set.getSetType(), set.getGroupKeys()));

        }



    }








    /**
     * strValue '.size()'
     * - 若strValue为relation，最后一列取length
     * - 若strValue为Literal，直接取length
     *
     * @param ctx the parse tree
     * @return ArithValue instance
     */
    @Override
    public OCLElement visitArithValueStrSize(STOCLParser.ArithValueStrSizeContext ctx){
        StrValue strValue = (StrValue) visit(ctx.strValue());

        if (strValue.isRelation()){

            RelNode res;
            int n = strValue.getRefSet().getRelNode().getRowType().getFieldCount();

            builder.push(strValue.getRefSet().getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode rex = builder.call(
                    SqlStdOperatorTable.CHAR_LENGTH,
                    builder.field(n-1)
            );
            proj.add(rex);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue.getRefSet().getRelNode().getRowType().getFieldNames().get(i));
            alias.add("length");

            res = builder.project(proj, alias)
                                    // .distinct()
                                    .build();

            return new ArithExpr(new OCLBag(res,
                    strValue.getRefSet().getClassName(),
                    PrimitiveType.INTEGER,
                    strValue.getRefSet().getSetType(),
                    strValue.getRefSet().getGroupKeys()));

        } else {

            String literal = strValue.getLiteralString();
            return new ArithExpr(new Literal(builder.literal(literal.length())));

        }



    }


    @Override
    public OCLElement visitArithValueAbs(STOCLParser.ArithValueAbsContext ctx) {
        ArithExpr arithExpr = (ArithExpr) visit(ctx.arithExpr());
        Either<RelNode, RexNode> result = arithOpWithoutParams(arithExpr, SqlStdOperatorTable.ABS, "abs");

        if (result.isLeft()){
            return new ArithExpr(new OCLBag(result.getLeft(),
                    arithExpr.getRefSet().getClassName(),
                    arithExpr.getRefSet().getSetElementType(),
                    arithExpr.getRefSet().getSetType(),
                    arithExpr.getRefSet().getGroupKeys()));
        } else {
            return new ArithExpr(result.getRight());
        }

    }



    public Either<RelNode, RexNode> arithOpWithoutParams(ArithExpr arithExpr, SqlOperator arithOp, String opStr){

        if (arithExpr.isRelation()){

            RelNode res;
            int n = arithExpr.getRefSet().getRelNode().getRowType().getFieldCount();

            builder.push(arithExpr.getRefSet().getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode rex = builder.call(
                    arithOp,
                    builder.field(n-1)
            );
            proj.add(rex);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(arithExpr.getRefSet().getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
                            // .distinct()
                            .build();

            return Either.left(res);

        } else {

            RexNode raw = arithExpr.getRexNode();
            RexNode rex = builder.call(
                    arithOp,
                    raw
            );
            return Either.right(rex);

        }


    }




    @Override
    public OCLElement visitArithValueFloor(STOCLParser.ArithValueFloorContext ctx) {
        ArithExpr arithExpr = (ArithExpr) visit(ctx.arithExpr());
        Either<RelNode, RexNode> result = arithOpWithoutParams(arithExpr, SqlStdOperatorTable.FLOOR, "floor");

        if (result.isLeft()){
            return new ArithExpr(new OCLBag(result.getLeft(),
                    arithExpr.getRefSet().getClassName(),
                    PrimitiveType.INTEGER,
                    arithExpr.getRefSet().getSetType(),
                    arithExpr.getRefSet().getGroupKeys()));
        } else {
            return new ArithExpr(result.getRight());
        }
    }

    @Override
    public OCLElement visitArithValueRound(STOCLParser.ArithValueRoundContext ctx) {
        ArithExpr arithExpr = (ArithExpr) visit(ctx.arithExpr());
        Either<RelNode, RexNode> result = arithOpWithoutParams(arithExpr, SqlStdOperatorTable.ROUND, "round");

        if (result.isLeft()){
            return new ArithExpr(new OCLBag(result.getLeft(),
                    arithExpr.getRefSet().getClassName(),
                    PrimitiveType.INTEGER,
                    arithExpr.getRefSet().getSetType(),
                    arithExpr.getRefSet().getGroupKeys()));
        } else {
            return new ArithExpr(result.getRight());
        }
    }




    @Override
    public OCLElement visitArithValueMax(STOCLParser.ArithValueMaxContext ctx) {
        ArithExpr arithExpr1 = (ArithExpr) visit(ctx.arithExpr(0));
        ArithExpr arithExpr2 = (ArithExpr) visit(ctx.arithExpr(1));
        Either<RelNode, RexNode> result = arithOpWithoutOneParams(arithExpr1, arithExpr2, SqlStdOperatorTable.MAX, "max");

        if (result.isLeft()){
            return new ArithExpr(new OCLBag(result.getLeft(),
                    arithExpr1.getRefSet().getClassName(),
                    PrimitiveType.REAL,
                    arithExpr1.getRefSet().getSetType(),
                    arithExpr1.getRefSet().getGroupKeys()));
        } else {
            return new ArithExpr(result.getRight());
        }




    }



    public Either<RelNode, RexNode> arithOpWithoutOneParams(ArithExpr arithExpr1, ArithExpr arithExpr2, SqlOperator arithOp, String opStr){

        if (arithExpr1.isRelation() && arithExpr2.isRelation()) {

            RelNode res;
            int ln = arithExpr1.getRelNode().getRowType().getFieldCount();
            List<String> lfields = new ArrayList<>(arithExpr1.getRelNode().getRowType().getFieldNames());
            lfields.remove(ln-1);

            int rn = arithExpr2.getRelNode().getRowType().getFieldCount();
            List<String> rfields = new ArrayList<>(arithExpr2.getRelNode().getRowType().getFieldNames());
            rfields.remove(rn-1);

            List<String> common = new ArrayList<>(lfields);
            common.retainAll(rfields);

            if (common.isEmpty()){
                throw new RuntimeException("Common fields is empty.");
            }


            builder.push(arithExpr1.getRelNode())
                    .push(arithExpr2.getRelNode());


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
                    arithOp,
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


        } else if (arithExpr1.isRelation() && !arithExpr2.isRelation()) {

            RelNode res;
            int n = arithExpr1.getRelNode().getRowType().getFieldCount();


            builder.push(arithExpr1.getRelNode());
            // 投影：前 n-1 列来自左表 + (A.n + rightLiteral)列
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));


            RexNode a2 = arithExpr2.getRexNode();

            // scalar
            RexNode disLast = builder.call(
                    arithOp,
                    builder.field(n - 1),
                    a2
            );
            proj.add(disLast);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(arithExpr1.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
                            // .distinct()
                            .build();

            return Either.left(res);


        } else if (!arithExpr1.isRelation() && arithExpr2.isRelation()) {

            RelNode res;
            int n = arithExpr2.getRelNode().getRowType().getFieldCount();


            builder.push(arithExpr2.getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode a1 = arithExpr1.getRexNode();

            RexNode disLast = builder.call(
                    arithOp,
                    a1,
                    builder.field(n - 1)
            );
            proj.add(disLast);


            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(arithExpr2.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
                            // .distinct()
                            .build();

            return Either.left(res);

        } else {
            RexNode rex = builder.call(
                    arithOp,
                    arithExpr1.getRexNode(),
                    arithExpr2.getRexNode()
            );
            return Either.right(rex);
        }


    }





    @Override
    public OCLElement visitArithValueMin(STOCLParser.ArithValueMinContext ctx) {
        ArithExpr arithExpr1 = (ArithExpr) visit(ctx.arithExpr(0));
        ArithExpr arithExpr2 = (ArithExpr) visit(ctx.arithExpr(1));
        Either<RelNode, RexNode> result = arithOpWithoutOneParams(arithExpr1, arithExpr2, SqlStdOperatorTable.MIN, "min");

        if (result.isLeft()){
            return new ArithExpr(new OCLBag(result.getLeft(),
                    arithExpr1.getRefSet().getClassName(),
                    PrimitiveType.REAL,
                    arithExpr1.getRefSet().getSetType(),
                    arithExpr1.getRefSet().getGroupKeys()));
        } else {
            return new ArithExpr(result.getRight());
        }
    }





    @Override
    public OCLElement visitArithValueMod(STOCLParser.ArithValueModContext ctx) {
        ArithExpr arithExpr1 = (ArithExpr) visit(ctx.arithExpr(0));
        ArithExpr arithExpr2 = (ArithExpr) visit(ctx.arithExpr(1));
        Either<RelNode, RexNode> result = arithOpWithoutOneParams(arithExpr1, arithExpr2, SqlStdOperatorTable.MOD, "mod");

        if (result.isLeft()){
            return new ArithExpr(new OCLBag(result.getLeft(),
                    arithExpr1.getRefSet().getClassName(),
                    PrimitiveType.INTEGER,
                    arithExpr1.getRefSet().getSetType(),
                    arithExpr1.getRefSet().getGroupKeys()));
        } else {
            return new ArithExpr(result.getRight());
        }
    }



    @Override
    public OCLElement visitArithValueDiv(STOCLParser.ArithValueDivContext ctx) {
        ArithExpr arithExpr1 = (ArithExpr) visit(ctx.arithExpr(0));
        ArithExpr arithExpr2 = (ArithExpr) visit(ctx.arithExpr(1));
        Either<RelNode, RexNode> result = arithOpWithoutOneParams(arithExpr1, arithExpr2, SqlStdOperatorTable.DIVIDE_INTEGER, "div");

        if (result.isLeft()){
            return new ArithExpr(new OCLBag(result.getLeft(),
                    arithExpr1.getRefSet().getClassName(),
                    PrimitiveType.INTEGER,
                    arithExpr1.getRefSet().getSetType(),
                    arithExpr1.getRefSet().getGroupKeys()));
        } else {
            return new ArithExpr(result.getRight());
        }
    }



    /**
     * oclObj '.' fRole
     * - oclObj需为对象集合，不是literal
     * - 在cd中查找fRole所属的关联类
     * - 与关联类进行连接，投影oclObj所有列 + 关联类的标识列，结果为objectSet
     *
     *
     * @param ctx the parse tree
     * @return OCLObj instance
     */
    @Override
    public OCLElement visitOclObjectRole(STOCLParser.OclObjectRoleContext ctx) {


        OCLObj obj = (OCLObj) visit(ctx.oclObj());
        String role = ctx.role().getText();

        // the role must be a single role (1-1)
        if(!cd.isSingleRole(obj.getClassName(), role)) {
            throw new RuntimeException("Role " + role + " of class " + obj.getClassName() + " is not a single role.");
        }

        String classCol = UMLClassDiagram.getObjectIDColumn(obj.getClassName());

        String assoClassName = cd.getAssoClassWithRole(obj.getClassName(), role);
        String assoEndClassName = cd.getAssoEndClassWithRole(obj.getClassName(), role);

        String roleCol = UMLClassDiagram.getObjectIDColumn(assoEndClassName);

        int n = obj.getRelNode().getRowType().getFieldCount();


        builder.push(obj.getRelNode())
                .scan(assoClassName)
                .join(JoinRelType.INNER,
                        builder.equals(
                                builder.field(2, 0, n-1),
                                builder.field(2, 1, classCol)
                        ));

        List<RexInputRef> proj = obj.getRelNode()
                .getRowType()
                .getFieldNames()
                .stream()
                .map(f -> builder.field(f))
                .collect(Collectors.toList());

        proj.add(builder.field(roleCol));

        RelNode res = builder.project(proj) // fields
                .build();

        // result is object set
        if(obj.getRefSet().isSingleSet()){
            return new OCLObj(new OCLBag(res, assoEndClassName, PrimitiveType.OBJECT, OCLBag.SetType.SINGLE_SET, null)); 
        } else {
            return new OCLObj(new OCLBag(res, assoEndClassName, PrimitiveType.OBJECT, OCLBag.SetType.POWER_SET, obj.getRefSet().getGroupKeys())); 
        }

    }




    /**
     * var
     * - 在varMap中查找var对应的oclBag
     * - 返回该oclBag对应的oclObj
     *
     * @param ctx the parse tree
     * @return OCLObj instance
     */
    @Override
    public OCLElement visitOclObjectVar(STOCLParser.OclObjectVarContext ctx) {
        Var var = (Var) visit(ctx.var());
        OCLBag set = this.varEnv.resolve(var.getVarName());
        return new OCLObj(set);

    }



    /**
     * 'self'
     * - 在varMap中查找self对应的oclBag
     * - 返回该oclBag对应的oclObj
     *
     * @param ctx the parse tree
     * @return OCLObj instance
     */
    @Override
    public OCLElement visitOclObjectSelf(STOCLParser.OclObjectSelfContext ctx) {
        OCLBag set = this.varEnv.resolve("self");
        return new OCLObj(set);
    }




    /**
     * oclObj '.' attr
     * - attr只能为单值属性
     * - 将oclObj与其所属类进行连接，投影oclObj所有列 + attr列，结果为valueSet
     *
     * @param ctx the parse tree
     * @return ArithValue or StrValue instance
     */
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
        } else {
            throw new RuntimeException("Attribute " + attr + " of class " + obj.getClassName() +
                    " is not of type Integer, Real or String.");
        }


    }


    /**
     * var
     * - 在varMap中查找var对应的oclBag
     * - oclBag需为valueSet
     * - 返回该oclBag对应的ArithValue
     *
     * @param ctx the parse tree
     * @return ArithValue instance
     */

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
        } else {
            throw new RuntimeException(" Variable " + var.getVarName() + " must be of type Integer, Real or String.");
        }

    }



    /**
     * STRING_LITERAL
     *
     * @param ctx the parse tree
     * @return StrValue instance
     */
    @Override
    public OCLElement visitStringValueLiteral(STOCLParser.StringValueLiteralContext ctx) {
        String raw = ctx.STRING_LITERAL().getText();
        String val = raw.substring(1, raw.length() - 1);
        return new StrValue(new Literal(builder.literal(val)));
    }


    @Override
    public OCLElement visitStringValueObjAttrValue(STOCLParser.StringValueObjAttrValueContext ctx) {
        OCLElement strValue = (OCLElement) visit(ctx.objAttrValue());

        if(!(strValue instanceof StrValue)){
            throw new RuntimeException(" Object attribute value must be a string value.");
        }
        return (StrValue) strValue;
    }



    @Override
    public OCLElement visitStringValueConcat(STOCLParser.StringValueConcatContext ctx) {
        StrValue strValue1 = (StrValue) visit(ctx.strValue(0));
        StrValue strValue2 = (StrValue) visit(ctx.strValue(1));

        SqlOperator strOp = SqlStdOperatorTable.CONCAT;
        String opStr = "concat";

        if (strValue1.isRelation() && strValue2.isRelation()) {

            RelNode res;
            int ln = strValue1.getRelNode().getRowType().getFieldCount();
            List<String> lfields = new ArrayList<>(strValue1.getRelNode().getRowType().getFieldNames());
            lfields.remove(ln-1);

            int rn = strValue2.getRelNode().getRowType().getFieldCount();
            List<String> rfields = new ArrayList<>(strValue2.getRelNode().getRowType().getFieldNames());
            rfields.remove(rn-1);

            List<String> common = new ArrayList<>(lfields);
            common.retainAll(rfields);

            if (common.isEmpty()){
                throw new RuntimeException("Common fields is empty.");
            }


            builder.push(strValue1.getRelNode())
                    .push(strValue2.getRelNode());


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
                    strOp,
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


            return new StrValue(new OCLBag(res,
                    strValue1.getRefSet().getClassName(),
                    strValue1.getRefSet().getSetElementType(),
                    strValue1.getRefSet().getSetType(),
                    strValue1.getRefSet().getGroupKeys()));


        } else if (strValue1.isRelation() && !strValue2.isRelation()) {

            RelNode res;
            int n = strValue1.getRelNode().getRowType().getFieldCount();


            builder.push(strValue1.getRelNode());
            // 投影：前 n-1 列来自左表 + (A.n + rightLiteral)列
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));


            RexNode s2 = strValue2.getLiteral();

            // scalar
            RexNode disLast = builder.call(
                    strOp,
                    builder.field(n - 1),
                    s2
            );
            proj.add(disLast);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue1.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
                            // .distinct()
                            .build();

            return new StrValue(new OCLBag(res,
                    strValue1.getRefSet().getClassName(),
                    strValue1.getRefSet().getSetElementType(),
                    strValue1.getRefSet().getSetType(),
                    strValue1.getRefSet().getGroupKeys()));

        } else if (!strValue1.isRelation() && strValue2.isRelation()) {

            RelNode res;
            int n = strValue2.getRelNode().getRowType().getFieldCount();


            builder.push(strValue2.getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode s1 = strValue1.getLiteral();

            RexNode disLast = builder.call(
                    strOp,
                    s1,
                    builder.field(n - 1)
            );
            proj.add(disLast);


            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue2.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
                            // .distinct()
                            .build();

            return new StrValue(new OCLBag(res,
                    strValue2.getRefSet().getClassName(),
                    strValue2.getRefSet().getSetElementType(),
                    strValue2.getRefSet().getSetType(),
                    strValue2.getRefSet().getGroupKeys()));

        } else {
            String strLiteral1 = strValue1.getLiteralString();
            String strLiteral2 = strValue2.getLiteralString();
            return new StrValue(new Literal(builder.literal(strLiteral1+strLiteral2)));
        }




    }


    
    
    
    @Override
    public OCLElement visitStringValueSubstring(STOCLParser.StringValueSubstringContext ctx) {
        StrValue strValue = (StrValue) visit(ctx.strValue());
        int intValue1 = Integer.valueOf(ctx.INT_LITERAL(0).getText());
        int intValue2 = Integer.valueOf(ctx.INT_LITERAL(1).getText());

        SqlOperator strOp = SqlStdOperatorTable.SUBSTRING;
        String opStr = "substring";
        if (strValue.isRelation()){

            RelNode res;
            int n = strValue.getRefSet().getRelNode().getRowType().getFieldCount();

            builder.push(strValue.getRefSet().getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            // 注意：ocl中的substring的int1和int2指定起止index，calcite中分别指起始index和length
            RexNode rex = builder.call(
                    strOp,
                    builder.field(n-1),
                    builder.literal(intValue1),
                    builder.literal(intValue2-intValue1)
            );
            proj.add(rex);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue.getRefSet().getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
                        // .distinct()
                        .build();

            return new StrValue(new OCLBag(res,
                    strValue.getRefSet().getClassName(),
                    strValue.getRefSet().getSetElementType(),
                    strValue.getRefSet().getSetType(),
                    strValue.getRefSet().getGroupKeys()));

        } else {

            String raw = strValue.getLiteralString();
            return new StrValue(new Literal(builder.literal(raw.substring(intValue1, intValue2))));

        }


    }


    @Override
    public OCLElement visitStringValueToUpperCase(STOCLParser.StringValueToUpperCaseContext ctx) {
        StrValue strValue = (StrValue) visit(ctx.strValue());

        SqlOperator strOp = SqlStdOperatorTable.UPPER;
        String opStr = "upper";
        if (strValue.isRelation()){

            RelNode res;
            int n = strValue.getRefSet().getRelNode().getRowType().getFieldCount();

            builder.push(strValue.getRefSet().getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode rex = builder.call(
                    strOp,
                    builder.field(n-1)
            );
            proj.add(rex);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue.getRefSet().getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
                        // .distinct()
                        .build();

            return new StrValue(new OCLBag(res,
                    strValue.getRefSet().getClassName(),
                    strValue.getRefSet().getSetElementType(),
                    strValue.getRefSet().getSetType(),
                    strValue.getRefSet().getGroupKeys()));

        } else {

            String raw = strValue.getLiteralString();
            return new StrValue(new Literal(builder.literal(raw.toUpperCase())));

        }

    }




    @Override
    public OCLElement visitStringValueToLowerCase(STOCLParser.StringValueToLowerCaseContext ctx) {
        StrValue strValue = (StrValue) visit(ctx.strValue());

        SqlOperator strOp = SqlStdOperatorTable.LOWER;
        String opStr = "lower";
        if (strValue.isRelation()){

            RelNode res;
            int n = strValue.getRefSet().getRelNode().getRowType().getFieldCount();

            builder.push(strValue.getRefSet().getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode rex = builder.call(
                    strOp,
                    builder.field(n-1)
            );
            proj.add(rex);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue.getRefSet().getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
                        // .distinct()
                        .build();

            return new StrValue(new OCLBag(res,
                    strValue.getRefSet().getClassName(),
                    strValue.getRefSet().getSetElementType(),
                    strValue.getRefSet().getSetType(),
                    strValue.getRefSet().getGroupKeys()));

        } else {

            String raw = strValue.getLiteralString();
            return new StrValue(new Literal(builder.literal(raw.toLowerCase())));

        }
    }


    @Override
    public OCLElement visitStringValueAt(STOCLParser.StringValueAtContext ctx) {
        StrValue strValue = (StrValue) visit(ctx.strValue());
        int intValue = Integer.valueOf(ctx.INT_LITERAL().getText());

        SqlOperator strOp = SqlStdOperatorTable.SUBSTRING;
        String opStr = "at";
        if (strValue.isRelation()){

            RelNode res;
            int n = strValue.getRefSet().getRelNode().getRowType().getFieldCount();

            builder.push(strValue.getRefSet().getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode rex = builder.call(
                    strOp,
                    builder.field(n-1),
                    builder.literal(1)
            );
            proj.add(rex);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue.getRefSet().getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
                        // .distinct()
                        .build();

            return new StrValue(new OCLBag(res,
                    strValue.getRefSet().getClassName(),
                    strValue.getRefSet().getSetElementType(),
                    strValue.getRefSet().getSetType(),
                    strValue.getRefSet().getGroupKeys()));

        } else {

            String raw = strValue.getLiteralString();
            return new StrValue(new Literal(builder.literal(raw.charAt(intValue))));

        }
    }







    /**
     * INT_LITERAL 
     * - 将 INT_LITERAL 转换为Calcite literal
     *
     * @param ctx the parse tree
     * @return Literal instance
     */
    @Override
    public OCLElement visitLiteralInt(STOCLParser.LiteralIntContext ctx) {
        int literal = Integer.valueOf(ctx.INT_LITERAL().getText());
        return new Literal(builder.literal(literal));

    }



    /**
     * REAL_LITERAL
     * - 将 REAL_LITERAL 转换为Calcite literal
     *
     * @param ctx the parse tree
     * @return Literal instance
     */
    @Override
    public OCLElement visitLiteralReal(STOCLParser.LiteralRealContext ctx) {
        double literal = Double.valueOf(ctx.REAL_LITERAL().getText());
        return new Literal(builder.literal(literal));
    }



    /**
     * STRING_LITERAL
     * - 将 STRING_LITERAL 转换为Calcite literal
     *
     * @param ctx the parse tree
     * @return Literal instance
     */

    @Override
    public OCLElement visitLiteralString(STOCLParser.LiteralStringContext ctx) {
        String raw = ctx.STRING_LITERAL().getText();
        String val = raw.substring(1, raw.length() - 1);
        return new Literal(builder.literal(val));
    }




    /**
     * BOOLEAN_LITERAL
     * - 将 BOOLEAN_LITERAL 转换为Calcite literal
     *
     * @param ctx the parse tree
     * @return Literal instance
     */
    @Override
    public OCLElement visitLiteralBoolean(STOCLParser.LiteralBooleanContext ctx) {
        boolean literal = Boolean.valueOf(ctx.BOOLEAN_LITERAL().getText());
        return new Literal(builder.literal(literal));
    }



    /**
     * var (',' var)*
     *
     * @param ctx the parse tree
     * @return VarList instance
     */
    @Override
    public OCLElement visitVarListValue(STOCLParser.VarListValueContext ctx) {
        List<Var> vars = ctx.var().stream()
                .map(v -> (Var) visit(v))
                .collect(Collectors.toList());
        return new VarList(vars);
    }


    /**
     * ID (':' ID)?
     *
     * @param ctx the parse tree
     * @return Var instance
     */
    @Override
    public OCLElement visitVarID(STOCLParser.VarIDContext ctx) {
        String varName = ctx.ID(0).getText();
        String typeName = ctx.ID().size() > 1 ? ctx.ID(1).getText() : null;
        return new Var(varName, typeName);
    }




    public ArithExpr arithExprBinaryOp(ArithExpr left,
                                       ArithExpr right,
                                     String op) {

        Map<String, SqlBinaryOperator> binaryOperatorMap = Map.of(
                "*", SqlStdOperatorTable.MULTIPLY,
                "/", SqlStdOperatorTable.DIVIDE,
                "+", SqlStdOperatorTable.PLUS,
                "-", SqlStdOperatorTable.MINUS
        );




        if(left.isRelation() && right.isRelation()) {
            RelNode res;
            int ln = left.getRelNode().getRowType().getFieldCount();
            String leftLastCol = left.getRelNode().getRowType().getFieldNames().get(ln-1);

            int rn = right.getRelNode().getRowType().getFieldCount();
            String rightLastCol = right.getRelNode().getRowType().getFieldNames().get(rn-1);
            // left 和 right的 groupKeys 列表应该相同
            if (!new HashSet<>(left.getRefSet().getGroupKeys()).equals(new HashSet<>(right.getRefSet().getGroupKeys()))){
                throw new RuntimeException(" Both sides of arithmetic operation must have the same group keys.");
            }


            builder.push(left.getRelNode())
                .push(right.getRelNode());


            // 构造join condition: refSet 的groupKeys相等
            List<RexNode> joinFields = left.getRefSet().getGroupKeys().stream()
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
            for (String key : left.getRefSet().getGroupKeys()) proj.add(builder.field(key));

            RexNode sumLast = builder.call(
                    binaryOperatorMap.get(op),
                    builder.field(leftLastCol),
                    builder.field(rightLastCol)
            );
            proj.add(sumLast);

            // 列别名
            List<String> alias = new ArrayList<>(left.getRefSet().getGroupKeys());
            alias.add(leftLastCol + op + rightLastCol);

            res = builder.project(proj, alias)
                            // .distinct()
                            .build();


            return new ArithExpr(new OCLBag(res,
                                            left.getRefSet().getClassName(), 
                                            PrimitiveType.REAL, 
                                            left.getRefSet().getSetType(), 
                                            left.getRefSet().getGroupKeys()));


        } else if (left.isRelation() && !right.isRelation()) {
            RelNode res;
            int n = left.getRelNode().getRowType().getFieldCount();


            builder.push(left.getRelNode());
            // 投影：前 n-1 列来自左表 + (A.n + rightLiteral)列
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));



            // scalar
            RexNode sumLast = builder.call(
                    binaryOperatorMap.get(op),
                    builder.field(n - 1),
                    right.getRexNode()
            );
            proj.add(sumLast);

            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(left.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(left.getRelNode().getRowType().getFieldNames().get(n-1) + op + right.getRexNode().toString());


            res = builder.project(proj, alias)
                        // .distinct()
                        .build();

                

            return new ArithExpr(new OCLBag(res,
                                            left.getRefSet().getClassName(), 
                                            PrimitiveType.REAL, 
                                            left.getRefSet().getSetType(), 
                                            left.getRefSet().getGroupKeys()));


        }else if (!left.isRelation() && right.isRelation()) {
            RelNode res;
            int n = right.getRelNode().getRowType().getFieldCount();


            builder.push(right.getRelNode());
            // 投影：前 n-1 列来自右表 + (leftLiteral + B.n)
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));


            RexNode sumLast = builder.call(
                    binaryOperatorMap.get(op),
                    left.getRexNode(),
                    builder.field(n - 1)
            );
            proj.add(sumLast);


            // 列别名
            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(right.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(right.getRelNode().getRowType().getFieldNames().get(n-1) + op + left.getRexNode().toString());



            res = builder.project(proj)
                        // .distinct()
                        .build();

            return new ArithExpr(new OCLBag(res,
                                            right.getRefSet().getClassName(), 
                                            PrimitiveType.REAL, 
                                            right.getRefSet().getSetType(), 
                                            right.getRefSet().getGroupKeys()));

        } else {
            // RexNode res;
            // RexBuilder rexBuilder = builder.getRexBuilder();
            // res = rexBuilder.makeCall(binaryOperatorMap.get(op), left.getLiteral(), right.getLiteral());
            // return new ArithExpr(res);


            double leftVal = Double.valueOf(left.getLiteralString());
            double rightVal = Double.valueOf(right.getLiteralString());
            Literal res;
            if (op.equals("-")) {
                res = new Literal(builder.literal(leftVal - rightVal));
            } else if (op.equals("+")) {
                res = new Literal(builder.literal(leftVal + rightVal));
            } else if (op.equals("*")) {
                res = new Literal(builder.literal(leftVal * rightVal));
            } else if (op.equals("/")) {
                res = new Literal(builder.literal(leftVal / rightVal));
            } else {
                throw new IllegalArgumentException("Unsupported op: " + op);
            }

            

            return new ArithExpr(res);



        }

    }




}
