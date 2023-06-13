package io.github.JumperOnJava.lavajumper;

import io.github.JumperOnJava.autocfg.ConfigGenerator;
import io.github.JumperOnJava.lavajumper.common.ConfigTestClass;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
	public class LavaJumper implements ClientModInitializer {
		private static Logger DebugOutput = LoggerFactory.getLogger("LavaJumper");
		private static ConfigGenerator LavaJumperConfig;
		@Nullable
		public static ConfigGenerator getConfig(){
			var isYaclLoaded = FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
			if(!isYaclLoaded)
				return null;
			if(LavaJumperConfig==null){
				LavaJumperConfig = new ConfigGenerator("LavaJumper");
				LavaJumperConfig.restoreConfig();
			}
			return LavaJumperConfig;
		}
		static {

		}
		@Override
		public void onInitializeClient() {
			if (FabricLoader.getInstance().isDevelopmentEnvironment())
				new ConfigTestClass();
			log("LavaJumper Initialized");
		}

		/**
		 * Prints array of objects to console
		 * @param objects
		 */
		public static void log(Object... objects) {
			var string = "";
			for (Object arg : objects) {
				if (arg == null)
					arg = "null";
				string = string + arg;

			}
			DebugOutput.info(string);
		}
		public static void testLog(Object... objects) {
			var string = "";
			for (Object arg : objects) {
				if (arg == null)
					arg = "null";
				string = string + arg;

			}
			System.out.println(string);
		}
	}