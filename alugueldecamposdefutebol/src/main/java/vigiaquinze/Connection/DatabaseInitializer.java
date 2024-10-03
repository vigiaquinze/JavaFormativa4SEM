package vigiaquinze.Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void criarTabelas() {
        String sqlCliente = "CREATE TABLE IF NOT EXISTS cliente (" +
                "id SERIAL PRIMARY KEY," +
                "nome VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL UNIQUE," +
                "telefone VARCHAR(15)," +
                "tipo VARCHAR(10) CHECK (tipo IN ('ADMIN', 'USER')) NOT NULL" +
                ");";

        String sqlCampo = "CREATE TABLE IF NOT EXISTS campo (" +
                "id SERIAL PRIMARY KEY," +
                "nome VARCHAR(100) NOT NULL," +
                "disponibilidade BOOLEAN NOT NULL DEFAULT TRUE" +
                ");";

        String sqlReserva = "CREATE TABLE IF NOT EXISTS reserva (" +
                "id SERIAL PRIMARY KEY," +
                "cliente_id INT REFERENCES cliente(id) ON DELETE CASCADE," +
                "campo_id INT REFERENCES campo(id) ON DELETE CASCADE," +
                "data DATE NOT NULL," +
                "hora_inicio TIME NOT NULL," +
                "hora_fim TIME NOT NULL," +
                "UNIQUE (cliente_id, data, hora_inicio)" +
                ");";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt1 = conn.prepareStatement(sqlCliente);
             PreparedStatement stmt2 = conn.prepareStatement(sqlCampo);
             PreparedStatement stmt3 = conn.prepareStatement(sqlReserva)) {
            
            stmt1.executeUpdate();
            stmt2.executeUpdate();
            stmt3.executeUpdate();
            System.out.println("Tabelas criadas com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}