package ADT.models;

import ADT.services.implementations.TranslateServiceImpl;

public class SearchResult {
    private String text = "";
    private String line = "";
    private String translation = "!!! ЗДЕСЬ ДОЛЖЕН БЫТЬ ПЕРЕВОД !!!";
    private boolean isCompleted = true;
    private int indexStart = 0;
    private int indexEnd = 0;
    private int lineNumber = 0;
    private int lineNumberEnd = 0;
    private int commentType = 0;

    public String getTranslation() {
        return translation;
    }
    public void setTranslation(TranslateServiceImpl tSimpl) {
        this.translation = tSimpl.translateText(text);
    }

    public static final int COMM_SINGLE_LINE = 1;
    public static final int COMM_MULTI_LINE = 2;
    public static final int COMM_DOC = 3;
    public int getLineNumberEnd() {
        return lineNumberEnd;
    }
    public void setLineNumberEnd(int lineNumberEnd) {
        this.lineNumberEnd = lineNumberEnd;
    }
    public int getCommentType() {
        return commentType;
    }
    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }
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

    public boolean isCompleted() {
        return isCompleted;
    }
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
