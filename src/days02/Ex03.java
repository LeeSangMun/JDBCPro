package days02;

public class Ex03 {

	public static void main(String[] args) {
		try {
			int n = 100;
			System.out.println("1");
			
			if(n == 100) return;
			System.out.println("2");
		} catch(Exception e) {
			System.out.println("3");
		} finally {
			System.out.println("4");
		}
		System.out.println("5");
	}

}
