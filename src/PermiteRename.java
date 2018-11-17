import java.util.logging.Level;
import java.util.logging.Logger;

public class PermiteRename {
	boolean troca = false;
	boolean mudanca = false;
	
	public synchronized void setTroca(boolean t){
		while(mudanca){
			try {
				wait();
			}catch (InterruptedException ex){
				Logger.getLogger(PermiteRename.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		this.troca = t;
		mudanca = true;
		notifyAll();
		System.out.println("Entrou na classe PermiteRename em setTroca:"+ this.troca);
	}
	
	public synchronized boolean getTroca(){
//		SE MUDANCA FOR FALSA => ESPERA
//		SE MUDANCA FOR VERDADEIRA => SAI DA ESPERA
		while(!mudanca){
			try{
				wait();
			}catch (InterruptedException ex){
				Logger.getLogger(PermiteRename.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		boolean tmp = this.troca;
		this.troca = false;
		notifyAll();
		System.out.println("Entrou na classe PermiteRename em getTroca: "+tmp);
		mudanca = false;
		return tmp;
	}
}
