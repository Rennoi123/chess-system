package Application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    //Código especias das cores para imprimir no console
    //Cores do texto

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //Cores do fundo
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    //Utiliza O Scanner para salvar a posição que o usuario vai digitatar
    // utiliza o substring para pegar o valor de a1, pois são String e Int então precisa converter com parseInt
    public static ChessPosition readChessPosition(Scanner sc){
        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        } catch (RuntimeException e){
            throw new InputMismatchException("Erro ao ler a posição, pois no tabuleiro de xadrez so pode utilizar as posições do a1 ao h8");
        }
  }
  // Metodo que mostra as informações do tabuleiro
    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces()); // informa o tabuleiro
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Turno: "+ chessMatch.getTurn()); // informa o turno que a partida está
       if(!chessMatch.getCheckMate()) {

           System.out.println("Esperando o jogador: " + chessMatch.getCurrentPlayer() + " fazer a jogada:"); // informa que esta esperando o Jogadador fazer a jogada

           if (chessMatch.getCheck()) {
               System.out.println("CHECK!");
           }
       } else {
           System.out.println("CHECKMATE!");
           System.out.println("Vencedor: "+chessMatch.getCurrentPlayer());
       }
    }
    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void printPiece(ChessPiece piece, boolean background) { //background variavel para saber se deve ou não colorir fundo da peça
        if (background){
            System.out.print(ANSI_BLUE_BACKGROUND );
        }
        if (piece == null) {
            System.out.print("-"+ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }
    //Criando Listas a partir das peças que foram capturadas durante o jogo
    // Separando as peças de acordo com suas cores
    private static void printCapturedPieces(List<ChessPiece> captured){
        List<ChessPiece> white =captured.stream().filter(x -> x.getColor()==Color.WHITE).collect(Collectors.toList());
        List<ChessPiece> black =captured.stream().filter(x -> x.getColor()==Color.BLACK).collect(Collectors.toList());

        System.out.println("Peças Capturadas");
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(white.toArray()));
        System.out.print(ANSI_RESET);

        System.out.print("Yellow: ");
        System.out.print(ANSI_YELLOW);
        System.out.println(Arrays.toString(black.toArray()));
        System.out.print(ANSI_RESET);
    }


}
