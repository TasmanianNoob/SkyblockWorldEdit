package net.tribalmc.skyblockworldedit.hooks;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class ASkyBlock<Island> implements ISkyBlock<Island>
{
	@Override
	public boolean isSelectionWithinIslandLocation(final Selection selection, final Island island)
	{
		final com.wasteofplastic.askyblock.Island island1 = (com.wasteofplastic.askyblock.Island) island;
		
		final double radius = island1.getProtectionSize() / 2.0;
		final Location islandCenter = island1.getCenter();
		
		final Vector topVector = new Vector(islandCenter.getBlock().getX() + radius, 255.0, islandCenter.getBlock().getZ() + radius);
		final Vector bottomVector = new Vector(islandCenter.getBlock().getX() - radius, 0.0, islandCenter.getBlock().getZ() - radius);
		
		final CuboidSelection islandSelection = new CuboidSelection(islandCenter.getWorld(), bottomVector, topVector);
		
		return islandSelection.contains(selection.getMinimumPoint()) && islandSelection.contains(selection.getMaximumPoint());
	}
	
	@Override
	public boolean isPlayersIsland(final Selection selection, final Player player)
	{
		final ASkyBlockAPI aSkyblock = ASkyBlockAPI.getInstance();
		final com.wasteofplastic.askyblock.Island island = aSkyblock.getIslandAt(player.getLocation());
		
		if (island != null &&
			island.getMembers().contains(player.getUniqueId()))
		{
			return isSelectionWithinIslandLocation(selection, (Island) island);
		}
		
		return false;
	}
}
