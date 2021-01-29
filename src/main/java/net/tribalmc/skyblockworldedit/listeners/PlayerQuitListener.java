package net.tribalmc.skyblockworldedit.listeners;

import net.tribalmc.skyblockworldedit.utils.PlayerUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public final class PlayerQuitListener implements Listener
{
	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent event)
	{
		final UUID playerUUID = event.getPlayer().getUniqueId();
		
		if (PlayerUtils.hasPlayerEditSession(playerUUID)) PlayerUtils.removePlayerEditSession(playerUUID);
	}
}
