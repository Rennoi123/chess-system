package boardgame;

public abstract class Piece {

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
    public abstract boolean [][] possibleMoves();

    //Metodo concreto que usa um metodo abstato
    //Testa se a peça pode mover para a posição informada
    public boolean possibleMove(Position position){
         return possibleMoves()[position.getRow()][position.getColumn()];
    }

    //Verifica o movimento da peça para que tenha o caminho certo
    //Testa se a matriz na coluna e na linha é verdade
    // caso seja, terá um movimento possivel de se fazer com a peça
    public boolean isThereAnyPossibleMove() {
        boolean[][] mat = possibleMoves();
        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat.length; j++) {
                if (mat[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
