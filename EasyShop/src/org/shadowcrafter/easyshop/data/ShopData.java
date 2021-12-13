package org.shadowcrafter.easyshop.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.shadowcrafter.easyshop.EasyShop;
import org.shadowcrafter.easyshop.inventories.Inventories;
import org.shadowcrafter.easyshop.inventories.Invs;
import org.shadowcrafter.easyshop.inventories.ItemBuilder;
import org.shadowcrafter.easyshop.strings.Mess;

public class ShopData {
	
	private FileConfiguration config = EasyShop.getPlugin().getConfig();
	
	public boolean isValid = true;
	
	private Location location;
	private UUID uuid;
	private UUID owner;
	private Inventory inventory;
	private int[] prices = new int[4];
	private int[] amounts = new int[4];
	private ItemStack selling;
	private ItemStack buying;
	private String path;
	private String sellName;
	private String buyName;

	public ShopData(Location location, UUID owner) {
		this.uuid = UUID.randomUUID();
		
		this.location = location;
		this.owner = owner;
		this.inventory = Bukkit.createInventory(null, 9*6);
		this.selling = new ItemStack(Material.AIR);
		this.buying = new ItemStack(Material.AIR);
		this.path = "shops." + uuid.toString();
		
		for (int i = 0; i < 4; i++) {
			prices[i] = -1;
			amounts[i] = -1;
		}
		
		updateBuySellName();
		
		EasyShop.getPlugin().addShop(this);
	}
	
	public void reorganiseInv() {
		int selling = getItemTypeAmount(inventory, this.selling.getType());
		int buying = getItemTypeAmount(inventory, this.buying.getType());
		
		Inventory cashedInv = Bukkit.createInventory(null, 9*6);
		
		int sellingLeftover = selling % 64;
		int sellingStacks = (selling - sellingLeftover) / 64;
		
		int buyingLeftover = buying % 64;
		int buyingStacks = (buying - buyingLeftover) / 64;
		
		for (int i = 0; i < sellingStacks; i++) {
			cashedInv.addItem(new ItemBuilder(this.selling.getType(), 64).build());
		}
		if (sellingLeftover > 0) cashedInv.addItem(new ItemBuilder(this.selling.getType(), sellingLeftover).build());
		
		for (int i = 0; i < buyingStacks; i++) {
			cashedInv.addItem(new ItemBuilder(this.buying.getType(), 64).build());
		}
		if (buyingLeftover > 0) cashedInv.addItem(new ItemBuilder(this.buying.getType(), buyingLeftover).build());
		
		int cashedSelling = getItemTypeAmount(cashedInv, this.selling.getType());
		int cashedBuying = getItemTypeAmount(cashedInv, this.buying.getType());
		
		if (cashedSelling + cashedBuying == getItemAmount(inventory)) {
			inventory = cashedInv;
		}
	}
	
	public int getItemTypeAmount(Inventory inv, Material type) {
		int out = 0;
		for (int i = 0; i < inv.getSize(); i++) {
			if (inv.getItem(i) != null && inv.getItem(i).getType() == type) {
				out += inv.getItem(i).getAmount();
			}
		}
		return out;
	}
	
	public int getItemAmount(Inventory inv) {
		int out = 0;
		for (ItemStack item : inv.getContents()) {
			if (item != null) out += item.getAmount();
		}
		return out;
	}
	
	public Inventory getBuyInventory() {
		Inventories invs = EasyShop.getPlugin().getInvs();
		
		Inventory output = Bukkit.createInventory(null, invs.list.get(Invs.BUY).getSize(), Mess.PREFIX + "§3Buy");
		output.setContents(invs.list.get(Invs.BUY).getContents());
		
		int filledBuyOptions = 0;
		ItemStack[] buyoptions = new ItemStack[4];
		
		updateBuySellName();
		
		for (int i = 0; i < 4; i++) {
			if (prices[i] != -1 && amounts[i] != -1 && selling.getType() != Material.AIR && buying.getType() != Material.AIR) {
				if (amounts[i] < 65) {
					buyoptions[filledBuyOptions] = new ItemBuilder(selling.getType(), amounts[i]).build();
					buyoptions[filledBuyOptions] = new ItemBuilder(buyoptions[filledBuyOptions]).setName("§2Buy option " + Integer.toString(i + 1)).setLore(" ", "§aYou GET§a: " + sellName + " x" + amounts[i], " ", "§aYou GIVE§a: " + buyName + " x" + prices[i], " ", "§2[Click to buy]").build();
					filledBuyOptions++;
				}else {
					buyoptions[filledBuyOptions] = new ItemBuilder(selling.getType(), 64).build();
					buyoptions[filledBuyOptions] = new ItemBuilder(buyoptions[filledBuyOptions]).setName("§2Buy option " + Integer.toString(i + 1)).setLore(" ", "§aYou GET§a: " + sellName + " x" + amounts[i], " ", "§aYou GIVE§a: " + buyName + " x" + prices[i], " ", "§2[Click to buy]").build();
					filledBuyOptions++;
				}
			}
		}
		
		int sellOptions = 0;
		for (ItemStack item : buyoptions) {
			if (item != null) sellOptions++;
		}
		if (sellOptions == 0) return null;
		
		switch (sellOptions) {
		case 1:
			output.setItem(22, buyoptions[0]);
			break;
			
		case 2:
			output.setItem(20, buyoptions[0]);
			output.setItem(24, buyoptions[1]);
			break;
			
		case 3:
			output.setItem(20, buyoptions[0]);
			output.setItem(22, buyoptions[1]);
			output.setItem(24, buyoptions[2]);
			break;
			
		case 4:
			output.setItem(19, buyoptions[0]);
			output.setItem(21, buyoptions[1]);
			output.setItem(23, buyoptions[2]);
			output.setItem(25, buyoptions[3]);
			break;
		
		default:
			break;
		}
		
		return output;
	}
	
	public void updateBuySellName() {
		String sell = "§6N/A";
		String buy = "§6N/A";
		
		if (!getSelling().equals(new ItemStack(Material.AIR))) {
			char[] chars = getSelling().getType().toString().toLowerCase().toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			
			sell = String.valueOf(chars);
		}
		if (!getBuying().equals(new ItemStack(Material.AIR))) {
			char[] chars = getBuying().getType().toString().toLowerCase().toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			
			buy = String.valueOf(chars);
		}
		
		this.sellName = "§6" + sell;
		this.buyName = "§6" + buy;
	}
	
	public String getSellName() {
		return sellName;
	}

	public String getBuyName() {
		return buyName;
	}

	public void HashShop() {
		EasyShop.getPlugin().removeShop(this.location);
		
		EasyShop.getPlugin().addShop(this);
	}
	
	public int getAmount(int index) {
		return amounts[index];
	}
	
	public void setAmount(int index, int amount) {
		amounts[index] = amount;
	}
	
	public int getPrice(int index) {
		return prices[index];
	}
	
	public void setPrice(int index, int amount) {
		prices[index] = amount;
	}
	
	public boolean isValid() {
		return isValid;
	}

	public Location getLocation() {
		return location;
	}

	public UUID getUUID() {
		return uuid;
	}

	public UUID getOwner() {
		return owner;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public int sellItem(int index, Player p) {
		//testing if there enough space for payment
		Inventory cashedInv = Bukkit.createInventory(null, 9*6);
		cashedInv.setContents(inventory.getContents());
		
		int originalAmount = 0;
		for (ItemStack item : cashedInv.getContents()) {
			if (item != null && item.getType() == buying.getType()) originalAmount += item.getAmount();
		}

		for (int i = 0; i < cashedInv.getSize(); i++) {
			if (cashedInv.getItem(i) != null && cashedInv.getItem(i).getType() == buying.getType()) {
				cashedInv.setItem(i, new ItemStack(Material.AIR));
			}
		}
		
		int testLeftover = (originalAmount + prices[index]) % 64;
		int testStacks = (originalAmount + prices[index] - testLeftover) / 64;
		
		for (int i = 0; i < cashedInv.getSize(); i++) {
			if (cashedInv.getItem(i) == null || cashedInv.getItem(i).getType() == Material.AIR) {
				if (testStacks > 0) {
					cashedInv.setItem(i, new ItemBuilder(buying.getType(), 64).build());
					testStacks--;
				}
			}
		}
		if (testLeftover > 0) {
			cashedInv.addItem(new ItemBuilder(buying.getType(), testLeftover).build());
		}
		
		int valueAfterTest = 0;
		for (ItemStack item : cashedInv.getContents()) {
			if (item != null && item.getType() == buying.getType()) valueAfterTest += item.getAmount();
		}
		if (valueAfterTest != originalAmount + prices[index]) {			
			return 3;
		}
		
		//testing if there is enough stock
		int amountLeft = 0;
		for (ItemStack content : inventory.getContents()) {
			if (content != null && content.getType() == selling.getType()) {
				amountLeft += content.getAmount();
			}
		}
		
		int rest;
		if (amountLeft >= amounts[index]) {
			rest = amountLeft - amounts[index];
		}else { 
			return 1;
		}
		
		for (int i = 0; i < inventory.getSize(); i++) {
			if (inventory.getItem(i) != null && inventory.getItem(i).getType() == selling.getType()) {
				inventory.setItem(i, new ItemStack(Material.AIR));
			}
		}
		
		int leftover = rest % 64;
		int stacks = (rest - leftover) / 64;
		
		ItemStack Stacks = new ItemBuilder(selling.getType(), 64).build();
		ItemStack Leftover = new ItemBuilder(selling.getType(), leftover).build();
		
		for (int i = 0; i < inventory.getSize(); i++) {
			if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
				if (stacks > 0) {
					inventory.setItem(i, Stacks);
					stacks--;
				}
			}
		}
		inventory.setItem(inventory.firstEmpty(), Leftover);
		
		//testing if player has enough payment
		int playerChargeAmountLeft = 0;
		ArrayList<Integer> chargeSlots = new ArrayList<>();
		Inventory playerInv = p.getInventory();
		for (int i = 0; i < playerInv.getSize(); i++) {
			if (playerInv.getItem(i) != null && playerInv.getItem(i).getType() == buying.getType()) {
				playerChargeAmountLeft += playerInv.getItem(i).getAmount();
				chargeSlots.add(i);
			}
		}
		
		int playerRest = 0;
		if (playerChargeAmountLeft >= prices[index]) {
			playerRest = playerChargeAmountLeft - prices[index];
		}else {
			return 2;
		}
		
		//Completing payment
		for (int i = 0; i < playerInv.getSize(); i++) {
			if (playerInv.getItem(i) != null && playerInv.getItem(i).getType() == buying.getType()) {
				playerInv.setItem(i, new ItemStack(Material.AIR));
			}
		}
		
		int playerLeftover = playerRest % 64;
		int playerStacks = (playerRest - playerLeftover) / 64;
		
		ItemStack PlayerLeftover = new ItemBuilder(buying.getType(), playerLeftover).build();
		ItemStack PlayerStacks = new ItemBuilder(buying.getType(), 64).build();
		
		for (int i : chargeSlots) {
			if (playerInv.getItem(i) == null || playerInv.getItem(i).getType() == Material.AIR) {
				if (playerStacks > 0) {
					playerInv.setItem(i, PlayerStacks);
					playerStacks--;
				}else if (playerLeftover > 0) {
					playerInv.setItem(i, PlayerLeftover);
					playerLeftover = 0;
				}
			}
		}
		
		
		inventory.addItem(new ItemBuilder(buying.getType(), prices[index]).build());
		playerInv.addItem(new ItemBuilder(selling.getType(), amounts[index]).build());
		
		reorganiseInv();
		
		return 0;
	}

	public ItemStack getSelling() {
		return selling;
	}

	public void setSelling(ItemStack selling) {
		this.selling = selling;
	}

	public ItemStack getBuying() {
		return buying;
	}

	public void setBuying(ItemStack buying) {
		this.buying = buying;
	}

	public String getPath() {
		return path;
	}

	public Inventory getDataInventory() {
		Inventories invs = EasyShop.getPlugin().getInvs();
		
		Inventory output = Bukkit.createInventory(null, invs.list.get(Invs.EDIT).getSize(), Mess.PREFIX + "§3Edit shop");
		
		output.setContents(invs.list.get(Invs.EDIT).getContents());
		
		String sell = "N/A";
		String buy = "N/A";
		
		if (!getSelling().equals(new ItemStack(Material.AIR))) {
			char[] chars = getSelling().getType().toString().toLowerCase().toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			
			sell = String.valueOf(chars);
		}
		if (!getBuying().equals(new ItemStack(Material.AIR))) {
			char[] chars = getBuying().getType().toString().toLowerCase().toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			
			buy = String.valueOf(chars);
		}
		
		if (selling != null) {
			output.getItem(19).setType(selling.getType());
			output.setItem(19, new ItemBuilder(output.getItem(19)).setName("§2Selling: §6" + sell).build());
		}
		if (buying != null) {
			output.getItem(25).setType(buying.getType());
			output.setItem(25, new ItemBuilder(output.getItem(25)).setName("§2Getting: §6" + buy).build());
		}
		
		for (int i = 0; i < 4; i++) {
			int itemPos = 37 + 2 * i;
			ItemStack item = output.getItem(itemPos);
			
			if (amounts[i] != -1) output.setItem(itemPos, new ItemBuilder(item).setLoreLine("§6Amount: " + amounts[i], 1).build());
			if (prices[i] != -1) output.setItem(itemPos, new ItemBuilder(item).setLoreLine("§6Price: " + prices[i], 2).build());
		}
		
		return output;
	}
	
	public void printShopData() {
		Bukkit.broadcastMessage("loc: " + location.getX() + ", " + location.getY() + ", " + location.getZ() + ", " );
		Bukkit.broadcastMessage("shop UUID: " + uuid.toString());
		Bukkit.broadcastMessage("owner UUID: " + owner.toString());
		Bukkit.broadcastMessage("prices: " + prices[0] + ", " + prices[1] + ", " + prices[2] + ", " + prices[3] + ", ");
		Bukkit.broadcastMessage("amounts: " + amounts[0] + ", " + amounts[1] + ", " + amounts[2] + ", " + amounts[3] + ", ");
		Bukkit.broadcastMessage("selling: " + selling.toString());
		Bukkit.broadcastMessage("buying: " + buying.toString());
		Bukkit.broadcastMessage("path: " + path);
	}
	
	public ShopData(UUID uuid) {	
		if (!config.contains("shops." + uuid)) {
			isValid = false;
			return;
		}
		
		ConfigurationSection shopSection = config.getConfigurationSection("shops." + uuid);
		this.uuid = uuid;
		
		getShopData(shopSection);
	}
	
	private void getShopData(ConfigurationSection section) {
		location = section.getLocation(".location");
		owner = UUID.fromString(section.getString(".owner"));
		selling = section.getItemStack(".selling");
		buying = section.getItemStack(".buying");
		path = section.getCurrentPath();
		
		inventory = createInventoryFromList(section.getList(".inventory.list"), "§2Shop");
		
		try {
			for (int i = 0; i < 4; i++) {
				prices[i] = Integer.parseInt(section.getString(".prices").split("&")[i]);
				amounts[i] = Integer.parseInt(section.getString(".amounts").split("&")[i]);
			}
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public void SaveShopData() {
		if (!isValid) return;
		
		ConfigurationSection shopSection = null;
		
		if (config.contains("shops." + uuid)) {
			shopSection = config.getConfigurationSection("shops." + uuid);
		}else {
			shopSection = config.createSection("shops." + uuid);
		}
			
		
		shopSection.set(".location", location);
		shopSection.set(".owner", owner.toString());
		shopSection.set(".selling", selling);
		shopSection.set(".buying", buying);
		
		ArrayList<ItemStack> inventoryContents = new ArrayList<>();
		for (int i = 0; i < 9*6; i++) {
			if (inventory.getItem(i) == null) {
				inventoryContents.add(new ItemStack(Material.AIR));
			}else {
				inventoryContents.add(inventory.getItem(i));
			}
		}
		shopSection.set(".inventory.list", inventoryContents);
		
		String pricesStr = Integer.toString(prices[0]);
		String amountsStr = Integer.toString(amounts[0]);
		
		for (int i = 1; i < 4; i++) {
			pricesStr = pricesStr + "&" + prices[i];
			amountsStr = amountsStr + "&" + amounts[i];
		}
		
		shopSection.set(".prices", pricesStr);
		shopSection.set(".amounts", amountsStr);
		
		EasyShop.getPlugin().saveConfig();
	}
	
	@SuppressWarnings("unchecked")
	private Inventory createInventoryFromList(List<?> list, String title) {
		ArrayList<ItemStack> itemList;
		try {
			itemList = (ArrayList<ItemStack>) list;
		}catch (Exception e) {
			return null;
		}
		
		Inventory output = Bukkit.createInventory(null, itemList.size(), title);
		
		for (int i = 0; i < itemList.size(); i++) {
			output.setItem(i, itemList.get(i));
		}
		
		return output;
	}

}
