package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


class MoneyTransferTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:7777");
    }

    @Test
    void shouldTransferFromSecondToFirstCard() {
        var amount = 3500;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var initialBalanceToCard = dashboardPage.getFirstCardBalance();
        var initialBalanceFromCard = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.topUpFirstCard();
        transferPage.setPayCardNumber(DataHelper.getSecondCardInfo().getCardNumber(), amount);
        var dashboardPage1 = transferPage.validPayCard();
        var actual = dashboardPage1.getFirstCardBalance();
        var expected = initialBalanceToCard + amount;
        var actual2 = dashboardPage1.getSecondCardBalance();
        var expected2 = initialBalanceFromCard - amount;
        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        var amount = 100;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var initialBalanceToCard = dashboardPage.getSecondCardBalance();
        var initialBalanceFromCard = dashboardPage.getFirstCardBalance();
        var transferPage = dashboardPage.topUpSecondCard();
        transferPage.setPayCardNumber(DataHelper.getFirstCardInfo().getCardNumber(), amount);
        var dashboardPage1 = transferPage.validPayCard();
        var actual1 = dashboardPage1.getSecondCardBalance();
        var expected1 = initialBalanceToCard + amount;
        var actual2 = dashboardPage1.getFirstCardBalance();
        var expected2 = initialBalanceFromCard - amount;
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldCheckTheTransferFromAnInvalidCard() {
        var amount = 200;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var transferPage = dashboardPage.topUpFirstCard();
        transferPage.setPayCardNumber(DataHelper.getInvalidCardInfo().getCardNumber(), amount);
        transferPage.invalidPayCard();
    }

    @Test
    void shouldTransferAnAmountGreaterThanTheLimitFromTheFirstCard() {
        var amount = 40_000;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var initialBalanceFromCard = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.topUpFirstCard();
        transferPage.setPayCardNumber(DataHelper.getSecondCardInfo().getCardNumber(), amount);
        transferPage.validPayExtendAmount();
    }

    @Test
    void shouldTransferAnAmountGreaterThanTheLimitFromTheSecondCard() {
        var amount = 40_000;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var initialBalanceFromCard = dashboardPage.getFirstCardBalance();
        var transferPage = dashboardPage.topUpSecondCard();
        transferPage.setPayCardNumber(DataHelper.getFirstCardInfo().getCardNumber(), amount);
        transferPage.validPayExtendAmount();
    }
}