package ru.netology.homeworkselenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class HomeworkSelenideSecondTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldSubmitTheForm(){
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item__control").findBy(text("Москва")).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        String desiredDate = generateDate(7, "dd.MM.yyyy");
        $("button .icon_name_calendar").click();
        if (generateDate(3,"MM").equals (generateDate(7,"MM"))) {
            $$(".calendar__day").findBy(text(generateDate(7, "d"))).click();
        }else {
            $$(".calendar__arrow").last().click();
            $$(".calendar__day").findBy(text(generateDate(7, "d"))).click();}
        $("[data-test-id='name'] input").setValue("Иванова Анна Петровна");
        $("[data-test-id='phone'] input").setValue("+79990000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(20))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на "  + desiredDate));

    }

}
