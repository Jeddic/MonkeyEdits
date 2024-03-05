package com.epagagames.windows.props;

import com.epagagames.particles.valuetypes.Curve;
import com.epagagames.particles.valuetypes.ValueType;
import com.epagagames.particles.valuetypes.VectorValueType;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import imgui.ImGui;
import imgui.type.ImFloat;
import imgui.type.ImInt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropVecValueType extends PropBase {

  private Method set;
  private Method get;


  private PropVec3 value = new PropVec3("Value", VectorValueType.class, "getValue", "setValue");
  private PropVec3 valueMax = new PropVec3("Value Max", VectorValueType.class, "getMax", "setMax");

  private CurveRender renderCurveX = new CurveRender();
  private CurveRender renderCurveY = new CurveRender();
  private CurveRender renderCurveZ = new CurveRender();
  private String name = "DEF";

  private String[] modes = {"Constant", "Curve", "Random", "Random Between Curve"};
  private ImInt modeIndex = new ImInt();
  private ImInt curveIndex = new ImInt();
  public PropVecValueType(String name, Class c, String getMeth, String setMeth) {
    this.name = name;
    set = findMethod(c, setMeth, VectorValueType.class);
    get = findMethod(c, getMeth, VectorValueType.class);
  }

  @Override
  public void render(Object obj) {
    try {
      VectorValueType current = (VectorValueType) get.invoke(obj);
      modeIndex.set(current.getType().ordinal());

      ImGui.pushID("##");
      ImGui.columns(2);
      ImGui.setColumnWidth(0, COLUMN_WIDTH);
      ImGui.text(name);
      ImGui.newLine();
      ImGui.combo("Mode##" + name, modeIndex, modes);
      ImGui.nextColumn();
      if (current.getType() != VectorValueType.Type.values()[modeIndex.intValue()]) {
        var newMode = VectorValueType.Type.values()[modeIndex.intValue()];
        if (newMode == VectorValueType.Type.CONSTANT) current.setValue(new Vector3f());
        if (newMode == VectorValueType.Type.RANDOM) current.setValue(new Vector3f(), new Vector3f(1.0f, 1.0f, 1.0f));
        if (newMode == VectorValueType.Type.CURVE) {
          Curve x = new Curve();
          x.addControlPoint(new Vector2f(), new Vector2f(), new Vector2f());
          x.addControlPoint(new Vector2f(), new Vector2f(), new Vector2f());
          Curve y = new Curve();
          y.addControlPoint(new Vector2f(), new Vector2f(), new Vector2f());
          y.addControlPoint(new Vector2f(), new Vector2f(), new Vector2f());
          Curve z = new Curve();
          z.addControlPoint(new Vector2f(), new Vector2f(), new Vector2f());
          z.addControlPoint(new Vector2f(), new Vector2f(), new Vector2f());
          current.setCurve(x, y, z);

        }
        //if (newMode == ValueType.Type.CURVE) current.setCurve();
        //if (newMode == ValueType.Type.RANDOM_BETWEEN_CURVES) current.setBetweenCurves();
      }
      if (current.getType() == VectorValueType.Type.CONSTANT) {
        Vector3f t = value.renderControl(current.getValue());
        current.setValue(new Vector3f(t));
      } else if (current.getType() == VectorValueType.Type.RANDOM) {
        Vector3f t1 = value.renderControl(current.getValue());
        Vector3f t2 = valueMax.renderControl(current.getMax());
        current.setValue(new Vector3f(t1), new Vector3f(t2));
      } else if (current.getType() == VectorValueType.Type.CURVE) {
        ImGui.radioButton("X", curveIndex, 0);ImGui.sameLine();
        ImGui.radioButton("Y", curveIndex, 1);ImGui.sameLine();
        ImGui.radioButton("Z", curveIndex, 2);

        if (curveIndex.get() == 0) {
          ImGui.text("X");
          renderCurveX.render(current.getX1());
        } else if (curveIndex.get() == 1) {
          ImGui.text("Y");
          renderCurveY.render(current.getY1());
        } else {
          ImGui.text("Z");
          renderCurveZ.render(current.getZ1());
        }

      }
      ImGui.columns(1);
//      if (current != null && current.floatValue() != value.floatValue()) {
//        set.invoke(obj, value.get());
//      }

      ImGui.popID();
      ImGui.separator();
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

}
