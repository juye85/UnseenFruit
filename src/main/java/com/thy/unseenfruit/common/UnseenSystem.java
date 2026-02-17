package com.thy.unseenfruit.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

import java.util.*;

public class UnseenSystem {
    private static final Map<UUID, Set<UUID>> unseenMap = new HashMap<>();
    
    public static void maintainUnseenState(EntityPlayer player, int amplifier) {
        Set<UUID> myUnseen = unseenMap.computeIfAbsent(player.getUniqueID(), k -> new HashSet<>());
        
        for (Object obj : player.worldObj.playerEntities) {
            EntityPlayer other = (EntityPlayer) obj;
            if (other == player) continue;
            
            boolean shouldSee = true;
            
            if (other.isPotionActive(CommonProxy.pinkEye)) {
                if (amplifier == 0) {
                    shouldSee = false;
                } else {
                    shouldSee = (player.ticksExisted / 40) % 2 == 0;
                }
            }
            
            if (!shouldSee) {
                myUnseen.add(other.getUniqueID());
                unseenMap.computeIfAbsent(other.getUniqueID(), k -> new HashSet<>()).add(player.getUniqueID());
            } else {
                myUnseen.remove(other.getUniqueID());
                Set<UUID> theirUnseen = unseenMap.get(other.getUniqueID());
                if (theirUnseen != null) theirUnseen.remove(player.getUniqueID());
            }
        }
    }
    
    public static void clearUnseenState(EntityPlayer player) {
        UUID myId = player.getUniqueID();
        Set<UUID> myUnseen = unseenMap.remove(myId);
        
        if (myUnseen != null) {
            for (UUID otherId : myUnseen) {
                Set<UUID> theirUnseen = unseenMap.get(otherId);
                if (theirUnseen != null) theirUnseen.remove(myId);
            }
        }
    }
    
    public static boolean canCollide(EntityPlayer a, EntityPlayer b) {
        Set<UUID> aUnseen = unseenMap.getOrDefault(a.getUniqueID(), Collections.emptySet());
        Set<UUID> bUnseen = unseenMap.getOrDefault(b.getUniqueID(), Collections.emptySet());
        return !aUnseen.contains(b.getUniqueID()) && !bUnseen.contains(a.getUniqueID());
    }
}
