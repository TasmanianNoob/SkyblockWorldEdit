package net.tribalmc.skyblockworldedit.utils;

import de.leonhard.storage.Yaml;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.tribalmc.skyblockworldedit.file.ConfigHandler;

import java.util.List;

public final class ConfigUtils
{
	private static final Object2ObjectOpenHashMap<String, String> configValues = new Object2ObjectOpenHashMap<>();
	private static final Object2ObjectOpenHashMap<String, ObjectArrayList<String>> configListStringValues = new Object2ObjectOpenHashMap<>();
	
	public static String getConfigString(final String key)
	{
		return configValues.get(key);
	}
	
	public static ObjectArrayList<String> getConfigStringList(final String key)
	{
		return configListStringValues.get(key);
	}
	
	public static void reloadConfig()
	{
		final Yaml file = ConfigHandler.getFile();
		file.setPathPrefix(null);
		
		ConfigHandler.forceReload();
		configValues.clear();
		configListStringValues.clear();
		
		configValues.put("permission", file.getString("permission"));
		configValues.put("use-economy", String.valueOf(file.getBoolean("use-economy")));
		configValues.put("undo-refund", String.valueOf(file.getDouble("undo-refund")));
		configValues.put("default-price-per-block", String.valueOf(file.getDouble("default-price-per-block")));
		configValues.put("default-blocks-placeable", String.valueOf(file.getInt("default-blocks-placeable")));
		
		for (final String block : file.getSection("unique-block-price").keySet())
			configValues.put("unique-block-price." + block, String.valueOf(file.getDouble("unique-block-price." + block)));
		
		for (final String group : file.getSection("groups").keySet())
			configValues.put("groups." + group, String.valueOf(file.getInt("groups." + group)));
		
		insertStringList("place-blacklist");
		insertStringList("disabled-worlds");
	}
	
	private static void insertStringList(final String section)
	{
		final Yaml file = ConfigHandler.getFile();
		file.setPathPrefix(null);
		
		final List<String> configValueTemp = file.getStringList(section);
		
		configListStringValues.put(section, new ObjectArrayList<>());
		configListStringValues.get(section).ensureCapacity(configValueTemp.size());
		configListStringValues.get(section).addAll(configValueTemp);
		
		configValueTemp.clear();
	}
}
