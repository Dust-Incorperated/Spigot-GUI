package net.dusterthefirst.simplespigot;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import net.dusterthefirst.windowsxp.MasterWindow;

public class Plugin extends JavaPlugin{

	private Server SERVER = this.getServer();
	private MasterWindow frame;
	
	@Override
	public void onLoad() {
		frame = new MasterWindow();
	}
	
	@Override
	public void onEnable() {
		frame.setVisible(true);
	}
	
	@Override
	public void onDisable() {
		frame.dispose();
	}
	
}
