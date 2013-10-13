package me.burnblader.ncptesting;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_6_R3.DataWatcher;
import net.minecraft.server.v1_6_R3.Packet20NamedEntitySpawn;
import net.minecraft.server.v1_6_R3.Packet29DestroyEntity;
import net.minecraft.server.v1_6_R3.Packet33RelEntityMoveLook;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class Human{
	
	String name;
	World world;
	int id;
	Location l;
	int itemInHand;
	
	private List<Integer> ids = new ArrayList<Integer>();
	
	public Human(World w, String name, int id, Location l, int itemInHand) {
		this.name = name;
		this.world = w;
		this.id = id;
		this.l = l;
		this.itemInHand = itemInHand;
		Packet20NamedEntitySpawn spawn = new Packet20NamedEntitySpawn();
		spawn.a = id;
		spawn.b = name;
		spawn.c = l.getBlockX() * 32;
		spawn.d = l.getBlockY() * 32;
		spawn.e = l.getBlockZ() * 32;
		spawn.f = getCompressedAngle(l.getYaw());
		spawn.g = getCompressedAngle(l.getPitch());
		spawn.h = itemInHand;
		DataWatcher d = new DataWatcher();
		d.a(0, (Object) (byte) 0);
		d.a(1, (Object) (short) 0);
		d.a(8, (Object) (byte) 0);
		try
		{
		    Field nameField = Packet20NamedEntitySpawn.class.getDeclaredField("i");
		    nameField.setAccessible(true);
		    nameField.set(spawn, d);
		} catch(Exception e) {
			e.printStackTrace();
		}
		for(Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawn);
		}
		ids.add(id);
	}
	
	private byte getCompressedAngle(float value) {
		return (byte) (value * 256.0F / 360.0F);
	}
	
	public void remove() {
		Packet29DestroyEntity packet = new Packet29DestroyEntity(id);
		for(Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void setItemInHand(ItemStack i) {
		Packet29DestroyEntity packet = new Packet29DestroyEntity(id);
		for(Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
		Packet20NamedEntitySpawn spawn = new Packet20NamedEntitySpawn();
		spawn.a = id;
		spawn.b = name;
		spawn.c = l.getBlockX() * 32;
		spawn.d = l.getBlockY() * 32;
		spawn.e = l.getBlockZ() * 32;
		spawn.f = getCompressedAngle(l.getYaw());
		spawn.g = getCompressedAngle(l.getPitch());
		spawn.h = i.getTypeId();
		itemInHand = i.getTypeId();
		DataWatcher d = new DataWatcher();
		d.a(0, (Object) (byte) 0);
		d.a(1, (Object) (short) 0);
		d.a(8, (Object) (byte) 0);
		try
		{
		    Field nameField = Packet20NamedEntitySpawn.class.getDeclaredField("i");
		    nameField.setAccessible(true);
		    nameField.set(spawn, d);
		} catch(Exception e) {
			e.printStackTrace();
		}
		for(Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawn);
		}
	}
	
	public void walk(double x, double y, double z) {
		byte d = (byte) x;
		byte e = (byte) y;
		byte f = (byte) z;
		Packet33RelEntityMoveLook packet = new Packet33RelEntityMoveLook();
		packet.a = id;
		packet.b = d;
		packet.c = e;
		packet.d = f;
		packet.e = getCompressedAngle(l.getYaw());
		packet.f = getCompressedAngle(l.getPitch());
		for(Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
		l.setX(d);
		l.setY(e);
		l.setZ(f);
	}
	
	public double getX() {
		return l.getX();
	}
	
	public double getY() {
		return l.getY();
	}
	
	public double getZ() {
		return l.getZ();
	}
	
}
