package me.domirusz24.pb.pbtokens.pbtokens;

import me.domirusz24.pb.pbtokens.pbtokens.commands.Schowek;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SchowekGUI {
    public static final HashMap<Player, SchowekGUI> guis = new HashMap<>();

    private Player player;
    private Inventory inventory;
    private int tokens = 0;

    public static ItemStack emptyPanel() {
        ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta meta = empty.getItemMeta();
        meta.setDisplayName(" ");
        empty.setDurability((short) 15);
        empty.setItemMeta(meta);
        return empty;
    }

    public static ItemStack getItem(int ID) {
        ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta meta = empty.getItemMeta();
        if (ID == 0) {
            empty.setDurability((short) 13);
            meta.setDisplayName(ChatColor.GREEN + "Kliknij, by dodac do schowka jeden Token");
        } else if (ID == 1) {
            empty.setDurability((short) 5);
            meta.setDisplayName(ChatColor.DARK_GREEN + "Kliknij, by dodac do schowka wszystkie posiadane Tokeny");
        } else if (ID == 2) {
            empty.setDurability((short) 6);
            meta.setDisplayName(ChatColor.RED + "Kliknij, by wziac ze schowka jeden Token");
        } else if (ID == 3) {
            empty.setDurability((short) 14);
            meta.setDisplayName(ChatColor.DARK_RED + "Kliknij, by wziac ze schowka wszystkie Tokeny");
        }
        empty.setItemMeta(meta);
        return empty;
    }

    public SchowekGUI(Player player) {
        this.player = player;
        SchowekGUI.guis.put(player, this);
        tokens = PBUserTokens.getPlayerTokens(player);
        openInventory();
    }

    public ItemStack getPlayerStatus() {
        ItemStack i = PBTokens.token.clone();
        ItemMeta meta = i.getItemMeta();
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Masz aktualnie w schowku " + tokens + " tokenow!"));
        i.setItemMeta(meta);
        return i;
    }

    public void refreshPlayerStatus() {
        inventory.setItem(4, getPlayerStatus());
    }

    public void openInventory() {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BOLD + "" + ChatColor.GOLD + "Tokeny");
        this.inventory = inv;
        ItemStack panel = emptyPanel();
        ItemStack[] con = {panel, getItem(0), getItem(1), panel, getPlayerStatus(), panel, getItem(2), getItem(3), panel};
        inv.setContents(con);
        player.openInventory(inv);
    }

    public void onLeave() {
        PBUserTokens.setPlayerTokens(player, tokens);
        SchowekGUI.guis.remove(player);
    }

    public void transferToken(boolean all) {
        ItemStack[] con = player.getInventory().getContents().clone();
        int amount = 0;
        for (int i = 0; i < con.length; i++) {
            ItemStack item = con[i];
            if (item == null || item.getType().equals(Material.AIR)) {
                continue;
            }
            if (item.getItemMeta() == null) {
                continue;
            }
            if (item.getItemMeta().equals(PBTokens.token.getItemMeta()) && item.getType().equals(PBTokens.token.getType())) {
                if (all) {
                    amount += item.getAmount();
                    player.getInventory().setItem(i, new ItemStack(Material.AIR));
                } else {
                    ItemStack temp = item.clone();
                    temp.setAmount(temp.getAmount() - 1);
                    player.getInventory().setItem(i, temp);
                    amount+= 1;
                    break;
                }
            }
        }
        tokens+= amount;
        refreshPlayerStatus();
    }

    public void getToken(boolean all) {
        if (tokens == 0) {
            return;
        }
        if (all) {
            for (int i = 0; i < tokens; i++) {
                player.getInventory().addItem(PBTokens.token);
            }
            tokens = 0;
        } else {
            player.getInventory().addItem(PBTokens.token);
            tokens--;
        }
        refreshPlayerStatus();
    }

    public void itemClick(InventoryClickEvent event) {
        if (event == null) {
            return;
        }
        if (event.getClickedInventory() == null) {
            return;
        }
        ItemStack item = event.getCurrentItem();
        event.setCancelled(true);
        if (item.getItemMeta() == null) {
            return;
        }
        if (item.getItemMeta().equals(getItem(0).getItemMeta())) {
            transferToken(false);
        } else if (item.getItemMeta().equals(getItem(1).getItemMeta())) {
            transferToken(true);
        } else if (item.getItemMeta().equals(getItem(2).getItemMeta())) {
            getToken(false);
        } else if (item.getItemMeta().equals(getItem(3).getItemMeta())) {
            getToken(true);
        }
    }

    public void dragEvent(InventoryDragEvent event) {
        if (event == null) {
            return;
        }
        if (event.getInventory() == null) {
            return;
        }
        event.setCancelled(true);
    }
}
