package io.github.javajumper.lavajumper.common;

import io.github.javajumper.lavajumper.LavaJumper;

/**
 * Used for my own purposes. Extend this if you want to add your class config to my shared configuration screen of my personal mods
 */
public abstract class Feature {
    public Feature()
    {
        LavaJumper.LavaJumperConfig.addClassToConfig(this.getClass());
    }
}
