package com.uniovi.steps;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.pageObjects.PO_LoginView;
import com.uniovi.pageObjects.PO_RegisterView;
import com.uniovi.pageObjects.PO_View;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginSteps {

	static String PathFirefox = "C:\\Firefox46.win\\FirefoxPortable.exe";

	static WebDriver driver = getDriver(PathFirefox);
	static String URL_INCI = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() throws Exception {
		driver.navigate().to(URL_INCI);
	}

	@BeforeClass
	static public void begin() {
	}

	@AfterClass
	static public void end() {
		driver.quit();
	}

	@After
	public void tearDown() throws Exception {
		driver.manage().deleteAllCookies();
	}

	@Given("^a list of users:$")
	public void a_list_of_users(List<Agent> users) throws Throwable {
		for (Agent u : users) {
			System.out.println("Inserting user..." + u.name + " - " + u.password);
		}
	}
	
	@Given("^a user:$")
	public void a_user(List<Agent> users) throws Throwable {
		PO_LoginView.identificar(driver);
		PO_LoginView.fillForm(driver, "12345678P", "123456");
	}

	@When("^I login with name \"(.+)\" and password \"(.+)\"$")
	public void i_login_with_name_and_password(String name, String password) throws Throwable {
		PO_LoginView.identificar(driver);
		PO_LoginView.fillForm(driver, name, password);
	}
	
	@When("^I fill the form with name \"(.+)\" description \"(.+)\" location \"(.+)\" tags \"(.+)\" additionalInformation \"(.+)\" properties \"(.+)\" state \"(.+)\" notification \"(.+)\" expireAt \"(.+)\" and assignedTo \"(.+)\"$")
	public void i_fill_the_form(String name, String description, String location, String tags,
			String additionalInformation, String properties, String state, String notification, String expireAt,
			String assignedTo) throws Throwable {
		PO_RegisterView.fillForm(driver, name, description, location, tags, additionalInformation, properties, state,
				notification, expireAt, assignedTo);
	}

	@Then("^I am redirected to the incident form$")
	public void i_receive_a_welcome_message() throws Throwable {
		PO_View.checkElement(driver, "text", "Datos de la incidencia");
	}
	
	@Then("^I am not redirected to the incident form$")
	public void i_am_not_redirected_to_the_form() throws Throwable {
		PO_View.checkElement(driver, "text", "ident");
	}
	


	public static class Agent {
		private String name;
		private String password;
	}

}
