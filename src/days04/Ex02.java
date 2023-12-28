package days04;

/**
 * @author sangmun
 * @date 2023. 9. 22. - 오후 4:47:31
 * @subject
 * @content
 */
public class Ex02 {

	public static void main(String[] args) {
		// [1] 2 3 4 5 6 7 8 9 10 >
		int currentPage = 2;
		int numberPerPage = 10;
		int numberOfPageBlock = 10;
		// 1 [2] 3 4 5 6 7 8 9 10 >
		// 1) 총 레코드수 ? 186 SELECT COUNT(*)FROM cstvsboard;
		// 2) 총 페이지수 ? 19 SELECT COUNT(*), CEIL(COUNT(*)/10) FROM cstvsboard;
		int totalPages = 19;
		for (int i = 1; i <= 19; i++) {
			System.out.printf("%d 페이지 :  ", i);

			// int start = (currentPage -1) /numberOfPageBlock * numberOfPageBlock +1 ;
			int start = (i - 1) / numberOfPageBlock * numberOfPageBlock + 1;
			int end = start + numberOfPageBlock - 1;
			end = end > totalPages ? totalPages : end;

			if (start != 1)
				System.out.print(" < ");
			for (int j = start; j <= end; j++) {
				System.out.printf(i == j ? "[%d] " : "%d ", j);
			}
			if (end != totalPages)
				System.out.print(" > ");

			System.out.println();

		}
	}

}