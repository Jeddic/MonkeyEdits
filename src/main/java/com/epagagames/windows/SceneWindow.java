package com.epagagames.windows;

import com.epagagames.AppState;
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
    ImGui.setNextWindowPos(10.0f, 10.0f, ImGuiCond.Once);
    ImGui.setNextWindowSize(200.0f, 400.0f, ImGuiCond.Once);
    if (ImGui.begin("Scene Viewer", showSceneWindow)) {
      renderTree(state, state.rootNode);
    }
    ImGui.end();

  }
}
