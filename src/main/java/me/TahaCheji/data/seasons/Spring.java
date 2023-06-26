package me.TahaCheji.data.seasons;

import me.TahaCheji.data.Season;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Spring extends Season {
    public Spring() {
        super("Spring", ChatColor.GREEN + "☀", 4);
    }

    private static List<Block> spawnedFlowers = new ArrayList<>(); // List to store the spawned flowers

    @Override
    public boolean eventOne(World world) {
        int i = ThreadLocalRandom.current().nextInt(1, 4);
        if (i == 1 || i == 2) {
            for (Player player : world.getPlayers()) {
                spawnRandomFlowersAroundPlayer(player, 16, 32);
                Location playerLocation = player.getLocation();
                int radius = 128;
                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            Location blockLocation = playerLocation.clone().add(x, y, z);
                            Block block = blockLocation.getBlock();
                            if (block.getType() == Material.SNOW || block.getType() == Material.SNOW_BLOCK) {
                                block.setType(Material.AIR);
                            }
                            if (block.getType() == Material.ICE || block.getType() == Material.PACKED_ICE) {
                                block.setType(Material.WATER);
                            }
                            world.setBiome(block.getLocation(), Biome.PLAINS);
                        }
                    }
                }
            }
        } else {
            world.setStorm(true);
            world.setWeatherDuration(1500);
            world.setThundering(false);
        }
        if (i == 1) {
            Fall.restoreLeaves();
        }
        return true;
    }

    public static void spawnRandomFlowersAroundPlayer(Player player, int flowerCount, int radius) {
        Location playerLocation = player.getLocation();
        int i = 0;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (i >= flowerCount) {
                        return;
                    }
                    int random = ThreadLocalRandom.current().nextInt(1, 30);
                    if (random == 1) {
                        Location blockLocation = playerLocation.clone().add(x, y, z);
                        Block block = blockLocation.getBlock();
                        if (block.getType() == Material.GRASS_BLOCK) {
                            Material flowerType = getRandomFlowerType();
                            Block flowerBlock = player.getWorld().getBlockAt(block.getLocation().add(0, 1, 0));
                            flowerBlock.setType(flowerType);
                            getSpawnedFlowers().add(flowerBlock);
                            i += 1;
                        }
                    }
                }
            }
        }
    }


    private static Material getRandomFlowerType() {
        Material[] flowerTypes = {Material.DANDELION, Material.POPPY, Material.BLUE_ORCHID, Material.ALLIUM,
                Material.AZURE_BLUET, Material.RED_TULIP, Material.ORANGE_TULIP, Material.WHITE_TULIP,
                Material.PINK_TULIP, Material.OXEYE_DAISY};

        int randomIndex = (int) (Math.random() * flowerTypes.length);
        return flowerTypes[randomIndex];
    }

    public static void removeSpawnedFlowers() {
        for (Block flowerBlock : spawnedFlowers) {
            flowerBlock.setType(Material.AIR);
        }
        spawnedFlowers.clear(); // Clear the list after removing the flowers
    }

    public static List<Block> getSpawnedFlowers() {
        return spawnedFlowers;
    }
}
