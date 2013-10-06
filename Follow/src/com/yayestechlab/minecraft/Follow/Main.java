package com.yayestechlab.minecraft.Follow;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	public static Player p1;
	public static Player p2;
	
	public void onEnable(){
		getCommand("follow").setExecutor(new FollowCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new FollowListener(), this);
	}
}
