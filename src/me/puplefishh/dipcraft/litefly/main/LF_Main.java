package me.puplefishh.dipcraft.litefly.main;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.puplefishh.dipcraft.litefly.commands.LF_FlyCommand;
import me.puplefishh.dipcraft.litefly.events.LF_Events;
import me.puplefishh.dipcraft.litefly.utilitis.LF_FoodTimer;

public class LF_Main extends JavaPlugin {

	public HashMap<Player, Boolean> fly = new HashMap<>();
	public HashMap<Player, Boolean> flying = new HashMap<>();
	static ConfigurationSection config;
	public static LF_Main plugin;

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		plugin = this;
		RegisterCommands();
		RegisterConfig();
		RegisterEvents();
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new LF_FoodTimer(), 20L, 20L * TimeHunger());
		config = this.getConfig();
		Server server = getServer();
		ConsoleCommandSender console = server.getConsoleSender();

		console.sendMessage("+==================================+");
		console.sendMessage("+                                  +");
		console.sendMessage("+            LiteFly               +");
		console.sendMessage("+            Enable!               +");
		console.sendMessage("+                                  +");
		console.sendMessage("+==================================+");
	}

	@Override
	public void onDisable() {
		System.out.println("Disable!");
	}

	

	private void RegisterConfig() {
		saveDefaultConfig();
	}

	private void RegisterCommands() {
		this.getCommand("fly").setExecutor(new LF_FlyCommand());
	}

	private void RegisterEvents() {
		PluginManager plmanager = Bukkit.getPluginManager();
		plmanager.registerEvents(new LF_Events(this), this);
	}

	public int TimeHunger() {
		int t = this.getConfig().getInt("Hunger");
		return t;
	}

	public static ConfigurationSection Config()
	{
		return config;
	}
	public static String WM() {
		String wm = "";
		wm = Config().getString("Water-Mark");
		wm.replaceAll("&", "§");
		wm = ChatColor.translateAlternateColorCodes('&', wm);
		wm = ChatColor.translateAlternateColorCodes('$', wm);
		wm = ChatColor.translateAlternateColorCodes('%', wm);
		return wm;
	}

	public String FE() {
		String fe = "";

		fe = getConfig().getString("Fly-Activate-Message");
		fe.replaceAll("&", "§");
		fe = ChatColor.translateAlternateColorCodes('&', fe);
		fe = ChatColor.translateAlternateColorCodes('$', fe);
		fe = ChatColor.translateAlternateColorCodes('%', fe);

		return WM() + " " + fe;
	}

	public String FD() {
		String fd = "";

		fd = getConfig().getString("Fly-Disable-Message");
		fd.replaceAll("&", "§");
		fd = ChatColor.translateAlternateColorCodes('&', fd);
		fd = ChatColor.translateAlternateColorCodes('$', fd);
		fd = ChatColor.translateAlternateColorCodes('%', fd);

		return WM() + " " + fd;
	}

	public String FDH() {
		String fdh = "";
		fdh = getConfig().getString("Disable-for-Hunger-Message");
		fdh.replaceAll("&", "§");
		fdh = ChatColor.translateAlternateColorCodes('&', fdh);
		fdh = ChatColor.translateAlternateColorCodes('$', fdh);
		fdh = ChatColor.translateAlternateColorCodes('%', fdh);
		return WM() + fdh;
	}

	public String FH() {
		String fd = "";

		fd = getConfig().getString("Activate-Without-Food-Message");
		fd.replaceAll("&", "§");
		fd = ChatColor.translateAlternateColorCodes('&', fd);
		fd = ChatColor.translateAlternateColorCodes('$', fd);
		fd = ChatColor.translateAlternateColorCodes('%', fd);

		return WM() + " " + fd;
	}
	public static String FlyItemHad() {
		String fd = "";

		fd = Config().getString("Fly-Item-Had");
		fd.replaceAll("&", "§");
		fd = ChatColor.translateAlternateColorCodes('&', fd);
		fd = ChatColor.translateAlternateColorCodes('$', fd);
		fd = ChatColor.translateAlternateColorCodes('%', fd);

		return WM() + " " + fd;
	}
	public static String FlyItemReceive() {
		String fd = "";

		fd = Config().getString("Fly-Item-Receive");
		fd.replaceAll("&", "§");
		fd = ChatColor.translateAlternateColorCodes('&', fd);
		fd = ChatColor.translateAlternateColorCodes('$', fd);
		fd = ChatColor.translateAlternateColorCodes('%', fd);

		return WM() + " " + fd;
	}
}
