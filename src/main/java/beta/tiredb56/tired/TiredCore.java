package beta.tiredb56.tired;

import beta.tiredb56.api.util.DiscordRPC;
import beta.tiredb56.command.CommandManager;
import beta.tiredb56.notification.NotificationRenderer;
import beta.tiredb56.event.EventManager;
import beta.tiredb56.api.logger.impl.ErrorLog;


public enum TiredCore {

    CORE;

    public EventManager eventManager;

    public NotificationRenderer notificationRenderer;
    public CommandManager commandManager;

    public boolean onSendChatMessage(String s) {
        if (s.startsWith(".")) {
            commandManager.execute(s.substring(1));
            return false;
        }
        return true;
    }

    public void initCore() {
        if (eventManager == null) {
            ErrorLog.ERROR_LOG.doLog("Event manager is null!");
        }
        this.eventManager = EventManager.SINGLETON;
        this.commandManager = new CommandManager();
        this.notificationRenderer = new NotificationRenderer();
        CheatMain.INSTANCE.discordRPC = new DiscordRPC();
        CheatMain.INSTANCE.discordRPC.start();
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
