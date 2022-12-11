import java.net.Socket;
import java.io.*;


class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;



    public Client(){
        try {
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("Connection done...");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

            
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void startReading() 
    {
    // Thread - Read karke deta rhega
        Runnable r1=()-> {
            System.out.println("Reader started...");
            try {
            while(true)
            {
                String msg;
                
                    msg = br.readLine();
                    if(msg.equals("quit"))
                    {
                        System.out.println("Server terminated the chat");
                        break; 
                    }
                    System.out.println("Server : "+msg);
                
              
                }
            
            } catch (IOException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                System.out.println("Connection is closed..");
            }
        };
        new Thread(r1).start();
    }

    public void startWriting()
    {
        // Thread - data user lega and the send karega client tak
        Runnable r2=()->{
            System.out.println("Writer started...");
            try 
            {
            while(!socket.isClosed())
            {
          
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content  = br1.readLine();
                 out.println(content);
                 out.flush();

          
            }
        }
         catch (Exception e) 
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        };
        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("This is client");
        new Client();
    }
}
