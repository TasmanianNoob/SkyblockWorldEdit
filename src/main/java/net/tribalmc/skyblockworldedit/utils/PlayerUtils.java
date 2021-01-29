package net.tribalmc.skyblockworldedit.utils;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.tribalmc.skyblockworldedit.SkyblockWorldEdit;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class PlayerUtils
{
	private static final Object2ObjectOpenHashMap<UUID, PlayerWorldEdit> playerUndos = new Object2ObjectOpenHashMap<>();
	
	public static boolean playerHasPermission(final Player player, final String permission)
	{
		if (!player.hasPermission(permission))
		{
			player.sendMessage(Utils.colorize(MessagesUtils.getMessage("no-permission")));
			return false;
		}
		
		return true;
	}
	
	public static boolean hasPlayerEditSession(final UUID playerUUID)
	{
		return playerUndos.containsKey(playerUUID);
	}
	
	public static void removePlayerEditSession(final UUID playerUUID)
	{
		playerUndos.remove(playerUUID);
	}
	
	public static void addPlayerEditSession(final UUID playerUUID, final PlayerWorldEdit wEditSession)
	{
		playerUndos.put(playerUUID, wEditSession);
	}
	
	public static PlayerWorldEdit getPlayerEditSession(final UUID playerUUID)
	{
		return playerUndos.get(playerUUID);
	}
	
	public static String getPlayerRank(final Player player)
	{
		try
		{
			return SkyblockWorldEdit.getPermissionHandler().getPrimaryGroup(player);
		}
		catch (final UnsupportedOperationException exception)
		{
			return "null";
		}
	}
	
	
}
