package com.epagagames.windows.props;

import com.jme3.math.Vector3f;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.type.ImFloat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropVec3 extends PropBase {

  private Method get;
  private Method set;
  private String name;
  private ImFloat x = new ImFloat();
  private ImFloat y = new ImFloat();
  private ImFloat z = new ImFloat();
  private Vector3f temp = new Vector3f();

  public PropVec3(String name, Class c, String get, String set) {
    this.name = name;
    this.get = findMethod(c, get, Vector3f.class);
    this.set = findMethod(c, set, Vector3f.class);
  }

  @Override
  public void render(Object obj) {
    try {
      Vector3f current = (Vector3f) get.invoke(obj);
      x.set(current.x);
      y.set(current.y);
      z.set(current.z);

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

      temp.set(x.get(), y.get(), z.get());
      set.invoke(obj, temp);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    }
  }
}
