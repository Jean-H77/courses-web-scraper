package org.john;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Client {

    private final WebDriver driver;
    private final String URL;

    public Client(WebDriver driver, String url) {
        this.driver = driver;
        URL = url;
    }

    private void loadData() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.setProperty("webdriver.gecko.driver","C:/geckodriver.exe");
        driver.get(URL);

        WebElement iframe = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ptifrmtgtframe")));
        driver.switchTo().frame(iframe);

        WebElement subject = driver.findElement(By.id("NR_SSS_SOC_NWRK_SUBJECT"));
        Select subjectDropdown = new Select(subject);
        subjectDropdown.selectByValue("COMP");

        Thread.sleep(1000);
        driver.findElement(By.id("NR_SSS_SOC_NWRK_BASIC_SEARCH_PB")).click();
        Thread.sleep(15_000); // have to find a better way to do this
    }

    public String getPageSource() throws InterruptedException {
        loadData();
        return driver.getPageSource();
    }

    public WebDriver getDriver() {
        return driver;
    }
}
