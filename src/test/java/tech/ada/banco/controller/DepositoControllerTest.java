package tech.ada.banco.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import tech.ada.banco.exceptions.ValorInvalidoException;
import tech.ada.banco.model.Conta;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import tech.ada.banco.services.Deposito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DepositoControllerTest extends BaseContaTest {

    private final String baseUri = "/deposito";

    @Test
    void testDepositoContaErrada() throws Exception {
        int contaFake = 30000;

        String response =
                mvc.perform(post(baseUri + "/" + contaFake)
                                .param("valor", "10")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isNotFound())
                        .andReturn().getResponse().getErrorMessage();

        assertEquals("Recurso não encontrado.", response);
    }

    @DisplayName("Depositar valor negativo")
    @Test
    void testDepositoValorNegativo() throws Exception {
        Conta conta = criarConta(BigDecimal.ZERO);

        String response =
                mvc.perform(post(baseUri + "/" + conta.getNumeroConta())
                                .param("valor", "-1")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andReturn().getResponse().getErrorMessage();

        assertEquals("Valor informado está inválido.", response);
    }




}
