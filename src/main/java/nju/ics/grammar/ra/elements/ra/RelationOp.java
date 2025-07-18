package nju.ics.grammar.ra.elements.ra;

public class RelationOp extends Query{

    boolean isJoin;
    boolean hasCondition;
    String joinCondition;
    String relationOp;


    public RelationOp(boolean isJoin) {
        super();
        this.isJoin = isJoin;
        this.hasCondition = false;
        this.joinCondition = null;
        this.relationOp = null;
    }

    public RelationOp(boolean isJoin, String joinCondition) {
        super();
        this.isJoin = isJoin;
        this.hasCondition = true;
        this.joinCondition = joinCondition;
    }


    public RelationOp(String relationOp) {
        super();
        this.isJoin = false;
        this.hasCondition = false;
        this.joinCondition = null;
        this.relationOp = relationOp;
    }


    public boolean isJoin() {
        return isJoin;
    }

    public boolean hasCondition() {
        return hasCondition;
    }

    public String getJoinCondition() {
        return joinCondition;
    }

    public String getRelationOp() {
        return relationOp;
    }

}
