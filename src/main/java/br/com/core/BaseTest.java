package br.com.core;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

import static br.com.core.DriverFactory.getDriver;
import static br.com.core.DriverFactory.killDriver;

public class BaseTest {



    @AfterEach
    public void killDriverParaCadaTeste(TestInfo testInfo) throws IOException {
        TakesScreenshot ss = (TakesScreenshot) getDriver();
        File arquivo = ss.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(arquivo, new File("target" + File.separator + "screenshot" + File.separator + testInfo.getDisplayName() + ".jpg"));
        if(Propriedades.FECHAR_BROWSER_POR_TESTE) killDriver();

    }

    @AfterAll
    public static void killDriverParaCadaClasse(){
        if(!Propriedades.FECHAR_BROWSER_POR_TESTE) killDriver();
    }
}
