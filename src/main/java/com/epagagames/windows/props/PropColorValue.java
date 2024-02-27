package com.epagagames.windows.props;

import com.epagagames.particles.valuetypes.ColorValueType;
import com.epagagames.particles.valuetypes.Curve;
import com.epagagames.particles.valuetypes.ValueType;
import com.jme3.math.ColorRGBA;
import imgui.ImGui;
import imgui.type.ImInt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropColorValue extends PropBase{

  private Method set;
  private Method get;
  private String name = "DEF";
  private String[] modes = {"Constant", "Random", "Gradient", "Random Between", "Random Gradients"};
  private ImInt modeIndex = new ImInt();

  private float[] conColor = new float[4];
  private float[] secColor = new float[4];
  public PropColorValue(String name, Class c, String getMeth, String setMeth) {
    this.name = name;
    set = findMethod(c, setMeth, ColorValueType.class);
    get = findMethod(c, getMeth, ColorValueType.class);
  }


  @Override
  public void render(Object obj) {
    try {
      ColorValueType current = (ColorValueType) get.invoke(obj);
      modeIndex.set(current.getType().ordinal());
      if (current != null && current.getType() == ColorValueType.Type.CONSTANT) {
        ColorRGBA c = current.getColor();
        conColor[0] = c.r;
        conColor[1] = c.g;
        conColor[2] = c.b;
        conColor[3] = c.a;
      } else if (current != null && current.getType() == ColorValueType.Type.RANDOM_COLOR) {
      } else if (current != null && current.getType() == ColorValueType.Type.RANDOM_BETWEEN_COLORS) {
        ColorRGBA c = current.getColor();
        conColor[0] = c.r;
        conColor[1] = c.g;
        conColor[2] = c.b;
        conColor[3] = c.a;
        c = current.getColorTwo();
        secColor[0] = c.r;
        secColor[1] = c.g;
        secColor[2] = c.b;
        secColor[3] = c.a;
      } else if (current != null && current.getType() == ColorValueType.Type.GRADIENT) {
      } else if (current != null && current.getType() == ColorValueType.Type.RANDOM_BETWEEN_GRADIENTS) {
      }

      ImGui.columns(2);
      ImGui.setColumnWidth(0, COLUMN_WIDTH);
      ImGui.text(name);
      ImGui.newLine();
      ImGui.combo("Mode##" + name, modeIndex, modes);
      ImGui.nextColumn();
      if (current.getType() != ColorValueType.Type.values()[modeIndex.intValue()]) {
        var newMode = ColorValueType.Type.values()[modeIndex.intValue()];
        if (newMode == ColorValueType.Type.CONSTANT) current.setColor(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
        if (newMode == ColorValueType.Type.RANDOM_BETWEEN_COLORS) current.setColorRange(new ColorRGBA(0.0f, 0.0f, 0.0f, 1.0f), new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
        //if (newMode == ValueType.Type.CURVE) current.setCurve();
        //if (newMode == ValueType.Type.RANDOM_BETWEEN_CURVES) current.setBetweenCurves();
      }
      if (current.getType() == ColorValueType.Type.CONSTANT) {
        ImGui.colorEdit4("##" + name, conColor);
      } else if (current.getType() == ColorValueType.Type.RANDOM_BETWEEN_COLORS) {
        ImGui.colorEdit4("Min##" + name, conColor);
        ImGui.newLine();
        ImGui.colorEdit4("Max##" + name, secColor);
      }
      ImGui.columns(1);

      ImGui.separator();

      if (current != null && current.getType() == ColorValueType.Type.CONSTANT) {
        current.setColor(new ColorRGBA(conColor[0], conColor[1], conColor[2], conColor[3]));
      } else if (current != null && current.getType() == ColorValueType.Type.RANDOM_COLOR) {
      } else if (current != null && current.getType() == ColorValueType.Type.RANDOM_BETWEEN_COLORS) {
        current.setColorRange(new ColorRGBA(conColor[0], conColor[1], conColor[2], conColor[3]),
                         new ColorRGBA(secColor[0], secColor[1], secColor[2], secColor[3]));
      } else if (current != null && current.getType() == ColorValueType.Type.GRADIENT) {
      } else if (current != null && current.getType() == ColorValueType.Type.RANDOM_BETWEEN_GRADIENTS) {
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
}
