package com.yayestechlab.minecraft.jumppads.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.yayestechlab.minecraft.jumppads.JumpPads;
import com.yayestechlab.minecraft.jumppads.config.DataManager;

public class JumpPadListener implements Listener{
	JumpPads plugin;
	
	public JumpPadListener(JumpPads p){
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
			if (Math.floor(p.getLocation().getY()) != p.getLocation().getY()){
				while(Math.floor(p.getLocation().getY()) != p.getLocation().getY()){
					try {
						Thread.currentThread().sleep(1L);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			p.setVelocity(velocity);
		}
	}
}
