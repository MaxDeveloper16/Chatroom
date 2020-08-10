package edu.udacity.java.nano;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { WebSocketChatApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketChatApplicationTest {

    private WebDriver webDriver;
    private WebDriver webDriver2;
    private String homeUrl = "http://localhost:8080";
    


    @Test
    public void testLoginAndJoin() {
//        webDriver = new HtmlUnitDriver(true);
        System.setProperty("webdriver.chrome.driver", "/Users/max/Documents/10-Workspace/webdrivers/chromedriver");
        webDriver = new ChromeDriver();
        webDriver.get(homeUrl);

        String inputName = "Hello world!";

        WebElement username = webDriver.findElement(By.name("username"));
        username.sendKeys(inputName);

        WebElement submit = webDriver.findElement(By.className("submit"));
        submit.click();

        assertThat(webDriver.findElement(By.id("username")).getAttribute("innerHTML")).isEqualTo(inputName);
    }

//    @Test
    public void testChatAndLeave() {
        System.setProperty("webdriver.chrome.driver", "/Users/max/Documents/10-Workspace/webdrivers/chromedriver");
        webDriver = new ChromeDriver();


        //1.login
        String testname1 = "test1";
        String testname2 = "test2";

        webDriver.get(homeUrl);
        WebElement username =webDriver.findElement(By.name("username"));
        username.sendKeys(testname1);
        WebElement submit = webDriver.findElement(By.className("submit"));
        submit.click();

        webDriver2  = new ChromeDriver();
        webDriver2.get(homeUrl);
        WebElement username2 =webDriver2.findElement(By.name("username"));
        username2.sendKeys(testname2);
        WebElement submit2 = webDriver2.findElement(By.className("submit"));
        submit2.click();

        //2.send message
        WebElement sendText = webDriver.findElement(By.id("msg"));
        sendText.sendKeys("ChatroomTesting");


        WebElement sendButton = webDriver.findElement(By.id("sendBtn"));
        sendButton.click();

        WebDriverWait wait = new WebDriverWait(webDriver2, 30);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mdui-card-content message-content")));
       
        System.out.println(element.getAttribute("textContent").length());
        assertThat(element.getAttribute("textContent").substring(5, 19)).isEqualTo("ChatroomTesting");

        WebElement leaveChat = webDriver.findElement(By.className("exit"));
        leaveChat.click();

        assertThat(webDriver.findElement(By.className("submit")).getText().equals("login"));
    }
}
