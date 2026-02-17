package com.thy.unseenfruit.item;

import com.thy.unseenfruit.block.BlockInvisibleCrop;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemInvisibleSeed extends Item {
    
    private BlockInvisibleCrop cropBlock;
    
    public ItemInvisibleSeed(BlockInvisibleCrop crop) {
        this.cropBlock = crop;
        this.setUnlocalizedName("invisible_seed");
        this.setTextureName("unseenfruit:invisible_seed");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        this.setMaxStackSize(64);
    }
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, 
                         int x, int y, int z, int side, 
                         float hitX, float hitY, float hitZ) {
    if (world.getBlock(x, y, z) == Blocks.farmland) {
        if (world.isAirBlock(x, y + 1, z)) {
            world.setBlock(x, y + 1, z, this.cropBlock, 0, 2);
            
            // 播放种植音效
            if (!world.isRemote) {
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, 
                                     "unseenfruit.seed.plant", 
                                     1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
            }
            
            stack.stackSize--;
            return true;
        }
    }
    return false;
}