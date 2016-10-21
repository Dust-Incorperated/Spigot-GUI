package net.dusterthefirst.simplespigot;

import java.io.IOException;
import java.io.PrintStream;

import org.bukkit.ChatColor;

import net.dusterthefirst.windowsxp.ConsoleOutputStream;
import net.dusterthefirst.windowsxp.MasterWindow;

public class Master {

	static MasterWindow window;
	static PrintStream out, err;
	
	public static void main(String[] args) throws IOException{
		makeConsole();
		ProcessBuilder builder = new ProcessBuilder("hello");
		builder.redirectErrorStream(true);
		//Process process = builder.start();
		System.out.println(ChatColor.RED + "R" + ChatColor.GOLD + "G" +  ChatColor.DARK_GRAY + "GR" +  ChatColor.DARK_PURPLE + "P");
	}
	
	static void makeConsole(){
		window = new MasterWindow();
		window.setVisible(true);
		out = new PrintStream(new ConsoleOutputStream(window, ConsoleColors.BLACK));
		err = new PrintStream(new ConsoleOutputStream(window, ConsoleColors.RED));
		System.setOut(out);
		System.setErr(err);
	}
	
}
