package com.yayestechlab.minecraft.SupplyCrates.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_6_R3.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.yayestechlab.minecraft.SupplyCrates.Main;
import com.yayestechlab.minecraft.SupplyCrates.config.DataManager;

public class SupplyCratesListener implements Listener {
	
	private Main plugin;
	private DataManager dm;
	private FileConfiguration cfg;
	
	public SupplyCratesListener(Main plugin1) {
		plugin = plugin1;
		dm = plugin.getDataManager();
		cfg = dm.getCrateConfig(plugin);
	}
	
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e){
		Player p = e.getPlayer();
		Block b = p.getTargetBlock(null, 5);;
		int id = b.getTypeId();
		int metadata = b.getData();
		if ((id == 29 || id == 33) && metadata == 6) {
			if (cfg.getString("inv." + b.getLocation()) != null){
				String invstring = cfg.getString("inv." +  b.getLocation().toString());
				Inventory invnoname = dm.fromBase64(invstring);
				Inventory inv = new CraftInventoryCustom(p, 27, "Supply Crate");
				inv.setContents(invnoname.getContents());
				System.out.println(inv.toString());
				p.openInventory(inv);
				plugin.invlocs.put(p, b.getLocation());
			} else {
				Inventory inv = new CraftInventoryCustom(p, 27, "Supply Crate");
				System.out.println(inv.toString());
				p.openInventory(inv);
				plugin.invlocs.put(p, b.getLocation());
			}
		}
	}
	
	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent e){
		Inventory inv = e.getInventory();
		System.out.println(inv.toString());
		if (inv.getName().equals("Supply Crate")){
			Location loc = plugin.invlocs.get(e.getPlayer());
			System.out.println(loc.toString());
			cfg.set("inv." + loc.toString(), dm.toBase64(inv));
		}
	}
	
}
