package com.epagagames.states;

import com.epagagames.AppState;
import com.epagagames.MonkeyEdits;
import com.epagagames.particles.Emitter;
import com.epagagames.windows.PropertyWindow;
import com.epagagames.windows.SceneWindow;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.export.JmeExporter;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.lwjgl.LwjglWindow;
import imgui.ImGui;
import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.callback.ImGuiFileDialogPaneFun;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

public class EditorGui extends AbstractAppState {
  private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
  private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

  private String glslVersion = null;
  private ImBoolean showCanvaWindow = new ImBoolean(true);

  /**
   * Background color of the window.
   */
  protected final Color colorBg = new Color(.5f, .5f, .5f, 1);
  /**
   * Pointer to the native GLFW window.
   */
  protected long handle;

  private AppState state;

  private SceneWindow sceneWindow;
  private PropertyWindow propertyWindow;

  private ImBoolean debugViewCheck = new ImBoolean(false);

  private SimpleApplication application;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    application = (SimpleApplication)app;
    LwjglWindow lwjglContext = (LwjglWindow) app.getContext();
    handle = lwjglContext.getWindowHandle();
    ImGui.createContext();
    ImGui.getIO().setIniFilename("editor.ini");
    ImGui.getIO().addConfigFlags(ImGuiConfigFlags.DockingEnable);

    // Needed for OSX to compile
    glslVersion = "#version 330 core";
    imGuiGlfw.init(handle, true);
    imGuiGl3.init(glslVersion);


    state = new AppState();
    state.rootNode = ((SimpleApplication)app).getRootNode();
    state.application = (MonkeyEdits)app;

    sceneWindow = new SceneWindow();
    propertyWindow = new PropertyWindow();
  }

  @Override
  public void cleanup() {
    super.cleanup();

    imGuiGl3.dispose();
    imGuiGlfw.dispose();

    ImGui.destroyContext();
  }

  @Override
  public void update(float tpf) {
  }

  private static ImGuiFileDialogPaneFun callback = new ImGuiFileDialogPaneFun() {
    @Override
    public void paneFun(String filter, long userDatas, boolean canContinue) {
      ImGui.text("Filter: " + filter);
    }
  };

  private void saveFile(Spatial spatial, String filename) {
      JmeExporter exporter = BinaryExporter.getInstance();
      File file = new File(filename);
      try {
        exporter.save(spatial, file);
      } catch (IOException exception) {
        //logger.log(Level.SEVERE, "write to {0} failed", filePath);
      }
  }

  private Spatial loadFile(String filename) {
    AssetManager manager = state.application.getAssetManager();
    manager.registerLocator(".", FileLocator.class);
    Spatial object = manager.loadModel(filename);
    return object;
  }

  @Override
  public void postRender() {

    // start
    //clearBuffer();
    imGuiGlfw.newFrame();
    ImGui.newFrame();

    ImGui.dockSpaceOverViewport(ImGui.getMainViewport(), ImGuiDockNodeFlags.PassthruCentralNode);

    ImGui.beginMainMenuBar();
    if(ImGui.beginMenu("File")) {
      if (ImGui.menuItem("New")) {
        state.rootNode.detachAllChildren();
        state.rootNode.attachChild(state.application.createDefaultEmitter());
        state.loadedFile = "";
      }
      ImGui.beginDisabled(state.loadedFile.isEmpty());
      if (ImGui.menuItem("Save")) {
        saveFile(application.getRootNode(), state.loadedFile);
      }
      ImGui.endDisabled();
      if (ImGui.menuItem("Save As")) {
        ImGuiFileDialog.openModal("save-key", "Save As File", ".j3o", ".", callback, 250, 1, 42, ImGuiFileDialogFlags.None);
      }
      if (ImGui.menuItem("Load")) {
        ImGuiFileDialog.openModal("browse-key", "Load File", ".j3o", ".", callback, 250, 1, 42, ImGuiFileDialogFlags.None);
      }

      ImGui.endMenu();
    }
    if (ImGui.beginMenu("View")) {
      if (ImGui.menuItem("Debug Shapes", "", debugViewCheck)) {
          Node rootNode = application.getRootNode();
          rootNode.breadthFirstTraversal(spatial -> {
            if (spatial instanceof Emitter emitter) {
              emitter.setDebug(application.getAssetManager(), debugViewCheck.get(), false);
            }
          });
      }
      ImGui.endMenu();
    }
    ImGui.endMainMenuBar();

    if (ImGuiFileDialog.display("browse-key", ImGuiFileDialogFlags.None, 200, 400, 800, 600)) {
      if (ImGuiFileDialog.isOk()) {
        String filename = ImGuiFileDialog.getCurrentFileName();
        HashMap<String, String> selection = ImGuiFileDialog.getSelection();
        long userData = ImGuiFileDialog.getUserDatas();
        int red5 = 0;
        state.rootNode.detachAllChildren();
        state.loadedFile = filename;
        Spatial loaded = loadFile(filename);
        state.rootNode.attachChild(loaded);
      }
      ImGuiFileDialog.close();
    }
    if (ImGuiFileDialog.display("save-key", ImGuiFileDialogFlags.None, 200, 400, 800, 600)) {
      if (ImGuiFileDialog.isOk()) {
        String filename = ImGuiFileDialog.getCurrentFileName();
        HashMap<String, String> selection = ImGuiFileDialog.getSelection();
        long userData = ImGuiFileDialog.getUserDatas();
        int red5 = 0;
        saveFile(application.getRootNode(), filename);
      }
      ImGuiFileDialog.close();
    }

    sceneWindow.render(state);
    propertyWindow.render(state);

    // end frame
    ImGui.render();
    imGuiGl3.renderDrawData(ImGui.getDrawData());
  }
}
