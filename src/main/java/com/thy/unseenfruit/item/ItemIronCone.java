package com.thy.unseenfruit.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemIronCone extends Item {
    
    public ItemIronCone() {
        this.setUnlocalizedName("iron_cone");
        this.setTextureName("unseenfruit:iron_cone");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        this.setMaxStackSize(64);
    }
}
