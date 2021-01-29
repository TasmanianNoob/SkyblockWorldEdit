package net.tribalmc.skyblockworldedit.hooks;

import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface IWorldEdit
{
	void undoWorldEdit(final Player player);
	void setStructure(final Selection selection, final Material block, final int maxBlocks, final UUID playerUUID);
	void replaceStructure(final Selection selection, final Material[] replacedBlocks, final Material block, final int maxBlocks, final UUID playerUUID);
}
