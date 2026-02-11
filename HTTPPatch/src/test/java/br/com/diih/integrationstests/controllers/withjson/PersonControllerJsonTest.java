package br.com.diih.integrationstests.controllers.withjson;

import br.com.diih.config.TestConfigs;
import br.com.diih.integrationstests.dto.PersonDTO;
import br.com.diih.integrationstests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectmapper;
    private static PersonDTO personDTO;

    @BeforeAll
    static void setUp() {
        objectmapper = new ObjectMapper();
        objectmapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        personDTO = new PersonDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockPerson();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .build();

        String content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(personDTO)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectmapper.readValue(content, PersonDTO.class);
        personDTO = createdPerson;

        assertTrue(createdPerson.getId() > 0);
        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());
    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        personDTO.setLastName("Benedict Torvalds");

        String content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(personDTO)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO getPerson = objectmapper.readValue(content, PersonDTO.class);
        personDTO = getPerson;

        assertTrue(getPerson.getId() > 0);
        assertEquals("Linus", getPerson.getFirstName());
        assertEquals("Benedict Torvalds", getPerson.getLastName());
        assertEquals("Helsinki - Finland", getPerson.getAddress());
        assertEquals("Male", getPerson.getGender());
        assertTrue(getPerson.getEnabled());
    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {
        String content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", personDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO getPerson = objectmapper.readValue(content, PersonDTO.class);
        personDTO = getPerson;

        assertTrue(getPerson.getId() > 0);
        assertEquals("Linus", getPerson.getFirstName());
        assertEquals("Benedict Torvalds", getPerson.getLastName());
        assertEquals("Helsinki - Finland", getPerson.getAddress());
        assertEquals("Male", getPerson.getGender());
        assertTrue(getPerson.getEnabled());
    }

    @Test
    @Order(4)
    void disablePersonTest() throws JsonProcessingException {
        String content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", personDTO.getId())
                .when()
                .patch("{id}/disable")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO getPerson = objectmapper.readValue(content, PersonDTO.class);
        personDTO = getPerson;

        assertTrue(getPerson.getId() > 0);
        assertEquals("Linus", getPerson.getFirstName());
        assertEquals("Benedict Torvalds", getPerson.getLastName());
        assertEquals("Helsinki - Finland", getPerson.getAddress());
        assertEquals("Male", getPerson.getGender());
        assertFalse(getPerson.getEnabled());
    }

    @Test
    @Order(5)
    void deletePersonTest() throws JsonProcessingException {
        given(specification)
                .pathParam("id", personDTO.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {
        String content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        List<PersonDTO> people = objectmapper.readValue(content, new TypeReference<List<PersonDTO>>() {
        });

        PersonDTO personOne = people.getFirst();

        assertTrue(personOne.getId() > 0);
        assertEquals("Ayrton", personOne.getFirstName());
        assertEquals("Senna", personOne.getLastName());
        assertEquals("São Paulo - Brasil", personOne.getAddress());
        assertEquals("Male", personOne.getGender());
        assertTrue(personOne.getEnabled());

        PersonDTO personFour = people.get(4);

        assertTrue(personFour.getId() > 0);
        assertEquals("Muhamamd", personFour.getFirstName());
        assertEquals("Ali", personFour.getLastName());
        assertEquals("Kentucky - US", personFour.getAddress());
        assertEquals("Male", personFour.getGender());
        assertTrue(personFour.getEnabled());
    }

    private void mockPerson() {
        personDTO.setFirstName("Linus");
        personDTO.setLastName("Torvalds");
        personDTO.setAddress("Helsinki - Finland");
        personDTO.setGender("Male");
        personDTO.setEnabled(true);
    }
}