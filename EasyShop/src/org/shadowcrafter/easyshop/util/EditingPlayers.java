package org.shadowcrafter.easyshop.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.shadowcrafter.easyshop.EasyShop;
import org.shadowcrafter.easyshop.data.ShopData;

public class EditingPlayers {
	
	private HashMap<UUID, EditType> editingPlayersEditType;
	private HashMap<UUID, Location> editingPlayersShop;
	
	private ArrayList<UUID> guiSwitch;
	
	public EditingPlayers() {
		editingPlayersEditType = new HashMap<>();
		editingPlayersShop = new HashMap<>();
		guiSwitch = new ArrayList<>();
	}
	
	public void addGUISwitcher(UUID uuid) {
		guiSwitch.add(uuid);
	}
	
	public boolean isGUISwitching(UUID uuid) {
		return guiSwitch.contains(uuid);
	}
	
	public void removeGUISwitcher(UUID uuid) {
		guiSwitch.remove(uuid);
	}
	
	public ArrayList<UUID> getEditingPlayers(){
		ArrayList<UUID> output = new ArrayList<>();
		
		for (UUID current : editingPlayersEditType.keySet()) {
			output.add(current);
		}
		
		return output;
	}
	
	public ShopData getShop(UUID uuid) {
		try {
			return EasyShop.getPlugin().getShop(editingPlayersShop.get(uuid));
		}catch (NullPointerException e) {
			return null;
		}
	}
	
	public EditType getEditType(UUID uuid) {
		return editingPlayersEditType.get(uuid);
	}
	
	public void removeEditingPlayer(UUID uuid) {
		editingPlayersEditType.remove(uuid);
		editingPlayersShop.remove(uuid);
	}
	
	public void addEditingPlayer(UUID uuid, Location loc ,EditType type) {
		editingPlayersEditType.remove(uuid);
		editingPlayersShop.remove(uuid);
		
		editingPlayersEditType.put(uuid, type);
		editingPlayersShop.put(uuid, loc);
	}
}
