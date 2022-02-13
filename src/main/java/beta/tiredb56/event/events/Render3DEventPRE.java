package beta.tiredb56.event.events;

import beta.tiredb56.event.Event;

public class Render3DEventPRE extends Event {

    public float partialTicks;

    public Render3DEventPRE(float partialTicks) {
        this.partialTicks = partialTicks;
    }

}
