package beta.tiredb56.event.events;

import beta.tiredb56.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent extends Event {

    float partialTicks;
    public ScaledResolution scaledRes;

    public Render2DEvent(float partialTicks, ScaledResolution scaledRes) {
        this.partialTicks = partialTicks;
        this.scaledRes = scaledRes;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
