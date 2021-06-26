package classes;

public class FoundComment {
	String text = "";
	String line = "";
	String translation = "";
	boolean isCompleted = true;
	int indexStart = 0;
	int indexEnd = 0;
	int lineNumber = 0;
	
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public int getIndexStart() {
		return indexStart;
	}
	public void setIndexStart(int indexStart) {
		this.indexStart = indexStart;
	}
	public int getIndexEnd() {
		return indexEnd;
	}
	public void setIndexEnd(int indexEnd) {
		this.indexEnd = indexEnd;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
	public boolean isCompleted() {
		return isCompleted;
	}
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
}
