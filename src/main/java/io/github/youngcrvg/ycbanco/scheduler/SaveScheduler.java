package io.github.youngcrvg.ycbanco.scheduler;

import io.github.youngcrvg.ycbanco.Main;
import io.github.youngcrvg.ycbanco.cache.PlayerCache;
import io.github.youngcrvg.ycbanco.sql.H2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class SaveScheduler implements Runnable {
    private final Main instance = Main.getInstance();
    private final H2 h2 = instance.getH2();
    private Connection connection = instance.getH2().getConnection();

    @Override
    public void run() {
        Map<String, PlayerCache> cacheMap = instance.getCache();
        PreparedStatement statement = null;

        try {
            String updateQuery = "INSERT OR REPLACE INTO ycbanco (player, tp, money) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(updateQuery);

            for (Map.Entry<String, PlayerCache> entry : cacheMap.entrySet()) {
                String playerName = entry.getKey();
                PlayerCache cache = entry.getValue();
                statement.setString(1, playerName);
                statement.setDouble(2, cache.getTp());
                statement.setDouble(3, cache.getMoney());
                statement.executeUpdate();
            }
            System.out.println("[ycBanco] Banco salvo");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
