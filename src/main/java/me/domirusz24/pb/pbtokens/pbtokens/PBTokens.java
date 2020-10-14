package me.domirusz24.pb.pbtokens.pbtokens;

import me.domirusz24.pb.pbtokens.pbtokens.commands.Schowek;
import me.domirusz24.pb.pbtokens.pbtokens.commands.TokenManagmentCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class PBTokens extends JavaPlugin {

    public static ItemStack token;
    public static PBTokens inst;

    @Override
    public void onEnable() {
        System.out.println("PBTokens has been enabled!");
        inst = this;
        this.getCommand("tokensmanage").setExecutor(new TokenManagmentCommand());
        this.getCommand("schowek").setExecutor(new Schowek());
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        getConfig().options().copyDefaults(true);
        getConfig().options().copyDefaults();
        getConfig().addDefault("Token", new ItemStack(Material.GOLD_INGOT, 1));
        getConfig().options().copyDefaults(true);
        getConfig().options().copyDefaults();
        saveConfig();
        token = getConfig().getItemStack("Token");
    }

    public void setItem(ItemStack i) {
        i.setAmount(1);
        token = i;
        getConfig().set("Token", i);
        saveConfig();
    }

    @Override
    public void onDisable() {
        System.out.println("PBTokens has been enabled!");
    }
}
