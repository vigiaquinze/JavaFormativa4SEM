package vigiaquinze.Repository;

import vigiaquinze.Model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {
    private List<Cliente> clientes = new ArrayList<>();

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Cliente buscarClientePorId(int id) {
        return clientes.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public void atualizarCliente(Cliente cliente) {
        Cliente existente = buscarClientePorId(cliente.getId());
        if (existente != null) {
            existente.setNome(cliente.getNome());
            existente.setEmail(cliente.getEmail());
            existente.setTelefone(cliente.getTelefone());
            existente.setTipo(cliente.getTipo());
        }
    }

    public void deletarCliente(int id) {
        clientes.removeIf(c -> c.getId() == id);
    }

    public List<Cliente> buscarClientesPorNome(String nome) {
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(cliente);
            }
        }
        return resultado;
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }
}
