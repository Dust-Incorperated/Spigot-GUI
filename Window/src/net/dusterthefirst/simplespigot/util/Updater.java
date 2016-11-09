package net.dusterthefirst.simplespigot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import net.dusterthefirst.simplespigot.PluginClass;
import net.dusterthefirst.simplespigot.gui.Update;

public class Updater {
	
	static HashMap<String, String> map;
	
	public static void update(){
		String content = null;
		try {
			content = LoadGithubContent.Load("https://raw.githubusercontent.com/DusterTheFirst/Spigot-GUI/master/Window/plugin.yml");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		map = new HashMap<String, String>();
		String[] lines = content.split("\n");
		for (String line : lines) {
			String[] parts = line.split(":", 2);
			map.put(parts[0].trim().toLowerCase(), parts[1].trim());
		}
		
		if(needsUpdate()){
			try{
			String newversion = null;
			newversion = getNewVersion();
				new Update(newversion, Arrays.asList(getNewThings()), new URI("https://github.com/DusterTheFirst/Spigot-GUI/releases"));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean needsUpdate(){
		String newversion = null;
		newversion = getNewVersion();
		String oldVersion = PluginClass.getPlugin().getDescription().getVersion();
		return !newversion.equalsIgnoreCase(oldVersion);
	}
	
	public static String getNewVersion(){
		return map.get("version");
	}
	
	public static String[] getNewThings(){
		return map.getOrDefault("newthings", "ERROR,GETTING,THINGS")
				.split(",");
	}
	
}

class LoadGithubContent {
	 
	
	public static String Load(String link) throws IOException{
		URL Url = new URL(link);
		HttpURLConnection Http = (HttpURLConnection)  Url.openConnection();
		InputStream  Stream =  Http.getInputStream();
		String  Response =  GetStringFromStream(Stream);
		return Response;
	}
 
        // ConvertStreamToString() Utility - we name it as  GetStringFromStream()
	private static String  GetStringFromStream(InputStream  Stream) throws IOException {
		if ( Stream != null) {
			Writer  Writer = new StringWriter();
 
			char[]  Buffer = new char[2048];
			try {
				Reader  Reader = new BufferedReader(new InputStreamReader( Stream, "UTF-8"));
				int counter;
				while ((counter =  Reader.read( Buffer)) != -1) {
					 Writer.write( Buffer, 0, counter);
				}
			} finally {
				 Stream.close();
			}
			return  Writer.toString();
		} else {
			return "No Contents";
		}
	}
}