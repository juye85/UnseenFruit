package com.thy.unseenfruit.effect;

import com.thy.unseenfruit.common.CommonProxy;
import com.thy.unseenfruit.common.UnseenSystem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class EffectPinkEye extends Potion {
    
    public EffectPinkEye(int id, boolean isBadEffect, int color) {
        super(id, isBadEffect, color);
    }
    
    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (entity instanceof EntityPlayer) {
            UnseenSystem.maintainUnseenState((EntityPlayer)entity, amplifier);
        }
    }
    
    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
    
    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entity, 
                                                    net.minecraft.entity.ai.attributes.BaseAttributeMap map, 
                                                    int amplifier) {
        super.removeAttributesModifiersFromEntity(entity, map, amplifier);
        
        if (entity instanceof EntityPlayer) {
            UnseenSystem.clearUnseenState((EntityPlayer)entity);
            entity.addPotionEffect(new PotionEffect(Potion.nightVision.id, 60, 0));
        }
    }
}