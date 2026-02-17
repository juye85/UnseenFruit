package com.thy.unseenfruit.client;

import com.thy.unseenfruit.client.render.LayerPinkEye;
import com.thy.unseenfruit.common.CommonProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }
    
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        
        RenderPlayer renderDefault = (RenderPlayer) Minecraft.getMinecraft().getRenderManager().skinMap.get("default");
        RenderPlayer renderSlim = (RenderPlayer) Minecraft.getMinecraft().getRenderManager().skinMap.get("slim");
        
        renderDefault.addLayer(new LayerPinkEye(renderDefault));
        renderSlim.addLayer(new LayerPinkEye(renderSlim));
    }
    
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
