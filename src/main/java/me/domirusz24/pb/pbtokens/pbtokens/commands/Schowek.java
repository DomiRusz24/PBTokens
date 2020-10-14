package me.domirusz24.pb.pbtokens.pbtokens.commands;

import me.domirusz24.pb.pbtokens.pbtokens.SchowekGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Schowek implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equalsIgnoreCase("schowek")) {
            if (sender instanceof Player) {
                new SchowekGUI((Player) sender);
                sender.sendMessage("Otworzono schowek!");
            }
        }
        return false;
    }
}
