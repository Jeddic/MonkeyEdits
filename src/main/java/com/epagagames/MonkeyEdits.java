package com.epagagames;

import com.epagagames.particles.Emitter;
import com.epagagames.particles.emittershapes.EmitterCone;
import com.epagagames.particles.valuetypes.ValueType;
import com.epagagames.states.EditorGui;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

public class MonkeyEdits extends SimpleApplication {
  @Override
  public void simpleInitApp() {
    stateManager.attach(new EditorGui());

    inputManager.setCursorVisible(true);
    flyCam.setEnabled(false);

    Sphere mesh = new Sphere(25, 25, 2, false, true);
    Geometry geom = new Geometry("A shape", mesh); // wrap shape into geometry
    Material mat = new Material(assetManager,
        "Common/MatDefs/Misc/ShowNormals.j3md");   // create material
    geom.setMaterial(mat);                         // assign material to geometry
    geom.setLocalTranslation(0, -5.0f, 0);
// if you want, transform (move, rotate, scale) the geometry.
    rootNode.attachChild(geom);

    mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
    mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
    Texture tex = assetManager.loadTexture("Effects/Particles/part_light.png");
    mat.setTexture("Texture", tex);

    Emitter emitter = new Emitter("TestEmitter", mat, 100);
    emitter.setStartSpeed(new ValueType(6.5f));
    emitter.setLifeFixedDuration(2.0f);
    emitter.setEmissionsPerSecond(20);
    emitter.setParticlesPerEmission(1);
    emitter.setShape(new EmitterCone());
    ((EmitterCone)emitter.getShape()).setRadius(0.005f);

    emitter.setLocalTranslation(0, 0.5f, 0);
    rootNode.attachChild(emitter);
  }

  @Override
  public void simpleUpdate(float tpf) {
    inputManager.setCursorVisible(true);
  }
}
