package fr.ht06.justskyblock.Commands;

import fr.ht06.justskyblock.Config.IslandLevel;
import fr.ht06.justskyblock.Inventory.*;
import fr.ht06.justskyblock.Inventory.Quest.MainQuestInventory;
import fr.ht06.justskyblock.Inventory.rankup.RankupInventory;
import fr.ht06.justskyblock.Inventory.upgrade.UpgradeMain;
import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.IslandManager.IslandManager;
import fr.ht06.justskyblock.IslandManager.IslandWorldBorder;
import fr.ht06.justskyblock.JustSkyblock;
import fr.ht06.justskyblock.IslandManager.TimerForInvitedPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class IslandCommand implements CommandExecutor {
    JustSkyblock main;
    MiniMessage miniMessage = MiniMessage.miniMessage();
    public IslandCommand(JustSkyblock main) {
        this.main  = main;
    }
    IslandManager islandManager = JustSkyblock.islandManager;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        Island island = islandManager.getIslandbyplayer(player.getName());
        if (args.length == 0) {
            if (!islandManager.playerHasIsland(player.getName())) {
                CreateIslandInventory gui = new CreateIslandInventory();
                player.openInventory(gui.getInventory());
            }

            else {
                island = islandManager.getIslandbyplayer(player.getName()).getIsland();
                player.teleport(islandManager.getIslandbyplayer(player.getName()).getIslandSpawn());
                player.setWorldBorder(IslandWorldBorder.setWorldBorder(island));

                player.sendMessage("§aTeleportation to your Island");
            }
        }
        if (args.length>=1) {

            if (args[0].equalsIgnoreCase("create")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                else{
                    player.sendMessage("You already have an island");
                }
            }

            else if (args[0].equalsIgnoreCase("setspawn")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (!island.isMember(player.getUniqueId())) {
                    commandSetspawn(island, player);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                }
            }
            else if (args[0].equalsIgnoreCase("delete")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (island.isOwner(player.getUniqueId())) {
                    commandDelete(player);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                    player.sendMessage("If you wan't to leave the island, do /island leave");
                }
            }
            else if (args[0].equalsIgnoreCase("visit")) {
                commandVisit(args, player);
            }

            else if (args[0].equalsIgnoreCase("settings")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }

                if (!island.isMember(player.getUniqueId())) {
                    commandSettings(player);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                }
            }
            else if (args[0].equalsIgnoreCase("team")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                commandTeam(args, player, island);
            }
            else if (args[0].equalsIgnoreCase("invite")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (!island.isMember(player.getUniqueId())) {
                    commandInvite(args, island, player);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                }
            }

            else if (args[0].equalsIgnoreCase("join")) {
                commandJoin(args, player);
            }

            else if (args[0].equalsIgnoreCase("decline")) {
                commandDecline(args, player);
            }

            else if (args[0].equalsIgnoreCase("leave")) {
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }

                commandLeave(args, player, island);
            }

            else if (args[0].equalsIgnoreCase("promote")){
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (!island.isMember(player.getUniqueId())) {
                    commandPromote(args, player, island);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                }

            }

            else if (args[0].equalsIgnoreCase("demote")){
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (!island.isMember(player.getUniqueId())) {
                    commandDemote(args, player, island);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                }
            }

            else if (args[0].equalsIgnoreCase("Kick")){
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (!island.isMember(player.getUniqueId())) {
                    commandKick(args, player, island);
                }
                else{
                    player.sendMessage("You don't have the permission to do this");
                }
            }

            else if (args[0].equalsIgnoreCase("setName")){
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                commandSetName(args, island, player);
            }

            else if (args[0].equalsIgnoreCase("level") || args[0].equalsIgnoreCase("lvl")){
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }

                if (!island.isMember(player.getUniqueId())){
                    double level = IslandLevel.calculateIslandLevel(island) / 100;
                    BigDecimal bd = new BigDecimal(level).setScale(2, RoundingMode.HALF_UP);
                    level = bd.doubleValue();
                    island.setLevel(level);
                    player.sendMessage("Your island is level " + level);
                    double size = level/10;
                    if (size<50){
                        size = 50;
                    }
                    island.setSize(size);
                    for (Player p: islandManager.getIslandbyplayer(player.getName()).getAllPlayerOnIsland()){
                        p.setWorldBorder(IslandWorldBorder.setWorldBorder(island));
                    }
                    player.sendMessage(Component.text("Your Island WorldBorder size is now: " + (int)island.getSize()+"x"+(int) island.getSize(), TextColor.color(0x99a3a4)).decoration(TextDecoration.ITALIC, true));
                }
            }

            else if (args[0].equalsIgnoreCase("help")){
                //pas d'île
                commandHelp(args, island, player);
            }

            else if (args[0].equalsIgnoreCase("rankup")){
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }

                RankupInventory rankupInventory = new RankupInventory(player, island);
                player.openInventory(rankupInventory.getInventory());
            }

            else if (args[0].equalsIgnoreCase("quest")){
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }
                if (island.getRank()==0){
                    player.sendMessage("§cUpgrade your island rank to unlock the quest: /is rankup");
                }
                else {
                    MainQuestInventory mainQuestInventory = new MainQuestInventory(player, island);
                    player.openInventory(mainQuestInventory.getInventory());
                }
            }

            else if (args[0].equalsIgnoreCase("upgrade")){
                if (!islandManager.playerHasIsland(player.getName())) {
                    CreateIslandInventory gui = new CreateIslandInventory();
                    player.openInventory(gui.getInventory());
                    return true;
                }

                UpgradeMain upgradeMain = new UpgradeMain(player, island);
                player.openInventory(upgradeMain.getInventory());
            }

            else{
                Bukkit.dispatchCommand(player, "island help");
            }
        }
        return true;

    }

    private void commandLeave(String[] args, Player player, Island island) {
        if (args.length != 1){
            player.sendMessage("/is leave");
        }
        else {
            if (island.isOwner(player.getUniqueId())) {
                player.sendMessage("§cYou can't leave your island because your are the owner");
                player.sendMessage("§cPromote someone else to Owner and leave (/island promote <player>");
                player.sendMessage("§cOr delete the island (/island delete)");
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
                Island island = islandManager.getIslandbyplayer(args[1]);
                player.teleport(islandManager.getIslandbyplayer(args[1]).getIslandSpawn());
                player.sendMessage("§aTeleportation to " + args[1] + "'s Island");
                islandManager.getIslandbyplayer(args[1]).BroadcastMessage(Component.text(player.getName()+" is visiting your island"));

                player.setWorldBorder(IslandWorldBorder.setWorldBorder(island));

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
            Player target1 = Bukkit.getPlayer(island.getOwner());
            if (target1 == null || !target1.isOnline()){
                player.sendMessage(" - Owner " + Bukkit.getPlayer(islandManager.getIslandbyplayer(player.getName()).getOwner()).getName()  + " §c●");
            }
            else{
                player.sendMessage(" - Owner " + Bukkit.getPlayer(islandManager.getIslandbyplayer(player.getName()).getOwner()).getName() + " §a●");
            }

            for (UUID moderator : island.getAllModerators()) {

                Player target = Bukkit.getPlayer(moderator);
                if (target == null || !target.isOnline()){
                    player.sendMessage(" - Moderator " + Bukkit.getPlayer(moderator).getName() + " §c●");
                }
                else{
                    player.sendMessage(" - Moderator " + Bukkit.getPlayer(moderator).getName() + " §a●");
                }

            }
            for (UUID member : island.getAllMembers()) {

                Player target = Bukkit.getPlayer(member);
                if (target == null || !target.isOnline()){
                    player.sendMessage(" - Member " + Bukkit.getPlayer(member).getName() + " §c●");
                }
                else{
                    player.sendMessage(" - Member " + Bukkit.getPlayer(member).getName() + " §a●");
                }
            }
        }

        if (args.length == 2) {

            if (!islandManager.playerHasIsland(args[2])) {
                player.sendMessage("this player doesn't have an island");
                return;
            }
            island = islandManager.getIslandbyplayer(args[2]);
            player.sendMessage("--- " + islandManager.getIslandbyplayer(player.getName()).getIslandName() + " ---");
            player.sendMessage("Players :");
            Player target1 = Bukkit.getPlayer(island.getOwner());
            if (target1 == null || !target1.isOnline()){
                player.sendMessage(" - Owner " + islandManager.getIslandbyplayer(player.getName()).getOwner() + " §c●");
            }
            else{
                player.sendMessage(" - Owner " + islandManager.getIslandbyplayer(player.getName()).getOwner() + " §a●");
            }

            for (UUID moderator : island.getAllModerators()) {

                Player target = Bukkit.getPlayer(moderator);
                if (target == null || !target.isOnline()){
                    player.sendMessage(" - Moderator " + moderator + " §c●");
                }
                else{
                    player.sendMessage(" - Moderator " + moderator + " §a●");
                }

            }
            for (UUID member : island.getAllMembers()) {

                Player target = Bukkit.getPlayer(member);
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
            if (island.isMember(player.getUniqueId())) {
                player.sendMessage("§cYou cannot invite a player");
                return ;
            }
            if (island.isOnThisIsland(Bukkit.getPlayerUniqueId(args[1]))) {
                player.sendMessage("§cThis player is already on your island");
                return ;
            }
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target == null || !target.isOnline()){
                player.sendMessage("§cThis player is offline or didn't exist");
            }
            else {
                TimerForInvitedPlayer timerTask = new TimerForInvitedPlayer(target, player.getName(), 60);
                timerTask.runTaskTimer(JustSkyblock.getInstance(), 0, 20);
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
                    islandManager.getIslandbyplayer(args[1]).addMember(player.getUniqueId());
                    //player.sendMessage("You join " + islandManager.getIslandbyplayer(player.getName()).getIslandName());
                    islandManager.getIslandbyplayer(player.getName()).BroadcastMessage(Component.text(player.getName()+" Join the Island"));
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
            if (island.isOwner(player.getUniqueId())) {
                //Si le joueur qu'on veut promote est sur l'ile
                if (island.isOnThisIsland(Bukkit.getPlayerUniqueId(args[1]))) {
                    //S'il est membre
                    if (island.isMember(Bukkit.getPlayerUniqueId(args[1]))) {
                        player.sendMessage(args[1] + " is now moderator");
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target != null && target.isOnline()) {
                            target.sendMessage("You've been promoted to moderator on this island");
                        }
                        island.addModerator(Bukkit.getPlayerUniqueId(args[1]));
                        island.removeMember(Bukkit.getPlayerUniqueId(args[1]));
                    }
                    //S'il est modo
                    else if (island.isModerator(Bukkit.getPlayerUniqueId(args[1]))) {
                        player.sendMessage(args[1]+" is now the owner of the island");
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target != null && target.isOnline()) {
                            target.sendMessage("you are now the Owner of this island");
                        }
                        island.setOwner(Bukkit.getPlayerUniqueId(args[1]));
                        island.addModerator(player.getUniqueId());
                        island.removeModerator(Bukkit.getPlayerUniqueId(args[1]));
                    }
                    //S'il est Owner
                    else if (island.isOwner(Bukkit.getPlayerUniqueId(args[1]))){
                        player.sendMessage("§cYou can't promote yourself");
                    }
                }
                //Si le joueur que l'on veut promote n'est pas sur l'île
                else {
                    player.sendMessage("§cThis player isn't on your island");
                }
            }
            else if (island.isModerator(player.getUniqueId())) {
                if (args[1].equalsIgnoreCase(player.getName())){
                    player.sendMessage("You can't promote yourself");
                }
                else if (island.isMember(Bukkit.getPlayerUniqueId(args[1]))) {
                    island.addModerator(Bukkit.getPlayerUniqueId(args[1]));
                }
                else if (island.isModerator(Bukkit.getPlayerUniqueId(args[1]))) {
                    player.sendMessage("You can't promote " + args[1] + ": this player is already moderator");

                }
                else if (island.isOwner(Bukkit.getPlayerUniqueId(args[1]))){
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
            if (island.isOwner(player.getUniqueId())) {
                //Si le joueur qu'on veut promote est sur l'ile
                if (island.isOnThisIsland(Bukkit.getPlayerUniqueId(args[1]))) {
                    //S'il est membre
                    if (island.isMember(Bukkit.getPlayerUniqueId(args[1]))) {
                        player.sendMessage(Bukkit.getPlayerUniqueId(args[1]) + " is member, he cannot be demoted");
                    }
                    //S'il est modo
                    else if (island.isModerator(Bukkit.getPlayerUniqueId(args[1]))) {
                        player.sendMessage(Bukkit.getPlayerUniqueId(args[1])+" is now a member");
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target != null && target.isOnline()) {
                            target.sendMessage("you are demoted by the owner to a member");
                        }
                        island.addMember(Bukkit.getPlayerUniqueId(args[1]));
                        island.removeModerator(Bukkit.getPlayerUniqueId(args[1]));
                    }
                    //S'il est Owner
                    else if (island.isOwner(Bukkit.getPlayerUniqueId(args[1]))){
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

        else if (island.isOwner(player.getUniqueId())){
            if (island.isMember(Bukkit.getPlayerUniqueId(args[1]))){
                island.removeMember(Bukkit.getPlayerUniqueId(args[1]));
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target != null && target.isOnline()) {
                    target.sendMessage(player.getName() + " kick you from the island");
                }
                player.sendMessage("You kick " + args[1] + " from the island");
            }
            else if (island.isModerator(Bukkit.getPlayerUniqueId(args[1]))){
                island.removeModerator(Bukkit.getPlayerUniqueId(args[1]));
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target != null && target.isOnline()) {
                    target.sendMessage(player.getName() + " kick you from the island");
                }
                player.sendMessage("You kick " + args[1] + " from the island");
            }
        }
        else if (island.isModerator(player.getUniqueId())) {
            if (island.isMember(Bukkit.getPlayerUniqueId(args[1]))){
                island.removeMember(Bukkit.getPlayerUniqueId(args[1]));
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

    private void commandSetName(String[] args, Island island, Player player){
        if (island.isOwner(player.getUniqueId())) {
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

    private void commandHelp(String[] args,Island island, Player player){
        //pas d'île
        if (!islandManager.playerHasIsland(player.getName())){
            player.sendMessage(Component.text("     ----- ", TextColor.color(0x52BE80))
                    .append(Component.text("Island Help (Page 1)", TextColor.color(0x5499C7)))
                    .append(Component.text(" -----", TextColor.color(0x52BE80))));  // --- Help Island ---

            player.sendMessage(Component.text("/island create: ", TextColor.color(0x5499C7))
                    .clickEvent(ClickEvent.suggestCommand("/island create"))
                    .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                    .append(Component.text("Create the island of your dream ", TextColor.color(0x52BE80))));

            player.sendMessage(Component.text("/island decline <player>: ", TextColor.color(0x5499C7))
                    .clickEvent(ClickEvent.suggestCommand("/island decline "))
                    .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                    .append(Component.text("Decline an island invitation ", TextColor.color(0x52BE80))));

            player.sendMessage(Component.text("/island help: ", TextColor.color(0x5499C7))
                    .clickEvent(ClickEvent.suggestCommand("/island help"))
                    .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                    .append(Component.text("All that can help you about Skyblock commands ", TextColor.color(0x52BE80))));

            player.sendMessage(Component.text("/island join <player>: ", TextColor.color(0x5499C7))
                    .clickEvent(ClickEvent.suggestCommand("/island join "))
                    .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                    .append(Component.text("Join an island by accepting an invitation", TextColor.color(0x52BE80))));

            player.sendMessage(Component.text("/island visit <player>: ", TextColor.color(0x5499C7))
                    .clickEvent(ClickEvent.suggestCommand("/island visit "))
                    .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                    .append(Component.text("Visit another player's island", TextColor.color(0x52BE80))));


            player.sendMessage(Component.text("     ----------------------", TextColor.color(0x52BE80))
                    //.append(Component.text(/*">>"*/"--", TextColor.color(0x5499C7))
                    //.hoverEvent(HoverEvent.showText(Component.text("go to page 2", TextColor.color(0x5499C7))))
                    //.clickEvent(ClickEvent.runCommand("/is help 2")))
                    .append(Component.text("------", TextColor.color(0x52BE80))));
        }
        //à une ile
        else{
            //est membre
            if (island.isMember(player.getUniqueId())){
                player.sendMessage(Component.text("     ----- ", TextColor.color(0x52BE80))
                        .append(Component.text("Island Help (Page 1)", TextColor.color(0x5499C7)))
                        .append(Component.text(" -----", TextColor.color(0x52BE80))));  // --- Help Island ---

                player.sendMessage(Component.text("/island help: ", TextColor.color(0x5499C7))
                        .clickEvent(ClickEvent.suggestCommand("/island help"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                        .append(Component.text("All that can help you about Skyblock commands ", TextColor.color(0x52BE80))));

                player.sendMessage(Component.text("/island leave: ", TextColor.color(0x5499C7))
                        .clickEvent(ClickEvent.suggestCommand("/island leave "))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                        .append(Component.text("Leave you island (Be careful)", TextColor.color(0x52BE80))));

                player.sendMessage(Component.text("/island quest: ", TextColor.color(0x5499C7))
                        .clickEvent(ClickEvent.suggestCommand("/island quest "))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                        .append(Component.text("Open the quest menu", TextColor.color(0x52BE80))));

                player.sendMessage(Component.text("/island team: ", TextColor.color(0x5499C7))
                        .clickEvent(ClickEvent.suggestCommand("/island team "))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                        .append(Component.text("See your island team", TextColor.color(0x52BE80))));

                player.sendMessage(Component.text("/island visit: ", TextColor.color(0x5499C7))
                        .clickEvent(ClickEvent.suggestCommand("/island visit "))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                        .append(Component.text("Visit another player's island", TextColor.color(0x52BE80))));

                player.sendMessage(Component.text("     ---------------------", TextColor.color(0x52BE80))
                        //.append(Component.text(/*">>"*/"--", TextColor.color(0x5499C7))
                        //.hoverEvent(HoverEvent.showText(Component.text("go to page 2", TextColor.color(0x5499C7))))
                        //.clickEvent(ClickEvent.runCommand("/is help 2")))
                        .append(Component.text("-------", TextColor.color(0x52BE80))));
            }
            //MOD
            else if (island.isModerator(player.getUniqueId())) {

                if (args.length == 1 || args[1].equalsIgnoreCase("1")){

                    player.sendMessage(Component.text("     ----- ", TextColor.color(0x52BE80))
                            .append(Component.text("Island Help (Page 1)", TextColor.color(0x5499C7)))
                            .append(Component.text(" -----", TextColor.color(0x52BE80))));  // --- Help Island ---

                    player.sendMessage(Component.text("/island demote: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island demote "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Demote a member of your island", TextColor.color(0x52BE80))));


                    player.sendMessage(Component.text("/island help: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island help"))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("All that can help you about Skyblock commands ", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island invite: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island invite "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Invite a player to join your island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island kick: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island kick "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Kick a member of your island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island leave: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island leave "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Leave you island (Be careful)", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island level: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island level "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Update the level of your island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island quest: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island quest "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Open the quest menu", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island promote: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island promote  "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Promote a player of the island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("     --------------------- ", TextColor.color(0x52BE80))
                            .append(Component.text(">>", TextColor.color(0x5499C7))
                                    .hoverEvent(HoverEvent.showText(Component.text("go to page 2", TextColor.color(0x5499C7))))
                                    .clickEvent(ClickEvent.runCommand("/is help 2")))
                            .append(Component.text(" -----", TextColor.color(0x52BE80))));

                }
                //PAGE 2
                else if (args[1].equalsIgnoreCase("2")){
                    player.sendMessage(Component.text("     ----- ", TextColor.color(0x52BE80))
                            .append(Component.text("Island Help (Page 2)", TextColor.color(0x5499C7)))
                            .append(Component.text(" -----", TextColor.color(0x52BE80))));  // --- Help Island ---

                    player.sendMessage(Component.text("/island setspawn: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island setspawn "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Change the spawn of the island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island team: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island team "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("See your island team", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island upgrade: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island upgrade"))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Upgrade your island generator", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island visit: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island visit "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Visit another player's island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island settings: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island settings "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Change the settings of the island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("     ----- ", TextColor.color(0x52BE80))
                            .append(Component.text("<<", TextColor.color(0x5499C7))
                                    .hoverEvent(HoverEvent.showText(Component.text("go to page 1", TextColor.color(0x5499C7))))
                                    .clickEvent(ClickEvent.runCommand("/is help 1")))
                            .append(Component.text(" ---------------------", TextColor.color(0x52BE80))));
                } else player.performCommand("island help");


            }
            else if (island.isOwner(player.getUniqueId())){
                //PAGE 1
                if (args.length == 1 || args[1].equalsIgnoreCase("1")) {

                    player.sendMessage(Component.text("     ----- ", TextColor.color(0x52BE80))
                            .append(Component.text("Island Help (Page 1)", TextColor.color(0x5499C7)))
                            .append(Component.text(" -----", TextColor.color(0x52BE80))));  // --- Help Island ---

                    player.sendMessage(Component.text("/island delete: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island delete "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Delete you island (Be careful)", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island demote: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island demote "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Demote a member of your island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island help: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island help"))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("All that can help you about Skyblock commands ", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island invite: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island invite "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Invite a player to join your island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island visit: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island visit "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Kick a member of your island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island leave: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island leave "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Leave you island (Be careful)", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island level: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island level "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Update the level of your island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island promote: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island promote "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Promote a player of the island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("     -------------------- ", TextColor.color(0x52BE80))
                            .append(Component.text(">>", TextColor.color(0x5499C7))
                                    .hoverEvent(HoverEvent.showText(Component.text("go to page 2", TextColor.color(0x5499C7))))
                                    .clickEvent(ClickEvent.runCommand("/is help 2")))
                            .append(Component.text(" ------", TextColor.color(0x52BE80))));

                }
                else if (args[1].equalsIgnoreCase("2")){

                    player.sendMessage(Component.text("     ----- ", TextColor.color(0x52BE80))
                            .append(Component.text("Island Help (Page 2)", TextColor.color(0x5499C7)))
                            .append(Component.text(" ------", TextColor.color(0x52BE80))));  // --- Help Island ---

                    player.sendMessage(Component.text("/island quest: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island quest "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Open the quest menu", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island rankup: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island rankup "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Open the rankup menu", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island setspawn: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island setspawn "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Change the spawn of the island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island settings: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island settings "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Change the settings of the island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island team: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island team "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("See your island team", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island upgrade: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island upgrade"))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Upgrade your island generator", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island visit: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island visit "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Visit another player's island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("/island setname: ", TextColor.color(0x5499C7))
                            .clickEvent(ClickEvent.suggestCommand("/island setname "))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x5499C7))))
                            .append(Component.text("Change the name of the island", TextColor.color(0x52BE80))));

                    player.sendMessage(Component.text("     ----- ", TextColor.color(0x52BE80))
                            .append(Component.text("<<", TextColor.color(0x5499C7))
                                    .hoverEvent(HoverEvent.showText(Component.text("go to page 1", TextColor.color(0x5499C7))))
                                    .clickEvent(ClickEvent.runCommand("/is help 1")))
                            .append(Component.text(" ---------------------", TextColor.color(0x52BE80))));
                } else player.performCommand("island help");
            }
        }
    }
}
