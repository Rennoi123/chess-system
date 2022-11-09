package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMovie(Position position){
        ChessPiece p =  (ChessPiece)getBoard().piece(position);
        return  p == null || p.getColor() !=getColor();
        }
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        //Movimento para cima
        p.setValues(position.getRow()-1, position.getColumn());
        if(getBoard().positionExists(p)&& canMovie(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }
        //Movimento para baixo
        p.setValues(position.getRow()+1, position.getColumn());
        if(getBoard().positionExists(p)&& canMovie(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }
        //Movimento para esquerda
        p.setValues(position.getRow(), position.getColumn()-1);
        if(getBoard().positionExists(p)&& canMovie(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }
        //Movimento para direita
        p.setValues(position.getRow(), position.getColumn()+1);
        if(getBoard().positionExists(p)&& canMovie(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }
        //Movimento na diagonal Superior (esquerda)
        p.setValues(position.getRow()-1, position.getColumn()-1);
        if(getBoard().positionExists(p)&& canMovie(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }
        //Movimento na diagonal Superior (Direita)
        p.setValues(position.getRow()-1, position.getColumn()+1);
        if(getBoard().positionExists(p)&& canMovie(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }
        //Movimento na diagonal Inferior (esquerda)
        p.setValues(position.getRow()+1, position.getColumn()-1);
        if(getBoard().positionExists(p)&& canMovie(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }
        //Movimento na diagonal Inferior (Direita)
        p.setValues(position.getRow()+1, position.getColumn()+1);
        if(getBoard().positionExists(p)&& canMovie(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }



        return mat;

    }
    @Override
    public boolean possibleMove(Position position) {
        return super.possibleMove(position);
    }



}
