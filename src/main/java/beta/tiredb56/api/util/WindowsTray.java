package beta.tiredb56.api.util;

import beta.tiredb56.tired.CheatMain;

import java.awt.*;

public enum WindowsTray {

	WINDOWS_TRAY;

	public void displayTray(String notify) throws AWTException {
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("System tray icon demo");
		tray.add(trayIcon);

		trayIcon.displayMessage(CheatMain.INSTANCE.CLIENT_NAME, notify, TrayIcon.MessageType.INFO);

	}

}
