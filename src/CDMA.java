import java.util.Scanner;

class walsh {

    //Creating Walsh matrix
    public void fillHadamard (int[][] mat){

        fillHadamard(mat, 0,0,mat.length, 1);
    }
    private void fillHadamard (int [][] mat, int top, int left, int size, int sign){
        if (size == 1)
            mat[top][left] = sign;
        else
        {
            fillHadamard (mat, top, left, size/2, sign);
            fillHadamard (mat, top+size/2, left, size/2, sign);
            fillHadamard (mat, top, left+size/2, size/2, sign);
            fillHadamard (mat, top+size/2, left+size/2, size/2, (-1)*sign);
        }
    }
    // This method checks Orthogonality of the generated Walsh Matrix
    public void verifyOrthogonality(int [][] mat){
        int val = 0;
        for(int i = 0;i < mat.length;i++){
            int k = 0;
            while(k < mat.length){
                if(i == k){
                    k++;
                    continue;
                }
                for (int j = 0;j < mat.length;j++){
                    val += mat[i][j] * mat[k][j];
                }
                if(val != 0){
                    System.out.println("Matrix is not orthogonal.");
                    return;
                }
                k++;
            }
        }
        System.out.println("Matrix is Orthogonal!");
    }
}
class code extends walsh{
    int[][] w, ae;          //Walsh Hadamard Matrix and Individual codes before spreading
    int[] encodedData, userMessage;      //After spreading
    int n;                  //Number of users
    int [] res;         // Array containing Final decoded messages

    code(int n){
        this.n = n;
        this.w = new int[n][n];
        this.ae = new int[n][n];
        this.encodedData = new int[n];
        this.userMessage = new int[n];
        this.res = new int[n];
        super.fillHadamard(w);
        this.verifyOrthogonality(w);
    }
    // Method to decode the data sent by users
    void decode(){
        for(int i=0; i<ae.length;i++){
            int temp=0;
            boolean flag=true;
            for(int j=0; j<ae.length;j++){
                if(ae[i][j]==-2){
                    flag = false;
                    break;
                }
                temp += encodedData[j]*w[i][j];
            }
            if(flag){
                if(temp/ae.length==1){
                    //System.out.println("1");
                    res[i] = 1;
                }
                else{
                    res[i] = 0;
                }
            }
            else{
                res[i] = -1;
            }
        }
    }
    // Method to return data sent by specified user
    void getMessage(int n){
        System.out.print("Message from User "+n+" is: ");
        if (res[n-1] == 1 || res[n-1] == 0){
            System.out.println(res[n-1]);
        }else {
            System.out.println("Idle");
        }
    }

    // Calculation of Spread data for each user
    void calculation(){
        System.out.println("The message after superimposing:  ");
        for(int i=0;i < ae.length; i++){
            int res=0;

            for(int j=0;j<ae.length;j++){
                if(ae[j][i]==-2)
                    continue;
                else{
                    res+=ae[j][i];
                }
            }
            encodedData[i]=res;
            System.out.print(encodedData[i]+"   ");
        }
        System.out.println();
    }
    // Method to take inputs from users
    void dataEntry(){
        fillHadamard(w);
        Scanner sc = new Scanner(System.in);
        userMessage = new int[n];
        for(int i=0;i<n;i++){
            System.out.print("Message from User "+(i+1)+",-1 if user is idle : ");
            userMessage[i]=sc.nextInt();
            if(userMessage[i]==-1){
                userMessage[i]=-2;
            }
            else{
                if(userMessage[i]==1){
                    continue;
                }
                else if(userMessage[i]==0){
                    userMessage[i]=-1;
                }
                else{
                    System.out.println("Entered number is invalid");
                    System.exit(0);
                }
            }
        }
    }
    // Multiplying user data by Chipping code
    void encode(){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(userMessage[i]==-2){
                    ae[i][j]=-2;
                }
                else{
                    ae[i][j]=w[i][j]*userMessage[i];
                }
            }
        }
    }

}

public class CDMA {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args){

        System.out.print("Enter Number of Users: ");
        int n1, u;
        n1 = sc.nextInt();
        int n = power(n1);
        code c = new code(n);
        c.dataEntry();
        c.encode();
        System.out.println("Enter the User whose message is to be retrieved: ");
        u = sc.nextInt();
        c.calculation();
        c.decode();
        c.getMessage(u);
    }
    private static int power(int n){
        int count = 0;
        if (n > 0 && (n & (n - 1)) == 0)
            return n;

        while(n != 0)
        {
            n >>= 1;
            count += 1;
        }

        return 1 << count;
    }
}

