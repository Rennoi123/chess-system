package Application;

import boardgame.Board;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ChessMatch chessMatch = new ChessMatch();

        List<ChessPiece> captured = new ArrayList<>();

        while (!chessMatch.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, captured); // imprime o tabuleiro
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

               if (capturedPiece != null){
                   captured.add(capturedPiece);
               }

                if (chessMatch.getPromoted() != null) {
                    System.out.print("Digite uma peça a ser promovida (B/C/R/Q): ");
                    String type = sc.nextLine().toUpperCase();
                    while (!type.equals("B")&&!type.equals("C")&&!type.equals("R")&&!type.equals("Q")){
                        System.out.println("Digite uma peça a ser promovida (B/C/R/Q): ");
                        type = sc.nextLine().toUpperCase();
                    }
                    chessMatch.replacePromotedPiece(type); // metodo q troca o peão com outra peça que o usuario  escolher
                }

           }
            catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();

            }
        }

        UI.printMatch(chessMatch,captured);
    }
}