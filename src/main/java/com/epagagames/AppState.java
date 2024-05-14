package com.epagagames;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class AppState {
  public Node rootNode;
  public Spatial selectedNode;

  public MonkeyEdits application;
  public String loadedFile = "";
  public String rootAssetDirectory = ".";

  public AppState() {
    rootNode = null;
    selectedNode = null;
  }
}
