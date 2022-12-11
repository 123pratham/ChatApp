import java.net.*;
import java.io.*;
class Server{
    
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Server()
    {
        try 
        {
            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept connection....");
            System.out.println("Waiting...");
            socket = server.accept();
            
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } 
        catch (IOException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void startReading() 
    {
    // Thread - Read karke deta rhega
        Runnable r1=()-> {
            System.out.println("Reader started...");
try{
            while(true)
            {
                String msg;
                
                    msg = br.readLine();
                    if(msg.equals("Exit"))
                    {
                        System.out.println("Client terminated the chat");

                        socket.close();

                        break; 
                    }
                    System.out.println("Client: "+msg);
                } 
            } 
            catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
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
            while(true)
            {
            
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content  = br1.readLine();
               
                 out.println(content);
                 out.flush();

                 if(content.equals("Exit"))
                 {
                     socket.close();
                     break;
                 }

            
            }
           
        }
        catch (IOException e) 
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        };
        new Thread(r2).start();

    }


    public static void main(String[] args) {
        System.out.println("Going to start server...");
        new Server();
    }
}