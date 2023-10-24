# Description

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
- Установка Java Development Kit 21 (JDK/OpenJDK)
- Установка Maven, инструмента для управления зависимостями и сборки проектов
- Установка JUnit - фреймворка для тестирования на Java
- Установка WebDriver для браузера Google Chrome (версия 18)
- Файл *pom.xml* - определяет зависимости для успешной сборки и выполнения автотестов
  
В файле pom.xml указать следующие ключевые зависимости:
```
<dependencies>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>3.141.59</version> <!-- Версия Selenium WebDriver -->
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.7.2</version> <!-- Версия JUnit -->
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.7.2</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
```

#### 1. *Selenium WebDriver (org.seleniumhq.selenium:selenium-java)*
Эта зависимость позволяет взаимодействовать с браузером для автоматизации действий на веб-страницах. В моем случае, это версия 3.141.59
#### 2. *JUnit (org.junit.jupiter:junit-jupiter и org.junit.jupiter:junit-jupiter-api)*
Эти зависимости используются для написания и запуска тестов в проекте. В моем случае, это версия 5.7.2

### Код:
В этой части кода происходит импорт различных классов и интерфейсов из библиотеки Selenium:

```
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.Assertions;
```
- *By* - для выбора элементов на веб-странице по различным критериям (id, CSS-селектор, XPath и др.)
- *WebDriver* - предоставляет методы для управления веб-браузером
- *WebElement* - представляет HTML-элемент на веб-странице и используется для взаимодействия с элементами
- *JavascriptExecutor* - позволяет выполнять JavaScript в контексте веб-страницы
- *ChromeDriver* - предоставляет реализацию WebDriver для управления браузером Google Chrome
- *ExpectedConditions* - обеспечивает ожидание определенных условий в автотестах
- *WebDriverWait* - для явного ожидания определенных условий на веб-странице перед выполнением действий
- *Assertions* - для проверки ожидаемых результатов в тесте

<br> В этом блоке определен класс MySeleniumTest с методом main, который устанавливает путь к драйверу браузера Chrome и создает объекты WebDriver и WebDriverWait для автоматизации веб-тестирования:
```
public class MySeleniumTest {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
```


<br>Этот блок находится внутри метода main и содержит фактический автоматизированный тестовый сценарий, включая действия, связанные с веб-браузером Chrome, навигацией, заполнением формы, выполнением JavaScript и проверкой данных на веб-странице:
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

#### *navigateToElementsPage* 
Метод предназначен для навигации по веб-странице. Он находит элемент с текстом "Elements" с использованием XPath, выполняет на нем клик, а затем ожидает, пока элемент с id "item-0" станет кликабельным, и снова выполняет на нем клик. Это позволяет перейти к разделу "Elements" и выбрать подраздел "Text Box":

```
    private static void navigateToElementsPage(WebDriver driver, WebDriverWait wait) {
        WebElement firstCard = driver.findElement(By.xpath("//h5[contains(text(),'Elements')]"));
        firstCard.click();
        WebElement textbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("item-0")));
        textbox.click();
    }
```

#### *fillOutForm* 
Метод заполняет форму на веб-странице. Он находит элементы формы по их id ("userName", "userEmail", "currentAddress" и "permanentAddress") и вводит в них соответствующие значения:

```
    private static void fillOutForm(WebDriver driver) {
        driver.findElement(By.id("userName")).sendKeys("Michael Davis");
        driver.findElement(By.id("userEmail")).sendKeys("michael.davis@gmail.com");
        driver.findElement(By.id("currentAddress")).sendKeys("456 Elm Avenue, Los Angeles, USA");
        driver.findElement(By.id("permanentAddress")).sendKeys("987 Pine Road, Chicago, USA");
    }
```

#### *submitForm*
Метод использует JavascriptExecutor для прокрутки страницы вниз на 300 пикселей и затем находит элемент формы с id "submit" и кликает на него для отправки формы:

```
    private static void submitForm(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 300);");
        driver.findElement(By.id("submit")).click();
    }
```

#### *verifyData* 
Метод используется для проверки данных на веб-странице после отправки формы. Он:
1. Находит на веб-странице контейнер (элемент с классом "border"), в котором, предположительно, содержатся данные для проверки
2. С помощью XPath-запросов находит отдельные элементы на странице, представляющие имя, email, текущий адрес и постоянный адрес пользователя
3. Определяет ожидаемые значения для каждого из этих элементов
4. Вызывает метод assertDataEquals для сравнения фактических данных на странице с ожидаемыми данными и выводит сообщение об ошибке, если значения не совпадают

```
    private static void verifyData(WebDriver driver) {
        WebElement container = driver.findElement(By.className("border"));

        WebElement nameElement = By.xpath("//p[contains(text(),\"Name:\")]").findElement(container);
        WebElement emailElement = By.xpath("//p[contains(text(),\"Email:\")]").findElement(container);
        WebElement currentAddressElement = By.xpath("//p[contains(text(),\"Current Address :\")]").findElement(container);
        WebElement permanentAddressElement = By.xpath("//p[contains(text(),\"Permananet Address :\")]").findElement(container);

        String expectedName = "Michael Davis";
        String expectedEmail = "michael.davis@gmail.com";
        String expectedCurrentAddress = "456 Elm Avenue, Los Angeles, USA";
        String expectedPermanentAddress = "987 Pine Road, Chicago, USA";

        assertDataEquals(nameElement, expectedName);
        assertDataEquals(emailElement, expectedEmail);
        assertDataEquals(currentAddressElement, expectedCurrentAddress);
        assertDataEquals(permanentAddressElement, expectedPermanentAddress);
    }
```

#### *assertDataEquals* 
Метод сравнивает фактический текст элемента на веб-странице с ожидаемым значением и выводит сообщение об ошибке в случае несоответствия:

```
    private static void assertDataEquals(WebElement element, String expectedValue) {
        String actualText = extractValue(element);
        Assertions.assertEquals(expectedValue, actualText, "Значения не совпадают. Ожидаемое: " + expectedValue + ", Фактическое: " + actualText);
    }
```

#### *extractValue*
Метод принимает веб-элемент (WebElement) в качестве аргумента, извлекает текстовое значение из этого элемента, обрезает начальные и конечные пробелы, и возвращает результат после символа ":" (если он есть), убирая также лишние пробелы:

```
    private static String extractValue(WebElement element) {
        String elementText = element.getAttribute("innerText").trim();
        return elementText.substring(elementText.indexOf(":") + 1).trim();
    }
}
```

### Результаты выполнения автотеста:

- Если тест пройден успешно, в консоли можно увидеть следующее сообщение:
```
Starting ChromeDriver 118.0.5993.70 (e52f33f30b91b4ddfad649acddc39ab570473b86-refs/branch-heads/5993@{#1216}) on port 44997
Only local connections are allowed.
Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
ChromeDriver was started successfully.
Oct 20, 2023 3:16:17 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
Данные отображены корректно.

Process finished with exit code 0
```
*Это сообщение об успехе, которое говорит о том, что тест не обнаружил никаких проблем или несоответствий в отображении данных на веб-странице и завершился успешно. Exit code 0 также указывает на успешное завершение процесса*

- Если тест завершился неудачно, в консоли можно увидеть похожее сообщение:
```
Starting ChromeDriver 118.0.5993.70 (e52f33f30b91b4ddfad649acddc39ab570473b86-refs/branch-heads/5993@{#1216}) on port 6153
Only local connections are allowed.
Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
ChromeDriver was started successfully.
Oct 20, 2023 3:54:41 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
Exception in thread "main" org.opentest4j.AssertionFailedError: Значения не совпадают. Ожидаемое: Michael, Фактическое: Michael Davis ==> expected: <Michael> but was: <Michael Davis>
	at org.junit.jupiter.api.AssertionUtils.fail(AssertionUtils.java:55)
	at org.junit.jupiter.api. ...
	at org.example.MySeleniumTest.verifyData(MySeleniumTest.java:69)
	at org.example.MySeleniumTest. ...

Process finished with exit code 1
```
*В данном случае, ожидаемое значение в тесте было "Michael", однако фактическое значение, полученное из приложения, было "Michael Davis". Тест упал, потому что фактическое значение не соответствовало ожидаемому значению, это вызвало ошибку в проверке данных. Exit code 1 также указывает на провал теста* 
