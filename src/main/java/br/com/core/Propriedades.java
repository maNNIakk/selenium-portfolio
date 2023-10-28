package br.com.core;


public class Propriedades {
    //E no caso de false, ele fecha o browser por classe de teste
    public static boolean BROWSER_POR_TESTE = true;
    public static String BASE_URL = "https://seubarriga.wcaquino.me/login";
    public static String BASE_DATA_PATH = "./src/main/resources/Data/basicTestData.json";

    public static Browsers browser = Browsers.CHROME;
    public enum Browsers {
        CHROME,
        FIREFOX
    }
}
