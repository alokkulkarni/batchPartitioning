package com.example.batch;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alokkulkarni on 30/10/16.
 */
public class CustomRangePartitioner implements Partitioner {


    private JdbcOperations jdbcTemplate;

    private String table;

    private String column;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setColumn(String column) {
        this.column = column;
    }


    @Override
    public Map<java.lang.String, ExecutionContext> partition(int gridSize) {
        int min = jdbcTemplate.queryForObject("SELECT MIN(" + column + ") from " + table, Integer.class);
        int max = jdbcTemplate.queryForObject("SELECT MAX(" + column + ") from " + table, Integer.class);
        int targetSize = (max - min) / gridSize + 1;

        Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
        int number = 0;
        int start = min;
        int end = start + targetSize - 1;

        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            if (end >= max) {
                end = max;
            }
            value.putInt("minValue", start);
            value.putInt("maxValue", end);
            result.put("partition" + number, value);
            System.out.println("threads :- " + result.get("partition" + number));
            start += targetSize;
            end += targetSize;
            number++;
        }

        return result;
    }
}
