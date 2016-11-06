package net.dusterthefirst.simplespigot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.dusterthefirst.simplespigot.util.NotifManager;

public class EventListener implements Listener{
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public static synchronized void onJoin(PlayerJoinEvent e){
		new BukkitRunnable() {
			@Override
			public void run() {
				PluginClass.updateSimplePlayerList();
				PluginClass.updatePlayerList();
			}
		}.runTaskLaterAsynchronously(PluginClass.getPlugin(), 10L);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public static synchronized void onLeave(PlayerQuitEvent e){
		new BukkitRunnable() {
			@Override
			public void run() {
				PluginClass.updateSimplePlayerList();
				PluginClass.updatePlayerList();
			}
		}.runTaskLaterAsynchronously(PluginClass.getPlugin(), 10L);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public static synchronized void onKick(PlayerKickEvent e){
		new BukkitRunnable() {
			@Override
			public void run() {
				PluginClass.updateSimplePlayerList();
				PluginClass.updatePlayerList();
			}
		}.runTaskLaterAsynchronously(PluginClass.getPlugin(), 10L);
		NotifManager.alert(null, null);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public static synchronized void onDeath(PlayerDeathEvent e){
		new BukkitRunnable() {
			@Override
			public void run() {
				PluginClass.updateSimplePlayerList();
				PluginClass.updatePlayerList();
			}
		}.runTaskLaterAsynchronously(PluginClass.getPlugin(), 10L);
	}
}
