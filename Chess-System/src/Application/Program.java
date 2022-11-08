package Application;

import boardgame.Board;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ChessMatch chessMatch = new ChessMatch();

        while (true){


            UI.printBoard(chessMatch.getPieces()); // imprime o tabuleiro
            System.out.println();
            System.out.println("Souce: ");
            ChessPosition source = UI.readChessPosition(sc); // digite a posição de origem

            // Movendo a peça de sua origem para o destino informado
            System.out.println();
            System.out.println("Target: ");  // imprime a posição de destino
            ChessPosition target = UI.readChessPosition(sc); // cria variavel do tipo target que recebe a posição de destino
            ChessPiece capturedPiece = chessMatch.perfomChessMove(source,target); // movendo a peça da origem para o destino


        }



    }
}