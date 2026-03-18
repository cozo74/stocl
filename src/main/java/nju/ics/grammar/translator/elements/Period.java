package nju.ics.grammar.translator.elements;


public class Period extends OCLElement{


    OCLTimestamp leftTimestamp, rightTimestamp;
    boolean isLeftClosed, isRightClosed;

    public Period(OCLTimestamp lt, OCLTimestamp rt, boolean isLeftClosed, boolean isRightClosed) {
        super(null);
        this.leftTimestamp = lt;
        this.rightTimestamp = rt;
        this.isLeftClosed = isLeftClosed;
        this.isRightClosed = isRightClosed;

    }



    public boolean isLeftClosed(){
        return this.isLeftClosed;
    }


    public boolean isRightClosed(){
        return this.isRightClosed;
    }


    public OCLTimestamp getLeftTimestamp(){
        return leftTimestamp;
    }


    public OCLTimestamp getRightTimestamp(){
        return rightTimestamp;
    }


}
