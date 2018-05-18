package me.puplefishh.dipcraft.litefly.utilitis.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Titles {
	@Deprecated
	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message,
			boolean color) {
		sendTitle(player, fadeIn, stay, fadeOut, message, null, color);
	}

	@Deprecated
	public static void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message,
			boolean color) {
		sendTitle(player, fadeIn, stay, fadeOut, null, message, color);
	}

	@Deprecated
	public static void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title,
			String subtitle, boolean color) {
		sendTitle(player, fadeIn, stay, fadeOut, title, subtitle, color);
	}

	public static void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Class<?> getNMSClass(String name) {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String color(String msg) {
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		msg = ChatColor.translateAlternateColorCodes('$', msg);
		msg = ChatColor.translateAlternateColorCodes('%', msg);
		return msg;
	}

	@SuppressWarnings("rawtypes")
	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title,
			String subtitle, boolean color) {
		TitleSendEvent titleSendEvent = new TitleSendEvent(player, title, subtitle);
		Bukkit.getPluginManager().callEvent(titleSendEvent);
		if (titleSendEvent.isCancelled())
			return;

		try {
			Object e;
			Object chatTitle;
			Object chatSubtitle;
			Constructor subtitleConstructor;
			Object titlePacket;
			Object subtitlePacket;

			if (title != null) {
				title = color(title);
				// Times packets
				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object) null);
				chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
						.getMethod("a", new Class[] { String.class })
						.invoke((Object) null, new Object[] { "{\"text\":\"" + title + "\"}" });
				subtitleConstructor = getNMSClass("PacketPlayOutTitle")
						.getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
								getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
				titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle, fadeIn, stay, fadeOut });
				sendPacket(player, titlePacket);

				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get((Object) null);
				chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
						.getMethod("a", new Class[] { String.class })
						.invoke((Object) null, new Object[] { "{\"text\":\"" + title + "\"}" });
				subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] {
						getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent") });
				titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle });
				sendPacket(player, titlePacket);
			}

			if (subtitle != null) {
				subtitle = color(subtitle);
				// Times packets
				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object) null);
				chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
						.getMethod("a", new Class[] { String.class })
						.invoke((Object) null, new Object[] { "{\"text\":\"" + title + "\"}" });
				subtitleConstructor = getNMSClass("PacketPlayOutTitle")
						.getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
								getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
				subtitlePacket = subtitleConstructor
						.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
				sendPacket(player, subtitlePacket);

				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get((Object) null);
				chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
						.getMethod("a", new Class[] { String.class })
						.invoke((Object) null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
				subtitleConstructor = getNMSClass("PacketPlayOutTitle")
						.getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
								getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
				subtitlePacket = subtitleConstructor
						.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
				sendPacket(player, subtitlePacket);
			}
		} catch (Exception var11) {
			var11.printStackTrace();
		}
	}

	public static void clearTitle(Player player) {
		sendTitle(player, 0, 0, 0, "", "", false);
	}

	public static void sendTabTitle(Player player, String header, String footer) {
		if (header == null)
			header = "";
		header = color(header);

		if (footer == null)
			footer = "";
		footer = color(footer);

		TabTitleSendEvent tabTitleSendEvent = new TabTitleSendEvent(player, header, footer);
		Bukkit.getPluginManager().callEvent(tabTitleSendEvent);
		if (tabTitleSendEvent.isCancelled())
			return;

		header = header.replaceAll("%player%", player.getDisplayName());
		footer = footer.replaceAll("%player%", player.getDisplayName());

		try {
			Object tabHeader = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\":\"" + header + "\"}");
			Object tabFooter = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\":\"" + footer + "\"}");
			Constructor<?> titleConstructor = getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor();
			Object packet = titleConstructor.newInstance();
			Field aField = packet.getClass().getDeclaredField("a");
			aField.setAccessible(true);
			aField.set(packet, tabHeader);
			Field bField = packet.getClass().getDeclaredField("b");
			bField.setAccessible(true);
			bField.set(packet, tabFooter);
			sendPacket(player, packet);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
