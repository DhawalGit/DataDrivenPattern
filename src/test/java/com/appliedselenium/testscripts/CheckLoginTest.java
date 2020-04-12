package com.appliedselenium.testscripts;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.appliedselenium.base.TestBase;

//Test class name should end with Test
//All the test classes must extend TestBase
public class CheckLoginTest extends TestBase {

	// @Test annotation is used to create a test method
	@Test
	public void testLogin() {

		logger.debug("Inside testLogin()");

		// enter the username and password. These values are stored in config.properties
		// file and locators are stored in
		// or.properties file
		driver.findElement(By.xpath(or.getProperty("username_XPATH"))).sendKeys(config.getProperty("user_name"));
		logger.debug("user name entered: " + config.getProperty("user_name"));

		driver.findElement(By.xpath(or.getProperty("passwd_XPATH"))).sendKeys(config.getProperty("password"));
		logger.debug("Password entered: " + config.getProperty("password"));

		driver.findElement(By.xpath(or.getProperty("loginBtn_XPATH"))).click();
		logger.debug("Log in button clicked");

		// create a method isElementPresent() in TestBase to verify the presence of an
		// element
		// if element is found then pass the test else fail the test with message "Login
		// not successful"
		Assert.assertTrue(isElementPresent(By.xpath(or.getProperty("logout_XPATH"))), "Login not successful");

		// this log will only be printed if above assertion is passed
		logger.debug("Login Successful");

	}

}
