package io.github.JumperOnJava.lavajumper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
	public class LavaJumper implements ClientModInitializer {
		private static Logger DebugOutput = LoggerFactory.getLogger("LavaJumper");
		static {
		}
		@Override
		public void onInitializeClient() {
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