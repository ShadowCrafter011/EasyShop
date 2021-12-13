package org.shadowcrafter.easyshop.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.shadowcrafter.easyshop.EasyShop;
import org.shadowcrafter.easyshop.data.ShopData;
import org.shadowcrafter.easyshop.strings.Err;
import org.shadowcrafter.easyshop.strings.Mess;
import org.shadowcrafter.easyshop.util.EditType;
import org.shadowcrafter.easyshop.util.EditingPlayers;

public class HandleInventoryClickEvent implements Listener{

	private EditingPlayers editingPlayers = EasyShop.getPlugin().getEditingPlayers();
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if (editingPlayers.getEditingPlayers().contains(p.getUniqueId()) && editingPlayers.getEditType(p.getUniqueId()) == EditType.EDIT_INVENTORY) {
			e.setCancelled(true);
			
			switch (e.getSlot()) {
			
			case 8:
				editingPlayers.getShop(p.getUniqueId()).HashShop();
				
				editingPlayers.removeEditingPlayer(p.getUniqueId());
				
				p.closeInventory();
				break;
				
			case 19:
				editingPlayers.addEditingPlayer(p.getUniqueId(), editingPlayers.getShop(p.getUniqueId()).getLocation(), EditType.SELL);
				
				p.closeInventory();
				p.sendMessage(Mess.SET_SELLING_ITEM);
				break;
			case 25:
				editingPlayers.addEditingPlayer(p.getUniqueId(), editingPlayers.getShop(p.getUniqueId()).getLocation(), EditType.BUY);
				
				p.closeInventory();
				p.sendMessage(Mess.SET_BUYING_ITEM);
				break;
				
			case 37:
				editingPlayers.addEditingPlayer(p.getUniqueId(), editingPlayers.getShop(p.getUniqueId()).getLocation(), EditType.PRICE_AMOUNT_1);
			
				p.closeInventory();
				p.sendMessage(Mess.HOW_TO_SET_AMOUNT_AND_PRICE);
				break;
				
			case 39:
				editingPlayers.addEditingPlayer(p.getUniqueId(), editingPlayers.getShop(p.getUniqueId()).getLocation(), EditType.PRICE_AMOUNT_2);
			
				p.closeInventory();
				p.sendMessage(Mess.HOW_TO_SET_AMOUNT_AND_PRICE);
				break;
				
			case 41:
				editingPlayers.addEditingPlayer(p.getUniqueId(), editingPlayers.getShop(p.getUniqueId()).getLocation(), EditType.PRICE_AMOUNT_3);
			
				p.closeInventory();
				p.sendMessage(Mess.HOW_TO_SET_AMOUNT_AND_PRICE);
				break;
				
			case 43:
				editingPlayers.addEditingPlayer(p.getUniqueId(), editingPlayers.getShop(p.getUniqueId()).getLocation(), EditType.PRICE_AMOUNT_4);
			
				p.closeInventory();
				p.sendMessage(Mess.HOW_TO_SET_AMOUNT_AND_PRICE);
				break;
			default:
				break;
			}
		}else if (editingPlayers.getEditingPlayers().contains(p.getUniqueId()) && editingPlayers.getEditType(p.getUniqueId()) == EditType.BUY_INVENTORY) {
			e.setCancelled(true);
			
			if (e.getCurrentItem() == null) return;
			if (e.getCurrentItem().getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) return;
			if (e.getClickedInventory() == p.getInventory()) return;
			
			int num = -1;
			String number = e.getCurrentItem().getItemMeta().getDisplayName().replace("§2Buy option ", "");
			num = Integer.parseInt(number) - 1;
			
			if (num == -1) {
				p.sendMessage(Err.SOMETHING_WENT_WRONG);
				return;
			}
			
			ShopData shop = editingPlayers.getShop(p.getUniqueId());

			if (!((float) EasyShop.getPlugin().getInvs().getEmptySlots(EasyShop.getPlugin().getInvs().getPlayerInventoryWithoutArmor(p)) >= (float) shop.getAmount(num) / 64f)) {
				p.sendMessage(Err.NOT_ENOUGH_SPACE_TO_BUY);
				return;
			}
			
			int result = shop.sellItem(num, p);
			if (result == 1) {
				p.sendMessage(Err.SHOP_OUT_OF_STOCK);
			}else if (result == 2) {
				p.sendMessage(Err.NOT_ENOUGH_MONEY_TO_BUY.replace("&", shop.getBuyName()));
			}else if (result == 0) {
				p.sendMessage(Mess.BAUGHT_ITEM.replace("&", shop.getSellName()).replace("%", Integer.toString(shop.getAmount(num))).replace("ç", shop.getBuyName()).replace("*", Integer.toString(shop.getPrice(num))));
			}else if (result == 3) {
				p.sendMessage(Err.NOT_ENOUGH_SPACE_IN_SHOP);
			}
			
			shop.HashShop();
				
		}else if (editingPlayers.getEditingPlayers().contains(p.getUniqueId()) && editingPlayers.getEditType(p.getUniqueId()) == EditType.SHOP_MAIN) {
			e.setCancelled(true);
			
			if (e.getCurrentItem() == null) return;
			
			if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
				editingPlayers.addGUISwitcher(p.getUniqueId());
				
				p.openInventory(editingPlayers.getShop(p.getUniqueId()).getDataInventory());
				
				editingPlayers.addEditingPlayer(p.getUniqueId(), editingPlayers.getShop(p.getUniqueId()).getLocation(), EditType.EDIT_INVENTORY);
			}else if (e.getCurrentItem().getType() == Material.CHEST) {
				editingPlayers.addGUISwitcher(p.getUniqueId());
				
				p.openInventory(editingPlayers.getShop(p.getUniqueId()).getInventory());
				
				editingPlayers.addEditingPlayer(p.getUniqueId(), editingPlayers.getShop(p.getUniqueId()).getLocation(), EditType.SHOP_INVENTORY);
			}else if (e.getCurrentItem().getType() == Material.EMERALD) {
				if (editingPlayers.getShop(p.getUniqueId()).getBuyInventory() == null) {
					p.sendMessage(Err.SHOP_NOT_READY);
					return;
				}
				editingPlayers.addGUISwitcher(p.getUniqueId());
				
				p.openInventory(editingPlayers.getShop(p.getUniqueId()).getBuyInventory());
				
				editingPlayers.addEditingPlayer(p.getUniqueId(), editingPlayers.getShop(p.getUniqueId()).getLocation(), EditType.BUY_INVENTORY);
			}else if (e.getCurrentItem().getType() == Material.REDSTONE_TORCH) {
				p.closeInventory();
				
				p.performCommand("shop");
				
				editingPlayers.removeEditingPlayer(p.getUniqueId());
			}
		}
	}
	
}
