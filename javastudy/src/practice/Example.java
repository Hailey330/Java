package practice;

class Person {
	private int age;
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
//		this.age = age;
		if (age < 0) {
			return;
		}
		this.age = age;
	}
}
	
public class Example{
	public static void main(String[] args) {
		Person aPerson = new Person();
		aPerson.setAge(17);
		System.out.println(aPerson.getAge());
	}
}
