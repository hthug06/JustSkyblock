package fr.ht06.justskyblock.Inventory.upgrade;

import fr.ht06.justskyblock.CreateItem;
import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IslandSizeUpgrade implements InventoryHolder, Listener {

    Inventory inventory;
    private final List<Integer> phase1 = new ArrayList<>(List.of(12, 13, 14, 21, 23, 30, 31, 32));
    private final List<Integer> phase2 = new ArrayList<>(List.of(2,3,4,5,6, 11, 15, 20, 24, 29, 33,38, 39, 40, 41, 42));
    private final List<Integer> phase3 = new ArrayList<>(List.of(1, 7, 10, 16, 19, 25, 28, 34, 37, 43));
    private final List<Integer> phase4 = new ArrayList<>(List.of(0, 8, 9, 17, 18, 26, 27, 35, 36, 44));



    public IslandSizeUpgrade(Player player){
        inventory= Bukkit.createInventory(this, 45, Component.text("WorldBorder Size"));
        init(player);

    }
    public IslandSizeUpgrade(){}

    private void init(Player player) {
        double size = JustSkyblock.islandManager.getIslandbyplayer(player.getName()).getSize();
        List<Component> lore = new ArrayList<>();
        int maxSize = JustSkyblock.getInstance().getConfig().getInt("IslandWorldBorderMaxSize");
        int minSize = JustSkyblock.getInstance().getConfig().getInt("IslandWorldBorderMinSize");
        if (size == minSize){
            lore = Arrays.asList(Component.text("To increase your worldBorder,", TextColor.color(0x683735)),
                    Component.text("You need to upgrade your island size (/is level)", TextColor.color(0x683735)),
                    Component.text("Your actual island size: "+ size +"x"+size+ " (this is the minimum size)", TextColor.color(0x683735)),
                    Component.text("The calculation for the level is : level/100", TextColor.color(0x683735)),
                    Component.text("The Maximum size is "+ maxSize + "x" + maxSize, TextColor.color(0x683735)),
                    Component.text("The Minimum size is "+ minSize + "x" + minSize, TextColor.color(0x683735)),
                    Component.text(""),
                    Component.text("Yes, this is your island...", NamedTextColor.GRAY));
        }
        else if (size == maxSize){
            lore = Arrays.asList(Component.text("To increase your worldBorder,", TextColor.color(0x683735)),
                    Component.text("You need to upgrade your island size (/is level)", TextColor.color(0x683735)),
                    Component.text("Your actual island size: "+ size +"x"+size+ " (this is the maximum size)", TextColor.color(0x683735)),
                    Component.text("The calculation for the level is : level/100", TextColor.color(0x683735)),
                    Component.text("The Maximum size is "+ maxSize + "x" + maxSize, TextColor.color(0x683735)),
                    Component.text("The Minimum size is "+ minSize + "x" + minSize, TextColor.color(0x683735)),
                    Component.text(""),
                    Component.text("Yes, this is your island...", NamedTextColor.GRAY));
        }
        else{
            lore = Arrays.asList(Component.text("To increase your worldBorder,", TextColor.color(0x683735)),
                    Component.text("You need to upgrade your island size (/is level)", TextColor.color(0x683735)),
                    Component.text("Your actual island size: "+size+"x"+size, TextColor.color(0x683735)),
                    Component.text("The calculation for the level is : level/100", TextColor.color(0x683735)),
                    Component.text("The Maximum size is "+ maxSize + "x" + maxSize, TextColor.color(0x683735)),
                    Component.text("The Minimum size is "+ minSize + "x" + minSize, TextColor.color(0x683735)),
                    Component.text(""),
                    Component.text("Yes, this is your island...", NamedTextColor.GRAY));
        }
        ItemStack itemIsland = CreateItem.createItem(Component.text("Your island", TextColor.color(0x1e8449)).decoration(TextDecoration.ITALIC, false),
                1,
                Material.GRASS_BLOCK,
                lore);
        inventory.setItem(22, itemIsland);

        ItemStack item = CreateItem.createItem(Component.text("WorldBorder", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false), 1, Material.LIGHT_BLUE_STAINED_GLASS_PANE,  List.of(Component.text("Yes, this is a WorldBorder", NamedTextColor.GRAY)));

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(JustSkyblock.getInstance(), new Runnable() {
            int num = 0;
            public void run() {
                if (inventory.getViewers().isEmpty()){
                    return;
                }
                if (num == 1) {
                    setAir(phase4);
                    phase1.forEach(integer -> {
                        inventory.setItem(integer, item);
                    });
                }
                if (num == 2) {
                    setAir(phase1);
                    phase2.forEach(integer -> {
                        inventory.setItem(integer, item);
                    });
                }
                if (num == 3) {
                    setAir(phase2);
                    phase3.forEach(integer -> {
                        inventory.setItem(integer, item);
                    });
                }
                if (num == 4) {
                    setAir(phase3);
                    phase4.forEach(integer -> {
                        inventory.setItem(integer, item);
                    });
                    num = 0;
                }
                num ++;
            }
        }, 0, 20); //Time to update the item

    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        Island island = JustSkyblock.islandManager.getIslandbyplayer(player.getName());
        //@NotNull List<HumanEntity> human = event.getViewers();
        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof IslandSizeUpgrade) {//Vérification si c'estle bon inventaire
            event.setCancelled(true);

        }
    }

    public void setAir(List<Integer> integerList){
        integerList.stream().forEach(integer -> {
            ItemStack item = new ItemStack(Material.AIR);
            inventory.setItem(integer, item);
        });
    }
}
