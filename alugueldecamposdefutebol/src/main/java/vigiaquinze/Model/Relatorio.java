package vigiaquinze.Model;
import java.util.List;

public class Relatorio {
    private List<Reserva> reservas;

    public Relatorio(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void gerarRelatorio() {
        // Código para gerar o relatório
        // Por exemplo, pode gerar uma string ou um arquivo com informações das reservas
    }

    // Getters e setters
    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

}