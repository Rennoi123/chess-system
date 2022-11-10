package chess.pieces;
import boardgame.Position;
import chess.Color;

import boardgame.Board;
import chess.ChessPiece;

public class Bishop extends ChessPiece {

    // quem é o tabuleiro e quem é a cor da peça
    // repassa os dados para o construtor da super classe
    public Bishop (Board board, Color color) {
        super(board, color);// repassa a chamada para super classe
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] possibleMoves() {

        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0,0);

        //Movimento na diagonal esquerda superior
        p.setValues(position.getRow() - 1, position.getColumn()-1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow()-1,p.getColumn()-1);
        }
        //Testa se tem peça adversaria no final do movimento
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        //Movimento na Diagonal Superior  Direita
        p.setValues(position.getRow()-1, position.getColumn()+1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()]= true;
            p.setValues(p.getRow()-1,p.getColumn()+1);
        }
        //Testa se tem peça adversaria no final do movimento
        if (getBoard().positionExists(p)&& isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }

        //Movimento na diagonal direita inferior
        p.setValues(position.getRow()+1, position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow()+1,p.getColumn()+1);        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        //****Movimento PARA  DIREITA ****
        // Faz a movimentação verificando as colunas, de acordo com os movientos real da torre
        //verifica se consegue andar de acordo com a coluna, no caso na diagonal

        p.setValues(position.getRow()+1,position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()]=true;
            p.setValues(p.getRow()+1,p.getColumn()-1);
        }
        //Testa se tem peça adversaria no final do movimento
        if (getBoard().positionExists(p)&& isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        return mat;
    }
}

