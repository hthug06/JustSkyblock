package fr.ht06.justskyblock;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class CreateItem {

    public static ItemStack createItem(@Nullable Component name, @Nullable Integer amount, Material material){
        ItemStack itemStack = new ItemStack(material, Objects.requireNonNullElse(amount, 1));
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (name!=null) itemMeta.displayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItem(@Nullable Component name, @Nullable Integer amount, Material material, List<Component> lore){
        ItemStack itemStack = new ItemStack(material, Objects.requireNonNullElse(amount, 1));
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (name!=null) itemMeta.displayName(name);
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItem(@Nullable Component name, @Nullable Integer amount, Material material, List<Component> lore, Boolean hideToolTips){
        ItemStack itemStack = new ItemStack(material, Objects.requireNonNullElse(amount, 1));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name);
        itemMeta.lore(lore);
        itemMeta.setHideTooltip(hideToolTips);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
