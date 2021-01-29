package net.tribalmc.skyblockworldedit.file;

import de.leonhard.storage.Yaml;

import java.io.IOException;

public final class MessagesHandler
{
	private static final Yaml file = new Yaml("messages", "plugins/SkyblockWorldEdit");
	
	public static Yaml getFile()
	{
		return file;
	}
	
	public static void forceReload()
	{
		file.forceReload();
	}
	
	public static void createFile()
	{
		try
		{
			file.getFile().createNewFile();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		
		file.setPathPrefix(null);
		file.setDefault("no-permission", "&cSorry, you are unable to do this.");
		file.setDefault("not-player", "&cSorry, you are unable to do this.");
		file.setDefault("not-on-island", "&cSorry, some of your region is not on your island.");
		file.setDefault("no-region", "&cSorry, you don't have your region set.");
		file.setDefault("disabled-world", "&cSorry, you can't use worldedit in this world.");
		file.setDefault("invalid-block", "&cSorry, that block is invalid.");
		file.setDefault("too-large-region", "&cSorry, your region is too large.");
		file.setDefault("not-enough-blocks", "&cSorry, you don't have enough blocks to complete this worldedit.");
		file.setDefault("not-enough-money", "&cSorry, you don't have enough money to complete this worldedit.");
		file.setDefault("disabled-block", "&cSorry, that block is disabled from being used with worldedit.");
		file.setDefault("no-undo-available", "&cSorry, you don't have any undo's available.");
		file.setDefault("set-usage", "&c/set <block>");
		file.setDefault("replace-usage", "&c/replace <replaced-blocks> <block>");
		file.setDefault("files-reloaded", "&aSuccessfully reloaded the config.yml and messages.yml!");
		
		file.setDefault("enough-money", "&aYou successfully completed this worldedit, it cost you %cost%.");
		file.setDefault("eco-disabled", "&aYou successfully completed this worldedit.");
		file.setDefault("undo-refund", "&aYou successfully undid your most recent worldedit, you got %refund% (%refund_percentage%).");
		file.setDefault("undo-eco-disabled", "&aYou successfully undid your most recent worldedit.");
		
		file.setDefault("help-command", new String[]{ "help", "me" });
	}
}
