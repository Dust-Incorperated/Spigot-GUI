package net.dusterthefirst.simplespigot.util;

import java.awt.Color;

import net.dusterthefirst.simplespigot.gui.MasterWindow;

public class ConsoleColors {
	
	public static final Color 
			BLACK = new Color(0,0,0),
			DARK_BLUE = new Color(0,0,170),
			DARK_GREEN = new Color(0,170,0),
			DARK_AQUA = new Color(0,170,170),
			DARK_RED = new Color(170,0,0),
			DARK_PURPLE = new Color(170,0,170),
			GOLD = new Color(255,170,0),
			GREY = new Color(170,170,170),
			DARK_GREY = new Color(85,85,85),
			BLUE = new Color(85,85,255),
			GREEN = new Color(85,255,85),
			AQUA = new Color(85,255,255),
			RED = new Color(255,85,85),
			LIGHT_PURPLE = new Color(255,85,255),
			YELLOW = new Color(255,255,85),
			WHITE = new Color(255,255,255);
	
	public static void parse(String text, MasterWindow window, Color defaultColor) {
		text = text.replaceAll("ￂﾧ", "&");
		text = text.replaceAll("§", "&");
		String[] colors = text.split("&");
		for(String color: colors){
			if(color != ""){
				if(color.startsWith("0")){
					window.writeToConsole(color.substring(1), ConsoleColors.BLACK);
				}else if(color.startsWith("1")){
					window.writeToConsole(color.substring(1), ConsoleColors.DARK_BLUE);
				}else if(color.startsWith("2")){
					window.writeToConsole(color.substring(1), ConsoleColors.DARK_GREEN);
				}else if(color.startsWith("3")){
					window.writeToConsole(color.substring(1), ConsoleColors.DARK_AQUA);
				}else if(color.startsWith("4")){
					window.writeToConsole(color.substring(1), ConsoleColors.DARK_RED);
				}else if(color.startsWith("5")){
					window.writeToConsole(color.substring(1), ConsoleColors.DARK_PURPLE);
				}else if(color.startsWith("6")){
					window.writeToConsole(color.substring(1), ConsoleColors.GOLD);
				}else if(color.startsWith("7")){
					window.writeToConsole(color.substring(1), ConsoleColors.GREY);
				}else if(color.startsWith("8")){
					window.writeToConsole(color.substring(1), ConsoleColors.DARK_GREY);
				}else if(color.startsWith("9")){
					window.writeToConsole(color.substring(1), ConsoleColors.BLUE);
				}else if(color.startsWith("a")){
					window.writeToConsole(color.substring(1), ConsoleColors.GREEN);
				}else if(color.startsWith("b")){
					window.writeToConsole(color.substring(1), ConsoleColors.AQUA);
				}else if(color.startsWith("c")){
					window.writeToConsole(color.substring(1), ConsoleColors.RED);
				}else if(color.startsWith("d")){
					window.writeToConsole(color.substring(1), ConsoleColors.LIGHT_PURPLE);
				}else if(color.startsWith("e")){
					window.writeToConsole(color.substring(1), ConsoleColors.YELLOW);
				}else if(color.startsWith("f")){
					window.writeToConsole(color.substring(1), ConsoleColors.WHITE);
				}else{
					window.writeToConsole(color, defaultColor);
				}
			}
		}
		window.writeToConsole("\n", defaultColor);
	}
	
}
