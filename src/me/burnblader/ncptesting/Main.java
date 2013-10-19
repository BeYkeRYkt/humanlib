package me.burnblader.ncptesting;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	public void onEnable() {
		registerCommands();
	}
	
	public void onDisable() {
		
	}
	
	void registerCommands() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(label.equalsIgnoreCase("npc")) {
			if(args.length == 1) {
				Player player = (Player) sender;
				@SuppressWarnings("deprecation")
				Human h = new Human(player.getWorld(), args[0], new Random().nextInt(5000-1000)+1000, player.getLocation(), player.getItemInHand().getTypeId());
				h.setItemInHand(new ItemStack(Material.BOW));
				h.addPotionColor(Color.PURPLE.asBGR());
			}
		}
		
		return false;
	}
	
}
