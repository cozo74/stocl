package nju.ics.grammar.oclcore.elements;

public class OCLStringValue extends OCLElement{

        final OCLStringValueType valueType;

        final OCLSet oclSet;
        final String literal;

        public OCLStringValue(OCLStringValueType valueType, OCLSet oclSet, String literal, String RAExpr) {
            super(RAExpr);
            this.valueType = valueType;
            this.oclSet = oclSet;
            this.literal = literal;
        }


        public OCLStringValueType getValueType() {
            return valueType;
        }

        public OCLSet getOclSet() {
            return oclSet;
        }

        public String getLiteral() {
            return literal;
        }


}
