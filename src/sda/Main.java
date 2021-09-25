package sda;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static  char charRepresentShip = 'O';
    static Scanner sc = new Scanner(System.in);
    static String ip ;

    public static void main(String[] args) throws IOException {

        System.out.println("***SHIPS***\n");
        char[][] myFleet = new char[10][10];
        char[][] enemyFleet = new char[10][10];

        String unit = "Player 2";
        boolean yourTurn = true;
        boolean isGameOver;

        System.out.println("If you wanna start game as server choose \"s\". Anything else start as client.");
        String choose = sc.next();
        Socket s;
        if (choose.equals("s")) {
            unit = "Player 1";
            yourTurn = false;
            ServerSocket ss = new ServerSocket(3333);
            s = ss.accept();
        } else {
            System.out.println("Write server ip address: ");
            ip = sc.next();
            s = new Socket(ip, 3333);
        }

        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());

        dout.writeUTF(unit + " connected");
        dout.flush();
        System.out.println(din.readUTF());


        fillMyPool(myFleet);
        fillMyPool(enemyFleet);
        showState(myFleet);

        setShips(myFleet, sc, 4);
        setShips(myFleet, sc, 3);
        setShips(myFleet, sc, 3);
        setShips(myFleet, sc, 2);
        setShips(myFleet, sc, 2);
        setShips(myFleet, sc, 2);
        setShips(myFleet, sc, 1);
        setShips(myFleet, sc, 1);
        setShips(myFleet, sc, 1);
        setShips(myFleet, sc, 1);

        refillWater(myFleet);
        showState(myFleet);
        showState(enemyFleet);

        System.out.println("Wait for opponent");
        dout.writeUTF(unit + " is ready for game");
        dout.flush();
        System.out.println(din.readUTF());

        int x , y ;
        while (true) {
            if (yourTurn) {
                System.out.println("Your turn! write column number");
                y = sc.nextInt();
                dout.writeInt(y);
                dout.flush();
                System.out.println("Your turn! write row number");
                x = sc.nextInt();
                dout.writeInt(x);
                dout.flush();
                String shot = din.readUTF();
                System.out.println(shot);
                if (shot.equals("BOOM")) {
                    enemyFleet[x - 1][y - 1] = 'X';
                    yourTurn = true;
                    String youWin = din.readUTF();
                    System.out.println(youWin);
                    if(youWin.equals("You WIN")){
                        break;
                    }
                } else {
                    enemyFleet[x - 1][y - 1] = '+';
                    yourTurn = false;
                }
                showState(myFleet);
                showState(enemyFleet);
                System.out.println(yourTurn ? "shot again" : "Enemy's turn");
            } else {
                y = din.readInt();
                System.out.println(unit + " says: " + y);
                x = din.readInt();
                System.out.println(unit + " says: " + x);
                if (myFleet[x - 1][y - 1] == 'O') {
                    myFleet[x - 1][y - 1] = 'X';
                    dout.writeUTF("BOOM");
                    dout.flush();
                    yourTurn = false;
                    isGameOver = checkIsGameOver(myFleet);
                    if (isGameOver) {
                        System.err.println("GAME OVER you loose");
                        dout.writeUTF("You WIN");
                        dout.flush();
                        break;
                    }
                    else {
                        System.out.println("Its not over");
                        dout.writeUTF("You hit ship");
                        dout.flush();
                    }
                } else {
                    myFleet[x - 1][y - 1] = '+';
                    dout.writeUTF("MISS");
                    dout.flush();
                    yourTurn = true;
                }
                showState(myFleet);
                showState(enemyFleet);
            }
        }
        dout.close();
        s.close();
        sc.close();
    }

    private static void fillMyPool(char[][] myFleet) {
        for (char[] chars : myFleet) {
            Arrays.fill(chars, '~');
        }
    }

    private static void blockAdjacentCoordinate(char[][] myFleet, int x, int y) {
        if (x != 10 && myFleet[x][y - 1] != charRepresentShip && myFleet[x][y - 1] != ' ') {
            myFleet[x][y - 1] = '.';
            if (y != 1 && myFleet[x][y - 2] != charRepresentShip && myFleet[x][y - 2] != ' ') {
                myFleet[x][y - 2] = '.';
            }
            if (y != 10 && myFleet[x][y] != charRepresentShip && myFleet[x][y] != ' ') {
                myFleet[x][y] = '.';
            }
        }
        if (x != 1 && myFleet[x - 2][y - 1] != charRepresentShip && myFleet[x - 2][y - 1] != ' ') {
            if (y != 1 && myFleet[x - 2][y - 2] != charRepresentShip && myFleet[x - 2][y - 2] != ' ') {
                myFleet[x - 2][y - 2] = '.';
            }
            if (y != 10 && myFleet[x - 2][y] != charRepresentShip && myFleet[x - 2][y] != ' ') {
                myFleet[x - 2][y] = '.';
            }
            myFleet[x - 2][y - 1] = '.';
        }
        if (y != 10 && myFleet[x - 1][y] != charRepresentShip && myFleet[x - 1][y] != ' ') {
            myFleet[x - 1][y] = '.';
        }
        if (y != 1 && myFleet[x - 1][y - 2] != charRepresentShip && myFleet[x - 1][y - 2] != ' ') {
            myFleet[x - 1][y - 2] = '.';
        }
    }

    private static void setShips(char[][] myFleet, Scanner sc, int numberOfMast) {
        System.out.println("Podaj współrzędne okrętu " + numberOfMast + " masztowego:");
        for (int i = 0; i < numberOfMast; ) {
            System.out.println("Podaj numer kolumny:");
            int y = sc.nextInt();
            System.out.println("Podaj numer wiersza:");
            int x = sc.nextInt();
            if (x < 1 || y < 1 || x > 10 || y > 10) {
                System.out.println("Nieprawidłowa wartość. Podaj współrzedne z przedziału od 1 do 10");
                continue;
            }
            if (myFleet[x - 1][y - 1] == charRepresentShip) {
                System.out.println("To pole jest zajęte! Podaj inne współrzędne.");
                continue;
            }
            if (i != 0) {

                if (((x == 10 || myFleet[x][y - 1] != charRepresentShip)
                        && (y == 10 || myFleet[x - 1][y] != charRepresentShip)
                        && (y == 1 || myFleet[x - 1][y - 2] != charRepresentShip)
                        && (x == 1 || myFleet[x - 2][y - 1] != charRepresentShip))||(myFleet[x - 1][y - 1] != '.')) {
                    System.out.println("współrzędne powinny sąsiadować");
                    continue;
                }

            } else if (myFleet[x - 1][y - 1] == ' ') {
                System.out.println("To pole jest zajęte! Podaj inne współrzędne.");
                continue;
            }

            myFleet[x - 1][y - 1] = charRepresentShip;
            blockAdjacentCoordinate(myFleet, x, y);
            showState(myFleet);
            i++;
        }
        refillSpaceAroundShip(myFleet);
        showState(myFleet);
    }

    private static void showState(char[][] myFleet) {
        System.out.print("   ");
        for (int i = 0; i < myFleet.length; i++) {

            System.out.print((i + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < myFleet.length; i++) {
            System.out.printf("%2d ", i + 1);
            for (int j = 0; j < myFleet[i].length; j++) {
                System.out.print(myFleet[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void refillWater(char[][] myFleet) {

        for (int i = 0; i < myFleet.length; i++) {
            for (int j = 0; j < myFleet[i].length; j++) {
                if (myFleet[i][j] == ' ') {
                    myFleet[i][j] = '~';
                }
            }
        }
    }

    private static void refillSpaceAroundShip(char[][] myFleet) {

        for (int i = 0; i < myFleet.length; i++) {
            for (int j = 0; j < myFleet[i].length; j++) {
                if (myFleet[i][j] == '.') {
                    myFleet[i][j] = ' ';
                }
            }
        }
    }

    private static boolean checkIsGameOver(char[][] myFleet) {

        for (char[] chars : myFleet) {
            for (char aChar : chars) {
                if (aChar == charRepresentShip) {
                    return false;
                }
            }
        }
        return true;
    }
}
