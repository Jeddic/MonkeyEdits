package com.epagagames.windows.propwrappers;

import com.epagagames.windows.props.PropRotation;
import com.epagagames.windows.props.PropStr;
import com.epagagames.windows.props.PropVec3;
import com.jme3.scene.Spatial;
import imgui.ImGui;
import imgui.ImGuiInputTextCallbackData;
import imgui.callback.ImGuiInputTextCallback;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SpatialWrap extends WrapBase{

  private ImString NAME = new ImString();

  private PropStr nameProp;
  private PropVec3 position;
  private PropRotation rotation;
  private PropVec3 scale;

  public SpatialWrap() {
    nameProp = new PropStr("Name", Spatial.class, "getName", "setName");
    position = new PropVec3("Translation", Spatial.class, "getLocalTranslation", "setLocalTranslation");
    rotation = new PropRotation("Rotation", Spatial.class, "getLocalRotation", "setLocalRotation");
    scale = new PropVec3("Scale", Spatial.class, "getLocalScale", "setLocalScale");
  }

  @Override
  public void render(Spatial spatial) {
    if (spatial != null) {
      nameProp.render(spatial);
      position.render(spatial);
      rotation.render(spatial);
      scale.render(spatial);

    }
  }

  @Override
  public boolean wrapperApplicable(Spatial spatial) {
    return spatial != null;
  }

}
