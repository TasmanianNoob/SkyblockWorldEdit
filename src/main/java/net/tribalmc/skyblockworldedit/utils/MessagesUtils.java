package net.tribalmc.skyblockworldedit.utils;

import de.leonhard.storage.Yaml;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.tribalmc.skyblockworldedit.file.MessagesHandler;

import java.util.List;

public final class MessagesUtils
{
	private static final Object2ObjectOpenHashMap<String, String> messages = new Object2ObjectOpenHashMap<>();
	private static final ObjectArrayList<String> helpMessage = new ObjectArrayList<>();
	
	public static String getMessage(final String key)
	{
		return messages.get(key);
	}
	public static ObjectArrayList<String> getHelpMessage()
	{
		return helpMessage;
	}
	
	public static void reloadMessages()
	{
		final Yaml file = MessagesHandler.getFile();
		file.setPathPrefix(null);
		
		MessagesHandler.forceReload();
		messages.clear();
		helpMessage.clear();
		
		insertMessage("no-permission");
		insertMessage("not-player");
		insertMessage("not-on-island");
		insertMessage("no-region");
		insertMessage("disabled-world");
		insertMessage("invalid-block");
		insertMessage("too-large-region");
		insertMessage("not-enough-blocks");
		insertMessage("not-enough-money");
		insertMessage("disabled-block");
		insertMessage("no-undo-available");
		insertMessage("set-usage");
		insertMessage("replace-usage");
		insertMessage("enough-money");
		insertMessage("eco-disabled");
		insertMessage("undo-refund");
		insertMessage("undo-eco-disabled");
		insertMessage("files-reloaded");
		
		final List<String> helpMessageTemp = file.getStringList("help-command");
		
		helpMessage.ensureCapacity(helpMessageTemp.size());
		helpMessage.addAll(helpMessageTemp);
		
		helpMessageTemp.clear();
	}
	
	private static void insertMessage(final String location)
	{
		final Yaml file = MessagesHandler.getFile();
		file.setPathPrefix(null);
		
		messages.put(location, file.getString(location));
	}
}
