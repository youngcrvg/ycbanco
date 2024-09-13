package io.github.youngcrvg.ycbanco.listener;

import io.github.youngcrvg.ycbanco.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;


public class BancoMenuListener implements Listener {
    private final String title = Main.getInstance().getConfig().getString("Gui.banco.nome").replace("&", "§");
    @EventHandler
    public void onClickInventory(InventoryClickEvent e) {
        if(e.getInventory().getTitle().equals(title)) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            ItemStack clickedItem = e.getCurrentItem();
            if(clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }
            ItemMeta meta = clickedItem.getItemMeta();
            if(meta.getDisplayName().equals(Main.getInstance().getConfig().getString("Gui.banco.itens.sacar.nome").replace("&", "§"))) {
                p.setMetadata("banco_sacar", new FixedMetadataValue(Main.getInstance(), p));
                p.sendMessage(ChatColor.GREEN+"Digite no chat o valor de saque.");
                p.closeInventory();
            }
            if(meta.getDisplayName().equals(Main.getInstance().getConfig().getString("Gui.banco.itens.depositar.nome").replace("&", "§"))) {
                p.setMetadata("banco_depositar", new FixedMetadataValue(Main.getInstance(), p));
                p.sendMessage(ChatColor.GREEN+"Digite no chat o valor de deposito.");
                p.closeInventory();
            }
        }
    }
}
