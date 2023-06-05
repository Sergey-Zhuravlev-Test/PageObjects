package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р. ";

    private static SelenideElement firstCardAddButton = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] .button__text");
    private static SelenideElement secondCardAddButton = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] .button__text");
    private static SelenideElement refreshButton = $("[data-test-id='action-reload");


    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getFirstCardBalance() {
        var text = cards.first().getText();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        var text = cards.last().getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage topUpFirstCard() {
        firstCardAddButton.click();
        return new TransferPage();
    }

    public TransferPage topUpSecondCard() {
        secondCardAddButton.click();
        return new TransferPage();
    }
}