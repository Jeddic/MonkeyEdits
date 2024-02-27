package com.epagagames.windows.props;

import com.epagagames.particles.valuetypes.Curve;
import com.epagagames.particles.valuetypes.ValueType;
import com.epagagames.particles.valuetypes.VectorValueType;
import imgui.ImGui;
import imgui.type.ImFloat;
import imgui.type.ImInt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropVecValueType extends PropBase {

  private Method set;
  private Method get;


  private PropVec3 value = new PropVec3("Value", VectorValueType.class, "getValue", "setValue");
  private String name = "DEF";

  private String[] modes = {"Constant", "Random", "Curve", "Random Between Curve"};
  private ImInt modeIndex = new ImInt();
  public PropVecValueType(String name, Class c, String getMeth, String setMeth) {
    this.name = name;
    set = findMethod(c, setMeth, VectorValueType.class);
    get = findMethod(c, getMeth, VectorValueType.class);
  }

  @Override
  public void render(Object obj) {
//    try {
//      VectorValueType current = (VectorValueType) get.invoke(obj);
//      modeIndex.set(current.getType().ordinal());
////      if (current != null && current.getType().ordinal() != modeIndex.intValue()) {
////        value.set(current);
////      }
//      if (current != null && current.getType() == VectorValueType.Type.CONSTANT) {
//        value.setValue(current.getValue());
//      } else if (current != null && current.getType() == VectorValueType.Type.RANDOM) {
//        value.set(current.getValue());
//        valueSecond.set(current.getMax());
//      } else if (current != null && current.getType() == VectorValueType.Type.CURVE) {
//        Curve curve = current.getCurve();
//
//      } else if (current != null && current.getType() == VectorValueType.Type.RANDOM_BETWEEN_CURVES) {
//      }
//
//      ImGui.columns(2);
//      ImGui.setColumnWidth(0, COLUMN_WIDTH);
//      ImGui.text(name);
//      ImGui.newLine();
//      ImGui.combo("Mode##" + name, modeIndex, modes);
//      ImGui.nextColumn();
//      if (current.getType() != VectorValueType.Type.values()[modeIndex.intValue()]) {
//        var newMode = ValueType.Type.values()[modeIndex.intValue()];
//        if (newMode == ValueType.Type.CONSTANT) current.setValue(1.0f);
//        if (newMode == ValueType.Type.RANDOM) current.setMinMaxValue(0.0f, 1.0f);
//        //if (newMode == ValueType.Type.CURVE) current.setCurve();
//        //if (newMode == ValueType.Type.RANDOM_BETWEEN_CURVES) current.setBetweenCurves();
//      }
//      if (current.getType() == VectorValueType.Type.CONSTANT) {
//        ImGui.inputFloat("##" + name, value);
//        if (current.getValue() != value.get()) {
//          current.setValue(value.get());
//        }
//      } else if (current.getType() == VectorValueType.Type.RANDOM) {
//        ImGui.inputFloat("##" + name + "Start", value);
//        ImGui.newLine();
//        ImGui.inputFloat("##" + name + "End", valueSecond);
//
//      }
//      ImGui.columns(1);
////      if (current != null && current.floatValue() != value.floatValue()) {
////        set.invoke(obj, value.get());
////      }
//
//      ImGui.separator();
//
//      if (current != null && current.getType() == VectorValueType.Type.CONSTANT) {
//        current.setValue(value.get());
//      } else if (current != null && current.getType() == VectorValueType.Type.RANDOM) {
//        current.setMinMaxValue(value.get(), valueSecond.get());
//      } else if (current != null && current.getType() == VectorValueType.Type.CURVE) {
//      } else if (current != null && current.getType() == VectorValueType.Type.RANDOM_BETWEEN_CURVES) {
//      }
//    } catch (IllegalAccessException e) {
//      throw new RuntimeException(e);
//    } catch (InvocationTargetException e) {
//      throw new RuntimeException(e);
//    }
  }

}
