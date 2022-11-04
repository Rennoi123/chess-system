package boardgame;

public class Board {

    private int rows;
    private  int columns;
    private Piece [][] pieces;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        // SERÁ INSTANCIADA NA QUATIDADE DE LINHAS(ROWS) INFOMRADO E NA QUANTIDADE DE COLUNAS(COLUMNS) INFORMADA
        pieces = new  Piece[rows] [rows];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }


}
