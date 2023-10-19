package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MySeleniumTest {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        try {
            driver.get("https://demoqa.com/");
            driver.manage().window().maximize();

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 300);");

            navigateToElementsPage(driver, wait);
            fillOutForm(driver);
            submitForm(driver);
            verifyData(driver);

            System.out.println("Данные отображены корректно.");
        } finally {
            driver.quit();
        }
    }

    private static void navigateToElementsPage(WebDriver driver, WebDriverWait wait) {
        WebElement firstCard = driver.findElement(By.xpath("//h5[contains(text(),'Elements')]"));
        firstCard.click();
        WebElement textbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("item-0")));
        textbox.click();
    }

    private static void fillOutForm(WebDriver driver) {
        driver.findElement(By.id("userName")).sendKeys("Michael Davis");
        driver.findElement(By.id("userEmail")).sendKeys("michael.davis@gmail.com");
        driver.findElement(By.id("currentAddress")).sendKeys("456 Elm Avenue, Los Angeles, USA");
        driver.findElement(By.id("permanentAddress")).sendKeys("987 Pine Road, Chicago, USA");
    }

    private static void submitForm(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 300);");
        driver.findElement(By.id("submit")).click();
    }

    private static void verifyData(WebDriver driver) {
        WebElement nameElement = driver.findElement(By.id("name"));
        WebElement emailElement = driver.findElement(By.id("email"));
        WebElement currentAddressElement = driver.findElement(By.id("currentAddress"));
        WebElement permanentAddressElement = driver.findElement(By.id("permanentAddress"));

        String expectedName = "Name: Michael Davis";
        String expectedEmail = "Email: michael.davis@gmail.com";
        String expectedCurrentAddress = "Current Address : 456 Elm Avenue, Los Angeles, USA";
        String expectedPermanentAddress = "Permanent Address : 987 Pine Road, Chicago, USA";

        assertDataEquals(nameElement, expectedName);
        assertDataEquals(emailElement, expectedEmail);
        assertDataEquals(currentAddressElement, expectedCurrentAddress);
        assertDataEquals(permanentAddressElement, expectedPermanentAddress);
    }

    private static void assertDataEquals(WebElement element, String expectedText) {
        String actualText = element.getText();
        assert actualText.equals(expectedText) : "Expected: " + expectedText + ", but got: " + actualText;
    }
}
