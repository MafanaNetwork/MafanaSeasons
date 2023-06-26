package me.TahaCheji.data.seasons;

import it.unimi.dsi.fastutil.Hash;
import me.TahaCheji.data.Season;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Fall extends Season {
    public Fall() {
        super("Fall", ChatColor.DARK_PURPLE + "☁", 2);
    }

    private static HashMap<Location, Material> changedBlocks = new HashMap<>();

    @Override
    public boolean eventOne(World world) {
        double chance = 0.8; // Adjust the chance as desired (between 0.0 and 1.0)
        for (Player player : Bukkit.getOnlinePlayers()) {
            Location playerLocation = player.getLocation();
            int radius = 25; // Adjust the radius as desired

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Location blockLocation = playerLocation.clone().add(x, y, z);
                        Block block = blockLocation.getBlock();
                        world.setBiome(block.getLocation(), Biome.DARK_FOREST);
                        if (block.getType() == Material.OAK_LEAVES || block.getType() == Material.BIRCH_LEAVES || block.getType() == Material.SPRUCE_LEAVES) {
                            if (Math.random() < chance) {
                                Fall.getChangedBlocks().put(block.getLocation(), block.getType());
                                block.setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }
        Spring.removeSpawnedFlowers();
        world.setStorm(true);
        world.setWeatherDuration(3000);
        world.setThundering(true);
        world.setThunderDuration(3000);
        return true;
    }

    public static void restoreLeaves() {
        for (Location blockLocation : Fall.getChangedBlocks().keySet()) {
            Block block = blockLocation.getBlock();
            block.setType(Fall.getChangedBlocks().get(blockLocation)); // Change to the appropriate leaf material
        }
        Fall.getChangedBlocks().clear(); // Clear the list after restoring the leaves
    }

    public static HashMap<Location, Material> getChangedBlocks() {
        return changedBlocks;
    }
}
