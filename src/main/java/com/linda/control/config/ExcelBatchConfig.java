package com.linda.control.config;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.batch.item.excel.support.rowset.DefaultRowSetFactory;
import org.springframework.batch.item.excel.support.rowset.RowNumberColumnNameExtractor;
import org.springframework.batch.item.excel.support.rowset.RowSetFactory;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * Created by ywang on 2017/2/17.
 */
@Configuration
@EnableBatchProcessing
public class ExcelBatchConfig {

    /**
     *
     * @return
     * @throws Exception
     */

    @Bean
    @StepScope
    public PoiItemReader reader(@Value("#{jobParameters['input.file.name']}") String pathToFile,
                                @Value("#{jobParameters['targetType']}" )String targetType
    ) throws Exception{
        PoiItemReader reader = new PoiItemReader();
        reader.setLinesToSkip(1);
        reader.setResource(new UrlResource(pathToFile));
        reader.setRowMapper(rowMapper(targetType));
        reader.setRowSetFactory(rowSetFactory());
        return reader;

    }

    @Bean
    @StepScope
    public RowSetFactory rowSetFactory(){
        DefaultRowSetFactory rowSetFactory =  new DefaultRowSetFactory();
        RowNumberColumnNameExtractor columnNameExtractor = new RowNumberColumnNameExtractor();
        columnNameExtractor.setHeaderRowNumber(0);
        rowSetFactory.setColumnNameExtractor(columnNameExtractor);
        return rowSetFactory;
    }


    @Bean
    @StepScope
    public RowMapper rowMapper(@Value("#{jobParameters['targetType']}" )String targetType) throws Exception{
        BeanWrapperRowMapper rowMapper = new BeanWrapperRowMapper();
        rowMapper.setTargetType(Class.forName(targetType));
        return rowMapper;
    }



    @Bean
    @StepScope
    public ItemWriter writer(DataSource dataSource,
                             @Value("#{jobParameters['sql']}") String sql) {
        JdbcBatchItemWriter writer = new JdbcBatchItemWriter();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        writer.setSql(sql);
        writer.setDataSource(dataSource);
        return writer;
    }


    @Bean
    public JobExplorer jobExplorer() throws Exception {

        MapJobExplorerFactoryBean factoryExplorer = new MapJobExplorerFactoryBean(
                new MapJobRepositoryFactoryBean(new ResourcelessTransactionManager()));
        factoryExplorer.afterPropertiesSet();
        return factoryExplorer.getObject();
    }

    /**
     * JobRegistryBeanPostProcessor配置
     *
     * @param jobRegistry
     * @return
     */
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    /**
     * 批处理数据配置
     *
     * @param transactionManager
     * @return
     * @throws Exception
     */
    @Bean
    public JobRepository jobRepository(
            PlatformTransactionManager transactionManager) throws Exception {
        MapJobRepositoryFactoryBean repository = new MapJobRepositoryFactoryBean();
        repository.setTransactionManager(transactionManager);
        return repository.getObject();
    }

    /**
     * SimpleJobLauncher配置
     *
     * @param transactionManager
     * @return
     * @throws Exception
     */
    @Bean
    public SimpleJobLauncher jobLauncher(
            PlatformTransactionManager transactionManager) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository(transactionManager));
        return jobLauncher;
    }


}
