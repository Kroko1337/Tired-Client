package beta.tiredb56.api.annotations;

import beta.tiredb56.module.ModuleCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleAnnotation {

    String name();

    String clickG() default "mod";

    int key() default -1;

    String desc() default "";

    ModuleCategory category();

}
