package br.com.festagestor.domain.item.model;

import br.com.festagestor.domain.shared.exception.RegraDeNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemTest {

    @Test
    @DisplayName("Deve colocar item disponível em manutenção")
    void deveColocarItemDisponivelEmManutencao() {
        var brinquedo = new Brinquedo(
                "Tobogã Inflável", "Tobogã grande", new BigDecimal("350.00"), Status.DISPONIVEL, 8, "5x3m"
        );

        brinquedo.colocarEmManutencao();

        assertEquals(Status.MANUTENCAO, brinquedo.getStatus());
    }

    @Test
    @DisplayName("Não deve colocar item alugado em manutenção")
    void naoDeveColocarItemAlugadoEmManutencao() {
        var brinquedo = new Brinquedo(
                "Tobogã Inflável", "Tobogã grande", new BigDecimal("350.00"), Status.ALUGADO, 8, "5x3m"
        );

        assertThrows(RegraDeNegocioException.class, brinquedo::colocarEmManutencao);
    }
}
