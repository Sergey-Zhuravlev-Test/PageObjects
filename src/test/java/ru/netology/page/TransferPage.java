package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement transferHeading = $(withText("Пополнение карты"));
    private final SelenideElement amount = $("[data-test-id=amount] input");
    private final SelenideElement fromCard = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private final SelenideElement cancelButton = $("[data-test-id=action-cancel]");
    private final SelenideElement errorMessage = $("[data-test-id=error-notification]");

    public TransferPage() {
        transferHeading.shouldBe(visible);
    }

    public void setPayCardNumber(String card, int payment) {
        amount.setValue(String.valueOf(payment));
        fromCard.setValue(card);
        transferButton.click();
    }

    public DashboardPage validPayCard() {
        return new DashboardPage();
    }

    public void invalidPayCard() {
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Произошла ошибка"));
    }

    public void validPayExtendAmount() {
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Недостаточно средств на карте"));
    }
}
