package net.tribalmc.skyblockworldedit.commands;

import net.tribalmc.skyblockworldedit.utils.ConfigUtils;
import net.tribalmc.skyblockworldedit.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AssistCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
	{
		final Player player = (Player) sender;
		
		final int maxBlocks = ConfigUtils.getConfigString("group." + PlayerUtils.getPlayerRank(player)) != null ?
			Integer.parseInt(ConfigUtils.getConfigString("group." + PlayerUtils.getPlayerRank(player))) :
			Integer.parseInt(ConfigUtils.getConfigString("default-blocks-placeable"));
		
		sender.sendMessage(String.valueOf(maxBlocks));
		return false;
	}
}
