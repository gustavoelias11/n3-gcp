package br.com.festagestor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FestagestorApplicationTests {

    @Test
    @DisplayName("Classe principal da aplicação deve estar disponível")
    void applicationClassDeveExistir() {
        assertNotNull(FestagestorApplication.class);
    }
}
