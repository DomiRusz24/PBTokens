package me.domirusz24.pb.pbtokens.pbtokens;

import me.domirusz24.pb.pbtokens.pbtokens.commands.Schowek;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class GUIListener implements Listener {

    @EventHandler
    public void onLeave(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            SchowekGUI gui = SchowekGUI.guis.get(event.getPlayer());
            if (gui != null) {
                gui.onLeave();
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            SchowekGUI gui = SchowekGUI.guis.get(event.getWhoClicked());
            if (gui != null) {
                gui.itemClick(event);
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            SchowekGUI gui = SchowekGUI.guis.get(event.getWhoClicked());
            if (gui != null) {
                gui.dragEvent(event);
            }
        }
    }


}
