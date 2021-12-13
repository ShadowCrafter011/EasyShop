package org.shadowcrafter.easyshop.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.shadowcrafter.easyshop.EasyShop;
import org.shadowcrafter.easyshop.data.ShopData;
import org.shadowcrafter.easyshop.strings.Err;
import org.shadowcrafter.easyshop.util.EditType;
import org.shadowcrafter.easyshop.util.EditingPlayers;

public class HandlePlayerCloseInventoryEvent implements Listener{
	
	EditingPlayers editingPlayers = EasyShop.getPlugin().getEditingPlayers();
	
	@EventHandler
	public void onPlayerInventoryClose(InventoryCloseEvent e) {
		if (editingPlayers.getEditingPlayers().contains(e.getPlayer().getUniqueId()) && editingPlayers.getEditType(e.getPlayer().getUniqueId()) == EditType.SHOP_INVENTORY) {
			
			//Remove one item if shop is full
			if (e.getInventory().firstEmpty() == -1) {
				ItemStack refund = e.getInventory().getItem(e.getInventory().getSize() - 1);
				e.getInventory().setItem(e.getInventory().getSize() - 1, new ItemStack(Material.AIR));
				if (e.getPlayer().getInventory().firstEmpty() != - 1) {
					e.getPlayer().getInventory().addItem(refund);
					e.getPlayer().sendMessage(Err.REFUND);
					e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.8f, 0.8f);
				}else {
					e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(), refund);
					e.getPlayer().sendMessage(Err.REFUND);
					e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.8f, 0.8f);
				}
			}
			
			ShopData shop = editingPlayers.getShop(e.getPlayer().getUniqueId());
			
			//Removing items shop is not selling or buying
			boolean removedItems = false;
			for (int i = 0; i < e.getInventory().getSize(); i++) {
				if (e.getInventory().getItem(i) != null && e.getInventory().getItem(i).getType() != Material.AIR && shop.getSelling() != null && shop.getSelling().getType() != Material.AIR && shop.getBuying() != null && shop.getBuying().getType() != Material.AIR) {
					if (e.getInventory().getItem(i).getType() != shop.getSelling().getType() && e.getInventory().getItem(i).getType() != shop.getBuying().getType()) {
						if (e.getPlayer().getInventory().firstEmpty() != -1) {
							e.getPlayer().getInventory().addItem(e.getInventory().getItem(i));
							e.getInventory().setItem(i, new ItemStack(Material.AIR));
							removedItems = true;
						}else {
							e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(), e.getInventory().getItem(i));
							e.getInventory().setItem(i, new ItemStack(Material.AIR));
							removedItems = true;
						}
					}
				}
			}
			if (removedItems) {
				e.getPlayer().sendMessage(Err.REFUNDED_ITEMS);
				e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.8f, 0.8f);
			}
			
			editingPlayers.getShop(e.getPlayer().getUniqueId()).setInventory(e.getInventory());
			editingPlayers.getShop(e.getPlayer().getUniqueId()).reorganiseInv();
			
			editingPlayers.getShop(e.getPlayer().getUniqueId()).HashShop();
			
			editingPlayers.removeEditingPlayer(e.getPlayer().getUniqueId());
		}else if (editingPlayers.getEditingPlayers().contains(e.getPlayer().getUniqueId()) && editingPlayers.getEditType(e.getPlayer().getUniqueId()) == EditType.EDIT_INVENTORY) {
			editingPlayers.getShop(e.getPlayer().getUniqueId()).HashShop();
			
			editingPlayers.removeEditingPlayer(e.getPlayer().getUniqueId());
		}else if(editingPlayers.getEditingPlayers().contains(e.getPlayer().getUniqueId()) && editingPlayers.getEditType(e.getPlayer().getUniqueId()) == EditType.BUY_INVENTORY) {
			editingPlayers.removeEditingPlayer(e.getPlayer().getUniqueId());
		}else if (editingPlayers.getEditingPlayers().contains(e.getPlayer().getUniqueId()) && editingPlayers.getEditType(e.getPlayer().getUniqueId()) == EditType.SHOP_MAIN && !editingPlayers.isGUISwitching(e.getPlayer().getUniqueId())) {
			editingPlayers.removeEditingPlayer(e.getPlayer().getUniqueId());
		}else if (editingPlayers.getEditingPlayers().contains(e.getPlayer().getUniqueId()) && editingPlayers.getEditType(e.getPlayer().getUniqueId()) == EditType.SHOP_MAIN && editingPlayers.isGUISwitching(e.getPlayer().getUniqueId())) {
			editingPlayers.removeGUISwitcher(e.getPlayer().getUniqueId());
		}
	}

}
