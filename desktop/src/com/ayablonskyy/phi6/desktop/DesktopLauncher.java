package com.ayablonskyy.phi6.desktop;

import com.ayablonskyy.phi6.Phi6;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1500;
		config.height = 1300;
		new LwjglApplication(new Phi6(), config);
	}
}
