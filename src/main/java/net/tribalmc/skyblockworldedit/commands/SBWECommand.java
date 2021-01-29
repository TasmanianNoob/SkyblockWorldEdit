package net.tribalmc.skyblockworldedit.commands;

import net.tribalmc.skyblockworldedit.utils.MessagesUtils;
import net.tribalmc.skyblockworldedit.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class SBWECommand implements CommandExecutor
{
	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
	{
		if (args.length == 0 || !args[0].equalsIgnoreCase("reload"))
		{
			MessagesUtils.getHelpMessage().forEach(str -> sender.sendMessage(Utils.colorize(str)));
			return true;
		}
		
		if (!sender.hasPermission("skyblockworldedit.admin"))
		{
			sender.sendMessage(Utils.colorize(MessagesUtils.getMessage("no-permission")));
			return true;
		}
		
		Utils.loadConfigurations();
		sender.sendMessage(Utils.colorize(MessagesUtils.getMessage("files-reloaded")));
		return true;
	}
}
