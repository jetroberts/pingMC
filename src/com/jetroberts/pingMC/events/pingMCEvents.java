package com.jetroberts.pingMC.events;

import com.jetroberts.pingMC.items.itemManager;
import com.jetroberts.pingMC.pingMC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.Objects;

public class pingMCEvents implements Listener {

    private final pingMC plugin;

    public pingMCEvents(pingMC plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if(hasRightClicked(event)){
            if(hasItemInHand(event)){
                if(hasPingTool(event)){
                    placePingItem(event);
                }
            }
        }
    }

    private boolean hasRightClicked(PlayerInteractEvent event) {
        return event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK;
    }

    private boolean hasItemInHand(PlayerInteractEvent event) {
        return event.getItem() != null;
    }

    private boolean hasPingTool(PlayerInteractEvent event) {
        return Objects.equals(Objects.requireNonNull(event.getItem()).getItemMeta(), itemManager.pingtool.getItemMeta());
    }

    private void placePingItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block targetBlock = player.getTargetBlock(null, 45);

        if(targetBlock.getType() != Material.AIR){
            Location targetLocation = targetBlock.getLocation();
            targetLocation.setY(targetLocation.getY() + 1);


            if(checkThatBlockIsAir(targetLocation)) {
                targetLocation.setX(targetLocation.getX() - 0.25);
                targetLocation.setZ(targetLocation.getZ() + 0.25);
                targetLocation.setYaw((float) 135);
                placeArmorStandAtBlock(targetLocation, player);
            }
        }
    }

    private void placeArmorStandAtBlock(Location targetLocation, Player player) {
        ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(targetLocation, EntityType.ARMOR_STAND);
        Objects.requireNonNull(stand.getEquipment()).setItemInMainHand(new ItemStack(Material.REDSTONE));

        stand.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(0), Math.toRadians(0)));

        stand.setVisible(false);
        stand.setInvulnerable(true);
        stand.setMarker(true);
        stand.setGlowing(false);
        stand.setBasePlate(false);
        stand.setSilent(true);
        stand.setGravity(false);

        delayedDelete(stand);
    }

    private void delayedDelete(ArmorStand stand){
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                stand.remove();
            }
        };

        task.runTaskLater(this.plugin, 20 * 6);
    }

    private boolean checkThatBlockIsAir(Location newTargetLocation) {
        return newTargetLocation.getBlock().getType() == Material.AIR;
    }
}
