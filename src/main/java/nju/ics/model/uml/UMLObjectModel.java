package nju.ics.model.uml;



import java.io.FileOutputStream;
import java.sql.Timestamp;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.dialect.MysqlSqlDialect;
import org.apache.calcite.sql.dialect.OracleSqlDialect;
import org.apache.calcite.sql.dialect.PostgresqlSqlDialect;
import org.apache.calcite.sql.dialect.SqliteSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;

import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;

import com.alibaba.fastjson2.JSONArray;

import nju.ics.grammar.stocl.PrimitiveType;

import org.apache.tinkerpop.gremlin.structure.Vertex;

import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONIo;
import org.apache.tinkerpop.gremlin.structure.io.graphson.GraphSONWriter;



public class UMLObjectModel {

    String name;
    UMLClassDiagram cd;

    Graph graph;
    GraphTraversalSource g;





    public UMLObjectModel(UMLClassDiagram cd) {
        this.name = cd.getName();
        this.cd = cd;
        this.graph = TinkerGraph.open();
        this.g = graph.traversal();
    }



    public List<UMLObject> getLinkedObjects(UMLObject sourceObject, String link){
        List<Vertex> list = g.V(sourceObject.getNode().id()).out(link).toList();
        return list.stream()
                    .map(v -> new UMLObject(v))
                    .collect(Collectors.toList());
    }







    public static <T> T createLiteral(String value, PrimitiveType type, Class<T> clazz) {
        try {
            Object parsed = switch (type) {
                            case INTEGER -> Integer.valueOf(value);
                            case REAL -> Double.valueOf(value);
                            case BOOLEAN -> Boolean.valueOf(value);
                            case STRING -> value;
                            case GEOMETRY -> new WKTReader().read(value);
                            case TIMESTAMP -> Timestamp.valueOf(value);
                            default -> throw new IllegalArgumentException("Unexpected value. ");
            };
            return clazz.cast(parsed);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse " + value, e);
        }
    }


    public void addObject(String clsType, Map<String, String> properties) {

        if (!cd.hasClass(clsType)) {
            throw new RuntimeException("Class " + clsType + " does not exist.");
        }


        try {

            GraphTraversal<Vertex, Vertex> tempVertex = g.addV(clsType);

            for (String key : properties.keySet()) {
                if ((UMLClassDiagram.getObjectIDColumn(clsType)).equals(key)) {
                    tempVertex.property(key, Integer.parseInt(properties.get(key)));
                } else {
                    if (!cd.hasAttr(clsType, key)) {
                        throw new RuntimeException("Attr does not exist.");
                    }

                    PrimitiveType propertyType = cd.getAttrType(clsType, key);

                    tempVertex.property(key, createLiteral(properties.get(key), 
                                                            propertyType,
                                                            propertyType.getTypeClass()));
    
                }
    
            }

            tempVertex.next();
    

        } catch (Exception e) {
            System.out.println(e);
        }
        


    }



    public void addLink(String sourceType, int sid, String targetType, int tid) {

        if (!cd.hasClass(sourceType)) {
            throw new RuntimeException("Class " + sourceType + " does not exist.");
        }

        if (!cd.hasClass(targetType)) {
            throw new RuntimeException("Class " + targetType + " does not exist.");
        }

        String role = cd.getRole(sourceType, targetType);
        String r_role = cd.getRRole(sourceType, targetType);

        Vertex source = g.V().hasLabel(sourceType).has(UMLClassDiagram.getObjectIDColumn(sourceType), sid).next();
        Vertex target = g.V().hasLabel(targetType).has(UMLClassDiagram.getObjectIDColumn(targetType), tid).next();

        source.addEdge(role, target);
        target.addEdge(r_role, source);
                
    }




    public void addObjects(String clsType, JSONArray objects){

        if (!cd.hasClass(clsType)) {
            throw new RuntimeException("Class " + clsType + " does not exist.");
        }

        for (int i = 0; i < objects.size(); i++) {
            Map<String, String> properties = objects.getJSONObject(i)
                                                        .entrySet()
                                                        .stream()
                                                        .collect(Collectors.toMap(
                                                            Map.Entry::getKey,
                                                            e -> e.getValue().toString()
                                                        ));
            String value = properties.remove("id");
            properties.put(UMLClassDiagram.getObjectIDColumn(clsType), value);
            addObject(clsType, properties);
        }


    }



    public void addLinks(String assoClass, JSONArray links){

        String sourceType = cd.getAssoClasse(assoClass).getStartClass();
        String targetType = cd.getAssoClasse(assoClass).getEndClass();

        for (int i = 0; i < links.size(); i++) {
            Map<String, Integer> link = links.getJSONObject(i)
                                                .entrySet()
                                                .stream()
                                                .collect(Collectors.toMap(
                                                    Map.Entry::getKey,
                                                    e -> Integer.parseInt(e.getValue().toString())
                                                ));
            int sid = link.get("sid");
            int tid = link.get("eid");
            addLink(sourceType, sid, targetType, tid);
        }
        
    }




    public List<UMLObject> getObjects(String clsType){
        return g.V().hasLabel(clsType)
                    .toList()
                    .stream()
                    .map(f->new UMLObject(f))
                    .collect(Collectors.toList());

    }





    public void printObjects(){
        System.out.println("=== All Objects ===");
        g.V().elementMap().forEachRemaining(System.out::println);

        System.out.println("=== All Links ===");
        g.E().elementMap().forEachRemaining(System.out::println);
    }


    public void saveObjects(String filePath){


        for (String cls : cd.getClasses().keySet()) {
            for (String attr : cd.getClasses().get(cls).getAttrList()) {
                PrimitiveType type = cd.getAttrType(cls, attr);
                if (type == PrimitiveType.GEOMETRY) {
                    List<Vertex> list = g.V().hasLabel(cls)
                                        .toList();
                    try {
                        for (Vertex v : list) {
                            Geometry geom = (Geometry) v.property(attr).value();
                            v.property(attr, geom.toText()); // 替换成 WKT 字符串
                        }
                    } catch (Exception e){
                        throw new RuntimeException("Error converting Geometry to WKT.");
                    }


                }


                if (type == PrimitiveType.TIMESTAMP) {
                    List<Vertex> list = g.V().hasLabel(cls)
                                        .toList();
                    try {
                        for (Vertex v : list) {
                            Timestamp timestamp = (Timestamp) v.property(attr).value();
                            v.property(attr, timestamp.toString()); // 替换成 timestamp 字符串
                        }
                    } catch (Exception e){
                        throw new RuntimeException("Error converting Timestamp to String.");
                    }
                }
            }
        }

        GraphSONWriter writer = graph.io(GraphSONIo.build()).writer().create();

        try (FileOutputStream out = new FileOutputStream(filePath)) {
            writer.writeGraph(out, graph);
        } catch (Exception e) {
            throw new RuntimeException("Can not save graph.");
        }

        System.out.println("Graph exported to " + filePath);
    }
    




    public void readObjects(String filePath){




        this.graph = TinkerGraph.open();
        this.g = graph.traversal();

        g.io(filePath).read().iterate();



        for (String cls : cd.getClasses().keySet()) {
            for (String attr : cd.getClasses().get(cls).getAttrList()) {
                PrimitiveType type = cd.getAttrType(cls, attr);
                if (type == PrimitiveType.GEOMETRY) {
                    List<Vertex> list = g.V().hasLabel(cls)
                                        .toList();
                    for (Vertex v : list) {
                        try {
                            String val = (String) v.property(attr).value();
                            Geometry geom = createLiteral(val, PrimitiveType.GEOMETRY, Geometry.class);
                            v.property(attr, geom);
                        } catch (Exception e) {
                            throw new RuntimeException("Error converting WKT to Geometry.");
                        }

                    }
                }

                if (type == PrimitiveType.TIMESTAMP) {
                    List<Vertex> list = g.V().hasLabel(cls)
                                        .toList();
                    for (Vertex v : list) {
                        try {
                            String val = (String) v.property(attr).value();
                            Timestamp timestamp = createLiteral(val, PrimitiveType.TIMESTAMP, Timestamp.class);
                            v.property(attr, timestamp);
                        } catch (Exception e) {
                            throw new RuntimeException("Error converting String to Timestamp.");
                        }

                    }
                }
            }
        }


        System.out.println("Graph read from " + filePath);
    }




    public String getDataDML(String sqlDialect){

        String schema = this.name;

        StringBuilder dmlBuilder = new StringBuilder();

        
        Set<String> classes = cd.getClasses().keySet();

        for (String cls : classes) {
            List<Vertex> objs = g.V().hasLabel(cls).toList();
            for (Vertex obj : objs) {
                StringBuilder insertBuilder = new StringBuilder();
                insertBuilder.append("INSERT INTO ").append(schema).append(".").append(cls).append(" (");

                List<String> columns = cd.getClasses().get(cls).getAttrList();

                String idCol = UMLClassDiagram.getObjectIDColumn(cls);
                insertBuilder.append(idCol).append(", ");
                insertBuilder.append(String.join(", ", columns));
                insertBuilder.append(") VALUES (");

                insertBuilder.append(obj.property(idCol).value().toString()).append(", ");
                List<String> values = columns.stream().map(attr -> {
                    PrimitiveType type = cd.getAttrType(cls, attr);
                    Object val = obj.property(attr).value();
                    if (val == null) {
                        return "NULL";
                    }
                    return switch (type) {
                        case INTEGER, REAL, BOOLEAN -> val.toString();
                        case STRING -> "'" + val.toString().replace("'", "''") + "'";
                        case GEOMETRY -> "ST_GeomFromText('" + ((Geometry) val).toText() + "')";
                        case TIMESTAMP -> "'" + val.toString() + "'";
                        default -> throw new IllegalArgumentException("Unexpected value: " + type);
                    };
                }).collect(Collectors.toList());

                insertBuilder.append(String.join(", ", values));
                insertBuilder.append(");");

                dmlBuilder.append(insertBuilder.toString()).append("\n");
            }
        }


        Set<String> assoes = cd.getAssoClasses().keySet();
        for (String asso : assoes) {
            String sourceType = cd.getAssoClasse(asso).getStartClass();
            String targetType = cd.getAssoClasse(asso).getEndClass();
            String role = cd.getRole(sourceType, targetType);

            String sourceIdCol = UMLClassDiagram.getObjectIDColumn(sourceType);
            String targetIdCol = UMLClassDiagram.getObjectIDColumn(targetType);

            List<Vertex> sourceObjs = g.V().hasLabel(sourceType).toList();
            for (Vertex sourceObj : sourceObjs) {
                List<Vertex> linkedTargets = g.V(sourceObj.id()).out(role).toList();
                for (Vertex targetObj : linkedTargets) {
                    StringBuilder insertBuilder = new StringBuilder();
                    insertBuilder.append("INSERT INTO ").append(schema).append(".").append(asso).append(" (");

                    insertBuilder.append(sourceIdCol).append(", ").append(targetIdCol);
                    insertBuilder.append(") VALUES (");

                    insertBuilder.append(sourceObj.property(sourceIdCol).value().toString()).append(", ");
                    insertBuilder.append(targetObj.property(targetIdCol).value().toString());
                    insertBuilder.append(");");

                    dmlBuilder.append(insertBuilder.toString()).append("\n");
                }
            }
        }



        StringBuilder results = new StringBuilder();

        try {
            String sql = dmlBuilder.toString();



            SqlParser.Config config = SqlParser.config()
                                                .withParserFactory(org.apache.calcite.server.ServerDdlExecutor.PARSER_FACTORY)

                                                .withLex(Lex.MYSQL_ANSI) 
                                                .withCaseSensitive(true);
            SqlParser parser = SqlParser.create(sql, config);
            SqlNodeList node = parser.parseStmtList();
            for (SqlNode n : node.getList()){
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



}
