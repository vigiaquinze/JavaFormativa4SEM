package vigiaquinze.Control;

import vigiaquinze.Connection.ConnectionFactory;
import vigiaquinze.Model.Reserva;
import vigiaquinze.Model.Cliente;
import vigiaquinze.Model.Campo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ReservaController {

    public void adicionarReserva(Reserva reserva) {
        String sql = "INSERT INTO reserva (data, hora_inicio, hora_fim, campo_id, cliente_id, preco_reserva) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, reserva.getData());
            stmt.setTime(2, reserva.getHoraInicio());
            stmt.setTime(3, reserva.getHoraFim());
            stmt.setInt(4, reserva.getCampo().getId());
            stmt.setInt(5, reserva.getCliente().getId());
            stmt.setInt(6, reserva.getPrecoReserva());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Reserva buscarReserva(int id) {
        String sql = "SELECT * FROM reserva WHERE id = ?";
        Reserva reserva = null;
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                int campoId = rs.getInt("campo_id"); // Obtenha o campo_id
                Campo campo = new CampoController().buscarCampo(campoId); // Busque o Campo
    
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = new ClienteController().buscarClientePorId(clienteId); // Corrigido para buscar cliente por ID
    
                reserva = new Reserva(
                    rs.getInt("id"),
                    rs.getDate("data"),
                    rs.getTime("hora_inicio"),
                    rs.getTime("hora_fim"),
                    rs.getInt("preco_reserva"),
                    campo,  // Deve ter um Campo encontrado
                    cliente // Deve ter um Cliente encontrado
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return reserva; // Se a reserva não for encontrada, retorna null
    }
    

    public List<Reserva> listarReservas() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reserva";
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                int campoId = rs.getInt("campo_id");
                Campo campo = new CampoController().buscarCampo(campoId); // Buscando campo
    
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = new ClienteController().buscarClientePorId(clienteId); // Buscando cliente
    
                reservas.add(new Reserva(
                    rs.getInt("id"),
                    rs.getDate("data"),
                    rs.getTime("hora_inicio"),
                    rs.getTime("hora_fim"),
                    rs.getInt("preco_reserva"),
                    campo,  // Pode ser null se não encontrado
                    cliente // Pode ser null se não encontrado
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return reservas;
    }
    

    public void atualizarReserva(Reserva reserva) {
        String sql = "UPDATE reserva SET data = ?, hora_inicio = ?, hora_fim = ?, campo_id = ?, cliente_id = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, reserva.getData());
            stmt.setTime(2, reserva.getHoraInicio());
            stmt.setTime(3, reserva.getHoraFim());
            stmt.setInt(4, reserva.getCampo().getId());
            stmt.setInt(5, reserva.getCliente().getId());
            stmt.setInt(6, reserva.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarReserva(int id) {
        String sql = "DELETE FROM reserva WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verificarHorarioDisponivel(Date data, Time horaInicio, Campo campo) {
        // Substitua a lógica atual por algo assim
        String sql = "SELECT COUNT(*) FROM reserva WHERE data = ? AND hora_inicio = ? AND campo_id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, data);
            pstmt.setTime(2, horaInicio);
            pstmt.setInt(3, campo.getId()); // Ou como você estiver identificando o campo
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Retorna verdadeiro se não houver reservas
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
}
