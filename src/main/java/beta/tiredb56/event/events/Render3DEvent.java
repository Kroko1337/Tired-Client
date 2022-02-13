package beta.tiredb56.event.events;

import beta.tiredb56.event.Event;

public class Render3DEvent extends Event {

    public float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

}
