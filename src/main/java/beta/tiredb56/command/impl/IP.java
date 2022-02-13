package beta.tiredb56.command.impl;

import beta.tiredb56.api.annotations.CommandAnnotation;
import beta.tiredb56.command.Command;
import beta.tiredb56.interfaces.IHook;
import net.minecraft.client.gui.GuiScreen;

import java.util.Arrays;

@CommandAnnotation(name = "IP", help = "Amongus", alias = {"i", "IP"})
public class IP extends Command implements IHook {

    @Override
    public void doCommand(String[] args) {
        GuiScreen.setClipboardString(MC.isSingleplayer() ? "SinglePlayer" : MC.getCurrentServerData().serverIP);
        super.doCommand(args);
    }

    public static String toCompleteString(final String[] args, final int start) {
        if(args.length <= start) return "";

        return String.join(" ", Arrays.copyOfRange(args, start, args.length));
    }


}



