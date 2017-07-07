package com.higgs.wom.event;

import com.higgs.wom.HiggsWom;
import com.higgs.wom.entitydata.WomPlayerData;
import com.higgs.wom.network.packets.WomPacketSyncPlayerData;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class WomEventHandlerCommon
{
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing e)
	{
	    if(e.entity instanceof EntityPlayer)// || e.entity instanceof EntityPlayerMP)
	    {
//	    	e.entity.registerExtendedProperties("womPlayerData", new WomPlayerData((EntityPlayer)e.entity));
	        WomPlayerData.register((EntityPlayer)e.entity);
	    }
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e)
	{
//	    if(e.entity instanceof EntityPlayer)// || e.entity instanceof EntityPlayerMP)
//	    {
//	    	e.entity.registerExtendedProperties("womPlayerData", new WomPlayerData((EntityPlayer)e.entity));
//	    }
		
		if(e.entity instanceof EntityPlayerMP)
		{
			WomPlayerData.get((EntityPlayer)e.entity).loadNBTData(new NBTTagCompound());
			HiggsWom.network.sendTo(new WomPacketSyncPlayerData((EntityPlayer)e.entity), (EntityPlayerMP) e.entity);
		}
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event)
	{
		WomPlayerData.get(event.entityPlayer).copy(WomPlayerData.get(event.original));
	}
}
