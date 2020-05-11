package address.test.temp;

import org.junit.Test;

public class MyStringParserTest {

	// JUnit 을 만들어놓으면 수정할 때 편함.
	@Test
	public void getId() {
		// . 은 파싱이 안된다. 역슬러쉬 2개(\\) 혹은 [] 필요
		int memberId = Integer.parseInt("1.홍길동".split("\\.")[0]); 
		System.out.println(memberId);
				
	}
}
