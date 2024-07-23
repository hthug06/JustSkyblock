package fr.ht06.justskyblock.Inventory.upgrade;

import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomCobbleGenUpgrade implements InventoryHolder {

    Inventory inv;

    public CustomCobbleGenUpgrade(Player player, Island island){
        inv = Bukkit.createInventory(this, 45, Component.text("Cobblestone generator upgrade"));
        init(player, island);
    }

    public CustomCobbleGenUpgrade(){}

    private void init(Player player, Island island) {
        //inv.setItem(0, JustSkyblock.createItem(Component.text("Tier 1"), 1, Material.COBBLESTONE, List.of(Component.text("tier 1"))));
        List<Material> materialList = new ArrayList<>(List.of(Material.COBBLESTONE, Material.COAL, Material.IRON_INGOT,
                Material.GOLD_INGOT, Material.REDSTONE, Material.DIAMOND, Material.EMERALD));
        int i = 1;
        int place = 10;
        ItemStack itemStack = null;
        for (Material material : materialList){
            switch (material){
                case COBBLESTONE -> {
                    itemStack = JustSkyblock.createItem(
                            Component.text("Tier " + i + " Generator", TextColor.color(0xAAB7B8)).decoration(TextDecoration.ITALIC, false),
                            i,
                            material,
                            List.of(Component.text("The Beginning")));
                }
                case COAL -> {
                    itemStack = JustSkyblock.createItem(
                            Component.text("Tier " + i + " Generator", TextColor.color(0xAAB7B8)).decoration(TextDecoration.ITALIC, false),
                            i,
                            material,
                            List.of(Component.text("Time for an upgrade")));
                }
                case IRON_INGOT -> {
                    itemStack = JustSkyblock.createItem(
                            Component.text("Tier " + i + " Generator", TextColor.color(0xAAB7B8)).decoration(TextDecoration.ITALIC, false),
                            i,
                            material,
                            List.of(Component.text("The iron age")));
                }
                case GOLD_INGOT -> {
                    itemStack = JustSkyblock.createItem(
                            Component.text("Tier " + i + " Generator", TextColor.color(0xAAB7B8)).decoration(TextDecoration.ITALIC, false),
                            i,
                            material,
                            List.of(Component.text("The gold age")));
                }
                case REDSTONE -> {
                    itemStack = JustSkyblock.createItem(
                            Component.text("Tier " + i + " Generator", TextColor.color(0xAAB7B8)).decoration(TextDecoration.ITALIC, false),
                            i,
                            material,
                            List.of(Component.text("Automation time")));
                }
                case DIAMOND -> {
                    itemStack = JustSkyblock.createItem(
                            Component.text("Tier " + i + " Generator", TextColor.color(0xAAB7B8)).decoration(TextDecoration.ITALIC, false),
                            i,
                            material,
                            List.of(Component.text("DIAMOND!")));
                }
                case EMERALD -> {
                    itemStack = JustSkyblock.createItem(
                            Component.text("Tier " + i + " Generator", TextColor.color(0xAAB7B8)).decoration(TextDecoration.ITALIC, false),
                            i,
                            material,
                            List.of(Component.text("The final generator (congrats)")));
                }
            }
            inv.setItem(place, itemStack);
            place++;
            i++;
        }

        ItemStack tier1 = JustSkyblock.createItem(Component.text("Tier 1"), 1, Material.CHEST,
                List.of(Component.text("Stone : 55%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Granite : 10%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Diorite : 10%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Andesite : 10%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Gravel : 10%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Coal ore : 4%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Iron ore : 1%").decoration(TextDecoration.ITALIC, false)));

        inv.setItem(19, tier1);

        ItemStack tier2 = JustSkyblock.createItem(Component.text("Tier 2"), 1, Material.CHEST,
                List.of(Component.text("Stone : 40%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Granite : 12%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Diorite : 12%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Andesite : 12%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Gravel : 10%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Coal ore : 10%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Iron ore : 4%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Lapis ore : 2%").decoration(TextDecoration.ITALIC, false),
                        Component.text("Coal block : 1%").decoration(TextDecoration.ITALIC, false)));

        inv.setItem(20, tier2);

        ItemStack tier3 = JustSkyblock.createItem(Component.text("Tier 3"), 1, Material.CHEST,
                List.of(Component.text("Stone : 40%"),
                        Component.text("Granite : 8%"),
                        Component.text("Diorite : 8%"),
                        Component.text("Andesite : 8%"),
                        Component.text("Gravel : 7%"),
                        Component.text("Coal_ore : 8%"),
                        Component.text("Iron_ore : 10%"),
                        Component.text("Lapis_ore : 6%")));

        inv.setItem(20, tier3);
    }


    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }


}
