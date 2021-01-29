package net.tribalmc.skyblockworldedit.utils;

import net.tribalmc.skyblockworldedit.file.ConfigHandler;
import net.tribalmc.skyblockworldedit.file.MessagesHandler;
import org.bukkit.ChatColor;

public final class Utils
{
	public static String colorize(final String string)
	{
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public static void loadConfigurations()
	{
		ConfigHandler.createFile();
		MessagesHandler.createFile();
		
		ConfigUtils.reloadConfig();
		MessagesUtils.reloadMessages();
	}
}
