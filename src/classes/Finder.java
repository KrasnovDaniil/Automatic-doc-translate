package classes;

import java.io.*;
import java.util.ArrayList;

/* Класс для поиска комментариев/String в коде */

/* TODO сделать поиск Сваггеров @ (здесь список: https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X) 
 * Продумать как программа будет запоминать количество переведенной информации(количество строк и комментариев) */

public class Finder {
	final char quotes = (char) 34; // в этой переменной хранятся кавычки, т.к. не получается писать кавычки в кавычках
	
	public void insertTranslation(String fileName){
		SearchResult comment = new SearchResult();
		SearchResult string = new SearchResult();
		Writer wr = new Writer();
		
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineCount=0;
            String longLine = "";
            
            while((line = br.readLine())!=null){
            	lineCount++;
            	
            	string = findStringStart(line);
            	if(!string.getText().equals("")) {
        			string.setLineNumber(lineCount);
        			if(string.isCompleted()==true) {
        				string.setLineNumberEnd(lineCount);
        				// TODO do the translate thing
    					// line = line.replace(string.getText(), string.getTranslation()); // нужно ли это? TODO
    					wr.writeTranslation(fileName, string);
    					line.replace(string.getText(), string.getTranslation());
        			}
            	}
            	
            	if (comment.isCompleted()==true) {
            		comment = findCommentStart(line);
            		if(!comment.getText().equals("")) {
            			comment.setLineNumber(lineCount);
            			if(comment.isCompleted()==true) {
            				comment.setLineNumberEnd(lineCount);
            				// TODO do the translate thing
        					// line = line.replace(comment.getText(), comment.getTranslation()); // нужно ли это? TODO
        					longLine="";
        					wr.writeTranslation(fileName, comment);
            			} else {
            				longLine+=line+"\n";
            			}
            		}
            	} else if (comment.isCompleted()==false){
            		longLine+=line;
            		comment = findMultilineCommentEnd(line, comment);
            		if(comment.isCompleted()==true) {
            			comment.setLineNumberEnd(lineCount);
    					longLine = longLine.replace(comment.getText(), comment.getTranslation()); // TODO do the string change to translation
    					System.out.println(longLine);
    					longLine="";
    					wr.writeTranslation(fileName, comment);// TODO do the file write thing
            		} else {
            			longLine+="\n";
            		}
            	}
            	
            	if(lineCount==29) {
            		System.out.println("29 строка. String = "+string.getText());
            		System.out.println("29 строка. Comm = "+comment.getText());
            	}
            	
            	/* if (string.isCompleted()==true) {
                	string = findStringStart(line);
                	if(!string.getText().equals("")) {
                		string.setLineNumber(lineCount);
                		// string.setLine(line);
                		// TODO do the translate thing
    					// TODO do the string change to translation
                	}
                } else if (string.isCompleted()==false){
                	string = findStringEnd(line);
                	// string.setLineNumber(lineCount);
                	// string.setLine(line);
                	string.setLineNumberEnd(lineCount);
                	// TODO do the translate thing
					// TODO do the string change to translation
                } */
            } 
            br.close();
        }
		catch(FileNotFoundException ex) {
			System.err.println("Unable to open file '" + fileName + "'. It probably doesn't exist or has a different name");
		}
        catch(IOException ex) {
            System.err.println("Error reading file '" + fileName + "'");
            System.err.println(ex.getMessage());
        }
		
	}
	
	public ArrayList<SearchResult> findAllComments(String fileName, ArrayList<SearchResult> comments) { 
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            SearchResult comment = new SearchResult();
            int lineCount=0;
            
            while((line = br.readLine())!=null){
            	lineCount++;
            	if (comment.isCompleted()==true) {
            		comment = findCommentStart(line);
            		if(!comment.getText().equals("")) {
            			comment.setLineNumber(lineCount);
            			if(comment.isCompleted()==true) {
            				comment.setLineNumberEnd(lineCount);
            				comments.add(comment);
            			}
            		}
            	} else {
            		comment = findMultilineCommentEnd(line, comment);
            		// comment.setLineNumber(lineCount);
            		// comment.setLine(line);
            		if(comment.isCompleted()==true) {
            			comment.setLineNumberEnd(lineCount);
            			comments.add(comment);
            		}
            	}
            }
            br.close();
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
	
	public SearchResult findCommFromALine(String fileName, int lineNumber) {
		SearchResult comment = new SearchResult();
		
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineCount=0;
            
            while((line = br.readLine())!=null){
            	lineCount++;
            	if(lineCount==lineNumber || comment.isCompleted()==false) {
            		if (comment.isCompleted()==true) {
            			comment = findCommentStart(line);
            			if(!comment.getText().equals("")) {
            				comment.setLineNumber(lineCount);
            				// 	comment.setLine(line);
            				if(comment.isCompleted()==true) {
            					comment.setLineNumberEnd(lineCount);
            					// TODO do the translate thing
            					// TODO do the string change to translation
            				}
            			}
            		} else {
            			comment = findMultilineCommentEnd(line, comment);
            			// comment.setLineNumber(lineCount);
            			// comment.setLine(line);
            			if(comment.isCompleted()==true) {
            				comment.setLineNumberEnd(lineCount);
            				// TODO do the translate thing
        					// TODO do the multiline string change to translated text
            			}
            		}
            	}
            } 
            br.close();
        }
		catch(FileNotFoundException ex) {
			System.err.println("Unable to open file '" + fileName + "'. It probably doesn't exist or has a different name");
		}
        catch(IOException ex) {
            System.err.println("Error reading file '" + fileName + "'");
            System.err.println(ex.getMessage());
        }
		return comment;
	}
	
	public int getAmountOfLinesFromAFile(String fileName) {
		int amount = 0;
		
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            
            while((line = br.readLine())!=null){
            	amount++;
            } 
            br.close();
        }
		catch(FileNotFoundException ex) {
			System.err.println("Unable to open file '" + fileName + "'. It probably doesn't exist or has a different name");
		}
        catch(IOException ex) {
            System.err.println("Error reading file '" + fileName + "'");
            System.err.println(ex.getMessage());
        }
		return amount;
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
	
	public ArrayList<SearchResult> findAllStrings(String fileName, ArrayList<SearchResult> strings) { // TODO
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
	        SearchResult string = new SearchResult();
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
            br.close();
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
	
	private SearchResult findStringStart(String line) {
		SearchResult string = new SearchResult();
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
	
	private SearchResult findStringEnd(String line) {
		SearchResult string = new SearchResult();
		
		int indexEnd=findQuoteColon(line); // найти конец String в этой строке
		
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
	
	private int findQuoteColon(String line) {
		int index=line.indexOf(quotes + ";");
		
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
