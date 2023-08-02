package me.TahaCheji;

import me.TahaCheji.command.PlayerAdminCommand;
import me.TahaCheji.data.Season;
import me.TahaCheji.data.SeasonManager;
import me.TahaCheji.data.SeasonsMysql;
import me.TahaCheji.data.seasons.Fall;
import me.TahaCheji.data.seasons.Spring;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MafanaSeasons extends JavaPlugin {

    private static MafanaSeasons instance;
    private SeasonsMysql seasonsMysql = new SeasonsMysql();
    private SeasonManager seasonManager;
    private List<Season> seasonList = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        String packageName = getClass().getPackage().getName();
        for (Class<?> clazz : new Reflections(packageName, ".events").getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listener, this);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ignored) {

            }
        }
        for (Class<?> clazz : new Reflections(packageName, ".events").getSubTypesOf(Season.class)) {
            try {
                Season getClass = (Season) clazz.getDeclaredConstructor().newInstance();
                seasonList.add(getClass);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ignored) {
            }
        }
        seasonsMysql.connect();
        seasonManager = new SeasonManager(Bukkit.getWorld("world"));
        seasonManager.registerListeners();
        getCommand("mfseason").setExecutor(new PlayerAdminCommand());
    }

    @Override
    public void onDisable() {
        seasonsMysql.disconnect();
        Fall.restoreLeaves();
        Spring.removeSpawnedFlowers();
    }

    public SeasonManager getSeasonManager() {
        return seasonManager;
    }

    public List<Season> getSeasonList() {
        return seasonList;
    }

    public Season getSeason(String s) {
        for(Season season : getSeasonList()) {
            if(season.getName().equalsIgnoreCase(s)) {
                return season;
            }
        }
        return null;
    }

    public SeasonsMysql getSeasonsMysql() {
        return seasonsMysql;
    }

    public static MafanaSeasons getInstance() {
        return instance;
    }
}
