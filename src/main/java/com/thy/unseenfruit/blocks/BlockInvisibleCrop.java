package com.thy.unseenfruit.block;

import com.thy.unseenfruit.UnseenFruit;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class BlockInvisibleCrop extends BlockCrops {
    
    @SideOnly(Side.CLIENT)
    private IIcon[] growthIcons;  // 现在只需要3个纹理
    
    public BlockInvisibleCrop() {
        super();
        this.setBlockName("invisible_crop");
        this.setBlockTextureName("unseenfruit:invisible_crop");
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
    }
    
    // 获取种子物品
    @Override
    protected Item func_149866_i() {
        return UnseenFruit.instance.invisibleSeed;
    }
    
    // 获取果实物品
    @Override
    protected Item func_149865_P() {
        return UnseenFruit.instance.unseenFruit;
    }
    
    // ========== 关键修改：8阶段映射到3张纹理 ==========
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
        // 只注册3个纹理，对应3个生长阶段
        this.growthIcons = new IIcon[3];
        
        this.growthIcons[0] = register.registerIcon("unseenfruit:invisible_crop_stage_0");  // 萌芽期
        this.growthIcons[1] = register.registerIcon("unseenfruit:invisible_crop_stage_4");  // 生长期
        this.growthIcons[2] = register.registerIcon("unseenfruit:invisible_crop_stage_7");  // 成熟期
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata) {
        // 将8个生长阶段（0-7）映射到3张纹理上
        if (metadata <= 2) {
            // 阶段0-2 显示萌芽期纹理（stage_0）
            return this.growthIcons[0];
        } else if (metadata <= 5) {
            // 阶段3-5 显示生长期纹理（stage_4）
            return this.growthIcons[1];
        } else {
            // 阶段6-7 显示成熟期纹理（stage_7）
            return this.growthIcons[2];
        }
    }
    
    // ========== 收获逻辑（只在阶段7可收获） ==========
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                                     EntityPlayer player, int side, 
                                     float hitX, float hitY, float hitZ) {
        int meta = world.getBlockMetadata(x, y, z);
        
        // 只有阶段7（最终阶段）才能收获
        if (meta == 7) {
            if (!world.isRemote) {
                // 收获果实：1-3个
                int fruitCount = 1 + world.rand.nextInt(3);
                ItemStack fruitStack = new ItemStack(this.func_149865_P(), fruitCount);
                
                if (!player.inventory.addItemStackToInventory(fruitStack)) {
                    this.dropBlockAsItem(world, x, y, z, fruitStack);
                }
                
                // 30%几率掉落种子
                if (world.rand.nextFloat() < 0.3F) {
                    ItemStack seedStack = new ItemStack(this.func_149866_i(), 1);
                    if (!player.inventory.addItemStackToInventory(seedStack)) {
                        this.dropBlockAsItem(world, x, y, z, seedStack);
                    }
                }
                
                // 重置为阶段4（可再次生长，显示生长期纹理）
                world.setBlockMetadataWithNotify(x, y, z, 4, 2);
                
                // 播放收获音效
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, 
                                     "random.pop", 0.2F, 
                                     ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            }
            return true;
        }
        return false;
    }
    
    // ========== 破坏掉落逻辑 ==========
    
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, 
                                         int metadata, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        
        Item seed = this.func_149866_i();
        Item fruit = this.func_149865_P();
        
        if (metadata == 7) {
            // 成熟阶段：掉落果实
            int fruitCount = 1 + world.rand.nextInt(3);
            drops.add(new ItemStack(fruit, fruitCount));
            
            // 掉落种子
            int seedCount = 1 + world.rand.nextInt(2);
            drops.add(new ItemStack(seed, seedCount));
        } else if (metadata >= 4 && metadata <= 5) {
            // 生长期阶段：只掉落种子
            drops.add(new ItemStack(seed, 1));
        } else if (metadata >= 0 && metadata <= 2) {
            // 萌芽期：小几率掉落种子
            if (world.rand.nextFloat() < 0.3F) {
                drops.add(new ItemStack(seed, 1));
            }
        }
        
        return drops;
    }
    
    // ========== 骨粉效果 ==========
    
    @Override
    public void func_149853_b(World world, Random rand, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        int newMeta = meta + rand.nextInt(3) + 1;
        
        if (newMeta > 7) {
            newMeta = 7;
        }
        
        world.setBlockMetadataWithNotify(x, y, z, newMeta, 2);
    }
    
    // ========== 可选：生长速度调整 ==========
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        
        // 可以调整生长速度
        if (world.getBlockLightValue(x, y + 1, z) >= 9) {
            int meta = world.getBlockMetadata(x, y, z);
            
            if (meta < 7 && rand.nextInt(25) == 0) {
                world.setBlockMetadataWithNotify(x, y,
    @Override
    public void breakBlock(World world, int x, int y, int z, 
                       net.minecraft.block.Block block, int metadata) {
    // 播放破坏音效
    if (!world.isRemote) {
        world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, 
                             "unseenfruit.crop.break", 
                             0.8F, 1.2F);
    }
    super.breakBlock(world, x, y, z, block, metadata);
}