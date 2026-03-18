package nju.ics.model.schema;


import nju.ics.model.uml.UMLClassDiagram;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;


import java.util.Map;

public class UMLSchemaFactory implements SchemaFactory {


    UMLClassDiagram cd;


    public UMLSchemaFactory(UMLClassDiagram cd) {
        this.cd = cd;

    }



    @Override
    public Schema create(SchemaPlus parentSchema, String name, Map<String, Object> operand) {
        return new UMLSchema(cd);
    }
}
