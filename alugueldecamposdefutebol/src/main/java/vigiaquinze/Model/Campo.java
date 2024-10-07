package vigiaquinze.Model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Campo {
    private int id;
    private String nome;
    private String local;
    private double preco;
    private List<Reserva> reservas;

    // Construtores
    public Campo(int id, String nome, String local, double preco) {
        this.id = id;
        this.nome = nome;
        this.local = local;
        this.preco = preco;
        this.reservas = new ArrayList<>();
    }

    public Campo(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public Campo() {
        super();
    }

    // Sobrescrevendo o método toString
    @Override
    public String toString() {
        return nome; // Retorna o nome do campo
    }

    // Métodos
    public boolean verificarDisponibilidade(Date data, Time horaInicio, Time horaFim) {
        for (Reserva reserva : reservas) {
            if (reserva.getData().equals(data) &&
                    horaInicio.before(reserva.getHoraFim()) && horaFim.after(reserva.getHoraInicio())) {
                return false;
            }
        }
        return true;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void adicionarReserva(Reserva reserva) {
        if (verificarDisponibilidade(reserva.getData(), reserva.getHoraInicio(), reserva.getHoraFim())) {
            reservas.add(reserva);
        } else {
            throw new IllegalArgumentException("Campo não disponível no horário solicitado");
        }
    }
}
