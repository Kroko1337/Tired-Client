package beta.tiredb56.command.impl;

import beta.tiredb56.api.annotations.CommandAnnotation;
import beta.tiredb56.command.Command;
import beta.tiredb56.api.logger.impl.IngameChatLog;
import beta.tiredb56.module.Module;
import beta.tiredb56.tired.CheatMain;
import beta.tiredb56.api.util.FileUtil;
import org.lwjgl.input.Keyboard;

@CommandAnnotation(name = "Bind", help = "", alias = {"b", "bi"})
public class BindCommand extends Command {

    @Override
    public void doCommand(String[] args) {
        args[1] = args[1].toUpperCase();
        int key = Keyboard.getKeyIndex(args[1]);
        for(Module m : CheatMain.INSTANCE.moduleManager.getModuleList()) {
            if(args[0].equalsIgnoreCase(m.getName())) {
                m.setKey(key);
                FileUtil.FILE_UTIL.saveKeybinds();
                IngameChatLog.INGAME_CHAT_LOG.doLog("§e" + Keyboard.getKeyName(key) + " §7has been bound to §e" + m.getName());
                return;
            } else if(args[0].equalsIgnoreCase("reset")) {
                if(args[1].equalsIgnoreCase(m.getName())) {
                    m.setKey(0);
                    IngameChatLog.INGAME_CHAT_LOG.doLog("§e" + m.getName() + " §7has been " + "§eunbound");
                    return;
                }
            }
        }
        IngameChatLog.INGAME_CHAT_LOG.doLog("§cInvalid Module!");
        super.doCommand(args);
    }
}
