package me.puplefishh.dipcraft.litefly.utilitis;

import org.bukkit.entity.Player;

import me.puplefishh.dipcraft.litefly.main.LF_Main;
import me.puplefishh.dipcraft.litefly.utilitis.api.Titles;

public class LF_Fly {

	private LF_Main plugin;
	private Player p;

	public LF_Fly(LF_Main plugin, Player p) {
		this.plugin = plugin;
		this.p = p;
		Fly();
	}

	public void Fly() {
		if (!plugin.fly.containsKey(p)) {
			plugin.fly.put(p, false);
		}
		
		if (plugin.fly.get(p) == false) {
			p.sendMessage(plugin.FE());
			Titles.sendTitle(p, 20,20,20,plugin.getConfig().getString("Fly-Activate-Message"), "",true);
			p.setAllowFlight(true);
			plugin.fly.replace(p, true);
		} else if (plugin.fly.get(p) == true) {
			p.sendMessage(plugin.FD());
			Titles.sendTitle(p, 20,20,20,plugin.getConfig().getString("Fly-Disable-Message"), "",true);
			if (!p.isFlying()) {
				p.setAllowFlight(false);
				plugin.fly.replace(p, false);
			} else {
				if (!plugin.flying.containsKey(p)) {
					plugin.flying.put(p, true);
				} else {
					plugin.flying.replace(p, true);
				}
				p.setAllowFlight(false);
				plugin.fly.replace(p, false);
			}

		}

	}

}
