package net.dusterthefirst.simplespigot.util;

import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.bukkit.ChatColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import net.dusterthefirst.simplespigot.PluginClass;

@Deprecated
public class Updater {

    final static String VERSION_URL = "https://api.spiget.org/v2/resources/30893/versions?size=" + Integer.MAX_VALUE + "&spiget__ua=SpigetDocs";
    final static String DESCRIPTION_URL = "https://api.spiget.org/v2/resources/30893/updates?size=" + Integer.MAX_VALUE + "&spiget__ua=SpigetDocs";

    public static Object[] getLastUpdate()
    {
        try
        {
            JSONArray versionsArray = (JSONArray) JSONValue.parseWithException(IOUtils.toString(new URL(String.valueOf(VERSION_URL))));
            String lastVersion = ((JSONObject) versionsArray.get(versionsArray.size() - 1)).get("name").toString();

            if(lastVersion != PluginClass.getPlugin(PluginClass.class).getDescription().getVersion())
            {
                JSONArray updatesArray = (JSONArray) JSONValue.parseWithException(IOUtils.toString(new URL(String.valueOf(DESCRIPTION_URL))));
                String updateName = ((JSONObject) updatesArray.get(updatesArray.size() - 1)).get("title").toString();
   
                Object[] update = {lastVersion, updateName};
                return update;
            }
        }
        catch (Exception e)
        {
            return new String[0];
        }

        return new String[0];
    }
}

class Checkere {
	
	//private static Plugin plugin = Plugin.getPlugin(Plugin.class);
	
	static final String ERROR = "err", NOTHING = "null";

	public static String update(String resource){
		String version = getCurrentVersion(resource);
		if(version.equals(ERROR)){
			return ChatColor.RED + "Error In Retrieving The Update";
		}else if(version.equals(NOTHING)){
			return ChatColor.RED + "Could Not Fetch The Update";
//		}else if(version.equals(plugin.getDescription().getVersion())){
//			return ChatColor.GREEN + "Your Version Of Simple Spigot Is Up To Date";
		}else{
			return ChatColor.YELLOW + "Found Update, " + version;
		}
	}

	private static String getCurrentVersion(String resource) {
		
		return null;
	}
	
	
}
