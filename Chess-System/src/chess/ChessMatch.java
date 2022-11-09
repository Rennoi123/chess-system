package chess;
                                                                         // PARTIDA DE XADREZ
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;

    private List<Piece> piecesOnTheBoad =new ArrayList<>();

    private List<Piece> capturedPieces = new ArrayList<>();

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

    public boolean getCheck(){
        return check;
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
        Piece capturedPiece = makeMove(source, target); // Cria uma variavel para receber a operação makeMove que irá mover a peça de lugar

        if (testCheck(currentPlayer)){
            undoMove(source, target, capturedPiece);
            throw new ChessException("Você não pode se colocar em Check:");
        }

        check = (testCheck(opponent(currentPlayer))) ? true : false;

        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    //Logica de realizar o movimento das peças
    private Piece makeMove(Position source, Position target){
        Piece p = board.removePiece(source); //Remove peça da posição de origem
        Piece capturedPiece = board.removePiece(target); // remove peça na posição de destino que será a peça CAPTURADA
        board.placePiece(p, target); // coloca na posição de destino a peça que estava na posição de origem

        if (capturedPiece != null){
            piecesOnTheBoad.remove(capturedPiece); // Retira a peça na lista de peças o tabuleiro
            capturedPieces.add(capturedPiece);
        }
        return  capturedPiece;
    }

    private  void undoMove(Position source,Position target, Piece capturedPiece){
    Piece p = board.removePiece(target);
    board.placePiece(p, source);

    if (capturedPiece != null){
        board.placePiece(capturedPiece, target);
        capturedPieces.remove(capturedPiece);
        piecesOnTheBoad.add(capturedPiece);
    }
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
        if (!board.piece(position).isThereAnyPossibleMove()) {  //acessa o tabuleiro e acessa a peça e verifica se a peça tem algum movimento possivel
            throw new ChessException("Não existe movimentos possiveis para a peça selecionada");
        }
    }

    private void validateTargetPosition(Position source,Position target){
        if (!board.piece(source).possibleMove(target)){ // Testa se a peça pode fazer o movimento informado ( destino )
            throw new ChessException("A peça escolhida não pode se mover para esse posição");
        }
    }
    //Metodo para trocar de turno apos 1 jogada
    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer==Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private  Color opponent (Color color){
        return (color==Color.WHITE) ?Color.BLACK : Color.WHITE;
    }

    private  ChessPiece king (Color color){
        List<Piece> list = piecesOnTheBoad.stream().filter(x -> ((ChessPiece)x).getColor()==color).collect(Collectors.toList());
        for (Piece p : list){
            if (p instanceof King){
                return (ChessPiece) p;
            }
        }throw new IllegalStateException("Não existe o Rei na cor "+color+" no tabuleiro");
    }

    //Teste do Check
    private boolean testCheck (Color color){
        Position KingPosition =king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces =  piecesOnTheBoad.stream().filter(x -> ((ChessPiece)x).getColor()==opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPieces){
            boolean [] [] mat = p.possibleMoves();
            if (mat[KingPosition.getRow()][KingPosition.getColumn()]){
                return true;
            }
        }return false;
    }
    //Metodo para colocar uma nova peça
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoad.add(piece); //
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