package fr.nilowk.spawnmanager.listener;

import fr.nilowk.spawnmanager.Main;
import fr.nilowk.spawnmanager.task.Visit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class Navigation implements Listener {

    private Main instance;

    public Navigation(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        player.getInventory().clear();


        ItemStack nav = new ItemStack(Material.COMPASS);

        ItemMeta customN = nav.getItemMeta();
        customN.setDisplayName("§9Navigation");

        nav.setItemMeta(customN);
        player.getInventory().setItem(4, nav);



        ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);

        ItemMeta customT = totem.getItemMeta();
        customT.setDisplayName("§2Visit");

        totem.setItemMeta(customT);
        player.getInventory().setItem(0, totem);

    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (item == null) return;

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {

            if (item.getType() == Material.COMPASS && item.getItemMeta().getDisplayName().equalsIgnoreCase("§9Navigation")) {

                Inventory nav = Bukkit.createInventory(null, 27, "Navigation");
                player.openInventory(nav);

            } else if (item.getType() == Material.TOTEM_OF_UNDYING && item.getItemMeta().getDisplayName().equalsIgnoreCase("§2Visit")) {

                HashMap<Integer, Location> hash = new HashMap<>();
                int num = 0;

                for (String sec : instance.getConfig().getConfigurationSection("view").getKeys(false)) {

                    ConfigurationSection section = instance.getConfig().getConfigurationSection("view." + sec);

                    World world = Bukkit.getWorld(section.getString("world"));
                    double x = section.getDouble("x");
                    double y = section.getDouble("y");
                    double z = section.getDouble("z");
                    float yaw = (float) section.getDouble("yaw");
                    float pitch = (float) section.getDouble("pitch");

                    Location loc = new Location(world, x, y, z, yaw, pitch);
                    hash.put(num, loc);
                    num++;

                }

                player.sendMessage(instance.getConfig().getString("message.visit"));
                instance.getVisit().add(player);
                Visit visit = new Visit(instance, hash, player);
                visit.runTaskTimer(instance, 0, instance.getConfig().getInt("time.view") * 20);

            }

        }

    }

}
