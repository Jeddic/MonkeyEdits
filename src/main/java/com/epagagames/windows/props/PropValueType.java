package com.epagagames.windows.props;

import com.epagagames.particles.valuetypes.Curve;
import com.epagagames.particles.valuetypes.ValueType;
import com.jme3.math.Vector2f;
import imgui.ImGui;
import imgui.type.ImFloat;
import imgui.type.ImInt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropValueType extends PropBase {

  private Method set;
  private Method get;


  private ImFloat value = new ImFloat();
  private ImFloat valueSecond = new ImFloat();

  private CurveRender curveRender = new CurveRender();
  private CurveRender curveRenderMax = new CurveRender();
  private String name = "DEF";

  private String[] modes = {"Constant", "Random", "Curve", "Random Between Curve"};
  private ImInt modeIndex = new ImInt();
  public PropValueType(String name, Class c, String getMeth, String setMeth) {
    this.name = name;
    set = findMethod(c, setMeth, ValueType.class);
    get = findMethod(c, getMeth, ValueType.class);
  }

  @Override
  public void render(Object obj) {
    try {
      ValueType current = (ValueType) get.invoke(obj);
      modeIndex.set(current.getType().ordinal());
//      if (current != null && current.getType().ordinal() != modeIndex.intValue()) {
//        value.set(current);
//      }
      if (current != null && current.getType() == ValueType.Type.CONSTANT) {
        value.set(current.getValue());
      } else if (current != null && current.getType() == ValueType.Type.RANDOM) {
        value.set(current.getValue());
        valueSecond.set(current.getMax());
      }

      ImGui.columns(2);
      ImGui.setColumnWidth(0, COLUMN_WIDTH);
      ImGui.text(name);
      ImGui.newLine();
      ImGui.combo("Mode##" + name, modeIndex, modes);
      ImGui.nextColumn();
      if (current.getType() != ValueType.Type.values()[modeIndex.intValue()]) {
        var newMode = ValueType.Type.values()[modeIndex.intValue()];
        if (newMode == ValueType.Type.CONSTANT) current.setValue(1.0f);
        if (newMode == ValueType.Type.RANDOM) current.setMinMaxValue(0.0f, 1.0f);
        if (newMode == ValueType.Type.CURVE) {
          Curve c = new Curve();
          c.addControlPoint(new Vector2f(), new Vector2f(0.0f, 0.0f), new Vector2f(0.1f, 0.0f));
          c.addControlPoint(new Vector2f(.9f, 1.0f), new Vector2f(1.0f, 1.0f), new Vector2f());
          current.setCurve(c);
        }
        if (newMode == ValueType.Type.RANDOM_BETWEEN_CURVES) {
          Curve c = new Curve();
          c.addControlPoint(new Vector2f(), new Vector2f(0.0f, 0.0f), new Vector2f(0.1f, 0.0f));
          c.addControlPoint(new Vector2f(.9f, 0.0f), new Vector2f(1.0f, 0.0f), new Vector2f());
          Curve c2 = new Curve();
          c2.addControlPoint(new Vector2f(), new Vector2f(0.0f, 1.0f), new Vector2f(0.1f, 1.0f));
          c2.addControlPoint(new Vector2f(.9f, 1.0f), new Vector2f(1.0f, 1.0f), new Vector2f());
          current.setBetweenCurves(c, c2);
        }
        //if (newMode == ValueType.Type.RANDOM_BETWEEN_CURVES) current.setBetweenCurves();
      }
      if (current.getType() == ValueType.Type.CONSTANT) {
        ImGui.inputFloat("##" + name, value);
        if (current.getValue() != value.get()) {
          current.setValue(value.get());
        }
      } else if (current.getType() == ValueType.Type.RANDOM) {
        ImGui.inputFloat("##" + name + "Start", value);
        ImGui.newLine();
        ImGui.inputFloat("##" + name + "End", valueSecond);

      } else if (current.getType() == ValueType.Type.CURVE) {
        curveRender.render(current.getCurve());
      } else if (current.getType() == ValueType.Type.RANDOM_BETWEEN_CURVES) {
        ImGui.text("Min Curve");
        curveRender.render(current.getCurve());
        ImGui.text("Max Curve");
        curveRenderMax.render(current.getCurveMax());
      }
      ImGui.columns(1);
//      if (current != null && current.floatValue() != value.floatValue()) {
//        set.invoke(obj, value.get());
//      }

      ImGui.separator();

      if (current != null && current.getType() == ValueType.Type.CONSTANT) {
        current.setValue(value.get());
      } else if (current != null && current.getType() == ValueType.Type.RANDOM) {
        current.setMinMaxValue(value.get(), valueSecond.get());
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

}
