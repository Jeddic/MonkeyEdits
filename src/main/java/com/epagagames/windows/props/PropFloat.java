package com.epagagames.windows.props;

import imgui.ImGui;
import imgui.type.ImFloat;
import imgui.type.ImInt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropFloat extends PropBase {

  private Method set;
  private Method get;


  private ImFloat value = new ImFloat();
  private String name = "DEF";
  public PropFloat(String name, Class c, String getMeth, String setMeth) {
    this.name = name;
    set = findMethod(c, setMeth, float.class);
    get = findMethod(c, getMeth, float.class);
  }

  @Override
  public void render(Object obj) {
    try {
      Float current = (Float) get.invoke(obj);
      if (current != null && current.floatValue() != value.floatValue()) {
        value.set(current);
      }
      ImGui.columns(2);
      ImGui.setColumnWidth(0, COLUMN_WIDTH);
      ImGui.text(name);
      ImGui.nextColumn();
      ImGui.inputFloat("##" + name, value);
      ImGui.columns(1);
      if (current != null && current.floatValue() != value.floatValue()) {
        set.invoke(obj, value.get());
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

}
