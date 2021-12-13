package org.shadowcrafter.easyshop.listeners;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.shadowcrafter.easyshop.EasyShop;
import org.shadowcrafter.easyshop.crafts.RecipeKeys;

public class HandlePlayerJoinEvent implements Listener{
	
	FileConfiguration config = EasyShop.getPlugin().getConfig();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		UUID playerUUID = e.getPlayer().getUniqueId();
		
		config.set("players." + playerUUID.toString() + ".name", e.getPlayer().getName());
		config.set("players." + playerUUID.toString() + ".lastplaced", null);
		EasyShop.getPlugin().saveConfig();
		
		if (!e.getPlayer().hasDiscoveredRecipe(RecipeKeys.SHOP_CHEST_KEY)) {
			
			if (e.getPlayer().getInventory().firstEmpty() != -1) {
				e.getPlayer().performCommand("giveknowledgebook");
			}else {/*
				TextComponent message = new TextComponent(Mess.NOT_ENOUGH_SPACE_CLICKABLE);
				message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/giveknowledgebook"));
				message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder(Mess.CLICKABLE_KNOWLEDGE_HOVER).create())));
				e.getPlayer().spigot().sendMessage(message);*/
			}
		}
	}

}
