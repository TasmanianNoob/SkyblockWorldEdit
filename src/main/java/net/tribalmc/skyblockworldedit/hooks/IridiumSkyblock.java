package net.tribalmc.skyblockworldedit.hooks;

import com.iridium.iridiumskyblock.managers.IslandManager;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.entity.Player;

public final class IridiumSkyblock<Island> implements ISkyBlock<Island>
{
	@Override
	public boolean isSelectionWithinIslandLocation(final Selection selection, final Island island)
	{
		final com.iridium.iridiumskyblock.Island island1 = (com.iridium.iridiumskyblock.Island) island;
		
		return island1.isInIsland(selection.getMinimumPoint()) && island1.isInIsland(selection.getMaximumPoint());
	}
	
	@Override
	public boolean isPlayersIsland(final Selection selection, final Player player)
	{
		final com.iridium.iridiumskyblock.Island island = IslandManager.getIslandViaLocation(player.getLocation());
		
		if (island != null &&
			island.members.contains(player.getUniqueId().toString()))
		{
			return isSelectionWithinIslandLocation(selection, (Island) island);
		}
		return false;
	}
}
