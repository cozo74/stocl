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


    
    // 实例字段
    private final Class<?> typeClass;
    
    // 构造函数
    PrimitiveType(Class<?> typeClass) {
        this.typeClass = typeClass;
    }
    
    // 获取方法
    public Class<?> getTypeClass() {
        return typeClass;
    }



    public static PrimitiveType fromClass(Class<?> clazz) {
        for (PrimitiveType pt : values()) {
            if (pt.getTypeClass().equals(clazz)) {
                return pt;
            }
        }
        return null; // 或者抛异常
    }


}


