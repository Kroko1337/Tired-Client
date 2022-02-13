package beta.tiredb56.api.util.rotation;

import javax.vecmath.Vector3d;
import java.util.Random;

public class AngleUtility {
    private float smooth;
    public static Random random;

    public AngleUtility(float smooth) {
        this.smooth = smooth;
        random = new Random();
    }

    public Angle calculateAngle(Vector3d pos1, Vector3d pos2) {
        Angle class1602 = new Angle();
        pos1.x -= pos2.x;
        pos1.y -= pos2.y;
        pos1.z -= pos2.z;
        class1602.setRotationYAW((float)(Math.atan2(pos1.z, pos1.x) * 57.29577951308232) - 90.0f);
        class1602.setRotationPITCH(-(float)(Math.atan2(pos1.y, Math.hypot(pos1.x, pos1.z)) * 57.29577951308232));
        return class1602.constraintAngle();
    }


    public Angle smoothAngle(Angle destination, Angle source) {
        final Angle angles = new Angle(source.getRotationYAW() - destination.getRotationYAW(), source.getRotationPITCH() - destination.getRotationPITCH()).constraintAngle();
        angles.setRotationYAW(source.getRotationYAW() - angles.getRotationYAW() / 100.0f * this.smooth);
        angles.setRotationPITCH(source.getRotationPITCH() - angles.getRotationPITCH() / 100.0f * this.smooth);
        return angles.constraintAngle();
    }

}