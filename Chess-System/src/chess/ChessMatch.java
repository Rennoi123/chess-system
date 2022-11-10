package chess;
                                                                         // PARTIDA DE XADREZ
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;

    private List<Piece> piecesOnTheBoard =new ArrayList<>();

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
    public boolean getCheckMate() {
        return checkMate;
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

        //Verifica se a jogada que fi feita deixa o oponente em ChecMate
        //Caso seja verdadeira encerra jogo

        if (testCheckMate(opponent(currentPlayer))){
            checkMate = true;
        }
        else {
            nextTurn();
        }

        return (ChessPiece) capturedPiece;
    }
    //Logica de realizar o movimento das peças
    private Piece makeMove(Position source, Position target){
        ChessPiece p =  (ChessPiece) board.removePiece(source); //Remove peça da posição de origem
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target); // remove peça na posição de destino que será a peça CAPTURADA
        board.placePiece(p, target); // coloca na posição de destino a peça que estava na posição de origem

        if (capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece); // Retira a peça na lista de peças o tabuleiro
            capturedPieces.add(capturedPiece);
        }
        return  capturedPiece;
    }

    private  void undoMove(Position source,Position target, Piece capturedPiece){
    ChessPiece p = (ChessPiece) board.removePiece(target);
    p.decreaseMoveCount();
    board.placePiece(p, source);

    if (capturedPiece != null){
        board.placePiece(capturedPiece, target);
        capturedPieces.remove(capturedPiece);
        piecesOnTheBoard.add(capturedPiece);
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
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor()==color).collect(Collectors.toList());
        for (Piece p : list){
            if (p instanceof King){
                return (ChessPiece)p;
            }
        }throw new IllegalStateException("Não existe o Rei na cor "+color+" no tabuleiro");
    }

    //Teste do Check
    private boolean testCheck (Color color){
        Position KingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor()==opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPieces){
            boolean [][] mat = p.possibleMoves();
            if (mat[KingPosition.getRow()][KingPosition.getColumn()]){
                return true;
            }
        }return false;
    }

    //Metodo que testa se a peça esta em CheckMate
    private boolean testCheckMate(Color color) {
        if(!testCheck(color)){
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor()==(color)).collect(Collectors.toList());
        for (Piece p :  list) { // Percorre todas as peças que pertence a lista para verificar se alguma peça onsegue tirar o rei do Check
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++){
                for (int j = 0; j<board.getColumns();j++){

                    if(mat[i][j]){

                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i,j);
                        Piece capturedPiece = makeMove(source,target);
                        boolean testCheck = testCheck(color);
                        undoMove(source,target,capturedPiece);

                        if(!testCheck){ // se não estava em check ele retorna falso
                            return false;
                        }
                    }
                }
            }
        }return true;
    }

        //Metodo para colocar uma nova peça
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece); //
    }


    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
    }
}