import java.util.Random;
import java.util.Scanner;

public class GunShooting {
    static int[] gunPos = new int[]{-1,-1};
    static int[] player1 = new int[]{-1,-1};
    static int[] player2 = new int[]{-1,-1};
    static int[][] grid = new int[9][9];
    static int haveGun = 0;
    static Random random = new Random();
    public static void main(String[] args) {
//        level1();
//        level2();
//        level3();
        level4();
//        level5();
    }
    public static void level1(){
        addGun();
    }
    public static void level2(){
        addGun();
        addPlayer(1);
    }

    public static void level3(){
        addGun();
        addPlayer(1);
        move(1);
    }

    public static void level4(){
        addGun();
        addPlayer(2);
        move(2);
    }

    public static void level5(){
        addGun();
        addPlayer(2);
        move(2);
        moveThreeSteps(2);
    }
    public static void addGun(){
        System.out.println("Enter Gun position");
        int row = random.nextInt(1,10)-1;
        int col = random.nextInt(1,10)-1;
        System.out.println("Enter gun row : "+(row+1));
        System.out.println("Enter gun col : "+(col+1));
        grid[row][col] = 5;
        gunPos[0] = row;
        gunPos[1] = col;
        printGrid();
    }

    public static void printGrid(){
        System.out.println();
        for(int[] arr:grid){
            for(int val:arr){
                System.out.print(val+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void addPlayer(int number){
        int n = 1;
        while(n<number+1){
            System.out.println("Enter player"+ n +" position");
            int row = random.nextInt(1,10)-1;
            int col = random.nextInt(1,10)-1;
            System.out.println("Enter player"+n+" row : "+(row+1));
            System.out.println("Enter player"+n+" col : "+(col+1));
            if(grid[row][col] != 0){
                System.out.println("as already placed in same location. Generate new position values for\n" +
                        "player "+ n+ " :");
                continue;
            }
            grid[row][col] = n;
            if(n==1) {
                player1[0] = row;
                player1[1] = col;
            }else if(n ==2){
                player2[0] = row;
                player2[1] = col;
            }
            n++;
            printGrid();
        }
    }
    public static void move(int players){
        int direction, steps,iteration = 0,mod;
        boolean flag = true;
        Scanner input = new Scanner(System.in);
        while (true){
            mod = iteration%players +1;
            System.out.print("Enter the palyer"+mod+" direction 1(<) 2(>) 3(^) 4(v) : ");
            direction = input.nextInt();
            steps = random.nextInt(0, 4);
            System.out.println("move player"+mod+" (0-3) : " + steps);
            if(mod == 1) {
                flag = play(direction,steps,player1,1);
                if (player1[0] == gunPos[0] && player1[1] == gunPos[1]){
                    haveGun=1;
                    break;
                }
            }else{
                flag = play(direction,steps,player2,2);
                if (player2[0] == gunPos[0] && player2[1] == gunPos[1]) {
                    haveGun = 2;
                    break;
                }
            }
            printGrid();
            if(flag)
                iteration++;
        }
        System.out.println("Player"+haveGun+" Picked Gun");
        printGrid();
    }

    public static void moveThreeSteps(int players){

        int direction,number = 0,iteration = 0,mod;
        boolean flag = true;
        Scanner input = new Scanner(System.in);
        int tries = 3;
        while (tries >0){
            mod = iteration%players +1;
            System.out.println("HaveGun = player"+haveGun);
            if(haveGun == mod && (player1[0] == player2[0] || player1[1] == player2[1])){
                System.out.print("Enter the palyer"+mod+" direction 1(<) 2(>) 3(^) 4(v) / shoot[5] : ");
            }
            else
                System.out.print("Enter the palyer"+mod+" direction 1(<) 2(>) 3(^) 4(v) : ");
            direction = input.nextInt();
            if(direction != 5) {
                number = random.nextInt(0, 4);
                System.out.println("move player" + mod + " (0-3) : " + number);
            }
            if(haveGun == mod && direction == 5) {
                if(haveGun == 1)
                    grid[player2[0]][player2[1]] = 0;
                else
                    grid[player1[0]][player1[1]] = 0;
                System.out.println("Player"+haveGun+" won");
                printGrid();
                return;
            }
            if(mod == 1) {
                flag = play(direction,number,player1,1);
            }else{
                flag = play(direction,number,player2,2);
            }
            printGrid();
            if(flag) {
                if(haveGun == mod) tries--;
                iteration++;
            }
        }
        System.out.println(haveGun == 1?"Player2 Won":"Player1 Won");
        printGrid();
    }

    public static boolean play(int direction, int steps, int[] currPlayer, int player){
        int opponent = player == 1?2:1;
        if (direction == 1 && currPlayer[1] - steps >= 0) {
            System.out.println("Left");
            if(grid[currPlayer[0]][currPlayer[1] - steps] == opponent) return false;
            grid[currPlayer[0]][currPlayer[1]] = 0;
            grid[currPlayer[0]][currPlayer[1] - steps] = player;
            currPlayer[1] = currPlayer[1] - steps;
        } else if (direction == 2 && currPlayer[1] + steps < 9) {
            System.out.println("Right");
            if(grid[currPlayer[0]][currPlayer[1] + steps] == opponent) return false;
            grid[currPlayer[0]][currPlayer[1]] = 0;
            grid[currPlayer[0]][currPlayer[1] + steps] = player;
            currPlayer[1] = currPlayer[1] + steps;
        } else if (direction == 3 && currPlayer[0] - steps >= 0) {
            System.out.println("Up");
            if(grid[currPlayer[0]-steps][currPlayer[1]] == opponent) return false;
            grid[currPlayer[0]][currPlayer[1]] = 0;
            grid[currPlayer[0] - steps][currPlayer[1]] = player;
            currPlayer[0] = currPlayer[0] - steps;
        } else if (direction == 4 && currPlayer[0] + steps < 9) {
            System.out.println("Down");
            if(grid[currPlayer[0]+steps][currPlayer[1]] == opponent) return false;
            grid[currPlayer[0]][currPlayer[1]] = 0;
            grid[currPlayer[0] + steps][currPlayer[1]] = player;
            currPlayer[0] = currPlayer[0] + steps;
        }
        return true;
    }
}
