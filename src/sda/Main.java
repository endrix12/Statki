package sda;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        System.out.println("***STATKI***\n");
        char[][] mojePole = new char[10][10];
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < mojePole.length; i++) {
            for (int j = 0; j < mojePole[i].length; j++) {
                mojePole[i][j] = '~';
            }

        }
        showState(mojePole);


        //ustawianie jednomasztowców
        for (int i = 0; i < 4; i++) {
            System.out.println("Podaj numer wiersza:");
            int x = sc.nextInt();
            System.out.println("Podaj numer kolumny:");
            int y = sc.nextInt();
            mojePole[x-1][y-1] = 'O';
            showState(mojePole);


        }


        showState(mojePole);

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
    }
}
