package vigiaquinze.Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import vigiaquinze.Connection.ConnectionFactory;

public class ConnectionDAO {
    private Connection connection;

    // codigo para o banco de dados
    public ConnectionDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

}

