package de.gakai.headlamps;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.registry.GameRegistry;

public class HeadlampItem extends ItemArmor
{

	public HeadlampItem(ArmorMaterial material)
	{
		super(material, getArmorType(material), 0);
		setUnlocalizedName(HeadlampMod.MODID + ".headlamp" + StringUtils.capitalize(getMaterialName()));
		setTextureName(getMaterialName() + "_helmet");
		setCreativeTab(CreativeTabs.tabCombat);
		if (material == ArmorMaterial.GOLD)
			setTextureName("gold_helmet");
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
			throw new RuntimeException("Unknown headlamp material");
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

	public void registerRecipe(Item helmet)
	{
		GameRegistry.registerItem(this, getMaterialName() + "_headlamp");

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

	public int getLightLevel()
	{
		switch (getArmorMaterial())
		{
		case CHAIN:
			return 13;
		case GOLD:
			return 14;
		case DIAMOND:
			return 15;
		case IRON:
			return 13;
		default:
			throw new RuntimeException("Unknown headlamp material");
		}
	}
}
