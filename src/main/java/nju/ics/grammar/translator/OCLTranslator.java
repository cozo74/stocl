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

        Schema schema = new UMLSchema(cd);


        SchemaPlus root = Frameworks.createRootSchema(true);



        for (String tableName : schema.getTableNames()) {
            Table table = schema.getTable(tableName);
            root.add(tableName, table);
        }


        FrameworkConfig config = Frameworks.newConfigBuilder().defaultSchema(root).build();

        this.builder = RelBuilder.create(config);


    }


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




    @Override
    public OCLElement visitContext(STOCLParser.ContextContext ctx) {

        String className = ctx.ID().getText();

        RelNode var = builder.scan(className)
                .project(builder.field(UMLClassDiagram.getObjectIDColumn(className)))
                .build();

        OCLBag selfSet = new OCLBag(var, className, PrimitiveType.OBJECT, OCLBag.SetType.SINGLE_SET, new ArrayList<>());

        this.varEnv.pushScope();
        this.varEnv.put("self", selfSet);


        List<STOCLParser.InvContext> inv = ctx.inv();
        List<Inv> invs = new ArrayList<>();

        for (STOCLParser.InvContext invContext : inv) {
            invs.add((Inv) visit(invContext));
        }

        this.varEnv.popScope();

        return new Context(className, invs);

    }




    @Override
    public OCLElement visitInv(STOCLParser.InvContext ctx) {


        String invName = ctx.ID() != null ? ctx.ID().getText() : null;
        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());

        OCLBag universalSet = varEnv.resolve("self");


        if (!new HashSet<>(universalSet.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }


        RelNode inv = builder.push(universalSet.getRelNode())
                .push(oclBool.getRelNode())
                .minus(false, 2)
                .aggregate(
                        builder.groupKey(), builder.countStar("cnt"))
                .project(
                        List.of(builder.call(SqlStdOperatorTable.EQUALS,
                                builder.field("cnt"), builder.literal(0))), 
                        List.of("satisfied"))
                .build();


        return new Inv(invName, ctx.getText(), inv);


    }




    @Override
    public OCLElement visitOclBoolNot(STOCLParser.OclBoolNotContext ctx) {

        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());

        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());

        if (!new HashSet<>(universalSet.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }

        RelNode res = builder.push(universalSet.getRelNode())
                .push(oclBool.getRelNode())
                .minus(false, 2)
                .build();


        return new OCLBool(res);

    }




    @Override
    public OCLElement visitOclBoolAndOr(STOCLParser.OclBoolAndOrContext ctx) {


        OCLBool bool1 = (OCLBool) visit(ctx.oclBool(0));
        OCLBool bool2 = (OCLBool) visit(ctx.oclBool(1));

        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());


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
            RelNode and = builder.push(left)
                    .push(right)
                    .intersect(false)
                    .build();
            resultBool = new OCLBool(and);
        } else {
            RelNode or = builder.push(left)
                    .push(right)
                    .union(false)
                    .build();
            resultBool = new OCLBool(or);
        }


        return resultBool;


    }




    @Override
    public OCLElement visitOclBoolImpliesXor(STOCLParser.OclBoolImpliesXorContext ctx) {

        OCLBool bool1 = (OCLBool) visit(ctx.oclBool(0));
        OCLBool bool2 = (OCLBool) visit(ctx.oclBool(1));

        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());


        if (!new HashSet<>(universalSet.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(bool1.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }
        if (!new HashSet<>(universalSet.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(bool2.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }

        RelNode left = bool1.getRelNode();
        RelNode right = bool2.getRelNode();

        OCLBool resultBool;

        if (ctx.boolOp.getText().equals("implies")) {
            RelNode implies = builder.push(universalSet.getRelNode())
                    .push(left)
                    .minus(false, 2)
                    .push(right)
                    .union(false,2)
                    .build();

            resultBool = new OCLBool(implies);


        } else {
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



    @Override
    public OCLElement visitOclBoolEqualityExpr(STOCLParser.OclBoolEqualityExprContext ctx) {

        EqualExpr equalExpr = (EqualExpr) visit(ctx.equalExpr());


        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());

        builder.push(equalExpr.getRelNode());

        List<RexInputRef> proj = universalSet.getRelNode().getRowType().getFieldNames()
                .stream()
                .map(f -> builder.field(f))
                .toList();

        RelNode projectAndDistinct = builder.project(proj)
                        .build();


        return new OCLBool(projectAndDistinct);
    }



    @Override
    public OCLElement visitOclBoolBAttr(STOCLParser.OclBoolBAttrContext ctx) {

        String bAttr = ctx.bAttr().getText();

        OCLObj oclObj = (OCLObj) visit(ctx.oclObj());


        String objTableName = oclObj.getClassName();    
        String idColName = UMLClassDiagram.getObjectIDColumn(objTableName); 
        RelNode objTable = builder.scan(objTableName)       
                .project(builder.field(idColName),  
                        builder.field(bAttr)) 
                .build();

        builder.push(oclObj.getRelNode())
                .push(objTable)
                .join(JoinRelType.INNER,
                        builder.equals(
                                builder.field(2, 0, idColName),
                                builder.field(2, 1, idColName))); 

        List<RexInputRef> fields = oclObj.getRelNode()
                                                    .getRowType()
                                                    .getFieldNames()
                                                    .stream()
                                                    .map(f -> builder.field(f)).collect(Collectors.toList());
        fields.add(builder.field(bAttr));


        
        RelNode project = builder.project(fields)
                                .build(); 

        RelNode filter = builder.push(project)
                .filter(builder.equals(builder.field(bAttr),
                        builder.literal(true)))
                .build();



        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());

        builder.push(filter);

        List<RexInputRef> proj = universalSet.getRelNode().getRowType().getFieldNames()
                .stream()
                .map(f -> builder.field(f))
                .toList();

        RelNode projectAndDistinct = builder.project(proj)
                                        .build();




        return new OCLBool(projectAndDistinct);
    }



    @Override
    public OCLElement visitOclBoolBagPredicate(STOCLParser.OclBoolBagPredicateContext ctx) {
        BagPredicate bagPredicate = (BagPredicate) visit(ctx.bagPredicate());

        OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());

        builder.push(bagPredicate.getRelNode());
        List<RexInputRef> proj = universalSet.getRelNode().getRowType().getFieldNames()
                .stream()
                .map(f -> builder.field(f))
                .toList();

        RelNode projectAndDistinct = builder.project(proj)
                        .build();


        return new OCLBool(projectAndDistinct);
    }



    @Override
    public OCLElement visitOclBoolParen(STOCLParser.OclBoolParenContext ctx) {

        return visit(ctx.oclBool());
    }


    @Override
    public OCLElement visitIncludesAll(STOCLParser.IncludesAllContext ctx) {



        OCLBag set1 = (OCLBag) visit(ctx.oclBag(0));
        OCLBag set2 = (OCLBag) visit(ctx.oclBag(1));


        RelNode res;
        if (set1.isSingleSet() ){
            if (set2.isSingleSet()){
                
                RelNode set2Count = builder.push(set2.getRelNode())
                        .aggregate(builder.groupKey(), builder.count(false, "b_count"))
                        .build();
                RelNode interCount = builder.push(set1.getRelNode())
                        .push(set2.getRelNode())
                        .intersect(false, 2)
                        .aggregate(builder.groupKey(), builder.count(false, "i_count"))
                        .build();
                RelNode countJoin = builder.push(set2Count).push(interCount)
                        .join(JoinRelType.INNER,
                                builder.equals(builder.field(2, 0, "b_count"),
                                                builder.field(2, 1, "i_count")))
                        .build();



                RexNode condition = builder.scalarQuery(b -> {
                        b.push(countJoin)
                            .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                            .project(
                                    List.of(builder.equals(builder.field("cnt"), builder.literal(1))), 
                                    List.of("satisfied") 
                            );
                        return b.build();
                });

                RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
                res = builder.push(contextObjects)
                        .filter(condition)
                        .build();


            } else {

                RelNode set2GroupAndCount = builder.push(set2.getRelNode())
                        .aggregate(builder.groupKey(set2.getGroupKeys().stream().map(f->builder.field(f)).toList())
                                ,builder.count(false, "b_count"))
                        .build();

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


                builder.push(innerJoin).push(set2GroupAndCount);

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

                RexNode set2Count = builder.scalarQuery(b -> {
                    b.push(set2.getRelNode());
                    b.aggregate(builder.groupKey(), builder.count(false, "b_count"));
                    return b.build();
                });


                RelNode innerJoin = builder.push(set1.getRelNode())
                        .push(set2.getRelNode())
                        .join(JoinRelType.INNER,
                                builder.equals(
                                        builder.field(2, 0, set1.getRelNode().getRowType().getFieldCount()-1),
                                        builder.field(2, 1, set2.getRelNode().getRowType().getFieldCount()-1)
                                )
                        ).build();




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



                RelNode set2GroupAndCount = builder.push(set2.getRelNode())
                        .aggregate(builder.groupKey(set1.getGroupKeys().stream().map(f->builder.field(f)).toList())
                                                    ,builder.count(false, "b_count"))
                        .build();

                builder.push(set1.getRelNode())
                        .push(set2.getRelNode());


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


                builder.push(innerJoin)
                        .push(set2GroupAndCount);

                joinFields.remove(joinFields.size()-1);
                joinFields.add(builder.equals(builder.field(2, 0, "i_count"),
                                             builder.field(2, 1, "b_count")));
                RexNode joinCondition2 = builder.and(joinFields);

                res = builder.join(JoinRelType.INNER, joinCondition2)
                        .build();

            }
        }


        return new BagPredicate(res);


    }



    @Override
    public OCLElement visitExcludesAll(STOCLParser.ExcludesAllContext ctx) {
        OCLBag set1 = (OCLBag) visit(ctx.oclBag(0));
        OCLBag set2 = (OCLBag) visit(ctx.oclBag(1));


        RelNode res;
        if (set1.isSingleSet() ){
            if (set2.isSingleSet()){

                RelNode interCount = builder.push(set1.getRelNode())
                        .push(set2.getRelNode())
                        .intersect(false, 2)
                        .aggregate(builder.groupKey(), builder.count(false, "i_count"))
                        .build();


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

                RelNode contextObjects =
                varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
                
                List<String> gks = set2.getGroupKeys();
                
                RelNode allGroups =
                    builder.push(contextObjects)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();
                
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
                
                RelNode hitGroups =
                    builder.push(hitRows)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();
                
                res = builder.push(allGroups)
                            .push(hitGroups)
                            .minus(false, 2)
                            .build();


            }

        } else {
            if(set2.isSingleSet()) {

                RelNode contextObjects =
                    varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();

                List<String> gks = set1.getGroupKeys();

                RelNode allGroups =
                    builder.push(contextObjects)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();

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

                RelNode hitGroups =
                    builder.push(hitRows)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();

                res = builder.push(allGroups)
                            .push(hitGroups)
                            .minus(false, 2)
                            .build();



            } else {
                if (!new HashSet<>(set1.getGroupKeys()).equals(new HashSet<>(set1.getGroupKeys()))){
                    throw new RuntimeException(" Group keys of two oclBags should be the same for includesAll operation.");
                }


                RelNode contextObjects =
                    varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();

                List<String> gks = set1.getGroupKeys(); 

                RelNode allGroups =
                    builder.push(contextObjects)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();

                builder.push(set1.getRelNode())
                    .push(set2.getRelNode());

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

                RelNode hitGroups =
                    builder.push(hitRows)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();

                res = builder.push(allGroups)
                            .push(hitGroups)
                            .minus(false, 2)
                            .build();


            }
        }

        return new BagPredicate(res);


    }



    @Override
    public OCLElement visitIncludes(STOCLParser.IncludesContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        Literal literal = (Literal) visit(ctx.literal());



        RelNode res;

        if (set.isSingleSet()) {

            RelNode filter = builder.push(set.getRelNode())
                    .filter(builder.equals(
                            builder.field(set.getRelNode().getRowType().getFieldCount()-1),
                            literal.getValue()
                    ))
                    .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                    .build();

            RexNode condition = builder.scalarQuery(b -> {
                b.push(filter)
                    .project(
                        List.of(b.greaterThan(
                                b.field("cnt"),
                                b.literal(0))), 
                        List.of("satisfied") 
                    );
                return b.build();
            });

            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();

        } else {

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



    @Override
    public OCLElement visitExcludes(STOCLParser.ExcludesContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        Literal literal = (Literal) visit(ctx.literal());


        RelNode res;

        if (set.isSingleSet()) {

            RelNode filter = builder.push(set.getRelNode())
                    .filter(builder.equals(
                            builder.field(set.getRelNode().getRowType().getFieldCount()-1),
                            literal.getValue()
                    ))
                    .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                    .build();

            RexNode condition = builder.scalarQuery(b -> {
                b.push(filter)
                    .project(
                        List.of(b.equals(
                                b.field("cnt"),
                                b.literal(0))),
                        List.of("satisfied") 
                    );
                return b.build();
            });

            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();

        } else {



            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            List<String> gks = set.getGroupKeys();

            RelNode allGroups =
                builder.push(contextObjects)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();
        
            RelNode hitRows =
                builder.push(set.getRelNode())
                        .filter(builder.equals(
                            builder.field(set.getRelNode().getRowType().getFieldCount() - 1),
                            literal.getValue()))
                        .build();
        
            RelNode hitGroups =
                builder.push(hitRows)
                        .project(
                            gks.stream().map(builder::field).toList(),
                            gks
                        )
                        .build();
        
            res = builder.push(allGroups)
                        .push(hitGroups)
                        .minus( false, 2) 
                        .build();


        }

        return new BagPredicate(res);
    }





    @Override
    public OCLElement visitIsEmpty(STOCLParser.IsEmptyContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());


        RelNode res;


        if (set.isSingleSet()) {
            
            RexNode condition = builder.scalarQuery(b -> {
                    b.push(set.getRelNode())
                        .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                        .project(
                                List.of(builder.equals(builder.field("cnt"), builder.literal(0))), 
                                List.of("satisfied") 
                        );
                    return b.build();
            });

            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();


        } else {

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

            RelNode nonEmptyGroups =
                builder.push(set.getRelNode())
                        .project(
                            set.getGroupKeys().stream()
                                .map(builder::field)
                                .toList(),
                            set.getGroupKeys()
                        )
                        .build();


            res = builder.push(allGroups)
                        .push(nonEmptyGroups)
                        .minus(false, 2)
                        .build();

                }


        return new BagPredicate(res);

    }



    @Override
    public OCLElement visitNotEmpty(STOCLParser.NotEmptyContext ctx) {
        OCLBag set = (OCLBag) visit(ctx.oclBag());


        RelNode res;


        if (set.isSingleSet()) {
            
            RexNode condition = builder.scalarQuery(b -> {
                    b.push(set.getRelNode())
                        .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                        .project(
                                List.of(builder.greaterThan(builder.field("cnt"), builder.literal(0))), 
                                List.of("satisfied") 
                        );
                    return b.build();
            });

            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();


        } else {

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





    @Override
    public OCLElement visitForAll(STOCLParser.ForAllContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        VarList varList = (VarList) visit(ctx.varList());

        this.varEnv.pushScope();
        for (String varName : varList.getVarNames()) {
            this.varEnv.put(varName, set);
        }

        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());


        varEnv.popScope();


        List<String> contextGroupkeys = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getGroupKeys();

        if (!new HashSet<>(set.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }


        if (!set.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" set does not contain all group keys.");
        }
        if (!oclBool.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" oclBool does not contain all group keys.");
        }


        RelNode res;


        if(set.isSingleSet()){


            RelNode boolCount = builder.push(oclBool.getRelNode())
                    .aggregate(builder.groupKey(), builder.count(false, "b_count"))
                    .build();
            RelNode setCount = builder.push(set.getRelNode())
                    .aggregate(builder.groupKey(), builder.count(false, "s_count"))
                    .build();

            RelNode countJoin = builder.push(boolCount).push(setCount)
                    .join(JoinRelType.INNER,
                            builder.equals(builder.field(2, 0, "b_count"),
                                            builder.field(2, 1, "s_count")))
                    .build();




            RexNode condition = builder.scalarQuery(b -> {
                    b.push(countJoin)
                        .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                        .project(
                                List.of(builder.equals(builder.field("cnt"), builder.literal(1))), 
                                List.of("satisfied") 
                        );
                    return b.build();
            });

            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();



        } else {

            List<String> groupKeys = set.getGroupKeys();

            RelNode boolCount = builder.push(oclBool.getRelNode())
                    .aggregate(builder.groupKey(groupKeys.stream().map(f->builder.field(f)).toList()),
                                                            builder.count(false, "b_count"))
                    .build();

            RelNode setCount = builder.push(set.getRelNode())
                    .aggregate(builder.groupKey(groupKeys.stream().map(f->builder.field(f)).toList()), builder.count(false, "s_count"))
                    .build();


            builder.push(boolCount).push(setCount);

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

            res = builder.join(JoinRelType.INNER, joinCondition).build();


        }




        return new BagPredicate(res);

    }



    @Override
    public OCLElement visitExists(STOCLParser.ExistsContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        VarList varList = (VarList) visit(ctx.varList());

        this.varEnv.pushScope();
        for (String varName : varList.getVarNames()) {
            this.varEnv.put(varName, set);
        }

        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());

        this.varEnv.popScope();


        List<String> contextGroupkeys = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getGroupKeys();

        if (!new HashSet<>(set.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }

        if (!set.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" set does not contain all group keys.");
        }
        if (!oclBool.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" oclBool does not contain all group keys.");
        }


        RelNode res;


        if(set.isSingleSet()){


            RexNode condition = builder.scalarQuery(b -> {
                    b.push(oclBool.getRelNode())
                        .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                        .project(
                                List.of(builder.greaterThan(builder.field("cnt"), builder.literal(0))), 
                                List.of("satisfied") 
                        );
                    return b.build();
            });

            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();


        } else {

            res = builder.push(oclBool.getRelNode())
                    .aggregate(builder.groupKey(set.getGroupKeys().stream().map(f->builder.field(f)).toList()), builder.count(false, "cnt"))
                    .filter(builder.greaterThan(builder.field("cnt"), builder.literal(0)))
                    .build();

        }



        return new BagPredicate(res);
    }






    @Override
    public OCLElement visitOne(STOCLParser.OneContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        Var var = (Var) visit(ctx.var());

        this.varEnv.pushScope();
        this.varEnv.put(var.getVarName(), set);

        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());


        this.varEnv.popScope();


        List<String> contextGroupkeys = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getGroupKeys();

        if (!new HashSet<>(set.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }

        if (!set.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" set does not contain all group keys.");
        }
        if (!oclBool.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" oclBool does not contain all group keys.");
        }

        RelNode res;


        if(set.isSingleSet()){

            RexNode condition = builder.scalarQuery(b -> {
                    b.push(oclBool.getRelNode())
                        .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                        .project(
                                List.of(builder.equals(builder.field("cnt"), builder.literal(1))), 
                                List.of("satisfied") 
                        );
                    return b.build();
            });

            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();


        } else {

            res = builder.push(oclBool.getRelNode())
                    .aggregate(builder.groupKey(set.getGroupKeys().stream().map(f->builder.field(f)).toList()), builder.count(false, "cnt"))
                    .filter(builder.equals(builder.field("cnt"), builder.literal(1)))
                    .build();

        }




        return new BagPredicate(res);
    }





    @Override
    public OCLElement visitIsUnique(STOCLParser.IsUniqueContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());

        String attr = ctx.attr().getText();




        if (!set.isObjectSet()){
            throw new RuntimeException(" only object set can call isUnique(attr) ");
        }

        String className = set.getClassName();
        String classId = UMLClassDiagram.getObjectIDColumn(className);

        RelNode classTable = builder.scan(className)
                                    .project(builder.field(classId), builder.field(attr)) 
                                    .build();

        RelNode joinTable = builder.push(set.getRelNode())
                                    .push(classTable)
                                    .join(JoinRelType.INNER, builder.equals(
                                                    builder.field(2, 0, classId),
                                                    builder.field(2, 1, classId)))
                                    .build();

        RelNode res;

        if(set.isSingleSet()) {

            RelNode distinctAttrCount = builder.push(joinTable)
                    .aggregate(builder.groupKey(), builder.count(true, "u_count", builder.field(attr)))
                    .build();

            RelNode setCount = builder.push(set.getRelNode())
                    .aggregate(builder.groupKey(), builder.count(false, "s_count"))
                    .build();


            RelNode countJoin = builder.push(distinctAttrCount).push(setCount)
                    .join(JoinRelType.INNER,
                            builder.equals(builder.field(2, 0, "u_count"),
                                            builder.field(2, 1, "s_count")))
                    .build();

            RexNode condition = builder.scalarQuery(b -> {
                    b.push(countJoin)
                        .aggregate(builder.groupKey(), builder.count(false, "cnt"))
                        .project(
                                List.of(builder.equals(builder.field("cnt"), builder.literal(1))),
                                List.of("satisfied") 
                        );
                    return b.build();
            });

            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            res = builder.push(contextObjects)
                    .filter(condition)
                    .build();


        } else {

            List<String> groupKeys = set.getGroupKeys();

            RelNode boolCount = builder.push(joinTable)
                    .aggregate(builder.groupKey(groupKeys.stream().map(f->builder.field(f)).toList()),
                                builder.count(true, "b_count", builder.field(attr)))
                    .build();

            RelNode setCount = builder.push(set.getRelNode())
                    .aggregate(builder.groupKey(groupKeys.stream().map(f->builder.field(f)).toList()), builder.count(false, "s_count"))
                    .build();


            builder.push(boolCount).push(setCount);

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

            res = builder.join(JoinRelType.INNER, joinCondition).build();


        }



        return new BagPredicate(res);

    }





    private OCLBag setOperation(OCLBag set1, OCLBag set2, String op){

        String set1FieldName = set1.getRelNode().getRowType().getFieldNames().get(set1.getRelNode().getRowType().getFieldCount()-1);
        String set2FieldName = set2.getRelNode().getRowType().getFieldNames().get(set2.getRelNode().getRowType().getFieldCount()-1);

        if (set1.getSetElementType() != set2.getSetElementType()) {
            throw new RuntimeException(" Set element types are not the same for set operation.");
        }


        if(set1.isSingleSet()){
            if(set2.isSingleSet()){
 

                RelNode left = builder.push(set1.getRelNode())
                                .project(builder.field(set1FieldName))
                                .build();


                RelNode right = builder.push(set2.getRelNode())
                                .project(builder.field(set2FieldName))
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


                return new OCLBag(res, set1.getClassName(), set1.getSetElementType(), OCLBag.SetType.SINGLE_SET, null);

            } else {

                int fieldIndex = set1.getRelNode().getRowType().getFieldCount()-1;
                RelNode singleProject = builder.push(set1.getRelNode())
                        .project(builder.field(fieldIndex))
                        .build();

                builder.push(set2.getRelNode());


                RelNode groupProject = builder.project(set2.getGroupKeys().stream().map(f->builder.field(f)).toList())
                                                .build();

                RelNode product = builder.push(groupProject)
                                        .push(singleProject)
                                        .join(JoinRelType.INNER, builder.literal(true))
                                        .build();


                builder.push(set2.getRelNode());
                String fieldName = set2.getRelNode().getRowType().getFieldNames().get(set2.getRelNode().getRowType().getFieldCount()-1);

                List<RexInputRef> proj = new ArrayList<>(set2.getGroupKeys().stream().map(f->builder.field(f)).toList());
                proj.add(builder.field(fieldName));

                RelNode groupProjectWithLastCol = builder.project(proj)
                                                        .build();

                                        

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

                return new OCLBag(res, set2.getClassName(), set2.getSetElementType(), OCLBag.SetType.POWER_SET, set2.getGroupKeys());
            }

            

        } else {
            if(set2.isSingleSet()) {

                int fieldIndex = set2.getRelNode().getRowType().getFieldCount()-1;
                RelNode singleProject = builder.push(set2.getRelNode())
                        .project(builder.field(fieldIndex))
                        .build();

                builder.push(set1.getRelNode());


                RelNode groupProject = builder.project(set1.getGroupKeys().stream().map(f->builder.field(f)).toList())
                                                                                                        .build();

                RelNode product = builder.push(groupProject)
                                        .push(singleProject)
                                        .join(JoinRelType.INNER, builder.literal(true))
                                        .build();


                builder.push(set1.getRelNode());
                String fieldName = set1.getRelNode().getRowType().getFieldNames().get(set1.getRelNode().getRowType().getFieldCount()-1);
                List<RexInputRef> proj = set1.getGroupKeys().stream().map(f->builder.field(f)).collect(Collectors.toList());
                proj.add(builder.field(fieldName));

                RelNode groupProjectWithLastCol = builder.project(proj)
                                                            .build();
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

                return new OCLBag(res, set1.getClassName(), set1.getSetElementType(), OCLBag.SetType.POWER_SET, set1.getGroupKeys());

            } else {

                if (!new HashSet<>(set1.getGroupKeys()).equals(new HashSet<>(set2.getGroupKeys()))) {
                    throw new RuntimeException(" set1 does not have the same group keys as set2.");
                }


                builder.push(set1.getRelNode());
                List<RexInputRef> proj = set1.getGroupKeys().stream().map(f->builder.field(f)).collect(Collectors.toList());
                String fieldName = set1.getRelNode().getRowType().getFieldNames().get(set1.getRelNode().getRowType().getFieldCount()-1);
                proj.add(builder.field(fieldName));

                RelNode left = builder.project(proj)
                                    .build();

                builder.push(set2.getRelNode());
                proj = set1.getGroupKeys().stream().map(f->builder.field(f)).collect(Collectors.toList());
                proj.add(builder.field(fieldName));
                RelNode right = builder.project(proj)
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

                return new OCLBag(res, set1.getClassName(), set1.getSetElementType(), OCLBag.SetType.POWER_SET, set1.getGroupKeys());

            }


        }
    }






    @Override
    public OCLElement visitUnion(STOCLParser.UnionContext ctx) {
        OCLBag set1 = (OCLBag) visit(ctx.oclBag(0));
        OCLBag set2 = (OCLBag) visit(ctx.oclBag(1));

        return setOperation(set1, set2, "union");

    }


    @Override
    public OCLElement visitIntersection(STOCLParser.IntersectionContext ctx) {

        OCLBag set1 = (OCLBag) visit(ctx.oclBag(0));
        OCLBag set2 = (OCLBag) visit(ctx.oclBag(1));

        return setOperation(set1, set2, "intersection");

    }



    @Override
    public OCLElement visitDifference(STOCLParser.DifferenceContext ctx) {

        OCLBag set1 = (OCLBag) visit(ctx.oclBag(0));
        OCLBag set2 = (OCLBag) visit(ctx.oclBag(1));

        return setOperation(set1, set2, "difference");

    }


    @Override
    public OCLElement visitSymmetricDifference(STOCLParser.SymmetricDifferenceContext ctx) {

        OCLBag set1 = (OCLBag) visit(ctx.oclBag(0));
        OCLBag set2 = (OCLBag) visit(ctx.oclBag(1));

        return setOperation(set1, set2, "symmetricDifference");

    }




    @Override
    public OCLElement visitSelect(STOCLParser.SelectContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        Var var = (Var) visit(ctx.var());

        this.varEnv.pushScope();
        this.varEnv.put(var.getVarName(), set);

        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());

        this.varEnv.popScope();


        List<String> contextGroupkeys = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getGroupKeys();

        if (!new HashSet<>(set.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }


        if (!set.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" set does not contain all group keys.");
        }
        if (!oclBool.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" oclBool does not contain all group keys.");
        }




        RelNode res = oclBool.getRelNode();
        if(set.isSingleSet()){
            return new OCLBag(res, set.getClassName(), set.getSetElementType(), OCLBag.SetType.SINGLE_SET, null);
        } else {
            return new OCLBag(res, set.getClassName(), set.getSetElementType(), OCLBag.SetType.POWER_SET, set.getGroupKeys());
        }



    }



    @Override
    public OCLElement visitReject(STOCLParser.RejectContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        Var var = (Var) visit(ctx.var());

        this.varEnv.pushScope();
        this.varEnv.put(var.getVarName(), set);

        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());

        this.varEnv.popScope();


        List<String> contextGroupkeys = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getGroupKeys();

        if (!new HashSet<>(set.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException(" oclBool does not have the same columns as universal set.");
        }
        if (!set.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" set does not contain all group keys.");
        }
        if (!oclBool.getRelNode().getRowType().getFieldNames().containsAll(contextGroupkeys)) {
            throw new RuntimeException(" oclBool does not contain all group keys.");
        }

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





    @Override
    public OCLElement visitBagRoleOrAttr(STOCLParser.BagRoleOrAttrContext ctx) {

        OCLBag set = (OCLBag) visit(ctx.oclBag());
        String roleOrAttr = ctx.roleOrAttr().getText();

        if (set.isValueSet()) {
            throw new RuntimeException(" Only object set can access role or attribute.");
        }

        if (!cd.hasAttr(set.getClassName(), roleOrAttr) && !cd.hasRole(set.getClassName(), roleOrAttr)) {
            throw new RuntimeException(" Class " + set.getClassName() +
                    " has no attribute or role named " + roleOrAttr);
        }


        if(cd.hasAttr(set.getClassName(), roleOrAttr) ) {

            String className = set.getClassName();

            int n = set.getRelNode().getRowType().getFieldCount();

            builder.push(set.getRelNode())
                    .scan(className)
                    .join(JoinRelType.INNER,
                            builder.equals(
                                    builder.field(2, 0, n-1),
                                    builder.field(2, 1, UMLClassDiagram.getObjectIDColumn(className))
                            ));

            List<RexInputRef> proj = set.getRelNode()
                    .getRowType()
                    .getFieldNames()
                    .stream()
                    .map(f -> builder.field(f))
                    .collect(Collectors.toList());

            proj.add(builder.field(roleOrAttr));

            RelNode res = builder.project(proj) 
                    .build();

            if(set.isSingleSet()){
                return new OCLBag(res, className, cd.getAttrType(className, roleOrAttr), OCLBag.SetType.SINGLE_SET, null);
            } else {
                return new OCLBag(res, className, cd.getAttrType(className, roleOrAttr), OCLBag.SetType.POWER_SET, set.getGroupKeys());
            }


        } else if (cd.hasRole(set.getClassName(), roleOrAttr)) {

            String className = set.getClassName();
            String classCol = UMLClassDiagram.getObjectIDColumn(className);
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

            RelNode res = builder.project(proj) 
                    .build();

            if(set.isSingleSet()){
                return new OCLBag(res, assoEndClassName, PrimitiveType.OBJECT, OCLBag.SetType.SINGLE_SET, null);
            } else {
                return new OCLBag(res,  assoEndClassName, PrimitiveType.OBJECT, OCLBag.SetType.POWER_SET, new ArrayList<>(set.getGroupKeys()));

            }


        } else {
            throw new RuntimeException("Class " + set.getClassName() +
                    " has no attribute or role named " + roleOrAttr);
        }



    }




    @Override
    public OCLElement visitAllInstances(STOCLParser.AllInstancesContext ctx) {

        String className = ctx.ID().getText();

        if (!cd.hasClass(className)) {
            throw new RuntimeException(" Class " + className + " does not exist in the UML class diagram.");
        }

        RelNode rel = builder.scan(className)
                        .project(builder.field(UMLClassDiagram.getObjectIDColumn(className)))
                        .build();

        return new OCLBag(rel, className, PrimitiveType.OBJECT, OCLBag.SetType.SINGLE_SET, new ArrayList<>()); 
    }




    @Override
    public OCLElement visitMultipleRole(STOCLParser.MultipleRoleContext ctx) {
        OCLObj obj = (OCLObj) visit(ctx.oclObj());
        String role = ctx.role().getText();

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
                .build();


        List<String> groupKeys;


        if (obj.getRefSet().isSingleSet()) {
            groupKeys = new ArrayList<>();
            groupKeys.add(classCol);

        } else {
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

            List<String> lFieldNames = new ArrayList<>( left.getRelNode().getRowType().getFieldNames());
            List<String> rFieldNames = new ArrayList<>( right.getRelNode().getRowType().getFieldNames());
            int leftIndex = lFieldNames.size()-1;
            int rightIndex = rFieldNames.size()-1;


            builder.push(left.getRelNode())
                    .push(right.getRelNode());

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

            RelNode rel = builder.push(left.getRelNode())
                    .filter(builder.call(compOpMap.get(compOp),
                            builder.field(leftIndex),
                            right.getRexNode()))
                    .build();

            return new EqualExpr(rel);

        } else if (!left.isRelation() && right.isRelation()) {

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

            RelNode rel = builder.push(right.getRelNode())
                    .filter(builder.call(compOpMap.get(compOp),
                            left.getRexNode(),
                            builder.field(2, 0, rightIndex)))
                    .build();

            return new EqualExpr(rel);


        } else {


            if (left.isLiteral() && right.isLiteral()){
                throw new RuntimeException("Both sides of equality cannot be literal: " +
                    left.getRexNode() + " " + compOp + " " + right.getRexNode());
            }


            RexNode condition = builder.call(compOpMap.get(compOp),left.getRexNode(), right.getRexNode());

            RelNode contextObjects = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst()).getRelNode();
            RelNode res = builder.push(contextObjects)
                    .filter(condition)
                    .build();

            return new EqualExpr(res);

        }



    }



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


            RelNode res = builder.push(left.getRelNode())
                    .push(right.getRelNode())
                    .semiJoin(builder.call(compOpMap.get(compOp),
                            builder.field(2, 0, leftIndex),
                            builder.field(2, 1, rightIndex)))
                    .build();

            return new EqualExpr(res);

        } else if (left.isRelation() && !right.isRelation()) {

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

            RelNode rel = builder.push(left.getRelNode())
                    .filter(builder.call(compOpMap.get(compOp),
                            builder.field(leftIndex),
                            right.getLiteral()))
                    .build();

            return new EqualExpr(rel);

        } else if (!left.isRelation() && right.isRelation()) {

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

            RelNode rel = builder.push(right.getRelNode())
                    .filter(builder.call(compOpMap.get(compOp),
                            left.getLiteral(),
                            builder.field( rightIndex)))
                    .build();

            return new EqualExpr(rel);


        } else {
            OCLBag universalSet = varEnv.resolve(varEnv.getCurScopeVarNames().getFirst());

            RelNode res = builder.push(universalSet.getRelNode())
                    .filter(builder.call(compOpMap.get(compOp),
                            left.getLiteral(),
                            right.getLiteral()))
                    .build();

            return new EqualExpr(res);

        }


    }


   @Override
   public OCLElement visitEqualityExprObject(STOCLParser.EqualityExprObjectContext ctx) {

       Map<String, SqlBinaryOperator> compOpMap = Map.of(
               "=", SqlStdOperatorTable.EQUALS,
               "<>", SqlStdOperatorTable.NOT_EQUALS
       );

       OCLObj left = (OCLObj) visit(ctx.oclObj(0));
       OCLObj right = (OCLObj) visit(ctx.oclObj(1));

       String compOp = ctx.compOp.getText();

        if (!left.getRefSet().isObjectSet()){
            throw new RuntimeException(" Left side of comparison must be an object set.");
        }
        if (!right.getRefSet().isObjectSet()){
            throw new RuntimeException(" Right side of comparison must be an object set.");
        }


       int leftIndex = left.getRelNode().getRowType().getFieldCount()-1;
       int rightIndex = right.getRelNode().getRowType().getFieldCount()-1;
       RelNode res = builder.push(left.getRelNode())
               .push(right.getRelNode())
               .semiJoin(builder.call(compOpMap.get(compOp),
                       builder.field(2, 0, leftIndex),
                       builder.field(2, 1, rightIndex)))
               .build();

       return new EqualExpr(res);

   }




    @Override
    public OCLElement visitArithUnaryMinus(STOCLParser.ArithUnaryMinusContext ctx) {
        ArithExpr arithExpr = (ArithExpr) visit(ctx.arithExpr());


        if (arithExpr.isRelation()) {

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
                projects.add(builder.field(i));  
            }
            projects.add(negLastCol);             
            List<String> alias = new ArrayList<>(arithExpr.getRelNode().getRowType().getFieldNames());
            String lastColStr = "-" + alias.get(count);
            alias.remove(count);
            alias.add(lastColStr);


            RelNode res = builder.project(projects, alias) 
                                .build();


            return new ArithExpr(new OCLBag(res, arithExpr.getRefSet().getClassName(), arithExpr.getRefSet().getSetElementType(), arithExpr.getRefSet().getSetType(), arithExpr.getRefSet().getGroupKeys()));

        } else {
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



    @Override
    public OCLElement visitArithMultDiv(STOCLParser.ArithMultDivContext ctx) {


        ArithExpr left = (ArithExpr) visit(ctx.arithExpr(0));
        ArithExpr right = (ArithExpr) visit(ctx.arithExpr(1));
        String op = ctx.op.getText();

        return arithExprBinaryOp(left, right, op);

    }





    @Override
    public OCLElement visitArithAddSub(STOCLParser.ArithAddSubContext ctx) {
        ArithExpr left = (ArithExpr) visit(ctx.arithExpr(0));
        ArithExpr right = (ArithExpr) visit(ctx.arithExpr(1));
        String op = ctx.op.getText();

        return arithExprBinaryOp(left, right, op);
    }




    @Override
    public OCLElement visitArithParen(STOCLParser.ArithParenContext ctx) {
        return visit(ctx.arithExpr());
    }




    @Override
    public OCLElement visitArithValueIntLiteral(STOCLParser.ArithValueIntLiteralContext ctx) {

        int val = Integer.parseInt(ctx.INT_LITERAL().getText());
        RexLiteral literal = builder.literal(val);

        return new ArithExpr(new Literal(literal));
    }



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
                                builder.field(2, 1, UMLClassDiagram.getObjectIDColumn(tableName)) 
                        ));



        List<RexInputRef> proj = obj.getRelNode()
                .getRowType()
                .getFieldNames()
                .stream()
                .map(f -> builder.field(f))
                .collect(Collectors.toList());

        proj.add(builder.field(attr));


        RelNode res = builder.project(proj)
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






    @Override
    public OCLElement visitArithValueStrSize(STOCLParser.ArithValueStrSizeContext ctx){
        StrValue strValue = (StrValue) visit(ctx.strValue());

        if (strValue.isRelation()){

            RelNode res;
            int n = strValue.getRefSet().getRelNode().getRowType().getFieldCount();

            builder.push(strValue.getRefSet().getRelNode());
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode rex = builder.call(
                    SqlStdOperatorTable.CHAR_LENGTH,
                    builder.field(n-1)
            );
            proj.add(rex);

            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue.getRefSet().getRelNode().getRowType().getFieldNames().get(i));
            alias.add("length");

            res = builder.project(proj, alias)
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
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode rex = builder.call(
                    arithOp,
                    builder.field(n-1)
            );
            proj.add(rex);

            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(arithExpr.getRefSet().getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
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



            builder.push(joined);
            List<RexNode> proj = new ArrayList<>();
            for (String key : lfields) proj.add(builder.field(key));

            RexNode rex = builder.call(
                    arithOp,
                    builder.field(ln-1), 
                    builder.field(ln+rn-1) 
            );

            proj.add(rex);

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
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));


            RexNode a2 = arithExpr2.getRexNode();

            RexNode disLast = builder.call(
                    arithOp,
                    builder.field(n - 1),
                    a2
            );
            proj.add(disLast);

            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(arithExpr1.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
                            .build();

            return Either.left(res);


        } else if (!arithExpr1.isRelation() && arithExpr2.isRelation()) {

            RelNode res;
            int n = arithExpr2.getRelNode().getRowType().getFieldCount();


            builder.push(arithExpr2.getRelNode());
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode a1 = arithExpr1.getRexNode();

            RexNode disLast = builder.call(
                    arithOp,
                    a1,
                    builder.field(n - 1)
            );
            proj.add(disLast);


            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(arithExpr2.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
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



    @Override
    public OCLElement visitOclObjectRole(STOCLParser.OclObjectRoleContext ctx) {


        OCLObj obj = (OCLObj) visit(ctx.oclObj());
        String role = ctx.role().getText();

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

        RelNode res = builder.project(proj) 
                .build();

        if(obj.getRefSet().isSingleSet()){
            return new OCLObj(new OCLBag(res, assoEndClassName, PrimitiveType.OBJECT, OCLBag.SetType.SINGLE_SET, null)); 
        } else {
            return new OCLObj(new OCLBag(res, assoEndClassName, PrimitiveType.OBJECT, OCLBag.SetType.POWER_SET, obj.getRefSet().getGroupKeys())); 
        }

    }





    @Override
    public OCLElement visitOclObjectVar(STOCLParser.OclObjectVarContext ctx) {
        Var var = (Var) visit(ctx.var());
        OCLBag set = this.varEnv.resolve(var.getVarName());
        return new OCLObj(set);

    }



 
    @Override
    public OCLElement visitOclObjectSelf(STOCLParser.OclObjectSelfContext ctx) {
        OCLBag set = this.varEnv.resolve("self");
        return new OCLObj(set);
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
        } else {
            throw new RuntimeException("Attribute " + attr + " of class " + obj.getClassName() +
                    " is not of type Integer, Real or String.");
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
        } else {
            throw new RuntimeException(" Variable " + var.getVarName() + " must be of type Integer, Real or String.");
        }

    }



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



            builder.push(joined);
            List<RexNode> proj = new ArrayList<>();
            for (String key : lfields) proj.add(builder.field(key));

            RexNode rex = builder.call(
                    strOp,
                    builder.field(ln-1), 
                    builder.field(ln+rn-1) 
            );

            proj.add(rex);

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
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));


            RexNode s2 = strValue2.getLiteral();

            RexNode disLast = builder.call(
                    strOp,
                    builder.field(n - 1),
                    s2
            );
            proj.add(disLast);

            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue1.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
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
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode s1 = strValue1.getLiteral();

            RexNode disLast = builder.call(
                    strOp,
                    s1,
                    builder.field(n - 1)
            );
            proj.add(disLast);


            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue2.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
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
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode rex = builder.call(
                    strOp,
                    builder.field(n-1),
                    builder.literal(intValue1),
                    builder.literal(intValue2-intValue1)
            );
            proj.add(rex);

            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue.getRefSet().getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
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
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode rex = builder.call(
                    strOp,
                    builder.field(n-1)
            );
            proj.add(rex);

            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue.getRefSet().getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
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
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode rex = builder.call(
                    strOp,
                    builder.field(n-1)
            );
            proj.add(rex);

            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue.getRefSet().getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
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
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));

            RexNode rex = builder.call(
                    strOp,
                    builder.field(n-1),
                    builder.literal(1)
            );
            proj.add(rex);

            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(strValue.getRefSet().getRelNode().getRowType().getFieldNames().get(i));
            alias.add(opStr);

            res = builder.project(proj, alias)
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








    @Override
    public OCLElement visitLiteralInt(STOCLParser.LiteralIntContext ctx) {
        int literal = Integer.valueOf(ctx.INT_LITERAL().getText());
        return new Literal(builder.literal(literal));

    }




    @Override
    public OCLElement visitLiteralReal(STOCLParser.LiteralRealContext ctx) {
        double literal = Double.valueOf(ctx.REAL_LITERAL().getText());
        return new Literal(builder.literal(literal));
    }




    @Override
    public OCLElement visitLiteralString(STOCLParser.LiteralStringContext ctx) {
        String raw = ctx.STRING_LITERAL().getText();
        String val = raw.substring(1, raw.length() - 1);
        return new Literal(builder.literal(val));
    }





    @Override
    public OCLElement visitLiteralBoolean(STOCLParser.LiteralBooleanContext ctx) {
        boolean literal = Boolean.valueOf(ctx.BOOLEAN_LITERAL().getText());
        return new Literal(builder.literal(literal));
    }




    @Override
    public OCLElement visitVarListValue(STOCLParser.VarListValueContext ctx) {
        List<Var> vars = ctx.var().stream()
                .map(v -> (Var) visit(v))
                .collect(Collectors.toList());
        return new VarList(vars);
    }


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
            if (!new HashSet<>(left.getRefSet().getGroupKeys()).equals(new HashSet<>(right.getRefSet().getGroupKeys()))){
                throw new RuntimeException(" Both sides of arithmetic operation must have the same group keys.");
            }


            builder.push(left.getRelNode())
                .push(right.getRelNode());


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


            builder.push(joined);
            List<RexNode> proj = new ArrayList<>();
            for (String key : left.getRefSet().getGroupKeys()) proj.add(builder.field(key));

            RexNode sumLast = builder.call(
                    binaryOperatorMap.get(op),
                    builder.field(leftLastCol),
                    builder.field(rightLastCol)
            );
            proj.add(sumLast);

            List<String> alias = new ArrayList<>(left.getRefSet().getGroupKeys());
            alias.add(leftLastCol + op + rightLastCol);

            res = builder.project(proj, alias)
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
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));



            RexNode sumLast = builder.call(
                    binaryOperatorMap.get(op),
                    builder.field(n - 1),
                    right.getRexNode()
            );
            proj.add(sumLast);

            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(left.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(left.getRelNode().getRowType().getFieldNames().get(n-1) + op + right.getRexNode().toString());


            res = builder.project(proj, alias)
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
            List<RexNode> proj = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) proj.add(builder.field(i));


            RexNode sumLast = builder.call(
                    binaryOperatorMap.get(op),
                    left.getRexNode(),
                    builder.field(n - 1)
            );
            proj.add(sumLast);


            List<String> alias = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) alias.add(right.getRelNode().getRowType().getFieldNames().get(i));
            alias.add(right.getRelNode().getRowType().getFieldNames().get(n-1) + op + left.getRexNode().toString());



            res = builder.project(proj)
                        .build();

            return new ArithExpr(new OCLBag(res,
                                            right.getRefSet().getClassName(), 
                                            PrimitiveType.REAL, 
                                            right.getRefSet().getSetType(), 
                                            right.getRefSet().getGroupKeys()));

        } else {



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
