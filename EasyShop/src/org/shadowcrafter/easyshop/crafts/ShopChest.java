package org.shadowcrafter.easyshop.crafts;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.shadowcrafter.easyshop.inventories.ItemBuilder;

public class ShopChest {
	
	public ItemStack shopChest;
	
	public ShopChest() {
		shopChest = new ItemBuilder(Material.CHEST).setName("§3Shop").setLore(" ", "§a§oThis chest can be used", "§a§oto create interactive", "§a§oshops. Place it down", "§a§oand §2[Right click] §ait configure it", " ").build();
		
		ShapelessRecipe shopChestRec = new ShapelessRecipe(RecipeKeys.SHOP_CHEST_KEY, shopChest);
		
		shopChestRec.addIngredient(2, Material.CHEST);
		
		Bukkit.getServer().addRecipe(shopChestRec);
	}

}
