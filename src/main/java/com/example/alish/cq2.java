package com.example.alish;



import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class cq2 {

    public static void main(String args[]) throws IOException{

        String name = null;
        InetAddress address=InetAddress.getLocalHost();
        Socket s1=null;
        String line=null;
        BufferedReader scan=null;
        DataInputStream chitat=null;
        DataOutputStream otpravlyat=null;

        try {
            s1=new Socket(address, 4445); // You can use static final constant PORT_NUM
            scan= new BufferedReader(new InputStreamReader(System.in));
            chitat=new DataInputStream(s1.getInputStream());
            otpravlyat= new DataOutputStream(s1.getOutputStream());
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.print("IO Exception");
        }

        System.out.println("Client Address : "+address);
        System.out.println("Enter Data to echo Server ( Enter QUIT to end):");

        String response=null;
        try{
            System.out.println("Print your name:");
            //otprav name

            name = scan.readLine();
            otpravlyat.writeUTF(name);
            otpravlyat.flush();
            //jay
            int otvettest = scan.read();
            otpravlyat.writeInt(otvettest);
            otpravlyat.flush();





        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Socket read Error");
        }
        finally{

            chitat.close();otpravlyat.close();scan.close();s1.close();

            System.out.println("Connection Closed");

        }

    }
}

