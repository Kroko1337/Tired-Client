package beta.tiredb56.command.impl;

import beta.tiredb56.api.annotations.CommandAnnotation;
import beta.tiredb56.api.logger.impl.IngameChatLog;
import beta.tiredb56.command.Command;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.module.Module;
import beta.tiredb56.tired.CheatMain;

@CommandAnnotation(name = "Toggle", help = "ToggleSuss", alias = {"toggle", "t"})
public class Toggle extends Command implements IHook {

    @Override
    public void doCommand(String[] args) {
        if (args.length != 1) {
            IngameChatLog.INGAME_CHAT_LOG.doLog("You can toggle with .t name or with .toggle name");
        }



        for (Module module : CheatMain.INSTANCE.moduleManager.getModuleList()) {
            if (!args[0].equalsIgnoreCase(module.getName())) continue;
            module.executeMod();
            IngameChatLog.INGAME_CHAT_LOG.doLog(module.getName() + " was toggled");
            break;
        }
        super.doCommand(args);
    }

}
