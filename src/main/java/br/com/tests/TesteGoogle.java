package br.com.tests;

import br.com.core.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static br.com.core.DriverFactory.getDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TesteGoogle extends BaseTest {
    @Test
    @Order(0)
    public void teste() {
        getDriver().get("https://google.com");

        Assertions.assertEquals("Google", getDriver().getTitle());



    }
}
