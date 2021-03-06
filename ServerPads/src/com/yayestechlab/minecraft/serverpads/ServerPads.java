package com.yayestechlab.minecraft.serverpads;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.util.Vector;

import com.yayestechlab.minecraft.serverpads.config.DataManager;
import com.yayestechlab.minecraft.serverpads.listeners.ServerPadListener;
import com.yayestechlab.minecraft.serverpads.listeners.commands.ServerPadsCommandExecutor;

public class ServerPads extends JavaPlugin implements PluginMessageListener{
	
	public FileConfiguration cfg;
	
	public ServerPads getPluginMain(){
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
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
		getCommand("serverpads").setExecutor(new ServerPadsCommandExecutor(this));
		getCommand("sp").setExecutor(new ServerPadsCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new ServerPadListener(this), this);
	}
 
	public void onDisable(){
		DataManager.savePadConfig(this, cfg);
	}

	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		
	}
	
}
