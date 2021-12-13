package org.shadowcrafter.easyshop.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.shadowcrafter.easyshop.crafts.RecipeKeys;
import org.shadowcrafter.easyshop.strings.Err;
import org.shadowcrafter.easyshop.strings.Mess;

public class KnowledgeBook implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				
				if (p.getInventory().firstEmpty() == -1) {
					p.sendMessage(Err.NOT_ENOUGH_SPACE_IN_INV);
					return true;
				}
				
				p.getInventory().addItem(getBook());
				p.sendMessage(Mess.KNOWLEDGEBOOK_OBTAINED);
				
			}else if (args.length == 1 && args[0].equals("nomsg")) {
				
				p.getInventory().setItem(8, getBook());
				
			}else
				p.sendMessage(Err.KNOWLEDGE_USAGE);
		}else
			sender.sendMessage(Err.ONLY_FOR_PLAYERS);
		return true;
	}
	
	private ItemStack getBook() {
		ItemStack book = new ItemStack(Material.KNOWLEDGE_BOOK);
		KnowledgeBookMeta meta = (KnowledgeBookMeta) book.getItemMeta();
		
		meta.addRecipe(RecipeKeys.SHOP_CHEST_KEY);
		
		book.setItemMeta(meta);
		
		return book;
	}

}
