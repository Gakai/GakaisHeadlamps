package de.gakai.headlamps;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.registry.GameRegistry;

public class HeadlampItem extends ItemArmor
{

    private String itemName;

    private String materialName;

    private String textureName;

    public HeadlampItem(ArmorMaterial material)
    {
        super(material, getArmorType(material), 0);
        materialName = getMaterialName();
        itemName = materialName + "_headlamp";
        textureName = HeadlampMod.MODID + ":textures/models/armor/" + itemName + ".png";

        setUnlocalizedName(HeadlampMod.MODID + ".headlamp" + StringUtils.capitalize(materialName));
        setTextureName(HeadlampMod.MODID + ":" + itemName);
        setCreativeTab(CreativeTabs.tabCombat);
    }

    void registerRecipe(Item helmet)
    {
        GameRegistry.registerItem(this, itemName);

        ItemStack headlampStack = new ItemStack(this);
        ItemStack diamondStack = new ItemStack(Items.diamond);
        ItemStack helmetStack = new ItemStack(helmet);
        ItemStack glowstoneStack = new ItemStack(Blocks.glowstone);

        GameRegistry.addRecipe(headlampStack, //
                "DGH", //
                'D', diamondStack, //
                'H', helmetStack, //
                'G', glowstoneStack);
    }

    private static int getArmorType(ArmorMaterial material)
    {
        switch (material)
        {
        case CHAIN:
            return 1;
        case GOLD:
            return 4;
        case DIAMOND:
            return 3;
        case IRON:
            return 2;
        default:
            throw new RuntimeException("Unknown armor type");
        }
    }

    private String getMaterialName()
    {
        switch (getArmorMaterial())
        {
        case CHAIN:
            return "chainmail";
        case GOLD:
            return "golden";
        case DIAMOND:
            return "diamond";
        case IRON:
            return "iron";
        default:
            throw new RuntimeException("Unknown headlamp material");
        }
    }

    public int getLightLevel()
    {
        switch (getArmorMaterial())
        {
        case CHAIN:
            return 10;
        case GOLD:
            return 13;
        case DIAMOND:
            return 15;
        case IRON:
            return 10;
        default:
            throw new RuntimeException("Unknown headlamp material");
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return textureName;
    }

}
