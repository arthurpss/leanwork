package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.FormPage;
import pages.HomePage;
import pages.LoginPage;
import utils.Utils;

class Cadastro {

	static WebDriver driver;
	static HomePage homePage;
	static LoginPage loginPage;
	static FormPage formPage;
	String cepGenerico = "95555000";
	String cpfValido = "46292763005";
	Utils utils = new Utils();

	@BeforeAll
	private static void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Windows\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		formPage = new FormPage(driver);
	}

	@Test
	@Order(1)
	@DisplayName("Deve acessar a página de login/pré-cadastro")
	void deveAcessarTelaDeLogin() {
		driver.get("https://teste.leancommerce.com.br");
		homePage.clickCadastrar();

		assertNotNull(loginPage.getFormLogin());
		assertNotNull(loginPage.getFormPreCadastro());
	}

	@Nested
	@DisplayName("Não deve realizar o pré-cadastro quando:")
	class ValidacoesPreCadastro {
		// Independent principle
		@BeforeEach
		void reset() {
			driver.get("https://teste.leancommerce.com.br");
			homePage.clickCadastrar();
		}

		@Test
		@DisplayName("os dois campos estiverem vazios")
		void deveIdentificarCamposVazios() {
			loginPage.setCpfCnpjInput("");
			loginPage.setCEPInput("");
			loginPage.clickCriarCadastro();

			assertEquals("campo obrigatório.", loginPage.getEmptyFieldMessage());
			assertEquals(2, loginPage.getEmptyFields());
		}

		@Test
		@DisplayName("o CPF/CNPJ for vazio")
		void deveIdentificarCPFCNPJVazio() {
			loginPage.setCpfCnpjInput("");
			loginPage.setCEPInput(cepGenerico);
			loginPage.clickCriarCadastro();

			assertEquals("campo obrigatório.", loginPage.getEmptyFieldMessage());
			assertEquals(1, loginPage.getEmptyFields());
		}

		@Test
		@DisplayName("o CPF/CNPJ for inválido")
		void deveIdentificarCPFInvalido() {
			loginPage.setCpfCnpjInput("00000000000");
			loginPage.setCEPInput(cepGenerico);
			loginPage.clickCriarCadastro();

			assertTrue(loginPage.isModalDisplayed());
			assertTrue(loginPage.getModalTextError().contains("CPF/CNPJ está inválido"));
		}

		@Test
		@DisplayName("o CEP for vazio")
		void deveIdentificarCEPVazio() {
			loginPage.setCpfCnpjInput(cpfValido);
			loginPage.setCEPInput("");
			loginPage.clickCriarCadastro();

			assertEquals("campo obrigatório.", loginPage.getEmptyFieldMessage());
			assertEquals(1, loginPage.getEmptyFields());
		}

		@Test
		@DisplayName("o CEP for inválido")
		void deveIdentificarCEPInvalido() {
			loginPage.setCpfCnpjInput(cpfValido);
			loginPage.setCEPInput("000000");
			loginPage.clickCriarCadastro();

			assertTrue(loginPage.getCEPMinLengthMessage().contains("campo deve ter 9 caracteres"));
		}
	}

	@Nested
	@DisplayName("Não deve finalizar o cadastro quando: ")
	@Disabled
	class ValidacoesCadastro {
		@BeforeEach
		void reset() {
			driver.get("https://teste.leancommerce.com.br");
			homePage.clickCadastrar();
			String cpf = utils.getValidCPF();

			loginPage.setCpfCnpjInput(cpf);
			loginPage.setCEPInput(cepGenerico);
			loginPage.clickCriarCadastro();
		}

		@Test
		@DisplayName("o nome conter números")
		void naoDeveAceitarNumerosNoNome() {
			String email = utils.generateEmail(40);
			String[] perfis = { "8" };
			preencheFormulario("Teste 1", "61996108589", "29121999", "MASCULINO", "Superior Incompleto", perfis,
					"Teste logradouro sem caracteres especiais", "15", "Bairro teste sem caracteres especiais",
					"Complemento teste sem caracteres especiais", "Ponto de referencia teste", email, "123456",
					"123456");

			assertTrue(formPage.isModalDisplayed());
			assertTrue(formPage.getModalTextError()
					.contains("Números e caracteres especiais não são permitidos no nome."));
		}

		@Test
		@DisplayName("a data de nascimento for muito antiga")
		void naoDeveAceitarDataMuitoAntiga() {
			String email = utils.generateEmail(40);
			String[] perfis = { "7", "8" };
			preencheFormulario("Teste data", "61996108589", "01011889", "FEMININO", "Superior Incompleto", perfis,
					"Teste logradouro sem caracteres especiais", "15", "Bairro teste sem caracteres especiais",
					"Complemento teste sem caracteres especiais", "Ponto de referencia teste", email, "123456",
					"123456");

			assertTrue(formPage.isModalDisplayed());
			assertTrue(formPage.getModalTextError().contains("Data de Nascimento inválida"));
		}

		@Test
		@DisplayName("a data de nascimento for maior ou igual à atual")
		void naoDeveAceitarDataAtual() {
			String email = utils.generateEmail(40);
			String[] perfis = { "7", "8" };
			preencheFormulario("Teste data", "61996108589", "01012022", "FEMININO", "Superior Incompleto", perfis,
					"Teste logradouro sem caracteres especiais", "15", "Bairro teste sem caracteres especiais",
					"Complemento teste sem caracteres especiais", "Ponto de referencia teste", email, "123456",
					"123456");

			assertTrue(formPage.isModalDisplayed());
			assertTrue(formPage.getModalTextError().contains("Data de Nascimento inválida"));
		}

		@Test
		@DisplayName("o endereço estiver incompleto")
		void naoDeveAceitarEnderecoIncompleto() {
			String email = utils.generateEmail(40);
			String[] perfis = { "7", "8" };
			preencheFormulario("Teste data", "61996108589", "01012022", "FEMININO", "Superior Incompleto", perfis, "",
					"", "", "Complemento teste sem caracteres especiais", "Ponto de referencia teste", email, "123456",
					"123456");

			assertEquals(3, formPage.getEmptyFields());
			assertEquals("campo obrigatório.", formPage.getEmptyFieldMessage());
		}

		@Test
		@DisplayName("o email já estiver em uso")
		void naoDeveAceitarEmailRepetido() {
			String[] perfis = { "7", "8" };
			preencheFormulario("Teste data", "61996108589", "01012022", "FEMININO", "Superior Incompleto", perfis,
					"teste", "12", "bairro", "Complemento teste sem caracteres especiais", "Ponto de referencia teste",
					"arthur.passos.correa@hotmail.com", "123456", "123456");

			assertTrue(formPage.isModalDisplayed());
			assertTrue(formPage.getModalTextError().contains("E-mail já está em uso"));
		}

		@Test
		@DisplayName("as senhas não baterem")
		void naoDeveAceitarSenhasDiferentes() {
			String[] perfis = { "7", "8" };
			preencheFormulario("Teste data", "61996108589", "01012022", "FEMININO", "Superior Incompleto", perfis,
					"teste", "12", "bairro", "Complemento teste sem caracteres especiais", "Ponto de referencia teste",
					"arthur.passos.correa@hotmail.com", "123456", "123456789");

			assertEquals("Este valor deveria ser igual.", formPage.getEmptyFieldMessage());
		}
	}

	private void preencheFormulario(String nome, String telefone, String data, String genero, String escolaridade,
			String[] perfis, String logradouro, String numero, String bairro, String complemento, String referencia,
			String email, String senha, String senha2) {
		formPage.setNomeCompleto(nome);
		formPage.setTelefone(telefone);
		formPage.setDataNascimento(data);
		formPage.setGenero(genero);
		formPage.setEscolaridade(escolaridade);
		formPage.setPerfis(perfis);
		formPage.setLogradouro(logradouro);
		formPage.setNumero(numero);
		formPage.setBairro(bairro);
		formPage.setComplemento(complemento);
		formPage.setReferencia(referencia);
		formPage.setEmail(email);
		formPage.setSenha(senha);
		formPage.setSenha2(senha2);
		formPage.clickNewsletter();
		formPage.clickConcluirCadastro();
	}

	@Test
	@DisplayName("Deve realizar cadastro de pessoa física")
	void deveRealizarCadastro() {
		driver.get("https://teste.leancommerce.com.br");
		homePage.clickCadastrar();

		// Repeatable principle
		String cpf = utils.getValidCPF();
		String email = utils.generateEmail(40);

		loginPage.setCpfCnpjInput(cpf);
		loginPage.setCEPInput(cepGenerico);
		loginPage.clickCriarCadastro();

		assertNotNull(formPage.getCadastroForm());

		String[] perfis = { "8", "9" };
		preencheFormulario("Arthur Corrêa", "61996108589", "29121999", "MASCULINO", "Superior Incompleto", perfis,
				"Teste logradouro sem caracteres especiais", "15", "Bairro teste sem caracteres especiais",
				"Complemento teste sem caracteres especiais", "Ponto de referencia teste", email, "123456", "123456");
		formPage.clickNewsletter();
		formPage.clickConcluirCadastro();
		formPage.closeModal();
		formPage.logout();
	}

	@AfterAll
	private static void end() {
		driver.quit();
	}
}
