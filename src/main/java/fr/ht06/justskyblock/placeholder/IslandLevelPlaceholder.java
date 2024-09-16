package fr.ht06.justskyblock.placeholder;

import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.IslandManager.IslandManager;
import fr.ht06.justskyblock.JustSkyblock;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IslandLevelPlaceholder extends PlaceholderExpansion {

    IslandManager islandManager = JustSkyblock.islandManager;

    @Override
    public @NotNull String getIdentifier() {
        return "justskyblock";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ht06";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;    //le mettre sur false si on a d'autre plugin externe
    }

    @Override
    public boolean persist() {
        return true;  //toujour le mettre sur true
    }




    @Override
    public @Nullable String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
        if (offlinePlayer != null && offlinePlayer.isOnline()){
            Player player =  offlinePlayer.getPlayer();

            if (params.equalsIgnoreCase("level")){
                //return player.getName();
                if (islandManager.playerHasIsland(player.getName())){
                    Island island = islandManager.getIslandbyplayer(player.getName());
                    return "["+ (int) island.getLevel() +"]"; //String.valueOf(island.getLevel());

                }

                else {
                    return "";
                }
            }
        }
        return null;
    }



}
