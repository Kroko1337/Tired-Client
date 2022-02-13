package beta.tiredb56.api.logger.impl;

import beta.tiredb56.api.annotations.LoggerAnnotation;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.tired.CheatMain;
import net.minecraft.util.ChatComponentText;

@LoggerAnnotation(error = false, minecraft = true)
public enum IngameChatLog implements IHook {

    INGAME_CHAT_LOG;

    public void doLog(String text) {
        MC.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("[" + CheatMain.INSTANCE.CLIENT_NAME +"] " + text));
    }




}
