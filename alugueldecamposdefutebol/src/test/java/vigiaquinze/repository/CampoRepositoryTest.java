package vigiaquinze.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vigiaquinze.Model.Campo;
import vigiaquinze.Repository.CampoRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CampoRepositoryTest {
    private CampoRepository campoRepository;

    @BeforeEach
    void setUp() {
        campoRepository = new CampoRepository();
    }

    @Test
    void testAdicionarCampo() {
        Campo campo = new Campo(1, "Campo A", "Local A", 100.0);
        campoRepository.adicionarCampo(campo);
        
        Campo encontrado = campoRepository.buscarCampo(1);
        assertEquals(campo, encontrado);
    }

    @Test
    void testBuscarCampoPorId() {
        Campo campo = new Campo(1, "Campo A", "Local A", 100.0);
        campoRepository.adicionarCampo(campo);
        
        Campo encontrado = campoRepository.buscarCampo(1);
        assertNotNull(encontrado);
        assertEquals("Campo A", encontrado.getNome());
    }

    @Test
    void testAtualizarCampo() {
        Campo campo = new Campo(1, "Campo A", "Local A", 100.0);
        campoRepository.adicionarCampo(campo);

        Campo campoAtualizado = new Campo(1, "Campo Atualizado", "Local A", 150.0);
        campoRepository.atualizarCampo(campoAtualizado);

        Campo encontrado = campoRepository.buscarCampo(1);
        assertEquals("Campo Atualizado", encontrado.getNome());
        assertEquals(150.0, encontrado.getPreco());
    }

    @Test
    void testDeletarCampo() {
        Campo campo = new Campo(1, "Campo A", "Local A", 100.0);
        campoRepository.adicionarCampo(campo);

        campoRepository.deletarCampo(1);
        Campo encontrado = campoRepository.buscarCampo(1);
        assertNull(encontrado);
    }

    @Test
    void testListarCampos() {
        Campo campo1 = new Campo(1, "Campo A", "Local A", 100.0);
        Campo campo2 = new Campo(2, "Campo B", "Local B", 150.0);
        campoRepository.adicionarCampo(campo1);
        campoRepository.adicionarCampo(campo2);

        List<Campo> campos = campoRepository.listarCampos();
        assertEquals(2, campos.size());
    }

    @Test
    void testBuscarCamposPorNome() {
        Campo campo1 = new Campo(1, "Campo A", "Local A", 100.0);
        Campo campo2 = new Campo(2, "Campo B", "Local B", 150.0);
        campoRepository.adicionarCampo(campo1);
        campoRepository.adicionarCampo(campo2);

        List<Campo> resultado = campoRepository.buscarCamposPorNome("Campo A");
        assertEquals(1, resultado.size());
        assertEquals("Campo A", resultado.get(0).getNome());
    }
}
