package io.github.javajumper.lavajumper.common.actiontext;

import net.minecraft.util.math.ColorHelper;

public class ActionText {
    public ActionText(String text)
    {
        this.text = text;
    }
    public String text;
    public float time = 80;
    public int color = ColorHelper.Argb.getArgb(255,255,255,255);
}
