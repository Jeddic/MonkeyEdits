package com.epagagames.windows.propwrappers;

import com.epagagames.particles.Emitter;
import com.epagagames.particles.EmitterShape;
import com.epagagames.particles.emittershapes.*;
import com.epagagames.windows.props.PropBool;
import com.epagagames.windows.props.PropFloat;
import imgui.ImGui;
import imgui.type.ImInt;

public class ShapeWrapBase {

  private PropFloat randomDirection = new PropFloat("Random Direction", EmitterShape.class, "getRandomDirection", "setRandomDirection");
  private PropFloat orginDirection = new PropFloat("Origin Direction", EmitterShape.class, "getOriginDirection", "setOriginDirection");
  private PropFloat randomizePos = new PropFloat("Randomize Position", EmitterShape.class, "getRandomizePosition", "setRandomizePosition");

  // CIRCLE VARIABLES
  private PropFloat radius = new PropFloat("Radius", EmitterCircle.class, "getRadius", "setRadius");
  private PropFloat radiusThickness = new PropFloat("Radius Thickness", EmitterCircle.class, "getRadiusThickness", "setRadiusThickness");
  private PropFloat arc = new PropFloat("Arc", EmitterCircle.class, "getArc", "setArc");


  // CONE VARIABLES
  private PropFloat radiusCone = new PropFloat("Radius", EmitterCone.class, "getRadius", "setRadius");
  private PropFloat radiusThicknessCone = new PropFloat("Radius Thickness", EmitterCone.class, "getRadiusThickness", "setRadiusThickness");
  private PropFloat arcCone = new PropFloat("Arc", EmitterCone.class, "getArc", "setArc");
  private PropFloat angleCone = new PropFloat("Angle", EmitterCone.class, "getAngle", "setAngle");
  private PropFloat lengthCone = new PropFloat("Length", EmitterCone.class, "getLength", "setLength");

  private PropBool emitVolumeCone = new PropBool("Emit From Volume", EmitterCone.class, "isEmitFromVolume", "setEmitFromVolume");


  // LINE VARIABLES
  private PropFloat radiusLine = new PropFloat("Radius", EmitterLine.class, "getRadius", "setRadius");

  // SPHERE VARIABLES
  private PropFloat radiusSphere = new PropFloat("Radius", EmitterSphere.class, "getRadius", "setRadius");
  private PropFloat radiusThicknessSphere = new PropFloat("Radius Thickness", EmitterSphere.class, "getRadiusThickness", "setRadiusThickness");
  private PropFloat arcSphere = new PropFloat("Arc", EmitterSphere.class, "getArc", "setArc");

  private ImInt shapeSelection = new ImInt(0);
  private String[] shapeList = new String[] {"Circle", "Cone", "Line", "Mesh", "Sphere"};

  public ShapeWrapBase() {

  }

  public void render(Emitter emitter, EmitterShape shape) {
    if (shape instanceof EmitterCircle) shapeSelection.set(0);
    if (shape instanceof EmitterCone) shapeSelection.set(1);
    if (shape instanceof EmitterLine) shapeSelection.set(2);
    if (shape instanceof EmitterMesh) shapeSelection.set(3);
    if (shape instanceof EmitterSphere) shapeSelection.set(4);

    ImGui.combo("Shape", shapeSelection, shapeList);
    if (!(shape instanceof EmitterCircle) && shapeSelection.get() == 0) emitter.setShape(new EmitterCircle());
    if (!(shape instanceof EmitterCone) && shapeSelection.get() == 1) emitter.setShape(new EmitterCone());
    if (!(shape instanceof EmitterLine) && shapeSelection.get() == 2) emitter.setShape(new EmitterLine());
    if (!(shape instanceof EmitterMesh) && shapeSelection.get() == 3) emitter.setShape(new EmitterMesh());
    if (!(shape instanceof EmitterSphere) && shapeSelection.get() == 4) emitter.setShape(new EmitterSphere());

    randomDirection.render(shape);
    orginDirection.render(shape);
    randomizePos.render(shape);

    if (shape instanceof EmitterCircle ec) {
      radius.render(ec);
      radiusThickness.render(ec);
      arc.render(ec);
    } else if (shape instanceof EmitterCone ec) {
      angleCone.render(ec);
      arcCone.render(ec);
      radiusCone.render(ec);
      radiusThicknessCone.render(ec);
      lengthCone.render(ec);
      emitVolumeCone.render(ec);
    } else if (shape instanceof EmitterLine el) {
      radiusLine.render(el);
    } else if (shape instanceof EmitterMesh em) {

    } else if (shape instanceof EmitterSphere es) {
      radiusSphere.render(es);
      radiusThicknessSphere.render(es);
      arcSphere.render(es);
    }
  }
}
