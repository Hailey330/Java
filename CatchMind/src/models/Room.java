package models;

import java.util.ArrayList;

import chatting.MainHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Room {

	private int rID;
	private String title;
	private String rPassword;
	private String userCount;
	private String masterName;
	private int conditionP;
	private ArrayList<MainHandler> roomInUserList;
}
