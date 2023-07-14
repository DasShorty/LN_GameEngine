package com.laudynetwork.gameengine.api.animation;

import com.laudynetwork.gameengine.api.animation.impl.ActionBarAnimation;
import com.laudynetwork.gameengine.api.animation.impl.TitleAnimation;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class AnimationController {


    private final ArrayList<ActionBarAnimation> actionBarAnimations = new ArrayList<>();
    private final ArrayList<TitleAnimation> titleAnimations = new ArrayList<>();


}
