package io.github.javajumper.lavajumper.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;
import org.joml.Vector4f;

import java.util.Random;

import static net.minecraft.client.gui.DrawableHelper.fill;

public class GuiHelper {
    public static void beginScissor(double x, double y, double endX, double endY)
    {
        var client = MinecraftClient.getInstance();
        double width = endX - x;
        double height = endY - y;
        width = Math.max(0, width);
        height = Math.max(0, height);
        float d = (float) client.getWindow().getScaleFactor();
        int ay = (int) ((client.getWindow().getScaledHeight() - (y + height)) * d);
        RenderSystem.enableScissor((int) (x * d), ay, (int) (width * d), (int) (height * d));
    }
    public static void beginMatrixScissor(MatrixStack matrixStack,double x, double y, double endX, double endY){
        var pos1 = matrixStack.peek().getPositionMatrix().transform(new Vector4f((float) x, (float) y,0,1));
        var pos2 = matrixStack.peek().getPositionMatrix().transform(new Vector4f((float) endX, (float) endY,0,1));
        GuiHelper.beginScissor(pos1.x,pos1.y,pos2.x,pos2.y);
    }
    public static void endScissor(){
        RenderSystem.disableScissor();
    }
    public static void renderSides(MatrixStack matrices, int x, int y, int width, int height)
    {
        y-=1;
        height+=1;
        fill(matrices, x-7, y-7, x + width+7, y + height+7, 0xFF000000);
        fill(matrices, x-6, y-6, x + width+6, y + height+6, 0xFFC6C6C6);
        fill(matrices, x-6, y-6, x + width+4, y + height+4, 0xFFFFFFFF);
        fill(matrices, x-4, y-4, x + width+6, y + height+6, 0xFF555555);
        fill(matrices, x-4, y-4, x + width+4, y + height+4, 0xFFC6C6C6);
        /*RenderSystem.enableBlend();
        fill(matrices,x,y,x+width,y+height,0x3F000000 | (int)(Math.pow(x + width + y + height,5f)%Integer.MAX_VALUE));*/
    }
    public static TestScreen TestScreen(int color){
        return new TestScreen(color);
    }
    public static class TestScreen extends Screen {
        int color;
        public TestScreen(int color) {
            super(Text.empty());
            this.color=color;
        }
        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            super.render(matrices, mouseX, mouseY, delta);
            fill(matrices,0,0,width,height,color);
        }
    }
}
