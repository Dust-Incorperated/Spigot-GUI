package net.dusterthefirst.simplespigot;

import java.io.IOException;
import java.io.PrintStream;

import org.bukkit.ChatColor;

import net.dusterthefirst.windowsxp.ConsoleOutputStream;
import net.dusterthefirst.windowsxp.MasterWindow;
import net.ftb.util.OSUtils;
import net.ftb.util.OSUtils.OS;

public class Master {
	
	public enum BIT{
		a64on32, a32on64, a64on64, a32on32, nil;
	}
	
	static MasterWindow window;
	static PrintStream out, err;
	
	public static void main(String[] args) throws IOException{
		createWindow(OSUtils.getCurrentOS(), OSUtils.getOSTotalMemory(), OSUtils.getCores());
		setConsole();
		ProcessBuilder builder = new ProcessBuilder("hello");
		builder.redirectErrorStream(true);
		//Process process = builder.start();
		System.out.println(ChatColor.RED + "R" + ChatColor.GOLD + "G" +  ChatColor.DARK_GRAY + "GR" +  ChatColor.DARK_PURPLE + "P");
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

	static void setConsole(){
		out = new PrintStream(new ConsoleOutputStream(window, ConsoleColors.BLACK));
		err = new PrintStream(new ConsoleOutputStream(window, ConsoleColors.RED));
		System.setOut(out);
		System.setErr(err);
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