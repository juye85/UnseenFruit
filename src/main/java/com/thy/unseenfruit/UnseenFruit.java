package com.thy.unseenfruit;

import com.thy.unseenfruit.block.BlockInvisibleCrop;
import com.thy.unseenfruit.common.CommonProxy;
import com.thy.unseenfruit.item.ItemInvisibleSeed;
import com.thy.unseenfruit.network.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@Mod(modid = UnseenFruit.MODID, version = UnseenFruit.VERSION, name = UnseenFruit.NAME)
public class UnseenFruit {
    public static final String MODID = "unseenfruit";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "Unseen Fruit";
    
    @Instance(MODID)
    public static UnseenFruit instance;
    
    @SidedProxy(clientSide = "com.thy.unseenfruit.client.ClientProxy", 
                serverSide = "com.thy.unseenfruit.common.CommonProxy")
    public static CommonProxy proxy;
    
    // 新增：种子和作物方块引用
    public static Item invisibleSeed;
    public static Block invisibleCrop;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.init();
        proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
