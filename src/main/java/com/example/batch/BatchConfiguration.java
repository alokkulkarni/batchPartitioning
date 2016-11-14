package com.example.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alokkulkarni on 30/10/16.
 */

@Configuration
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource datasource;

    @Bean
    public CustomRangePartitioner partitioner() {
        CustomRangePartitioner customRangePartitioner = new CustomRangePartitioner();
        customRangePartitioner.setColumn("id");
        customRangePartitioner.setTable("userdetails");
        customRangePartitioner.setDataSource(this.datasource);
        return customRangePartitioner;
    }


    @Bean
    @StepScope
    JdbcPagingItemReader<UserDetails> pagingItemReader (
            @Value("#{stepExecutionContext['minValue']}")Long minValue,
            @Value("#{stepExecutionContext['maxValue']}")Long maxValue
    ) {
        System.out.println("reading " + minValue + " to " + maxValue);
        JdbcPagingItemReader<UserDetails> reader = new JdbcPagingItemReader<>();

        reader.setDataSource(this.datasource);
        reader.setFetchSize(1000);
        reader.setRowMapper(new UserRowMapper());

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, firstName, lastName");
        queryProvider.setFromClause("from userdetails");
        queryProvider.setWhereClause("where id >= " + minValue + " and id < " + maxValue);


        Map<String, Order> sortKeys = new HashMap<>(1);

        sortKeys.put("id", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);

        reader.setQueryProvider(queryProvider);

        return reader;
    }


    @Bean
    @StepScope
    public JdbcBatchItemWriter<UserDetails> userItemWriter() {
        JdbcBatchItemWriter<UserDetails> itemWriter = new JdbcBatchItemWriter<>();

        itemWriter.setDataSource(this.datasource);
        itemWriter.setSql("INSERT INTO newuser VALUES (:id, :firstName, :lastName)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .partitioner(slaveStep().getName(), partitioner())
                .step(slaveStep())
                .gridSize(37)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Step slaveStep() {
        return stepBuilderFactory.get("slaveStep")
                .<UserDetails, UserDetails>chunk(1000)
                .reader(pagingItemReader(null, null))
                .writer(userItemWriter())
                .build();
    }

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("job")
                .start(step1())
                .build();
    }
}
