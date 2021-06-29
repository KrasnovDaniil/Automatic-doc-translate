package classes;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
// import java.util.ArrayList;

/* Класс для поиска комментариев/String в коде */

/* TODO сделать поиск Сваггеров @ (здесь список: https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X) 
 * Продумать как программа будет запоминать количество переведенной информации(количество строк и комментариев) */

public class Finder {
	final char quotes = (char) 34; // в этой переменной хранятся кавычки, т.к. не получается писать кавычки в кавычках
	
	public Map<String, Integer> insertTranslation(String fileName){
		Map<String, Integer> amounts = new HashMap<String, Integer>();
		SearchResult comment = new SearchResult();
		Writer wr = new Writer();
		amounts.put("SingleComms", 0); amounts.put("MultiComms", 0); amounts.put("DocComms", 0); amounts.put("Total", 0);
		
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineCount=0;
            String longLine = "";
            
            while((line = br.readLine())!=null){
            	lineCount++;
            	
            	if (comment.isCompleted()==true) {
            		comment = findCommentStart(line);
            		if(!comment.getText().equals("")) {
            			comment.setLineNumber(lineCount);
            			if(comment.isCompleted()==true) {
            				comment.setLineNumberEnd(lineCount);
            				// comment.setTranslation(); // TODO вставить код перевода
        					longLine="";
        					wr.writeTranslation(fileName, comment);
        					addAmountOfComments(amounts, comment);
            			} else {
            				longLine+=line+"\n";
            			}
            		}
            	} else if (comment.isCompleted()==false){
            		longLine+=line;
            		comment = findMultilineCommentEnd(line, comment);
            		if(comment.isCompleted()==true) {
            			comment.setLineNumberEnd(lineCount);
            			// comment.setTranslation(); // TODO вставить код перевода
    					longLine = longLine.replace(comment.getText(), comment.getTranslation()); // TODO нужно ли это?
    					longLine="";
    					wr.writeTranslation(fileName, comment);
    					addAmountOfComments(amounts, comment);
            		} else {
            			longLine+="\n";
            		}
            	}
            } 
            System.out.println("Перевод и запись успешна!");
            br.close();
        }
		catch(FileNotFoundException ex) {
			System.err.println("Unable to open file '" + fileName + "'. It probably doesn't exist or has a different name");
		}
        catch(IOException ex) {
            System.err.println("Error reading file '" + fileName + "'");
            System.err.println(ex.getMessage());
        }
		return amounts;
	}
	
	private Map<String, Integer> addAmountOfComments(Map<String, Integer> amounts, SearchResult comment) {
		amounts.replace("Total", amounts.get("Total")+1);
		if(comment.getCommentType()==SearchResult.COMM_SINGLE_LINE)
			amounts.replace("SingleComms", amounts.get("SingleComms")+1);
		if(comment.getCommentType()==SearchResult.COMM_MULTI_LINE)
			amounts.replace("MultiComms", amounts.get("MultiComms")+1);
		if(comment.getCommentType()==SearchResult.COMM_DOC)
			amounts.replace("DocComms", amounts.get("DocComms")+1);
		
		
		return amounts;
	}
	
	private SearchResult findSingleComment(String line) {
		SearchResult comment = new SearchResult();
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
			comment.setCommentType(1);
			comment.setIndexEnd(line.length());
			comment.setCompleted(true);
		}
		
		return comment;
	}
	
	private SearchResult findDocumentationComment(String line) {
		SearchResult comment = new SearchResult();
		int indexStart = line.indexOf("/**");; // индекс начала комментария
		int indexOfQuotes1 = line.indexOf(quotes); // индекс первых кавычек, используется для проверки - не пишется ли /* в кавычках - чтоб не ломало поиск комментариев
		int indexOfQuotes2 = line.indexOf(quotes, indexOfQuotes1+1); // индекс первых кавычек, используется для проверки - не пишется ли /* в кавычках - чтоб не ломало поиск комментариев 
		int indexEnd = -1; // индекс окончания комментария, если это '/* */'
		
		/* если (/**) нашелся и он не находится в кавычках */
		if(indexStart!=-1 && (indexOfQuotes1==-1 || indexOfQuotes1>=indexStart || indexOfQuotes2<=indexStart)) {
			for(int i=indexStart; i<line.length(); i++)
				comment.setText(comment.getText()+line.charAt(i));
			indexEnd=findStarSlash(line, indexStart);
			comment.setIndexStart(indexStart);
			comment.setCommentType(3);
			
			if(indexEnd==-1) {
				comment.setCompleted(false);
				comment.setIndexEnd(line.length());
        	} else 
				comment.setIndexEnd(indexEnd);
		}
		
		return comment;
	}
	
	private SearchResult findMultilineComment(String line) {
		SearchResult comment = new SearchResult();
		int indexStart = line.indexOf("/*");; // индекс начала комментария
		int indexOfQuotes1 = line.indexOf(quotes); // индекс первых кавычек, используется для проверки - не пишется ли /* в кавычках - чтоб не ломало поиск комментариев
		int indexOfQuotes2 = line.indexOf(quotes, indexOfQuotes1+1); // индекс первых кавычек, используется для проверки - не пишется ли /* в кавычках - чтоб не ломало поиск комментариев 
		int indexEnd = -1; // индекс окончания комментария, если это '/* */'
		
		/* если (/*) нашелся и он не находится в кавычках */
		if(indexStart!=-1 && (indexOfQuotes1==-1 || indexOfQuotes1>=indexStart || indexOfQuotes2<=indexStart)) {
			for(int i=indexStart; i<line.length(); i++)
				comment.setText(comment.getText()+line.charAt(i));
			indexEnd=findStarSlash(line, indexStart);
			comment.setIndexStart(indexStart);
			comment.setCommentType(2);
			
			if(indexEnd==-1) {
				comment.setCompleted(false);
				comment.setIndexEnd(line.length());
        	}
			else 
				comment.setIndexEnd(indexEnd);
		}
		
		return comment;
	}
	
	private SearchResult findCommentStart(String line) {
		SearchResult comment = new SearchResult();
		
		comment = findSingleComment(line);
		if(comment.getText().equals("")) {
			comment = findDocumentationComment(line);
			if(comment.getText().equals("")) {
				comment = findMultilineComment(line);
			}
		}
		
		return comment;
	}
	
	private SearchResult findMultilineCommentEnd(String line, SearchResult comment) {
		int indexEnd = findStarSlash(line);
		
		if(indexEnd!=-1) { // если конец найден
			comment.setText(comment.getText()+System.getProperty("line.separator"));
			for(int i=0; i<=indexEnd+1; i++)
				comment.setText(comment.getText()+line.charAt(i));
			comment.setIndexEnd(indexEnd);
			comment.setCompleted(true);
		} else { // если конец не найден
			comment.setText(comment.getText()+System.getProperty("line.separator"));
			for(int i=0; i<line.length(); i++)
				comment.setText(comment.getText()+line.charAt(i));
			comment.setIndexEnd(line.length());
		}
		return comment;
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
	
	private int findDoubleSlashCommentStart(String line, int indexStart) {
		int index=line.indexOf("//", indexStart+1);
		return index;
	}
	
	private int findDoubleSlashCommentStart(String line) {
		int index=line.indexOf("//");
		return index;
	}

	private int findStarSlash(String line, int indexStart) {
		int index=line.indexOf("*/", indexStart); // искать в переданной строке конец коммента */
		
		return index;
	}
	
	private int findStarSlash(String line) {
		int index=line.indexOf("*/"); // искать в переданной строке конец коммента */
		
		return index;
	}
}
