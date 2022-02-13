package beta.tiredb56.command.impl;

import beta.tiredb56.api.annotations.CommandAnnotation;
import beta.tiredb56.command.Command;
import beta.tiredb56.config.Config;
import beta.tiredb56.api.logger.impl.IngameChatLog;
import beta.tiredb56.tired.CheatMain;

@CommandAnnotation(name = "Config", help = "", alias = {"c", "config", "cfg"})
public class ConfigCommand extends Command {

    @Override
    public void doCommand(String[] args) {
        if (args.length < 1) {

        } else {
            switch (args[0].toLowerCase()) {
                case "save":
                    IngameChatLog.INGAME_CHAT_LOG.doLog("Saved config " + args[1]);
                    if (CheatMain.INSTANCE.configManager.configBy(args[1]) == null) {
                        CheatMain.INSTANCE.configManager.create(new Config(args[1]));
                    } else {
                        CheatMain.INSTANCE.configManager.save(args[1]);
                    }
                    break;
                case "load":
                    if (CheatMain.INSTANCE.configManager.configBy(args[1]) != null) {
                        if (CheatMain.INSTANCE.configManager.load(args[1])) {
                            IngameChatLog.INGAME_CHAT_LOG.doLog("Loaded config " + args[1]);
                        }
                        break;
                    }
                    IngameChatLog.INGAME_CHAT_LOG.doLog("Couldn't find config " + args[1]);
                    break;
                case "list":
                    if (CheatMain.INSTANCE.configManager.configs().size() > 0) {
                        IngameChatLog.INGAME_CHAT_LOG.doLog("Configs:");
                        CheatMain.INSTANCE.configManager.configs().forEach(config -> IngameChatLog.INGAME_CHAT_LOG.doLog(config.name()));
                        break;
                    }
                    IngameChatLog.INGAME_CHAT_LOG.doLog("You dont have any configs!");
                    break;
            }
        }
        super.doCommand(args);
    }
}
