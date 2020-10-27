import java.util.Scanner;

public class TDMA {
    public static Scanner in = new Scanner(System.in);
    int t;
    int slot;
    int [] delay;
    TDMA(int n,int time,int s){     //n : number of channels, time : delay, s : time slice
        delay = new int[n];
        t = time;
        slot = s;
    }
    // Method to get the time delay for each channel in order to transmit it's data
    public void getData(){
        for (int x = 0;x < delay.length;x++){
            System.out.println("Enter the time delay for channel "+(x+1)+" : ");
            delay[x] = in.nextInt();
        }
    }
    //Method to check if the transmission of data from all channels is complete or not
    public boolean checkCompletion(){
        for (int i : delay){
            if (i != 0){
                return false;
            }
        }
        return true;
    }
    //Method to show the exact time slice allotted to each channel in each cycle
    public void generateResults(){
        int curr = 0;
        for (int j = 1;!this.checkCompletion();j++){
            System.out.println("Cycle "+j);
            curr = 0;
            for (int i = 0;i < delay.length;i++){
                if (delay[i] > t){
                    delay[i] = t-1;
                    System.out.println("Channel "+(i+1)+" has not been assigned any slot");
                }else if(delay[i] < t && delay[i] > 0){
                    delay[i] = 0;
                    System.out.println("Channel "+(i+1)+" has been assigned slot "+curr+" to "+(curr+slot));
                    curr += slot+1;
                }else {
                    System.out.println("Time slot "+curr+" to "+(curr+slot)+" is idle.");
                    curr += slot+1;
                }
            }
        }
    }

    public static void main(String[] args) {
        int n, time, slot;
        System.out.println("Enter the number of Channels: ");
        n = in.nextInt();
        System.out.println("Enter the delay: ");
        time = in.nextInt();
        System.out.println("Enter the time slice for each Channel: ");
        slot = in.nextInt();
        TDMA t = new TDMA(n,time,slot);
        t.getData();
        t.generateResults();
    }
}