package net.tribalmc.skyblockworldedit.hooks;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.songoda.skyblock.SkyBlock;
import com.songoda.skyblock.api.SkyBlockAPI;
import com.songoda.skyblock.api.island.IslandRole;
import com.songoda.skyblock.island.IslandEnvironment;
import com.songoda.skyblock.island.IslandWorld;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class FabledSkyblock<Island> implements ISkyBlock<Island>
{
	@Override
	public boolean isSelectionWithinIslandLocation(final Selection selection, final Island island)
	{
		final com.songoda.skyblock.api.island.Island island1 = (com.songoda.skyblock.api.island.Island) island;
		
		final double radius = island1.getRadius();
		final Location islandCenter = island1.getIsland().getLocation(IslandWorld.Normal, IslandEnvironment.Main);
		
		final Vector topVector = new Vector(islandCenter.getBlock().getX() + radius, 255.0, islandCenter.getBlock().getZ() + radius);
		final Vector bottomVector = new Vector(islandCenter.getBlock().getX() - radius, 0.0, islandCenter.getBlock().getZ() - radius);
		
		final CuboidSelection islandSelection = new CuboidSelection(SkyBlock.getInstance().getWorldManager().getWorld(IslandWorld.Normal), bottomVector, topVector);
		
		return islandSelection.contains(selection.getMinimumPoint()) && islandSelection.contains(selection.getMaximumPoint());
	}
	
	@Override
	public boolean isPlayersIsland(final Selection selection, final Player player)
	{
		final com.songoda.skyblock.api.island.Island island = SkyBlockAPI.getIslandManager().getIslandAtLocation(player.getLocation());
		
		if (island != null &&
			island.getRole(player) != null &&
			!island.getRole(player).equals(IslandRole.VISITOR))
		{
			return isSelectionWithinIslandLocation(selection, (Island) island);
		}
		
		return false;
	}
}
