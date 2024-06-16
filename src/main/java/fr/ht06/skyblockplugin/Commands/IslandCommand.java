package fr.ht06.skyblockplugin.Commands;

import fr.ht06.skyblockplugin.Config.IslandLevel;
import fr.ht06.skyblockplugin.Inventory.DeleteIslandInventory;
import fr.ht06.skyblockplugin.Inventory.IslandInventory;
import fr.ht06.skyblockplugin.Inventory.IslandSettingsInv;
import fr.ht06.skyblockplugin.Inventory.LeaveIslandInventory;
import fr.ht06.skyblockplugin.IslandManager.Island;
import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import fr.ht06.skyblockplugin.timerTask;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IslandCommand implements CommandExecutor {
    SkyblockPlugin main;
    MiniMessage miniMessage = MiniMessage.miniMessage();
    public IslandCommand(SkyblockPlugin main) {
        this.main  = main;
    }
    IslandManager islandManager = SkyblockPlugin.islandManager;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;



        Island island = islandManager.getIslandbyplayer(player.getName());
        if (args.length == 0) {
            if (!islandManager.playerHasIsland(player.getName())) {
                IslandInventory gui = new IslandInventory();
                player.openInventory(gui.getInventory());
            }

            else {
                island = islandManager.getIslandbyplayer(player.getName()).getIsland();
                player.teleport(islandManager.getIslandbyplayer(player.getName()).getIslandSpawn());
                SkyblockPlugin.worldBorderApi.setBorder(player, 100, new Location(Bukkit.getWorld("world_Skyblock"),
                        island.getIslandSpawn().getBlockX(),
                        island.getIslandSpawn().getBlockY(),
                        island.getIslandSpawn().getBlockZ()));
                player.sendMessage("§aTeleportation to your Island");
            }
        }
        if (args.length>=1) {

            if (args[0].equalsIgnoreCase("create")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    IslandInventory gui = new IslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                else{
                    player.sendMessage("You already have an island");
                }
            }

            if (args[0].equalsIgnoreCase("setspawn")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    IslandInventory gui = new IslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (!island.isMember(player.getName())) {
                    commandSetspawn(island, player);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                }
            }
            if (args[0].equalsIgnoreCase("delete")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    IslandInventory gui = new IslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (island.isOwner(player.getName())) {
                    commandDelete(player);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                    player.sendMessage("If you wan't to leave the island, do /island leave");
                }
            }
            if (args[0].equalsIgnoreCase("visit")) {
                commandVisit(args, player);
            }

            if (args[0].equalsIgnoreCase("settings")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    IslandInventory gui = new IslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }

                if (!island.isMember(player.getName())) {
                    commandSettings(player);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                }
            }
            if (args[0].equalsIgnoreCase("team")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    IslandInventory gui = new IslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                commandTeam(args, player, island);
            }
            if (args[0].equalsIgnoreCase("invite")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    IslandInventory gui = new IslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (!island.isMember(player.getName())) {
                    commandInvite(args, island, player);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                }
            }

            if (args[0].equalsIgnoreCase("join")) {
                commandJoin(args, player);
            }

            if (args[0].equalsIgnoreCase("decline")) {
                commandDecline(args, player);
            }

            if (args[0].equalsIgnoreCase("leave")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    IslandInventory gui = new IslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }

                commandLeave(args, player, island);
            }

            if (args[0].equalsIgnoreCase("promote")){
                if (!islandManager.playerHasIsland(player.getName())) {
                    IslandInventory gui = new IslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (!island.isMember(player.getName())) {
                    commandPromote(args, player, island);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                }

            }

            if (args[0].equalsIgnoreCase("demote")){
                if (!islandManager.playerHasIsland(player.getName())) {
                    IslandInventory gui = new IslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (!island.isMember(player.getName())) {
                    commandDemote(args, player, island);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                }
            }

            if (args[0].equalsIgnoreCase("Kick")){
                if (!islandManager.playerHasIsland(player.getName())) {
                    IslandInventory gui = new IslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (!island.isMember(player.getName())) {
                    commandKick(args, player, island);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                }
            }

            if (args[0].equalsIgnoreCase("setName")){
                if (!islandManager.playerHasIsland(player.getName())) {
                    IslandInventory gui = new IslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (island.isOwner(player.getName())) {
                    StringBuilder stringBuilder = new StringBuilder();
                    int nbr = 0;
                    for (int i = 1; i<args.length; i++){
                        if (i > 1 ){
                            stringBuilder.append(" ");
                        }
                        stringBuilder.append(args[i]);
                    }
                    island.setIslandName(stringBuilder.toString());
                    player.sendMessage("the island name is now : " + stringBuilder.toString());
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                }
            }

            if (args[0].equalsIgnoreCase("level")){
                if (!islandManager.playerHasIsland(player.getName())) {
                    IslandInventory gui = new IslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }

                double Level = IslandLevel.calculateIslandLevel(island);
                island.setLevel(Level);
                player.sendMessage("Your island is level " + Level);
            }

        }
        return true;

    }

    private void commandLeave(String[] args, Player player, Island island) {
        if (args.length != 1){
            player.sendMessage("/is leave");
        }
        else {
            if (island.isOwner(player.getName())) {
                player.sendMessage("§cYou can't leave your island because your are the owner");
                player.sendMessage("§cPromote someone else to Owner and leave (/island promote <player>");
                player.sendMessage("§cOr delete the island (/island delete");
            } else {
                LeaveIslandInventory leaveIslandInventory = new LeaveIslandInventory(player);
                player.openInventory(leaveIslandInventory.getInventory());
            }
        }
    }

    private void commandDelete(Player player){
        DeleteIslandInventory deleteIslandInventory = new DeleteIslandInventory();
        player.openInventory(deleteIslandInventory.getInventory());
    }

    private void commandSetspawn(Island island, Player player){
        island = islandManager.getIslandbyplayer(player.getName()).getIsland();
        //Si il n'y a rien en dessous
        Location ploc = player.getLocation();
        Location behindplayer = new Location(ploc.getWorld(), ploc.getX(), ploc.getY() - 1, ploc.getZ(), ploc.getYaw(), ploc.getPitch());
        if (behindplayer.getBlock().getType().isAir()) {
            player.sendMessage("§cSelect a valid spot for the spawn");
        } else {
            player.sendMessage("Island spawn has changed");
            island.setIslandSpawn(player.getLocation());
        }
    }

    private void commandVisit(String[] args, Player player){
        if (args.length == 2) {
            if (islandManager.playerHasIsland(args[1])) {
                player.teleport(islandManager.getIslandbyplayer(args[1]).getIslandSpawn());
                player.sendMessage("§aTeleportation to " + args[1] + "'s Island");
                SkyblockPlugin.worldBorderApi.setBorder(player, 100, islandManager.getIslandbyplayer(args[1]).getIslandSpawn());
            } else {
                player.sendMessage("§cThis player doesn't have an island or doesn't exist.");
            }
        } else {
            player.sendMessage("§c/is visit <player>");
        }
    }

    private void commandSettings(Player player){
        IslandSettingsInv islandSettingsInv = new IslandSettingsInv(player);
        player.openInventory(islandSettingsInv.getInventory());
    }

    private void commandTeam(String[] args, Player player, Island island){
        if (args.length == 1) {
            player.sendMessage("--- " + islandManager.getIslandbyplayer(player.getName()).getIslandName() + " ---");
            player.sendMessage("Players :");
            Player target1 = Bukkit.getPlayerExact(island.getOwner());
            if (target1 == null || !target1.isOnline()){
                player.sendMessage(" - Owner " + islandManager.getIslandbyplayer(player.getName()).getOwner() + " §c●");
            }
            else{
                player.sendMessage(" - Owner " + islandManager.getIslandbyplayer(player.getName()).getOwner() + " §a●");
            }

            for (String moderator : island.getAllModerators()) {

                Player target = Bukkit.getPlayerExact(moderator);
                if (target == null || !target.isOnline()){
                    player.sendMessage(" - Moderator " + moderator + " §c●");
                }
                else{
                    player.sendMessage(" - Moderator " + moderator + " §a●");
                }

            }
            for (String member : island.getAllMembers()) {

                Player target = Bukkit.getPlayerExact(member);
                if (target == null || !target.isOnline()){
                    player.sendMessage(" - Member " + member + " §c●");
                }
                else{
                    player.sendMessage(" - Member " + member + " §a●");
                }
            }
        }
    }

    private void commandInvite(String[] args, Island island, Player player){
        if (args.length == 2) {
            if (island.isMember(player.getName())) {
                player.sendMessage("§cYou cannot invite a player");
                return ;
            }
            if (island.isOnThisIsland(args[1])) {
                player.sendMessage("§cThis player is already on your island");
                return ;
            }
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target == null || !target.isOnline()){
                player.sendMessage("§cThis player is offline or didn't exist");
            }
            else {
                timerTask timerTask = new timerTask(target, player.getName(), 60);
                timerTask.runTaskTimer(SkyblockPlugin.getInstance(), 0, 20);
                player.sendMessage("invite send to " + args[1]);
                target.sendMessage(player.getName()+" invite you on his island");
                target.sendMessage(miniMessage.deserialize("<click:run_command:/is join "+ player.getName()+"><color:#27AE60>[Accept]</color></click>" +
                        " or "+ "<click:run_command:/is decline "+ player.getName()+ "><color:#A93226>[Decline]</color></click>"));
            }
        }
        else {
            player.sendMessage("§c/is invite <player>");
        }
    }

    private void commandJoin(String[] args, Player player){
        if (args.length == 2) {
            //à déjà une ile
            if (islandManager.playerHasIsland(player.getName())){
                player.sendMessage("§cYou already have an island, delete it with /is delete or /is leave");
            }
            //à pas d'île
            else {
                if (islandManager.isInvitedByPlayer(player.getName(), args[1])) {
                    islandManager.getIslandbyplayer(args[1]).addMember(player.getName());
                    //player.sendMessage("You join " + islandManager.getIslandbyplayer(player.getName()).getIslandName());
                    islandManager.getIslandbyplayer(player.getName()).BroadcastJoin(player);
                } else {
                    player.sendMessage("You didn't have any pending invite of this player");
                }
            }
        }
        else{
            player.sendMessage("§c/is join <player>");
        }
    }

    private void commandDecline(String[] args, Player player){
        if (args.length != 2){
            player.sendMessage("/island decline <player>");
        }
        else {
            if (islandManager.isInvitedby(player.getName(), args[1])){
                islandManager.removePlayerInvitation(player.getName(), args[1]);
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target != null && target.isOnline()) {
                    target.sendMessage(player.getName() + " decline your invitation.");
                    player.sendMessage("You decline the invitation of " + args[1]);
                }
                else player.sendMessage("§cThis player is offline or didn't exist");
            }
            else{
                player.sendMessage("This player didn't send you any invitation");
            }
        }
    }

    private void commandPromote(String[] args, Player player, Island island){
        if (args.length != 2){
            player.sendMessage("§c/island promote <player>");
        }
        else {
            //SI Le joueur est le Owner
            if (island.isOwner(player.getName())) {
                //Si le joueur qu'on veut promote est sur l'ile
                if (island.isOnThisIsland(args[1])) {
                    //S'il est membre
                    if (island.isMember(args[1])) {
                        player.sendMessage(args[1] + " is now moderator");
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target != null && target.isOnline()) {
                            target.sendMessage("You've been promoted to moderator on this island");
                        }
                        island.addModerator(args[1]);
                        island.removeMember(args[1]);
                    }
                    //S'il est modo
                    else if (island.isModerator(args[1])) {
                        player.sendMessage(args[1]+" is now the owner of the island");
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target != null && target.isOnline()) {
                            target.sendMessage("you are now the Owner of this island");
                        }
                        island.setOwner(args[1]);
                        island.addModerator(player.getName());
                        island.removeModerator(args[1]);
                    }
                    //S'il est Owner
                    else if (island.isOwner(args[1])){
                        player.sendMessage("§cYou can't promote yourself");
                    }
                }
                //Si le joueur que l'on veut promote n'est pas sur l'île
                else {
                    player.sendMessage("§cThis player isn't on your island");
                }
            }
            else if (island.isModerator(player.getName())) {
                if (args[1].equalsIgnoreCase(player.getName())){
                    player.sendMessage("You can't promote yourself");
                }
                else if (island.isMember(args[1])) {
                    island.addModerator(args[1]);
                }
                else if (island.isModerator(args[1])) {
                    player.sendMessage("You can't promote " + args[1] + ": this player is already moderator");

                }
                else if (island.isOwner(args[1])){
                    player.sendMessage("this is the owner");
                }
            }
            else {
                player.sendMessage("You can't promote anyone");
            }
        }
    }

    private void commandDemote(String[] args, Player player, Island island){
        if (args.length != 2){
            player.sendMessage("§c/island demote <player>");
        }
        else {
            //SI Le joueur est le Owner
            if (island.isOwner(player.getName())) {
                //Si le joueur qu'on veut promote est sur l'ile
                if (island.isOnThisIsland(args[1])) {
                    //S'il est membre
                    if (island.isMember(args[1])) {
                        player.sendMessage(args[1] + " is member, he cannot be demoted");
                    }
                    //S'il est modo
                    else if (island.isModerator(args[1])) {
                        player.sendMessage(args[1]+" is now a member");
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target != null && target.isOnline()) {
                            target.sendMessage("you are demoted by the owner to a member");
                        }
                        island.addMember(args[1]);
                        island.removeModerator(args[1]);
                    }
                    //S'il est Owner
                    else if (island.isOwner(args[1])){
                        player.sendMessage("§cYou can't demote yourself");
                    }
                }
                //Si le joueur que l'on veut promote n'est pas sur l'île
                else {
                    player.sendMessage("§cThis player isn't on your island");
                }
            }
            else {
                player.sendMessage("You can't demote anyone");
            }
        }
    }

    private void commandKick(String[] args, Player player, Island island){
        if (args.length != 2){
            player.sendMessage("§c/island kick <player>");
        }

        else if (island.isOwner(player.getName())){
            if (island.isMember(args[1])){
                island.removeMember(args[1]);
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target != null && target.isOnline()) {
                    target.sendMessage(player.getName() + " kick you from the island");
                }
                player.sendMessage("You kick " + args[1] + " from the island");
            }
            else if (island.isModerator(args[1])){
                island.removeModerator(args[1]);
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target != null && target.isOnline()) {
                    target.sendMessage(player.getName() + " kick you from the island");
                }
                player.sendMessage("You kick " + args[1] + "from the island");
            }
        }
        else if (island.isModerator(player.getName())) {
            if (island.isMember(args[1])){
                island.removeMember(args[1]);
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target != null && target.isOnline()) {
                    target.sendMessage(player.getName() + " kick you from the island");
                }
                player.sendMessage("You kick " + args[1] + "from the island");
            }
        }
        else{
            player.sendMessage("you can't kick anyone");
        }
    }





    /*if (args.length == 1){
            if (args[0].equalsIgnoreCase("setspawn")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else {
                    Island island = islandManager.getIslandbyplayer(player.getName()).getIsland();
                    //Si il n'y a rien en dessous
                    Location ploc = player.getLocation();
                    Location behindplayer = new Location(ploc.getWorld(), ploc.getX(), ploc.getY() - 1, ploc.getZ(), ploc.getYaw(), ploc.getPitch());
                    if (behindplayer.getBlock().getType().isAir()) {
                        player.sendMessage("§cSelect a valid spot for the spawn");
                    }
                    else {
                        player.sendMessage("Island spawn has changed");
                        island.setIslandSpawn(player.getLocation());
                    }
                }
            }

            if (args[0].equalsIgnoreCase("delete")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else{
                    DeleteIslandInventory deleteIslandInventory = new  DeleteIslandInventory();
                    player.openInventory(deleteIslandInventory.getInventory());
                }

            }

            if (args[0].equalsIgnoreCase("visit")){
                player.sendMessage("§c/is visit <player>");
            }

            if (args[0].equalsIgnoreCase("settings")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else{
                    IslandSettingsInv islandSettingsInv = new IslandSettingsInv(player);
                    player.openInventory(islandSettingsInv.getInventory());
                }
            }
            if (args[0].equalsIgnoreCase("team")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                    return true;
                }
                else{

                }
                player.sendMessage("ça va être les infos");
                if (args[1].equalsIgnoreCase("add")){
                }
            }


        }

        if (args.length == 2){

            if (args[0].equalsIgnoreCase("setspawn")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else {
                    Island island = islandManager.getIslandbyplayer(player.getName()).getIsland();
                    //Si il n'y a rien en dessous
                    Location ploc = player.getLocation();
                    Location behindplayer = new Location(ploc.getWorld(), ploc.getX(), ploc.getY() - 1, ploc.getZ(), ploc.getYaw(), ploc.getPitch());
                    if (behindplayer.getBlock().getType().isAir()) {
                        player.sendMessage("§cSelect a valid spot for the spawn");
                    }
                    else {
                        player.sendMessage("Island spawn has changed");
                        island.setIslandSpawn(player.getLocation());
                    }
                }
            }



            if (args[0].equalsIgnoreCase("delete")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else {
                    DeleteIslandInventory deleteIslandInventory = new  DeleteIslandInventory();
                    player.openInventory(deleteIslandInventory.getInventory());
                }
            }

            if (args[0].equalsIgnoreCase("visit")){
                if (islandManager.playerHasIsland(args[1])){
                    player.teleport(islandManager.getIslandbyplayer(args[1]).getIslandSpawn());
                    player.sendMessage("§aTeleportation to "+args[1]+"'s Island");
                    SkyblockPlugin.worldBorderApi.setBorder(player, 100, islandManager.getIslandbyplayer(args[1]).getIslandSpawn());

                }
                else{
                    player.sendMessage("§cThis player doesn't have an island or doesn't exist.");
                }
            }

            if (args[0].equalsIgnoreCase("setspawn")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else {
                    Island island = islandManager.getIslandbyplayer(player.getName()).getIsland();
                    //Si il n'y a rien en dessous
                    Location ploc = player.getLocation();
                    Location behindplayer = new Location(ploc.getWorld(), ploc.getX(), ploc.getY() - 1, ploc.getZ(), ploc.getYaw(), ploc.getPitch());
                    if (behindplayer.getBlock().getType().isAir()) {
                        player.sendMessage("§cSelect a valid spot for the spawn");
                    }
                    else {
                        player.sendMessage("Island spawn has changed");
                        island.setIslandSpawn(player.getLocation());
                    }
                }
            }


            if (args[0].equalsIgnoreCase("settings")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else{
                    IslandSettingsInv islandSettingsInv = new IslandSettingsInv(player);
                    player.openInventory(islandSettingsInv.getInventory());
                }
            }

        }
        if (args.length > 2){

            if (args[0].equalsIgnoreCase("setspawn")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else {
                    Island island = islandManager.getIslandbyplayer(player.getName()).getIsland();
                    //Si il n'y a rien en dessous
                    Location ploc = player.getLocation();
                    Location behindplayer = new Location(ploc.getWorld(), ploc.getX(), ploc.getY() - 1, ploc.getZ(), ploc.getYaw(), ploc.getPitch());
                    if (behindplayer.getBlock().getType().isAir()) {
                        player.sendMessage("§cSelect a valid spot for the spawn");
                    }
                    else {
                        player.sendMessage("Island spawn has changed");
                        island.setIslandSpawn(player.getLocation());
                    }
                }
            }

            if (args[0].equalsIgnoreCase("visit")){
                player.sendMessage("§c/visit <player>");
            }

            if (args[0].equalsIgnoreCase("delete")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else{
                    DeleteIslandInventory deleteIslandInventory = new  DeleteIslandInventory();
                    player.openInventory(deleteIslandInventory.getInventory());
                }

            }

            if (args[0].equalsIgnoreCase("settings")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else{
                    IslandSettingsInv islandSettingsInv = new IslandSettingsInv(player);
                    player.openInventory(islandSettingsInv.getInventory());
                }
            }
        }*/
}
