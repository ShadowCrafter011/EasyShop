package org.shadowcrafter.easyshop.util;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

public class PlayerHeads {
	
	public HashMap<Heads, ItemStack> list;
	
	public PlayerHeads() {
		list = new HashMap<>();
		
		list.put(Heads.DOWN, new StringToHead().getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTllOTM4MTgxZDhjOTZiNGY1OGY2MzMyZDNkZDIzM2VjNWZiODUxYjVhODQwNDM4ZWFjZGJiNjE5YTNmNWYifX19"));
		list.put(Heads.UP, new StringToHead().getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGZlYjM5ZDcxZWY4ZTZhNDI2NDY1OTMzOTNhNTc1M2NlMjZhMWJlZTI3YTBjYThhMzJjYjYzN2IxZmZhZSJ9fX0="));
		list.put(Heads.RIGHT, new StringToHead().getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjgxMzYzM2JkNjAxNTJkOWRmNTRiM2Q5ZDU3M2E4YmMzNjU0OGI3MmRjMWEzMGZiNGNiOWVjMjU2ZDY4YWUifX19"));
		list.put(Heads.LEFT, new StringToHead().getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzE2MmQ0MmY0ZGJhMzU0ODhmNGY2NmQ2NzM2MzViZmM1NjE5YmRkNTEzZDAyYjRjYzc0ZjA1ZWM4ZTk1NiJ9fX0="));
		list.put(Heads.SAVE, new StringToHead().getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2UyYTUzMGY0MjcyNmZhN2EzMWVmYWI4ZTQzZGFkZWUxODg5MzdjZjgyNGFmODhlYThlNGM5M2E0OWM1NzI5NCJ9fX0="));
		list.put(Heads.DISCARD, new StringToHead().getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTljZGI5YWYzOGNmNDFkYWE1M2JjOGNkYTc2NjVjNTA5NjMyZDE0ZTY3OGYwZjE5ZjI2M2Y0NmU1NDFkOGEzMCJ9fX0="));

	}

}
