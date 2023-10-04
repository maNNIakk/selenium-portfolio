package br.com.core;


public class Propriedades {
    //E no caso de false, ele fecha o browser por classe de teste
    public static boolean FECHAR_BROWSER_POR_TESTE = false;

    public static Browsers browser = Browsers.CHROME;
    public enum Browsers {
        CHROME,
        FIREFOX
    }
}
