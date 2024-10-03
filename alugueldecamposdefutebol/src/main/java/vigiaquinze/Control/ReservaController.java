package vigiaquinze.Control;
import vigiaquinze.Connection.ConnectionFactory;
import vigiaquinze.Model.Reserva;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class ReservaController {

    public void adicionarReserva(Reserva reserva) {
        String sql = "INSERT INTO reserva (data, hora_inicio, hora_fim, campo_id, cliente_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, reserva.getData());
            stmt.setTime(2, reserva.getHoraInicio());
            stmt.setTime(3, reserva.getHoraFim());
            stmt.setInt(4, reserva.getCampo().getId());
            stmt.setInt(5, reserva.getCliente().getId());
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
                reserva = new Reserva(
                        rs.getInt("id"),
                        rs.getDate("data"),
                        rs.getTime("hora_inicio"),
                        rs.getTime("hora_fim"),
                        null,  // Deverá buscar o CampoController e ClienteController para obter esses dados
                        null   // Deverá buscar o CampoController e ClienteController para obter esses dados
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reserva;
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
}