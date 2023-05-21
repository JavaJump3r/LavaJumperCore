package io.github.JumperOnJava.lavajumper.gui.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec2f;

import java.util.LinkedList;
import java.util.List;

/**
 * Pizza menu widget
 * Should be set up by using setupSize and setupSlices methods to work properly.
 */
public class PizzaWidget implements Drawable, Element, Selectable {
    private final LinkedList<PizzaWidgetSlice> slices = new LinkedList<>();
    public float radius;
    public float innerRadius;
    public int x,y;

    /**
     * Creates Pizza widget for slice list
     * @param radius Radius of pizza
     * @param x x coordinate of pizza center
     * @param y y coordinate of pizza center
     */
    public PizzaWidget setupSize(int radius,int innerRadius, int x, int y) {
        this.radius=radius;
        this.innerRadius=innerRadius;
        this.x=x;
        this.y=y;
        return this;
    }
    public PizzaWidget setupSlices(List<? extends PizzaSlice> slices){
        this.slices.clear();
        for(var slice : slices){
            this.slices.add(new PizzaWidgetSlice(slice,this));
        }
        return this;
    }
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta){
        matrixStack.push();
        //ActionTextRenderer.sendChatMessag*e("x: ",mouseX," y: ",mouseY);
        var prof = MinecraftClient.getInstance().getProfiler();
        prof.push("Pizza");
        matrixStack.translate(x, y,0);
        slices.forEach(slice->{
            slice.render(matrixStack,mouseX-x,mouseY-y,delta);
        });
        slices.forEach(slice->{
            slice.renderText(matrixStack);
        });
        slices.forEach(slice->{
            slice.renderIcons(matrixStack);
        });
        prof.pop();
        matrixStack.pop();

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean cond = false;
        for(var slice:new LinkedList<>(slices)){
            cond = cond || slice.mouseClicked(mouseX-x,mouseY-y,button);
        }
        return cond;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        boolean cond = false;
        for(var slice:new LinkedList<>(slices)  ){
            cond = cond || slice.mouseScrolled(mouseX-x,mouseY-y,amount);
        }
        return cond;
    }
    @Override
    public SelectionType getType() {
        return SelectionType.HOVERED;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        var center = new Vec2f(x,y);
        var mouse = new Vec2f((float) mouseX, (float) mouseY);
        var distanceSq = center.distanceSquared(mouse);
        return distanceSq < radius*radius && distanceSq > innerRadius*innerRadius;
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    public boolean isMouseOverRel(double mouseX, double mouseY){
        return isMouseOver(mouseX+x,mouseY+y);
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }
}
