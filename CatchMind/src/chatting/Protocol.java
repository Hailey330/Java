package chatting;

public class Protocol {
	public static final String REGISTER = "100"; // 회원가입(request)
	public static final String ENTERLOGIN = "120"; // 로그인(request)
	public static final String ENTERLOGIN_OK = "121"; // 로그인 성공
	public static final String ENTERLOGIN_NO = "122"; // 로그인 실패
	public static final String ROOMMAKE = "200"; // 방 만들기
	public static final String ROOMMAKE_OK = "201"; // 방 만들기
	public static final String ROOMMAKE_OK1 = "202"; // 방 만들기 - 방장
	public static final String ENTERROOM = "300"; // 방 입장
	public static final String ENTERROOM_OK = "301"; // 방 입장 성공
	public static final String ENTERROOM_OK1 = "302"; // 방 입장 성공 - 들어가는 사람
	public static final String ENTERROOM_NO = "303"; // 방 입장 실패
	public static final String ENTERROOM_USERLISTSEND = "305"; // 방에 유저들을 보내줌
	public static final String EXITCHATTINGROOM = "310"; // 방 나가기
	public static final String SENDMESSAGE = "400"; // 메세지 보내기
	public static final String SENDMESSAGE_ACK = "410"; // 메세지 보내기(답장)
	public static final String CHATTINGSENDMESSAGE = "420"; // 채팅방에서 메세지 보내기 (Request)
	public static final String CHATTINGSENDMESSAGE_OK = "430"; // 채팅방에서 메세지 보내기 (Request)





	
	
	
	
	
	
	
}
