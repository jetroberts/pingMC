package com.jetroberts.pingMC.events;

import com.jetroberts.pingMC.items.itemManager;
import com.jetroberts.pingMC.pingMC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class pingMCEvents implements Listener {

    private final pingMC plugin;

    public pingMCEvents(pingMC plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(event.getItem() != null){
                if(Objects.equals(event.getItem().getItemMeta(), itemManager.pingtool.getItemMeta())){
                    placePingItem(event);
                }
            }
        }
    }

    public void placePingItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block targetBlock = player.getTargetBlock(null, 45);

        if(targetBlock.getType() != Material.AIR){
            Location newTargetLocation = targetBlock.getLocation();
            newTargetLocation.setY(newTargetLocation.getY() + 1);

            Block currentBlock = newTargetLocation.getBlock();
            if(checkThatBlockIsAir(currentBlock)) {
                ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(newTargetLocation, EntityType.ARMOR_STAND);
                stand.setMarker(true);
                stand.setVisible(false);
                delayedDelete(stand);
            }
        }
    }

    private void delayedDelete(ArmorStand stand){
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                stand.remove();
            }
        };

        task.runTaskLater(this.plugin, 20 * 4);
    }

    private boolean checkThatBlockIsAir(Block currentBlock) {
        return currentBlock.getType() == Material.AIR;
    }
}
