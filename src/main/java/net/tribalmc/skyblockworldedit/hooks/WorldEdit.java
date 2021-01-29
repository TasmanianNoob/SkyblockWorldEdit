package net.tribalmc.skyblockworldedit.hooks;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import net.tribalmc.skyblockworldedit.SkyblockWorldEdit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class WorldEdit implements IWorldEdit
{
	@Override
	public void undoWorldEdit(final Player player)
	{
		player.performCommand("/undo");
	}
	
	@Override
	public void setStructure(final Selection selection, final Material block, final int maxBlocks, final UUID playerUUID)
	{
		final World world = selection.getRegionSelector().getWorld();
		
		Region region = null;
		try
		{
			region = selection.getRegionSelector().getRegion();
		}
		catch (final IncompleteRegionException e)
		{
			e.printStackTrace();
		}
		
		final EditSession editSession =  SkyblockWorldEdit.getWorldEditPlugin().getWorldEdit().getEditSessionFactory().getEditSession(world, maxBlocks);
		
		editSession.setBlocks(region, new BaseBlock(block.getId()));
		editSession.flushQueue();
		
		SkyblockWorldEdit.getWorldEditPlugin().getSession(Bukkit.getPlayer(playerUUID)).remember(editSession);
	}
	
	@Override
	public void replaceStructure(final Selection selection, final Material[] replacedBlocks, final Material block, final int maxBlocks, final UUID playerUUID)
	{
		final World world = selection.getRegionSelector().getWorld();
		
		Region region = null;
		try
		{
			region = selection.getRegionSelector().getRegion();
		}
		catch (final IncompleteRegionException e)
		{
			e.printStackTrace();
		}
		
		final EditSession editSession =  SkyblockWorldEdit.getWorldEditPlugin().getWorldEdit().getEditSessionFactory().getEditSession(world, maxBlocks);
		
		final Set<BaseBlock> blocksReplaced = new HashSet<>();
		
		for (final Material baseBlock : replacedBlocks)
			blocksReplaced.add(new BaseBlock(baseBlock.getId()));
		
		editSession.replaceBlocks(region, blocksReplaced, new BaseBlock(block.getId()));
		editSession.flushQueue();
		
		SkyblockWorldEdit.getWorldEditPlugin().getSession(Bukkit.getPlayer(playerUUID)).remember(editSession);
	}
}
