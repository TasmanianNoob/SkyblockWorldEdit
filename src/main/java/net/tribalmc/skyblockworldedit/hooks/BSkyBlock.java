package net.tribalmc.skyblockworldedit.hooks;

import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;

import java.util.Optional;

public final class BSkyBlock<Island> implements ISkyBlock<Island>
{
	@Override
	public boolean isSelectionWithinIslandLocation(final Selection selection, final Island island)
	{
		final world.bentobox.bentobox.database.objects.Island island1 = (world.bentobox.bentobox.database.objects.Island) island;
		
		return island1.inIslandSpace(selection.getMinimumPoint()) && island1.inIslandSpace(selection.getMaximumPoint());
	}
	
	@Override
	public boolean isPlayersIsland(final Selection selection, final Player player)
	{
		final Optional<world.bentobox.bentobox.database.objects.Island> island = BentoBox.getInstance().getIslands().getIslandAt(player.getLocation());
		
		if (island.isPresent() &&
			island.get().getMembers().containsKey(player.getUniqueId()))
		{
			return isSelectionWithinIslandLocation(selection, (Island) island);
		}
		
		return false;
	}
}
