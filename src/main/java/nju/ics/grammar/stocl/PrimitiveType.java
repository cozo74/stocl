package nju.ics.grammar.stocl;

import java.sql.Timestamp;

import org.locationtech.jts.geom.Geometry;

import nju.ics.model.uml.UMLObject;

public enum PrimitiveType{


    OBJECT(UMLObject.class), 
    INTEGER(Integer.class),
    REAL(Double.class),
    BOOLEAN(Boolean.class),
    STRING(String.class),
    GEOMETRY(Geometry.class),
    TIMESTAMP(Timestamp.class);


    

    private final Class<?> typeClass;
    

    PrimitiveType(Class<?> typeClass) {
        this.typeClass = typeClass;
    }
    

    public Class<?> getTypeClass() {
        return typeClass;
    }



    public static PrimitiveType fromClass(Class<?> clazz) {
        for (PrimitiveType pt : values()) {
            if (pt.getTypeClass().equals(clazz)) {
                return pt;
            }
        }
        return null;
    }


}


