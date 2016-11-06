package net.dusterthefirst.simplespigot.util;

import java.awt.Color;
import java.util.HashMap;

public class ConsoleColors{
	
	/**
	 * Java Equivalent Of Minecraft Chat Colors
	 */
	public static final Color 
			BLACK 		= new Color(0,0,0),
			DARK_BLUE 	= new Color(0,0,170),
			DARK_GREEN	= new Color(0,170,0),
			DARK_AQUA	= new Color(0,170,170),
			DARK_RED 	= new Color(170,0,0),
			DARK_PURPLE = new Color(170,0,170),
			GOLD 		= new Color(255,170,0),
			GREY 		= new Color(170,170,170),
			DARK_GREY 	= new Color(85,85,85),
			BLUE 		= new Color(85,85,255),
			GREEN 		= new Color(85,255,85),
			AQUA 		= new Color(85,255,255),
			RED 		= new Color(255,85,85),
			LIGHT_PURPLE= new Color(255,85,255),
			YELLOW 		= new Color(255,255,85),
			WHITE 		= new Color(255,255,255);
	
	/**
	 * Hexadecimal Equivalents Of The Colors In This Class
	 */
	public static final String
			BLACK_HEX 		= "#" + Integer.toHexString(BLACK.getRGB()).substring(2),
			DARK_BLUE_HEX 	= "#" + Integer.toHexString(DARK_BLUE.getRGB()).substring(2),
			DARK_GREEN_HEX 	= "#" + Integer.toHexString(DARK_GREEN.getRGB()).substring(2),
			DARK_AQUA_HEX 	= "#" + Integer.toHexString(DARK_AQUA.getRGB()).substring(2),
			DARK_RED_HEX 	= "#" + Integer.toHexString(DARK_RED.getRGB()).substring(2),
			DARK_PURPLE_HEX = "#" + Integer.toHexString(DARK_PURPLE.getRGB()).substring(2),
			GOLD_HEX 		= "#" + Integer.toHexString(GOLD.getRGB()).substring(2),
			GREY_HEX 		= "#" + Integer.toHexString(GREY.getRGB()).substring(2),
			DARK_GREY_HEX 	= "#" + Integer.toHexString(DARK_GREY.getRGB()).substring(2),
			BLUE_HEX 		= "#" + Integer.toHexString(BLUE.getRGB()).substring(2),
			GREEN_HEX 		= "#" + Integer.toHexString(GREEN.getRGB()).substring(2),
			AQUA_HEX 		= "#" + Integer.toHexString(AQUA.getRGB()).substring(2),
			RED_HEX 		= "#" + Integer.toHexString(RED.getRGB()).substring(2),
			LIGHT_PURPLE_HEX= "#" + Integer.toHexString(LIGHT_PURPLE.getRGB()).substring(2),
			YELLOW_HEX 		= "#" + Integer.toHexString(YELLOW.getRGB()).substring(2),
			WHITE_HEX 		= "#" + Integer.toHexString(WHITE.getRGB()).substring(2);
	
	/**
	 * Map Of All Color Codes To Their Hexadecimal Equivalent
	 */
	@SuppressWarnings("serial")
	final static HashMap<String, String> HEXCOLORCODES = new HashMap<String, String>(){{
		put("4", DARK_RED_HEX);
		put("c", RED_HEX);
		put("6", GOLD_HEX);
		put("e", YELLOW_HEX);
		put("2", DARK_GREEN_HEX);
		put("a", GREEN_HEX);
		put("b", AQUA_HEX);
		put("3", DARK_AQUA_HEX);
		put("1", DARK_BLUE_HEX);
		put("9", BLUE_HEX);
		put("d", LIGHT_PURPLE_HEX);
		put("5", DARK_PURPLE_HEX);
		put("f", WHITE_HEX);
		put("7", GREY_HEX);
		put("8", DARK_GREY_HEX);
		put("0", BLACK_HEX);
	}};
	/**
	 * Map Of All Color Codes To Their Java Color Equivalent
	 */
	@SuppressWarnings("serial")
	final static HashMap<String, Color> COLORCODES = new HashMap<String, Color>(){{
		put("4", DARK_RED);
		put("c", RED);
		put("6", GOLD);
		put("e", YELLOW);
		put("2", DARK_GREEN);
		put("a", GREEN);
		put("b", AQUA);
		put("3", DARK_AQUA);
		put("1", DARK_BLUE);
		put("9", BLUE);
		put("d", LIGHT_PURPLE);
		put("5", DARK_PURPLE);
		put("f", WHITE);
		put("7", GREY);
		put("8", DARK_GREY);
		put("0", BLACK);
	}};
	
}
