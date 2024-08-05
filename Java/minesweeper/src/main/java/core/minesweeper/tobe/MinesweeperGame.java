package core.minesweeper.tobe;

import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    private static String[][] board = new String[8][10];
    private static Integer[][] landMineCounts = new Integer[8][10];
    private static boolean[][] landMines = new boolean[8][10];
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {
        showGameStartComments();
        Scanner scanner = new Scanner(System.in);
        initializeGame();

        while (true) {
            showBoard();
            if (gameStatus == 1) {
                System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                break;
            }
            if (gameStatus == -1) {
                System.out.println("지뢰를 밟았습니다. GAME OVER!");
                break;
            }
            System.out.println("선택할 좌표를 입력하세요. (예: a1)");
            String cellInput = scanner.nextLine();
            System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
            String userAction = scanner.nextLine();

            char cellInputCol = cellInput.charAt(0);
            char cellInputRow = cellInput.charAt(1);

            int selectedColIndex = convertColFrom(cellInputCol);
            int selectedRowIndex = convertRowFrom(cellInputRow);

            if (userAction.equals("2")) {
                board[selectedRowIndex][selectedColIndex] = "⚑";
                checkIfGameIsOver();
            } else if (userAction.equals("1")) {
                if (landMines[selectedRowIndex][selectedColIndex]) {
                    board[selectedRowIndex][selectedColIndex] = "☼";
                    gameStatus = -1;
                    continue;
                } else {
                    open(selectedRowIndex, selectedColIndex);
                }
                checkIfGameIsOver();
            } else {
                System.out.println("잘못된 번호를 선택하셨습니다.");
            }
        }
    }

    private static void checkIfGameIsOver() {
        boolean isAllOpen = isAllCellOpened();
        if (isAllOpen) {
            gameStatus = 1;
        }
    }

    private static boolean isAllCellOpened() {
        boolean isAllOpen = true;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                if (board[row][col].equals("□")) {
                    isAllOpen = false;
                }
            }
        }
        return isAllOpen;
    }

    private static int convertRowFrom(char cellInputRow) {
        return Character.getNumericValue(cellInputRow) - 1;
    }

    private static int convertColFrom(char cellInputCol) {
        return switch (cellInputCol) {
            case 'a' -> 0;
            case 'b' -> 1;
            case 'c' -> 2;
            case 'd' -> 3;
            case 'e' -> 4;
            case 'f' -> 5;
            case 'g' -> 6;
            case 'h' -> 7;
            case 'i' -> 8;
            case 'j' -> 9;
            default -> -1;
        };
    }

    private static void showBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < 8; row++) {
            System.out.printf("%d  ", row + 1);
            for (int col = 0; col < 10; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void initializeGame() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                board[row][col] = "□";
            }
        }
        for (int i = 0; i < 10; i++) {
            int col = new Random().nextInt(10);
            int row = new Random().nextInt(8);
            landMines[row][col] = true;
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                int count = 0;
                if (!landMines[row][col]) {
                    if (row - 1 >= 0 && col - 1 >= 0 && landMines[row - 1][col - 1]) {
                        count++;
                    }
                    if (row - 1 >= 0 && landMines[row - 1][col]) {
                        count++;
                    }
                    if (row - 1 >= 0 && col + 1 < 10 && landMines[row - 1][col + 1]) {
                        count++;
                    }
                    if (col - 1 >= 0 && landMines[row][col - 1]) {
                        count++;
                    }
                    if (col + 1 < 10 && landMines[row][col + 1]) {
                        count++;
                    }
                    if (row + 1 < 8 && col - 1 >= 0 && landMines[row + 1][col - 1]) {
                        count++;
                    }
                    if (row + 1 < 8 && landMines[row + 1][col]) {
                        count++;
                    }
                    if (row + 1 < 8 && col + 1 < 10 && landMines[row + 1][col + 1]) {
                        count++;
                    }
                    landMineCounts[row][col] = count;
                    continue;
                }
                landMineCounts[row][col] = 0;
            }
        }
    }

    private static void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void open(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 10) {
            return;
        }
        if (!board[row][col].equals("□")) {
            return;
        }
        if (landMines[row][col]) {
            return;
        }
        if (landMineCounts[row][col] != 0) {
            board[row][col] = String.valueOf(landMineCounts[row][col]);
            return;
        } else {
            board[row][col] = "■";
        }
        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}
