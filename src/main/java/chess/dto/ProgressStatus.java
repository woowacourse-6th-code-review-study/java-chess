package chess.dto;

public enum ProgressStatus {

    WHITE_WIN(false),
    BLACK_WIN(false),
    PROGRESS(true),
    ;

    private final boolean isContinue;

    ProgressStatus(boolean isContinue) {
        this.isContinue = isContinue;
    }

    public boolean isContinue() {
        return isContinue;
    }
}
