package de.gakai.headlamps;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import atomicstryker.dynamiclights.client.DynamicLights;
import atomicstryker.dynamiclights.client.IDynamicLightSource;

final class HeadlampLightSource implements IDynamicLightSource
{

    private final EntityPlayer player;

    private final Entity phantom;

    private int lightLevel;

    public HeadlampLightSource(EntityPlayer player, int lightLevel)
    {
        this.player = player;
        this.lightLevel = lightLevel;
        phantom = new EntityItem(player.worldObj);
        DynamicLights.addLightSource(this);
    }

    public void remove()
    {
        DynamicLights.removeLightSource(this);
        phantom.setDead();
    }

    @Override
    public Entity getAttachmentEntity()
    {
        return phantom;
    }

    @Override
    public int getLightLevel()
    {
        return lightLevel;
    }

    public void setLightLevel(int lightLevel)
    {
        if (this.lightLevel != lightLevel)
        {
            this.lightLevel = lightLevel;
            DynamicLights.removeLightSource(this);
            DynamicLights.addLightSource(this);
        }
    }

    public double getLightDistance()
    {
        return lightLevel / 2.1;
    }

    public void updatePosition()
    {
        double maxDistance = getLightDistance();
        Vec3 lookAt = player.getLook(1);
        Vec3 playerPos = new Vec3(player.posX, player.posY + (player.getEyeHeight() - player.getDefaultEyeHeight()), player.posZ);
        Vec3 start = playerPos.addVector(0, player.getEyeHeight(), 0);
        Vec3 end = start.addVector(lookAt.xCoord * maxDistance, lookAt.yCoord * maxDistance, lookAt.zCoord * maxDistance);
        MovingObjectPosition result = player.worldObj.rayTraceBlocks(start, end, false);
        if (result != null)
            end = result.hitVec;
        phantom.posX = end.xCoord;
        phantom.posY = end.yCoord;
        phantom.posZ = end.zCoord;
    }

}