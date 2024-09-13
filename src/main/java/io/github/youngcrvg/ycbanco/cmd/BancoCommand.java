package io.github.youngcrvg.ycbanco.cmd;

import io.github.youngcrvg.ycbanco.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BancoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        Main.getInstance().getBancoGui().open(player);
        return true;
    }
}
