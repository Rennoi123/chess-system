package chess;
                                                                         // PARTIDA DE XADREZ
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

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

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
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
    // Executar jogada dde Xadrez
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
        ChessPiece movedPiece = (ChessPiece) board.piece(target); // faz uma referencia a peça que se moveu a cima no performChessMove

        //PROMOTION
        promoted =null;
        if (movedPiece instanceof  Pawn){
            if((movedPiece.getColor())==Color.WHITE && target.getRow()==0 ||(movedPiece.getColor())==Color.BLACK && target.getRow()==7){
                promoted = (ChessPiece)board.piece(target);
                promoted = replacePromotedPiece("Q");
            }
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
        //#specialMove en passant
        //Verifica se a peça movida é um Peao
        // Caso seja significa q foi o 1  movimento do peao e foi de 2 casas

        if(movedPiece instanceof  Pawn && (target.getRow())== source.getColumn()-2 || (target.getRow())== source.getColumn()-2 ){
            enPassantVulnerable = movedPiece;
        }
        else {
            enPassantVulnerable = null;
        }

        return (ChessPiece) capturedPiece;
    }
    public ChessPiece replacePromotedPiece(String type){
         if(promoted == null){
             throw new IllegalStateException("Não possui peça para ser promovida");
         }
         if (!type.equals("B")&&!type.equals("C")&&!type.equals("R")&&!type.equals("Q")){
             return promoted; // caso seja selecionado uma letra invalida, ele retorna o valor que foi declarado na promoted ("Q")
         }

         Position pos = promoted.getChessPosition().toPosition();
         Piece p = board.removePiece(pos);
         piecesOnTheBoard.remove(p);
         ChessPiece newPiece = newPiece(type,promoted.getColor());
         board.placePiece(newPiece,pos);
         piecesOnTheBoard.add(newPiece);

         return newPiece;
    }

    private  ChessPiece newPiece(String type, Color color){
        if (type.equals("B")) return  new Bishop(board,color);
        if (type.equals("C")) return  new Knight(board,color);
        if (type.equals("Q")) return  new Queen(board,color);
         return  new Rook(board,color);
    }
    //Logica de realizar o movimento das peças
    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source); //Remove peça da posição de origem
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target); // remove peça na posição de destino que será a peça CAPTURADA
        board.placePiece(p, target); // coloca na posição de destino a peça que estava na posição de origem

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece); // Retira a peça na lista de peças o tabuleiro
            capturedPieces.add(capturedPiece);
        }
        //#specialmove  Rook pequeno
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) { // verifica se o p é instancia de king e pega a coluna de destino e e iguala a de origem e soma 2
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1); // +1 coluna pra direita
            ChessPiece rook = (ChessPiece) board.removePiece(sourceT); // criam uma variavel rook para remover a torre  de onde ela está
            board.placePiece(rook, targetT); //Coloca a Torre no lugar de desitno dela que é referente ao moviemnto Rook
            rook.increaseMoveCount(); //incremente e faz a quantidade de movimento dela

        }
        //#specialmove Queenside Grande
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) { // verifica se o p é instancia de king e pega a coluna de destino e e iguala a de origem e soma 2
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1); // -1 coluna pra esquerda
            ChessPiece rook = (ChessPiece) board.removePiece(sourceT); // criam uma variavel rook para remover a torre  de onde ela está
            board.placePiece(rook, targetT);//Coloca a Torre no lugar de desitno dela que é referente ao moviemnto Rook
            rook.decreaseMoveCount(); //incremente e faz a quantidade de movimento dela
        }

        //en passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                }
                else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece); // remove a peça do tabuleirop ( peça q sofreu en passan )

            }
        }
        return capturedPiece;
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
        //#specialmove  Rook pequeno
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) { // verifica se o p é instancia de king e pega a coluna de destino e e iguala a de origem e soma 2
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1); // +1 coluna pra direita
            ChessPiece rook = (ChessPiece) board.removePiece(targetT); // criam uma variavel rook para remover a torre  de onde ela está
            board.placePiece(rook, sourceT); //Coloca a Torre no lugar de desitno dela que é referente ao moviemnto Rook
            rook.decreaseMoveCount(); //incremente e faz a quantidade de movimento dela
        }
        //#specialmove Queenside Grande
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) { // verifica se o p é instancia de king e pega a coluna de destino e e iguala a de origem e soma 2
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1); // +1 coluna pra direita
            ChessPiece rook = (ChessPiece) board.removePiece(targetT); // criam uma variavel rook para remover a torre  de onde ela está
            board.placePiece(rook, sourceT);//Coloca a Torre no lugar de desitno dela que é referente ao moviemnto Rook
            rook.decreaseMoveCount(); //incremente e faz a quantidade de movimento dela
        }
        //en passant
        if (p instanceof Pawn){
            //Verifica se  a peça andou na diagonal e não capturou nenhuma peça
            if(source.getColumn()!=target.getColumn()&& capturedPiece== enPassantVulnerable){
                ChessPiece pawn = (ChessPiece)board.removePiece(target);
                Position pawnPosition;
            if(p.getColor()==Color.WHITE){
                pawnPosition = new Position(3, target.getColumn());

                } else{
                pawnPosition = new Position(4, target.getColumn());
            }
            board.placePiece(pawn,pawnPosition);

            }
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
        placeNewPiece('e', 1, new King(board, Color.WHITE,this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE,this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK,this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK,this));
    }
}