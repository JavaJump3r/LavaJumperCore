package io.github.JumperOnJava.lavajumper.common;

import io.github.JumperOnJava.lavajumper.LavaJumper;

/**
 * Used for my own purposes. Extend this if you want to add your class config to my shared configuration screen of my personal mods
 */
public abstract class Feature {
    public Feature()
    {
        LavaJumper.getConfig().addClassToConfig(this.getClass());
    }
}
