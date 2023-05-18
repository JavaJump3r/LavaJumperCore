package io.github.javajumper.lavajumper.gui;

import net.minecraft.util.math.MathHelper;

public class HoverManager {
    public final float maxHoverTime;
    public float hoverTime;
    public HoverManager(float maxHoverTime){
        this.maxHoverTime=maxHoverTime;
    }
    public void tickHover(boolean isMouseHovered,float delta){
        hoverTime+=delta * (isMouseHovered?1:-2);
        hoverTime= MathHelper.clamp(hoverTime,0,maxHoverTime);
    }
    public float getHoverProgress(){
        return Math.min(hoverTime/maxHoverTime,1);
    }
}
