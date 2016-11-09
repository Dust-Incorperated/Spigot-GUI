package net.dusterthefirst.simplespigot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Updater {
	
	
	
}

class LoadGithubContent {
	 
	public static void main(String[] args) throws Throwable {
		String link = "https://raw.githubusercontent.com/Crunchify/All-in-One-Webmaster/master/all-in-one-webmaster-premium.php";
		URL  Url = new URL(link);
		HttpURLConnection  Http = (HttpURLConnection)  Url.openConnection();
		Map<String, List<String>>  Header =  Http.getHeaderFields();
 
		// If URL is getting 301 and 302 redirection HTTP code then get new URL link.
		// This below for loop is totally optional if you are sure that your URL is not getting redirected to anywhere
		for (String header :  Header.get(null)) {
			if (header.contains(" 302 ") || header.contains(" 301 ")) {
				link = Header.get("Location").get(0);
				Url = new URL(link);
				Http = (HttpURLConnection)  Url.openConnection();
				Header =  Http.getHeaderFields();
			}
		}
		InputStream  Stream =  Http.getInputStream();
		String  Response =  GetStringFromStream( Stream);
		System.out.println( Response);
	}
	
	public static void Load(){
		
		return;
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