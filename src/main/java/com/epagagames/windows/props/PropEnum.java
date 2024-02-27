package com.epagagames.windows.props;

import com.epagagames.particles.BillboardMode;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropEnum<T extends Enum> extends PropBase {

  private Method set;
  private Method get;


  private ImInt index = new ImInt();

  private T[] types;
  private String[] typeString;
  private String name = "DEF";
  public PropEnum(String name, Class c, Class<T> type, T[] typeList, String getMeth, String setMeth) {
    this.name = name;
    types = typeList;
    typeString = new String[typeList.length];

    for (int i=0; i < typeList.length; i++) {
      typeString[i] = typeList[i].name();
    }

    set = findMethod(c, setMeth, type);
    get = findMethod(c, getMeth, type);
  }

  @Override
  public void render(Object obj) {
    try {
      T current = (T) get.invoke(obj);
      if (current != types[index.get()]) {
        index.set(current.ordinal());
      }
      ImGui.columns(2);
      ImGui.setColumnWidth(0, COLUMN_WIDTH);
      ImGui.text(name);
      ImGui.nextColumn();
      ImGui.combo("##" + name, index, typeString);
      ImGui.columns(1);
      if (current != types[index.get()]) {
        set.invoke(obj, types[index.get()]);
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

}
