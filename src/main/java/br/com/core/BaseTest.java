package br.com.core;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static br.com.core.DriverFactory.getDriver;
import static br.com.core.DriverFactory.killDriver;

public class BaseTest {
    protected static JsonObject jsonObject;
    protected static CustomCommands cmd;
    protected static BasePage basePage;
    @BeforeAll
    public static void setupAll(){
         cmd = new CustomCommands();
         basePage = new BasePage();
        try {
            String jsonFilePath = Propriedades.BASE_DATA_PATH;
            String jsonContent = Files.readString(Paths.get(jsonFilePath));
            jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(!Propriedades.BROWSER_POR_TESTE) getDriver().get(Propriedades.BASE_URL);

    }

    @BeforeEach
    public void setupEach(){
        if(Propriedades.BROWSER_POR_TESTE) getDriver().get(Propriedades.BASE_URL);
    }



    @AfterEach
    public void killDriverParaCadaTeste(TestInfo testInfo) throws IOException {
        TakesScreenshot ss = (TakesScreenshot) getDriver();
        File arquivo = ss.getScreenshotAs(OutputType.FILE);
        File screenshotDir = new File("target" + File.separator + "screenshot");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();}
        FileUtils.copyFile(arquivo, new File("target" + File.separator + "screenshot" + File.separator + testInfo.getDisplayName() + ".jpg"));
        if(Propriedades.BROWSER_POR_TESTE) killDriver();

    }

    @AfterAll
    public static void killDriverParaCadaClasse(){
        if(!Propriedades.BROWSER_POR_TESTE) killDriver();
    }
}
