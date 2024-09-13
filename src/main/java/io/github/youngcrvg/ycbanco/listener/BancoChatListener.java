package io.github.youngcrvg.ycbanco.listener;

import io.github.youngcrvg.api.YoungAPI;
import io.github.youngcrvg.ycbanco.Main;
import io.github.youngcrvg.ycbanco.powernbt.NBT;
import io.github.youngcrvg.ycbanco.sql.H2;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BancoChatListener implements Listener {
    private final YoungAPI api = Main.getInstance().getApi();
    private final Main instance = Main.getInstance();
    @EventHandler
    public void onAsyncChatEvent(AsyncPlayerChatEvent e) {
        String msg = e.getMessage();
        Player p = e.getPlayer();
        if (p.hasMetadata("banco_sacar")) {
            e.setCancelled(true);
            double actuallyBalance = instance.getCache().get(p.getName()).getTp();
            p.removeMetadata("banco_sacar", Main.getInstance());
            double unformattedValue;
            try {
                unformattedValue = api.getFormatBalance().unformatNumber(msg);
            } catch (IllegalArgumentException ex) {
                if (!onlyNumbers(msg)) {
                    p.sendMessage(ChatColor.RED + "Valor inválido");
                    return;
                }
                unformattedValue = Double.parseDouble(msg);
            }
            if (actuallyBalance < unformattedValue) {
                p.sendMessage(ChatColor.RED + "Você não tem saldo suficente.");
            }
            if(NBT.GetInt(p,"jrmcTpint") + unformattedValue > 2E9) {
                p.sendMessage(ChatColor.RED+"O Valor ultrapassa os 2B");
                return;
            }
            instance.getCache().get(p.getName()).setTp(actuallyBalance - unformattedValue);
            NBT.SetInt(p, "jrmcTpint", (int) unformattedValue);
            p.sendMessage(ChatColor.GREEN + "Você sacou " + api.getFormatBalance().formatNumber(unformattedValue) + " TP's");
        } else if (p.hasMetadata("banco_depoistar")) {
            e.setCancelled(true);
            p.removeMetadata("banco_depoistar", Main.getInstance());
            int actuallyBalance = NBT.GetInt(p, "jrmcTpint");
            double unformattedValue;
            try {
                unformattedValue = api.getFormatBalance().unformatNumber(msg);
            } catch (IllegalArgumentException ex) {
                if (!onlyNumbers(msg)) {
                    p.sendMessage(ChatColor.RED + "Valor inválido");
                    return;
                }
                unformattedValue = Double.parseDouble(msg);
            }
            NBT.SetInt(p, "jrmcTpint", actuallyBalance - (int) unformattedValue);
            instance.getCache().get(p.getName()).setTp(actuallyBalance + unformattedValue);
        }
    }
    public static boolean onlyNumbers(String input) {
        return input.matches("\\d+");
    }
}
