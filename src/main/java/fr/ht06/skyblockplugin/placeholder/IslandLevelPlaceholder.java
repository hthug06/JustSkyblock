package fr.ht06.skyblockplugin.placeholder;

import fr.ht06.skyblockplugin.IslandManager.Island;
import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IslandLevelPlaceholder extends PlaceholderExpansion {

    IslandManager islandManager = new IslandManager();

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
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.equalsIgnoreCase("level")){
            //return player.getName();
            if (islandManager.playerHasIsland(player.getName())){
                Island island = islandManager.getIslandbyplayer(player.getName());
                return String.valueOf(island.getLevel()); //String.valueOf(island.getLevel());

            }

            else {
                return String.valueOf(islandManager.getIslandbyplayerUUID(player.getUniqueId()).getLevel());
            }
        }
        return "test";
    }
    /*
    @Override
    public @Nullable String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
        if (offlinePlayer != null && offlinePlayer.isOnline()){
            Player player =  offlinePlayer.getPlayer();

            if (params.equalsIgnoreCase("level")){
                //return player.getName();
                if (islandManager.playerHasIsland(player.getName())){
                    Island island = islandManager.getIslandbyplayer(player.getName());
                    return String.valueOf(island.getLevel()); //String.valueOf(island.getLevel());

                }

                else {
                    return String.valueOf(islandManager.getIslandbyplayer(player.getName()).getLevel());
                }
            }

            if (params.equalsIgnoreCase("test")){
                return "hello";
            }

            if (params.equalsIgnoreCase("test2")){
                return "hello world mdr";
            }
        }
        return null;
    }

     */

}
