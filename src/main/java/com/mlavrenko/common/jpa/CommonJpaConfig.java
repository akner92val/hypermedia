package com.mlavrenko.common.jpa;

import com.mlavrenko.common.jpa.properties.JpaSchemaGenerationProperties;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.hibernate.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableJpaRepositories({"com.mlavrenko.api", "com.mlavrenko.common"})
@EnableConfigurationProperties(JpaSchemaGenerationProperties.class)
public class CommonJpaConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Bean
    public LocalValidatorFactoryBean smartValidator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            DataSource dataSource,
            JpaProperties jpaProperties,
            JpaSchemaGenerationProperties schemaGenerationProperties,
            Optional<Interceptor> hibernateInterceptor) {

        LocalContainerEntityManagerFactoryBean factory = builder
                .dataSource(dataSource)
                .packages("com.mlavrenko.api", "com.mlavrenko.common")
                .persistenceUnit("default")
                .properties(jpaProperties.getHibernateProperties(dataSource))
                .build();

        Map<String, Object> jpaPropertyMap = new HashMap<>();

        jpaPropertyMap.put("javax.persistence.validation.factory", smartValidator());
        jpaPropertyMap.put("jadira.usertype.databaseZone", env.getProperty("com.mlavrenko.datasource.timeZone"));
        jpaPropertyMap.put("hibernate.query.plan_cache_max_size", 128);
        jpaPropertyMap.put("hibernate.query.plan_parameter_metadata_max_size", 12);

        hibernateInterceptor.ifPresent(interceptor -> jpaPropertyMap.put("hibernate.ejb.interceptor", interceptor));

        for (String s : env.getActiveProfiles()) {
            if ("integration-test".equals(s)) {
                jpaPropertyMap.put("hibernate.show_sql", true);
                jpaPropertyMap.put("javax.persistence.schema-generation.database.action", schemaGenerationProperties.getDatabaseAction());
                jpaPropertyMap.put("javax.persistence.schema-generation.scripts.action", schemaGenerationProperties.getScriptsAction());
                jpaPropertyMap.put("javax.persistence.schema-generation.create-source", schemaGenerationProperties.getCreateSource());
                jpaPropertyMap.put("javax.persistence.schema-generation.scripts.create-target", schemaGenerationProperties.getScriptsCreateTarget());
                jpaPropertyMap.put("javax.persistence.schema-generation.create-script-source", schemaGenerationProperties.getCreateScriptSource());
                jpaPropertyMap.put("javax.persistence.schema-generation.drop-source", schemaGenerationProperties.getDropSource());
                jpaPropertyMap.put("javax.persistence.schema-generation.drop-script-source", schemaGenerationProperties.getDropScriptSource());
                jpaPropertyMap.put("javax.persistence.schema-generation.scripts.drop-target", schemaGenerationProperties.getScriptsDropTarget());
            }
        }
        factory.setJpaPropertyMap(jpaPropertyMap);
        return factory;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(smartValidator());
        return methodValidationPostProcessor;
    }
}
