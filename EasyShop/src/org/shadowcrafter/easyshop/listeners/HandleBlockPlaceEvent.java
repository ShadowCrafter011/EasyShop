package org.shadowcrafter.easyshop.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.shadowcrafter.easyshop.EasyShop;
import org.shadowcrafter.easyshop.data.ShopData;
import org.shadowcrafter.easyshop.strings.Err;
import org.shadowcrafter.easyshop.strings.Mess;

public class HandleBlockPlaceEvent implements Listener{
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getBlock().getType() == Material.CHEST && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§3Shop")) {
			
			Chest chest = (Chest) e.getBlock().getBlockData();
			
			if (e.getBlock().getRelative(BlockFace.EAST).getType() == Material.CHEST || e.getBlock().getRelative(BlockFace.NORTH).getType() == Material.CHEST || e.getBlock().getRelative(BlockFace.WEST).getType() == Material.CHEST || e.getBlock().getRelative(BlockFace.SOUTH).getType() == Material.CHEST) {
				e.setCancelled(true);
				if (EasyShop.getPlugin().getShop(e.getBlock().getRelative(BlockFace.SOUTH).getLocation()) != null) {
					e.getPlayer().sendMessage(Err.CANT_PLACE_SHOP_NEXT_TO_SHOP);
					return;
				}
				if (EasyShop.getPlugin().getShop(e.getBlock().getRelative(BlockFace.NORTH).getLocation()) != null) {
					e.getPlayer().sendMessage(Err.CANT_PLACE_SHOP_NEXT_TO_SHOP);
					return;
				}
				if (EasyShop.getPlugin().getShop(e.getBlock().getRelative(BlockFace.EAST).getLocation()) != null) {
					e.getPlayer().sendMessage(Err.CANT_PLACE_SHOP_NEXT_TO_SHOP);
					return;
				}
				if (EasyShop.getPlugin().getShop(e.getBlock().getRelative(BlockFace.WEST).getLocation()) != null) {
					e.getPlayer().sendMessage(Err.CANT_PLACE_SHOP_NEXT_TO_SHOP);
					return;
				}
				e.getPlayer().sendMessage(Err.CANT_PLACE_SHOP_NEXT_TO_CHEST);
				return;
			}
			
			Location signLoc = e.getBlock().getRelative(chest.getFacing()).getLocation();
			
			signLoc.getBlock().setType(Material.DARK_OAK_WALL_SIGN);
			
			BlockState state = signLoc.getBlock().getState();
			WallSign sign = (WallSign) state.getBlockData();
			
			sign.setFacing(chest.getFacing());
			state.setBlockData(sign);
			
			Sign textSign = (Sign) state;
			textSign.setGlowingText(false);
			textSign.setLine(0, Mess.PREFIX);
			textSign.setLine(1, "§aSell: §6N/A");
			textSign.setLine(2, "§aBuy: §6N/A");
			textSign.setLine(3, "§2[Right Click]");
			
			state.update();
			
			new ShopData(e.getBlock().getLocation(), e.getPlayer().getUniqueId());
			
			e.getPlayer().sendMessage(Mess.PLACED_SHOP);
		}
		if (e.getBlock().getType() == Material.CHEST) {
			if (EasyShop.getPlugin().getShop(e.getBlock().getRelative(BlockFace.NORTH).getLocation()) != null) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(Err.CANT_PLACE_CHEST_NEXT_TO_SHOP);
			}
			if (EasyShop.getPlugin().getShop(e.getBlock().getRelative(BlockFace.SOUTH).getLocation()) != null) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(Err.CANT_PLACE_CHEST_NEXT_TO_SHOP);
			}
			if (EasyShop.getPlugin().getShop(e.getBlock().getRelative(BlockFace.EAST).getLocation()) != null) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(Err.CANT_PLACE_CHEST_NEXT_TO_SHOP);
			}
			if (EasyShop.getPlugin().getShop(e.getBlock().getRelative(BlockFace.WEST).getLocation()) != null) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(Err.CANT_PLACE_CHEST_NEXT_TO_SHOP);
			}
		}
	}

}
