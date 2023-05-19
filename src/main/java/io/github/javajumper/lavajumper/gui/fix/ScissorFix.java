package io.github.javajumper.lavajumper.gui.fix;

import net.minecraft.client.util.math.MatrixStack;

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
