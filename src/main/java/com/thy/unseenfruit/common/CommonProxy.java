package com.thy.unseenfruit.common;

import com.thy.unseenfruit.block.BlockInvisibleCrop;
import com.thy.unseenfruit.effect.EffectPinkEye;
import com.thy.unseenfruit.item.ItemInvisibleSeed;
import com.thy.unseenfruit.item.ItemIronCone;
import com.thy.unseenfruit.item.ItemIronNeedle;
import com.thy.unseenfruit.item.ItemUnseenFruit;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    
    public static Item unseenFruit;
    public static Item ironCone;
    public static Item ironNeedle;
    public static Potion pinkEye;
    
    // 新增
    public static Item invisibleSeed;
    public static Block invisibleCrop;
    
    public void preInit(FMLPreInitializationEvent event) {
        // 先创建果实
        unseenFruit = new ItemUnseenFruit();
        
        // 创建作物方块
        invisibleCrop = new BlockInvisibleCrop();
        GameRegistry.registerBlock(invisibleCrop, "invisible_crop");
        
        // 创建种子（传入作物方块）
        invisibleSeed = new ItemInvisibleSeed((BlockInvisibleCrop)invisibleCrop);
        
        // 注册物品
        GameRegistry.registerItem(unseenFruit, "unseen_fruit");
        GameRegistry.registerItem(invisibleSeed, "invisible_seed");
        
        ironCone = new ItemIronCone();
        ironNeedle = new ItemIronNeedle();
        GameRegistry.registerItem(ironCone, "iron_cone");
        GameRegistry.registerItem(ironNeedle, "iron_needle");
        
        pinkEye = new EffectPinkEye(32, false, 0xFF69B4).setPotionName("potion.pinkEye");
        
        // 保存到主类实例
        UnseenFruit.instance.invisibleSeed = invisibleSeed;
        UnseenFruit.instance.invisibleCrop = invisibleCrop;
    }
    
    public void init(FMLInitializationEvent event) {
        // 铁锥配方：倒三角 ▽
        GameRegistry.addRecipe(new ItemStack(ironCone, 2),
            "I I",
            " I ",
            'I', new ItemStack(net.minecraft.init.Items.iron_nugget)
        );
        
        // 铁针配方：竖条，铁锥在下
        GameRegistry.addRecipe(new ItemStack(ironNeedle, 4),
            "I",
            "I",
            "C",
            'I', new ItemStack(net.minecraft.init.Items.iron_ingot),
            'C', ironCone
        );
        
        // ====== 新增合成配方 ======
        
        // 种子合成配方：直接用果实合成种子
        GameRegistry.addShapelessRecipe(
            new ItemStack(invisibleSeed, 1),  // 产出1个种子
            unseenFruit  // 消耗1个果实
        );
        
        // 可选：果实+铁针合成更多果实（类似"繁殖"）
        GameRegistry.addRecipe(new ItemStack(unseenFruit, 2),
            "F",
            "N",
            'F', unseenFruit,
            'N', ironNeedle
        );
        
        MinecraftForge.EVENT_BUS.register(new UnseenFruitEventHandler());
        FMLCommonHandler.instance().bus().register(new UnseenFruitEventHandler());
    }
    
    public void postInit(FMLPostInitializationEvent event) {
        // 注册骨粉效果
        net.minecraft.init.Blocks.farmland.setFertilizable(invisibleCrop);
    }
}
    public void init(FMLInitializationEvent event) {
    // ... 原有的合成配方 ...
    
    // 注册事件处理器
    UnseenFruitEventHandler eventHandler = new UnseenFruitEventHandler();
    MinecraftForge.EVENT_BUS.register(eventHandler);
    FMLCommonHandler.instance().bus().register(eventHandler);
    
    // 注册Forge的草掉落（可选）
    if (invisibleSeed != null) {
        net.minecraftforge.common.MinecraftForge.addGrassSeed(
            new ItemStack(invisibleSeed), 10); // 权重10（较高）
    }
    
    // ... 其他初始化 ...
}