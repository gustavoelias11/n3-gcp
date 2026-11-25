package br.com.festagestor.domain.aluguel.model;

import br.com.festagestor.domain.aluguel.dto.DadosCadastroAluguel;
import br.com.festagestor.domain.aluguel.dto.DadosCadastroAluguelItem;
import br.com.festagestor.domain.cliente.dto.DadosCadastroCliente;
import br.com.festagestor.domain.cliente.model.Cliente;
import br.com.festagestor.domain.item.model.Brinquedo;
import br.com.festagestor.domain.item.model.Decoracao;
import br.com.festagestor.domain.item.model.Status;
import br.com.festagestor.domain.shared.endereco.DadosCadastroEndereco;
import br.com.festagestor.domain.shared.endereco.Endereco;
import br.com.festagestor.domain.shared.exception.RegraDeNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AluguelTest {

    private Aluguel criarAluguelBase(BigDecimal acrescimo, BigDecimal desconto, BigDecimal frete) {
        var dadosEndereco = new DadosCadastroEndereco(
                "Rua das Flores", "123", "Centro", "Casa", "Joinville", "SC", "89201-000"
        );
        var endereco = new Endereco(dadosEndereco);
        var cliente = new Cliente(new DadosCadastroCliente("Maria Silva", "12345678901", "47999999999", dadosEndereco));

        var entrega = LocalDateTime.of(2026, 12, 10, 10, 0);
        var retirada = LocalDateTime.of(2026, 12, 11, 18, 0);
        var itemDto = new DadosCadastroAluguelItem(1L, 2);

        var dadosAluguel = new DadosCadastroAluguel(
                1L, dadosEndereco, entrega, retirada, acrescimo, desconto, frete, List.of(itemDto)
        );
        var aluguel = new Aluguel(cliente, dadosAluguel, endereco);

        var brinquedo = new Brinquedo(
                "Cama Elástica", "Inflável grande", new BigDecimal("200.00"), Status.DISPONIVEL, 6, "4x4m"
        );
        var decoracao = new Decoracao(
                "Painel Safari", "Painel temático", new BigDecimal("150.00"), Status.DISPONIVEL, "Safari"
        );

        var aluguelItem1 = new AluguelItem(aluguel, brinquedo, new DadosCadastroAluguelItem(1L, 2));
        var aluguelItem2 = new AluguelItem(aluguel, decoracao, new DadosCadastroAluguelItem(2L, 1));
        aluguel.setItens(List.of(aluguelItem1, aluguelItem2));

        return aluguel;
    }

    @Test
    @DisplayName("Deve calcular o valor total somando itens, acréscimo e frete e subtraindo desconto")
    void deveCalcularValorTotalCorretamente() {
        var aluguel = criarAluguelBase(
                new BigDecimal("30.00"),
                new BigDecimal("50.00"),
                new BigDecimal("40.00")
        );

        aluguel.calcularValorTotal();

        // (200 * 2) + (150 * 1) + 30 + 40 - 50 = 570.00
        assertEquals(0, new BigDecimal("570.00").compareTo(aluguel.getValorTotal()));
    }

    @Test
    @DisplayName("Deve permitir fluxo de status PENDENTE -> CONFIRMADO -> MONTADO -> FINALIZADO")
    void deveSeguirFluxoDeStatusValido() {
        var aluguel = criarAluguelBase(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        assertEquals(StatusAluguel.PENDENTE, aluguel.getStatus());
        aluguel.confirmar();
        assertEquals(StatusAluguel.CONFIRMADO, aluguel.getStatus());
        aluguel.montar();
        assertEquals(StatusAluguel.MONTADO, aluguel.getStatus());
        aluguel.finalizar();
        assertEquals(StatusAluguel.FINALIZADO, aluguel.getStatus());
    }

    @Test
    @DisplayName("Não deve permitir cancelar um aluguel já finalizado")
    void naoDeveCancelarAluguelFinalizado() {
        var aluguel = criarAluguelBase(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        aluguel.confirmar();
        aluguel.finalizar();

        assertThrows(RegraDeNegocioException.class, aluguel::cancelar);
    }
}
