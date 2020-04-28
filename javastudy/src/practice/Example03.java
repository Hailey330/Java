package practice;
// 객체 지향 프로그래밍 관점에서 바람직하게 코드 수정하기

// (2) 생성자 대신 메소드 추가

class Power1 {
	private	 int kick;
	private int punch;

	public void set(int kick, int punch) {
		this.kick = kick;
		this.punch = punch;
	}
}

public class Example03 {
	public static void main(String[] args) {
		Power1 robot = new Power1();
		robot.set(10, 15);
//		robot.kick = 10;
//		robot.punch = 15;
	}
}
