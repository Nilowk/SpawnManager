package fr.nilowk.spawnmanager;

import fr.nilowk.spawnmanager.listener.Manager;
import fr.nilowk.spawnmanager.listener.Navigation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private List<Player> visit = new ArrayList<>();

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new Navigation(this), this);
        getServer().getPluginManager().registerEvents(new Manager(this), this);

    }

    public Location getSpawn() {

        World world = Bukkit.getWorld(getConfig().getString("spawn.world"));
        double x = getConfig().getDouble("spawn.x");
        double y = getConfig().getDouble("spawn.y");
        double z = getConfig().getDouble("spawn.z");
        float yaw = (float) getConfig().getDouble("spawn.yaw");
        float pitch = (float) getConfig().getDouble("spawn.pitch");

        Location spawn = new Location(world, x, y, z, yaw, pitch);

        return spawn;

    }

    public List<Player> getVisit() {

        return this.visit;

    }

}
