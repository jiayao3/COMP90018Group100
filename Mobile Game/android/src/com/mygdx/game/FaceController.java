package com.mygdx.game;

import com.google.mediapipe.formats.proto.LandmarkProto;

public class FaceController {
    private volatile LandmarkProto.NormalizedLandmark noseLandmark;

    public void updateNoseLandmark(LandmarkProto.NormalizedLandmark noseLandmark) {
        this.noseLandmark = noseLandmark;
    }

    public LandmarkProto.NormalizedLandmark getNoseLandmark() {
        return noseLandmark;
    }
}