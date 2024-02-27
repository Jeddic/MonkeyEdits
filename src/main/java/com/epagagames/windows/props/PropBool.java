package com.epagagames.windows.props;

import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropBool extends PropBase {

  private Method set;
  private Method get;


  private ImBoolean value = new ImBoolean();
  private String name = "DEF";
  public PropBool(String name, Class c, String getMeth, String setMeth) {
    this.name = name;
    set = findMethod(c, setMeth, boolean.class);
    get = findMethod(c, getMeth, boolean.class);
  }

  @Override
  public void render(Object obj) {
    try {
      boolean current = (boolean) get.invoke(obj);
      if (current != value.get()) {
        value.set(current);
      }
      ImGui.columns(2);
      ImGui.setColumnWidth(0, COLUMN_WIDTH);
      ImGui.text(name);
      ImGui.nextColumn();
      ImGui.checkbox("##" + name, value);
      ImGui.columns(1);
      if (current != value.get()) {
        set.invoke(obj, value.get());
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

}
