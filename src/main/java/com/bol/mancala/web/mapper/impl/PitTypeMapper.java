package com.bol.mancala.web.mapper.impl;

import com.bol.mancala.domain.Pit;
import org.springframework.stereotype.Component;

@Component
public class PitTypeMapper {

  public Pit.Type asEnum(int type) {
    if (type == 1) {
      return Pit.Type.BIG;
    }
    return Pit.Type.LITTLE;
  }

  public int asInteger(Pit.Type type) {
    if (type == Pit.Type.BIG) {
      return 1;
    }
    return 0;
  }

}
