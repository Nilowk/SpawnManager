package fr.nilowk.spawnmanager.listener;

import fr.nilowk.spawnmanager.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Manager implements Listener {

    private Main instance;

    public Manager(Main instance) {

        this.instance = instance;

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(instance.getSpawn());

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (instance.getVisit().contains(player)) {

            instance.getVisit().remove(player);

        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (instance.getVisit().contains(player)) {

            event.setCancelled(true);

        }

    }

    @EventHandler
    public void onTakeDamage(EntityDamageEvent event) {

        if (event.getEntityType() == EntityType.PLAYER) {

            event.setCancelled(true);

        }

    }

}
