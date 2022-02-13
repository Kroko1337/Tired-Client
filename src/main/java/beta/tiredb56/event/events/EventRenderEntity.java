package beta.tiredb56.event.events;

import beta.tiredb56.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class EventRenderEntity extends Event {
    Entity entity;


    public EventRenderEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(EntityLivingBase entity) {
        this.entity = entity;
    }


}