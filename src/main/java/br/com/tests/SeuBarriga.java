package br.com.tests;

import br.com.core.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static br.com.core.DriverFactory.getDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeuBarriga extends BaseTest {

    @Test
    @Order(0)
    @DisplayName("Realiza Login")
    public void login() {
        cmd.login();
    }

    @Test
    @Order(1)
    @DisplayName("Cria Conta")
    public void criaConta(){
        cmd.login();
        cmd.cadastraNovaConta();

    }

    @Test
    @Order(2)
    @DisplayName("Altera Conta")
    public void alteraConta(){
        cmd.login();
        cmd.alterarConta();

    }

    @Test
    @Order(3)
    @DisplayName("Inserir Movimentação")
    public void insereMovimentacao(){
        cmd.login();
        cmd.inserirMovimentacao("Receita");
    }

    @Test
    @Order(4)
    @DisplayName("Verifica campos obrigatórios da movimentação")
    public void verificaCamposObrigatorios(){
        cmd.login();
        cmd.verificaCamposObrigatoriosMovimentacao();
    }

    @Test
    @Order(5)
    @DisplayName("Verifica o bloqueio de data de movimentações com datas futuras")
    public void bloqueiaTransacaoFutura(){
        cmd.login();
        basePage.clicarBotao(By.xpath("//a[contains(@href, '/movimentacao')]"));
        LocalDate weekAhead = LocalDate.now().plusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String weekAheadString = weekAhead.format(formatter);
        basePage.escrever(By.id("data_transacao"),weekAheadString);
        basePage.clicarBotao(By.cssSelector(".btn-primary"));
        WebElement dangerText = getDriver().findElement(By.className("alert-danger"));
        List<WebElement> itensValidados = dangerText.findElements(By.tagName("li"));
        boolean foundCorrectText = false;
        for(WebElement item : itensValidados){
            if(item.getText().equals("Data da Movimentação deve ser menor ou igual à data atual")){
                foundCorrectText = true;
                break;
            }
        }
        Assertions.assertTrue(foundCorrectText);
    }

    @Test
    @Order(6)
    @DisplayName("Remove Movimentação")
    public void removeMovimentacao(){
        cmd.login();
        cmd.inserirMovimentacao("Receita");
        cmd.removeMovimentacao();
    }

    @Test
    @Order(7)
    @DisplayName("Remover conta com movimentação")
    public void removeContaComMovimentacao(){
        cmd.login();
        cmd.removerConta();
    }

}
