package net.dusterthefirst.simplespigot;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

import javax.swing.JEditorPane;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import net.dusterthefirst.simplespigot.gui.MasterWindow;
import net.ftb.util.OSUtils;

public class Plugin extends JavaPlugin{
	
	public enum BIT{
		a64on32, a32on64, a64on64, a32on32, nil;
	}
	
	public static MasterWindow window;
	static PrintStream out, err;
	private Server server;
	private File serverProperties, spigotyml, bukkityml, helpyml;
	private JEditorPane JserverProperties, Jspigotyml, Jbukkityml, Jhelpyml;
	
	@Override
	public void onLoad() {
		createWindow();
		server = getServer();
		JserverProperties = window.properties;
		Jspigotyml = window.spigot;
		Jbukkityml = window.bukkit;
		Jhelpyml = window.help;
	}
	
	@Override
	public void onEnable() {
		serverProperties = new File("server.properties");
		spigotyml = new File("spigot.yml");
		bukkityml = new File("bukkit.yml");
		helpyml = new File("help.yml");
		
		JserverProperties.setText(readFile(serverProperties));
		Jspigotyml.setText(readFile(spigotyml));
		Jbukkityml.setText(readFile(bukkityml));
		Jhelpyml.setText(readFile(helpyml));
		
		if(new File("server-icon.png").exists()){
			window.setIconImage(Toolkit.getDefaultToolkit().getImage("server-icon.png"));
		}else{
			window.setIconImage(Toolkit.getDefaultToolkit().getImage(MasterWindow.class.getResource("/net/dusterthefirst/res/icon.png")));
			sendConsoleAMsg(ChatColor.YELLOW + "No Server Image Found.. Defaulting To Default Image");
		}
	}
	
	@Override
	public void onDisable() {
		window.dispose();
	}
	
	private static void createWindow() {
		BIT bitness = getBitness();
		window = new MasterWindow();
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

	String readFile(File f){
        // This will reference one line at a time
        String line = "", output = "";

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(f);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                output += line + "\n";
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(Exception ex) {
        	sendConsoleAMsg(ChatColor.YELLOW + "Your Server Is Missing " + f.getName());
        }
        return output;
	}
	
	//Sends A Message To The Console, With Chat Colors
	public static boolean sendConsoleAMsg(String msg) {
        try {
            Bukkit.getServer().getConsoleSender().sendMessage(msg);
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

	public static void stopServer(){
		Bukkit.getServer().shutdown();
	}
	
}