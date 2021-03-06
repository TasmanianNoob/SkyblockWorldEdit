package net.tribalmc.skyblockworldedit.commands;

import com.sk89q.worldedit.bukkit.selections.Selection;
import net.tribalmc.skyblockworldedit.SkyblockWorldEdit;
import net.tribalmc.skyblockworldedit.utils.*;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class SetCommand implements CommandExecutor
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
		
		if (ConfigUtils.getConfigStringList("disabled-worlds").contains(player.getWorld().getName()))
		{
			player.sendMessage(Utils.colorize(MessagesUtils.getMessage("disabled-world")));
			return true;
		}
		
		final Selection selection = SkyblockWorldEdit.getWorldEditPlugin().getSelection(player);
		
		if (selection == null)
		{
			player.sendMessage(Utils.colorize(MessagesUtils.getMessage("no-region")));
			return true;
		}
		
		if (!SkyblockWorldEdit.getSkyBlockHandler().isPlayersIsland(selection, player))
		{
			player.sendMessage(Utils.colorize(MessagesUtils.getMessage("not-on-island")));
			return true;
		}
		
		if (args.length == 0)
		{
			player.sendMessage(Utils.colorize(MessagesUtils.getMessage("set-usage")));
			return true;
		}
		
		final Material block;
		
		try
		{
			block = Material.valueOf(args[0].toUpperCase());
			
			if (!block.isBlock()) throw new IllegalArgumentException();
		}
		catch (final IllegalArgumentException exception)
		{
			player.sendMessage(Utils.colorize(MessagesUtils.getMessage("invalid-block")));
			return true;
		}
		
		if (ConfigUtils.getConfigStringList("place-blacklist").contains(block.name()))
		{
			player.sendMessage(Utils.colorize(MessagesUtils.getMessage("disabled-block")));
			return true;
		}
		
		final int maxBlocks = ConfigUtils.getConfigString("group." + PlayerUtils.getPlayerRank(player)) != null ?
			Integer.parseInt(ConfigUtils.getConfigString("group." + PlayerUtils.getPlayerRank(player))) :
			Integer.parseInt(ConfigUtils.getConfigString("default-blocks-placeable"));
		
		final int area = selection.getArea();
		
		if (area > maxBlocks)
		{
			player.sendMessage(Utils.colorize(MessagesUtils.getMessage("too-large-region")));
			return true;
		}
		
		if (!player.getInventory().containsAtLeast(new ItemStack(block), area))
		{
			player.sendMessage(Utils.colorize(MessagesUtils.getMessage("not-enough-blocks")));
			return true;
		}
		
		final double cost = (ConfigUtils.getConfigString("unique-block-price." + block) == null ?
			Double.parseDouble(ConfigUtils.getConfigString("default-price-per-block")) :
			Double.parseDouble(ConfigUtils.getConfigString("unique-block-price." + block))) * area;
		
		if (Boolean.parseBoolean(ConfigUtils.getConfigString("use-economy")))
		{
			if (!SkyblockWorldEdit.getEconomyHandler().has(player, cost))
			{
				player.sendMessage(Utils.colorize(MessagesUtils.getMessage("not-enough-money")));
				return true;
			}
			
			SkyblockWorldEdit.getEconomyHandler().withdrawPlayer(player, cost);
			player.sendMessage(Utils.colorize(MessagesUtils.getMessage("enough-money")).replace("%cost%", String.valueOf(cost)));
		}
		else
		{
			player.sendMessage(Utils.colorize(MessagesUtils.getMessage("eco-disabled")));
		}
		
		final PlayerWorldEdit playerWorldEdit = new PlayerWorldEdit(cost, new ItemStack(block, area));
		
		player.getInventory().removeItem(new ItemStack(block, area));
		SkyblockWorldEdit.getWorldEditHandler().setStructure(selection, block, maxBlocks, player.getUniqueId());
		
		PlayerUtils.addPlayerEditSession(player.getUniqueId(), playerWorldEdit);
		return true;
	}
}
