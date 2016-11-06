package net.dusterthefirst.simplespigot;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.dusterthefirst.simplespigot.gui.MasterWindow;
import net.dusterthefirst.simplespigot.util.NotifManager;
import net.ftb.util.OSUtils;

public class PluginClass extends JavaPlugin{
	
	protected static final Listener LISTENER = new EventListener();
	
	public enum BIT{
		a64on32, a32on64, a64on64, a32on32, nil;
	}
	
	public static MasterWindow window;
	static PrintStream out, err;
	private static Server server;
	private static File serverProperties, spigotyml, bukkityml, helpyml;
	private static JEditorPane JserverProperties, Jspigotyml, Jbukkityml, Jhelpyml;
	private static boolean traysuported;
	
	public static void main(String[] args) {
		createWindow();
		traysuported = NotifManager.createTrayIcon();
	}

	@Override
	public void onLoad() {
		createWindow();
		server = getServer();
	}
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(LISTENER, this);
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
	        @Override
	        public void run(){
	        	setFilesToFiles();
	    		updateIcon();
	    		updateSimpleWorldsList();
	    		updateSimplePluginsList();
	    		updatePluginsList();
	    		updatePlayerList();
	        }
	    });
		window.btnSaveFiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					saveFilesToFiles();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				sendConsoleAMsg(ChatColor.GREEN + "Saved Files, Reload Server To Apply Changes");
			}
		});
		window.mntmReloadServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				server.reload();
			}
		});
		window.mntmBugz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		window.setTitle(window.getTitle() + " - Spigot/Bukkit V" + server.getBukkitVersion().split("-", 2)[0]);
		setMOTD(server.getMotd());
		traysuported = NotifManager.createTrayIcon();
	}
	
	@Override
	public void onDisable() {
		window.dispose();
		traysuported = NotifManager.createTrayIcon();
	}
	
	private static void setMOTD(String text){
		text = text.replaceAll("(\u00A7|\u00BA|&).", "");
		String[] lines = text.split("\n", 2);
		String topLine, bottomLine;
		topLine = lines[0];
		if(lines.length < 2){
			bottomLine = "";
		}else{
			bottomLine = lines[1];
		}
		window.motd.setText(topLine);
		window.motd2.setText(bottomLine);
	}
	
	private static void createWindow() {
		try {
			BIT bitness = getBitness();
			window = new MasterWindow();
			boolean nope = window.warnBits(bitness);
			if(nope){
				window.setVisible(true);
			}else{
				window.dispose();
			}
			sendConsoleAMsg(ChatColor.DARK_GREEN + "Created Window");
		} catch (Exception e) {
			sendConsoleAMsg(ChatColor.YELLOW + "Error Creating Window");
			e.printStackTrace();
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
        } catch (Exception e) {
            System.out.println(msg);
        }
        return false;
    }

	public static void stopServer(){
		Bukkit.getServer().shutdown();
	}
	
	public static void updateSimplePlayerList(){
		DefaultListModel<String> newmodel = new DefaultListModel<String>();
		Collection<? extends Player> players = server.getOnlinePlayers();
		for(Player player : players){
			newmodel.addElement(player.getDisplayName());
		}
		window.simplePlayersList.setModel(newmodel);
	}
	
	public static void updatePlayerList(){
		DefaultListModel<String> newmodel = new DefaultListModel<String>();
		Collection<? extends Player> players = server.getOnlinePlayers();
		for(Player player : players){
			newmodel.addElement(player.getDisplayName());
		}
		window.playerList.setModel(newmodel);
	}
	
	private void updateSimpleWorldsList() {
		DefaultListModel<String> newmodel = new DefaultListModel<String>();
		Collection<World> worlds = server.getWorlds();
		for(World world : worlds){
			newmodel.addElement(world.getName());
		}
		window.simpleWorldsList.setModel(newmodel);
	}

	private void updateSimplePluginsList() {
		DefaultListModel<String> newmodel = new DefaultListModel<String>();
		Plugin[] plugins = server.getPluginManager().getPlugins();
		for(Plugin plugin : plugins){
			newmodel.addElement(plugin.getName());
		}
		window.simplePluginsList.setModel(newmodel);
	}

	private void updatePluginsList(){
		DefaultListModel<String> newmodel = new DefaultListModel<String>();
		Plugin[] plugins = server.getPluginManager().getPlugins();
		for(Plugin plugin : plugins){
			if(!plugin.getName().equalsIgnoreCase(this.getName()))
				newmodel.addElement(plugin.getName());
		}
		window.pluginList.setModel(newmodel);
	}
	
 	private void updateIcon() {
		if(new File("server-icon.png").exists()){
			window.setIconImage(Toolkit.getDefaultToolkit().getImage("server-icon.png"));
		}else{
			window.setIconImage(Toolkit.getDefaultToolkit().getImage(MasterWindow.class.getResource("/net/dusterthefirst/res/icon.png")));
			sendConsoleAMsg(ChatColor.YELLOW + "No Server Image Found.. Defaulting To Default Image");
		}
	}

	private void setFilesToFiles() {
		JserverProperties = window.properties;
		Jspigotyml = window.spigot;
		Jbukkityml = window.bukkit;
		Jhelpyml = window.help;
		
		serverProperties = new File("server.properties");
		spigotyml = new File("spigot.yml");
		bukkityml = new File("bukkit.yml");
		helpyml = new File("help.yml");
		
		JserverProperties.setText(readFile(serverProperties));
		Jspigotyml.setText(readFile(spigotyml));
		Jbukkityml.setText(readFile(bukkityml));
		Jhelpyml.setText(readFile(helpyml));
	}
	
	private void saveFilesToFiles() throws IOException {
		String 
		bukkitText = Jbukkityml.getText(),
		helpText = Jhelpyml.getText(),
		propertiesText = JserverProperties.getText(),
		spigotText = Jspigotyml.getText();
		
		writeToFile(serverProperties, propertiesText);
		writeToFile(spigotyml, spigotText);
		writeToFile(bukkityml, bukkitText);
		writeToFile(helpyml, helpText);
	}
	
	void writeToFile(File file, String content) throws IOException{
		List<String> lines = Arrays.asList(content);
		Path path = Paths.get(file.getAbsolutePath());
		Files.write(path, lines, Charset.forName("UTF-8"));
	}
	
	void writeToFile(File file, byte[] data) throws IOException{
		Path path = Paths.get(file.getAbsolutePath());
		Files.write(path, data);
	}
	
	public static synchronized Plugin getPlugin(){
		return getPlugin(PluginClass.class);
	}

}