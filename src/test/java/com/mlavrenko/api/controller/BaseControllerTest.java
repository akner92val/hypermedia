package com.mlavrenko.api.controller;

import static com.jayway.restassured.config.JsonConfig.jsonConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.path.json.config.JsonPathConfig;
import com.mlavrenko.common.test.HypermediaIntegrationTest;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@HypermediaIntegrationTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:/insert_test_data.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:/delete_test_data.sql"})
public abstract class BaseControllerTest {

    @Value("http://localhost:${local.server.port}/hypermedia")
    String serviceURL;

    private static boolean initOnceDone;

    protected static ObjectMapper objectMapper;

    @BeforeClass
    public static void configureHal() {
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jackson2HalModule());
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
                (aClass, s) -> objectMapper
        ));
    }

    @Before
    public void initOnce() throws HibernateException, SQLException, ClassNotFoundException {
        if (initOnceDone) {
            return;
        }
        initOnceDone = true;
    }

}
