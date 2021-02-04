package net.tribalmc.skyblockworldedit.file;

import de.leonhard.storage.Yaml;
import org.bukkit.Material;

import java.io.IOException;

public final class ConfigHandler
{
	private static final Yaml file = new Yaml("config", "plugins/SkyblockWorldEdit");
	
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
		file.setDefault("permission", "skyblockworldedit.use");
		file.setDefault("use-economy", true);
		file.setDefault("undo-refund", 50.0);
		file.setDefault("default-price-per-block", 10.0);
		file.setDefault("unique-block-price." + Material.DIAMOND_BLOCK, 12.5);

		file.setDefault("place-blacklist", new Material[] { Material.CHEST, Material.TRAPPED_CHEST, Material.BED });
		file.setDefault("disabled-worlds", new String[] { "disabled_world" });
		
		file.setDefault("default-blocks-placeable", 100);
		file.setPathPrefix("groups");
	}
}
