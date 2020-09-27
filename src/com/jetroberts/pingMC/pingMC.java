package com.jetroberts.pingMC;

import com.jetroberts.pingMC.commands.pingMCCommands;
import com.jetroberts.pingMC.events.pingMCEvents;
import com.jetroberts.pingMC.items.itemManager;
import org.bukkit.plugin.java.JavaPlugin;

public class pingMC extends JavaPlugin {

    @Override
    public void onEnable() {
        itemManager.init();
        new pingMCEvents(this);
        getCommand("getpinger").setExecutor(new pingMCCommands());
    }

    @Override
    public void onDisable() {
    }
}
