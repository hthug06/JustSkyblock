package fr.ht06.justskyblock.recipe;

import fr.ht06.justskyblock.JustSkyblock;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class ChainMailArmorRecipe {

    //Helmet
    public void Helmet(){
        ShapedRecipe shapedRecipe;
        ItemStack item;
        item = new ItemStack(Material.CHAINMAIL_HELMET, 1);

        shapedRecipe = new ShapedRecipe(new NamespacedKey(JustSkyblock.getInstance(), "ChainmailHelmet"), item);
        shapedRecipe.shape("   ",
                           "CCC",
                           "C C");
        shapedRecipe.setIngredient('C', Material.CHAIN);
        JustSkyblock.getInstance().getServer().addRecipe(shapedRecipe);

        item = new ItemStack(Material.CHAINMAIL_HELMET, 1);

        shapedRecipe = new ShapedRecipe(new NamespacedKey(JustSkyblock.getInstance(), "ChainmailHelmet2"), item);
        shapedRecipe.shape("CCC",
                           "C C",
                           "   ");
        shapedRecipe.setIngredient('C', Material.CHAIN);
        JustSkyblock.getInstance().getServer().addRecipe(shapedRecipe);
    }

    public void ChestPlate(){
        ShapedRecipe shapedRecipe;
        ItemStack item;
        item = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);

        shapedRecipe = new ShapedRecipe(new NamespacedKey(JustSkyblock.getInstance(), "ChainmailChestplate"), item);
        shapedRecipe.shape("C C",
                           "CCC",
                           "CCC");
        shapedRecipe.setIngredient('C', Material.CHAIN);
        JustSkyblock.getInstance().getServer().addRecipe(shapedRecipe);
    }

    public void Leggings(){
        ShapedRecipe shapedRecipe;
        ItemStack item;
        item = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);

        shapedRecipe = new ShapedRecipe(new NamespacedKey(JustSkyblock.getInstance(), "ChainmailLeggings"), item);
        shapedRecipe.shape("CCC",
                           "C C",
                           "C C");
        shapedRecipe.setIngredient('C', Material.CHAIN);
        JustSkyblock.getInstance().getServer().addRecipe(shapedRecipe);
    }

    public void Boots(){
        ShapedRecipe shapedRecipe;
        ItemStack item;
        item = new ItemStack(Material.CHAINMAIL_BOOTS, 1);

        shapedRecipe = new ShapedRecipe(new NamespacedKey(JustSkyblock.getInstance(), "ChainmailBoots"), item);
        shapedRecipe.shape("   ",
                           "C C",
                           "C C");
        shapedRecipe.setIngredient('C', Material.CHAIN);
        JustSkyblock.getInstance().getServer().addRecipe(shapedRecipe);

        item = new ItemStack(Material.CHAINMAIL_BOOTS, 1);

        shapedRecipe = new ShapedRecipe(new NamespacedKey(JustSkyblock.getInstance(), "ChainmailBoots2"), item);
        shapedRecipe.shape("C C",
                           "C C",
                           "   ");
        shapedRecipe.setIngredient('C', Material.CHAIN);
        JustSkyblock.getInstance().getServer().addRecipe(shapedRecipe);
    }

    public void createCraft(){
        this.Helmet();
        this.ChestPlate();
        this.Leggings();
        this.Boots();
    }
}
