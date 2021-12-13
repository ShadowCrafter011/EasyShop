package org.shadowcrafter.easyshop.inventories;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.shadowcrafter.easyshop.EasyShop;
import org.shadowcrafter.easyshop.strings.Mess;
import org.shadowcrafter.easyshop.util.Heads;
import org.shadowcrafter.easyshop.util.PlayerHeads;

public class Inventories {
	
	public HashMap<String, Inventory> list;
	
	private int[] border6 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 26, 35, 44, 53, 53, 51, 50, 49, 48, 47, 46, 45, 36, 27, 18, 9};
	private int[] content6 = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
	
	private int[] border5 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 26, 35, 44, 43, 42, 41, 40, 39, 38, 37, 36, 27, 18, 9};
	
	public void createInventories() {
		Inventory main = Bukkit.createInventory(null, 9*3, Mess.PREFIX + "§3Main");
		Inventory personal = Bukkit.createInventory(null, 9*6, Mess.PREFIX + "§3Your shops");
		Inventory edit = Bukkit.createInventory(null, 9*6, Mess.PREFIX + "§3Edit shop");
		Inventory shop = Bukkit.createInventory(null, 9*5, Mess.PREFIX + "§3Your shop");
		Inventory buy = Bukkit.createInventory(null, 9*5, Mess.PREFIX + "§3Buy");
		
		ItemStack empty = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1).setName(" ").build();
		ItemStack gray = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setName(" ").build();
		ItemStack red_pane = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName(" ").build();
		
		fill(main, 0, main.getSize(), empty, false);
		fill(edit, 0, edit.getSize(), empty, false);
		fill(shop, 0, shop.getSize(), empty, false);
		fill(buy, 0, buy.getSize(), empty, false);
		fillSpecific(personal, border6, empty);
		fillSpecific(personal, content6, red_pane);
		fillSpecific(buy, border5, gray);
		fillSpecific(shop, border5, gray);
		
		shop.setItem(20, new ItemBuilder(Material.NETHER_STAR).setName("§2Settings").setLore(" ", "§aEdit your shops settings here", " ").build());
		shop.setItem(24, new ItemBuilder(Material.CHEST).setName("§2Inventory").setLore(" ", "§aAccess your shops inventory", "§ahere. You will be able", "§ato collect profits and", "§arestock your shop here", " ", "§2[Click to view]").build());
		shop.setItem(22, new ItemBuilder(Material.EMERALD).setName("§2Buy menu").setLore(" ", "§aView the buy inventory", "§a of your shop", " ", "§2[Click to view]").build());
		shop.setItem(4, new ItemBuilder(Material.REDSTONE_TORCH).setName("§2Tutorial").setLore(" ", "§aView the tutorial for §2EasyShop", " ", "§2[Click to view]").build());
		
		edit.setItem(4, new ItemBuilder(Material.CHEST).setName("§3Shop").setLore(" ", "§aHere you can edit", "§ayour shops settings", " ").build());
		
		edit.setItem(37, new ItemBuilder(Material.CHEST, 1).setName("§2Sell option 1").setLore(" ", "§6Amount: N/A", "§6Price: N/A", " ", "§aFirst option presented", "§ato the customer", " ", "§aHere you can set:", " -  §5Amount which will be sold", " -  §5Price for the amount above", " ", "§aLeave unedited or set both", "§aamount and price to -1", "§ato disable this sell option", " ", "§2[Click to edit]").build());
		edit.setItem(39, new ItemBuilder(Material.CHEST, 2).setName("§2Sell option 2").setLore(" ", "§6Amount: N/A", "§6Price: N/A", " ", "§aSecond option presented", "§ato the customer", " ", "§aHere you can set:", " -  §5Amount which will be sold", " -  §5Price for the amount above", " ", "§aLeave unedited or set both", "§aamount and price to -1", "§ato disable this sell option", "§2[Click to edit]").build());
		edit.setItem(41, new ItemBuilder(Material.CHEST, 3).setName("§2Sell option 3").setLore(" ", "§6Amount: N/A", "§6Price: N/A", " ", "§aThird option presented", "§ato the customer", " ", "§aHere you can set:", " -  §5Amount which will be sold", " -  §5Price for the amount above", " ", "§aLeave unedited or set both", "§aamount and price to -1", "§ato disable this sell option", " ", "§2[Click to edit]").build());
		edit.setItem(43, new ItemBuilder(Material.CHEST, 4).setName("§2Sell option 4").setLore(" ", "§6Amount: N/A", "§6Price: N/A", " ", "§aFourth option presented", "§ato the customer", " ", "§aHere you can set:", " -  §5Amount which will be sold", " -  §5Price for the amount above", " ", "§aLeave unedited or set both", "§aamount and price to -1", "§ato disable this sell option", " ", "§2[Click to edit]").build());
		
		edit.setItem(36, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName(" ").build());
		edit.setItem(38, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName(" ").build());
		edit.setItem(40, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName(" ").build());
		edit.setItem(42, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName(" ").build());
		edit.setItem(44, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName(" ").build());
		
		edit.setItem(19, new ItemBuilder(Material.DEAD_BUSH).setName("§2Selling: §6N/A").setLore(" ", "§aThe item you are selling", " ", "§2[Click to edit]").build());
		edit.setItem(25, new ItemBuilder(Material.DEAD_BUSH).setName("§2Getting: §6N/A").setLore(" ", "§aThe item you are getting", "§ain exchange", " ", "§2[Click to edit]").build());
		
		PlayerHeads heads = EasyShop.getPlugin().getPlayerHeads();
		
		//edit.setItem(0, new ItemBuilder(heads.list.get(Heads.DISCARD)).setName("§4Discard and exit").setLore(" ", "§4Reset changes made", "§4and exit menu").build());
		edit.setItem(8, new ItemBuilder(heads.list.get(Heads.SAVE)).setName("§2Save and exit").setLore(" ", "§aSave your changes", "§aand exit menu", " ", "§aEquivalent of pressing 'e'").build());
		
		edit.setItem(13, new ItemBuilder(heads.list.get(Heads.DOWN)).setName(" ").build());
		edit.setItem(22, new ItemBuilder(heads.list.get(Heads.DOWN)).setName(" ").build());
		edit.setItem(31, new ItemBuilder(heads.list.get(Heads.DOWN)).setName(" ").build());
		edit.setItem(23, new ItemBuilder(heads.list.get(Heads.LEFT)).setName(" ").build());
		edit.setItem(24, new ItemBuilder(heads.list.get(Heads.LEFT)).setName(" ").build());
		edit.setItem(20, new ItemBuilder(heads.list.get(Heads.RIGHT)).setName(" ").build());
		edit.setItem(21, new ItemBuilder(heads.list.get(Heads.RIGHT)).setName(" ").build());
		
		list = new HashMap<>();
		
		list.put(Invs.MAIN, main);		
		list.put(Invs.PERSONAL, personal);
		list.put(Invs.EDIT, edit);
		list.put(Invs.SHOP, shop);
		list.put(Invs.BUY, buy);
	}
	
	public int getEmptySlots(Inventory inv) {
		int i = 0;
		ItemStack[] cont = inv.getContents();
		
		for (ItemStack current : cont) {
			if (current != null && current.getType() != Material.AIR) {
				i++;
			}
		}
		
		return inv.getSize() - i;
	}
	
	public Inventory getPlayerInventoryWithoutArmor(Player p) {
		Inventory out = Bukkit.createInventory(null, 9*4);
		
		for (int i = 0; i < 9*4; i++) {
			out.setItem(i, p.getInventory().getItem(i));
		}
		return out;
	}
	
	private void fill(Inventory inv, int start, int end, ItemStack item, boolean count) {
		for (int i = start; i < end; i++) {
			if (count) {
				inv.setItem(i, new ItemBuilder(item).setAmount(i).build());
			}else {
				inv.setItem(i, new ItemBuilder(item).setAmount(1).build());
			}
		}
	}
	
	private void fillSpecific(Inventory inv, int[] slots, ItemStack item) {
		for (int slot : slots) {
			inv.setItem(slot, item);
		}
	}
	
	public Inventory createInvFromSection(ArrayList<ItemStack> items, String title) {
		Inventory out = Bukkit.createInventory(null, items.size(), title);
		
		for (int i = 0; i < items.size(); i++) {
			out.setItem(i, items.get(i));
		}
		
		return out;
	}

}
