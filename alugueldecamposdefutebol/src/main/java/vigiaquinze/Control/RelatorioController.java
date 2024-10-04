package vigiaquinze.Control;

import vigiaquinze.Connection.ConnectionFactory;
import vigiaquinze.Model.Relatorio;
import vigiaquinze.Model.Reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioController {

    public Relatorio gerarRelatorio() {
        String sql = "SELECT * FROM reserva";
        List<Reserva> reservas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Reserva reserva = new Reserva(
                        rs.getInt("id"),
                        rs.getDate("data"),
                        rs.getTime("hora_inicio"),
                        rs.getTime("hora_fim"),
                        rs.getInt("preco_reserva"),
                        null,  // Deverá buscar o CampoController para obter esses dados
                        null   // Deverá buscar o ClienteController para obter esses dados
                );
                reservas.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Relatorio(reservas);
    }
}