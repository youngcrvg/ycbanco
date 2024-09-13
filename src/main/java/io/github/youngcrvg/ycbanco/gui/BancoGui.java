package io.github.youngcrvg.ycbanco.gui;

import io.github.youngcrvg.ycbanco.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BancoGui {
    private Inventory inv;
    private final String title = Main.getInstance().getConfig().getString("Gui.banco.nome").replace("&", "§");
    private final int tamanho = Main.getInstance().getConfig().getInt("Gui.banco.tamanho");
    public void open(Player p) {
        inv = Bukkit.createInventory(null, tamanho, title);
        setItens(p);
        p.openInventory(inv);
    }
    private void setItens(Player p) {
        ConfigurationSection section = Main.getInstance().getConfig().getConfigurationSection("Gui.banco.itens");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                int id = Main.getInstance().getConfig().getInt("Gui.banco.itens." + key + ".id");
                short data = (short) Main.getInstance().getConfig().getInt("Gui.banco.itens." + key + ".data");
                @Deprecated
                ItemStack item = new ItemStack(id, 0, data);
                ItemMeta meta  = item.getItemMeta();
                meta.setDisplayName(Main.getInstance().getConfig().getString("Gui.banco.itens." + key + ".nome").replace("&", "§"));
                List<String> lore = new ArrayList<>();
                List<String> loreConfig = Main.getInstance().getConfig().getStringList("Gui.banco.itens." + key + ".lore");
                for(String line : loreConfig) {
                    lore.add(
                            line.replace("&", "§")
                            .replace("{tp}", Main.getInstance().getApi().getFormatBalance().formatNumber(Main.getInstance().cache.get(p.getName()).getTp()))
                    );
                }
                meta.setLore(lore);
                item.setItemMeta(meta);
                int slot = Main.getInstance().getConfig().getInt("Gui.banco.itens." + key + ".slot");
                if(slot !=  -1) {
                    inv.setItem(slot, item);
                }
            }
        }
    }
}
