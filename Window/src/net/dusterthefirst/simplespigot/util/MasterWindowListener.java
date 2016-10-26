package net.dusterthefirst.simplespigot.util;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.bukkit.ChatColor;

import net.dusterthefirst.simplespigot.PluginClass;

public class MasterWindowListener implements WindowListener{

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		int i = PluginClass.window.warnClose();
		if(i == 0){
			PluginClass.window.dispose();
			PluginClass.sendConsoleAMsg(ChatColor.YELLOW + "Stopping Server With SimpleSpigot");
			PluginClass.stopServer();
		}else if(i == 1){
			PluginClass.window.dispose();
			PluginClass.sendConsoleAMsg(ChatColor.YELLOW + "Closed SimpleSpigot On Host");
		}else{
			PluginClass.window.requestFocus();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}
	
}
