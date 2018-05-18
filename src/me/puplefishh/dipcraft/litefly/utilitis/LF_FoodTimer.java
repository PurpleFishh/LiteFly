package me.puplefishh.dipcraft.litefly.utilitis;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import me.puplefishh.dipcraft.litefly.main.LF_Main;
import me.puplefishh.dipcraft.litefly.utilitis.api.Titles;

public class LF_FoodTimer implements Runnable {

	private LF_Main plugin = LF_Main.plugin;


	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.isFlying())
				if (plugin.fly.containsKey(p)) {
					if (p.getGameMode() != GameMode.CREATIVE) {
						if (!p.hasPermission("litefood.hunger")) {
							plugin = LF_Main.plugin;
							if (plugin.fly.get(p)) {
								p.setFoodLevel(p.getFoodLevel() - 1);
								if (p.getFoodLevel() == 0) {
									{
										p.sendMessage(plugin.FDH());
										Titles.sendTitle(p, 20,20,20,plugin.getConfig().getString("Disable-for-Hunger-Message"), "",true);
									}
								}
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
				}
		}
	}

}
