package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        //Movendo para cima 1 casa
        if (getColor() == Color.WHITE) {
            p.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // verifica se existe a posição e depois verifica se não possui peça na posição
                mat[p.getRow()][p.getColumn()] = true;
            }
            //Caso seja o 1º Movimento da peça ele pode andar 2 casas
            p.setValues(position.getRow() - 2, position.getColumn());
            Position p2 = new Position(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
            }
//            Testa se tem 1 peça na Diagonal superior Esquerda, caso tenha ele pode capturar a peça
            p.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // verifica se existe a posição e depois verifica se não possui peça na posição
                mat[p.getRow()][p.getColumn()] = true;
            }
            //Testa se tem 1 peça na Diagonal superior Direita, caso tenha ele pode capturar a peça
            p.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // verifica se existe a posição e depois verifica se não possui peça na posição
                mat[p.getRow()][p.getColumn()] = true;
            }
        } else {
            p.setValues(position.getRow() +1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // verifica se existe a posição e depois verifica se não possui peça na posição
                mat[p.getRow()][p.getColumn()] = true;
            }
            //Caso seja o 1º Movimento da peça ele pode andar 2 casas
            p.setValues(position.getRow() + 2, position.getColumn());
            Position p2 = new Position(position.getRow()  +1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
            }
//            Testa se tem 1 peça na Diagonal superior Esquerda, caso tenha ele pode capturar a peça
            p.setValues(position.getRow() + 1, position.getColumn() +1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // verifica se existe a posição e depois verifica se não possui peça na posição
                mat[p.getRow()][p.getColumn()] = true;
            }
            //Testa se tem 1 peça na Diagonal superior Direita, caso tenha ele pode capturar a peça
            p.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // verifica se existe a posição e depois verifica se não possui peça na posição
                mat[p.getRow()][p.getColumn()] = true;
            }

        }
        return mat;
    }
}