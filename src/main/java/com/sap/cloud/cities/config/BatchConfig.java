package com.sap.cloud.cities.config;

import com.sap.cloud.cities.entities.City;
import com.sap.cloud.cities.repository.CityRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashSet;

@Configuration
public class BatchConfig {
    private final BatchProperties applicationConfig;
    private final CityRepository cityRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private static final String DELIMITER = ";";

    public BatchConfig(BatchProperties applicationConfig, CityRepository cityRepository, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.applicationConfig = applicationConfig;
        this.cityRepository = cityRepository;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public FlatFileItemReader<City> reader() {
        FlatFileItemReader<City> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(this.applicationConfig.getFileLocation()));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer(DELIMITER) {{
                setNames("name", "area", "population");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(City.class);
            }});
        }});
        reader.setRecordSeparatorPolicy(new BlankLineRecordSeparationPolicy());
        return reader;
    }

    @Bean
    public ItemWriter<City> writer() {
        return (cities) -> {
            System.out.println("Saving City Records: " + cities);
            this.cityRepository.saveAll(cities);
        };
    }

    @Bean
    public ItemProcessor<City, City> processor() {
        return new CityProcessor();
    }

    @Bean
    public JobExecutionListener listener() {
        return new CityListener();
    }

    @Bean
    public Step importDataFromCsv() {
        return new StepBuilder("csv-step", this.jobRepository)
                .<City, City>chunk(10, this.transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Job runJob() {
        return new JobBuilder("Cities", this.jobRepository)
                .start(this.importDataFromCsv())
                .build();
    }

}
