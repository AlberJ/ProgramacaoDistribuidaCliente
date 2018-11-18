import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {

	private static Scanner input;

	public static void main(String[] args) {

		input = new Scanner(System.in);
		PermiteRename rename = new PermiteRename();
		int porta = 6500;
		String nome;
		String msg = "";
		String linha = "";

		try {
			Socket socket = new Socket("localhost", porta);
			Leitura leitura = new Leitura(socket, rename);
			leitura.start();

			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			String comandos[];
			
			
			System.out.print("Seu user name: ");
			nome = input.nextLine();
			out.writeUTF(nome);
			menu();

			do {
				// CAPTURA MENSAGEM DO LOG E ENVIA
				System.out.print("Escreva: ");
				linha = input.nextLine();
				msg = linha;
				comandos = linha.split(" ");
				for (String c : comandos) {
					c.trim();
				}
				
				switch (comandos[0]) {
				case "bye":
					out.writeUTF(comandos[0] + ":" + nome);
					break;

				case "send":
					msg = "";
					if (comandos[1].equals("-all")) {
						for (int i = 2; i < comandos.length; i++){
							msg += comandos[i]+" ";
						}
						out.writeUTF(comandos[1] + ":" + nome + ":" + msg);

					} else if (comandos[1].equals("-user")) {
						// MANDAR MENSAGEM PARA UM USUARIO
						for (int i = 3; i < comandos.length; i++){
							msg += comandos[i]+" ";
						}
						out.writeUTF(comandos[1]+":"+nome+":"+comandos[2]+":"+msg);

					} else {
						System.out.println("Comando inválido!");
					}

					break;

				case "list":
					out.writeUTF(comandos[0] + ":" + nome);
					break;

				case "rename":
					out.writeUTF(comandos[0] + ":" + comandos[1] + ":" + nome);
					String nometmp = comandos[1];
					// RECEBE A CONFIRMAÇÃO DA THREAD DE LEITURA E EXECUTAR A
					// MUDANÇA
					if (rename.getTroca()) {
						nome = nometmp;
					} else {
						// DESCARTA CASO NÃO NAO CONFIRME
						nometmp = null;
					}

					break;

				default:
					System.out.println("Comando inválido!");
				}

			} while (!msg.equals("bye"));

			socket.close();
			System.out.println("Desconectado!");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void menu() {
		System.out.println("----------------------  Menu  ----------------------");
		System.out.println("Sair: bye");
		System.out.println("Enviar mensagem ao grupo:  send -all sua_mensagem");
		System.out.println("Enviar mensagem reservada: send -user nome_destinatario sua_mensagem");
		System.out.println("Visualizar participantes: list");
		System.out.println("Renomear usuário: rename novo_nome");
		System.out.println("----------------------------------------------------");
	}
}
