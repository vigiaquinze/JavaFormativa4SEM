package vigiaquinze.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/servico_aluguel_campos";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void createDatabase() {
        try (Connection connection = DriverManager.getConnection(DEFAULT_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Verifica se o banco de dados já existe
            String checkDatabaseQuery = "SELECT 1 FROM pg_database WHERE datname = 'servico_aluguel_campos'";
            var resultSet = statement.executeQuery(checkDatabaseQuery);
            if (!resultSet.next()) {
                // Se o banco de dados não existe, crie-o
                String createDatabaseQuery = "CREATE DATABASE servico_aluguel_campos";
                statement.executeUpdate(createDatabaseQuery);
                System.out.println("Banco de dados criado com sucesso.");
            } else {
                System.out.println("Banco de dados já existe.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                "local VARCHAR(255) NOT NULL," +
                "preco INT NOT NULL," +
                "disponibilidade BOOLEAN NOT NULL DEFAULT TRUE" +
                ");";

        String sqlReserva = "CREATE TABLE IF NOT EXISTS reserva (" +
                "id SERIAL PRIMARY KEY," +
                "cliente_id INT REFERENCES cliente(id) ON DELETE CASCADE," +
                "campo_id INT REFERENCES campo(id) ON DELETE CASCADE," +
                "data DATE NOT NULL," +
                "hora_inicio TIME NOT NULL," +
                "hora_fim TIME NOT NULL," +
                "preco_reserva INT NOT NULL," +
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

    public static void initialize() {
        // Criar banco de dados se não existir
        createDatabase();

        // Criar tabelas no banco de dados
        criarTabelas();
    }
}
