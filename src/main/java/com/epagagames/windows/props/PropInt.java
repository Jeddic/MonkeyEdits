package com.epagagames.windows.props;

import imgui.ImGui;
import imgui.type.ImInt;
import imgui.type.ImString;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropInt extends PropBase {

  private Method set;
  private Method get;


  private ImInt value = new ImInt();
  private String name = "DEF";
  public PropInt(String name, Class c, String getMeth, String setMeth) {
    this.name = name;
    set = findMethod(c, setMeth, int.class);
    get = findMethod(c, getMeth, int.class);
  }

  @Override
  public void render(Object obj) {
    try {
      Integer current = (Integer) get.invoke(obj);
      if (current != null && current.intValue() != value.intValue()) {
        value.set(current);
      }
      ImGui.columns(2);
      ImGui.setColumnWidth(0, COLUMN_WIDTH);
      ImGui.text(name);
      ImGui.nextColumn();
      ImGui.inputInt("##" + name, value);
      ImGui.columns(1);
      if (current != null && current.intValue() != value.intValue()) {
        set.invoke(obj, value.get());
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

}
