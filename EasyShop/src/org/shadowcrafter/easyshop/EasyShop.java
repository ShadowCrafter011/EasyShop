package org.shadowcrafter.easyshop;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.shadowcrafter.easyshop.commands.KnowledgeBook;
import org.shadowcrafter.easyshop.commands.SetPrice;
import org.shadowcrafter.easyshop.crafts.ShopChest;
import org.shadowcrafter.easyshop.data.ShopData;
import org.shadowcrafter.easyshop.inventories.Inventories;
import org.shadowcrafter.easyshop.listeners.HandleBlockBreakEvent;
import org.shadowcrafter.easyshop.listeners.HandleBlockPlaceEvent;
import org.shadowcrafter.easyshop.listeners.HandleInventoryClickEvent;
import org.shadowcrafter.easyshop.listeners.HandlePlayerCloseInventoryEvent;
import org.shadowcrafter.easyshop.listeners.HandlePlayerInteractEvent;
import org.shadowcrafter.easyshop.listeners.HandlePlayerJoinEvent;
import org.shadowcrafter.easyshop.listeners.HandlePlayerQuitEvent;
import org.shadowcrafter.easyshop.listeners.HandlePrepareAnvilEvent;
import org.shadowcrafter.easyshop.listeners.HandleSignChangeEvent;
import org.shadowcrafter.easyshop.util.EditingPlayers;
import org.shadowcrafter.easyshop.util.PlayerHeads;

public class EasyShop extends JavaPlugin{

	private static EasyShop plugin;
	private Inventories invs;
	private EditingPlayers editingPlayers;
	private PlayerHeads playerHeads;
	private HashMap<Location, ShopData> shops;
	private ShopChest shopChest;
	
	@Override
	public void onEnable() {
		plugin = this;
		playerHeads = new PlayerHeads();
		editingPlayers = new EditingPlayers();
		shops = loadShops();
		invs = new Inventories();
		invs.createInventories();
		
		shopChest = new ShopChest();
		
		PluginManager pl = Bukkit.getPluginManager();
		
		pl.registerEvents(new HandlePlayerJoinEvent(), plugin);
		pl.registerEvents(new HandleBlockBreakEvent(), plugin);
		pl.registerEvents(new HandlePrepareAnvilEvent(), plugin);
		pl.registerEvents(new HandleBlockPlaceEvent(), plugin);
		pl.registerEvents(new HandleSignChangeEvent(), plugin);
		pl.registerEvents(new HandlePlayerInteractEvent(), plugin);
		pl.registerEvents(new HandlePlayerCloseInventoryEvent(), plugin);
		pl.registerEvents(new HandlePlayerQuitEvent(), plugin);
		pl.registerEvents(new HandleInventoryClickEvent(), plugin);
		
		//getCommand("shop").setExecutor(new Shop());
		getCommand("giveknowledgebook").setExecutor(new KnowledgeBook());
		getCommand("setprice").setExecutor(new SetPrice());
		
		if (!getConfig().contains("shops")) getConfig().createSection("shops");
		if (!getConfig().contains("players")) getConfig().createSection("players");
		saveConfig();
	}
	
	public ItemStack getShopChest() {
		return shopChest.shopChest;
	}
	
	public void onDisable() {
		for (Entry<Location, ShopData> shop : shops.entrySet()) {
			shop.getValue().SaveShopData();
		}
	}
	
	private HashMap<Location, ShopData> loadShops(){
		HashMap<Location, ShopData> output = new HashMap<>();
		FileConfiguration config = getConfig();
		
		if (!config.contains("shops")) return output;
		
		for (String uuid : config.getConfigurationSection("shops").getKeys(false)) {
			ShopData shop = new ShopData(UUID.fromString(uuid));
			
			if (shop.isValid) output.put(shop.getLocation(), shop);
		}
		
		return output;
	}
	
	public ShopData getShop(Location loc) {
		if (shops.containsKey(loc)) {
			return shops.get(loc);
		}else
			return null;
	}
	
	public ShopData getShop(UUID uuid) {
		for (ShopData shop : shops.values()) {
			if (shop.getUUID().compareTo(uuid) == 0) return shop;
		}
		return null;
	}
	
	public boolean removeShop(Location loc) {
		try {
			shops.remove(loc);
		}catch (NullPointerException e) {
			return false;
		}
		return true;
	}
	
	public boolean addShop(ShopData shop) {
		if (shop.isValid) {
			shops.put(shop.getLocation(), shop);
			return true;
		}
		return false;
	}
	
	public static EasyShop getPlugin() {
		return plugin;
	}
	
	public Inventories getInvs() {
		return invs;
	}
	
	public EditingPlayers getEditingPlayers() {
		return editingPlayers;
	}
	
	public PlayerHeads getPlayerHeads() {
		return playerHeads;
	}
	
}
