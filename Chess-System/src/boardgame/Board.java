package boardgame;

public class Board {
    private int rows;
    private int columns;
    private Piece[][] pieces;

    //rows quantidade de linha
    //Columns quanidade de coluna
    // o if verifica se possui linhas e colunas
    public Board(int rows, int columns) {
        if (rows <1 || columns <1 ) {
            throw new BoardException("Erro ao criar tabuleiro, é necessario existir 1 linha e 1 coluna");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
    // Se a posição não existir ele lança outra  uma BoardExcepction
    public Piece piece(int row, int column) {
        if (!positionExists(row,column))
            throw new BoardException("Não possui essa posição no tabuleiro");
        return pieces[row][column];
    }


    public Piece piece(Position position) {
        if (!positionExists(position))
        throw new BoardException("Não possui essa posição no tabuleiro");
        return pieces[position.getRow()][position.getColumn()];
    }

    //pegar a matriz na posição dada e atribuir a ela a peça informada

    public void placePiece(Piece piece, Position position){

        if(thereIsAPiece(position)){
            throw new BoardException("Já existe uma peça nessa posição: "+ position);
        }

        pieces[position.getRow()][position.getColumn()] =piece;
        piece.position= position; //Não está mais na posição nulla
    }

    //verifica se a posição existe dentro do tabuleiro
    private boolean positionExists(int row, int column){
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    //metodo retorna a posição existente da posição.get e da posição.getColumn
    public boolean positionExists (Position position){
        return positionExists(position.getRow(), position.getColumn());
    }

    //testando se possui uma peça na posição informada
    //Se a peça for != de null, existe uma peça na posição
    //Retorna o resultado
    // o if testa se a posição existe
    public boolean thereIsAPiece (Position position){
        if (!positionExists(position))
        throw new BoardException("Não possui essa posição no tabuleiro");
        return piece(position) !=null;
    }
}

