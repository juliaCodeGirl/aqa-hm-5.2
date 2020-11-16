package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.cssSelector;

public class AuthTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequestIfValidUser() {
        RegistrationDto user = GeneratorData.genValidActiveUser();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=login] input")).sendKeys(user.getLogin());
        form.$(cssSelector("[data-test-id=password] input")).sendKeys(user.getPassword());
        form.$(cssSelector("[data-test-id=action-login] ")).click();
        $(byText("Личный кабинет")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldNotSubmitRequestStatusIsBlocked() {
        RegistrationDto user = GeneratorData.genValidBlockedUser();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=login] input")).sendKeys(user.getLogin());
        form.$(cssSelector("[data-test-id=password] input")).sendKeys(user.getPassword());
        form.$(cssSelector("[data-test-id=action-login] ")).click();
        $(byText("Пользователь заблокирован")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldNotSubmitRequestLoginInvalid() {
        RegistrationDto user = GeneratorData.genInvalidLogin();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=login] input")).sendKeys(user.getLogin());
        form.$(cssSelector("[data-test-id=password] input")).sendKeys(user.getPassword());
        form.$(cssSelector("[data-test-id=action-login] ")).click();
        $(byText("Неверно указан логин или пароль")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldNotSubmitRequestPasswordInvalid() {
        RegistrationDto user = GeneratorData.genBadPassword();
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=login] input")).sendKeys(user.getLogin());
        form.$(cssSelector("[data-test-id=password] input")).sendKeys(user.getPassword());
        form.$(cssSelector("[data-test-id=action-login] ")).click();
        $(byText("Неверно указан логин или пароль")).waitUntil(Condition.visible, 15000);
    }
}