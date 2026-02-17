package com.thy.unseenfruit.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = 
        NetworkRegistry.INSTANCE.newSimpleChannel("unseenfruit");
    
    public static void init() {
        // 注册数据包（需要时添加）
    }
}