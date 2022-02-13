package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.util.font.CustomFont;
import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.api.util.renderapi.AnimationUtil;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.Render2DEvent;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.event.events.EventKey;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleManager;
import beta.tiredb56.tired.ShaderRenderer;
import beta.tiredb56.tired.CheatMain;
import net.minecraft.client.gui.Gui;

import java.awt.*;

@ModuleAnnotation(name = "TabGui", category = ModuleCategory.RENDER)
public class TabGui extends Module {

	private boolean usingCategory;
	public int cTab, mTab;

	public float height;
	public float animationX;
	public float modCountSus;
	public float animationYAxis;

	public TabGui() {
		this.cTab = 0;
		this.mTab = 0;
	}

	public static TabGui getInstance() {
		return ModuleManager.getInstance(TabGui.class);
	}

	public void render(boolean rect) {
		final double x = 2.0;
		double y = 42.0;


		final ModuleCategory selectedCategory = ModuleCategory.values()[this.cTab];

		final double yAxisAdditional = 15, WIDTH = 90, moduleWidth = 90;

		int color;
		for (final ModuleCategory c : ModuleCategory.values()) {
			final boolean isCategoryHovered = selectedCategory == c;
			color = isCategoryHovered ? ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().brighter().getRGB()  : new Color(20, 20, 20).getRGB();
			float value = (isCategoryHovered ? 2 : 0);
			animationX = (float) AnimationUtil.getAnimationState(animationX, WIDTH, 40);
			if (rect) {
				Gui.drawRect(x, y, x + animationX, y + yAxisAdditional, new Color(20, 20, 20, 210).getRGB());
			}
			if (isCategoryHovered) {
				Gui.drawRect(x, y, x + animationX, y + yAxisAdditional, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());
			}
			FontManager.bebasF.drawStringWithShadow(c.displayName, calculateMiddle(c.displayName, FontManager.bebasF, (float) x, (float) WIDTH), y + 1, -1);
			height = (float) (y + yAxisAdditional);
			y += yAxisAdditional;

		}

		double modCount = 14.0;
		if (this.usingCategory) {
			for (final Module m : CheatMain.INSTANCE.moduleManager.getModules(selectedCategory)) {
				final boolean isModuleHovered = m == CheatMain.INSTANCE.moduleManager.getModules(selectedCategory).get(this.mTab);

				animationYAxis = (float) AnimationUtil.getAnimationState(animationYAxis, yAxisAdditional, 20);
				int textColor = m.isState() ? Integer.MAX_VALUE : -1;
				color = isModuleHovered ? ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().brighter().getRGB() : new Color(20, 20, 20).getRGB();
				if (rect) {
					Gui.drawRect(x + WIDTH + 1.0, modCount, x + WIDTH + moduleWidth + 1.0, modCount + yAxisAdditional,  new Color(20, 20, 20, 210).getRGB());
				}
				if (isModuleHovered) {
					Gui.drawRect(x + WIDTH + 1.0, modCount, x + WIDTH + moduleWidth + 1.0, modCount + yAxisAdditional, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());
				}
				FontManager.bebasF.drawStringWithShadow(m.getName(), x + 4.0 + WIDTH + (isModuleHovered ? 2 : -1), modCount + 2.0, textColor);

				if (m.getName().length() > 12) {
					//System.out.println("jew" + m.getName());
					//	Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawGradientSideways(x + WIDTH + 42.0, modCount, x + WIDTH + moduleWidth + 1.0 + 7, modCount + yAxisAdditional, new Color(20,20,20,64).getRGB(),new Color(0,0,0,244).getRGB());
				}

				modCount += yAxisAdditional;
				modCountSus = (float) modCount;
			}
		}

	}

	@EventTarget
	public void onKey(final EventKey e) {
		final int key = e.getKey();
		final ModuleCategory selectedCategory = ModuleCategory.values()[this.cTab];
		if (key == 200) {
			if (this.usingCategory) {
				if (this.mTab <= 0) {
					this.mTab = CheatMain.INSTANCE.moduleManager.getModules(selectedCategory).size() - 1;
				} else {
					--this.mTab;
				}
			} else if (this.cTab <= 0) {
				this.cTab = ModuleCategory.values().length - 1;
			} else {
				--this.cTab;
			}
		}
		if (key == 208) {
			if (this.usingCategory) {
				if (this.mTab >= CheatMain.INSTANCE.moduleManager.getModules(selectedCategory).size() - 1) {
					this.mTab = 0;
				} else {
					++this.mTab;
				}
			} else if (this.cTab >= ModuleCategory.values().length - 1) {
				this.cTab = 0;
			} else {
				++this.cTab;
			}
		}
		if (key == 205) {
			if (this.usingCategory) {
				final Module mod = CheatMain.INSTANCE.moduleManager.getModules(selectedCategory).get(this.mTab);
				mod.setState(!mod.isState());
			} else {
				this.mTab = 0;
			}
			this.usingCategory = true;
		}
		if (key == 203) {
			this.mTab = 0;
			this.usingCategory = false;
		}
	}

	@Override
	public void onState() {

	}
	@Override
	public void onUndo() {

	}


	public int calculateMiddle(String text, CustomFont fontRenderer, float x, float widht) {
		return (int) ((float) (x + widht) - (fontRenderer.getStringWidth(text) / 2f) - (float) widht / 2);
	}
}
