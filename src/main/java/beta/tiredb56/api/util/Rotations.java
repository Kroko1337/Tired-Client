package beta.tiredb56.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Rotations {

    public boolean noRotate;

    public float server_yaw, server_pitch;

    public float scaffoldYaw, scaffoldPitch;

    public float yawDifference, pitchDifference;

    public static float yaw, pitch, beforePitch, beforeYaw;

}
