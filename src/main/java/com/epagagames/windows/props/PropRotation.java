package com.epagagames.windows.props;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.type.ImFloat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropRotation extends PropBase {

  private Method get;
  private Method set;
  private String name;
  private ImFloat x = new ImFloat();
  private ImFloat y = new ImFloat();
  private ImFloat z = new ImFloat();
  private Vector3f temp = new Vector3f();
  private Quaternion tempQuat = new Quaternion();
  private float[] tempAngles = new float[3];

  private boolean degrees = true;

  public PropRotation(String name, Class c, String get, String set) {
    this.name = name;
    this.get = findMethod(c, get, Quaternion.class);
    this.set = findMethod(c, set, Quaternion.class);
  }

  @Override
  public void render(Object obj) {
    try {
      Quaternion current = (Quaternion) get.invoke(obj);
      current.toAngles(tempAngles);
      x.set((float) Math.toDegrees(tempAngles[0]));
      y.set((float) Math.toDegrees(tempAngles[1]));
      z.set((float) Math.toDegrees(tempAngles[2]));


      ImGui.columns(2);
      ImGui.setColumnWidth(0, COLUMN_WIDTH);
      ImGui.text(name);
      ImGui.nextColumn();
      ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 0.0f, 0.0f);

      float lineHeight = ImGui.getFont().getFontSize() + ImGui.getStyle().getFramePaddingY();
      ImVec2 buttonSize = new ImVec2(lineHeight + 3.0f, lineHeight + 3.0f);
      ImVec2 contentRegion = ImGui.getContentRegionAvail();
      float itemWidth = (contentRegion.x - buttonSize.x * 3)/3;

      ImGui.pushStyleColor(ImGuiCol.Button, 0.8f, 0.2f, 0.2f, 1.0f);
      if (ImGui.button("X", buttonSize.x, buttonSize.y)) {
        x.set(0.0f);
      }
      ImGui.sameLine();
      ImGui.setNextItemWidth(itemWidth);
      ImGui.inputFloat("##x" + name, x);
      ImGui.popStyleColor();

      ImGui.sameLine();
      ImGui.pushStyleColor(ImGuiCol.Button, 0.2f, 0.8f, 0.2f, 1.0f);
      if (ImGui.button("Y", buttonSize.x, buttonSize.y)) {
        y.set(0.0f);
      }
      ImGui.sameLine();
      ImGui.setNextItemWidth(itemWidth);
      ImGui.inputFloat("##y" + name, y);
      ImGui.popStyleColor();

      ImGui.sameLine();
      ImGui.pushStyleColor(ImGuiCol.Button, 0.2f, 0.2f, 0.8f, 1.0f);
      if (ImGui.button("Z", buttonSize.x, buttonSize.y)) {
        z.set(0.0f);
      }
      ImGui.sameLine();
      ImGui.setNextItemWidth(itemWidth);
      ImGui.inputFloat("##z" + name, z);
      ImGui.popStyleColor();

      ImGui.columns(1);

      ImGui.popStyleVar(1);

      tempAngles[0] = (float) Math.toRadians(x.get());
      tempAngles[1] = (float) Math.toRadians(y.get());
      tempAngles[2] = (float) Math.toRadians(z.get());
      tempQuat.fromAngles(tempAngles);
      set.invoke(obj, tempQuat);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    }
  }
}
