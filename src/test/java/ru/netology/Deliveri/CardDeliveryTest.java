package ru.netology.Deliveri;

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
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

class CardDeliveryTest {


    @BeforeAll
    static void SetUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void all() {
        String planningDate = generateDate(6);
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999");
        $x("[data-test-id='city']").setValue("Пермь").click();
       // $x("//input[@placeholder='Город']").setValue("Пермь").pressEscape();
        $x("//input[@placeholder='Дата встречи']").doubleClick().setValue(planningDate).sendKeys(Keys.DELETE);
        $x("//input[@name='name']").setValue("Иванова-Петрова Мария");
        $x("//input[@name='phone']").setValue("+79904567890");
        $x("[data-test-id='agreement']").click();
        $x("//*[text()='Забронировать']").click();
        $x("//*div[@class='notification__content')]").shouldBe(visible, Duration.ofSeconds(15));
        $x("//*[contains(text)),'Встреча успешно забронирована на')]").shouldBe(visible,Duration.ofSeconds(15));

    }

}
