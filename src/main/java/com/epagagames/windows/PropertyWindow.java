package com.epagagames.windows;

import com.epagagames.AppState;
import com.epagagames.windows.propwrappers.MonkeyEmitterWrap;
import com.epagagames.windows.propwrappers.SpatialWrap;
import com.epagagames.windows.propwrappers.WrapBase;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.type.ImBoolean;

import java.util.LinkedList;
import java.util.List;

public class PropertyWindow {

  private ImBoolean showSceneWindow = new ImBoolean(true);

  private List<WrapBase> wrapBases = new LinkedList<>();

  public PropertyWindow() {
    wrapBases.add(new SpatialWrap());
    wrapBases.add(new MonkeyEmitterWrap());
  }

  public void render(AppState state) {

    ImGui.setNextWindowPos(10.0f, 10.0f, ImGuiCond.Once);
    ImGui.setNextWindowSize(200.0f, 400.0f, ImGuiCond.Once);
    if (ImGui.begin("Properties", showSceneWindow)) {
      if (state.selectedNode != null) {
        for (WrapBase wrap : wrapBases) {
          wrap.render(state.selectedNode);
        }
      }
    }
    ImGui.end();
  }
}
