package sda;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        System.out.println("***STATKI***\n");
        char[][] mojePole = new char[10][10];
        Scanner sc = new Scanner(System.in);

        fillMyPool(mojePole);
        showState(mojePole);
        setFourMastShips(mojePole, sc);
        setOneMastShips(mojePole, sc);
        showState(mojePole);

    }

    private static void fillMyPool(char[][] mojePole) {
        for (int i = 0; i < mojePole.length; i++) {
            for (int j = 0; j < mojePole[i].length; j++) {
                mojePole[i][j] = '~';
            }
        }
    }

    //ustawianie jednomasztowców
    private static void setOneMastShips(char[][] mojePole, Scanner sc) {
        System.out.println("Podaj współrzędne czterech okrętów jednomasztowych:");
        for (int i = 0; i < 4; ) {
            System.out.println("Podaj numer wiersza:");
            int x = sc.nextInt();
            System.out.println("Podaj numer kolumny:");
            int y = sc.nextInt();
            if (x < 1 || y < 1 || x > 10 || y > 10) {
                System.out.println("Nieprawidłowa wartość. Podaj współrzedne z przedziału od 1 do 10");
                continue;
            }
            if (mojePole[x - 1][y - 1] == 'O' || mojePole[x - 1][y - 1] == ' ') {
                System.out.println("To pole jest zajęte! Podaj inne współrzędne.");
                continue;
            }
            mojePole[x - 1][y - 1] = 'O';
            blockAdjacentCoordinate(mojePole, x, y);

            showState(mojePole);
            i++;


        }
    }

    private static void blockAdjacentCoordinate(char[][] mojePole, int x, int y) {
        if (x != 10 && mojePole[x][y - 1] != 'O') {
            mojePole[x][y - 1] = ' ';
            if (y != 1 && mojePole[x][y - 2] != 'O') {
                mojePole[x][y - 2] = ' ';
            }
            if (y != 10 && mojePole[x][y] != 'O') {
                mojePole[x][y] = ' ';
            }
        }
        if (x != 1 && mojePole[x - 2][y - 1] != 'O') {
            if (y != 1 && mojePole[x - 2][y - 2] != 'O') {
                mojePole[x - 2][y - 2] = ' ';
            }
            if (y != 10 && mojePole[x - 2][y] != 'O') {
                mojePole[x - 2][y] = ' ';
            }
            mojePole[x - 2][y - 1] = ' ';
        }
        if (y != 10 && mojePole[x - 1][y] != 'O') {
            mojePole[x - 1][y] = ' ';
        }
        if (y != 1 && mojePole[x - 1][y - 2] != 'O') {
            mojePole[x - 1][y - 2] = ' ';
        }
    }

    //ustawianie czteromasztowca
    private static void setFourMastShips(char[][] mojePole, Scanner sc) {
        System.out.println("Podaj współrzędne okrętu czteromasztowego:");
        for (int i = 0; i < 4; ) {
            System.out.println("Podaj numer wiersza:");
            int x = sc.nextInt();
            System.out.println("Podaj numer kolumny:");
            int y = sc.nextInt();
            if (x < 1 || y < 1 || x > 10 || y > 10) {
                System.out.println("Nieprawidłowa wartość. Podaj współrzedne z przedziału od 1 do 10");
                continue;
            }
            if (mojePole[x - 1][y - 1] == 'O') {
                System.out.println("To pole jest zajęte! Podaj inne współrzędne.");
                continue;
            }
            if (i != 0) {

                if ((x == 10 || mojePole[x][y - 1] != 'O') && (y == 10 || mojePole[x - 1][y] != 'O') && (y == 1 || mojePole[x - 1][y - 2] != 'O') && (x == 1 || mojePole[x - 2][y - 1] != 'O')) {
                    System.out.println("współrzędne powinny sąsiadować");
                    continue;
                }

            }
            mojePole[x - 1][y - 1] = 'O';
            blockAdjacentCoordinate(mojePole, x, y);
            showState(mojePole);
            i++;


        }
    }

    private static void showState(char[][] mojePole) {
        System.out.print("   ");
        for (int i = 0; i < mojePole.length; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();

        for (int i = 0; i < mojePole.length; i++) {
            System.out.printf("%2d ", i + 1);
            for (int j = 0; j < mojePole[i].length; j++) {
                System.out.print(mojePole[i][j] + " ");
            }
            System.out.println();

        }
        System.out.println();
    }
}
