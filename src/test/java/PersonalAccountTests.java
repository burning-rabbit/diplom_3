import data.UserData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import object.User;
import org.junit.Test;
import page.AccountPage;
import page.LoginPage;
import page.MainPage;
import rest.UserRest;

public class PersonalAccountTests extends ChoiseBrowser{
    private UserRest userRest;
    private User user;
    private String accessToken;

    MainPage mainPage;
    LoginPage loginPage;
    AccountPage accountPage;

    @Test
    @DisplayName("Переход по клику на <Личный кабинет>")
    public void loginUserPersonalAccountButton() {
        mainPage = new MainPage(webDriver);
        mainPage.openPage();
        mainPage.clickPersonalAccountButton();
        loginPage = new LoginPage(webDriver);
        loginPage.isSingInButtonDisplayed();
    }

    @Test
    @DisplayName("Переход по клику на <Конструктор>")
    public void checkGoToConstructor() {
        MainPage mainPage = new MainPage(webDriver);
        mainPage.openPage();
        mainPage.clickLoginButton();

        userRest = new UserRest();
        user = UserData.getUser();
        ValidatableResponse createResponse = userRest.createUser(user);
        accessToken = createResponse.extract().path("accessToken");

        loginPage = new LoginPage(webDriver);
        loginPage.setLoginForm(user.getEmail(), user.getPassword());
        loginPage.singInButtonClick();

        mainPage.clickPersonalAccountButton();
        accountPage = new AccountPage(webDriver);
        accountPage.clickCreateBurgerButton();
        mainPage.isOrderButtonDisplayed();
        userRest.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Переход по клику на логотипу Stellar Burgers")
    public void checkOutFromPersonalAccount() {
        mainPage = new MainPage(webDriver);
        mainPage.openPage();
        mainPage.clickLoginButton();

        userRest = new UserRest();
        user = UserData.getUser();
        ValidatableResponse createResponse = userRest.createUser(user);
        accessToken = createResponse.extract().path("accessToken");

        loginPage = new LoginPage(webDriver);
        loginPage.setLoginForm(user.getEmail(), user.getPassword());
        loginPage.singInButtonClick();
        mainPage.clickPersonalAccountButton();
        accountPage = new AccountPage(webDriver);
        accountPage.clickStellarBurgerLogoButton();
        mainPage.isOrderButtonDisplayed();
        userRest.deleteUser(accessToken);
    }
}
