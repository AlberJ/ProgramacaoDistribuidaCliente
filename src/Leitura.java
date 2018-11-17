import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Leitura extends Thread{
	private Socket socket;
	private PermiteRename rename;
	
	public Leitura (Socket s, PermiteRename r){
		this.socket = s;
		this.rename = r;
	}
	
	public void run(){
		try{
			DataInputStream in = new DataInputStream(socket.getInputStream());
			String msg = "";
			
			while (true){ // 
				msg = in.readUTF();
				
				if(msg.equals("Renomeado com sucesso.")){
//	            	COMUNICAR A THREAD PRINCIPAL Q PODE EXECUTAR A MUDANÇA
	            	rename.setTroca(true);
	            }else if(msg.equals("Nome de usuário já em uso.")){
	            	rename.setTroca(false);
	            }
				
				if(!msg.equals("")){	
					System.out.println();
					System.out.println("---------------  Mensagem recebida  ---------------");
		            System.out.println(msg);	            
		            System.out.println("---------------------------------------------------");
		            msg = "";
		            System.out.print("Escreva: ");
				}
			}
			
		}catch (IOException ex) {
            Logger.getLogger(Leitura.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
