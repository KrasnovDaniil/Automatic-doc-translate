package classes;

import java.io.*;

/* TODO сделать поиск Сваггеров @ (здесь список: https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X) */
public class Finder {
	int indexStart = -1; // индекс начала комментария
	int indexOfDoubleDot = -1; // индекс для поиска двоеточих, чтобы не выводить https:// как коммент
	int indexOfQuotes1 = -1;
	int indexOfQuotes2 = -1;
	int indexEnd = -1; // индекс окончания комментария, если это '/* */'
	int countLines = 0; // количество строк коммента '/* */', если коммент заканчивается на другой строке
	int countLines1 = 0; // количество строк коммента '/* */', если коммент заканчивается на другой строке
	
	final char dm = (char) 34; // в этой переменной хранятся кавычки, т.к. не получается писать кавычки в кавычках
	
	boolean bool = true; // true - конец коммента '/* */' найден или не ищется. false - конец коммента '/* */' не найден, но ищется
	boolean bool1 = true; // true - конец строки найден или не ищется. false - конец строки не найден, но ищется
	
	/* Найти всё - комментарии, строки, нужно ввести название файла */
	public void findAll(String fileName) { 
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) // чтение из файла
        {
            String line; // переменная очередной строки
            
            while((line = br.readLine()) !=null){ // while not the end of the file
            	System.out.print("Очередная строка: " + line);
            	findComments(line);
            	findStrings(line);
            	System.out.print("\n");
            } 
        }
		catch(FileNotFoundException ex) {
			System.err.println("Unable to open file '" + fileName + "'. It probably doesn't exist or has a different name");
		}
        catch(IOException ex){ // finding errors
            System.err.println("Error reading file '" + fileName + "'");
            System.err.println(ex.getMessage());
        }
	}
	
	/* Найти комментарии */
	private void findComments(String line) {
		if(bool==true) { // если поиск комментария окончен или поиск не начинался
			do {
				indexStart=line.indexOf("//", indexStart+1); // искать комментарий '//'
				indexOfDoubleDot=line.indexOf("://", indexOfDoubleDot+1); // искать есть ли '://', обычно в ссылках используется (аля https://vk.com)
			
				if(indexOfDoubleDot!=-1) // если есть ://
					indexOfDoubleDot++; // добавить 1, потому что из-за двоеточих, индекс :// и // не совпадет
				// System.out.println("\nTESTING. IndexStart="+indexStart+" , indexDouble="+indexOfDoubleDot);
			} while (indexStart!=-1 && indexOfDoubleDot==indexStart);
			
			 
			if(indexStart!=-1 && indexOfDoubleDot!=indexStart) { // если '//' есть и :// не совпадает
				System.err.print(". Комментарий '//' начинается с индекса = " + indexStart + "");
			} else { // иначе - искать коммент '/*'
				indexStart=line.indexOf("/*");
				indexOfQuotes1=line.indexOf(dm);
				indexOfQuotes2=line.indexOf(dm, indexOfQuotes1+1);
	    		
				/* если (/*) нашелся, вывести начало, искать конец комментария */
				if(indexStart!=-1 && (indexOfQuotes1==-1 || indexOfQuotes1>=indexStart || indexOfQuotes2<=indexStart)) {
					System.err.print(". Комментарий '/*' начинается с индекса = " + indexStart);
					indexEnd=findCommentEnd(line); // найти конец комментария в этой строке
					
					if(indexEnd!=-1) { // если конец нашелся
	            		System.err.print(" и '*/' заканчивается на индексе = " + indexEnd);
	            		indexEnd = -1;
	            	} else {
						bool = false; // иначе оставить метку на продолжение поиска конца
	            	}
				}
			}
    	} else { // если поиск комментария не окончен
    		indexEnd=findCommentEnd(line); // найти конец комментария в этой строке
    		countLines++; // прибавить 1 к счету строк
    		
    		if(indexEnd!=-1) { // если конец найден
    			System.err.print(". Комментарий '*/' заканчивается на индексе = " + indexEnd + ", на " + countLines + " строчек(-ки) ниже");
        		indexEnd = -1;
        		countLines = 0;
        		bool = true; // оставить метку что конец найден
    		} else
    			System.err.print(". Комментарий '/* */' продолжается");
    	}
	}
	
	/* Найти строки */
	private void findStrings(String line) {
		int index1;
		int index2;
		int indexEnd;
		int indexContinue;
		
		if(bool1) {
			index1=line.indexOf("=" + dm); // найти (="), т.к. разные люди пишут присвоение по разному. Должно также найти и (==")
			index2=line.indexOf("= " + dm); // найти (= "), т.к. разные люди пишут присвоение по разному. Должно также найти и (== ")
			indexEnd=findStringEnd(line); // найти конец String в этой строке
			indexContinue=findStringContinuation(line); // найти продолжатель String ("text" +) который используется для переноса

			// если нашлось (=")
			if(index1!=-1) {
				System.err.print(". Здесь есть String, начинается с индекса " + index1);
				if(indexEnd!=-1) { // если конец на этой же строке и нет "продолжателя"
	        		System.err.print(" и заканчивается на индексе = " + indexEnd);
	        		indexEnd = -1;
	        	} else if(indexContinue!=-1){
					bool1 = false; // иначе оставить метку на продолжение поиска конца
	        	} else
	        		System.err.print(" и, видимо, не заканчивается");
			}
        
			// если нашлось (= ")
			if(index2!=-1) {
				System.err.print(". Здесь есть String, начинается с индекса " + index2);
				if(indexEnd!=-1) { // если конец нашелся
	        		System.err.print(" и заканчивается на индексе = " + indexEnd);
	        		indexEnd = -1;
	        	} else if(indexContinue!=-1){
					bool1 = false; // иначе оставить метку на продолжение поиска конца
	        	} else
	        		System.err.print(" и, видимо, не заканчивается");
			}
			
		} else {
			indexEnd=findStringEnd(line); // найти конец комментария в этой строке
    		countLines1++; // прибавить 1 к счету строк
    		
    		if(indexEnd!=-1) { // если конец найден
    			System.err.print(". String заканчивается на индексе = " + indexEnd + ", на " + countLines1 + " строчек(-ки) ниже");
        		indexEnd = -1;
        		countLines1 = 0;
        		bool1 = true; // оставить метку что конец найден
    		}
		}
	}
	
	/* найти продолжение строки (" +), используется в findStrings */
	private int findStringContinuation(String line) {
		int index=line.indexOf(dm + " +");
		
		return index;
	}
	
	/* найти конец строки (";), используется в findStrings */
	private int findStringEnd(String line) {
		int index=line.indexOf(dm + ";");
		
		return index;
	}

	// найти конец комментария */, используется в findComments
	private int findCommentEnd(String line) {
		int index=line.indexOf("*/", indexStart); // искать в переданной строке конец коммента */
		
		return index;
	}
}
