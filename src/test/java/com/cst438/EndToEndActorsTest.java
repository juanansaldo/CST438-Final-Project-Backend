package com.cst438;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@SpringBootTest
public class EndToEndActorsTest {

    public static final String CHROME_DRIVER_FILE_LOCATION = "/Users/juanansaldo/Downloads/chromedriver-mac-arm64/chromedriver";
    public static final String URL = "http://localhost:3000";
    public static final int SLEEP_DURATION = 1000; // Time in milliseconds

    @Test
    public void createActor() throws Exception {
        
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get(URL);
            Thread.sleep(SLEEP_DURATION);
            
            // Login procedure
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("admin");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("admin");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("submit"))).click();

            // Navigate to "Actors" section
            wait.until(ExpectedConditions.elementToBeClickable(By.id("actorspage"))).click();
            Thread.sleep(SLEEP_DURATION);

            // Process of adding a new actor
            wait.until(ExpectedConditions.elementToBeClickable(By.id("addactor"))).click();
            driver.findElement(By.id("name")).sendKeys("New Actor Name");
            driver.findElement(By.id("age")).sendKeys("30");
            driver.findElement(By.id("portrait")).sendKeys("portrait.jpg");
            driver.findElement(By.id("about")).sendKeys("Brief description about the actor.");
            driver.findElement(By.id("add")).click();
            Thread.sleep(SLEEP_DURATION);

        } catch (Exception ex) {
            throw ex;
        } finally {
            driver.quit();
        }
    }
    
    @Test
    public void updateActor() throws Exception {
    	
    	System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get(URL);
            Thread.sleep(SLEEP_DURATION);

            // Login
            driver.findElement(By.id("username")).sendKeys("admin");
            driver.findElement(By.id("password")).sendKeys("admin");
            driver.findElement(By.id("submit")).click();
            Thread.sleep(SLEEP_DURATION);

            // Navigate to Actors page and select an actor to edit
            driver.findElement(By.id("actorspage")).click();
            Thread.sleep(SLEEP_DURATION);
            // Replace with the actual element that leads to the actor editing functionality
            driver.findElement(By.xpath("//some-selector-for-edit-button")).click();

            // Update actor details
            WebElement nameField = driver.findElement(By.id("name"));
            nameField.clear();
            nameField.sendKeys("Updated Name");

            WebElement ageField = driver.findElement(By.id("age"));
            ageField.clear();
            ageField.sendKeys("30");

            // Assuming there are other fields to update, similar steps for them

            driver.findElement(By.id("update")).click(); // Assuming there is an update button
            Thread.sleep(SLEEP_DURATION);

            // Verification
            // Add steps to ensure the actor's details are updated

        } catch (Exception ex) {
            throw ex;
        } finally {
            driver.quit();
        }
    }
    
    @Test
    public void deleteActor() throws Exception {
    	
    	System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get(URL);
            Thread.sleep(SLEEP_DURATION);

            // Login
            driver.findElement(By.id("username")).sendKeys("admin");
            driver.findElement(By.id("password")).sendKeys("admin");
            driver.findElement(By.id("submit")).click();
            Thread.sleep(SLEEP_DURATION);

            // Navigate to Actors page and select an actor to delete
            driver.findElement(By.id("actorspage")).click();
            Thread.sleep(SLEEP_DURATION);
            // Replace with the actual element that leads to the actor deletion functionality
            driver.findElement(By.xpath("//some-selector-for-delete-button")).click();

            // Handle confirmation alert if there is any
            Alert alert = driver.switchTo().alert();
            alert.accept();
            Thread.sleep(SLEEP_DURATION);

            // Verification
            // Add steps to ensure the actor is deleted from the list

        } catch (Exception ex) {
            throw ex;
        } finally {
            driver.quit();
        }
    }
}
