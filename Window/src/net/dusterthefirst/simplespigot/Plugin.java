package net.dusterthefirst.simplespigot;

import java.io.PrintStream;

import org.bukkit.plugin.java.JavaPlugin;

import net.dusterthefirst.simplespigot.gui.MasterWindow;
import net.ftb.util.OSUtils;
import net.ftb.util.OSUtils.OS;

public class Plugin extends JavaPlugin{
	
	public enum BIT{
		a64on32, a32on64, a64on64, a32on32, nil;
	}
	
	static MasterWindow window;
	static PrintStream out, err;
	
	@Override
	public void onLoad() {
		createWindow(OSUtils.getCurrentOS(), OSUtils.getOSTotalMemory(), OSUtils.getCores());
	}
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	private static void createWindow(OS os, long OSmem, int cores) {
		BIT bitness = getBitness();
		window = new MasterWindow(os, OSmem, cores);
		boolean nope = window.warnBits(bitness);
		if(nope){
			window.setVisible(true);
		}else{
			window.dispose();
		}
	}
	
	static BIT getBitness(){
		boolean 
		VMbit = OSUtils.is64BitVM(), 
		OSbit = OSUtils.is64BitOS();
		BIT wrongBit = BIT.nil;
		if(VMbit == true && OSbit == true){
			wrongBit = BIT.a64on64;
		}else if(VMbit == false && OSbit == false){
			wrongBit = BIT.a32on32;
		}else if(VMbit == true && OSbit == false){
			wrongBit = BIT.a64on32;
		}else if(VMbit == false && OSbit == true){
			wrongBit = BIT.a32on64;
		}
		return wrongBit;
	}

	
}
