package me.domirusz24.pb.pbtokens.pbtokens.commands;

import me.domirusz24.pb.pbtokens.pbtokens.PBTokens;
import me.domirusz24.pb.pbtokens.pbtokens.PBUserTokens;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.betoncraft.betonquest.database.PlayerData;

public class TokenManagmentCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equalsIgnoreCase("tokensmanage")) {
            if (args.length > 0) {
                if (!sender.hasPermission("PBTokens.Manage")) {
                    sender.sendMessage(ChatColor.RED + "Nie masz wystarczajacych permisji!");
                    return false;
                }
                if (args[0].equalsIgnoreCase("setitem")) {
                    if (sender instanceof Player) {
                        ItemStack i = ((Player) sender).getInventory().getItemInMainHand();
                        if (i == null || i.getType().equals(Material.AIR)) {
                            sender.sendMessage(ChatColor.RED + "Prosze wybrac item!");
                        } else {
                            PBTokens.inst.setItem(i);
                            sender.sendMessage(ChatColor.GREEN + "Ustawiono token!");
                        }
                    } else {
                        sender.sendMessage("Od kiedy konsola ma ekwipunek?");
                    }
                } else {
                    Player p = Bukkit.getServer().getPlayer(args[0]);
                    if (p == null) {
                        sender.sendMessage(ChatColor.RED + "Player " + args[0] + " nie jest online!");
                    } else if (args.length == 1) {
                        sender.sendMessage(ChatColor.BLUE + "Gracz " + p.getName() + " ma " + PBUserTokens.getPlayerTokens(p) + " tokenow!");
                    } else if (args.length == 3) {
                        int value;
                        try {
                            value = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + "Poprawna forma komendy: /pbtokens " + p.getName() + " (SET, ADD) (ILOSC)");
                            return false;
                        }
                        if (args[1].equalsIgnoreCase("set")) {
                            PBUserTokens.setPlayerTokens(p, value);
                            sender.sendMessage(ChatColor.BLUE + "Sukcess!");
                        } else if (args[1].equalsIgnoreCase("add")) {
                            PBUserTokens.addPlayerTokens(p, value);
                            sender.sendMessage(ChatColor.BLUE + "Sukcess!");
                        } else {
                            sender.sendMessage(ChatColor.RED + "Poprawna forma komendy: /pbtokens " + p.getName() + " (SET, ADD) (ILOSC)");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Poprawna forma komendy: /pbtokens " + p.getName() + " (SET, ADD) (ILOSC)");
                    }
                }
            } else {
                if (sender instanceof Player) {
                    sender.sendMessage(ChatColor.BLUE + "Masz aktualnie " + PBUserTokens.getPlayerTokens((Player) sender) + " tokenow!");
                } else {
                    sender.sendMessage("Od kiedy konsola gra w PB?");
                }
            }
        }
        return false;
    }
}
