package jcn.findthecake.Main;

import jcn.findthecake.GameManager.GameManager;
import jcn.findthecake.GameManager.GameState;
import jcn.findthecake.Utilities.Metadata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class Logic {
    private static Logic instance;
    private String PREFIX;
    private GameManager gameManager;
    private Plugin plugin;
    private Block cakeBlock;
    private BukkitTask timerTask;

    private Logic(GameManager gameManager, Plugin plugin, String PREFIX) {
        this.gameManager = gameManager;
        this.plugin = plugin;
        this.PREFIX = PREFIX;
    }

    public static Logic getInstance(GameManager gameManager, Plugin plugin, String PREFIX) {
        if (instance == null) {
            instance = new Logic(gameManager, plugin, PREFIX);
        }
        return instance;
    }

    public void Start() {
        if (!(gameManager.getGameState() == GameState.Active)) {
            return;
        }
        cakeBlock = getLocationForSpawn().getBlock();
        cakeBlock.setType(Material.CAKE);
        Metadata metadata = new Metadata(plugin, "Event Item Value");
        cakeBlock.setMetadata("Event Block", metadata);
        Bukkit.getLogger().info("Админ лог: Торт был размещен " + cakeBlock.getLocation());
        int timerTicks = 5 * 20;
        startTimer(timerTicks);
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

    public void startTimer(int time) {
        timerTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (cakeBlock.getType() == Material.CAKE) {
                    Bukkit.broadcastMessage(PREFIX + ChatColor.AQUA + "Торт все еще никто не нашел!");
                    Bukkit.broadcastMessage(PREFIX + ChatColor.AQUA + "Его координаты: " + ChatColor.LIGHT_PURPLE + "X - " + cakeBlock.getLocation().getBlockX()  + " Y - " + cakeBlock.getLocation().getBlockY() + " Z - " + cakeBlock.getLocation().getBlockZ());
                }
            }
        }.runTaskLater(plugin, time);
    }

    public void cancelTimer() {
        if (timerTask != null) {
            timerTask.cancel();
        }
    }
}
