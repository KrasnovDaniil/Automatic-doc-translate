package classes;

import java.util.ArrayList;

public class MainTest {
	public static void main(String[] args) {
		// String fileName = "notes.java";
		String fileName = "test4comments.java";
		ArrayList<FoundComment> comments = new ArrayList<>();
		ArrayList<FoundString> strings = new ArrayList<>();
		
		Finder finder = new Finder();
		comments = finder.findAllComments(fileName, comments);
		strings = finder.findAllStrings(fileName, strings);
		
		// testing
		System.out.println("Коммы: ");
        for(int i=0; i<comments.size(); i++)
        	System.out.println("Строка: "+comments.get(i).getLine()+". Коммент - это "+comments.get(i).getText() + 
        			" - строка " + comments.get(i).getLineNumber() + ", от "+comments.get(i).getIndexStart()+" до "+comments.get(i).getIndexEnd());
        
        System.out.println("\nСтроки: ");
        for(int i=0; i<strings.size(); i++)
        	System.out.println("Строка: "+strings.get(i).getLine()+". String - это "+strings.get(i).getText() + 
        			" - строка " + strings.get(i).getLineNumber() + ", от "+strings.get(i).getIndexStart()+" до "+strings.get(i).getIndexEnd());
	}
}
