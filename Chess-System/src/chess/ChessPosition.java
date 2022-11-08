package chess;

import boardgame.Position;

public class ChessPosition {
    private char column;
    private int row;

    //Se a coluna for menor que o caracter 'a'  ou mais que o caracter 'h'  não sera aceito
    //Se a linha for menor que 1 e maior que 8, não sera aceito
    public ChessPosition(char column, int row){
        if ( column < 'a' || column >'h'| row < 'a'|| row >8)
            throw new ChessException("Erro ao instanciar ChessPosition, os valores validos são ed a1 ate h8");
        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }
    // não existe o set para que não haja alteração das linhas e nem coluna
    public int getRow() {
        return row;
    }


    protected Position toPosition (){
        return  new Position(8- row, column -'a');
    }

    protected static  ChessPosition fromPosition (Position position){
        return  new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
    }


    //Aspas dupla serve para forçar o compilador a fazer concatenação de string
    @Override
    public String toString(){
        return ""+ column + row;
    }

}
