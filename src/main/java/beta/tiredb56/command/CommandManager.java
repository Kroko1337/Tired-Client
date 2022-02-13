package beta.tiredb56.command;

import beta.tiredb56.command.impl.BindCommand;
import beta.tiredb56.command.impl.ConfigCommand;
import beta.tiredb56.command.impl.IRC;
import beta.tiredb56.api.logger.impl.IngameChatLog;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager {

    private final ArrayList<Command> COMMANDS;

    public CommandManager() {
        COMMANDS = new ArrayList<>();

        addCommands(
                new BindCommand(), new IRC(), new ConfigCommand()
        );
    }

    public ArrayList<Command> getCommands() {
        return COMMANDS;
    }


    private void addCommands(Command... commands) {
        COMMANDS.addAll(Arrays.asList(commands));
    }

    public void execute(String input) {

        String[] split = input.split(" ");
        String command = split[0];
        String args = input.substring(command.length()).trim();

        for(Command c : getCommands()) {
            for(String alias : c.alias) {
                if(command.equalsIgnoreCase(alias)) {
                    try {
                        c.doCommand(args.split(" "));
                    } catch (Exception e) {
                        c.getHelp();
                    }
                    return;
                }
            }
            if(command.equalsIgnoreCase(c.getName())) {
                try {
                    c.doCommand(args.split(" "));
                } catch (Exception e) {
                    c.getHelp();
                }
                return;
            }
        }

        IngameChatLog.INGAME_CHAT_LOG.doLog("Cant find command!");
    }

}
