package practice;
// ��ü ���� ���α׷��� �������� �ٶ����ϰ� �ڵ� �����ϱ�

// (1) ������ �̿�

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
