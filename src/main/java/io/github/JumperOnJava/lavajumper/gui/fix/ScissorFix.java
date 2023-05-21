package io.github.JumperOnJava.lavajumper.gui.fix;

import net.minecraft.client.util.math.MatrixStack;

/**
 * Fix for scissors. For more info check io.github.JumperOnJava.lavajumper.mixin.ScissorMixin
 * Works only with drawablehelper scissors function
 */
public class ScissorFix {
    private static MatrixStack matrix;
    public static MatrixStack getMatrix() {
        if(matrix==null)
            setMatrix(new MatrixStack());
        return matrix;
    }
    public static void setMatrix(MatrixStack matrixStack){
        matrix=matrixStack;
    }
}
