package xyz.ng7.dynamicicon;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.CachedServerIcon;

public class DynamicIcon extends JavaPlugin implements Listener {
	CachedServerIcon[] icons;

	@Override
	public void onLoad() {
		if (!this.getDataFolder().exists()) this.getDataFolder().mkdirs();
	}

	@Override
	public void onEnable() {
		icons = getIcons();
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void change(ServerListPingEvent e) {
		try {
			CachedServerIcon i = chooseIcon();
			if (i != null) e.setServerIcon(i);
		} catch (Exception ignored) {}
	}

	private CachedServerIcon[] getIcons() {
		File[] files = this.getDataFolder().listFiles(new ServerIconFilter());
		CachedServerIcon[] icons = new CachedServerIcon[files.length];

		for (int i = 0; i < files.length; i++) {
			try {
				icons[i] = this.getServer().loadServerIcon(files[i]);
			} catch (Exception ignored) {}
		}

		return icons;
	}

	private CachedServerIcon chooseIcon() {
		if (icons.length != 0) return icons[ThreadLocalRandom.current().nextInt(icons.length)];
		return null;
	}
}
