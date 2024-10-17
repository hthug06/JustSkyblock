package fr.ht06.justskyblock.IslandManager;

import fr.ht06.justskyblock.JustSkyblock;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerForInvitedPlayer extends BukkitRunnable {
    IslandManager islandManager = JustSkyblock.islandManager;
    String islandInviter;
    Player invited;
    String invitedPlayer;
    Integer time;

    public TimerForInvitedPlayer(Player invited, String inviter, Integer time){
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
