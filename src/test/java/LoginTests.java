import data.UserData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import object.User;
import org.junit.Test;
import page.LoginPage;
import page.MainPage;
import page.RecoverPasswordPage;
import page.RegistrationPage;
import rest.UserRest;

public class LoginTests extends ChoiseBrowser{
    private UserRest userRest;
    private User user;

    MainPage mainPage;
    LoginPage loginPage;
    RegistrationPage registerPage;
    RecoverPasswordPage recoverPasswordPage;

    @Test
    @DisplayName("Вход через кнопку <Войти в аккаунт> на главной странице")
    public void loginUserLoginButton() {
        String accessToken;

        userRest = new UserRest();
        user = UserData.getUser();
        ValidatableResponse createResponse = userRest.createUser(user);
        accessToken = createResponse.extract().path("accessToken");

        mainPage = new MainPage(webDriver);
        mainPage.openPage();
        mainPage.clickLoginButton();

        loginPage = new LoginPage(webDriver);
        loginPage.setLoginForm(user.getEmail(), user.getPassword());
        loginPage.singInButtonClick();
        mainPage.isOrderButtonDisplayed();
        userRest.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Вход через кнопку <Личный кабинет>")
    public void loginUserPersonalAccountButton() {
        String accessToken;

        userRest = new UserRest();
        user = UserData.getUser();
        ValidatableResponse createResponse = userRest.createUser(user);
        accessToken = createResponse.extract().path("accessToken");

        mainPage = new MainPage(webDriver);
        mainPage.openPage();
        mainPage.clickPersonalAccountButton();

        loginPage = new LoginPage(webDriver);
        loginPage.setLoginForm(user.getEmail(), user.getPassword());
        loginPage.singInButtonClick();

        mainPage.isOrderButtonDisplayed();

        userRest.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void loginUserRegistrationForm() {
        String accessToken;

        userRest = new UserRest();
        user = UserData.getUser();
        ValidatableResponse createResponse = userRest.createUser(user);
        accessToken = createResponse.extract().path("accessToken");

        mainPage = new MainPage(webDriver);
        mainPage.openPage();
        mainPage.clickLoginButton();

        loginPage = new LoginPage(webDriver);
        loginPage.registerButtonClick();
        registerPage = new RegistrationPage(webDriver);
        registerPage.clickSingInButton();

        loginPage.setLoginForm(user.getEmail(), user.getPassword());
        loginPage.singInButtonClick();

        mainPage.isOrderButtonDisplayed();

        userRest.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void loginUserRecoverPasswordButton() {
        String accessToken;

        userRest = new UserRest();
        user = UserData.getUser();
        ValidatableResponse createResponse = userRest.createUser(user);
        accessToken = createResponse.extract().path("accessToken");

        mainPage = new MainPage(webDriver);
        mainPage.openPage();
        mainPage.clickLoginButton();
        loginPage = new LoginPage(webDriver);
        loginPage.clickRecoverPasswordButton();
        recoverPasswordPage = new RecoverPasswordPage(webDriver);
        recoverPasswordPage.clickLoginButton();

        loginPage.setLoginForm(user.getEmail(), user.getPassword());
        loginPage.singInButtonClick();

        mainPage.isOrderButtonDisplayed();

        userRest.deleteUser(accessToken);
    }
}
