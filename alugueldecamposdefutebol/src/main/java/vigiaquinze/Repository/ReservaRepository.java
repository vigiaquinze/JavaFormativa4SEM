package vigiaquinze.Repository;

import vigiaquinze.Model.Reserva;
import vigiaquinze.Model.Campo;
import vigiaquinze.Model.Cliente;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ReservaRepository {
    private List<Reserva> reservas = new ArrayList<>();

    public void adicionarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    public Reserva buscarReserva(int id) {
        return reservas.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }

    public List<Reserva> listarReservas() {
        return new ArrayList<>(reservas);
    }

    public void atualizarReserva(Reserva reserva) {
        Reserva existente = buscarReserva(reserva.getId());
        if (existente != null) {
            existente.setData(reserva.getData());
            existente.setHoraInicio(reserva.getHoraInicio());
            existente.setHoraFim(reserva.getHoraFim());
            existente.setCampo(reserva.getCampo());
            existente.setCliente(reserva.getCliente());
            existente.setPrecoReserva(reserva.getPrecoReserva());
        }
    }

    public void deletarReserva(int id) {
        reservas.removeIf(r -> r.getId() == id);
    }

    public boolean verificarHorarioDisponivel(Date data, Time horaInicio, Campo campo) {
        return reservas.stream().noneMatch(r -> 
            r.getData().equals(data) && r.getHoraInicio().equals(horaInicio) && r.getCampo().getId() == campo.getId());
    }
}
