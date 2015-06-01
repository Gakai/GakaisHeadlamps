package de.gakai.headlamps;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import atomicstryker.dynamiclights.client.DynamicLights;
import atomicstryker.dynamiclights.client.IDynamicLightSource;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

public class ClientProxy extends CommonProxy
{

	private static final class PlayerLightSource implements IDynamicLightSource
	{
		private final EntityPlayer player;
		public int lightLevel;

		private PlayerLightSource(EntityPlayer player)
		{
			this.player = player;
		}

		@Override
		public int getLightLevel()
		{
			return lightLevel;
		}

		@Override
		public Entity getAttachmentEntity()
		{
			return player;
		}
	}

	private static final Map<EntityPlayer, PlayerLightSource> headlampPlayers = new HashMap();

	@Override
	public void init()
	{
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void clientTickEvent(TickEvent.ClientTickEvent event)
	{

		if (Minecraft.getMinecraft().theWorld == null)
			return;

		if (event.phase == Phase.END)
			return;

		for (Object obj : Minecraft.getMinecraft().theWorld.loadedEntityList)
		{
			if (!(obj instanceof EntityPlayer))
			{
				continue;
			}

			EntityPlayer player = (EntityPlayer) obj;
			ItemStack stack = player.getCurrentArmor(3);
			if (stack != null && stack.getItem() instanceof HeadlampItem)
			{
				PlayerLightSource ls = headlampPlayers.get(player);
				if (ls == null)
				{
					ls = new PlayerLightSource(player);
					DynamicLights.addLightSource(ls);
					headlampPlayers.put(player, ls);
				}
				ls.lightLevel = ((HeadlampItem) stack.getItem()).getLightLevel();
			}
			else
			{
				IDynamicLightSource lightSource = headlampPlayers.remove(player);
				if (lightSource != null)
				{
					DynamicLights.removeLightSource(lightSource);
				}
			}
		}

	}
}
