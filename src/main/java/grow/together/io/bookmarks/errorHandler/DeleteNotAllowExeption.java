package grow.together.io.bookmarks.errorHandler;

public class DeleteNotAllowExeption extends Exception {
    private static final long serialVersionUID = 1L;

    public DeleteNotAllowExeption() {
    }

    public DeleteNotAllowExeption(String message) {
        super(message);
    }
}
