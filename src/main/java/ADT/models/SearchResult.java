package ADT.models;

import ADT.services.implementations.TranslateServiceImpl;
import org.apache.commons.text.StringEscapeUtils;

public class SearchResult {
    String text = "";
    String line = "";
    String translation = "!!! ЗДЕСЬ ДОЛЖЕН БЫТЬ ПЕРЕВОД !!!";
    boolean isCompleted = true;
    int indexStart = 0;
    int indexEnd = 0;
    int lineNumber = 0;
    int lineNumberEnd = 0;
    int commentType = 0;
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
    public String getTranslation() {
        return translation;
    }
    public void setTranslation(TranslateServiceImpl tSimpl) {
        String text1 = StringEscapeUtils.escapeJava(text);
        text1 = StringEscapeUtils.escapeJava(text1);
        this.translation = tSimpl.translateText(text1);
        this.translation = StringEscapeUtils.unescapeJava(translation);
    }
    public boolean isCompleted() {
        return isCompleted;
    }
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}