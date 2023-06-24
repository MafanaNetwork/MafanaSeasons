package me.TahaCheji.data;

import me.TahaCheji.MafanaSeasons;
import me.TahaCheji.data.Season;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeasonManager extends BukkitRunnable {

    private final World world;
    private final SeasonsMysql seasonsMysql = MafanaSeasons.getInstance().getSeasonsMysql();
    private Season currentSeason = getSeason(seasonsMysql.getSeason());
    private int daysPassed = seasonsMysql.getDay();
    private int minutesPassed = seasonsMysql.getDay() * 1440;

    private final List<Player> onlinePlayers;

    public SeasonManager(World world) {
        this.world = world;
        this.onlinePlayers = new ArrayList<>();
    }

    public void start() {
        runTaskTimer(MafanaSeasons.getInstance(), 0, 60L); // Runs every second (20 ticks)
    }

    public void addPlayer(Player player) {
        onlinePlayers.add(player);
    }

    public void removePlayer(Player player) {
        onlinePlayers.remove(player);
    }

    @Override
    public void run() {
        // Increment the minutes passed
        minutesPassed++;
        currentSeason.whileThere(world);

        // Calculate the current time in Minecraft hours
        int minecraftHours = (int) (minutesPassed / 1.2);

        // Check if a day has passed
        if (minecraftHours >= 24) {
            nextDay();
        } else {
            world.setTime(minecraftHours * 1000L);
        }
    }

    public void changeSeason(Season season) {
        currentSeason = season;
        seasonsMysql.setSeason(season.getName());
        for(Player player : world.getPlayers()) {
            player.sendMessage(ChatColor.YELLOW + "The season has changed to " + season.getName() +".");
        }
    }

    public void nextDay() {
        minutesPassed = 0;

        // Check if a season should change
        if (seasonsMysql.getDay() % 30 == 0) {
            nextSeason();
        }

        // Reset the time to 0 (12am)
        world.setTime(0);

        // Update the day in the database
        seasonsMysql.setDay(seasonsMysql.getDay() + 1);
        currentSeason.eventOne(world);
        currentSeason.eventTwo(world);
        currentSeason.eventTree(world);
        currentSeason.eventFour(world);
    }

    public void setDay(int i) {
        for(int x = 0; x < i; x++) {
            nextDay();
        }
    }

    public Season nextSeason() {
        List<Season> seasonList = MafanaSeasons.getInstance().getSeasonList();
        Season nextSeason = null;
        int currentPriority = currentSeason.getPriority();

        for (Season season : seasonList) {
            int seasonPriority = season.getPriority();

            if (seasonPriority > currentPriority) {
                if (nextSeason == null || seasonPriority < nextSeason.getPriority()) {
                    nextSeason = season;
                }
            }
        }

        if (nextSeason == null) {
            for (Season season : seasonList) {
                int seasonPriority = season.getPriority();

                if (nextSeason == null || seasonPriority < nextSeason.getPriority()) {
                    seasonsMysql.setDay(0);

                    nextSeason = season;
                }
            }
        }
        changeSeason(nextSeason);
        return nextSeason;
    }



    public Season getSeason(String s) {
        return MafanaSeasons.getInstance().getSeason(s);
    }

    public void setCurrentSeason(Season currentSeason) {
        this.currentSeason = currentSeason;
    }

    public void setDaysPassed(int daysPassed) {
        this.daysPassed = daysPassed;
    }

    public void setMinutesPassed(int minutesPassed) {
        this.minutesPassed = minutesPassed;
    }

    public World getWorld() {
        return world;
    }

    public SeasonsMysql getSeasonsMysql() {
        return seasonsMysql;
    }

    public Season getCurrentSeason() {
        return currentSeason;
    }

    public int getDaysPassed() {
        return daysPassed;
    }

    public int getMinutesPassed() {
        return minutesPassed;
    }

    public List<Player> getOnlinePlayers() {
        return onlinePlayers;
    }
}

