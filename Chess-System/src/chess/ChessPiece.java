package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

import javax.swing.*;

public abstract class ChessPiece extends Piece {

    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }
    public Color getColor() {
        return color;
    }

    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(position);

    }
    // metodo que verifica se possui uma peça adiversaria no local de destin
    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece)getBoard().piece(position); // variavel criada para receber a peça q esta nessa posição
        return  p != null && p.getColor() != color; // verifica se a variavel criada é diferente de null e se a cor da peça é diferente
    }
}
