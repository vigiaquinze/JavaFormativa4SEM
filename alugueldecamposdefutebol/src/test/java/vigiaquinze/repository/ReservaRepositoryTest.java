package vigiaquinze.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vigiaquinze.Model.Campo;
import vigiaquinze.Model.Cliente;
import vigiaquinze.Model.Reserva;
import vigiaquinze.Repository.ReservaRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservaRepositoryTest {
    private ReservaRepository reservaRepository;

    @BeforeEach
    void setUp() {
        reservaRepository = new ReservaRepository();
    }

    @Test
    void testAdicionarReserva() {
        Campo campo = new Campo(1, "Campo A", "Local A", 100);
        Cliente cliente = new Cliente(1, "Cliente A", "cliente@example.com", "123456789", "Regular");
        Reserva reserva = new Reserva(1, Date.valueOf("2024-10-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), campo, cliente, 200);
        
        reservaRepository.adicionarReserva(reserva);
        
        Reserva encontrado = reservaRepository.buscarReserva(1);
        assertEquals(reserva, encontrado);
    }

    @Test
    void testBuscarReserva() {
        Campo campo = new Campo(1, "Campo A", "Local A", 100);
        Cliente cliente = new Cliente(1, "Cliente A", "cliente@example.com", "123456789", "Regular");
        Reserva reserva = new Reserva(1, Date.valueOf("2024-10-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), campo, cliente, 200);
        
        reservaRepository.adicionarReserva(reserva);
        
        Reserva encontrado = reservaRepository.buscarReserva(1);
        assertNotNull(encontrado);
        assertEquals("Cliente A", encontrado.getCliente().getNome());
    }

    @Test
    void testListarReservas() {
        Campo campo1 = new Campo(1, "Campo A", "Local A", 100);
        Campo campo2 = new Campo(2, "Campo B", "Local B", 150);
        Cliente cliente = new Cliente(1, "Cliente A", "cliente@example.com", "123456789", "Regular");

        Reserva reserva1 = new Reserva(1, Date.valueOf("2024-10-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), campo1, cliente, 200);
        Reserva reserva2 = new Reserva(2, Date.valueOf("2024-10-02"), Time.valueOf("14:00:00"), Time.valueOf("16:00:00"), campo2, cliente, 300);
        
        reservaRepository.adicionarReserva(reserva1);
        reservaRepository.adicionarReserva(reserva2);

        List<Reserva> reservas = reservaRepository.listarReservas();
        assertEquals(2, reservas.size());
    }

    @Test
    void testAtualizarReserva() {
        Campo campo = new Campo(1, "Campo A", "Local A", 100);
        Cliente cliente = new Cliente(1, "Cliente A", "cliente@example.com", "123456789", "Regular");
        Reserva reserva = new Reserva(1, Date.valueOf("2024-10-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), campo, cliente, 200);
        
        reservaRepository.adicionarReserva(reserva);

        Reserva reservaAtualizada = new Reserva(1, Date.valueOf("2024-10-01"), Time.valueOf("11:00:00"), Time.valueOf("13:00:00"), campo, cliente, 250);
        reservaRepository.atualizarReserva(reservaAtualizada);

        Reserva encontrado = reservaRepository.buscarReserva(1);
        assertEquals(Time.valueOf("11:00:00"), encontrado.getHoraInicio());
        assertEquals(250, encontrado.getPrecoReserva());
    }

    @Test
    void testDeletarReserva() {
        Campo campo = new Campo(1, "Campo A", "Local A", 100);
        Cliente cliente = new Cliente(1, "Cliente A", "cliente@example.com", "123456789", "Regular");
        Reserva reserva = new Reserva(1, Date.valueOf("2024-10-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), campo, cliente, 200);
        
        reservaRepository.adicionarReserva(reserva);

        reservaRepository.deletarReserva(1);
        Reserva encontrado = reservaRepository.buscarReserva(1);
        assertNull(encontrado);
    }

    @Test
    void testVerificarHorarioDisponivel() {
        Campo campo = new Campo(1, "Campo A", "Local A", 100);
        Cliente cliente = new Cliente(1, "Cliente A", "cliente@example.com", "123456789", "Regular");
        Reserva reserva = new Reserva(1, Date.valueOf("2024-10-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), campo, cliente, 200);
        
        reservaRepository.adicionarReserva(reserva);

        assertFalse(reservaRepository.verificarHorarioDisponivel(Date.valueOf("2024-10-01"), Time.valueOf("10:00:00"), campo)); // Horário ocupado
        assertTrue(reservaRepository.verificarHorarioDisponivel(Date.valueOf("2024-10-01"), Time.valueOf("13:00:00"), campo)); // Horário livre
    }
}
