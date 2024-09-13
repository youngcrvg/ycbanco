package io.github.youngcrvg.ycbanco.cmd;

import io.github.youngcrvg.ycbanco.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BancoAdminCommand implements CommandExecutor {
    private final Main instance = Main.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("ycbanco.admin")) {
            p.sendMessage(ChatColor.RED+"Sem permissão");
            return true;
        }
        if(args.length != 3) {
            p.sendMessage(ChatColor.RED+"/bancoadmin help");
            return true;
        }
        if(args[0].equalsIgnoreCase("adicionar")) {
            Player target = Bukkit.getPlayer(args[1]);
            double qnt = Double.parseDouble(args[2]);
            if(target == null) {
                p.sendMessage(ChatColor.RED+"Player offline");
                return true;
            }
            instance.getCache().get(target.getName()).setTp(qnt + instance.getCache().get(target.getName()).getTp());
            p.sendMessage("§aSaldo de " + args[1] + " redefinido para §f" + instance.getApi().getFormatBalance().formatNumber(instance.cache.get(args[1]).getTp()));        } else if(args[0].equalsIgnoreCase("remover")) {
            Player target = Bukkit.getPlayer(args[1]);
            double qnt = Double.parseDouble(args[2]);
            if(target == null) {
                p.sendMessage(ChatColor.RED+"Player offline");
                return true;
            }
            if(qnt < instance.getCache().get(target.getName()).getTp()) {
                qnt = 0;
            }
            instance.getCache().get(target.getName()).setTp(qnt - instance.getCache().get(target.getName()).getTp());
            p.sendMessage("§aSaldo de " + args[1] + " redefinido para §f" + instance.getApi().getFormatBalance().formatNumber(instance.cache.get(args[1]).getTp()));        } else if(args[0].equalsIgnoreCase("setar")) {
            Player target = Bukkit.getPlayer(args[1]);
            double qnt = Double.parseDouble(args[2]);
            if(target == null) {
                p.sendMessage(ChatColor.RED+"Player offline");
                return true;
            }
            instance.getCache().get(target.getName()).setTp(qnt);
            p.sendMessage("§aSaldo de " + args[1] + " redefinido para §f" + instance.getApi().getFormatBalance().formatNumber(instance.cache.get(args[1]).getTp()));        }
        return true;
    }
}
