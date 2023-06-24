package me.TahaCheji.data.seasons;

import me.TahaCheji.data.Season;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

public class Fall extends Season {
    public Fall() {
        super("Fall", ChatColor.DARK_PURPLE + "☁", 2);
    }

    @Override
    public boolean whileThere(World world) {
        for (Chunk chunk : world.getLoadedChunks()) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int y = world.getHighestBlockYAt(chunk.getBlock(x, 0, z).getLocation());
                    Block block = chunk.getBlock(x, y, z);
                    world.setBiome(block.getLocation(), Biome.DARK_FOREST);
                }
            }
        }
        return true;
    }

    @Override
    public boolean eventOne(World world) {
        world.setStorm(true);
        world.setWeatherDuration(3000);
        world.setThundering(true);
        world.setThunderDuration(3000);
        return true;
    }

}
