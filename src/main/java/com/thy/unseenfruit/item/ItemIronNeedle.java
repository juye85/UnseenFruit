package com.thy.unseenfruit.item;

import com.thy.unseenfruit.common.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemIronNeedle extends Item {
    
    public ItemIronNeedle() {
        this.setUnlocalizedName("iron_needle");
        this.setTextureName("unseenfruit:iron_needle");
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setMaxStackSize(16);
        this.setMaxDamage(64);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote && player.isSneaking()) {
            if (player.isPotionActive(CommonProxy.pinkEye)) {
                player.removePotionEffect(CommonProxy.pinkEye.id);
                player.addPotionEffect(new PotionEffect(net.minecraft.potion.Potion.nightVision.id, 60, 0));
                world.playSoundAtEntity(player, "mob.slime.small", 1.0f, 0.5f);
                player.attackEntityFrom(net.minecraft.util.DamageSource.generic, 2);
                stack.damageItem(1, player);
            }
        }
        return stack;
    }
}
