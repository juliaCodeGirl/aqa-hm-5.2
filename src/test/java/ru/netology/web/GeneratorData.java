package ru.netology.web;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class GeneratorData {

    private static RequestSpecification request = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void RegUser(RegistrationDto regDto) {
        given()
                .spec(request)
                .body(regDto)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static RegistrationDto genValidActiveUser() {
        Faker faker = new Faker(new Locale("en"));
        RegistrationDto validUser = new RegistrationDto(
                faker.name().username().toLowerCase(),
                faker.internet().password(),
                "active"
        );
        RegUser(validUser);
        return validUser;
    }

    public static RegistrationDto genValidBlockedUser() {
        Faker faker = new Faker(new Locale("en"));
        RegistrationDto blockedUser = new RegistrationDto(
                faker.name().username().toLowerCase(),
                faker.internet().password(),
                "blocked"
        );
        RegUser(blockedUser);
        return blockedUser;
    }

    public static RegistrationDto genInvalidLogin() {
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().password();
        RegistrationDto invalidLogin = new RegistrationDto(
                "username",
                password,
                "active"
        );
        RegUser(invalidLogin);
        return new RegistrationDto("badLogin", password, "active");
    }

    public static RegistrationDto genBadPassword() {
        Faker faker = new Faker(new Locale("en"));
        String userName = faker.name().username().toLowerCase();
        RegistrationDto badPassword = new RegistrationDto(
                userName,
                "correctPassword",
                "active"
        );
        RegUser(badPassword);
        return new RegistrationDto(userName, "badPassword", "active");
    }


}
