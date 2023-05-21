package io.github.JumperOnJava.lavajumper.common;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3i;

import java.util.function.BiFunction;

@Deprecated
public class WorldActions {
    public static void foreachBlockAround(MinecraftClient client, BiFunction<MinecraftClient, Vec3i, Boolean> function)
    {
        if(foreachBlockAround(client,3,function))return;

        /*for(int i=0;i<=3;i++)
        {
        }*/
    }
    private static boolean foreachBlockAround(MinecraftClient client,int radius, BiFunction<MinecraftClient, Vec3i, Boolean> function) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    var playerpos = client.player.getPos();
                    var pos = new Vec3i((int) (playerpos.x + x), (int) (playerpos.y + y), (int) (playerpos.z + z));
                    if(function.apply(client,pos)) return true;
                }
            }
        }
        return false;
    }
}
