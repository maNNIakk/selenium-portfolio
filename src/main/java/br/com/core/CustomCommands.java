package br.com.core;

import org.openqa.selenium.By;

import static br.com.core.BaseTest.jsonObject;

public class CustomCommands {
    BasePage basePage = new BasePage();

    public void login(){
        basePage.escrever(By.id("email"),jsonObject.get("email").getAsString());
        basePage.escrever(By.id("senha"), jsonObject.get("senha").getAsString());
        basePage.clicarBotao(By.cssSelector("button[type='submit'].btn.btn-primary"));
    }
}
