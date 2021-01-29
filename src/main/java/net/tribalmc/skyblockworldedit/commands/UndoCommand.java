package net.tribalmc.skyblockworldedit.commands;

import net.tribalmc.skyblockworldedit.SkyblockWorldEdit;
import net.tribalmc.skyblockworldedit.utils.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class UndoCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage(Utils.colorize(MessagesUtils.getMessage("not-player")));
			return true;
		}
		
		final Player player = (Player) sender;
		
		if (!PlayerUtils.playerHasPermission(player, ConfigUtils.getConfigString("permission"))) return true;
		
		if (!PlayerUtils.hasPlayerEditSession(player.getUniqueId()))
		{
			sender.sendMessage(Utils.colorize(MessagesUtils.getMessage("no-undo-available")));
			return true;
		}
		
		final PlayerWorldEdit wEditSession = PlayerUtils.getPlayerEditSession(player.getUniqueId());
		
		if (Boolean.parseBoolean(ConfigUtils.getConfigString("use-economy")))
		{
			final double refund = wEditSession.cost * (Double.parseDouble(ConfigUtils.getConfigString("undo-refund")) / 100.0);
			
			SkyblockWorldEdit.getEconomyHandler().depositPlayer(player, refund);
			
			player.sendMessage(Utils.colorize(MessagesUtils.getMessage("undo-refund"))
				.replace("%refund%", String.valueOf(refund))
				.replace("%refund_percentage%", ConfigUtils.getConfigString("undo-refund")));
		}
		else
		{
			player.sendMessage(Utils.colorize(MessagesUtils.getMessage("undo-eco-disabled")));
		}
		
		player.getInventory().addItem(wEditSession.item);
		PlayerUtils.removePlayerEditSession(player.getUniqueId());
		
		SkyblockWorldEdit.getWorldEditHandler().undoWorldEdit(player);
		return true;
	}
}
