package com.epagagames.windows;

import com.epagagames.AppState;
import com.epagagames.particles.Emitter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImBoolean;

public class SceneWindow {


  private ImBoolean showSceneWindow = new ImBoolean(true);

  private void renderTree(AppState state, Spatial currentNode) {
    boolean selected = state.selectedNode == currentNode;
    boolean leafNode = (currentNode instanceof Node) ? false : true;
    int flags = ImGuiTreeNodeFlags.FramePadding |
                               ImGuiTreeNodeFlags.OpenOnArrow |
        (selected ? ImGuiTreeNodeFlags.Selected : 0) |
        (leafNode ? ImGuiTreeNodeFlags.Leaf : 0);
    long id = 0;
    boolean opened = ImGui.treeNodeEx(id, flags, currentNode.getName());

    if (ImGui.isItemClicked()) {
      state.selectedNode = currentNode;
    }

    if (ImGui.beginPopupContextItem()){
      if (ImGui.beginMenu ("Add")){
        if (ImGui.menuItem ("Node")){
          if (state.selectedNode != null && state.selectedNode instanceof Node p) {
            p.attachChild(new Node("Node"));
          }
        }
        if (ImGui.menuItem ("Emitter")){
          if (state.selectedNode != null && state.selectedNode instanceof Node p) {
            Emitter emitter = state.application.createDefaultEmitter();
            p.attachChild(emitter);
          }
        }
        ImGui.endMenu();
      }
      if (ImGui.menuItem("Remove")) {
        if (state.selectedNode != null) {
          state.selectedNode.removeFromParent();
          state.selectedNode = null;
        }
      }
      ImGui.endPopup();
    }

    if (opened) {
      if (currentNode instanceof Node node) {
        for (var child : node.getChildren()) {
          renderTree(state, child);
        }
      }
      ImGui.treePop();
    }

  }

  public void render(AppState state) {
    ImGui.setNextWindowPos(10.0f, 10.0f, ImGuiCond.FirstUseEver);
    ImGui.setNextWindowSize(200.0f, 400.0f, ImGuiCond.FirstUseEver);
    if (ImGui.begin("Scene Viewer", showSceneWindow)) {
      renderTree(state, state.rootNode);
    }
    ImGui.end();

  }
}
