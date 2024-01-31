package org.john;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Client {

    private final WebDriver driver = new FirefoxDriver();
    private final String URL;

    public Client(String url) {
        URL = url;
    }

    private void loadData() {
        System.setProperty("webdriver.gecko.driver","C:/geckodriver.exe");
        driver.get(URL);

        WebElement iframe = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("ptifrmtgtframe")));
        driver.switchTo().frame(iframe);

        WebElement subject = driver.findElement(By.id("NR_SSS_SOC_NWRK_SUBJECT"));
        Select subjectDropdown = new Select(subject);
        subjectDropdown.selectByValue("COMP");

        driver.findElement(By.id("NR_SSS_SOC_NWRK_BASIC_SEARCH_PB")).click();
        new WebDriverWait(driver, Duration.ofSeconds(45)).until(ExpectedConditions.visibilityOfElementLocated(By.id("NR_SSS_SOC_NSEC$scroll$0")));
    }

    public String getPageSource() {
        loadData();
        return driver.getPageSource();
    }

    public WebDriver getDriver() {
        return driver;
    }
}
