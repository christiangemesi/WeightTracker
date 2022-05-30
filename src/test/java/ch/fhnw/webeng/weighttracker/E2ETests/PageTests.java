package ch.fhnw.webeng.weighttracker.E2ETests;

import ch.fhnw.webeng.weighttracker.repositories.UserRepository;
import ch.fhnw.webeng.weighttracker.services.UserService;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PageTests {

    @Test
    void signupLink() {
        var driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:8080/login");

        var singupLink = driver.findElementByPartialLinkText("Don't have an account yet?");

        assertEquals("http://localhost:8080/signup", singupLink.getAttribute("href"));
    }

    /**
     * Note this Test will only work until username and password are valid
     */
    @Test
    void loginValid() {

        var driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:8080/login");

        var username = driver.findElementById("username");
        var password = driver.findElementById("password");
        var submit = driver.findElementById("submit");

        username.sendKeys("admin");
        password.sendKeys("admin");
        submit.click();

        var currentUrl = driver.getCurrentUrl();

        assertEquals("http://localhost:8080/", currentUrl);
    }

    @Test
    void loginInvalid() {

        var driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:8080/login");

        var username = driver.findElementById("username");
        var password = driver.findElementById("password");
        var submit = driver.findElementById("submit");

        username.sendKeys("notInDB");
        password.sendKeys("notInDB");
        submit.click();

        var currentUrl = driver.getCurrentUrl();

        assertEquals("http://localhost:8080/login?error", currentUrl);
    }

    @Test
    void signupInvalid() {

        var driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:8080/signup");

        var username = driver.findElementById("username");
        var password = driver.findElementById("password");
        var submit = driver.findElementById("submit");

        username.sendKeys("");
        password.sendKeys("");
        submit.click();

        var currentUrl = driver.getCurrentUrl();

        assertEquals("http://localhost:8080/signup", currentUrl);
    }


    /**
     * Note this Test creates a valid User!
     */
    @Test
    void signupValid() {

        var driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:8080/signup");

        var username = driver.findElementById("username");
        var password = driver.findElementById("password");
        var submit = driver.findElementById("submit");

        username.sendKeys("dasdkljaödw3"); //This line needs to be modified after every run
        password.sendKeys("dasdkljaödwpw");
        submit.click();

        var currentUrl = driver.getCurrentUrl();

        assertEquals("http://localhost:8080/", currentUrl);
    }




}
