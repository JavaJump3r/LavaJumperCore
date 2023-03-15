package io.github.javajumper.lavajumper;

import io.github.javajump3r.autocfg.ConfigGenerator;
import io.github.javajumper.lavajumper.common.ConfigTestClass;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
	@Environment(EnvType.CLIENT)
	public class LavaJumper implements ClientModInitializer {
		private static Logger DebugOutput = LoggerFactory.getLogger("LavaJumper");
		public static String dynmapId = "jjdmap";
		public static final String modid = "jjhud";
		public static ConfigGenerator LavaJumperConfig;
		private static boolean isInitialized = false;
		@Override
		public void onInitializeClient() {
			if(isInitialized)
				return;

			var config = new ConfigGenerator("LavaJumper");
			config.restoreConfig();
			LavaJumperConfig = config;
			if (FabricLoader.getInstance().isDevelopmentEnvironment())
				new ConfigTestClass();

			log("LavaJumper Initialized");
		}
		public void forceInit(){
			log("Calling force LavaJumper initialization");
			try{
				onInitializeClient();
			}
			catch (Exception e){
				throw e;
			}
			finally {
				isInitialized=true;
			}
		}
		public static void log(Object... objects) {
			var string = "";
			for (Object arg : objects) {
				if (arg == null)
					arg = "null";
				string = string + arg + ";";

			}
			DebugOutput.info(string);
		}
	}