package boardgame;

public class Piece {

    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        position = null;
    }

    // somente classes do mesmo pacote vão acessar
    protected Board getBoard() {
        return board;
    }
}
