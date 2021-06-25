package classes;

public class MainTest {
	/* main function */
	public static void main(String[] args) {
		String fileName = "test4comments.java";
		
		Finder finder = new Finder();
		finder.findAll(fileName);
	}
}
