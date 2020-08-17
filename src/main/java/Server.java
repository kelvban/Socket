import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(2000);
        System.out.println("Ready...");
        System.out.println("Server:"+serverSocket.getInetAddress()+"port:"+serverSocket.getLocalPort());

        for(;;){
            Socket client=serverSocket.accept();
            ClientHandler clientHandler=new ClientHandler(client);
            clientHandler.start();
        }
    }

    public static class ClientHandler extends Thread{
        private Socket socket;
        private boolean flag=true;

        ClientHandler(Socket socket){
            this.socket=socket;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("New Client:"+socket.getLocalAddress()+"port:"+socket.getPort());
            try{
                PrintStream printStream=new PrintStream(socket.getOutputStream());
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                do {
                    String str=bufferedReader.readLine();
                    if("bye".equalsIgnoreCase(str)){
                        flag=false;
                        printStream.println("bye");
                    }else {
                        System.out.println(str);
                        printStream.println("Callback:"+str.length());
                    }
                }while (flag);
                printStream.close();
                bufferedReader.close();
            }catch (Exception e){
                System.out.println("Connection Error!");
            }finally {
                try{
                    socket.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println("Client exit:"+socket.getLocalAddress()+"port:"+socket.getPort());
        }
    }
}
