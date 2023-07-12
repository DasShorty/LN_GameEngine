package com.laudynetwork.gameengine.api.animation;

import com.laudynetwork.gameengine.api.animation.impl.ActionBarAnimation;
import lombok.Getter;

import java.util.ArrayList;

public class AnimationController {

    @Getter
    private final ArrayList<ActionBarAnimation> actionBarAnimations = new ArrayList<>();


}
