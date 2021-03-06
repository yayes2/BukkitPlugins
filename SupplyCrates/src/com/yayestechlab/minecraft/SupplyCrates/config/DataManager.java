package com.yayestechlab.minecraft.SupplyCrates.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.server.v1_6_R3.NBTBase;
import net.minecraft.server.v1_6_R3.NBTTagCompound;
import net.minecraft.server.v1_6_R3.NBTTagList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_6_R3.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.v1_6_R3.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class DataManager {

	public  File crates;
	private  FileConfiguration cratesConfig;
	
	public  void reloadCrateConfig(Plugin p) {
	    if (crates == null) {
	    	crates = new File(p.getDataFolder(), "crates.yml");
	    }
	    
	    cratesConfig = YamlConfiguration.loadConfiguration(crates);
	}
	
	public  FileConfiguration getCrateConfig(Plugin p) {
		if (cratesConfig == null) {
			reloadCrateConfig(p);
		}
		return cratesConfig;
	}
	
	public  void saveCrateConfig(Plugin p, FileConfiguration cfg) {
	    if (cratesConfig == null || crates == null) {
	    	return;
	    }
	    try {
	        cfg.save(crates);
	    } catch (IOException ex) {
	        p.getLogger().log(Level.SEVERE, "Could not save config to " + crates, ex);
	    }
	}
	
	public  void saveDefaultCrateConfig(Plugin p) {
	    if (crates == null) {
	        crates = new File(p.getDataFolder(), "crates.yml");
	    }
	    if (!crates.exists()) {
	         p.saveResource("crates.yml", false);
	     }
	}
	
	 public String toBase64(Inventory inventory) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutput = new DataOutputStream(outputStream);
		NBTTagList itemList = new NBTTagList();
		// Save every element in the list
		for (int i = 0; i < inventory.getSize(); i++) {
		NBTTagCompound outputObject = new NBTTagCompound();
		CraftItemStack craft = getCraftVersion(inventory.getItem(i));
		// Convert the item stack to a NBT compound
		if (craft != null)
		CraftItemStack.asNMSCopy(craft).save(outputObject);
		itemList.add(outputObject);
		}

		// Now save the list
		NBTBase.a(itemList, dataOutput);
		
		// Serialize that array
		return Base64Coder.encodeLines(outputStream.toByteArray());
		}
		public Inventory fromBase64(String data) {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			NBTTagList itemList = (NBTTagList) NBTBase.b(new DataInputStream(inputStream), 0);
			Inventory inventory = new CraftInventoryCustom(null, itemList.size());
			
			for (int i = 0; i < itemList.size(); i++) {
				NBTTagCompound inputObject = (NBTTagCompound) itemList.get(i);
				if (!inputObject.isEmpty()) {
					inventory.setItem(i, CraftItemStack.asCraftMirror(net.minecraft.server.v1_6_R3.ItemStack.createStack(inputObject)));
				}
			}
			// Serialize that array
			return inventory;
		}
		public static Inventory getInventoryFromArray(ItemStack[] items) {
			CraftInventoryCustom custom = new CraftInventoryCustom(null, items.length);
			for (int i = 0; i < items.length; i++) {
				if (items[i] != null) {
					custom.setItem(i, items[i]);
				}
			}
			return custom;
		}
		private static CraftItemStack getCraftVersion(ItemStack stack) {
			if (stack instanceof CraftItemStack)
				return (CraftItemStack) stack;
			else if (stack != null)
				return CraftItemStack.asCraftCopy(stack);
			else
				return null;
		}
}
