package com.jetroberts.pingMC.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class itemManager {

    public static ItemStack pingtool;

    public static void init() {
        createPingTool();
    }

    private static void createPingTool() {
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("PingTool");

        List<String> lore = new ArrayList<>();
        lore.add("This tool will allow you");
        lore.add("to ping things around the world.");
        meta.setLore(lore);

        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        pingtool = item;
    }
}
