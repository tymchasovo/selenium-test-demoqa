# description

### Автотест выполняет следующие шаги:
1. Перейти на сайт DemoQA https://demoqa.com
2. Нажать на блок "Elements"
3. Выбрать в списке "Elements" поле "Text Box"
4. Заполнить все 4 поля любыми значениями
5. Нажать на кнопку "Submit"
6. Проверить, что выводятся правильные данные, те, которые были введены

### Подготовка окружения:
- ОС - macOS Ventura 13.4.1, chip М2
- Установка среды разработки IntelliJ IDEA
- Установка Java 21 (JDK/OpenJDK)
- Установка Maven, инструмента для управления зависимостями и сборки проектов
- Установка WebDriver для браузера Google Chrome (версия 18)

### Код:
В этой части кода происходит импорт различных классов и интерфейсов из библиотеки Selenium:

```
package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

```
- *By* - для выбора элементов на веб-странице по различным критериям (id, CSS-селектор, XPath и др.)
- *WebDriver* - предоставляет методы для управления веб-браузером
- *WebElement* - представляет HTML-элемент на веб-странице и используется для взаимодействия с элементами
- *JavascriptExecutor* - позволяет выполнять JavaScript в контексте веб-страницы
- *ChromeDriver* - предоставляет реализацию WebDriver для управления браузером Google Chrome
- *ExpectedConditions* - обеспечивает ожидание определенных условий в автотестах
- *WebDriverWait* - для явного ожидания определенных условий на веб-странице перед выполнением действий

<br> В этом блоке определен класс MySeleniumTest с методом main, который устанавливает путь к драйверу браузера Chrome и создает объекты WebDriver и WebDriverWait для автоматизации веб-тестирования:
```
public class MySeleniumTest {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
```


Этот блок находится внутри метода main и содержит фактический автоматизированный тестовый сценарий, включая действия, связанные с веб-браузером Chrome, навигацией, заполнением формы, выполнением JavaScript и проверкой данных на веб-странице:
```
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
```

#### *navigateToElementsPage(WebDriver driver, WebDriverWait wait)* 
Этот метод предназначен для навигации по веб-странице. Он находит элемент с текстом "Elements" с использованием XPath, выполняет на нем клик, а затем ожидает, пока элемент с идентификатором "item-0" станет кликабельным, и снова выполняет на нем клик. Это позволяет перейти к разделу "Elements" и выбрать подраздел "Text Box":

```
    private static void navigateToElementsPage(WebDriver driver, WebDriverWait wait) {
        WebElement firstCard = driver.findElement(By.xpath("//h5[contains(text(),'Elements')]"));
        firstCard.click();
        WebElement textbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("item-0")));
        textbox.click();
    }
```

#### *fillOutForm(WebDriver driver)* 
Метод заполняет форму на веб-странице. Он находит элементы формы по их идентификаторам ("userName", "userEmail", "currentAddress" и "permanentAddress") и вводит в них соответствующие значения:

```
    private static void fillOutForm(WebDriver driver) {
        driver.findElement(By.id("userName")).sendKeys("Michael Davis");
        driver.findElement(By.id("userEmail")).sendKeys("michael.davis@gmail.com");
        driver.findElement(By.id("currentAddress")).sendKeys("456 Elm Avenue, Los Angeles, USA");
        driver.findElement(By.id("permanentAddress")).sendKeys("987 Pine Road, Chicago, USA");
    }
```

#### *verifyData(WebDriver driver)* 
Метод используется для проверки данных на веб-странице после отправки формы. Он находит элементы с данными (имя, email, текущий адрес и постоянный адрес) с использованием их идентификаторов и сравнивает текст в них с ожидаемыми значениями:

```
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
```

#### *assertDataEquals(WebElement element, String expectedText)* 
Метод выполняет проверку на равенство текста, полученного из элемента WebElement, ожидаемому тексту. Если текст не соответствует ожидаемому, он вызывает исключение AssertionError с сообщением об ошибке:

```
    private static void assertDataEquals(WebElement element, String expectedText) {
        String actualText = element.getText();
        assert actualText.equals(expectedText) : "Expected: " + expectedText + ", but got: " + actualText;
    }
}
```
