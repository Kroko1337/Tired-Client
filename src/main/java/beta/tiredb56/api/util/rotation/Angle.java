package beta.tiredb56.api.util.rotation;

import lombok.Getter;
import lombok.Setter;

public class Angle {

    @Getter
    @Setter
    private float rotationYAW, rotationPITCH;

    public Angle(final float rotationYAW, final float rotationPITCH) {
        this.rotationYAW = rotationYAW;
        this.rotationPITCH = rotationPITCH;
    }


    public Angle() {
        this(0.0f, 0.0f);
    }

    public Angle constraintAngle() {
        this.setRotationYAW(getRotationYAW() % 360.0f);
        this.setRotationPITCH(getRotationPITCH() % 360.0f);
        while (getRotationYAW() <= -180.0f) {
            setRotationYAW(getRotationYAW() + 360.0f);
        }
        while (getRotationPITCH() <= -180.0f) {
            setRotationPITCH(getRotationPITCH() + 360.0f);
        }
        while (getRotationPITCH() > 180.0f) {
            setRotationYAW(getRotationYAW() - 360.0f);
        }
        while (getRotationPITCH() > 180.0f) {
            setRotationPITCH(getRotationPITCH() - 360.0f);
        }
        return this;
    }

}
