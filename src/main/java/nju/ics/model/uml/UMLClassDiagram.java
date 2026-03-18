package nju.ics.model.uml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import nju.ics.grammar.stocl.PrimitiveType;

import org.antlr.v4.runtime.misc.Pair;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.dialect.MysqlSqlDialect;
import org.apache.calcite.sql.dialect.OracleSqlDialect;
import org.apache.calcite.sql.dialect.PostgresqlSqlDialect;
import org.apache.calcite.sql.dialect.SqliteSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;




public class UMLClassDiagram {

    String name;
    Map<String, UMLClass> classes = new HashMap<>();
    Map<String, UMLAssoClass> assoClasses = new HashMap<>();

    // startClass    role    assoClass    endClass
    Map<String, Map<String, Pair<String, String>>> classRoleMap = new HashMap<>();


    public UMLClassDiagram(String jsonFilePath) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            JSONObject jsonObject = JSON.parseObject(jsonContent);

            // read class_diagram name
            String clsName = jsonObject.getString("class_diagram");

            // read classes
            JSONArray classes1 = jsonObject.getJSONArray("classes");

            // read associations
            JSONArray associations1 = jsonObject.getJSONArray("associations");

            parseClassAndAsso(clsName, classes1, associations1);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public UMLClassDiagram(String clsName, JSONArray classes1, JSONArray associations1) {
        parseClassAndAsso(clsName, classes1, associations1);
    }


    private void parseClassAndAsso(String clsName, JSONArray classes1, JSONArray associations1) {

        this.name = clsName;
        classes1.forEach(classObj -> {
            JSONObject classJson = (JSONObject) classObj;
            String className = classJson.getString("name");
            Map<String, String> attrMap = new HashMap<>();
            List<String> attrList = new ArrayList<>();
            // 更换Map，Map会改变顺序，使intersection等操作结果不稳定
            classJson.getJSONArray("attributes").forEach(attrObj -> {
                JSONObject attrJson = (JSONObject) attrObj;
                attrMap.put(attrJson.getString("attr"), attrJson.getString("type"));
                attrList.add(attrJson.getString("attr"));
            });
            this.classes.put(className, new UMLClass(className, attrList, attrMap));
        });


        associations1.forEach(assoClassObj -> {
            JSONObject assoClassJson = (JSONObject) assoClassObj;
            String assoClassName = assoClassJson.getString("name");
            String startClass = assoClassJson.getString("startClass");
            String endClass = assoClassJson.getString("endClass");
            String role = assoClassJson.getString("role");
            String r_Role = assoClassJson.getString("r_role");
            String multi = assoClassJson.getString("multi");
            String r_multi = assoClassJson.getString("r_multi");

            if(assoClassJson.getJSONArray("attributes") != null) {
                List<String> attrList = new ArrayList<>();
                Map<String, String> attrMap = new HashMap<>();
                assoClassJson.getJSONArray("attributes").forEach(attrObj -> {
                    JSONObject attrJson = (JSONObject) attrObj;
                    attrMap.put(attrJson.getString("attr"), attrJson.getString("type"));

                });
                this.assoClasses.put(assoClassName, new UMLAssoClass(assoClassName, startClass, endClass, role, r_Role, multi, r_multi, true, attrList, attrMap));
            } else {
                this.assoClasses.put(assoClassName, new UMLAssoClass(assoClassName, startClass, endClass, role, r_Role, multi, r_multi));
            }
        });




        for( String key : assoClasses.keySet()) {
            UMLAssoClass assoClass = assoClasses.get(key);
            String startClass = assoClass.getStartClass();
            String endClass = assoClass.getEndClass();

            if(!classRoleMap.containsKey(startClass)) {
                classRoleMap.put(startClass, new HashMap<>());
            }
            classRoleMap.get(startClass).put(assoClass.getRole(), new Pair<>(assoClass.getAssoClassName(), endClass));


            if(!classRoleMap.containsKey(endClass)) {
                classRoleMap.put(endClass, new HashMap<>());
            }
            classRoleMap.get(endClass).put(assoClass.getR_role(), new Pair<>(assoClass.getAssoClassName(), startClass));
        }
    }




    public String getName() {
        return name;
    }

    public Map<String, UMLClass> getClasses() {
        return classes;
    }

    public Map<String, UMLAssoClass> getAssoClasses() {
        return assoClasses;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setClasses(Map<String, UMLClass> classes) {
        this.classes = classes;
    }

    public void setAssoClasses(Map<String, UMLAssoClass> assoClasses) {
        this.assoClasses = assoClasses;
    }


    public boolean hasClass(String className) {
        return classes.containsKey(className);
    }

    public UMLAssoClass getAssoClasse(String assoClassName) {
        return assoClasses.get(assoClassName);
    }



    public PrimitiveType getAttrType(String className, String attrName) {


        if (!classes.containsKey(className)) {
            throw new RuntimeException("Class " + className + " does not exist in the UML Class Diagram.");
        }

        if (!classes.get(className).hasAttr(attrName)) {
            throw new RuntimeException("Attribute " + attrName + " does not exist in class " + className);
        }

        if (!classes.get(className).isAttrTypeLegal(attrName)) {
            throw new RuntimeException("Attribute " + attrName + " has illegal type in class " + className);
        }


        return classes.get(className).getAttrType(attrName);
    }




    public String getAssoClassWithRole(String startClass, String role) {
        return classRoleMap.get(startClass).get(role).a;
    }


    public String getAssoEndClassWithRole(String startClass, String role) {
        return classRoleMap.get(startClass).get(role).b;
    }

    public Pair<String, String> getAssoClassAndAssoEndClassWithRole(String startClass, String role) {
        return classRoleMap.get(startClass).get(role);
    }


    public boolean hasRole(String className, String role) {
        return classRoleMap.get(className).containsKey(role);
    }

    public boolean hasAttr(String className, String attr) {
        return classes.get(className).hasAttr(attr);
    }



    public boolean isSingleRole(String className, String role){

        if (!hasRole(className, role)) {
            throw new RuntimeException("Class " + className + " does not have role " + role);
        }

        UMLAssoClass umlAssoClass = assoClasses.get(getAssoClassWithRole(className, role));
        return umlAssoClass.isSingleRole(role);
    }


    public String getRole(String sourceClass, String targetClass){

        String role = null;

        Map<String, Pair<String, String>> roleMap = classRoleMap.get(sourceClass);
        for (String r : roleMap.keySet()){
            if (roleMap.get(r).b.equals(targetClass)){
                role = r;
            }
        }

        if (role==null){
            throw new RuntimeException("Association dose not exist.");
        }

        return role;

    }



    public String getRRole(String sourceClass, String targetClass){

        String r_role = null;

        Map<String, Pair<String, String>> roleMap = classRoleMap.get(targetClass);
        for (String r : roleMap.keySet()){
            if (roleMap.get(r).b.equals(sourceClass)){
                r_role = r;
            }
        }

        if (r_role==null){
            throw new RuntimeException("Association dose not exist.");
        }

        return r_role;

    }


    public static String getObjectIDColumn(String className) {
        return className.toLowerCase() + "_id";
    }



    public String getSchemaDDL(String sqlDialect) {


        String schema = this.name;

        StringBuilder ddlBuilder = new StringBuilder();

        ddlBuilder.append("-- DDL for UML Class Diagram: ").append(this.name).append("\n\n");
        ddlBuilder.append("CREATE SCHEMA IF NOT EXISTS ").append(schema).append(";\n\n");
        // Generate DDL for classes
        for (UMLClass umlClass : classes.values()) {
            ddlBuilder.append("CREATE TABLE ").append(schema + "." + umlClass.getClassName()).append(" (\n");
            ddlBuilder.append("    ").append(getObjectIDColumn(umlClass.getClassName())).append(" INTEGER,\n");
            for (String attrName : umlClass.getAttrList()) {
                ddlBuilder.append("    ").append(attrName).append(" ");
                String attrTypeStr;
                switch (umlClass.getAttrTypeString(attrName)) {
                    case "Integer":
                        attrTypeStr = "INTEGER";
                        break;
                    case "Real":
                        attrTypeStr = "DOUBLE";
                        break;
                    case "String":
                        attrTypeStr = "VARCHAR(255)";
                        break;
                    case "Boolean":
                        attrTypeStr = "BOOLEAN";
                        break;
                    case "Geometry":
                        attrTypeStr = "GEOMETRY";
                        break;
                    case "Timestamp":
                        attrTypeStr = "TIMESTAMP";
                        break;
                    default:
                        throw new RuntimeException("Unsupported attribute type: " + umlClass.getAttrTypeString(attrName));
                }
                ddlBuilder.append(attrTypeStr).append(",\n");

            }

            // Remove the last comma and newline

            ddlBuilder.append("    PRIMARY KEY (").append(getObjectIDColumn(umlClass.getClassName())).append(")\n");
            ddlBuilder.append(");\n\n");




        }

        // Generate DDL for association classes
        for (UMLAssoClass umlAssoClass : assoClasses.values()) {
            ddlBuilder.append("CREATE TABLE ").append(schema + "." + umlAssoClass.getAssoClassName()).append(" (\n");
            ddlBuilder.append("    ").append(getObjectIDColumn(umlAssoClass.getStartClass())).append(" INTEGER,\n");
            ddlBuilder.append("    ").append(getObjectIDColumn(umlAssoClass.getEndClass())).append(" INTEGER\n");
            ddlBuilder.append(");\n\n");
        }


        StringBuilder results = new StringBuilder();

        try {
            String sql = ddlBuilder.toString();


            SqlParser.Config config = SqlParser.config()
                                                .withParserFactory(org.apache.calcite.server.ServerDdlExecutor.PARSER_FACTORY)
                                                .withLex(Lex.MYSQL_ANSI) 
                                                .withCaseSensitive(true);
            SqlParser parser = SqlParser.create(sql, config);
            SqlNodeList node = parser.parseStmtList();
            for (SqlNode n : node.getList()){
//                System.out.println(n.toString());
                if (sqlDialect.equals("MySQL")){
                    results.append(n.toSqlString(MysqlSqlDialect.DEFAULT).getSql()).append(";\n");
                } else if (sqlDialect.equals("PostgreSQL")){
                    results.append(n.toSqlString(PostgresqlSqlDialect.DEFAULT).getSql()).append(";\n");
                } else if (sqlDialect.equals("SQLite")){
                    results.append(n.toSqlString(SqliteSqlDialect.DEFAULT).getSql()).append(";\n");
                } else if (sqlDialect.equals("Oracle")){
                    results.append(n.toSqlString(OracleSqlDialect.DEFAULT).getSql()).append(";\n");
                } else {
                    throw new RuntimeException("Unsupported SQL dialect: " + sqlDialect);
                }


            }


        } catch (SqlParseException e) {
            e.printStackTrace();
        }

        return results.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UMLClassDiagram that = (UMLClassDiagram) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }


    @Override
    public String toString() {
        return "UMLClassDiagram{" +
                "name='" + name + '\'' +
                '}';
    }
}
