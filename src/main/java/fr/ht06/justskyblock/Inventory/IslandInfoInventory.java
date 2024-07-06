package fr.ht06.justskyblock.Inventory;

import fr.ht06.justskyblock.IslandManager.Island;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IslandInfoInventory implements InventoryHolder {

    Inventory inv;

    public IslandInfoInventory(Island island, Player player){
        inv = Bukkit.createInventory(this, 27, island.getIslandName());
        init(island, player);
    }

    private void init(Island island, Player player) {

        for (int i = 0; i<= 26; i++){

            switch (i){
                case 0, 1, 2, 3,4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26->{
                    ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setHideTooltip(true);
                    itemStack.setItemMeta(itemMeta);
                    inv.setItem(i, itemStack);
                }
                case 10->{
                    ItemStack itemStack = new ItemStack(Material.CLOCK, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.displayName(Component.text("Creation date", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                    itemMeta.lore(List.of(Component.text(String.valueOf(island.getDateToString()), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false)));
                    itemStack.setItemMeta(itemMeta);
                    inv.setItem(i, itemStack);
                }
                case 11 -> {
                    ItemStack itemStack = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.displayName(Component.text("Island Level", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                    itemMeta.lore(List.of(Component.text(String.valueOf(island.getLevel()), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false)));
                    itemStack.setItemMeta(itemMeta);
                    inv.setItem(i, itemStack);
                }
                case 12 -> {
                    ItemStack itemStack = new ItemStack(Material.OAK_DOOR, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.displayName(Component.text("Visit the island", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                    itemStack.setItemMeta(itemMeta);
                    inv.setItem(i, itemStack);
                }
                case 13 -> {
                    ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1);
                    SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(island.getOwner());
                    skullMeta.setOwningPlayer(offlinePlayer);
                    skullMeta.displayName(Component.text("Owner of the island", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));

                    Player target = Bukkit.getPlayerExact(island.getOwner());
                    if (target != null && target.isOnline()) {
                        skullMeta.lore(List.of(Component.text(island.getOwner(), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false).append(Component.text(" ●", TextColor.color(0x58D68D)).decoration(TextDecoration.ITALIC, false))));

                    } else {
                        skullMeta.lore(List.of(Component.text(island.getOwner(), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false).append(Component.text(" ●", TextColor.color(0xE74C3C)).decoration(TextDecoration.ITALIC, false))));
                    }
                    //skullMeta.lore(List.of(Component.text(String.valueOf(island.getOwner()), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false)));
                    itemStack.setItemMeta(skullMeta);
                    //itemStack.setItemMeta(itemMeta);
                    inv.setItem(i, itemStack);
                }
                case 14 -> {
                    if (island.getAllMembers().isEmpty()){
                        ItemStack itemStack = new ItemStack(Material.IRON_SWORD, 1);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.displayName(Component.text("Member of the island", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                        itemMeta.lore(List.of(Component.text("No member on this island :(", TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false)));
                        itemStack.setItemMeta(itemMeta);
                        inv.setItem(i, itemStack);
                    }
                    else{
                        ItemStack itemStack = new ItemStack(Material.IRON_SWORD, island.getAllMembers().size());
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.displayName(Component.text("Member of the island", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                        List<Component> liste = new ArrayList<>();
                        for (String member : island.getAllMembers()){
                            Player target = Bukkit.getPlayerExact(member);
                            if (target != null && target.isOnline()) {
                                liste.add(Component.text("- "+member, TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false).append(Component.text(" ●", TextColor.color(0x58D68D)).decoration(TextDecoration.ITALIC, false)));

                            } else {
                                liste.add(Component.text("- "+member, TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false).append(Component.text(" ●", TextColor.color(0xE74C3C)).decoration(TextDecoration.ITALIC, false)));

                            }
                        }
                        itemMeta.lore(liste);
                        itemStack.setItemMeta(itemMeta);
                        inv.setItem(i, itemStack);
                    }
                }
                case 15 -> {
                    if (island.getAllModerators().isEmpty()){
                        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, 1);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.displayName(Component.text("Member of the island", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                        itemMeta.lore(List.of(Component.text("No moderator on this island :(", TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false)));
                        itemStack.setItemMeta(itemMeta);
                        inv.setItem(i, itemStack);
                    }
                    else{
                        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, island.getAllMembers().size());
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.displayName(Component.text("Member of the island", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                        List<Component> liste = new ArrayList<>();
                        for (String moderator : island.getAllModerators()){
                            Player target = Bukkit.getPlayerExact(moderator);
                            if (target != null && target.isOnline()) {
                                liste.add(Component.text("- "+ moderator, TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false).append(Component.text(" ●", TextColor.color(0x58D68D)).decoration(TextDecoration.ITALIC, false)));

                            } else {
                                liste.add(Component.text("- "+moderator, TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false).append(Component.text(" ●", TextColor.color(0xE74C3C)).decoration(TextDecoration.ITALIC, false)));

                            }
                        }
                        itemMeta.lore(liste);
                        itemStack.setItemMeta(itemMeta);
                        inv.setItem(i, itemStack);
                    }
                }
                case 16 -> {
                    ItemStack itemStack = new ItemStack(Material.DIAMOND_BLOCK, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.displayName(Component.text("Island Rank", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                    itemMeta.lore(List.of(Component.text(String.valueOf(island.getRank()), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false)));
                    itemStack.setItemMeta(itemMeta);
                    inv.setItem(i, itemStack);
                }
            }

        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
