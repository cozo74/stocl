package nju.ics.grammar.translator.elements;

import org.apache.calcite.rex.RexLiteral;
import org.apache.calcite.sql.type.SqlTypeName;


public class Literal extends OCLElement{

    RexLiteral value; 


    public Literal(RexLiteral value) {
        super(null);
        this.value = value;
    }


    public RexLiteral getValue(){
        return this.value;
    }



    public String getValueString(){
        return this.value.getValue2().toString();
    }



    public String getTypeString(){
        return value.getTypeName().toString();
    }

    public SqlTypeName getType(){
        return this.value.getTypeName();
    }




}



