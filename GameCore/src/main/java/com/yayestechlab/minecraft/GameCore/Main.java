package com.yayestechlab.minecraft.GameCore;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class Main extends JavaPlugin implements PluginMessageListener {
	private final HashMap<Player, GamePlayer> gameplayers = new HashMap<Player, GamePlayer>();
	private final HashMap<GamePlayer, Player> players = new HashMap<GamePlayer, Player>();
	private final HashMap<String, Game> games = new HashMap<String, Game>();
	
	public GamePlayer getGamePlayer(Player p) {
		if (gameplayers.containsKey(p)) {
			return gameplayers.get(p);
		} else {
			GamePlayer gp = new GamePlayer();
			gameplayers.put(p, gp);
			players.put(gp, p);
			return gp;
		}
	}
	
	public Player getPlayer(GamePlayer gp){
		return players.get(gp);
	}
	
	public HashMap<String, Game> getGames(){
		return games;
	}
	
	public void register(Game game) {
		games.put(game.getName(), game);
	}
	
	@Override
	public void onEnable() {
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
	}
	
	@SuppressWarnings("unused")
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}

		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
		String indata = "";
		try{
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);
			 
			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			indata = msgin.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] data = indata.split(" ");
		games.get(data[1]).playerJoin(games.get(data[1]).getArenas().get(data[2]), Bukkit.getPlayer(data[0]));
	}
}