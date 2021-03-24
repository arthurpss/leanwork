package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.HomePage;
import pages.LoginPage;

class Cadastro {

	static WebDriver driver;
	HomePage homePage;
	LoginPage loginPage;
	
	@BeforeAll
	private static void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Windows\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://teste.leancommerce.com.br");
	}
	
	@Test
	@DisplayName("Acessar a tela de login")
	void deveAcessarTelaDeLogin() {
		homePage = new HomePage(driver);
		homePage.clickCadastrar();
		loginPage = new LoginPage(driver);
		
		assertNotNull(loginPage.getFormLogin());
		assertNotNull(loginPage.getFormPreCadastro());
	}

	@AfterAll
	private static void end() {
		driver.quit();
	}
}
