package chess;
                                                                         // PARTIDA DE XADREZ
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        turn =1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }
    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i=0; i<board.getRows(); i++) {
            for (int j=0; j<board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    // metodo que informa quais posições a peça dele pode fazer===
    public boolean [][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    //Metodo para retirar a peça na opção de origem para colocar na opção de destino
    public ChessPiece perfomChessMove(ChessPosition sourcePosition,ChessPosition tagetPosition){
        Position source = sourcePosition.toPosition(); // converte posição para posição da matriz
        Position target = tagetPosition.toPosition();
        validateSourcePosition(source); // validar a posição de origem
        validateTargetPosition(source,target);
        Piece capturePiece = makeMove(source, target); // Cria uma variavel para receber a operação makeMove que irá mover a peça de lugar
        nextTurn();
        return (ChessPiece) capturePiece;
    }

    //Logica de realizar o movimento das peças
    private Piece makeMove(Position source, Position target){
        Piece p = board.removePiece(source); //Remove peça da posição de origem
        Piece capturedPiece = board.removePiece(target); // remove peça na posição de destino que será a peça  CAPTURADA
        board.placePiece(p, target);
        return (ChessPiece) capturedPiece;
    }

    // Valida a posição de origem
    //Verifica se não existe uma peça na posição de origem
    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("Não existe peça na posição de origem: ");
        }
        if (currentPlayer != ((ChessPiece)  board.piece(position)).getColor()){  // verifica esta tentando movimentar a peça do outro player
            throw new ChessException("A peça escolhida não é sua");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {  //acessa o tabuleiro  e acessa a peça e verifica se a peça tem algum movimento possivel
            throw new ChessException("Não existe movimentos possiveis para a peça selecionada");
        }
    }

    private void validateTargetPosition(Position source,Position target){
        if (!board.piece(source).possibleMove(target)){ // Testa se a peça pode fazer o movimento informado ( destino )
            throw new ChessException("A peça escolhida não pode se mover para esse posição");
        }
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }
    //Metodo para trocar de turno apos 1 jogada
    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer==Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void initialSetup() {
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}