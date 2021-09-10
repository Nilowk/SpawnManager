package fr.nilowk.spawnmanager.task;

import fr.nilowk.spawnmanager.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public class Visit extends BukkitRunnable {

    private Main instance;
    private Player player;
    private HashMap<Integer, Location> locations;
    private int num;
    private int timer = -1;

    public Visit(Main instance, HashMap<Integer, Location> locations, Player player) {

        this.instance = instance;
        this.locations = locations;
        num = timer + locations.size();
        this.player = player;

    }

    @Override
    public void run() {

        if (timer > num) {

            if (instance.getVisit().contains(player)) {

                player.setGameMode(GameMode.ADVENTURE);
                player.teleport(instance.getSpawn());
                instance.getVisit().remove(player);
                player.sendMessage(instance.getConfig().getString("message.stop_visit"));

            }

            cancel();

        }

        if (timer >= 0) {

            Location loc = locations.get(timer);

            if (instance.getVisit().contains(player)) {

                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(loc);

            }

        }

        timer++;

    }

}
