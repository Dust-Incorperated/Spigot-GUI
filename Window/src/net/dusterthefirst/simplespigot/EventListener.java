package net.dusterthefirst.simplespigot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.dusterthefirst.simplespigot.util.NotifManager;
import net.dusterthefirst.simplespigot.util.NotifManager.NotifType;

public class EventListener implements Listener{
	
	public static NotifType notif;
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public synchronized void onJoin(PlayerJoinEvent e){
		new BukkitRunnable() {
			@Override
			public void run() {
				PluginClass.updateSimplePlayerList();
				PluginClass.updatePlayerList();
			}
		}.runTaskLaterAsynchronously(PluginClass.getPlugin(), 10L);
		NotifManager.alert(notif, e.getPlayer().getDisplayName() + " Joined With A UUID Of, " + e.getPlayer().getUniqueId());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public synchronized void onLeave(PlayerQuitEvent e){
		new BukkitRunnable() {
			@Override
			public void run() {
				PluginClass.updateSimplePlayerList();
				PluginClass.updatePlayerList();
			}
		}.runTaskLaterAsynchronously(PluginClass.getPlugin(), 10L);
		NotifManager.alert(notif, e.getPlayer().getDisplayName() + " Disconnected With A Reason Of, " + e.getQuitMessage());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public synchronized void onDeath(PlayerDeathEvent e){
		new BukkitRunnable() {
			@Override
			public void run() {
				PluginClass.updateSimplePlayerList();
				PluginClass.updatePlayerList();
			}
		}.runTaskLaterAsynchronously(PluginClass.getPlugin(), 10L);
	}
}
