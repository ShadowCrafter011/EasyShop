package org.shadowcrafter.easyshop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shadowcrafter.easyshop.EasyShop;
import org.shadowcrafter.easyshop.data.ShopData;
import org.shadowcrafter.easyshop.strings.Err;
import org.shadowcrafter.easyshop.strings.Mess;
import org.shadowcrafter.easyshop.util.EditType;
import org.shadowcrafter.easyshop.util.EditingPlayers;

public class SetPrice implements CommandExecutor{

	private EditingPlayers editingPlayers = EasyShop.getPlugin().getEditingPlayers();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			if (editingPlayers.getEditingPlayers().contains(p.getUniqueId())) {
				if (editingPlayers.getEditType(p.getUniqueId()) == EditType.PRICE_AMOUNT_1 || editingPlayers.getEditType(p.getUniqueId()) == EditType.PRICE_AMOUNT_2 || editingPlayers.getEditType(p.getUniqueId()) == EditType.PRICE_AMOUNT_3 || editingPlayers.getEditType(p.getUniqueId()) == EditType.PRICE_AMOUNT_4) {
					if (args.length == 2) {
						
						int price = -1;
						int amount = -1;
						
						try {
							amount = Integer.parseInt(args[0]);
							price = Integer.parseInt(args[1]);
						}catch (NumberFormatException e) {
							p.sendMessage(Err.SETPRICE_USAGE);
							return true;
						}
						
						amount = amount < 0 ? -1 : amount;
						price = price < 0 ? -1 : price;
						
						if (amount > 512) {
							p.sendMessage(Err.MAX_AMOUNT);
							editingPlayers.removeEditingPlayer(p.getUniqueId());
							return true;
						}
						if (price > 64) {
							p.sendMessage(Err.MAX_PRICE);
							editingPlayers.removeEditingPlayer(p.getUniqueId());
							return true;
						}
						
						String priceS = Integer.toString(price);
						String amountS = Integer.toString(amount);
						
						ShopData shop = editingPlayers.getShop(p.getUniqueId());
						
						shop.updateBuySellName();
						
						String sell = shop.getSellName();
						String buy = shop.getBuyName();
						
						switch (editingPlayers.getEditType(p.getUniqueId())) {
						
						case PRICE_AMOUNT_1:
							shop.setAmount(0, amount);
							shop.setPrice(0, price);
							p.sendMessage(Mess.SET_PRICE_AND_AMOUNT.replace("&", amountS).replace("+", sell).replace("%", priceS).replace("ç", buy));
							break;
							
						case PRICE_AMOUNT_2:
							shop.setAmount(1, amount);
							shop.setPrice(1, price);
							p.sendMessage(Mess.SET_PRICE_AND_AMOUNT.replace("1", "2").replace("&", amountS).replace("+", sell).replace("%", priceS).replace("ç", buy));
							break;
							
						case PRICE_AMOUNT_3:
							shop.setAmount(2, amount);
							shop.setPrice(2, price);
							p.sendMessage(Mess.SET_PRICE_AND_AMOUNT.replace("1", "3").replace("&", amountS).replace("+", sell).replace("%", priceS).replace("ç", buy));
							break;
							
						case PRICE_AMOUNT_4:
							shop.setAmount(3, amount);
							shop.setPrice(3, price);
							p.sendMessage(Mess.SET_PRICE_AND_AMOUNT.replace("1", "4").replace("&", amountS).replace("+", sell).replace("%", priceS).replace("ç", buy));
							break;
						
						default: break;
						}
						shop.HashShop();
						
						p.openInventory(shop.getDataInventory());
						editingPlayers.addEditingPlayer(p.getUniqueId(), shop.getLocation(), EditType.EDIT_INVENTORY);
					}else
						p.sendMessage(Err.SETPRICE_USAGE);
				}else
					p.sendMessage(Err.NOT_EDITING_SHOP);
			}else
				p.sendMessage(Err.NOT_EDITING_SHOP);
		}else
			sender.sendMessage(Err.ONLY_FOR_PLAYERS);
		return true;
	}

}
