package ru.netology.homeworkselenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Selenide.*;


public class HomeworkSelenideTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldSubmitTheForm(){
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String desiredDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(desiredDate);
        $("[data-test-id='name'] input").setValue("Иванова Анна Петровна");
        $("[data-test-id='phone'] input").setValue("+79990000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(20))
        .shouldHave(Condition.exactText("Встреча успешно забронирована на " + desiredDate));
    }

    @Test
    public void sendingFormWithoutCity(){/*Без города*/
        open("http://localhost:9999");
        String desiredDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(desiredDate);
        $("[data-test-id='name'] input").setValue("Иванова Анна Петровна");
        $("[data-test-id='phone'] input").setValue("+79990000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(20))
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
    @Test
    public void sendingFormWithAnIncorrectCity(){/*Невалидное значение города*/
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Moscow");
        String desiredDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(desiredDate);
        $("[data-test-id='name'] input").setValue("Иванова Анна Петровна");
        $("[data-test-id='phone'] input").setValue("+79990000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(20))
                .shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    public void sendingFormWithoutDate(){/*Без даты*/
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String desiredDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='name'] input").setValue("Иванова Анна Петровна");
        $("[data-test-id='phone'] input").setValue("+79990000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='date'] .input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(20))
                .shouldHave(Condition.exactText("Неверно введена дата"));
    }

    @Test
    public void sendingFormWithAnIncorrectDate(){/*Невалидное значение даты*/
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String desiredDate = generateDate(0, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(desiredDate);
        $("[data-test-id='name'] input").setValue("Иванова Анна Петровна");
        $("[data-test-id='phone'] input").setValue("+79990000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='date'] .input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(20))
                .shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }
    @Test
    public void sendingFormWithoutName(){/*Без фамилии*/
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String desiredDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(desiredDate);
        $("[data-test-id='phone'] input").setValue("+79990000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(20))
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void sendingFormWithAnIncorrectName(){/*Невалидное значение фамилии*/
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String desiredDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(desiredDate);
        $("[data-test-id='name'] input").setValue("Ivanova Anna");
        $("[data-test-id='phone'] input").setValue("+79990000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(20))
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    public void sendingFormWithoutPhone(){/*Без телефона*/
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String desiredDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(desiredDate);
        $("[data-test-id='name'] input").setValue("Иванова Анна Петровна");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(20))
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void sendingFormWithAnIncorrectPhone(){/*Невалидное значение телефона*/
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String desiredDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(desiredDate);
        $("[data-test-id='name'] input").setValue("Иванова Анна Петровна");
        $("[data-test-id='phone'] input").setValue("8999888222333444");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldBe(Condition.visible, Duration.ofSeconds(20))
                .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    public void sendingFormWithAnUnmarkedCheckbox(){ /* Не нажат чекбокс*/
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        String desiredDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(desiredDate);
        $("[data-test-id='name'] input").setValue("Иванова Анна Петровна");
        $("[data-test-id='phone'] input").setValue("+79990000000");
        $("button.button").click();
        $("[data-test-id='agreement'].input_invalid")
                .shouldBe(Condition.visible, Duration.ofSeconds(20))
                .shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

}
