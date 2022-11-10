package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {
    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "Q";
    }

    private boolean canMovie(Position position){
        ChessPiece p =  (ChessPiece)getBoard().piece(position);
        return  p == null || p.getColor() !=getColor();
    }
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        //****Movimento PARA CIMA **** (ABOVE)
        // position = Posição da peça que esta como atributo da peça
        //-1 esta verificando se acima dela tem como se movimentar
        //Faz a movimentação verificando as linhas, de acordo com os movientos real da torre
        //verifica se consegue andar de acordo com as linhas, (horizontal)

        p.setValues(position.getRow() - 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() - 1);
        }
        //Testa se tem peça adversaria no final do movimento
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        //****Movimento PARA  BAIXO ****(Below)
        // position = Posição da peça que esta como atributo da peça
        //+1 esta verificando se a baixo dela tem como se movimentar
        //Faz a movimentação verificando as linhas, de acordo com os movientos real da torre
        //verifica se consegue andar de acordo com as linhas,(horizontal)

        p.setValues(position.getRow()+1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()]= true;
            p.setRow(p.getRow() + 1);
        }
        //Testa se tem peça adversaria no final do movimento
        if (getBoard().positionExists(p)&& isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()]=true;
        }

        //****Movimento PARA  ESQUERDA ****
        // Faz a movimentação verificando as colunas, de acordo com os movientos real da torre
        //verifica se consegue andar de acordo com a coluna, no caso na diagonal

        p.setValues(position.getRow(), position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        //****Movimento PARA  DIREITA ****
        // Faz a movimentação verificando as colunas, de acordo com os movientos real da torre
        //verifica se consegue andar de acordo com a coluna, no caso na diagonal

        p.setValues(position.getRow(),position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()]=true;
            p.setColumn(p.getColumn() + 1);
        }
        //Testa se tem peça adversaria no final do movimento
        if (getBoard().positionExists(p)&& isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
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
    @Override
    public boolean possibleMove(Position position) {
        return super.possibleMove(position);
    }



}
