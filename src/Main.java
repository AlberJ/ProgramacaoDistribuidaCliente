import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Main {
	
	
	public static void main(String[] args) {

		String nome;
		int porta = 6500;
		
		String msg = "";
		String linha = "";
		Scanner input = new Scanner(System.in);
			
		System.out.print("Seu user name? ");
		nome = input.next();
		menu();
		
		try{
			Socket socket = new Socket("localhost", porta);
			Leitura leitura = new Leitura(socket);
			leitura.start();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			String comandos[];
			
			System.out.print("Seu user name: ");
			nome = input.nextLine();
			out.writeUTF(nome);
			
			do{
				
//				CAPTURA MENSAGEM DO LOG E ENVIA
				System.out.print("Escreva: ");
				linha = input.nextLine();
				msg = linha;
				comandos = linha.split(" ");
				for (String c : comandos){
					c.trim();
				}
				switch (comandos[0]){
					case "bye":
						out.writeUTF(comandos[0]+":"+nome);
						break;
					case "send": 
						if(comandos[1].equals("-all")){
//							MANDAR MENSAGEM PARA TODOS
							out.writeUTF(comandos[1]+":"+nome+":"+msg);
						}else if(comandos[1].equals("-user")){
//							MANDAR MENSAGEM PARA UM USUARIO
							out.writeUTF(comandos[1]+":"+nome+":"+msg);
						}else{
							System.out.println("Comando inválido!");
						}
						break;
					case "list": 
						out.writeUTF(comandos[0]+":"+nome);
						break;
					case "rename": // ISSO AKI FICA NO LADO DO SERVIDOR E NO CLIENTE 
						out.writeUTF(comandos[0]+":"+nome);
						break;
						
					default:
						System.out.println("Comando inválido!");
				}
												
			}while(!msg.equals("bye")); // TALVEZ MUDAR PARA OBJETO SEJA MAIS INTERESSANTE
			
			socket.close();
			System.out.println("Desconectado!");
			
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	private static void menu(){
		System.out.println("----------------------  Menu  ----------------------");
		System.out.println("Sair: bye");
		System.out.println("Enviar mensagem ao grupo:  send -all sua_mensagem");
		System.out.println("Enviar mensagem reservada: send -user nome_destinatario sua_mensagem");
		System.out.println("Visualizar participantes: list");
		System.out.println("Renomear usuário: rename novo_nome");
		System.out.print("----------------------------------------------------");
		System.out.print("");
	}
	
	
}
