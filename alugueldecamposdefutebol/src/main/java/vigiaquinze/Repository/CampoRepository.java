package vigiaquinze.Repository;

import vigiaquinze.Model.Campo;

import java.util.ArrayList;
import java.util.List;

public class CampoRepository {
    private List<Campo> campos = new ArrayList<>();

    public void adicionarCampo(Campo campo) {
        campos.add(campo);
    }

    public Campo buscarCampo(int id) {
        return campos.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public void atualizarCampo(Campo campo) {
        Campo existente = buscarCampo(campo.getId());
        if (existente != null) {
            existente.setNome(campo.getNome());
            existente.setLocal(campo.getLocal());
            existente.setPreco(campo.getPreco());
        }
    }

    public void deletarCampo(int id) {
        campos.removeIf(c -> c.getId() == id);
    }

    public List<Campo> listarCampos() {
        return new ArrayList<>(campos);
    }

    public List<Campo> buscarCamposPorNome(String nome) {
        List<Campo> resultado = new ArrayList<>();
        for (Campo campo : campos) {
            if (campo.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(campo);
            }
        }
        return resultado;
    }
}
