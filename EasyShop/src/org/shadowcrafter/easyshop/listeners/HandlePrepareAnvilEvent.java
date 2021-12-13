package org.shadowcrafter.easyshop.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.shadowcrafter.easyshop.EasyShop;
import org.shadowcrafter.easyshop.inventories.ItemBuilder;

public class HandlePrepareAnvilEvent implements Listener{

	@EventHandler
	public void onPrepareAnvilEvent(PrepareAnvilEvent e) {
		boolean isShop = false;
		if (e.getInventory().getItem(0) == null) return;
		for (int i = 1; i < 65; i++) {
			if (e.getInventory().getItem(0).equals(new ItemBuilder(EasyShop.getPlugin().getShopChest()).setAmount(i).build())) {
				isShop = true;
			}
		}
		if (isShop) e.setResult(null);
	}
	
}
