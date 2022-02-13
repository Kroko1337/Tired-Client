package beta.tiredb56.event.events;

import beta.tiredb56.event.Event;

public class Render3DEvent2 extends Event {

    public float partialTicks;

    public Render3DEvent2(float partialTicks) {
        this.partialTicks = partialTicks;
    }

}
