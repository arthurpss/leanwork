package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	WebDriver driver;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	//Se a largura for menor que 1200px, o botão para acessar a tela de login fica dentro do "hamburguer"
	//TODO: Tornar assincrono o clique quando a tela estiver responsiva
	private WebElement getCadastrarLink() {
		Dimension windowSize = driver.manage().window().getSize();
		if(windowSize.width >= 1200) {
			return driver.findElement(By.linkText("Cadastre-se"));	
		} else {
			driver.findElement(By.className("navbar-toggle")).click();
			return driver.findElement(By.linkText("Entre ou cadastre-se aqui"));
		} 
	}
	
	public void clickCadastrar() {
		WebElement cadastrar = getCadastrarLink();
		cadastrar.click();
	}
}
