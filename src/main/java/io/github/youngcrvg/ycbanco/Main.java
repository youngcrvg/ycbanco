package io.github.youngcrvg.ycbanco;

import io.github.youngcrvg.api.YoungAPI;
import io.github.youngcrvg.api.apis.TaskScheduler;
import io.github.youngcrvg.ycbanco.cache.PlayerCache;
import io.github.youngcrvg.ycbanco.cmd.BancoAdminCommand;
import io.github.youngcrvg.ycbanco.cmd.BancoCommand;
import io.github.youngcrvg.ycbanco.listener.BancoChatListener;
import io.github.youngcrvg.ycbanco.listener.BancoMenuListener;
import io.github.youngcrvg.ycbanco.gui.BancoGui;
import io.github.youngcrvg.ycbanco.listener.PlayerListener;
import io.github.youngcrvg.ycbanco.scheduler.SaveScheduler;
import io.github.youngcrvg.ycbanco.sql.H2;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {
    private static Main instance;
    private BancoGui bancoGui;
    private PlayerCache playerCache;
    public HashMap<String, PlayerCache> cache = new HashMap<>();
    private H2 h2;
    private YoungAPI api;

    @Override
    public void onEnable() {
        instance = this;
        api = YoungAPI.getInstance();
        saveDefaultConfig();
        try {
            h2 = new H2();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        playerCache = new PlayerCache();
        bancoGui = new BancoGui();
        getCommand("banco").setExecutor(new BancoCommand());
        getCommand("bancoadmin").setExecutor(new BancoAdminCommand());
        getServer().getPluginManager().registerEvents(new BancoMenuListener(), this);
        getServer().getPluginManager().registerEvents(new BancoChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        BukkitScheduler scheduler = getServer().getScheduler();
        SaveScheduler task = new SaveScheduler();
        scheduler.runTaskTimer(this, task, 100L, 100L);
    }
    @Override
    public void onDisable() {
        save();
    }
    public static Main getInstance() {
        return instance;
    }

    public PlayerCache getPlayerCache() {
        return playerCache;
    }

    public HashMap<String, PlayerCache> getCache() {
        return cache;
    }

    public BancoGui getBancoGui() {
        return bancoGui;
    }

    public H2 getH2() {
        return h2;
    }

    public YoungAPI getApi() {
        return api;
    }

    private void save() {
        Map<String, PlayerCache> cacheMap = instance.getCache();
        PreparedStatement statement = null;

        try {
            String updateQuery = "INSERT OR REPLACE INTO ycbanco (player, tp, money) VALUES (?, ?, ?)";
            statement = h2.getConnection().prepareStatement(updateQuery);

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
