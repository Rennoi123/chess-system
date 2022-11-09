package Application;

import boardgame.Board;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch); // imprime o tabuleiro
                System.out.println();
                System.out.println("Souce: ");
                ChessPosition source = UI.readChessPosition(sc); // digite a posição de origem

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                // Movendo a peça de sua origem para o destino informado
                System.out.println();
                System.out.println("Target: ");  // imprime a posição de destino
                ChessPosition target = UI.readChessPosition(sc); // cria variavel do tipo target que recebe a posição de destino

                ChessPiece capturedPiece = chessMatch.perfomChessMove(source, target); // movendo a peça da origem para o destino

            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();

            }


        }

    }
}