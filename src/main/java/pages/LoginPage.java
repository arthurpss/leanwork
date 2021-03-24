package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement getFormLogin() {
		return driver.findElement(By.id("form-login"));
	}
	
	public WebElement getFormPreCadastro() {
		return driver.findElement(By.id("form-pre-cadastro"));
	}
}
