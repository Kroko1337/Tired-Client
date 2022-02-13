package beta.tiredb56.command.impl;

import beta.tiredb56.api.annotations.CommandAnnotation;
import beta.tiredb56.command.Command;


import java.util.Arrays;

@CommandAnnotation(name = "Togge", help = "du jude", alias = {"t", "toggle", "tggle"})
public class IRC extends Command {

    @Override
    public void doCommand(String[] args) {
        super.doCommand(args);
    }

    public static String toCompleteString(final String[] args, final int start) {
        if(args.length <= start) return "";

        return String.join(" ", Arrays.copyOfRange(args, start, args.length));
    }


}



