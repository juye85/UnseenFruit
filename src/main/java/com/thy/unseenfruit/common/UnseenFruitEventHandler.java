package com.thy.unseenfruit.common;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;

public class UnseenFruitEventHandler {
    
    // ... 原有的攻击事件处理器 ...
    
    // ========== 新增：打草掉落种子 ==========
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onBlockHarvested(BlockEvent.HarvestDropsEvent event) {
        // 检查是否是玩家破坏
        if (event.harvester != null && !event.harvester.worldObj.isRemote) {
            Block block = event.block;
            
            // 检查是否打草
            if (block == Blocks.tallgrass || block == Blocks.deadbush || 
                (block == Blocks.grass && event.blockMetadata == 1)) {
                
                // 50%几率掉落无形种子
                if (event.world.rand.nextFloat() < 0.5F) {
                    // 添加到掉落列表
                    event.drops.add(new ItemStack(CommonProxy.invisibleSeed, 1));
                    
                    // 播放掉落音效
                    if (!event.world.isRemote) {
                        event.world.playSoundAtEntity(event.harvester, 
                            "unseenfruit.seed_drop", 
                            0.5F, 1.0F + event.world.rand.nextFloat() * 0.2F);
                    }
                }
            }
        }
    }
    
    // ========== 新增：耕地时也有几率发现种子 ==========
    @SubscribeEvent
    public void onUseHoe(UseHoeEvent event) {
        if (!event.world.isRemote && event.world.rand.nextFloat() < 0.1F) {
            // 10%几率耕地时发现种子
            if (!event.entityPlayer.inventory.addItemStackToInventory(
                new ItemStack(CommonProxy.invisibleSeed, 1))) {
                
                // 背包满时掉落在地上
                event.entityPlayer.dropPlayerItemWithRandomChoice(
                    new ItemStack(CommonProxy.invisibleSeed, 1), false);
            }
            
            // 播放音效
            event.world.playSoundAtEntity(event.entityPlayer, 
                "unseenfruit.seed_drop", 0.3F, 1.0F);
        }
    }
    
    // ========== 新增：镰刀类工具打草有更高几率 ==========
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onBlockHarvestedWithScythe(BlockEvent.HarvestDropsEvent event) {
        if (event.harvester != null && !event.harvester.worldObj.isRemote) {
            Block block = event.block;
            
            // 检查是否打草
            if (block == Blocks.tallgrass || block == Blocks.deadbush || 
                (block == Blocks.grass && event.blockMetadata == 1)) {
                
                // 检查玩家是否使用镰刀类工具
                ItemStack heldItem = event.harvester.getHeldItem();
                if (heldItem != null) {
                    // 判断是否是类似镰刀的工具（比如剪刀、剑）
                    if (heldItem.getItem() instanceof net.minecraft.item.ItemShears || 
                        heldItem.getItem() instanceof net.minecraft.item.ItemSword) {
                        
                        // 使用镰刀类工具时，几率提高到80%
                        if (event.world.rand.nextFloat() < 0.8F) {
                            // 还可能掉落更多种子
                            int seedCount = 1 + event.world.rand.nextInt(2);
                            event.drops.add(new ItemStack(CommonProxy.invisibleSeed, seedCount));
                            
                            // 播放音效
                            event.world.playSoundAtEntity(event.harvester, 
                                "unseenfruit.seed_drop", 
                                0.6F, 0.8F + event.world.rand.nextFloat() * 0.4F);
                        }
                        event.setResult(net.minecraftforge.event.Event.Result.DENY); // 阻止其他处理
                    }
                }
            }
        }
    }
}
