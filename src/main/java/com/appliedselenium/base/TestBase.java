package com.appliedselenium.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {

	//initialize the WebDriver interface reference variable
	public static WebDriver driver;

	// Create instance of Properties class to refer the .properties file
	public static Properties or = new Properties();
	public static Properties config = new Properties();

	// Create filestream class object to load the file
	public static FileInputStream fis;
	
	//Create Logger class object to initialize the log4j
	public static Logger logger = Logger.getLogger("devpinoyLogger");
	
	//Generic Method to check the presence of an element
	//This method accepts 1 parameter by of type By and returns a boolean value
		public boolean isElementPresent(By by) {
			
			try {
				driver.findElement(by);
				return true;
			}catch (NoSuchElementException e) {
				return false;
			}
			
		}

	// First method to be called before automation suite is executed 
	@BeforeSuite
	public void setUp() {
		
		//check if driver reference variable is null, i.e. no browser is opened through selenium yet
		if (driver == null) {

			//get the reference of or.properties file
			try {
			
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\or.properties");
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			
			//load the file into memory
			try {
				or.load(fis);
				logger.debug("or.properties file loaded");

			} catch (IOException e) {
				
				e.printStackTrace();
			}

			//get the reference of config.properties file
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\config.properties");
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			
			//load the file into memory
			try {
				config.load(fis);
				logger.debug("config.properties file loaded");
			} catch (IOException e) {
				
				e.printStackTrace();
			}

			// invoke the browser mentioned in config.prop and assign it to driver reference variable
			if (config.getProperty("browser").equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
				logger.debug("Chrome browser launched");
			}

			//open the url and assign an implicit wait (as configured in config.properties)
			driver.get(config.getProperty("url"));
			logger.debug("Application launched: "+config.getProperty("url"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit_wait")),
					TimeUnit.SECONDS);
		}

	}

	// Mehod called after all the tests are executed
	@AfterSuite
	public void tearDown() {
		System.out.println("Closing the browser");
		driver.quit();
		logger.debug("Browser Closed");

	}

}
