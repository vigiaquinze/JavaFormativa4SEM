package vigiaquinze.Control;

import vigiaquinze.Connection.ConnectionFactory;
import vigiaquinze.Model.Relatorio;
import vigiaquinze.Model.Reserva;
import vigiaquinze.Model.Campo;
import vigiaquinze.Model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioController {

    public Relatorio gerarRelatorio() {
        String sql = "SELECT r.*, c.nome AS campo_nome, cl.nome AS cliente_nome " +
                     "FROM reserva r " +
                     "JOIN campo c ON r.campo_id = c.id " +
                     "JOIN cliente cl ON r.cliente_id = cl.id";
        List<Reserva> reservas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Campo campo = new Campo(rs.getInt("campo_id"), rs.getString("campo_nome"), rs.getDouble("preco")); // Ajuste conforme sua classe Campo
                Cliente cliente = new Cliente(rs.getInt("cliente_id"), rs.getString("cliente_nome")); // Ajuste conforme sua classe Cliente
                
                Reserva reserva = new Reserva(
                        rs.getInt("id"),
                        rs.getDate("data"),
                        rs.getTime("hora_inicio"),
                        rs.getTime("hora_fim"),
                        rs.getInt("preco_reserva"),
                        campo,
                        cliente
                );
                reservas.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Relatorio(reservas);
    }
}
