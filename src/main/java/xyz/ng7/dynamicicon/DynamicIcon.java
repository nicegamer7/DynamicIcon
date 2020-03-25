package xyz.ng7.dynamicicon;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DynamicIcon extends JavaPlugin implements Listener {
	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void change(ServerListPingEvent e) {
		try {
			File i = chooseIcon();
			if (i != null) this.getServer().loadServerIcon(i);
		} catch (Exception ignored) {}
	}
		
	private File chooseIcon() {
		File dir = getDataFolder();
		File[] files = dir.listFiles(new ServerIconFilter());
		if (files.length != 0) return files[ThreadLocalRandom.current().nextInt(files.length)];
		return null;
	}
}
