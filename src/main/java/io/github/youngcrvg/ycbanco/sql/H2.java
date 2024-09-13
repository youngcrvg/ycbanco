package io.github.youngcrvg.ycbanco.sql;

import io.github.youngcrvg.ycbanco.Main;
import org.bukkit.entity.Player;

import java.sql.*;

public class H2 {
    private Connection connection;
    public H2() throws SQLException, ClassNotFoundException {
        loadSQLite();
    }
    private void loadSQLite() throws SQLException, ClassNotFoundException {
        String url = "jdbc:sqlite:" + Main.getInstance().getDataFolder().getAbsolutePath() + "/database.db";
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection(url);
        initializeTables();
    }

    private void initializeTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createPlayersTable = "CREATE TABLE IF NOT EXISTS ycbanco (" +
                    "player VARCHAR(16) PRIMARY KEY, " +
                    "tp INT DEFAULT 0, " +
                    "money INT DEFAULT 0 " +
                    ");";
            statement.executeUpdate(createPlayersTable);
        }
    }
    public void register(Player p) {
        String sql = "INSERT INTO ycbanco (player, tp) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setInt(2, 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean exists(Player p) {
        String sql = "SELECT * FROM ycbanco WHERE player = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getName());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void edit(Player p, double value, String column) {
        if (!column.equals("tp") && !column.equals("money")) {
            throw new IllegalArgumentException("Coluna inválida. Use 'tp' ou 'money'.");
        }
        String sql = "UPDATE ycbanco SET " + column + " = ? WHERE player = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, value);
            ps.setString(2, p.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public double get(Player p, String column) {
        if (!column.equals("tp") && !column.equals("money")) {
            throw new IllegalArgumentException("Coluna inválida. Use 'tp' ou 'money'.");
        }
        String sql = "SELECT " + column + " FROM ycbanco WHERE player = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getName());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(column);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public Connection getConnection() {
        return connection;
    }
}
