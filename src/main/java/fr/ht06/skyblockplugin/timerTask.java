package fr.ht06.skyblockplugin;

import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class timerTask extends BukkitRunnable {
    IslandManager islandManager = SkyblockPlugin.islandManager;
    String islandInviter;
    Player invited;
    String invitedPlayer;
    Integer time;

    public timerTask(Player invited, String inviter, Integer time){
        this.invitedPlayer = invited.getName();
        this.invited = invited;
        this.islandInviter = inviter;
        islandManager.addPlayerInvitation(invited.getName(), inviter);
        this.time = time;
    }


    @Override
    public void run() {

        if (time == 1){
            //System.out.println(invitedPlayer+"invitedPklayer");
            //System.out.println(islandInviter+"inviter");
            islandManager.removePlayerInvitation(invitedPlayer, islandInviter);
            Player target = Bukkit.getPlayerExact(invited.getName());
            if (target != null && target.isOnline()) {
                target.sendMessage("Your invition from " + islandInviter + " has expired");
            }

            this.cancel();
        }
        time--;

    }
}
