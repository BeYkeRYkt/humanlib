package me.burnblader.ncptesting;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_6_R3.DataWatcher;
import net.minecraft.server.v1_6_R3.EntityHuman;
import net.minecraft.server.v1_6_R3.Packet20NamedEntitySpawn;
import net.minecraft.server.v1_6_R3.Packet29DestroyEntity;
import net.minecraft.server.v1_6_R3.Packet33RelEntityMoveLook;
import net.minecraft.server.v1_6_R3.Packet5EntityEquipment;

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
	
	public Human(EntityHuman h) {
		Packet20NamedEntitySpawn spawn = new Packet20NamedEntitySpawn(h);
		int id = new Random().nextInt(5000-1000)+1000;
		spawn.a = id;
		this.id = id;
		Packet5EntityEquipment armor1 = new Packet5EntityEquipment(id, 1, h.inventory.getArmorContents()[0]);
		Packet5EntityEquipment armor2 = new Packet5EntityEquipment(id, 2, h.inventory.getArmorContents()[1]);
		Packet5EntityEquipment armor3 = new Packet5EntityEquipment(id, 3, h.inventory.getArmorContents()[2]);
		Packet5EntityEquipment armor4 = new Packet5EntityEquipment(id, 4, h.inventory.getArmorContents()[3]);
		Packet5EntityEquipment sword = new Packet5EntityEquipment(id, 0, h.inventory.getItem(0));
		for(Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawn);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(armor1);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(armor2);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(armor3);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(armor4);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(sword);
		}
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
	
	public void walk(double a, double b, double c) {
		byte x = (byte) a;
		byte y = (byte) b;
		byte z = (byte) c;
		Packet33RelEntityMoveLook packet = new Packet33RelEntityMoveLook();
		packet.a = id;
		packet.b = x;
		packet.c = y;
		packet.d = z;
		packet.e = getCompressedAngle(l.getYaw());
		packet.f = getCompressedAngle(l.getPitch());
		for(Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
		l.setX(x);
		l.setY(y);
		l.setZ(z);
	}
	
	public void setInvisible() {
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
		spawn.h = itemInHand;
		DataWatcher d = new DataWatcher();
		d.a(0, (Object) (byte) 32);
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
	
	public void setCrouched() {
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
		spawn.h = itemInHand;
		DataWatcher d = new DataWatcher();
		d.a(0, (Object) (byte) 2);
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
	
	public void reset() {
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
	}
	
	public void sprint() {
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
		spawn.h = itemInHand;
		DataWatcher d = new DataWatcher();
		d.a(0, (Object) (byte) 8);
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
	
	public void block() {
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
		spawn.h = itemInHand;
		DataWatcher d = new DataWatcher();
		d.a(0, (Object) (byte) 16);
		d.a(1, (Object) (short) 0);
		d.a(6, (Object) (byte) 0);
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
