package io.github.javajumper.lavajumper.common.actiontext;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;

import java.util.LinkedList;
import java.util.List;

public class ActionTextRenderer {
    private static List<ActionText> text = new LinkedList<>();
    private static List<ActionText> addText = new LinkedList<>();
    static TextRenderer getTextRenderer(){return MinecraftClient.getInstance().textRenderer;};
    static {
        HudRenderCallback.EVENT.register((matrixStack,delta)->{
            text.addAll(addText);
            addText.clear();
            List<ActionText> removeText=new LinkedList<>();
            var iterator = text.listIterator(text.size());
            int i=0,j=0;
            while(iterator.hasPrevious())
            {
                var line=iterator.previous();
                if(line.time < 0)
                {
                    removeText.add(text.get(i));
                    continue;
                }
                else
                {
                    line.time-=delta;
                }
                if(ColorHelper.Argb.getAlpha(line.color)!=0){
                    renderActionText(matrixStack, line.text, -j, line.color);
                    j++;
                }
                i++;
            }
            text.removeAll(removeText);
        });
    }
    public static void addText(ActionText text)
    {
        ActionTextRenderer.addText.add(text);
    }
    public static void renderActionText(MatrixStack matrixStack, String text, int offset,int color)
    {
        DrawableHelper.drawCenteredText(
                matrixStack,
                MinecraftClient.getInstance().textRenderer,
                text,
                MinecraftClient.getInstance().getWindow().getScaledWidth()/2,
                (int) (MinecraftClient.getInstance().getWindow().getScaledHeight()/2*1.65 + offset*8),
                color);
    }
    public static void renderUpperRight(MatrixStack matrixStack, int offset, String text,int color)
    {

        var textWidth = getTextRenderer().getWidth(text);
        var x = MinecraftClient.getInstance().getWindow().getScaledWidth() - textWidth - 2;
        var y = 2;
        renderText(matrixStack,x,y+8*offset,text,color);
    }
    public static void renderText(MatrixStack matrixStack,int posX,int posY,String text,int color)
    {
        DrawableHelper.fill(matrixStack,posX-1,posY,posX+ getTextRenderer().getWidth(text),posY+8, ColorHelper.Argb.getArgb(64,0,0,0));
        if(getTextRenderer()== null)
        {
            return;
        }
        getTextRenderer().draw(matrixStack, text,
                posX,posY,
                color);
    }
    public static void sendChatMessage(Object... messages)
    {
        String fullMessage="";
        for(var message : messages)
            fullMessage+=(message==null)?"null": message +" ";
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal(fullMessage));
    }
}
