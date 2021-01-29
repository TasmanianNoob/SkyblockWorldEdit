package net.tribalmc.skyblockworldedit.hooks;

import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.entity.Player;

public interface ISkyBlock<Island>
{
	boolean isSelectionWithinIslandLocation(final Selection selection, final Island island);
	boolean isPlayersIsland(final Selection selection, final Player player);
}
