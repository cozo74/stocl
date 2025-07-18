package nju.ics.grammar.utils;

import java.util.List;

public class Utils {

    static public String reverseComOp(String op) {
        switch (op) {
            case "=":
                return "<>";
            case "<>":
                return "=";
            case "<":
                return ">=";
            case "<=":
                return ">";
            case ">":
                return "<=";
            case ">=":
                return "<";
            default:
                return null;
        }
    }

    static public String switchComOp(String op) {
        switch (op) {
            case "=":
                return "=";
            case "<>":
                return "<>";
            case "<":
                return ">";
            case "<=":
                return ">=";
            case ">":
                return "<";
            case ">=":
                return "<=";
            default:
                return null;
        }
    }


    static public StringBuilder concateAttrs(List<String> attrList, boolean containLastAttr) {
        StringBuilder sb = new StringBuilder();

        if (attrList.isEmpty()) {
            return sb;
        } else if(attrList.size() == 1) {
            if (containLastAttr) {
                sb.append(attrList.get(0));
            }
            return sb;
        } else {
            for (int i = 0; i < attrList.size()-1; i++) {
                sb.append(attrList.get(i));
                if (i != attrList.size() - 2) {
                    sb.append(", ");
                }
            }
            if (containLastAttr) {
                sb.append(", ").append(attrList.get(attrList.size()-1));
            }
            return sb;


        }


    }



}
