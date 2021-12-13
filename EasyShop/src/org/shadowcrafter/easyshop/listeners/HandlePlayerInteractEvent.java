package org.shadowcrafter.easyshop.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.shadowcrafter.easyshop.EasyShop;
import org.shadowcrafter.easyshop.data.ShopData;
import org.shadowcrafter.easyshop.inventories.Invs;
import org.shadowcrafter.easyshop.strings.Err;
import org.shadowcrafter.easyshop.strings.Mess;
import org.shadowcrafter.easyshop.util.EditType;
import org.shadowcrafter.easyshop.util.EditingPlayers;

public class HandlePlayerInteractEvent implements Listener{
	
	EditingPlayers editingPlayers = EasyShop.getPlugin().getEditingPlayers();
	
	@EventHandler
	public void onPlayerAction(PlayerInteractEvent e) {
		
		if (editingPlayers.getEditingPlayers().contains(e.getPlayer().getUniqueId())) {
			if (editingPlayers.getEditType(e.getPlayer().getUniqueId()) == EditType.BUY || editingPlayers.getEditType(e.getPlayer().getUniqueId()) == EditType.SELL) {
				ShopData shop = editingPlayers.getShop(e.getPlayer().getUniqueId());
				
				if (e.getItem() != null && e.getItem().getType() != Material.AIR && e.getItem().getMaxStackSize() == 1) {
					e.getPlayer().sendMessage(Err.CANT_SELL_UNSTACKABLE);
					
					editingPlayers.removeEditingPlayer(e.getPlayer().getUniqueId());
					return;
				}
				
				switch (editingPlayers.getEditType(e.getPlayer().getUniqueId())) {
				case BUY:
					e.setCancelled(true);
					if (e.getItem() != null) {
						shop.setBuying(new ItemStack(e.getItem().getType()));
						
						shop.HashShop();
						
						shop.updateBuySellName();
						
						Chest chest = (Chest) shop.getLocation().getBlock().getState().getBlockData();
						BlockState state = shop.getLocation().getBlock().getRelative(chest.getFacing()).getState();
						
						WallSign sign = (WallSign) state.getBlockData();
						state.setBlockData(sign);
						Sign textSign = (Sign) state;
						
						String buy = "§aBuy: " + shop.getBuyName().substring(0, Math.min(shop.getBuyName().length(), 13));
						
						textSign.setLine(2, buy);
						
						state.update();
					}else {
						shop.setBuying(new ItemStack(Material.AIR));
						shop.HashShop();
					}
					break;
					
				case SELL:					
					e.setCancelled(true);
					if (e.getItem() != null) {
						shop.setSelling(new ItemStack(e.getItem().getType()));
						
						shop.HashShop();
						
						shop.updateBuySellName();
						
						Chest chest = (Chest) shop.getLocation().getBlock().getState().getBlockData();
						BlockState state = shop.getLocation().getBlock().getRelative(chest.getFacing()).getState();
						
						WallSign sign = (WallSign) state.getBlockData();
						state.setBlockData(sign);
						Sign textSign = (Sign) state;
						
						String sell = "§aSell: " + shop.getSellName().substring(0, Math.min(shop.getSellName().length(), 13));
						
						textSign.setLine(1, sell);
						
						state.update();
					}else {
						shop.setSelling(new ItemStack(Material.AIR));
						shop.HashShop();
					}
					break;
				
				default:
					return;
				}
				shop.updateBuySellName();
				
				e.getPlayer().sendMessage(Mess.SET_ITEMS.replace("%", shop.getBuyName()).replace("&", shop.getSellName()));
				
				e.getPlayer().openInventory(shop.getDataInventory());
				
				editingPlayers.addEditingPlayer(e.getPlayer().getUniqueId(), shop.getLocation(), EditType.EDIT_INVENTORY);
				return;
			}
		}
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			switch (e.getClickedBlock().getType()) {
			case CHEST:
				ShopData shop = EasyShop.getPlugin().getShop(e.getClickedBlock().getLocation());
				
				if (shop != null && shop.getOwner().compareTo(e.getPlayer().getUniqueId()) == 0) {
					e.setCancelled(true);
					e.getPlayer().openInventory(EasyShop.getPlugin().getInvs().list.get(Invs.SHOP));

					editingPlayers.addEditingPlayer(e.getPlayer().getUniqueId(), shop.getLocation(), EditType.SHOP_MAIN);
				}else if(shop != null) {
					e.setCancelled(true);
					
					Inventory buyInv = shop.getBuyInventory();
					if (buyInv != null) {
						e.getPlayer().openInventory(buyInv);
						
						editingPlayers.addEditingPlayer(e.getPlayer().getUniqueId(), shop.getLocation(), EditType.BUY_INVENTORY);
					}else
						e.getPlayer().sendMessage(Err.SHOP_NOT_READY);
				}
				break;
				
			case DARK_OAK_WALL_SIGN:				
				WallSign wallSign = (WallSign) e.getClickedBlock().getBlockData();
				Sign sign = (Sign) e.getClickedBlock().getState();

				if (sign.getLine(0).equals(Mess.PREFIX)) {
					ShopData shop1 = EasyShop.getPlugin().getShop(e.getClickedBlock().getRelative(wallSign.getFacing().getOppositeFace()).getLocation());
					
					if (shop1 != null && shop1.getOwner().compareTo(e.getPlayer().getUniqueId()) == 0) {
						e.setCancelled(true);
						e.getPlayer().openInventory(EasyShop.getPlugin().getInvs().list.get(Invs.SHOP));

						editingPlayers.addEditingPlayer(e.getPlayer().getUniqueId(), shop1.getLocation(), EditType.SHOP_MAIN);
					}else if (shop1 != null) {
						e.setCancelled(true);

						Inventory buyInv = shop1.getBuyInventory();
						if (buyInv != null) {
							e.getPlayer().openInventory(buyInv);
							
							editingPlayers.addEditingPlayer(e.getPlayer().getUniqueId(), shop1.getLocation(), EditType.BUY_INVENTORY);
						}else
							e.getPlayer().sendMessage(Err.SHOP_NOT_READY);
					}
				}
				
				break;
				
			default:
				break;
			}
		}
	}

}
