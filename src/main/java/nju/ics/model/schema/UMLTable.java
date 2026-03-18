package nju.ics.model.schema;

import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.Statistic;
import org.apache.calcite.schema.Statistics;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.util.ImmutableBitSet;
import java.util.ArrayList;
import java.util.List;

public class UMLTable extends AbstractTable {

    private String name;
    private List<UMLColumn> columns;


    public UMLTable(String name, List<UMLColumn> columns) {
        this.name = name;
        this.columns = columns;
    }



    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {

        List<String> names = new ArrayList<>();

        List<RelDataType> types = new ArrayList<>();

        for(UMLColumn col : columns){
            names.add(col.getName());
            String upperCase = col.getType().toUpperCase();
            if ("STRING".equals(upperCase))
                upperCase = "VARCHAR";

            RelDataType sqlType = typeFactory.createSqlType(SqlTypeName.get(upperCase));
            types.add(sqlType);
        }

        return typeFactory.createStructType(types, names);

    }


    @Override
    public Statistic getStatistic() {
        return Statistics.of(null, List.of(ImmutableBitSet.of(0)), null, null);
    }




    public String getName() {
        return name;
    }
}
