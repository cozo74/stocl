package nju.ics.model.schema;

import nju.ics.model.uml.UMLAssoClass;
import nju.ics.model.uml.UMLClass;
import nju.ics.model.uml.UMLClassDiagram;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UMLSchema extends AbstractSchema {
    private String name;
    private List<UMLTable> tables;

    public UMLSchema(String name, List<UMLTable> tables) {
        this.name = name;
        this.tables = tables;
    }



    public UMLSchema(UMLClassDiagram cd) {

        this.name = cd.getName();
        this.tables = new ArrayList<>();
        Map<String, UMLClass> classMap = cd.getClasses();
        for (String clsName : classMap.keySet()) {
            UMLClass cls = classMap.get(clsName);

            List<UMLColumn> columns = new ArrayList<>();
            columns.add(new UMLColumn(UMLClassDiagram.getObjectIDColumn(clsName), "INTEGER")); // add primary key column
            for (var attr : cls.getAttrList()) {
                String attrName = attr;
                String attrType = cls.getAttrMap().get(attr);
                columns.add(new UMLColumn(attrName, attrType));
            }
            UMLTable table = new UMLTable(cls.getClassName(), columns);
            tables.add(table);
        }


        Map<String, UMLAssoClass> assoClasses = cd.getAssoClasses();
        for (String assoName : assoClasses.keySet()) {
            UMLAssoClass assocCls = assoClasses.get(assoName);

            List<UMLColumn> columns = new ArrayList<>();
            columns.add(new UMLColumn(UMLClassDiagram.getObjectIDColumn(assocCls.getStartClass()), "INTEGER")); // add primary key column
            columns.add(new UMLColumn(UMLClassDiagram.getObjectIDColumn(assocCls.getEndClass()), "INTEGER")); // add primary key column

            UMLTable table = new UMLTable(assocCls.getAssoClassName(), columns);
            tables.add(table);
        }




    }





    @Override
    protected Map<String, Table> getTableMap() {
        return tables.stream().collect(
                    Collectors.toMap(UMLTable::getName, table -> table)
            );
    }


}
