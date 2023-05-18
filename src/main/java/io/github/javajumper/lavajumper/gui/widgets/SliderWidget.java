package io.github.javajumper.lavajumper.gui.widgets;

import net.minecraft.text.Text;

import java.util.function.Consumer;

public class SliderWidget extends net.minecraft.client.gui.widget.SliderWidget {
    private final int round;
    private Consumer<Double> changedListener;
    private Text text;
    public double min, max;

    public SliderWidget(int x, int y, int width, int height, Text text, double minValue, double maxValue, double value,int round) {
        super(x, y, width, height, text, value);
        this.min=minValue;
        this.max=maxValue;
        this.text=text;
        this.round=round;
        super.setValue(scaleValueTo01(value));
        setMessage(text);
    }
    public SliderWidget(int x, int y, int width, int height, Text text, double maxValue, double value,int round){
        this(x, y, width, height, text,0,maxValue, value,round);
    }

    @Override
    protected void updateMessage() {
    }
    public void setText(Text text){
        this.text=text;
        updateMessage();
    }
    public Text getText(){
        return text;
    }

    @Override
    public Text getMessage() {
        return this.text.copy().append(Text.literal(" "+round(scaleValueFrom01(value))));
    }
    public double round(double value){
        return ((float)Math.round(value/round))*round;
    }
    @Override
    protected void applyValue() {
        if(changedListener==null)
            return;
        changedListener.accept(round(scaleValueFrom01(value)));
    }
    private double scaleValueFrom01(double value){
        return value*(max-min)+min;
    }
    private double scaleValueTo01(double value){
        return (value-min)/(max-min);
    }

    public void setChangedListener(Consumer<Double> changedListener) {
        this.changedListener = changedListener;
    }
}
