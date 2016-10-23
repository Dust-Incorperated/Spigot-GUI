package net.dusterthefirst.simplespigot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.bukkit.entity.Player;

import net.dusterthefirst.simplespigot.gui.MasterWindow;

public class Manager {
	
	static MasterWindow window;
	static ProcessBuilder builder;
	static Process process;
	static File serverFolder, serverFile, outputStream = new File("/");
	
	public static void init(MasterWindow window, PrintStream out){
		Manager.window = window;
	}

	public static void start(){
		window.comboBox.getSelectedItem();
		CommandLine cmdLine = new CommandLine("java");
		cmdLine.addArgument("-jar");
		cmdLine.addArgument((String) window.comboBox.getSelectedItem());

		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

		ExecuteWatchdog watchdog = new ExecuteWatchdog(60*1000);
		Executor executor = new DefaultExecutor();
		executor.setExitValue(1);
		executor.setWatchdog(watchdog);
		try {
			executor.execute(cmdLine, resultHandler);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String readOut() throws IOException {
		BufferedReader reader = 
                new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line = null;
		while ( (line = reader.readLine()) != null) {
		   builder.append(line);
		   builder.append(System.getProperty("line.separator"));
		}
		return builder.toString();
	}

	public static void stop(){
		System.out.println("STOP");
	}
	
	public static void restart(){
		System.out.println("REBUT");
	}
	
	public static void setJarPath(){
		builder = new ProcessBuilder("java", "-jar", window.comboBox.getSelectedItem().toString());
		System.out.println(window.comboBox.getSelectedItem().toString());
		builder.redirectErrorStream(true);
	}
	
	public static void runCommand(String cmd){
		
	}
	
	public static void setRam(){
		
	}
	
	public static Player[] getPlayers(){
		return null;	
	}
	
}
