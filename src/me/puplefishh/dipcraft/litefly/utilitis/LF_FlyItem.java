package me.puplefishh.dipcraft.litefly.utilitis;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.puplefishh.dipcraft.litefly.main.LF_Main;

public class LF_FlyItem {

	static private LF_Main plugin = LF_Main.plugin;

	public static ItemStack FlyItem() {
		int id = plugin.getConfig().getInt("Fly-Item-Id");
		@SuppressWarnings("deprecation")
		ItemStack item = new ItemStack(Material.getMaterial(id));
		ItemMeta meta = item.getItemMeta();
		String name = Form(plugin.getConfig().getString("Fly-Item-Name"));
		meta.setDisplayName(name);
		List<String> lore = new ArrayList<String>();
		if(plugin.getConfig().isList("Fly-Item-Lore"))
		for (String key : plugin.getConfig().getStringList("Fly-Item-Lore")) {
			lore.add(Form(key));
		}else if(plugin.getConfig().isString("Fly-Item-Lore"))
			lore.add(Form(plugin.getConfig().getString("Fly-Item-Lore")));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	private static String Form(String mess) {
		mess = ChatColor.translateAlternateColorCodes('&', mess);
		mess = ChatColor.translateAlternateColorCodes('$', mess);
		mess = ChatColor.translateAlternateColorCodes('%', mess);

		return mess;
	}

}
