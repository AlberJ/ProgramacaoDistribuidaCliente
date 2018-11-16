import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Leitura extends Thread{
	private Socket socket;
	
	public Leitura (Socket s){
		this.socket = s;
	}
	
	public void run(){
		try{
			DataInputStream in = new DataInputStream(socket.getInputStream());
			String msg = "";
			
			while (true){ // 
				msg = in.readUTF();
				
				if(!msg.equals("")){					
					System.out.println("---------------  Mensagem recebida  ---------------");
		            System.out.println(msg);	            
		            System.out.println("---------------------------------------------------");
//		            System.out.print("Escreva: ");
		            msg = "";
				}
			}
			
		}catch (IOException ex) {
            Logger.getLogger(Leitura.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
