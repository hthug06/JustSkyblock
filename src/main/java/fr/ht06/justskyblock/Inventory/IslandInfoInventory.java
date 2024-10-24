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
import java.util.UUID;

public class IslandInfoInventory implements InventoryHolder {

    Inventory inv;

    public IslandInfoInventory(Island island, Player player){
        inv = Bukkit.createInventory(this, 36, island.getIslandName());
        init(island, player);
    }

    private void init(Island island, Player player) {
        for (int slot = 0; slot <=35; slot++) {
            ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setHideTooltip(true);
            itemStack.setItemMeta(itemMeta);
            inv.setItem(slot, itemStack);
        }

        for (int i = 0; i<= 26; i++){

            switch (i){
                case 10->{
                    ItemStack itemStack = new ItemStack(Material.CLOCK, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.displayName(Component.text("Creation date", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                    itemMeta.lore(List.of(Component.text(String.valueOf(island.getDateToString()), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false),
                            Component.text(""),
                            Component.text("(The creation date might be not accurate,", TextColor.color(0xAEB6BF)),
                            Component.text("This is because the timezone is the server timezone).", TextColor.color(0xAEB6BF))));
                    itemStack.setItemMeta(itemMeta);
                    inv.setItem(i, itemStack);
                }
                case 11 -> {
                    ItemStack itemStack = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.displayName(Component.text("Island Level", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                    itemMeta.lore(List.of(Component.text(String.valueOf(island.getLevel()), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false),
                            Component.text("(Click to update the island level)" ,TextColor.color(0xAEB6BF))));
                    itemStack.setItemMeta(itemMeta);
                    inv.setItem(i, itemStack);
                }
                case 12 -> {
                    ItemStack itemStack = new ItemStack(Material.OAK_DOOR, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.lore(List.of(Component.text("Visit the island", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false)));
                    itemStack.setItemMeta(itemMeta);
                    inv.setItem(i, itemStack);
                }
                case 13 -> {
                    ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1);
                    SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(island.getOwner());
                    if (offlinePlayer.getPlayer() != null) {
                        skullMeta.setOwningPlayer(offlinePlayer.getPlayer());
                    }
                    skullMeta.displayName(Component.text("Owner of the island", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));

                    Player target = Bukkit.getPlayer(island.getOwner());
                    if (target != null && target.isOnline()) {
                        skullMeta.lore(List.of(Component.text(Bukkit.getOfflinePlayer(island.getOwner()).getName(), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false).append(Component.text(" ●", TextColor.color(0x58D68D)).decoration(TextDecoration.ITALIC, false))));

                    } else {
                        skullMeta.lore(List.of(Component.text(Bukkit.getOfflinePlayer(island.getOwner()).getName(), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false).append(Component.text(" ●", TextColor.color(0xE74C3C)).decoration(TextDecoration.ITALIC, false))));
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
                        for (UUID member : island.getAllMembers()){
                            Player target = Bukkit.getPlayer(member);
                            if (target != null && target.isOnline()) {
                                liste.add(Component.text("- "+ Bukkit.getOfflinePlayer(member).getName(), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false).append(Component.text(" ●", TextColor.color(0x58D68D)).decoration(TextDecoration.ITALIC, false)));

                            } else {
                                liste.add(Component.text("- "+Bukkit.getOfflinePlayer(member).getName(), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false).append(Component.text(" ●", TextColor.color(0xE74C3C)).decoration(TextDecoration.ITALIC, false)));

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
                        itemMeta.displayName(Component.text("Moderator of the island", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                        itemMeta.lore(List.of(Component.text("No moderator on this island :(", TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false)));
                        itemStack.setItemMeta(itemMeta);
                        inv.setItem(i, itemStack);
                    }
                    else{
                        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, island.getAllModerators().size());
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.displayName(Component.text("Moderator of the island", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                        List<Component> liste = new ArrayList<>();
                        for (UUID moderator : island.getAllModerators()){
                            Player target = Bukkit.getPlayer(moderator);
                            if (target != null && target.isOnline()) {
                                liste.add(Component.text("- "+ Bukkit.getOfflinePlayer(moderator).getName(), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false).append(Component.text(" ●", TextColor.color(0x58D68D)).decoration(TextDecoration.ITALIC, false)));

                            } else {
                                liste.add(Component.text("- "+ Bukkit.getOfflinePlayer(moderator).getName(), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false).append(Component.text(" ●", TextColor.color(0xE74C3C)).decoration(TextDecoration.ITALIC, false)));

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
                case 19-> {
                    ItemStack itemStack = new ItemStack(Material.TNT, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.displayName(Component.text("Delete the Island", TextColor.color(0xF73331)).decoration(TextDecoration.ITALIC, false));
                    itemStack.setItemMeta(itemMeta);
                    inv.setItem(i, itemStack);
                }
                case 20-> {
                    ItemStack itemStack = new ItemStack(Material.DIAMOND_PICKAXE, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.displayName(Component.text("Custom Generator Level", TextColor.color(0x4A9AF7)).decoration(TextDecoration.ITALIC, false));
                    itemMeta.lore(List.of(Component.text(String.valueOf("Level Unlocked: "+island.getCobbleGenLevelUnlock()), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false),
                            Component.text("Level currently in use : "+island.getCobbleGenLevel(), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false)));
                    itemStack.setItemMeta(itemMeta);
                    inv.setItem(i, itemStack);
                }

                case 24 ->{
                    String coordinates = String.valueOf(island.getCoordinates().getBlockX() + ", " + island.getCoordinates().getBlockY() + ", " + island.getCoordinates().getBlockZ());
                    ItemStack itemStack = new ItemStack(Material.COMPASS, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.displayName(Component.text("Island Coordinates", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                    itemMeta.lore(List.of(Component.text(coordinates, TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false)));
                    itemStack.setItemMeta(itemMeta);
                    inv.setItem(i, itemStack);
                }

                case 25 ->{
                    ItemStack itemStack = new ItemStack(Material.GRASS_BLOCK, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.displayName(Component.text("Island Size", TextColor.color(0xF7DC6F)).decoration(TextDecoration.ITALIC, false));
                    itemMeta.lore(List.of(Component.text(String.valueOf(island.getSize()+"x"+island.getSize()), TextColor.color(0xAEB6BF)).decoration(TextDecoration.ITALIC, false)));
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
