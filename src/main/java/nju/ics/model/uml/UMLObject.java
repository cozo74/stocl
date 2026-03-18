package nju.ics.model.uml;


import java.util.Objects;
import org.apache.tinkerpop.gremlin.structure.Vertex;





public class UMLObject {
    
    final String clsType;
    final int oid;
    final Vertex node;


    public UMLObject(Vertex node) {
        this.node = node;
        this.clsType = node.label();
        this.oid = (int) node.property(UMLClassDiagram.getObjectIDColumn(clsType)).value();
    }
    

    public String getClassType(){
        return this.clsType;
    }

    public int getObjectId(){
        return this.oid;
    }

    public Object getAttrValue(String attr){
        return this.node.property(attr).value();
    }


    public Vertex getNode(){
        return this.node;
    }




    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        UMLObject other = (UMLObject) obj;


        return this.clsType == other.getClassType() && this.oid == other.getObjectId();
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.clsType, this.oid);
    }
}
