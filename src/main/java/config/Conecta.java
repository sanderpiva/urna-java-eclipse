package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conecta {
    private static final String URL = "jdbc:mysql://localhost:3306/eleicoes_presidenciais";
    private static final String USUARIO = "root";
    private static final String SENHA = ""; // <-- troque pela sua senha

    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado", e);
        }
    }
}
