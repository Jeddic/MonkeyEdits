package com.epagagames.windows.props;

import imgui.ImGui;
import imgui.type.ImString;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropStr extends PropBase {

  private Method set;
  private Method get;


  private ImString value = new ImString();
  private String name = "DEF";
  public PropStr(String name, Class c, String getMeth, String setMeth) {
    this.name = name;
    set = findMethod(c, setMeth, String.class);
    get = findMethod(c, getMeth, String.class);
  }

  @Override
  public void render(Object obj) {
    try {
      String current = (String) get.invoke(obj);
      if (current != null && !current.equalsIgnoreCase(value.get())) {
        value.set(current);
      }
      ImGui.columns(2);
      ImGui.setColumnWidth(0, COLUMN_WIDTH);
      ImGui.text(name);
      ImGui.nextColumn();
      ImGui.inputText("##" + name, value);
      ImGui.columns(1);
      if (current != null && !current.equalsIgnoreCase(value.get())) {
        set.invoke(obj, value.get());
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

}
