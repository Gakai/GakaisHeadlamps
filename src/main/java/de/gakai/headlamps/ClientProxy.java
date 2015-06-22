package de.gakai.headlamps;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy
{

    private static final Map<EntityPlayer, HeadlampLightSource> headlampPlayers = new HashMap();

    @Override
    public void init()
    {
        FMLCommonHandler.instance().bus().register(this);

        String[] materials = { "chainmail", "diamond", "golden", "iron" };
        for (String material : materials)
        {
            String itemName = material + "_headlamp";
            Item item = GameRegistry.findItem(HeadlampMod.MODID, itemName);
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                    .register(item, 0, new ModelResourceLocation(HeadlampMod.MODID + ":" + itemName, "inventory"));
        }
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
