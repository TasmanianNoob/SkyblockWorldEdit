package net.tribalmc.skyblockworldedit.utils;

import org.bukkit.inventory.ItemStack;

public final class PlayerWorldEdit
{
	public double cost;
	public ItemStack item;
	
	public PlayerWorldEdit(final double cost, final ItemStack item)
	{
		this.cost = cost;
		
		this.item = item;
	}
}
