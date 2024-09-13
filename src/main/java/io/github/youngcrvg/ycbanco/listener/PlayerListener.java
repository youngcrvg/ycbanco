package io.github.youngcrvg.ycbanco.listener;

import io.github.youngcrvg.ycbanco.Main;
import io.github.youngcrvg.ycbanco.cache.PlayerCache;
import io.github.youngcrvg.ycbanco.sql.H2;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final Main instance = Main.getInstance();
    private final H2 h2 = instance.getH2();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        if (instance.getCache().containsKey(name)) {
            return;
        }
        PlayerCache cache = new PlayerCache();
        if (h2.exists(p)) {
            double tp = h2.get(p, "tp");
            if (Double.isNaN(tp)) {
                tp = 0;
            }
            cache.setTp(tp);
        } else {
            cache.setTp(0);
            cache.setMoney(0);
        }
        instance.getCache().put(name, cache);
    }
}
