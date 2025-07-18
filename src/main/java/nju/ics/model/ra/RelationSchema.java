package nju.ics.model.ra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nju.ics.model.uml.UMLClassDiagram;

public class RelationSchema {

    String name;

    Map<String, Relation> relations;

    public RelationSchema(String name, Map<String, Relation> relations) {
        this.name = name;
        this.relations = relations;
    }


    public RelationSchema(UMLClassDiagram classDiagram) {
        RelationSchema relationSchema = classDiagram.toRelationSchema();
        this.name = relationSchema.name;
        this.relations = relationSchema.relations;

    }


    public List<String> getRelationAttributes(String relationName) {

        Relation relation = relations.get(relationName);
        if (relation == null) {
            return new ArrayList<>();
        } else {
            return relation.getAttrName();
        }


    }

}
