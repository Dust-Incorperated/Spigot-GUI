package net.dusterthefirst.simplespigot.type;

public class Player {

	private boolean isOp, banned;
	private String name, uuid;
	
	Player(boolean isOp, boolean banned, String name, String uuid){
		this.banned = banned;
		this.isOp = isOp;
		this.name = name;
		this.uuid = uuid;
	}
	
	public boolean isOp() {
		return isOp;
	}
	public void setOp(boolean isOp) {
		this.isOp = isOp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getBanned() {
		return banned;
	}
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
