package ru.netology.selen;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {


   /* @BeforeAll
    static void SetUpAll() {
        WebDriverManager.chromedriver().setup();*/

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldGetNotification() {
        String planningDate = generateDate(6);
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999");
        $x("//input[@placeholder='Город']").setValue("Пермь").pressEscape();
        $x("//input[@placeholder='Дата встречи']").doubleClick().pressEscape().sendKeys(planningDate);
        $x("//input[@name='name']").setValue("Иванова-Петрова Мария");
        $x("//input[@name='phone']").setValue("+79904567890");
        $x("//label[@data-test-id='agreement']").click();
        $x("//*[text()='Забронировать']").click();
        $x("//div[@class='notification__content']").shouldBe(visible, Duration.ofSeconds(15));
        //$x("//div[contains(text(),'Встреча успешно забронирована на')]").shouldBe(visible,Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }
}




