package org.shadowcrafter.easyshop.runnables;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ReplaceItemAt extends BukkitRunnable{

	private Player p;
	private int slot;
	private ItemStack item;
	
	public ReplaceItemAt(Player p, int slot, ItemStack item) {
		this.slot = slot;
		this.item = item;
		this.p = p;
	}

	@Override
	public void run() {
		p.getInventory().setItem(slot, item);
	}
	
}
