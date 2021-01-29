package net.tribalmc.skyblockworldedit;

import com.boydti.fawe.Fawe;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.wasteofplastic.askyblock.Island;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.tribalmc.skyblockworldedit.commands.*;
import net.tribalmc.skyblockworldedit.hooks.*;
import net.tribalmc.skyblockworldedit.listeners.PlayerQuitListener;
import net.tribalmc.skyblockworldedit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.primesoft.asyncworldedit.api.IAsyncWorldEdit;

public final class SkyblockWorldEdit extends JavaPlugin {
	
	private static Permission permission;
	private static Economy economy;
	
	private static IWorldEdit worldEdit = null;
	private static ISkyBlock skyBlock = null;
	
	public static IWorldEdit getWorldEditHandler() { return worldEdit; }
	public static ISkyBlock getSkyBlockHandler() { return skyBlock; }
	public static Permission getPermissionHandler() { return permission; }
	public static Economy getEconomyHandler() { return economy; }
	
	public static Fawe getFastAsyncWorldEdit() { return Fawe.get(); }
	public static IAsyncWorldEdit getAsyncWorldEdit() { return (IAsyncWorldEdit) Bukkit.getPluginManager().getPlugin("AsyncWorldEdit"); }
	public static WorldEditPlugin getWorldEditPlugin() { return (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit"); }
	
	@Override
	public void onEnable()
	{
		
		Utils.loadConfigurations();
		
		loadSkyblockAPI();
		loadWorldEditAPI();
		
		loadListeners();
		loadCommands();
		loadPermissions();
		loadEconomy();
	}
	
	@Override
	public void onDisable()
	{
	}
	
	private void loadSkyblockAPI()
	{
		final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
		
		if (pluginManager.isPluginEnabled("ASkyBlock"))
		{
			System.out.println("Found ASkyBlock: Hooking");
			skyBlock = new ASkyBlock<Island>();
			return;
		}
		
		if (pluginManager.isPluginEnabled("FabledSkyblock"))
		{
			System.out.println("Found FabledSkyblock: Hooking");
			skyBlock = new FabledSkyblock<com.songoda.skyblock.island.Island>();
			return;
		}
		
		
		if (pluginManager.isPluginEnabled("IridiumSkyblock"))
		{
			System.out.println("Found IridiumSkyblock: Hooking");
			skyBlock = new IridiumSkyblock<com.iridium.iridiumskyblock.Island>();
			return;
		}
		
		if (pluginManager.isPluginEnabled("BSkyBlock"))
		{
			System.out.println("Found BSkyBlock: Hooking");
			skyBlock = new BSkyBlock<world.bentobox.bentobox.database.objects.Island>();
			return;
		}
		
		if (pluginManager.isPluginEnabled("SuperiorSkyblock2"))
		{
			System.out.println("Found SuperiorSkyblock: Hooking");
			skyBlock = new SuperiorSkyblock<com.bgsoftware.superiorskyblock.api.island.Island>();
			return;
		}
		
		Bukkit.getServer().getLogger().warning(Utils.colorize("WARNING: No skyblock plugin found, shutting down."));
		Bukkit.getServer().getPluginManager().disablePlugin(this);
	}
	
	private void loadWorldEditAPI()
	{
		final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
		
		if (pluginManager.isPluginEnabled("AsyncWorldEdit"))
		{
			System.out.println("Found AsyncWorldEdit: Hooking");
			worldEdit = new AsyncWorldEdit();
			return;
		}
		
		if (pluginManager.isPluginEnabled("FastAsyncWorldEdit"))
		{
			System.out.println("Found FastAsyncWorldEdit: Hooking");
			Bukkit.getServer().getLogger().warning(Utils.colorize("WARNING: FastAsyncWorldEdit is unstable, it is recommended to use AsyncWorldEdit instead."));
			worldEdit = new FastAsyncWorldEdit();
			return;
		}
		
		if (pluginManager.isPluginEnabled("WorldEdit"))
		{
			System.out.println("Found WorldEdit: Hooking");
			Bukkit.getServer().getLogger().warning(Utils.colorize("WARNING: It is recommended to use AsyncWorldEdit as well as WorldEdit to elimate lag."));
			worldEdit = new net.tribalmc.skyblockworldedit.hooks.WorldEdit();
			return;
		}
		
		Bukkit.getServer().getLogger().warning(Utils.colorize("WARNING: WorldEdit isn't installed, shutting down."));
		Bukkit.getServer().getPluginManager().disablePlugin(this);
	}
	
	private void loadListeners()
	{
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
	}
	
	private void loadCommands()
	{
		getCommand("set").setExecutor(new SetCommand());
		getCommand("replace").setExecutor(new ReplaceCommand());
		getCommand("sbwe").setExecutor(new SBWECommand());
		getCommand("assistancecommand").setExecutor(new AssistCommand());
		getCommand("undo").setExecutor(new UndoCommand());
	}
	
	private void loadPermissions()
	{
		permission = getServer().getServicesManager().getRegistration(Permission.class).getProvider();
	}
	
	private void loadEconomy()
	{
		economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
	}
}
