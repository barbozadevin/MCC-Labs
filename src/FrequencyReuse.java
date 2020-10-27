import java.util.Scanner;
/*
Assumptions:-
1. The number of cells is fixed.
2. Each row has 9 cells and there are 11 rows.
3. The central cell is fixed, #50
4. Users are required to provide their guess for 6 possible co-channels
5. 6 co-channels are present in 6 different directions :Top-left, Top-right, Bottom-left, Bottom-right, Left & Right
 */
public class FrequencyReuse {
    public static Scanner in = new Scanner(System.in);
    int tl, tr, bl, br, l, r, i, j, TL, TR, BL, BR, L, R, score;
    FrequencyReuse(int a,int b){
        i  = a;
        j = b;
        score = 0;
    }

    public void printInitialMatrix(){
        boolean flag;
        int count = 0;
        for (int x = 0;x < 11;x++){
            flag = (x % 2 == 0);
            for (int y = 0;y < 18;y++){
                if(flag){
                    System.out.print("   ");
                    flag = false;
                }else {
                    count++;
                    if(count < 10){
                        System.out.print("0"+count);
                        flag = true;
                    }else{
                        System.out.print(count);
                        flag = true;
                    }
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Central cell is #50");
    }

    public void getGuesses(){
        System.out.println("Enter your guesses for the co-channel cells");
        System.out.println("Top left: ");
        tl = in.nextInt();
        System.out.println("Top right: ");
        tr = in.nextInt();
        System.out.println("Bottom left: ");
        bl = in.nextInt();
        System.out.println("Bottom right: ");
        br = in.nextInt();
        System.out.println("Right: ");
        r = in.nextInt();
        System.out.println("Left: ");
        l = in.nextInt();
    }

    public void getTL(int center){
        boolean flag = true; // When this is true, subtract 10 else 9
        for(int x = 0;x < i;x++) {
            if (flag) {
                center -= 10;
                flag = false;
            } else {
                center -= 9;
                flag = true;
            }
        }
            center -= j;
        TL = center;
    }

    public void getTR(int center){
        boolean flag = true; // When this is true, subtract 9 else 8
        for(int x = 0;x < i;x++) {
            if (flag) {
                center -= 9;
                flag = false;
            } else {
                center -= 8;
                flag = true;
            }
        }
        for (int x = 0;x < j;x++){ //When flag is true, subtract 10 else 9
            if(flag){
                center -= 10;
                flag = false;
            }else {
                center -= 9;
                flag = true;
            }
        }
        TR = center;
    }

    public void getBR(int center){
        boolean flag = true; // When this is true, add 9 else 10
        for(int x = 0;x < i;x++) {
            if (flag) {
                center += 9;
                flag = false;
            } else {
                center += 10;
                flag = true;
            }
        }
            center += j;
        BR = center;
    }

    public void getBL(int center){
        boolean flag = true; // When this is true, add 8 else 9
        for(int x = 0;x < i;x++) {
            if (flag) {
                center += 8;
                flag = false;
            } else {
                center += 9;
                flag = true;
            }
        }
        for (int x = 0;x < j;x++){ // When flag is true, add 9 else 10
            if(flag){
                center += 9;
                flag = false;
            }else {
                center += 10;
                flag = true;
            }
        }
        BL = center;
    }

    public void getL(int center){
        center -= i;
        boolean flag = true; // When this is true, add 8 else 9
        for(int x = 0;x < j;x++) {
            if (flag) {
                center += 8;
                flag = false;
            } else {
                center += 9;
                flag = true;
            }
        }
        L = center;
    }

    public void getR(int center){
        center += i;
        boolean flag = true;                // When this is true, subtract 9 else 8
        for(int x = 0;x < j;x++) {
            if (flag) {
                center -= 9;
                flag = false;
            } else {
                center -= 8;
                flag = true;
            }
        }
        R = center;
    }



    public void calculate(){
        this.getTL(50);
        this.getTR(50);
        this.getBR(50);
        this.getBL(50);
        this.getL(50);
        this.getR(50);
    }

    public void checkScore(){
        if(tl == TL){
            score++;
        }else{
            System.out.println("Wrong answer for Top Left Co-channel. Your answer: "+tl+", Actual answer: "+TL);
        }
        if(tr == TR){
            score++;
        }else{
            System.out.println("Wrong answer for Top Right Co-channel. Your answer: "+tr+", Actual answer: "+TR);
        }
        if(bl == BL){
            score++;
        }else{
            System.out.println("Wrong answer for Bottom Left Co-channel. Your answer: "+bl+", Actual answer: "+BL);
        }
        if(br == BR){
            score++;
        }else{
            System.out.println("Wrong answer for Bottom Right Co-channel. Your answer: "+br+", Actual answer: "+BR);
        }
        if(r == R){
            score++;
        }else{
            System.out.println("Wrong answer for Right Co-channel. Your answer: "+r+", Actual answer: "+R);
        }
        if(l == L){
            score++;
        }else{
            System.out.println("Wrong answer for Left Co-channel. Your answer: "+l+", Actual answer: "+L);
        }
        System.out.println("Final score = "+score);
    }

    public static void main(String[] args) {
        System.out.println("Enter the value for i: ");
        int i = in.nextInt();
        System.out.println("Enter the value for j: ");
        int j = in.nextInt();
        FrequencyReuse fr = new FrequencyReuse(i,j);
        fr.printInitialMatrix();
        fr.calculate();
        fr.getGuesses();
        fr.checkScore();
    }
}