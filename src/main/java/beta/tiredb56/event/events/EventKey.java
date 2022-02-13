package beta.tiredb56.event.events;

import beta.tiredb56.event.Event;

public class EventKey extends Event
{
	public int key;

	public EventKey(final int key) {
		this.key = key;
	}

	public int getKey() {
		return this.key;
	}
}
