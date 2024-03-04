package com.epagagames.windows.propwrappers;

import com.epagagames.particles.BillboardMode;
import com.epagagames.particles.Emitter;
import com.epagagames.particles.EmitterShape;
import com.epagagames.particles.influencers.*;
import com.epagagames.windows.props.*;
import com.jme3.scene.Spatial;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiPopupFlags;
import imgui.type.ImBoolean;

public class MonkeyEmitterWrap extends WrapBase {

  private PropInt emissionsPerSecond = new PropInt("Emissions Per Second", Emitter.class, "getEmissionsPerSecond", "setEmissionsPerSecond");
  private PropInt partPerEmission = new PropInt("Part Per Emission", Emitter.class, "getParticlesPerEmission", "setParticlesPerEmission");

  private PropBool randEmission = new PropBool("Random Emission Point", Emitter.class, "getUseRandomEmissionPoint", "setUseRandomEmissionPoint");
  private PropBool staticPart = new PropBool("Static Particles", Emitter.class, "getUseStaticParticles", "setUseStaticParticles");
  private PropBool followEmitter = new PropBool("Part Follow Emitter", Emitter.class, "getParticlesFollowEmitter", "setParticlesFollowEmitter");

  private PropEnum<BillboardMode> billboardMode = new PropEnum<>("Billboard Mode", Emitter.class, BillboardMode.class, BillboardMode.values(), "getBillboardMode", "setBillboardMode");
  private PropBool looping = new PropBool("Looping", Emitter.class, "isLooping", "setLooping");
  private PropFloat duration = new PropFloat("Duration", Emitter.class, "getDuration", "setDuration");

  private PropValueType startSize = new PropValueType("Start Size", Emitter.class, "getStartSize", "setStartSize");
  private PropValueType lifeMin = new PropValueType("Life Min", Emitter.class, "getLifeMin", "setLifeMin");
  private PropValueType lifeMax = new PropValueType("Life Max", Emitter.class, "getLifeMax", "setLifeMax");
  private PropValueType startSpeed = new PropValueType("Start Speed", Emitter.class, "getStartSpeed", "setStartSpeed");

  private PropColorValue startColor = new PropColorValue("Start Color", Emitter.class, "getStartColor", "setStartColor");

  private PropColorValue colorInfluencer_color = new PropColorValue("Color Over Time", ColorInfluencer.class, "getColorOverTime", "setColorOverTime");
  private PropValueType sizeInfluencer_size = new PropValueType("Size Over Time", SizeInfluencer.class, "getSizeOverTime", "setSizeOverTime");

  private ShapeWrapBase shapeWrap = new ShapeWrapBase();

  private ImBoolean basicPhysicsInfluencer = new ImBoolean();
  private ImBoolean colorInfluencer = new ImBoolean();
  private ImBoolean emissionInfluencer = new ImBoolean();
  private ImBoolean gravityInfluencer = new ImBoolean();
  private ImBoolean impulseInfluencer = new ImBoolean();
  private ImBoolean preferedDestInfluencer = new ImBoolean();
  private ImBoolean preferedDirInfluencer = new ImBoolean();
  private ImBoolean randomInfluencer = new ImBoolean();
  private ImBoolean rotLifetimeInfluencer = new ImBoolean();
  private ImBoolean rotVelocityInfluencer = new ImBoolean();
  private ImBoolean sizeInfluencer = new ImBoolean();
  private ImBoolean spriteInfluencer = new ImBoolean();
  private ImBoolean trailInfluencer = new ImBoolean();
  private ImBoolean velocityInfluencer = new ImBoolean();
  public MonkeyEmitterWrap() {

  }

  @Override
  public void render(Spatial spatial) {
    if (spatial != null && spatial instanceof Emitter emitter) {
      //nameProp.render(spatial);

      if (ImGui.collapsingHeader("Emitter")) {
        ImGui.labelText("Active Particles", "" + emitter.getActiveParticleCount());
        billboardMode.render(spatial);

        looping.render(spatial);
        randEmission.render(spatial);
        staticPart.render(spatial);
        followEmitter.render(spatial);

        duration.render(spatial);
        emissionsPerSecond.render(spatial);
        partPerEmission.render(spatial);

        EmitterShape shape = emitter.getShape();
        ImGui.separator();
        shapeWrap.render(emitter, shape);
        ImGui.separator();

        startSize.render(spatial);
        lifeMin.render(spatial);
        lifeMax.render(spatial);
        startSpeed.render(spatial);
        startColor.render(spatial);

        ImGui.separator();

        if (ImGui.button("Edit Influencers")) {
          ImGui.openPopup("Edit Influencers");
        }
        if (ImGui.beginPopupModal("Edit Influencers")) {
          ImGui.text("Select influencers");
          ImGui.separator();

          if (renderInfCheckbox(emitter, BasicPhysicsInfluencer.class, basicPhysicsInfluencer)) {
            //emitter.addInfluencer(new BasicPhysicsInfluencer());
          }
          if (renderInfCheckbox(emitter, EmissionInfluencer.class, emissionInfluencer)) emitter.addInfluencer(new EmissionInfluencer());
          if (renderInfCheckbox(emitter, ColorInfluencer.class, colorInfluencer)) emitter.addInfluencer(new ColorInfluencer());
          if (renderInfCheckbox(emitter, GravityInfluencer.class, gravityInfluencer)) emitter.addInfluencer(new GravityInfluencer());
          if (renderInfCheckbox(emitter, ImpulseInfluencer.class, impulseInfluencer)) emitter.addInfluencer(new ImpulseInfluencer());
          if (renderInfCheckbox(emitter, PreferredDestinationInfluencer.class, preferedDestInfluencer)) emitter.addInfluencer(new PreferredDestinationInfluencer());
          if (renderInfCheckbox(emitter, PreferredDirectionInfluencer.class, preferedDirInfluencer)) emitter.addInfluencer(new PreferredDirectionInfluencer());
          if (renderInfCheckbox(emitter, RandomInfluencer.class, randomInfluencer)) emitter.addInfluencer(new RandomInfluencer());
          if (renderInfCheckbox(emitter, RotationLifetimeInfluencer.class, rotLifetimeInfluencer)) emitter.addInfluencer(new RotationLifetimeInfluencer());
          if (renderInfCheckbox(emitter, RotationVelocityInfluencer.class, rotVelocityInfluencer)) emitter.addInfluencer(new RotationVelocityInfluencer());
          if (renderInfCheckbox(emitter, SizeInfluencer.class, sizeInfluencer)) emitter.addInfluencer(new SizeInfluencer());
          if (renderInfCheckbox(emitter, SpriteInfluencer.class, spriteInfluencer)) emitter.addInfluencer(new SpriteInfluencer());
          if (renderInfCheckbox(emitter, TrailInfluencer.class, trailInfluencer)) emitter.addInfluencer(new TrailInfluencer());
          if (renderInfCheckbox(emitter, VelocityInfluencer.class, velocityInfluencer)) emitter.addInfluencer(new VelocityInfluencer());



          if (ImGui.button("OK", 120, 0)) { ImGui.closeCurrentPopup(); }
          ImGui.sameLine();
          if (ImGui.button("Cancel", 120, 0)) { ImGui.closeCurrentPopup(); }
          ImGui.endPopup();
        }

        ImGui.separator();
        for (ParticleInfluencer influencer : emitter.getInfluencerMap()) {
          if (influencer instanceof BasicPhysicsInfluencer physicsInf) {
            ImGui.text("Basic Physics");
            ImGui.separator();
          }
          if (influencer instanceof ColorInfluencer colorInf) {
            ImGui.text("Color Over Time");
            ImGui.separator();
            colorInfluencer_color.render(colorInf);
          }
          if (influencer instanceof EmissionInfluencer immInf) {
            ImGui.text("Emission");
            ImGui.separator();
          }
          if (influencer instanceof GravityInfluencer gravInf) {
            ImGui.text("Gravity");
            ImGui.separator();
          }
          if (influencer instanceof ImpulseInfluencer impInf) {
            ImGui.text("Impulse");
            ImGui.separator();
          }
          if (influencer instanceof PreferredDestinationInfluencer prefDestInf) {
            ImGui.text("Preferred Destination");
            ImGui.separator();

          }
          if (influencer instanceof PreferredDirectionInfluencer prefDirInf) {
            ImGui.text("Preferred Direction");
            ImGui.separator();

          }
          if (influencer instanceof RandomInfluencer randInf) {
            ImGui.text("Random");
            ImGui.separator();
          }
          if (influencer instanceof RotationLifetimeInfluencer rotInf) {

            ImGui.text("Rotation Lifetime");
            ImGui.separator();
          }
          if (influencer instanceof RotationVelocityInfluencer rotInf) {
            ImGui.text("Rotation Velocity");
            ImGui.separator();
          }
          if (influencer instanceof SizeInfluencer sizeInf) {
            ImGui.text("Size Over Time");
            ImGui.separator();
            sizeInfluencer_size.render(sizeInf);
          }
          if (influencer instanceof SpriteInfluencer spriteInf) {
            ImGui.text("Sprite");
            ImGui.separator();
          }
          if (influencer instanceof TrailInfluencer trailInf) {
            ImGui.text("Trail");
            ImGui.separator();
          }
          if (influencer instanceof VelocityInfluencer velInf) {
            ImGui.text("Velocity");
            ImGui.separator();
          }

        }
      }

    }
  }

  boolean renderInfCheckbox(Emitter emitter, Class infclass, ImBoolean checkFlag) {
    boolean originalValue = emitter.getInfluencer(infclass) != null;
    checkFlag.set(originalValue);
    ImGui.checkbox(infclass.getSimpleName(), checkFlag);
    if (originalValue != checkFlag.get() && !checkFlag.get()) emitter.removeInfluencer(infclass);
    if (originalValue != checkFlag.get() && checkFlag.get()) return true;

    return false;
  }

  @Override
  public boolean wrapperApplicable(Spatial spatial) {
    return spatial instanceof Emitter;
  }
}
