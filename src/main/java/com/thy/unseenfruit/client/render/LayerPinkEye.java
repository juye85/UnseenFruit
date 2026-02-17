package com.thy.unseenfruit.client.render;

import com.thy.unseenfruit.common.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LayerPinkEye implements LayerRenderer {
    
    private static final ResourceLocation PINK_EYES_BOTH = 
        new ResourceLocation("unseenfruit:textures/entity/pink_eyes_both.png");
    private static final ResourceLocation PINK_EYE_LEFT = 
        new ResourceLocation("unseenfruit:textures/entity/pink_eye_left.png");
    private static final ResourceLocation PINK_EYE_RIGHT = 
        new ResourceLocation("unseenfruit:textures/entity/pink_eye_right.png");
    
    private final RenderPlayer renderer;
    
    public LayerPinkEye(RenderPlayer renderer) {
        this.renderer = renderer;
    }
    
    @Override
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, 
                              float limbSwingAmount, float partialTicks, 
                              float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        
        if (!player.isPotionActive(CommonProxy.pinkEye)) return;
        
        int amplifier = player.getActivePotionEffect(CommonProxy.pinkEye).getAmplifier();
        
        ResourceLocation texture;
        if (amplifier == 1) {
            boolean leftEye = (player.getEntityId() % 2) == 0;
            texture = leftEye ? PINK_EYE_LEFT : PINK_EYE_RIGHT;
        } else {
            texture = PINK_EYES_BOTH;
        }
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        this.renderer.getMainModel().bipedHead.render(scale);
        
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
