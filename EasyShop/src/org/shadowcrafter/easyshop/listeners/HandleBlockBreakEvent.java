package org.shadowcrafter.easyshop.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.shadowcrafter.easyshop.EasyShop;
import org.shadowcrafter.easyshop.data.ShopData;
import org.shadowcrafter.easyshop.strings.Err;
import org.shadowcrafter.easyshop.strings.Mess;

public class HandleBlockBreakEvent implements Listener{
	
	private FileConfiguration config = EasyShop.getPlugin().getConfig();
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		ShopData shop = EasyShop.getPlugin().getShop(e.getBlock().getLocation());
		Player p = e.getPlayer();
		
		if (shop != null && shop.getOwner().compareTo(p.getUniqueId()) == 0) {
			e.setDropItems(false);
			
			//Removing sign
			Chest chest = (Chest) e.getBlock().getBlockData();
			e.getBlock().getRelative(chest.getFacing()).setType(Material.AIR);
			
			//dropping shop content
			for (ItemStack item : shop.getInventory().getContents()) {
				if (p.getInventory().firstEmpty() != -1) {
					if (item != null) {
						p.getInventory().addItem(item);
					}
				}else {
					if (item != null) {
						p.getWorld().dropItemNaturally(p.getLocation(), item);
					}
				}
			}
			
			if (p.getInventory().firstEmpty() != -1) {
				p.getInventory().addItem(EasyShop.getPlugin().getShopChest());
			}else {
				p.getWorld().dropItemNaturally(p.getLocation(), EasyShop.getPlugin().getShopChest());
			}
			
			p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.8f, 0.8f);
			
			p.sendMessage(Mess.BROKE_SHOP);
			
			EasyShop.getPlugin().removeShop(shop.getLocation());
			
			config.set(shop.getPath(), null);
			EasyShop.getPlugin().saveConfig();
		}else if (shop != null) {
			e.setCancelled(true);
			p.sendMessage(Err.CAN_ONLY_BREAK_YOUR_SHOP);
		}
		
		if (e.getBlock().getType() == Material.DARK_OAK_WALL_SIGN) {
			Sign sign = (Sign) e.getBlock().getState();
			
			if (sign.getLine(0).equals(Mess.PREFIX)) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(Err.BREAK_CHEST);
			}
		}
	}

}
