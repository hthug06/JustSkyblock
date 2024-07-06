package fr.ht06.justskyblock.Events;

import fr.ht06.justskyblock.JustSkyblock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.ItemMergeEvent;

import java.util.List;
import java.util.Random;

public class CobbleGenEvent implements Listener {

    Random random = new Random();

    @EventHandler
    public void onGenCobble(BlockFromToEvent event){

        Block source = event.getBlock();
        Block generatedBlock = event.getToBlock();

        if (!JustSkyblock.getInstance().getConfig().getConfigurationSection("CustomGenerator").getBoolean("Enable")) return;

        if (source.getType() != Material.LAVA) {
            return;
        }

        if (!this.isCobbleGenerator(generatedBlock)) {
            return;
        }

        event.setCancelled(true);
        generatedBlock.setType(chooseBlock());

    }

    private boolean isCobbleGenerator(Block generatedBlock) {
        BlockFace[] faces = {BlockFace.SELF, BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

        // At some face of the generated block has to be water or lava (by secondLiquid)
        for (BlockFace face : faces) {
            Block blockNextToGenerated = generatedBlock.getRelative(face, 1);
            if (blockNextToGenerated.getType() == Material.WATER) {
                return true;
            }
        }

        return false;
    }

    private Material chooseBlock(){
        List<Material> blocks = List.of(Material.STONE, Material.COAL_ORE, Material.IRON_ORE, Material.LAPIS_ORE,
                Material.REDSTONE_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE);

        int nbrRandom = random.nextInt(101);

        if (nbrRandom<=40){
            return blocks.get(0);
        }
        else if (nbrRandom<=65){
            return blocks.get(1);
        }
        else if (nbrRandom<=85){
            return blocks.get(2);
        }
        else if (nbrRandom<=91){
            return blocks.get(3);
        }
        else if (nbrRandom<=97){
            return blocks.get(4);
        }
        else if (nbrRandom<=99){
            return blocks.get(5);
        }
        else {
            return blocks.get(6);
        }
    }

}
