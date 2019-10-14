package com.damdamdeo.santa;

import com.damdamdamdeo.santa.SantaClausService;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;

@QuarkusTest
public class SantaClausServiceTest {

    @Inject
    SantaClausService santaClausService;

    @Inject
    EntityManager em;

    @Inject
    UserTransaction transaction;

    @BeforeEach
    @Transactional
    public void setup() throws IOException {
        em.createQuery("DELETE FROM Gift").executeUpdate();
        final ClassLoader classLoader = getClass().getClassLoader();
        try (final InputStream inputStream = classLoader.getResourceAsStream("debezium.json")) {
            final String debezium = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            given()
                    .contentType("application/json")
                    .accept("application/json")
                    .body(debezium)
                    .when()
                    .post("http://localhost:8083/connectors/")
                    .then()
                    .statusCode(201);
        }
    }

    @AfterEach
    public void teardown() {
        given().delete("http://localhost:8083/connectors/test-connector");
    }

    @Test
    public void should_create_gift() {
        // Given
        // When
        santaClausService.createGift("Quarkus !");

        // Then
        await().atMost(50, TimeUnit.SECONDS).until(() -> {
            // TODO check event consumed.
            return true;
        });
    }

}
