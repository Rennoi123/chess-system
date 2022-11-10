package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {


    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }
    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position){
        ChessPiece p =  (ChessPiece)getBoard().piece(position);
        return  p == null || p.getColor() !=getColor();
        }
        private boolean testRookCastling(Position position){
        ChessPiece p = (ChessPiece)getBoard().piece(position); //Pega a peça que esta na posição p
            return p != null && p instanceof Rook && p.getColor()==getColor()&& p.getMoveCount()==0; //Testa se a peça é diferente de null e depois verifica se é uma Rook e se a cor é a mesma do rei e verifica se eleas não se  movimentaram
        }
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        //Movimento para cima
        p.setValues(position.getRow()-1, position.getColumn());
        if(getBoard().positionExists(p)&& canMove(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }
        //Movimento para baixo
        p.setValues(position.getRow()+1, position.getColumn());
        if(getBoard().positionExists(p)&& canMove(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }
        //Movimento para esquerda
        p.setValues(position.getRow(), position.getColumn()-1);
        if(getBoard().positionExists(p)&& canMove(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }
        //Movimento para direita
        p.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        //Movimento na diagonal Superior (esquerda)
        p.setValues(position.getRow()-1, position.getColumn()-1);
        if(getBoard().positionExists(p)&& canMove(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }
        //Movimento na diagonal Superior (Direita)
        p.setValues(position.getRow()-1, position.getColumn()+1);
        if(getBoard().positionExists(p)&& canMove(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }
        //Movimento na diagonal Inferior (esquerda)
        p.setValues(position.getRow()+1, position.getColumn()-1);
        if(getBoard().positionExists(p)&& canMove(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }
        //Movimento na diagonal Inferior (Direita)
        p.setValues(position.getRow()+1, position.getColumn()+1);
        if(getBoard().positionExists(p)&& canMove(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }

        //#specialmove castling

        if(getMoveCount()==0&&!chessMatch.getCheck()) {
            //#Movimento do Rook pequeno
            Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(posT1)) {
                Position p1 = new Position(position.getRow(), position.getColumn() + 1);
                Position p2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) { //Se no tabuleiro a posição p1 e p2 forem igual a null ( não possuem peça )
                    mat[position.getRow()][position.getColumn() + 2] = true;
                }
            }
        }

        //#Movimento do Rook Grande
        if(getMoveCount()==0&&!chessMatch.getCheck()) {
            Position posT2 = new Position(position.getRow(), position.getColumn() - 4); // pega a posição da torre a esquerda do rei
            if (testRookCastling(posT2)) { // testa se a torre esta apta ao movimento de rook
                Position p1 = new Position(position.getRow(), position.getColumn() - 1); // pega a posição de t1 == getColumn -1
                Position p2 = new Position(position.getRow(), position.getColumn() - 2); // pega a posição de t2 == getColumn -2
                Position p3 = new Position(position.getRow(), position.getColumn() - 3); // pega a posição de t2 == getColumn -2
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) { //Se no tabuleiro a posição p1 e p2 forem igual a null ( não possuem peça )
                    mat[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }
        return mat;
    }
    @Override
    public boolean possibleMove(Position position) {
        return super.possibleMove(position);
    }



}
