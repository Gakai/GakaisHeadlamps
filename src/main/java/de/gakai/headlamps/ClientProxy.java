package de.gakai.headlamps;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

public class ClientProxy extends CommonProxy
{

    private static final Map<EntityPlayer, HeadlampLightSource> headlampPlayers = new HashMap();

    @Override
    public void init()
    {
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void clientTickEvent(TickEvent.ClientTickEvent event)
    {
        if (Minecraft.getMinecraft().theWorld == null || event.phase == Phase.END)
            return;

        for (Object obj : Minecraft.getMinecraft().theWorld.loadedEntityList)
        {
            if (!(obj instanceof EntityPlayer))
                continue;
            EntityPlayer player = (EntityPlayer) obj;
            ItemStack stack = player.getCurrentArmor(3);
            if (stack != null && stack.getItem() instanceof HeadlampItem)
            {
                HeadlampLightSource lightSource = headlampPlayers.get(player);
                int lightLevel = ((HeadlampItem) stack.getItem()).getLightLevel();
                if (lightSource == null)
                {
                    lightSource = new HeadlampLightSource(player, lightLevel);
                    headlampPlayers.put(player, lightSource);
                }
                lightSource.setLightLevel(lightLevel);
                lightSource.updatePosition();
            }
            else
            {
                HeadlampLightSource lightSource = headlampPlayers.remove(player);
                if (lightSource != null)
                    lightSource.remove();
            }
        }
    }

}
