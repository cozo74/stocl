package nju.ics.model.ra;

import org.antlr.v4.runtime.misc.Pair;

import java.util.List;
import java.util.ArrayList;

public class Relation {

    String name;

    List<Pair<String, String> > attributes;


    public Relation(String name, List<Pair<String, String>> attributes) {
        this.name = name;
        this.attributes = attributes;
    }


    public String getName() {
        return name;
    }


    public List<Pair<String, String>> getAttributes() {
        return attributes;
    }


    public boolean isAttrExist(String attrName) {
        for (Pair<String, String> attr : attributes) {
            if (attr.a.equals(attrName)) {
                return true;
            }
        }
        return false;
    }


    public String getAttrType(String attrName) {
        for (Pair<String, String> attr : attributes) {
            if (attr.a.equals(attrName)) {
                return attr.b;
            }
        }
        return null;
    }


    public List<String> getAttrName() {

        List<String> attrNames = new ArrayList<>();
        for (Pair<String, String> attr : attributes) {
            attrNames.add(attr.a);
        }
        return attrNames;
    }

}
