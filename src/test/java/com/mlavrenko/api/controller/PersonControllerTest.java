package com.mlavrenko.api.controller;


import static com.jayway.restassured.RestAssured.given;
import static com.mlavrenko.common.test.HttpAuthenticationHeadersFactory.createHeaders;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.springframework.http.HttpStatus;

public class PersonControllerTest extends BaseControllerTest {
    @Test
    public void getPersonByIdShouldReturn200AndEntityIfItExists() {
        final int existingId = 1;
        given()
                .headers(createHeaders())
                .pathParam("personId", existingId)
                .when()
                .get(serviceURL + "/person/{personId}").prettyPeek()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("entityId", equalTo(existingId));
    }
}
