package me.puplefishh.dipcraft.litefly.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.puplefishh.dipcraft.litefly.main.LF_Main;
import me.puplefishh.dipcraft.litefly.utilitis.LF_FlyItem;

public class LF_FlyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (!((Player) sender).getInventory().contains(LF_FlyItem.FlyItem())) {
				((Player) sender).getInventory().addItem(LF_FlyItem.FlyItem());
				sender.sendMessage(LF_Main.FlyItemReceive());
			} else {
				sender.sendMessage(LF_Main.FlyItemHad());
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Only players can execute that command!");
		}
		return true;
	}

}
