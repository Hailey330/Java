package practice;
// 객체 지향 프로그래밍 관점에서 바람직하게 코드 수정하기

// (1) 생성자 이용

class Power {
	private int kick;
	private int punch;

	public Power(int kick, int punch) {
		this.kick = kick;
		this.punch = punch;
	}
}

public class Example02 {
	public static void main(String[] args) {
		Power robot = new Power(10, 15);
//		robot.kick = 10;
//		robot.punch = 15;
	}
}
