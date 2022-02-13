package beta.tiredb56.event.events;

import beta.tiredb56.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;

@AllArgsConstructor  @Getter
@Setter
public class EntityRendererEvent extends Event {
    Entity entity;
}