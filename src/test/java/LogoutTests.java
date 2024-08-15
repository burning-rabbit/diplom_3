import data.UserData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import object.User;
import org.junit.Test;
import page.AccountPage;
import page.LoginPage;
import page.MainPage;
import rest.UserRest;

public class LogoutTests extends ChoiseBrowser{
    private UserRest userRest;
    private User user;

    MainPage mainPage;
    LoginPage loginPage;
    AccountPage accountPage;

    @Test
    @DisplayName("Выход по кнопке <Выйти> в личном кабинете")
    public void profileExitButtonTest() {

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

        mainPage.clickPersonalAccountButton();

        accountPage = new AccountPage(webDriver);
        accountPage.clickLogOutButton();

        loginPage.isSingInButtonDisplayed();

        userRest.deleteUser(accessToken);
    }
}
