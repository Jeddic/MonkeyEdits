package com.epagagames.windows.props;

import com.jme3.scene.Spatial;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public abstract class PropBase {

  public static final int COLUMN_WIDTH = 150;

  public PropBase() {

  }

  protected Method findMethod(Class c, String name, Class paramType) {
    Method meth = null;
    for (Method child : c.getMethods()) {
      if (child.getName().equalsIgnoreCase(name) && child.getParameterCount() <= 1) {
        Parameter[] parameters = child.getParameters();
        if (parameters.length == 0 || parameters[0].getType() == paramType) {
          meth = child;
        }
      }
    }
    return meth;
  }

  public void render(Object obj) {

  }

}
