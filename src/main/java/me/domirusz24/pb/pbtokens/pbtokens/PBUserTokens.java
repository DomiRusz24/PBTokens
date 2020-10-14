package me.domirusz24.pb.pbtokens.pbtokens;

import org.bukkit.entity.Player;
import pl.betoncraft.betonquest.database.PlayerData;

import java.util.ArrayList;
import java.util.List;

public class PBUserTokens {

    public static void setPlayerTokens(Player player, int value) {
        if (value < 0) {
            return;
        }
        PlayerData p = new PlayerData(player.getUniqueId().toString());
        List<String> tags = p.getTags();
        List<String> tokens = new ArrayList<>();
        for (String t : tags) {
            
            if (t.contains("token_")) {
                tokens.add(t);
            }
        }
        tokens.forEach(p::removeTag);
        p.addTag("token_pb.x" + value + "x");
        p.loadAllPlayerData();
    }

    public static int getPlayerTokens(Player player) {
        PlayerData p = new PlayerData(player.getUniqueId().toString());
        List<String> tags = p.getTags();
        
        List<String> tokens = new ArrayList<>();
        for (String t : tags) {
            
            if (t.contains("token_")) {
                String e;
                e = t.substring(10);
                
                e = e.replace("x", "");
                tokens.add(e);
            }
        }
        int tok = 0;
        for (String token : tokens) {
            try {
                tok+= Integer.parseInt(token);
            } catch (NumberFormatException ignored){}
            p.removeTag("token_pb.x" + token + "x");
        }
        
        p.addTag("token_pb.x" + tok + "x");
        p.loadAllPlayerData();
        return tok;
    }

    public static void addPlayerTokens(Player player, int vault) {
        PlayerData p = new PlayerData(player.getUniqueId().toString());
        List<String> tags = p.getTags();
        List<String> tokens = new ArrayList<>();
        for (String t : tags) {
            if (t.contains("pb_t")) {
                String e;
                e = t.substring(10);
                e = e.replace("x", "");
                tokens.add(e);
            }
        }
        int tok = 0;
        for (String token : tokens) {
            try {
                tok+= Integer.parseInt(token);
            } catch (NumberFormatException ignored){}
            p.removeTag("token_pb.x" + token + "x");
        }
        if ((tok + vault) <= 0) {
            p.addTag("token_pb.x0x");
        } else {
            p.addTag("token_pb.x" + (tok + vault) + "x");
        }
        p.loadAllPlayerData();
    }
}
