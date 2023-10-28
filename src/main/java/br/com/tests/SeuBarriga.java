package br.com.tests;

import br.com.core.BaseTest;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeuBarriga extends BaseTest {

    @Test
    @Order(0)
    public void login() throws InterruptedException {
        cmd.login();

    }

    @Test
    @Order(1)
    public void tantoFaz(){
        cmd.login();

    }

}
