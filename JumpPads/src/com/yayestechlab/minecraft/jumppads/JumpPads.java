package com.yayestechlab.minecraft.jumppads;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.yayestechlab.minecraft.jumppads.config.DataManager;
import com.yayestechlab.minecraft.jumppads.listeners.JumpPadListener;
import com.yayestechlab.minecraft.jumppads.listeners.commands.JumpPadsCommandExecutor;

public class JumpPads extends JavaPlugin{
	
	public FileConfiguration cfg;
	
	public JumpPads getPluginMain(){
		return this;
	}
	
	public Location convertVectorToLocation(Vector v){
		World world = getServer().getWorld("world");
		Location loc = new Location(world, v.getX(), v.getY(), v.getZ());
		return loc;
	}
	
	public Vector convertLocationToVector(Location loc){
		Vector v = new Vector(loc.getX(), loc.getY(), loc.getZ());
		return v;
	}
	
	public void onEnable(){
		DataManager.saveDefaultPadConfig(this);
		cfg = DataManager.getPadConfig(this);
		getCommand("jumppads").setExecutor(new JumpPadsCommandExecutor(this));
		getCommand("jp").setExecutor(new JumpPadsCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new JumpPadListener(this), this);
	}
 
	public void onDisable(){
		DataManager.savePadConfig(this, cfg);
	}
	
}
