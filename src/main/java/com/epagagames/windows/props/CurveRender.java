package com.epagagames.windows.props;

import com.epagagames.particles.valuetypes.Curve;
import com.jme3.math.Vector2f;
import imgui.ImGui;

import java.util.LinkedList;
import java.util.List;

public class CurveRender {

  private List<ControlPointRender> points = new LinkedList<>();

  public CurveRender() {

  }

  public void render(Curve curve) {
    if (curve.getControlPoints().size() != points.size()) {
      points.clear();
      for (int i = 0; i < curve.getControlPoints().size(); i++) {
        points.add(new ControlPointRender());
      }
    }

    int removedIndex = -1;
    for (int i = 0; i < curve.getControlPoints().size(); i++) {
      points.get(i).render(curve.getControlPoints().get(i), i);
      ImGui.sameLine();
      if (ImGui.button("-")) {
        removedIndex = i;
      }
    }

    if (removedIndex != -1) {
      curve.getControlPoints().remove(removedIndex);
    }

    // add a blank point to end
    if (ImGui.button("+")) {
      curve.addControlPoint(new Vector2f(), new Vector2f(1.0f, 0.0f), new Vector2f());
    }

  }

}
