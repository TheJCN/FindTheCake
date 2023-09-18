package jcn.findthecake.Main;

import jcn.findthecake.GameManager.GameManager;
import jcn.findthecake.Utilities.Metadata;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Logic {
    private static Logic instance;
    private GameManager gameManager;
    private Plugin plugin;
    private Block cakeBlock;

    private Logic(GameManager gameManager, Plugin plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    public static Logic getInstance(GameManager gameManager, Plugin plugin) {
        if (instance == null) {
            instance = new Logic(gameManager, plugin);
        }
        return instance;
    }

    public void Start() {
        cakeBlock = getLocationForSpawn().getBlock();
        cakeBlock.setType(Material.CAKE);
        Metadata metadata = new Metadata(plugin, "Event Item Value");
        cakeBlock.setMetadata("Event Block", metadata);

        int timerTicks = 5 * 60 * 20;
        Timer(timerTicks);
    }

    private Location getLocationForSpawn() {
        Random random = new Random();
        double x = random.nextDouble() * 10;
        double y = random.nextDouble() * 20 + 60;
        double z = random.nextDouble() * 10;
        Location location = new Location(Bukkit.getWorld("world"), x, y, z);
        Block block = location.getBlock();
        if (block.isEmpty()) {
            Location blockBelowLocation = location.subtract(0, 1, 0);
            Block blockBelow = blockBelowLocation.getBlock();
            if (!blockBelow.isEmpty()) {
                return location;
            }
        }
        return getLocationForSpawn();
    }

    public void Timer(int time) {
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (cakeBlock.getType() == Material.CAKE) {
                    Bukkit.broadcastMessage("Торт все еще никто не нашел!");
                    Bukkit.broadcastMessage("Его координаты " + cakeBlock.getLocation());
                }
            }
        };
        bukkitRunnable.runTaskLater(plugin, time);
    }
}
