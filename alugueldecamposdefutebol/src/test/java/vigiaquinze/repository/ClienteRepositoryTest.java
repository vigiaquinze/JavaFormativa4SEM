package vigiaquinze.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vigiaquinze.Model.Cliente;
import vigiaquinze.Repository.ClienteRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteRepositoryTest {
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        clienteRepository = new ClienteRepository();
    }

    @Test
    void testAdicionarCliente() {
        Cliente cliente = new Cliente(1, "Cliente A", "cliente@example.com", "123456789", "Regular");
        clienteRepository.adicionarCliente(cliente);

        Cliente encontrado = clienteRepository.buscarClientePorId(1);
        assertEquals(cliente, encontrado);
    }

    @Test
    void testBuscarClientePorId() {
        Cliente cliente = new Cliente(1, "Cliente A", "cliente@example.com", "123456789", "Regular");
        clienteRepository.adicionarCliente(cliente);

        Cliente encontrado = clienteRepository.buscarClientePorId(1);
        assertNotNull(encontrado);
        assertEquals("Cliente A", encontrado.getNome());
    }

    @Test
    void testAtualizarCliente() {
        Cliente cliente = new Cliente(1, "Cliente A", "cliente@example.com", "123456789", "Regular");
        clienteRepository.adicionarCliente(cliente);

        Cliente clienteAtualizado = new Cliente(1, "Cliente Atualizado", "atualizado@example.com", "987654321", "VIP");
        clienteRepository.atualizarCliente(clienteAtualizado);

        Cliente encontrado = clienteRepository.buscarClientePorId(1);
        assertEquals("Cliente Atualizado", encontrado.getNome());
        assertEquals("atualizado@example.com", encontrado.getEmail());
    }

    @Test
    void testDeletarCliente() {
        Cliente cliente = new Cliente(1, "Cliente A", "cliente@example.com", "123456789", "Regular");
        clienteRepository.adicionarCliente(cliente);

        clienteRepository.deletarCliente(1);
        Cliente encontrado = clienteRepository.buscarClientePorId(1);
        assertNull(encontrado);
    }

    @Test
    void testListarClientes() {
        Cliente cliente1 = new Cliente(1, "Cliente A", "cliente@example.com", "123456789", "Regular");
        Cliente cliente2 = new Cliente(2, "Cliente B", "clienteB@example.com", "987654321", "VIP");
        clienteRepository.adicionarCliente(cliente1);
        clienteRepository.adicionarCliente(cliente2);

        List<Cliente> clientes = clienteRepository.listarClientes();
        assertEquals(2, clientes.size());
    }

    @Test
    void testBuscarClientesPorNome() {
        Cliente cliente1 = new Cliente(1, "Cliente A", "cliente@example.com", "123456789", "Regular");
        Cliente cliente2 = new Cliente(2, "Cliente B", "clienteB@example.com", "987654321", "VIP");
        clienteRepository.adicionarCliente(cliente1);
        clienteRepository.adicionarCliente(cliente2);

        List<Cliente> resultado = clienteRepository.buscarClientesPorNome("Cliente A");
        assertEquals(1, resultado.size());
        assertEquals("Cliente A", resultado.get(0).getNome());
    }
}
