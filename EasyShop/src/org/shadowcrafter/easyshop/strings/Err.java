package org.shadowcrafter.easyshop.strings;

public class Err {
	
	public static final String PREFIX = "§2[EasyShop] ";
	
	public static final String NOT_ENOUGH_PERMS = PREFIX + "§cSorry, but you don't have enough permissions to do that",
			ONLY_FOR_PLAYERS = PREFIX + "§cSorry, but only players may use this command";
	
	public static final String SHOP_USAGE = PREFIX + "§cPlease use §5/shop <uuid>",
			KNOWLEDGE_USAGE = PREFIX + "§cPlease use §5/giveknowledgebook",
			SETPRICE_USAGE = PREFIX + "§cPlease use §5/setprice <amount> <price>";
	
	public static final String NOT_ENOUGH_SPACE_IN_INV = PREFIX + "§cYou don't have enough space in your inventory for that. Empty some space and run the command again";

	public static final String CAN_ONLY_BREAK_YOUR_SHOP = PREFIX + "§cSorry, but you can only break your own shops",
			BREAK_CHEST = PREFIX + "§cPlease break the chest is you'd like to close this shop",
			NOT_YOUR_SHOP = PREFIX + "§cThis is not your shop!",
			INVALID_UUID = PREFIX + "§cCould not read this uuid. Make sure it's correct",
			CANT_PLACE_SHOP_NEXT_TO_CHEST = PREFIX + "§cSorry, but you can't place a shop directly next to a chest",
			CANT_PLACE_CHEST_NEXT_TO_SHOP = PREFIX + "§cSorry, but you can't place a chest directly next to a shop",
			CANT_PLACE_SHOP_NEXT_TO_SHOP = PREFIX + "§cSorry, but you can't place a shop directly next to a shop";
	
	public static final String CANT_PUT_THAT_ON_SIGN = PREFIX + "§cSorry, but you cant put " + PREFIX + " §con the first line of a Sign";
	
	public static final String SHOP_NOT_READY = PREFIX + "§cSorry, but this shop has not been configured yet. If you are the shop's owner §2[Right click] §cit to set it up";
	
	public static final String NOT_EDITING_SHOP = PREFIX + "§cYou are not editing a shop right now";
	
	public static final String MAX_AMOUNT = PREFIX + "§cSorry, but you can't sell more than 512 items in one buy option",
			MAX_PRICE = PREFIX + "§cSorry, but you can't charge more than 64 items in a buy option";
	
	public static final String SOMETHING_WENT_WRONG = PREFIX + "§cSomething went wront. Please contact a server administrator";
	
	public static final String NOT_ENOUGH_SPACE_TO_BUY = PREFIX + "§cYou don't have enough space in your inventory to buy that. Empty some space and try again",
			NOT_ENOUGH_MONEY_TO_BUY = PREFIX + "§cYou don't have enough &s §cto buy that",
			SHOP_OUT_OF_STOCK = PREFIX + "§cThis buy option is currently out of stock. If the others are too come back later",
			NOT_ENOUGH_SPACE_IN_SHOP = PREFIX + "§cThere is not enough space in the shop for you to pay. Please contact the shop owner to fix it";

	public static String REFUND = PREFIX + "§cPlease don't fill the shops inventory completely. This could lead to the buyer not being able to pay up, which will lead to the deal getting canceled. Your shop will then get disabled until you create more space. For more information type §5/shop",
			REFUNDED_ITEMS = PREFIX + "§cItems detected in your shop that are not being sold nor baught. Cleared out these items automatically and refunded them to your inventory";

	public static String CANT_SELL_UNSTACKABLE = PREFIX + "§cSorry, but you can't sell unstackable items yet. Doing so anyway would currently result in your items getting bugged and unusable. I'm already working on a fix";
}
