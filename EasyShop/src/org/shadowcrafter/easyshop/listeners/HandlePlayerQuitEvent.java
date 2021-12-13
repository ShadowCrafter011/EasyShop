package org.shadowcrafter.easyshop.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.shadowcrafter.easyshop.EasyShop;

public class HandlePlayerQuitEvent implements Listener{
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		EasyShop.getPlugin().getEditingPlayers().removeEditingPlayer(e.getPlayer().getUniqueId());
	}

}
