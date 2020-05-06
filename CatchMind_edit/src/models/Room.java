package models;

import java.util.ArrayList;

import Server.MainHandler;

public class Room {
	private int rID;
	private String title;
	private String userCount;
	private String masterName;
	public ArrayList<MainHandler> roomInUserList;

	public Room() {
		this.rID = 0;
		this.title = "";
		this.userCount = "";
		this.masterName = "";
		roomInUserList = new ArrayList<MainHandler>();
	}

	public Room(int rID, String title, String userCount, String masterName) {
		this.rID = rID;
		this.title = title;
		this.userCount = userCount;
		this.masterName = masterName;
		roomInUserList = new ArrayList<MainHandler>();
	}

	public int getrID() {
		return rID;
	}

	public void setrID(int rID) {
		this.rID = rID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserCount() {
		return userCount;
	}

	public void setUserCount(String userCount) {
		this.userCount = userCount;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public ArrayList<MainHandler> getRoomInUserList() {
		return roomInUserList;
	}

	public void setRoomInUserList(ArrayList<MainHandler> roomInUserList) {
		this.roomInUserList = roomInUserList;
	}

	@Override
	public String toString() {
		return "Room [rID=" + rID + ", title=" + title + ", userCount=" + userCount + ", masterName=" + masterName + "]";
		
	}

}