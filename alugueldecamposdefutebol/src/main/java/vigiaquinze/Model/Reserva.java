package vigiaquinze.Model;

import java.sql.Date;
import java.sql.Time;

public class Reserva {
    private int id;
    private Date data;
    private Time horaInicio;
    private Time horaFim;
    private Campo campo;
    private Cliente cliente;
    private int precoReserva;

    public Reserva(int id, Date data, Time horaInicio, Time horaFim, int precoReserva, Campo campo, Cliente cliente) {
        this.id = id;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.precoReserva = precoReserva;
        this.campo = campo;
        this.cliente = cliente;
    }    
    

    public Reserva() {
        super();
    }

    
    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Time horaFim) {
        this.horaFim = horaFim;
    }

    public Campo getCampo() {
        return campo;
    }

    public void setCampo(Campo campo) {
        this.campo = campo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getPrecoReserva() {
        return precoReserva;
    }

    public void setPrecoReserva(int precoReserva) {
        this.precoReserva = precoReserva;
    }

}