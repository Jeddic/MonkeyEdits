package com.epagagames;

import com.jme3.system.AppSettings;

public class Main {
  public static void main(String[] args) {
    MonkeyEdits me = new MonkeyEdits();
    AppSettings settings = new AppSettings(true);
    me.setShowSettings(false);
    settings.setTitle("Monkey Edits");
    settings.setVSync(false);
    settings.setFullscreen(false);
    //app.setDisplayStatView(true);
    settings.setResolution(1920, 1080);
    settings.setRenderer(AppSettings.LWJGL_OPENGL33);
    me.setSettings(settings);
    me.start();
  }
}