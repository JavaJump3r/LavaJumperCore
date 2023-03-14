package io.github.javajumper.lavajumper.common;

import io.github.javajumper.lavajumper.LavaJumper;

public abstract class Feature {
    public Feature()
    {
        LavaJumper.LavaJumperConfig.addClassToConfig(this.getClass());
    }
}
