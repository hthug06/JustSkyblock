package fr.ht06.justskyblock;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class LoadSchematic {

    Location location;
    String filename;

    public LoadSchematic(Location location,String worldName ,String fileName){
        this.location = location;
        this.filename = fileName;

        ClipboardFormat format = ClipboardFormats.findByFile(new File(getServer().getPluginsFolder().getAbsoluteFile() + "/JustSkyblock/Schematic/" + fileName));

        if (!new File(getServer().getPluginsFolder().getAbsoluteFile() + "/JustSkyblock/Schematic/" + fileName).exists()){
            getLogger().severe("The schematic : '" + fileName +"' didn't exist");
            return;
        }


        try {
            assert format != null;
            try (ClipboardReader reader = format.getReader(new FileInputStream(new File(getServer().getPluginsFolder().getAbsoluteFile() + "/JustSkyblock/Schematic/"+fileName)));
                     EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(Objects.requireNonNull(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()))), -1)
            ) {
                Clipboard clipboard = reader.read();

                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(Double.parseDouble(String.valueOf(location.getX())), Double.parseDouble(String.valueOf(location.getY())), Double.parseDouble(String.valueOf(location.getZ()))))
                        .ignoreAirBlocks(false)
                        .build();
                Operations.complete(operation);
            }
        } catch (IOException | WorldEditException e) {
            throw new RuntimeException(e);
        }



    }



}
