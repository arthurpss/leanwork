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

	public void clickCriarCadastro() {
		driver.findElement(By.className("btn-register")).click();
	}

	public void setCpfCnpjInput(String cpf_cnpj) {
		driver.findElement(By.id("cpfCnpj")).sendKeys(cpf_cnpj);
	}

	public void setCEPInput(String cep) {
		driver.findElement(By.id("inputCalcularFreteCep")).sendKeys(cep);
	}

	public void closeModal() {
		driver.findElement(By.cssSelector("button[text='Ok']")).click();
	}

	public int getEmptyFields() {
		return driver.findElements(By.className("parsley-required")).size();
	}

	public String getEmptyFieldMessage() {
		return driver.findElement(By.className("parsley-required")).getText();
	}

	public String getCEPMinLengthMessage() {
		return driver.findElement(By.className("parsley-minlength")).getText();
	}

	public String getModalTextError() {
		String selector = "body > div.bootbox.modal.fade.bootbox-alert.in > div > div > div.modal-body > div";
		return driver.findElement(By.cssSelector(selector)).getText();
	}

	public boolean isModalDisplayed() {
		return driver
				.findElement(
						By.cssSelector("body > div.bootbox.modal.fade.bootbox-alert.in > div > div > div.modal-body"))
				.isDisplayed();
	}
}
