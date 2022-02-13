package beta.tiredb56.module.impl.list.movement;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@ModuleAnnotation(name = "InventoryMove", category = ModuleCategory.MOVEMENT, clickG = "You can move in the inventory")
public class InventoryMove extends Module {

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        if ((MC.currentScreen instanceof GuiContainer) || MC.currentScreen instanceof GuiGameOver && !MC.playerController.isInCreativeMode()) {
            handleKey(MC.gameSettings.keyBindLeft);
            handleKey(MC.gameSettings.keyBindRight);
            handleKey(MC.gameSettings.keyBindBack);
            handleKey(MC.gameSettings.keyBindJump);
            handleKey(MC.gameSettings.keyBindSneak);
        }
    }

    public void handleKey(KeyBinding keyBinding) {
        keyBinding.pressed = isDown(keyBinding.getKeyCode());
    }

    public boolean isDown(int key) {
        if(key < 0) {
            while (Mouse.next()) {
                int i = Mouse.getEventButton();
                return i - 100 == key;
            }
        }
        return Keyboard.isKeyDown(key);
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
