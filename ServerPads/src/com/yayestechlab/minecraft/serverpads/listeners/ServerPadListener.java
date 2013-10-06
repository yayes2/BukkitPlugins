package com.yayestechlab.minecraft.serverpads.listeners;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.yayestechlab.minecraft.serverpads.ServerPads;
import com.yayestechlab.minecraft.serverpads.config.DataManager;

public class ServerPadListener implements Listener{
	ServerPads plugin;
	
	public ServerPadListener(ServerPads p){
		plugin = p;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		Vector ploc = plugin.convertLocationToVector(p.getLocation().getBlock().getLocation());
		FileConfiguration cfg = plugin.cfg;
		DataManager dm = new DataManager();
		String key = dm.getKeyByValueVector(ploc, cfg, plugin);
		if(key != null){
			Vector velocity = cfg.getVector("padvelocity." + key);
			String server = cfg.getString("padname." + key.replace("", ""));
			p.setVelocity(velocity);
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			try {
				out.writeUTF("Connect");
				out.writeUTF(server);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
		}
	}
}
