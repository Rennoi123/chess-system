package chess.pieces;
import chess.Color;

import boardgame.Board;
import chess.ChessPiece;

public class Rook extends ChessPiece {

    // quem é o tabuleiro e quem é a cor da peça
    // repassa os dados para o construtor da super classe
    public Rook(Board board, Color color) {
        super(board, color);// repassa a chamada para super classe
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return mat;
       }
    }

