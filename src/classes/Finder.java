package classes;

import java.io.*;
import java.util.ArrayList;

/* Класс для поиска комментариев/String в коде */

/* TODO сделать поиск Сваггеров @ (здесь список: https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X) 
 * Продумать как программа будет запоминать количество переведенной информации(количество строк и комментариев) */

public class Finder {
	final char quotes = (char) 34; // в этой переменной хранятся кавычки, т.к. не получается писать кавычки в кавычках
	
	public ArrayList<FoundComment> findAllComments(String fileName, ArrayList<FoundComment> comments) { 
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            FoundComment comment = new FoundComment();
            int lineCount=0;
            
            while((line = br.readLine())!=null){
            	lineCount++;
            	if (comments.isEmpty()==true || comments.get(comments.size()-1).isCompleted()==true) {
            		comment = findCommentStart(line);
            		if(!comment.getText().equals("")) {
            			comment.setLineNumber(lineCount);
            			comment.setLine(line);
            			comments.add(comment);
            		}
            	} else {
            		comment = findMultilineCommentEnd(line);
            		comment.setLineNumber(lineCount);
            		comment.setLine(line);
            		comments.add(comment);
            	}
            } 
        }
		catch(FileNotFoundException ex) {
			System.err.println("Unable to open file '" + fileName + "'. It probably doesn't exist or has a different name");
		}
        catch(IOException ex) {
            System.err.println("Error reading file '" + fileName + "'");
            System.err.println(ex.getMessage());
        }
		return comments;
	}
	
	private FoundComment findSingleComment(String line) {
		FoundComment comment = new FoundComment();
		int indexStart = -1; // индекс начала комментария
		int indexOfDoubleDot = -1; // индекс для поиска двоеточих, чтобы не выводить https:// как коммент
		int indexOf1stQuotes=findQuotes(line);
		int indexOf2ndQuotes=findQuotes(line, indexOf1stQuotes);
		
		do {
			indexStart=findDoubleSlashCommentStart(line, indexStart);
			indexOfDoubleDot=findDoubleDotDoubleSlash(line, indexOfDoubleDot);
		} while (indexStart!=-1 && indexOfDoubleDot==indexStart);
		 
		if(indexStart!=-1 && indexOfDoubleDot!=indexStart && (indexStart<indexOf1stQuotes || indexStart>indexOf2ndQuotes)) { // если '//' есть и не совпадает с '://'
			for(int i=indexStart; i<line.length(); i++) 
				comment.setText(comment.getText()+line.charAt(i));
			comment.setIndexStart(indexStart);
			comment.setIndexEnd(line.length());
		}
		
		return comment;
	}
	
	private FoundComment findDocumentationComment(String line) {
		FoundComment comment = new FoundComment();
		int indexStart = line.indexOf("/**");; // индекс начала комментария
		int indexOfQuotes1 = line.indexOf(quotes); // индекс первых кавычек, используется для проверки - не пишется ли /* в кавычках - чтоб не ломало поиск комментариев
		int indexOfQuotes2 = line.indexOf(quotes, indexOfQuotes1+1); // индекс первых кавычек, используется для проверки - не пишется ли /* в кавычках - чтоб не ломало поиск комментариев 
		int indexEnd = -1; // индекс окончания комментария, если это '/* */'
		
		/* если (/*) нашелся и он не находится в кавычках */
		if(indexStart!=-1 && (indexOfQuotes1==-1 || indexOfQuotes1>=indexStart || indexOfQuotes2<=indexStart)) {
			for(int i=indexStart; i<line.length(); i++)
				comment.setText(comment.getText()+line.charAt(i));
			indexEnd=findMultilineEnd(line, indexStart);
			comment.setIndexStart(indexStart);
			
			if(indexEnd==-1) {
				comment.setCompleted(false);
				comment.setIndexEnd(line.length());
        	}
			else 
				comment.setIndexEnd(indexEnd);
		}
		
		return comment;
	}
	
	private FoundComment findMultilineComment(String line) {
		FoundComment comment = new FoundComment();
		int indexStart = line.indexOf("/*");; // индекс начала комментария
		int indexOfQuotes1 = line.indexOf(quotes); // индекс первых кавычек, используется для проверки - не пишется ли /* в кавычках - чтоб не ломало поиск комментариев
		int indexOfQuotes2 = line.indexOf(quotes, indexOfQuotes1+1); // индекс первых кавычек, используется для проверки - не пишется ли /* в кавычках - чтоб не ломало поиск комментариев 
		int indexEnd = -1; // индекс окончания комментария, если это '/* */'
		
		/* если (/*) нашелся и он не находится в кавычках */
		if(indexStart!=-1 && (indexOfQuotes1==-1 || indexOfQuotes1>=indexStart || indexOfQuotes2<=indexStart)) {
			for(int i=indexStart; i<line.length(); i++)
				comment.setText(comment.getText()+line.charAt(i));
			indexEnd=findMultilineEnd(line, indexStart);
			comment.setIndexStart(indexStart);
			
			if(indexEnd==-1) {
				comment.setCompleted(false);
				comment.setIndexEnd(line.length());
        	}
			else 
				comment.setIndexEnd(indexEnd);
		}
		
		return comment;
	}
	
	private FoundComment findCommentStart(String line) {
		FoundComment comment = new FoundComment();
		
		comment = findSingleComment(line);
		if(comment.getText().equals("")) {
			comment = findDocumentationComment(line);
			if(comment.getText().equals("")) {
				comment = findMultilineComment(line);
			}
		}
		
		return comment;
	}
	
	private FoundComment findMultilineCommentEnd(String line) {
		FoundComment comment = new FoundComment(); 
		int indexEnd = findMultilineEnd(line); // индекс окончания комментария, если это '/* */'
		
		if(indexEnd!=-1) { // если конец найден
			for(int i=0; i<=indexEnd+1; i++)
				comment.setText(comment.getText()+line.charAt(i));
			comment.setIndexEnd(indexEnd);
		} else { // если конец не найден
			for(int i=0; i<line.length(); i++)
				comment.setText(comment.getText()+line.charAt(i));
			comment.setIndexEnd(line.length());
			comment.setCompleted(false);
		}
		return comment;
	}
	
	public ArrayList<FoundString> findAllStrings(String fileName, ArrayList<FoundString> strings) { // TODO
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
	        FoundString string = new FoundString();
            int lineCount=0;
            
            while((line = br.readLine())!=null){
            	lineCount++;
            	if (strings.isEmpty()==true || strings.get(strings.size()-1).isCompleted()==true) {
                	string = findStringStart(line);
                	if(!string.getText().equals("")) {
                		string.setLineNumber(lineCount);
                		string.setLine(line);
                		strings.add(string);
                	}
                } else {
                	string = findStringEnd(line);
                	string.setLineNumber(lineCount);
                	string.setLine(line);
                	strings.add(string);
                }
            } 
        }
		catch(FileNotFoundException ex) {
			System.err.println("Unable to open file '" + fileName + "'. It probably doesn't exist or has a different name");
		}
        catch(IOException ex) {
            System.err.println("Error reading file '" + fileName + "'");
            System.err.println(ex.getMessage());
		}
		
		return strings;
	}
	
	private FoundString findStringStart(String line) {
		FoundString string = new FoundString();
		int index1=findStringStartWithoutASpace(line); // найти (="), т.к. разные люди пишут присвоение по разному. Должно также найти и (==")
		int index2=findStringStartWithASpace(line); // найти (= "), т.к. разные люди пишут присвоение по разному. Должно также найти и (== ")
		int indexDoubleSlash=findDoubleSlashCommentStart(line);
		int indexEnd=-1;
				
		// если нашлось (=")
		if((index1!=-1 && indexDoubleSlash==-1) || (index1!=-1 && indexDoubleSlash!=-1 && index1<indexDoubleSlash)) {
			string.setIndexStart(index1);
			indexEnd=findQuotes(line, index1); // найти конец String в этой строке
			if(indexEnd!=-1) { // если конец на этой же строке
    			string.setIndexEnd(indexEnd);
    			    			
        		for(int i=index1; i<indexEnd+1; i++)
        			string.setText(string.getText()+line.charAt(i));
			}
		}
       
		// если нашлось (= ")
		if((index2!=-1 && indexDoubleSlash==-1) || (index2!=-1 && indexDoubleSlash!=-1 && index2<indexDoubleSlash)) {
			string.setIndexStart(index2);
			indexEnd=findQuotes2(line, index2); // найти конец String в этой строке
						
			if(indexEnd!=-1) { // если конец на этой же строке
    			string.setIndexEnd(indexEnd);
    			    			
        		for(int i=index2; i<indexEnd+1; i++)
        			string.setText(string.getText()+line.charAt(i));
			}
		}
		return string;
	}
	
	private FoundString findStringEnd(String line) {
		FoundString string = new FoundString();
		
		int indexEnd=findStringsEnd(line); // найти конец String в этой строке
		
		if(indexEnd!=-1) { // если конец найден
			string.setIndexEnd(indexEnd);
			
			for(int i=0; i<=indexEnd+1; i++)
				string.setText(string.getText()+line.charAt(i));		
		}
		
		return string;
	}
	
	/* Найти '://' */
	private int findDoubleDotDoubleSlash(String line, int indexOfDoubleDot) {
		int index = line.indexOf("://", indexOfDoubleDot+1);
		
		if(index!=-1)
			index++; // добавить 1, потому что из-за двоеточих, индекс :// и // не совпадет
		return index;
	}
	
	private int findQuotes(String line) {
		int index = line.indexOf(quotes);
		return index;
	}
	
	private int findQuotes(String line, int startFrom) { // для поиска закрывающих кавычек после ="
		int index = line.indexOf(quotes, startFrom+2); // +2, потому что в '="' кавычки на третьем индексе - продолжение не находит
		return index;
	}
	
	private int findQuotes2(String line, int startFrom) { // для поиска закрывающих кавычек после = "
		int index = line.indexOf(quotes, startFrom+3); // +3, потому что в '= "' кавычки на третьем индексе - продолжение не находит
		return index;
	}
	
	private int findDoubleDotDoubleSlash(String line) {
		int index = line.indexOf("://");
		
		if(index!=-1)
			index++; // добавить 1, потому что из-за двоеточих, индекс :// и // не совпадет
		return index;
	}
	
	private int findDoubleSlashCommentStart(String line, int indexStart) {
		int index=line.indexOf("//", indexStart+1);
		return index;
	}
	
	private int findDoubleSlashCommentStart(String line) {
		int index=line.indexOf("//");
		return index;
	}
	
	private int findStringStartWithASpace(String line) {
		int index=line.indexOf("= " + quotes);
		return index;
	}
	
	private int findStringStartWithoutASpace(String line) {
		int index=line.indexOf("=" + quotes);
		
		return index;
	}
	
	private int findStringsEnd(String line) {
		int index=line.indexOf(quotes + ";");
		
		return index;
	}

	private int findMultilineEnd(String line, int indexStart) {
		int index=line.indexOf("*/", indexStart); // искать в переданной строке конец коммента */
		
		return index;
	}
	
	private int findMultilineEnd(String line) {
		int index=line.indexOf("*/"); // искать в переданной строке конец коммента */
		
		return index;
	}
}
