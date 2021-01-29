package net.tribalmc.skyblockworldedit.hooks;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import net.tribalmc.skyblockworldedit.SkyblockWorldEdit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerEntry;
import org.primesoft.asyncworldedit.api.utils.IFuncParamEx;
import org.primesoft.asyncworldedit.api.worldedit.IAsyncEditSessionFactory;
import org.primesoft.asyncworldedit.api.worldedit.ICancelabeEditSession;
import org.primesoft.asyncworldedit.api.worldedit.IThreadSafeEditSession;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class AsyncWorldEdit implements IWorldEdit
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
		
		final IThreadSafeEditSession editSession = ((IAsyncEditSessionFactory) SkyblockWorldEdit.getWorldEditPlugin().getWorldEdit().getEditSessionFactory()).getThreadSafeEditSession(world, maxBlocks);
		
		final IPlayerEntry playerEntry = SkyblockWorldEdit.getAsyncWorldEdit().getPlayerManager().getPlayer(playerUUID);
		final SetAction pasteAction = new SetAction(region, new BaseBlock(block.getId()));
		
		SkyblockWorldEdit.getAsyncWorldEdit().getBlockPlacer().performAsAsyncJob(editSession, playerEntry, "SkyBlockWorldEdit Pasting", pasteAction);
		Objects.requireNonNull(SkyblockWorldEdit.getWorldEditPlugin().getWorldEdit().getSessionManager().findByName(Bukkit.getPlayer(playerUUID).getName())).remember((EditSession) editSession);
	}
	
	@Override
	public void replaceStructure(final Selection selection, final Material[] replaceBlocks, final Material block, final int maxBlocks, final UUID playerUUID)
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
		
		final IThreadSafeEditSession editSession = ((IAsyncEditSessionFactory) SkyblockWorldEdit.getWorldEditPlugin().getWorldEdit().getEditSessionFactory()).getThreadSafeEditSession(world, maxBlocks);
		
		final Set<BaseBlock> replacedBlocks = new HashSet<>();
		
		for (final Material baseBlock : replaceBlocks)
			replacedBlocks.add(new BaseBlock(baseBlock.getId()));
		
		final IPlayerEntry playerEntry = SkyblockWorldEdit.getAsyncWorldEdit().getPlayerManager().getPlayer(playerUUID);
		final ReplaceAction replaceAction = new ReplaceAction(region, replacedBlocks, new BaseBlock(block.getId()));
		
		SkyblockWorldEdit.getAsyncWorldEdit().getBlockPlacer().performAsAsyncJob(editSession, playerEntry, "SkyBlockWorldEdit Replacing", replaceAction);
		Objects.requireNonNull(SkyblockWorldEdit.getWorldEditPlugin().getWorldEdit().getSessionManager().findByName(Bukkit.getPlayer(playerUUID).getName())).remember((EditSession) editSession);
	}
	
	private final static class SetAction implements IFuncParamEx<Integer, ICancelabeEditSession, MaxChangedBlocksException>
	{
		private final Region region;
		private final BaseBlock block;
		
		public SetAction(final Region region, final BaseBlock block)
		{
			this.region = region;
			this.block = block;
		}
		
		@Override
		public Integer execute(final ICancelabeEditSession editSession) throws MaxChangedBlocksException
		{
			editSession.setBlocks(region, block);
			editSession.flushQueue();
			
			return editSession.getBlockChangeCount();
		}
	}
	
	private final static class ReplaceAction implements IFuncParamEx<Integer, ICancelabeEditSession, MaxChangedBlocksException>
	{
		private final Region region;
		private final BaseBlock block;
		private final Set<BaseBlock> replaceBlocks;
		
		public ReplaceAction(final Region region, final Set<BaseBlock> replacedBlocks, final BaseBlock block)
		{
			this.region = region;
			this.block = block;
			
			replaceBlocks = replacedBlocks;
		}
		
		@Override
		public Integer execute(final ICancelabeEditSession editSession) throws MaxChangedBlocksException
		{
			editSession.replaceBlocks(region, replaceBlocks, block);
			editSession.flushQueue();
			
			return editSession.getBlockChangeCount();
		}
	}
}