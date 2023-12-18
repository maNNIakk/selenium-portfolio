package br.com.tests;

import br.com.core.BaseTest;
import org.junit.jupiter.api.*;

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

}
