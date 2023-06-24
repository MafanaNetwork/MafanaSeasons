package me.TahaCheji.data;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public interface SeasonEvents {

    default boolean whileThere(World world) {return false;}

    default boolean onJoin(World world, Player player) {return false;}

    default boolean eventOne(World world) {return false;}
    default boolean eventTwo(World world) {return false;}
    default boolean eventTree(World world) {return false;}
    default boolean eventFour(World world) {return false;}


}
