package booklist;

public class BookStore {
	// 만들어진 클래스를 사용하는 부분
	// BookStore()
	void run() {
		BookList bl = new BookList();
		bl.getConnection(); // 연결 open
		bl.getBookDB(); // 연결 close
		bl.printBook_Obj_array();

		// so.printBookArr();

		CustomerList cl = new CustomerList();
		cl.getConnection();
		cl.getCustomerDB();
		cl.printCustomer_Obj_array();
	}

}
