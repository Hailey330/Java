package practice;

import java.util.Scanner;

public class Ch02_03 {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("금액을 입력하시오 >> ");
		int num = sc.nextInt();
		
		int money [] = {50000, 10000, 1000, 500, 100, 50, 10, 1};
		
		for (int i = 0; i < money.length; i++) {
			System.out.println(money[i] + "원 : " + num/money[i]);
			num = num % money[i];
		}
		sc.close();
	}
}
