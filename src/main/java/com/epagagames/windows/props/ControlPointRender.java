package com.epagagames.windows.props;

import com.epagagames.particles.valuetypes.ControlPoint;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.type.ImFloat;

public class ControlPointRender {

  private ImFloat valX = new ImFloat();
  private ImFloat valY = new ImFloat();
  private ImFloat valXIn = new ImFloat();
  private ImFloat valYIn = new ImFloat();
  private ImFloat valXOut = new ImFloat();
  private ImFloat valYOut = new ImFloat();

  public ControlPointRender() {

  }

  public void render(ControlPoint controlPoint, int index) {
    ImGui.pushID("##" + index);
    valXIn.set(controlPoint.inControlPoint.x);
    valYIn.set(controlPoint.inControlPoint.y);
    valX.set(controlPoint.point.x);
    valY.set(controlPoint.point.y);
    valXOut.set(controlPoint.outControlPoint.x);
    valYOut.set(controlPoint.outControlPoint.y);


    float lineHeight = ImGui.getFont().getFontSize() + ImGui.getStyle().getFramePaddingY();
    ImVec2 buttonSize = new ImVec2(lineHeight + 3.0f, lineHeight + 3.0f);
    ImVec2 contentRegion = ImGui.getContentRegionAvail();
    float itemWidth = (contentRegion.x)/7;

    ImGui.setNextItemWidth(itemWidth);
    ImGui.inputFloat("##valXIn", valXIn);
    ImGui.sameLine();
    ImGui.setNextItemWidth(itemWidth);
    ImGui.inputFloat("##valYIn", valYIn);
    ImGui.sameLine();
    ImGui.setNextItemWidth(itemWidth);
    ImGui.inputFloat("##valX", valX);
    ImGui.sameLine();
    ImGui.setNextItemWidth(itemWidth);
    ImGui.inputFloat("##valY", valY);
    ImGui.sameLine();
    ImGui.setNextItemWidth(itemWidth);
    ImGui.inputFloat("##valXOut", valXOut);
    ImGui.sameLine();
    ImGui.setNextItemWidth(itemWidth);
    ImGui.inputFloat("##valYOut", valYOut);

    controlPoint.inControlPoint.x = valXIn.get();
    controlPoint.inControlPoint.y = valYIn.get();
    controlPoint.point.x = valX.get();
    controlPoint.point.y = valY.get();
    controlPoint.outControlPoint.x = valXOut.get();
    controlPoint.outControlPoint.y = valYOut.get();

    ImGui.popID();
  }
}
