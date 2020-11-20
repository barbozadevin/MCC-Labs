import java.util.Scanner;
import java.util.Arrays;
import java.util.stream.Collectors;

public class A5 {
    String pt;                  // Plain Text
    int [] binaryPT;            // Plain Text converted to Binary
    int [] cipherKey;           // Cipher Key Entered by User
    int [] keyStream;           // Key Stream Generated using Cipher Key
    int [] encrypted;           // Encrypted Plain Text
    int [] x;                   // Register X
    int [] y;                   // Register Y
    int [] z;                   // Register Z
    String binaryDT;            // Binary Decrypted Text
    String dt;                  // Decrypted Text
    public static Scanner in = new Scanner(System.in);

    A5(){
        cipherKey = new int[64];
        x = new int[19];
        y = new int[22];
        z = new int[23];
    }

    // Function to accept cipher key from user
    public void getCipherKey(){
        System.out.println("Enter your Cipher Key");
        for (int i = 0;i < 64;i++){
            cipherKey[i] = in.nextInt();
        }
    }

    // Function to convert plain text to binary
    public void convertStringToBinary() {
        String input;
        System.out.println("Enter your Plaintext: ");
        input = in.nextLine();
        pt = input;

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar)).replaceAll(" ", "0")
            );
        }
        binaryPT = new int[result.length()];
        for (int i = 0;i < result.length();i++){
            binaryPT[i] = Character.getNumericValue(result.charAt(i));
        }
        keyStream = new int[binaryPT.length];
        encrypted = new int[binaryPT.length];
    }

    // Function to perform Set operations on Register X
    public void shiftX(int bit){
        for (int i = 18;i > 0;i--){
            x[i] = x[i-1];
        }
        x[0] = bit;
    }

    // Function to perform Set operations on Register Y
    public void shiftY(int bit){
        for (int i = 21;i > 0;i--){
            y[i] = y[i-1];
        }
        y[0] = bit;
    }

    // Function to perform Set operations on Register Z
    public void shiftZ(int bit){
        for (int i = 22;i > 0;i--){
            z[i] = z[i-1];
        }
        z[0] = bit;
    }

    // Function to initially fill registers X, Y & Z
    public void fillXYZ(){
        int bitX, bitY, bitZ;
        for(int i=0;i < 64;i++){
            int c = cipherKey[i];
            bitX = c^x[13]^x[16]^x[17]^x[18];
            this.shiftX(bitX);
            bitY = c^y[20]^y[21];
            this.shiftY(bitY);
            bitZ = c^z[7]^z[20]^z[21]^z[22];
            this.shiftZ(bitZ);
        }
    }

    // Function to find the Majority Bit
    public int majorityFunction(int x,int y,int z){
        int co = 0, cz = 0;
        if(x == 0)
            cz++;
        else
            co++;
        if(y == 0)
            cz++;
        else
            co++;
        if(z == 0)
            cz++;
        else
            co++;

        if (cz > co)
            return 0;
        return 1;
    }

    // Function to Encrypt the text
    public void encrypt(){
        int n = binaryPT.length;
        int temp, max, key;
        for (int i = 0;i < n;i++){
            max = majorityFunction(x[8],y[10],z[10]);

            if(x[8] == max){
                temp = x[13]^x[16]^x[17]^x[18];
                this.shiftX(temp);
            }
            if(y[10] == max){
                temp = y[20]^y[21];
                this.shiftY(temp);
            }
            if(z[10] == max){
                temp = z[7]^z[20]^z[21]^z[22];
                this.shiftZ(temp);
            }
            key = x[18]^y[21]^z[22];
            keyStream[i] = key;
            encrypted[i] = key^binaryPT[i];
        }
    }

    // Function to Decrypt the text
    public void decrypt(){
        binaryDT = "";
        for(int i = 0;i < keyStream.length;i++){
            if(i % 8 == 0 && i != 0){
                binaryDT += " ";
            }
            binaryDT += (encrypted[i] ^ keyStream[i]);
        }

        dt = Arrays.stream(binaryDT.split(" "))
                .map(binary -> Integer.parseInt(binary, 2))
                .map(Character::toString)
                .collect(Collectors.joining());
    }

    // Function to Fetch results
    public void getResults(){
        System.out.println("Plain Text: "+pt);
        System.out.print("Key Stream: ");
        for(int i : keyStream){
            System.out.print(keyStream[i]);
        }
        System.out.println();
        System.out.println("Encrypted text: ");
        for(int i : encrypted){
            System.out.print(i);
        }
        System.out.println();
        System.out.println("Decrypted text: "+dt);
    }

    public static void main(String[] args) {
        A5 a5 = new A5();
        a5.convertStringToBinary();
        a5.getCipherKey();
        a5.fillXYZ();
        a5.encrypt();
        a5.decrypt();
        a5.getResults();
    }
}
