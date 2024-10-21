package fr.ht06.justskyblock.Events;

import fr.ht06.justskyblock.Config.IslandLevel;
import fr.ht06.justskyblock.Inventory.*;
import fr.ht06.justskyblock.Inventory.rankup.RankupInventory;
import fr.ht06.justskyblock.IslandManager.*;
import fr.ht06.justskyblock.LoadSchematic;
import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class InventoryEvents implements Listener {
    IslandManager islandManager = JustSkyblock.islandManager;
    MiniMessage miniMessage = MiniMessage.miniMessage();


    @EventHandler
    public void onClick(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();

        //@NotNull List<HumanEntity> human = event.getViewers();
        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof IslandSettingsInv){

            event.setCancelled(true);

            if (event.getSlot() >= islandManager.getIslandbyplayer(player.getName()).getAllSettings().size())return;  //permet de ne pas avoir d'erreurs quand on clique sur rien

            //if (event.getCurrentItem().isEmpty()) return;
            if (islandManager.getIslandbyplayer(player.getName()).getAllSettings().get(event.getCurrentItem().getType().name())){
                    setFalse(event.getCurrentItem());
                    islandManager.getIslandbyplayer(player.getName()).setSettings(event.getCurrentItem().getType().name(), false);
            }
            else{
                setTrue(event.getCurrentItem());
                islandManager.getIslandbyplayer(player.getName()).setSettings(event.getCurrentItem().getType().name(), true);

            }
            //player.sendMessage(String.valueOf(SkyblockPlugin.playerIslandSettings.get(player.getName())));

            player.updateInventory();
        }

        if (event.getClickedInventory().getHolder() instanceof DeleteIslandInventory){
            event.setCancelled(true);
            if (event.getSlot() ==  13){
                DeleteIsland.deleteIsland(player);
                player.closeInventory();
            }
        }

        if (event.getClickedInventory().getHolder() instanceof LeaveIslandInventory){
            event.setCancelled(true);
            if (event.getSlot() ==  13){
                Island island = islandManager.getIslandbyplayer(player.getName());
                //si il est membre
                if (islandManager.getIslandbyplayer(player.getName()).isMember(player.getUniqueId())){
                    player.closeInventory();
                    player.sendMessage("You leave " +  island.getIslandName());
                    //On le dégage
                    islandManager.getIslandbyplayer(player.getName()).removeMember(player.getUniqueId());
                    //On broadcast que le joueur à quitté l'île
                    island.BroadcastMessage(Component.text(player.getName()+" leave the island (maybe forever...)"));


                }
                else{  //S'il est modo
                    player.closeInventory();
                    player.sendMessage("You leave " + islandManager.getIslandbyplayer(player.getName()).getIslandName());
                    //On le dégage
                    islandManager.getIslandbyplayer(player.getName()).removeModerator(player.getUniqueId());
                    //On broadcast qu'il a quitté
                    island.BroadcastMessage(Component.text(player.getName()+" leave the island"));

                }
            }
        }

        if (event.getClickedInventory().getHolder() instanceof IslandInfoInventory){//Vérification si c'estle bon inventaire
            String islandName = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
            if (event.getSlot() == 11){
                IslandLevel.calculateIslandLevel(islandManager.getIslandbyName(islandName));
                player.sendMessage(Component.text(islandName + " level is " + islandManager.getIslandbyName(islandName).getLevel(), TextColor.color(0x43D649)));
                player.updateInventory();
            }
            if (event.getSlot() == 12){
                player.teleport(islandManager.getIslandbyName(islandName).getIslandSpawn());
                player.sendMessage(Component.text("Teleportation to " + islandManager.getIslandbyName(islandName).getIslandName(), TextColor.color(0x43D649)));
            }
            else if (event.getSlot() == 19){
                player.openInventory(new DeleteIslandInventoryAdmin(islandManager.getIslandbyName(islandName).getIslandName()).getInventory());
            }
            else event.setCancelled(true);
        }

        if (event.getClickedInventory().getHolder() instanceof RankupInventory){
            //player.sendMessage("invnvv");
            event.setCancelled(true);

            Island island = islandManager.getIslandbyplayer(player.getName());

            RankupInventory rankupInventory = new RankupInventory(player, islandManager.getIslandbyplayer(player.getName()));
            Map<Material, Integer>itemFromTier = rankupInventory.getTierItem(islandManager.getIslandbyplayer(player.getName()).getRank());

            if (event.getSlot() == 22 && event.getInventory().getItem(22).getType().equals(Material.REDSTONE_BLOCK)){
                player.closeInventory();
                player.sendMessage(Component.text("You don't have the required item to rankup", TextColor.color(0xC0392B)));
            }
            else if (event.getSlot() == 22 && event.getInventory().getItem(22).getType().equals(Material.DIAMOND_BLOCK)){
                int i = 0;

                for (ItemStack item: player.getInventory().getStorageContents()){
                    //player.sendMessage(itemFromTier.toString());
                    if (item != null) {
                        if (itemFromTier.containsKey(item.getType())){
                            if (itemFromTier.get(item.getType()) > 64 || itemFromTier.get(item.getType()) > item.getAmount()){
                                itemFromTier.put(item.getType(),itemFromTier.get(item.getType()) - item.getAmount());
                                player.getInventory().getItem(i).setAmount(0);
                            }
                            else if (itemFromTier.get(item.getType()) <= item.getAmount()){
                                player.getInventory().getItem(i).setAmount(item.getAmount() - itemFromTier.get(item.getType()));
                                itemFromTier.remove(item.getType());

                            }
                        }
                    }
                    i++;
                }
                player.closeInventory();
                islandManager.getIslandbyplayer(player.getName()).setRank(islandManager.getIslandbyplayer(player.getName()).getRank()+1);
                islandManager.getIslandbyplayer(player.getName()).BroadcastMessage("The island is now rank " + island.getRank());
            }

            if (event.getSlot() == 40){
                player.closeInventory();
                player.openInventory(rankupInventory.getInventory());
            }
        }
    }

    private void setTrue(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        List<Component> liste = new ArrayList<>();
        liste.add(Component.text("ALLOW").color(TextColor.color(0x2FCC33)).decoration(TextDecoration.ITALIC,false).decorate(TextDecoration.BOLD));
        liste.add(Component.text("DENY").color(TextColor.color(0x898F86)).decoration(TextDecoration.ITALIC,true));
        itemMeta.lore(liste);
        item.setItemMeta(itemMeta);
    }

    private void setFalse(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        List<Component> liste = new ArrayList<>();
        liste.add(Component.text("ALLOW").color(TextColor.color(0x898F86)).decoration(TextDecoration.ITALIC,true));
        liste.add(Component.text("DENY").color(TextColor.color(0xCC322A)).decoration(TextDecoration.ITALIC,false).decorate(TextDecoration.BOLD));
        itemMeta.lore(liste);
        item.setItemMeta(itemMeta);
    }

}