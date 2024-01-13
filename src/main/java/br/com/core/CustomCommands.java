package br.com.core;

import br.com.pages.HomePO;
import br.com.pages.TelaLoginPO;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;

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

    public void inserirMovimentacao(String tipoDespesa ){
        basePage.clicarBotao(By.xpath("//a[contains(@href, '/movimentacao')]"));
        basePage.selecionarCombo("tipo",tipoDespesa);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String todayString = today.format(formatter);
        basePage.escrever(By.id("data_transacao"),todayString);

        // Get a date 2 days in the future
        LocalDate futureDate = today.plusDays(2); // Change 2 to 3 if you want 3 days in the future
        String futureDateString = futureDate.format(formatter);
        JsonObject movimentacaoMock =  jsonObject.getAsJsonObject("movimentacao");
        basePage.escrever(By.id("data_pagamento"),futureDateString);
        basePage.escrever(By.id("descricao"),movimentacaoMock.get("descricao").getAsString());
        basePage.escrever(By.id("interessado"),movimentacaoMock.get("interessado").getAsString());
        basePage.escrever(By.id("valor"),movimentacaoMock.get("valor").getAsString());
        basePage.clicarBotao(By.id(movimentacaoMock.get("situacao").getAsString()));
        basePage.clicarBotao(By.cssSelector(".btn-primary"));
    }

    public void verificaCamposObrigatoriosMovimentacao(){
        basePage.clicarBotao(By.xpath("//a[contains(@href, '/movimentacao')]"));
        basePage.clicarBotao(By.cssSelector(".btn-primary"));
        WebElement dangerText = getDriver().findElement(By.className("alert-danger"));
        List<WebElement> itensValidados = dangerText.findElements(By.tagName("li"));
        Assertions.assertTrue(dangerText.isDisplayed());
        for (WebElement itemValidaddo : itensValidados){
            Assertions.assertTrue(itemValidaddo.isDisplayed());
        }
    }

    public void removeMovimentacao(){
        basePage.clicarBotao(By.xpath("//a[contains(@href, '/extrato')]"));
        JsonObject movimentacaoMock =  jsonObject.getAsJsonObject("movimentacao");
        String descricaoProcurada = String.format("//tr[td[1]='%s']", movimentacaoMock.get("descricao").getAsString());
        WebElement linhaProcurada = getDriver().findElement(By.xpath(descricaoProcurada));
        linhaProcurada.findElement(By.cssSelector("a[href*='/removerMovimentacao']")).click();
    }

    public void removerConta(){
        String conta = String.format("//td[text()='%s']",jsonObject.get("contaDeletar").getAsString());
        By locatorNomeConta = By.xpath(conta);
        By locatorConta = By.xpath("..//a[contains(@href, '/removerConta')]");

        basePage.clicarBotao(By.className(HomePO.CONTAS_DROPDOWN_CLASS));
        basePage.clicarBotao(By.xpath(HomePO.LISTAR_CONTA_LINK_X));
        WebElement tdConta = getDriver().findElement(locatorNomeConta);
        tdConta.findElement(locatorConta).click();
        WebElement dangerText = getDriver().findElement(By.className("alert-danger"));
        Assertions.assertTrue(dangerText.isDisplayed());
    }

    public void verificaSaldo() {
        String tituloAtual = getDriver().getTitle();
        Assertions.assertTrue(tituloAtual.contains("Home"));

        boolean accountFound = false;

        for (WebElement row : getDriver().findElements(By.cssSelector("#tabelaSaldo tbody tr"))) {
            String contaValue = row.findElement(By.cssSelector("td:first-child")).getText().trim();

            if (contaValue.equals(jsonObject.get("contaAlt").getAsString())) {
                accountFound = true;

                String valor = row.findElement(By.cssSelector("td:nth-child(2)")).getText().trim();

                double expectedValue = 12.35;
                double actualValue = Double.parseDouble(valor);

                Assertions.assertEquals(expectedValue, actualValue, 0.01); // Provide a delta for double comparison
            }
        }
        // Check if the account was not found
        if (!accountFound) {
            System.out.println("Account not found");
            Assertions.fail("Test failed because the account was not found");
        }
    }

}
