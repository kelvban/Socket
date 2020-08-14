import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket();
        socket.setSoTimeout(3000);
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(),2000),3000);
         System.out.println("Connecting...");
         System.out.println("Client:"+socket.getLocalAddress()+"port:"+socket.getLocalPort());
         System.out.println("Server:"+socket.getInetAddress()+"port:"+socket.getPort());
         try{
             todo(socket);
         }catch (Exception e){
             System.out.println("Unexceptional Error");
         }
         socket.close();
         System.out.println("Client exit...");
    }
    private static void todo(Socket client)throws IOException{
        InputStream in=System.in;
        BufferedReader input=new BufferedReader(new InputStreamReader(in));


        OutputStream outputStream=client.getOutputStream();
        PrintStream printStream=new PrintStream(outputStream);

        InputStream inputStream=client.getInputStream();
        BufferedReader socketReader=new BufferedReader(new InputStreamReader(inputStream));

        boolean flag=true;

        do{
            String str=input.readLine();
            printStream.println(str);

            String echo=socketReader.readLine();
            if("bye".equalsIgnoreCase(echo)){
                flag=false;
            }else {
                System.out.println(echo);
            }
        }while (flag);
        printStream.close();
        socketReader.close();
    }
}
