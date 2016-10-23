package net.dusterthefirst.simplespigot.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.dusterthefirst.simplespigot.Manager;

public class ServerControlButtonListener implements ActionListener{
	
	public enum ServerControl{
		START, STOP, RESTART;
	}

	private ServerControl type;
	
	public ServerControlButtonListener(ServerControl type) {
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (type){
		case START:
			Manager.start();
			break;
		case STOP:
			Manager.stop();
			break;
		case RESTART:
			Manager.restart();
			break;
		}
	}
	
}
