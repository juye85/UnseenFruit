package com.thy.unseenfruit.item;

import com.thy.unseenfruit.common.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemUnseenFruit extends ItemFood {
    
    public ItemUnseenFruit() {
        super(4, 0.3f, false);  // 4点饱食度，30%饱和度
        this.setUnlocalizedName("unseen_fruit");
        this.setTextureName("unseenfruit:unseen_fruit");
        this.setCreativeTab(net.minecraft.creativetab.CreativeTabs.tabFood);
        this.setAlwaysEdible();
    }
    
    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
    if (!world.isRemote) {
        int amplifier = player.getRNG().nextFloat() < 0.05f ? 1 : 0;
        player.addPotionEffect(new PotionEffect(CommonProxy.pinkEye.id, 6000, amplifier));
        
        // 播放食用音效
        world.playSoundAtEntity(player, "unseenfruit.unseen_fruit_eat", 
                               0.5F, 1.0F + world.rand.nextFloat() * 0.2F);
    }
}
