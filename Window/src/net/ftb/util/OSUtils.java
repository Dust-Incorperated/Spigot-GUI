/*
 * This file was part of FTB Launcher.
 * 
 * TRIMMED OSUTILS FROM FTB LAUNCHER
 *
 */
package net.ftb.util;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketException;
import java.net.URI;
import java.util.Enumeration;
import java.util.Map;

import javax.swing.text.html.StyleSheet;
/**
 * 
 * @author FTB TEAM
 *
 */
public class OSUtils {
    private static byte[] cachedMacAddress;
    @SuppressWarnings("unused")
	private static String cachedUserHome;

    /**
     * gets the number of cores for use in DL threading
     *
     * @return number of cores on the system
     */
    private static int numCores;
    
    public static int getCores(){
    	return numCores;
    }

    public static Proxy getProxy (String url) {
        // this is set explicitly with command line define or by our proxy setting
        String system = System.getProperty("java.net.useSystemProxies");
        // System-wide setting from java control panel or command line define
        String socks = System.getProperty("socksProxyHost");

        if (system != null && system.equals("true")) {
            System.out.println("Detected system proxy");
        }
        if (socks != null && !socks.isEmpty()) {
            System.out.println("Detected socks proxy");
        }

        java.util.List<Proxy> l = null;
        try {
            l = ProxySelector.getDefault().select(new URI(url));
            if (l != null) {
                for (Proxy p: l) {
                    InetSocketAddress address = (InetSocketAddress) p.address();
                    if (address == null) {
                        System.out.println("ProxySelector: type: " + p.type() + ", no proxy for " + url);
                    } else {
                        System.out.println("ProxySelector: type: " + p.type() + ", for " + url);
                    }
                }
                // correct? Can' decide without feedback
                return l.get(0);
            }
        } catch (Exception e) {
            System.out.println("failed");
        }
        System.out.println("Proxy was turned on but ProxySelector did not returned proxies for " + url);
        return Proxy.NO_PROXY;
    }

    public static enum OS {
        WINDOWS, UNIX, MACOSX, OTHER,
    }

    static {
        cachedUserHome = System.getProperty("user.home");
        numCores = Runtime.getRuntime().availableProcessors();
    }

    public static long getOSTotalMemory () {
        return getOSMemory("getTotalPhysicalMemorySize", "Could not get RAM Value");
    }

    public static long getOSFreeMemory () {
        return getOSMemory("getFreePhysicalMemorySize", "Could not get free RAM Value");
    }

    private static long getOSMemory (String methodName, String warning) {
        long ram = 0;

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        Method m;
        try {
            m = operatingSystemMXBean.getClass().getDeclaredMethod(methodName);
            m.setAccessible(true);
            Object value = m.invoke(operatingSystemMXBean);
            if (value != null) {
                ram = Long.valueOf(value.toString()) / 1024 / 1024;
            } else {
                System.out.println(warning);
                ram = 1024;
            }
        } catch (Exception e) {
            System.out.println("Error while getting OS memory info");
        }

        return ram;
    }

    /**
     * Used to get the java delimiter for current OS
     * @return string containing java delimiter for current OS
     */
    public static String getJavaDelimiter () {
        switch (getCurrentOS()) {
        case WINDOWS:
            return ";";
        case UNIX:
            return ":";
        case MACOSX:
            return ":";
        default:
            return ";";
        }
    }

    /**
     * Used to get the current operating system
     * @return OS enum representing current operating system
     */
    public static OS getCurrentOS () {
        String osString = System.getProperty("os.name").toLowerCase();
        if (osString.contains("win")) {
            return OS.WINDOWS;
        } else if (osString.contains("nix") || osString.contains("nux")) {
            return OS.UNIX;
        } else if (osString.contains("mac")) {
            return OS.MACOSX;
        } else {
            return OS.OTHER;
        }
    }

    /**
     * Used to check if Windows is 64-bit
     * @return true if 64-bit Windows
     */
    public static boolean is64BitWindows () {
        String arch = System.getenv("PROCESSOR_ARCHITECTURE");
        String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");
        return (arch.endsWith("64") || (wow64Arch != null && wow64Arch.endsWith("64")));
    }

    /**
     * Used to check if a posix OS is 64-bit
     * @return true if 64-bit Posix OS
     */
    public static boolean is64BitPosix () {
        String line, result = "";
        try {
            Process command = Runtime.getRuntime().exec("uname -m");
            BufferedReader in = new BufferedReader(new InputStreamReader(command.getInputStream()));
            while ((line = in.readLine()) != null) {
                result += (line + "\n");
            }
        } catch (Exception e) {
            System.out.println("Posix bitness check failed");
        }
        // 32-bit Intel Linuces, it returns i[3-6]86. For 64-bit Intel, it says x86_64
        return result.contains("_64");
    }

    /**
     * Used to check if OS X is 64-bit
     * @return true if 64-bit OS X
     */

    public static boolean is64BitOSX () {
        String line, result = "";
        if (!(System.getProperty("os.version").startsWith("10.6") || System.getProperty("os.version").startsWith("10.5"))) {
            return true;//10.7+ only shipped on hardware capable of using 64 bit java
        }
        try {
            Process command = Runtime.getRuntime().exec("/usr/sbin/sysctl -n hw.cpu64bit_capable");
            BufferedReader in = new BufferedReader(new InputStreamReader(command.getInputStream()));
            while ((line = in.readLine()) != null) {
                result += (line + "\n");
            }
        } catch (Exception e) {
            System.out.println("OS X bitness check failed");
        }
        return result.equals("1");
    }

    /**
     * Used to check if operating system is 64-bit
     * @return true if 64-bit operating system
     */
    public static boolean is64BitOS () {
        switch (getCurrentOS()) {
        case WINDOWS:
            return is64BitWindows();
        case UNIX:
            return is64BitPosix();
        case MACOSX:
            return is64BitOSX();
        case OTHER:
            return true;
        default:
            return true;
        }
    }

    /**
     * Used to get check if JVM is 64-bit
     * @return true if 64-bit JVM
     */
    public static Boolean is64BitVM () {
        return System.getProperty("sun.arch.data.model").equals("64");
    }

    /**
     * Used to get the OS name for use in google analytics
     * @return Linux/OSX/Windows/other/
     */
    public static String getOSString () {
        String osString = System.getProperty("os.name").toLowerCase();
        if (osString.contains("win")) {
            return "Windows";
        } else if (osString.contains("linux")) {
            return "linux";
        } else if (osString.contains("mac")) {
            return "OSX";
        } else {
            return osString;
        }
    }

    /**
     * sees if the hash of the UUID matches the one stored in the config
     * @return true if UUID matches hash or false if it does not
     */
    public static boolean verifyUUID () {
        return true;
    }

    /**
     * Grabs the mac address of computer and makes it 10 times longer
     * @return a byte array containing mac address
     */
    public static byte[] getMacAddress () {
        if (cachedMacAddress != null && cachedMacAddress.length >= 10) {
            return cachedMacAddress;
        }
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface network = networkInterfaces.nextElement();
                byte[] mac = network.getHardwareAddress();
                if (mac != null && mac.length > 0 && !network.isLoopback() && !network.isVirtual() && !network.isPointToPoint() && network.getName().substring(0,3) != "ham" && network.getName().substring(0,3) != "vir" && !network.getName().startsWith("docker")) {
                    System.out.println("Interface: " + network.getDisplayName() + " : " + network.getName());
                    cachedMacAddress = new byte[mac.length * 10];
                    for (int i = 0; i < cachedMacAddress.length; i++) {
                        cachedMacAddress[i] = mac[i - (Math.round(i / mac.length) * mac.length)];
                    }
                    return cachedMacAddress;
                }
            }
        } catch (SocketException e) {
            System.out.println("Exception getting MAC address");
        }

        System.out.println("Failed to get MAC address, using default logindata key");
        return new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
    }

    /**
     * Opens the given URL in the default browser
     * @param url The URL
     */
    public static void browse (String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url.replace(" ", "+")));
            } else if (getCurrentOS() == OS.UNIX) {
                // Work-around to support non-GNOME Linux desktop environments with xdg-open installed
                new ProcessBuilder("xdg-open", url).start();
            } else {
                System.out.println("Could not open Java Download url, not supported");
            }
        } catch (Exception e) {
            System.out.println("Could not open link: " + url);
        }
    }

    /**
     * Opens the given path with the default application
     * @param path The path
     */
    public static void open (File path) {
        if (!path.exists()) {
            return;
        }
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop.getDesktop().open(path);
            } else if (getCurrentOS() == OS.UNIX) {
                // Work-around to support non-GNOME Linux desktop environments with xdg-open installed
                new ProcessBuilder("xdg-open", path.toString()).start();
            }
        } catch (Exception e) {
            System.out.println("Could not open file");
        }
    }

    /**
     * @return if java 7+ can be ran on that version of osx
     */
    public static boolean canRun7OnMac () {
        return getCurrentOS() == OS.MACOSX && !(System.getProperty("os.version").startsWith("10.6") || System.getProperty("os.version").startsWith("10.5"));
    }

    /**
     * Removes environment variables which may cause faulty JVM memory allocations
     */
    public static void cleanEnvVars (Map<String, String> environment) {
        environment.remove("_JAVA_OPTIONS");
        environment.remove("JAVA_TOOL_OPTIONS");
        environment.remove("JAVA_OPTIONS");

        if (OSUtils.getCurrentOS() == OS.WINDOWS) {
            environment.put("__COMPAT_LAYER", "WIN8RTM");
        }
    }

    public static StyleSheet makeStyleSheet (String name) {
        try {
            StyleSheet sheet = new StyleSheet();
            Reader reader = new InputStreamReader(System.class.getResourceAsStream("/css/" + name + ".css"));
            sheet.loadRules(reader, null);
            reader.close();

            return sheet;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     *
     * @return pid of the running process. -1 if fail
     */
    public static long getPID () {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        long numericpid = -1;
        try {
            numericpid = Long.parseLong(pid);
        } catch (Exception e) {
            numericpid = -1;
            System.out.println("failed");
        }
        return numericpid;
    }

}
