package composite;

public class LotteriaApp {
	public static void main(String[] args) {
		// BigBurgerSet �߰�
		BigBurgerSet set1 = new BigBurgerSet();

		// Burger �߰�
		Burger burger1 = new Burger();

		// Coke �߰�
		Coke coke1 = new Coke();

		// BigBurgerSet �߰�, ���� 1000�� ���� ����
		BigBurgerSet set2 = 
				new BigBurgerSet(
						new BigBurger(3000, "�� ���� ����"),
						new Coke(), 
						new FrenchFried()
						);
		System.out.println(set1.getBigburger().getPrice());
		System.out.println(set2.getBigburger().getPrice());
		System.out.println();
		// BigBurgerSet �߰�, ���� 2000�� ���� ����
		BigBurgerSet set3 = 
				new BigBurgerSet(
						new BigBurger(2000, "�� ���� ����")
						);
		System.out.println(set3.getBigburger().getPrice());

		// ������� ��Ʈ �߰�
		ShrimpBurgerSet set4 = 
				new ShrimpBurgerSet();
		System.out.println(set4.getShrimpburger().getPrice());
	}
}