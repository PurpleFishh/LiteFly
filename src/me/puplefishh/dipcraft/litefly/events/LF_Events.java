package me.puplefishh.dipcraft.litefly.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.puplefishh.dipcraft.litefly.main.LF_Main;
import me.puplefishh.dipcraft.litefly.utilitis.LF_Fly;
import me.puplefishh.dipcraft.litefly.utilitis.LF_FlyItem;

public class LF_Events implements Listener {

	public LF_Main plugin;

	private ItemStack item = LF_FlyItem.FlyItem();

	public LF_Events(LF_Main pl) {
		plugin = pl;
	}

	@EventHandler
	public void NoDamager(EntityDamageEvent e) {
		if (e.getCause().equals(DamageCause.FALL)) {
			if (plugin.fly.containsKey(e.getEntity()))
				if (plugin.fly.get(e.getEntity()) == true) {
					e.setCancelled(true);
				}
			if (plugin.flying.containsKey(e.getEntity()))
				if (plugin.flying.get(e.getEntity()) == true) {
					plugin.flying.replace((Player) e.getEntity(), false);
					e.setCancelled(true);
				}
		}
	}

	@EventHandler
	public void ItemFly(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem() != null && e.getItem().getType() != Material.AIR) {
				if (e.getItem().equals(item)) {
					Player p = e.getPlayer();
					if (p.getFoodLevel() != 0) {
						new LF_Fly(plugin, p);
					} else {
						p.sendMessage(plugin.FH());
					}
				}
			}

		}
	}

}
