package br.com.core;

import br.com.pages.HomePO;
import br.com.pages.TelaLoginPO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static br.com.core.BaseTest.jsonObject;
import static br.com.core.DriverFactory.getDriver;

public class CustomCommands {
    BasePage basePage = new BasePage();


    public void login(){
        basePage.escrever(By.id(TelaLoginPO.EMAIL_INPUT_ID),jsonObject.get("email").getAsString());
        basePage.escrever(By.id(TelaLoginPO.PWD_INPUT_ID), jsonObject.get("senha").getAsString());
        basePage.clicarBotao(By.xpath(TelaLoginPO.BTN_LOGIN_CLASS));
        basePage.checaUrlAtual("/logar");

    }

    public void cadastraNovaConta(){
        basePage.clicarBotao(By.className(HomePO.CONTAS_DROPDOWN_CLASS));
        basePage.clicarBotao(By.xpath(HomePO.ADICIONAR_CONTA_LINK_X));
        basePage.checaUrlAtual("/addConta");
        basePage.escrever(By.id(HomePO.NOME_CONTA_INPUT_ID),jsonObject.get("conta").getAsString());
        basePage.clicarBotao(By.className(HomePO.BTN_SALVAR_CONTA_CLASS));
        basePage.checaUrlAtual("/salvarConta");
    }

    public void alterarConta(){
        String conta = String.format("//td[text()='%s']",jsonObject.get("conta").getAsString());
        By locatorNomeConta = By.xpath(conta);
        By locatorConta = By.xpath("..//a[contains(@href, '/editarConta')]");

        basePage.clicarBotao(By.className(HomePO.CONTAS_DROPDOWN_CLASS));
        basePage.clicarBotao(By.xpath(HomePO.LISTAR_CONTA_LINK_X));
        WebElement tdConta = getDriver().findElement(locatorNomeConta);
        WebElement linkAlterar = tdConta.findElement(locatorConta);
        linkAlterar.click();
        basePage.escrever(By.id(HomePO.NOME_CONTA_INPUT_ID),jsonObject.get("contaAlt").getAsString());
        basePage.clicarBotao(By.className(HomePO.BTN_SALVAR_CONTA_CLASS));
        basePage.checaUrlAtual("/salvarConta");
    }


}
