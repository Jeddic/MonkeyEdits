package com.epagagames.windows.propwrappers;

import com.jme3.scene.Spatial;

public abstract class WrapBase {

  public void render(Spatial spatial) {}
  public abstract boolean wrapperApplicable(Spatial spatial);
}
