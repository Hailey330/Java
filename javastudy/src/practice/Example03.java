package practice;
// ��ü ���� ���α׷��� �������� �ٶ����ϰ� �ڵ� �����ϱ�

// (2) ������ ��� �޼ҵ� �߰�

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