package beta.tiredb56.command;

import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.api.annotations.CommandAnnotation;

public class Command implements IHook {

    public final String name = getClass().getAnnotation(CommandAnnotation.class).name();

    public final String help = getClass().getAnnotation(CommandAnnotation.class).help();

    public final String[] alias = getClass().getAnnotation(CommandAnnotation.class).alias();

    public void doCommand(String[] args) {

    }

    public String getHelp() {
        return help;
    }

    public String getName() {
        return name;
    }

    public String[] getAlias() {
        return alias;
    }
}
