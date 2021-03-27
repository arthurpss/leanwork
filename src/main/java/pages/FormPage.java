package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FormPage {
	WebDriver driver;

	public FormPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getCadastroForm() {
		return driver.findElement(By.id("form-cadastro"));
	}
	
	public void setNomeCompleto(String nome) {
		driver.findElement(By.id("NomeCompleto-PF")).sendKeys(nome);
	}

	public void setCPF(String cpf) {
		driver.findElement(By.id("CPF")).sendKeys(cpf);
	}

	public void setTelefone(String telefone) {
		driver.findElement(By.xpath("//input[@id='Telefone-PF']")).sendKeys(telefone);
	}

	public void setDataNascimento(String data) {
		driver.findElement(By.id("DataNascimento")).sendKeys(data);
	}

	public void setGenero(String genero) {
		Select select = new Select(driver.findElement(By.id("Sexo")));
		select.selectByValue(genero);
	}

	public void setEscolaridade(String value) {
		String xPath = "//input[@type='radio' and @value='" + value + "']";
		WebElement radioButton = driver.findElement(By.xpath(xPath));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", radioButton);
	}

	public void setPerfis(String[] values) {
		// 7: Consumidor, 8: Profissional da área, 9: Lojista/revendedor
		for (String value : values) {
			String xPath = "//input[@type='checkbox' and @value='" + value + "']";
			WebElement checkbox = driver.findElement(By.xpath(xPath));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", checkbox);
		}
	}

	public void setCep(String cep) {
		driver.findElement(By.id("Cep")).sendKeys(cep);
	}

	public void clickEditarEndereco() {
		String xPath = "//input[@type='button' and @value='Editar']";
		driver.findElement(By.xpath(xPath)).click();
	}
	
	public void clickNaoSeiMeuCEP() {
		driver.findElement(By.linkText("Não sei meu CEP")).click();
	}
	
	public void setLogradouro(String logradouro) {
		driver.findElement(By.id("Endereco")).sendKeys(logradouro);
	}

	public void setNumero(String numero) {
		driver.findElement(By.id("Numero")).sendKeys(numero);
	}

	public void setBairro(String bairro) {
		driver.findElement(By.id("Bairro")).sendKeys(bairro);
	}

	public boolean isInputDisabled(String id) {
		return driver.findElement(By.id(id)).isEnabled();
	}

	public void setCidade(String cidade) {
		driver.findElement(By.id("Cidade")).sendKeys(cidade);
	}

	public void setEstado(String estado) {
		driver.findElement(By.id("Estado")).sendKeys(estado);
	}

	public void setComplemento(String complemento) {
		driver.findElement(By.id("Complemento")).sendKeys(complemento);
	}

	public void setReferencia(String referencia) {
		driver.findElement(By.id("PontoReferencia")).sendKeys(referencia);
	}

	public void setEmail(String email) {
		driver.findElement(By.id("Email")).sendKeys(email);
	}

	public void setSenha(String senha) {
		driver.findElement(By.id("Senha")).sendKeys(senha);
	}
	
	public void setSenha2(String senha2) {
		driver.findElement(By.id("Senha2")).sendKeys(senha2);
	}
	
	public void clickNewsletter() {
		WebElement button =  driver.findElement(By.id("IncluirNewsletter"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", button);
	}

	public void clickConcluirCadastro() {
		WebElement button =  driver.findElement(By.id("confirmarCadastro"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", button);
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
	
	public int getEmptyFields() {
		return driver.findElements(By.className("parsley-required")).size();
	}

	public String getEmptyFieldMessage() {
		return driver.findElement(By.className("parsley-required")).getText();
	}
	
	public void logout() {
		Dimension windowSize = driver.manage().window().getSize();
		if(windowSize.width >= 1200) {
			driver.findElement(By.linkText("Sair")).click();
		} else {
			driver.findElement(By.className("navbar-toggle")).click();
			driver.findElement(By.linkText("Sair")).click();
		} 
	}
	
	public boolean isLogado() {
		Dimension windowSize = driver.manage().window().getSize();
		if(windowSize.width >= 1200) {
			return driver.findElement(By.id("linkUsuario")).isDisplayed();
		} else {
			driver.findElement(By.className("navbar-toggle")).click();
			return driver.findElement(By.className("user-loggedin")).isDisplayed();
		} 
	}
	
	public void closeModal() {
		driver.findElement(By.cssSelector("body > div.bootbox.modal.fade.bootbox-alert.in > div > div > div.modal-footer > button")).click();
	}
}
