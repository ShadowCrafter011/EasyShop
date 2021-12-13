package org.shadowcrafter.easyshop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shadowcrafter.easyshop.strings.Mess;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class Shop implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			TextComponent message = new TextComponent(Mess.TUTORIAL);
			message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/watch?v=qH0N_KslmVU"));
			message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder(Mess.TUTORIAL_HOVER).create())));
			p.spigot().sendMessage(message);
		}else {
			//Update link here aswell
			sender.sendMessage(Mess.TUTORIAL_CONSOLE);
		}
		
		return true;		
	}

}
