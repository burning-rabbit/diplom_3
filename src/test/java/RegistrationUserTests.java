import data.UserData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import object.User;
import object.UserLogin;
import org.junit.Test;
import page.LoginPage;
import page.MainPage;
import page.RegistrationPage;
import rest.UserRest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class RegistrationUserTests extends ChoiseBrowser{
    private String accessToken;
    private UserRest userRest;

    MainPage mainPage;
    LoginPage loginPage;
    RegistrationPage registerPage;

    @Test
    @DisplayName("Успешная регистрация")
    public void checkRegistration() {
        mainPage = new MainPage(webDriver);
        mainPage.openPage();
        userRest = new UserRest();
        User user = UserData.getUser();
        mainPage.clickLoginButton();

        loginPage = new LoginPage(webDriver);
        loginPage.registerButtonClick();

        registerPage = new RegistrationPage(webDriver);
        registerPage.setFieldsForRegistration(user.getName(), user.getEmail(), user.getPassword());

        UserLogin userLogin = new UserLogin(user.getEmail(), user.getPassword());
        ValidatableResponse loginResponse = userRest.loginUser(userLogin);
        loginResponse.assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
        accessToken = loginResponse.extract().path("accessToken");
        userRest.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Ошибка некорректного пароля")
    public void checkRegistrationWithIncorrectPassword() {
        mainPage = new MainPage(webDriver);
        mainPage.openPage();
        userRest = new UserRest();
        User user = UserData.getIncorrectPasswordUser();
        mainPage.clickLoginButton();

        loginPage = new LoginPage(webDriver);
        loginPage.registerButtonClick();

        registerPage = new RegistrationPage(webDriver);
        registerPage.setFieldsForRegistration(user.getName(), user.getEmail(), user.getPassword());
        registerPage.getTextError();

        UserLogin userLogin = new UserLogin(user.getEmail(), user.getPassword());
        ValidatableResponse loginResponse = userRest.loginUser(userLogin);
        loginResponse.assertThat()
                .statusCode(401)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }
}
