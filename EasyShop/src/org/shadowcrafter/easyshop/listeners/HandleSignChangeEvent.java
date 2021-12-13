package org.shadowcrafter.easyshop.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.shadowcrafter.easyshop.strings.Err;

public class HandleSignChangeEvent implements Listener{
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if (e.getLine(0).equals("[EasyShop]")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Err.CANT_PUT_THAT_ON_SIGN);
			e.getBlock().breakNaturally();
		}
	}

}
