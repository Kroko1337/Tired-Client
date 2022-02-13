package beta.tiredb56.tired;

import beta.tiredb56.api.femboydrawer.ExternalImageDrawer;
import beta.tiredb56.api.guis.femdrawer.GuiFemboy;
import beta.tiredb56.api.util.DiscordRPC;
import beta.tiredb56.command.CommandManager;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.notification.NotificationRenderer;
import beta.tiredb56.event.EventManager;
import beta.tiredb56.api.logger.impl.ErrorLog;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public enum TiredCore {

    CORE;

    public EventManager eventManager;

    public NotificationRenderer notificationRenderer;
    public CommandManager commandManager;
    public static ArrayList<ExternalImageDrawer> externalImageDrawers = new ArrayList<>();
    public static ArrayList<File> resourceLocations = new ArrayList<>();

    public boolean onSendChatMessage(String s) {
        if (s.startsWith(".")) {
            commandManager.execute(s.substring(1));
            return false;
        }
        return true;
    }

    @SneakyThrows
    public void initCore() {
        if (eventManager == null) {
            ErrorLog.ERROR_LOG.doLog("Event manager is null!");
        }
        this.eventManager = EventManager.SINGLETON;
        this.commandManager = new CommandManager();
        this.notificationRenderer = new NotificationRenderer();
        CheatMain.INSTANCE.discordRPC = new DiscordRPC();
        CheatMain.INSTANCE.discordRPC.start();

        final File folder = new File(IHook.MC.mcDataDir + "/" + CheatMain.INSTANCE.CLIENT_NAME + "/femboys");

        Collections.addAll(resourceLocations, Objects.requireNonNull(folder.listFiles()));
        for (File location : resourceLocations) {
            File inFile = new File(location.getPath());

            if (GuiFemboy.isPng(inFile)) {

                BufferedImage iis = ImageIO.read(new FileInputStream(inFile));

                externalImageDrawers.add(new ExternalImageDrawer(iis));

            }
        }


    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
