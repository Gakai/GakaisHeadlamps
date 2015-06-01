package de.gakai.headlamps;

import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = HeadlampMod.MODID, version = HeadlampMod.VERSION)
public class HeadlampMod
{
	public static final String MODID = "GakaisHeadlamps";
	public static final String VERSION = "0.1";

	static final HeadlampItem ITEM_HEADLAMP_CHAIN = new HeadlampItem(ArmorMaterial.CHAIN);
	static final HeadlampItem ITEM_HEADLAMP_IRON = new HeadlampItem(ArmorMaterial.IRON);
	static final HeadlampItem ITEM_HEADLAMP_GOLD = new HeadlampItem(ArmorMaterial.GOLD);
	static final HeadlampItem ITEM_HEADLAMP_DIAMOND = new HeadlampItem(ArmorMaterial.DIAMOND);

	@SidedProxy(clientSide = "de.gakai.headlamps.ClientProxy", serverSide = "de.gakai.headlamps.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		ITEM_HEADLAMP_CHAIN.registerRecipe(Items.chainmail_helmet);
		ITEM_HEADLAMP_IRON.registerRecipe(Items.iron_helmet);
		ITEM_HEADLAMP_GOLD.registerRecipe(Items.golden_helmet);
		ITEM_HEADLAMP_DIAMOND.registerRecipe(Items.diamond_helmet);
		proxy.init();
	}

}
