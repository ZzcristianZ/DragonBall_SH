package nombreDelPaquete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:usuarios.db"; // Ruta a la base de datos

    public DatabaseManager() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "usuario TEXT NOT NULL UNIQUE,"
                + "contraseña TEXT NOT NULL"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCreateTable);
        } catch (SQLException e) {
            System.out.println("Error creando la tabla: " + e.getMessage());
        }
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.out.println("Error conectando a la base de datos: " + e.getMessage());
        }
        return conn;
    }

    public boolean registrarUsuario(String usuario, String contraseña) {
        String sqlInsert = "INSERT INTO usuarios(usuario, contraseña) VALUES(?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, contraseña);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error registrando usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean validarUsuario(String usuario, String contraseña) {
        String sqlSelect = "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, contraseña);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error validando usuario: " + e.getMessage());
        }
        return false;
    }
}
