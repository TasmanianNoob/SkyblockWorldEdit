package net.tribalmc.skyblockworldedit.hooks;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.entity.Player;

public final class SuperiorSkyblock<Island> implements ISkyBlock<Island>
{
	@Override
	public boolean isSelectionWithinIslandLocation(final Selection selection, final Island island)
	{
		final com.bgsoftware.superiorskyblock.api.island.Island island1 = (com.bgsoftware.superiorskyblock.api.island.Island) island;
		
		return island1.isInsideRange(selection.getMinimumPoint()) && island1.isInsideRange(selection.getMaximumPoint());
	}
	
	@Override
	public boolean isPlayersIsland(final Selection selection, final Player player)
	{
		final com.bgsoftware.superiorskyblock.api.island.Island island = SuperiorSkyblockAPI.getGrid().getIslandAt(player.getLocation());
		
		if (island != null &&
			island.getIslandMembers(true).contains(SuperiorSkyblockAPI.getPlayer(player.getUniqueId())))
		{
			return isSelectionWithinIslandLocation(selection, (Island) island);
		}
		return false;
	}
}
