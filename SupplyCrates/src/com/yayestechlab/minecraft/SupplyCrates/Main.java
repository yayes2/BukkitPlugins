package com.yayestechlab.minecraft.SupplyCrates;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.yayestechlab.minecraft.SupplyCrates.config.DataManager;
import com.yayestechlab.minecraft.SupplyCrates.listeners.SupplyCratesListener;

public class Main extends JavaPlugin{
	
	public HashMap<Player, Location> invlocs = new HashMap<Player, Location>();
	
	public FileConfiguration cfg;
	
	DataManager dm = new DataManager();
	
	public Location convertVectorToLocation(Vector v){
		World world = getServer().getWorld("world");
		Location loc = new Location(world, v.getX(), v.getY(), v.getZ());
		return loc;
	}

	public Vector convertLocationToVector(Location loc){
		Vector v = new Vector(loc.getX(), loc.getY(), loc.getZ());
		return v;
	}
	
	public DataManager getDataManager(){
		return dm;
	}
	
	public void onEnable(){
		dm.saveDefaultCrateConfig(this);
		cfg = dm.getCrateConfig(this);
		getServer().getPluginManager().registerEvents(new SupplyCratesListener(this), this);
	}
 
	public void onDisable(){
		dm.saveCrateConfig(this, cfg);
	}
}
