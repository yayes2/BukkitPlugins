package com.yayestechlab.minecraft.Follow;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FollowCommandExecutor implements CommandExecutor {

	Main plugin;
	
	public FollowCommandExecutor(Main main) {
		plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("follow")){
			Main.p1 = (Player) sender;
			Main.p2 = plugin.getServer().getPlayer(args[0]);
			Location newloc = ((Player) sender).getLocation();
			newloc.setY(((Player) sender).getLocation().getY() + 3);
			Main.p1.teleport(newloc);
			return true;
		} 
		return false; 
	}



}
