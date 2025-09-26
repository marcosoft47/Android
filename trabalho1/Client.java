import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class Client {
    private String hostname;
    private int port;
    private String username;
    private Socket socket;
    private DataOutputStream dataOut;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void execute() {
        try {
            socket = new Socket(hostname, port);
            System.out.println("Conectado ao servidor de chat.");

            new Thread(new ServerListener(socket, this)).start();
            
            dataOut = new DataOutputStream(socket.getOutputStream());
            
            Scanner scanner = new Scanner(System.in);
            String text;

            do {
                text = scanner.nextLine();
                handleUserInput(text);
            } while (!text.equals("/sair"));
            
            scanner.close();
            socket.close();

        } catch (UnknownHostException ex) {
            System.out.println("Servidor não encontrado: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro de I/O: " + ex.getMessage());
        }
    }
    
    private void handleUserInput(String input) throws IOException {
        String[] parts = input.split(" ", 4);
        String command = parts[0];

        if (command.equals("/send") && parts.length >= 4 && parts[1].equals("file")) {
            String recipient = parts[2];
            String filePath = parts[3];
            sendFile(recipient, filePath);
        } else {
            dataOut.writeUTF(input);
            dataOut.flush();
        }
    }
    
    private void sendFile(String recipient, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Erro: Arquivo não encontrado em '" + filePath + "'");
                return;
            }

            byte[] fileData = Files.readAllBytes(Paths.get(filePath));
            
            dataOut.writeUTF("/send file " + recipient + " " + file.getName());
            dataOut.writeLong(fileData.length);
            dataOut.write(fileData);
            dataOut.flush();
            System.out.println("Arquivo '" + file.getName() + "' enviado para " + recipient);

        } catch (IOException e) {
            System.err.println("Erro ao enviar o arquivo: " + e.getMessage());
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public static void main(String[] args) {
        String hostname = "localhost"; 
        int port = 12345;

        Client client = new Client(hostname, port);
        client.execute();
    }
}

class ServerListener implements Runnable {
    private Socket socket;
    private Client client;
    private DataInputStream dataIn;

    public ServerListener(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        try {
            this.dataIn = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Erro ao obter stream de entrada: " + e.getMessage());
        }
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
             while (true) {
                String serverResponse = dataIn.readUTF();
                
                if (serverResponse == null) break;

                if (serverResponse.equals("SUBMIT_USERNAME")) {
                    System.out.print("Digite seu nome de usuário: ");
                    String username = scanner.nextLine();
                    client.setUsername(username);
                    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
                    dataOut.writeUTF(username);
                    dataOut.flush();
                } else if (serverResponse.equals("USERNAME_TAKEN")) {
                    System.out.println("Nome de usuário já em uso. Tente outro.");
                } else if (serverResponse.equals("USERNAME_ACCEPTED")) {
                    System.out.println("Nome de usuário aceito. Você está online!");
                    System.out.println("Comandos disponíveis: \n /users \n /send message <destinatario> <mensagem> \n /send file <destinatario> <caminho_do_arquivo> \n /help \n /sair");
                } else if (serverResponse.startsWith("MSG:")) {
                    System.out.println(serverResponse.substring(4));
                } else if (serverResponse.startsWith("FILE:")) {
                    handleFileReception(serverResponse);
                } else {
                    System.out.println(serverResponse);
                }
            }
        } catch (EOFException e) {
            System.out.println("Conexão com o servidor perdida.");
        } catch (IOException e) {
             System.out.println("Erro ao ler do servidor: " + e.getMessage());
        }
    }
    
    private void handleFileReception(String fileCommand) throws IOException {
        String[] parts = fileCommand.split(":", 4);
        String fromUser = parts[1];
        String fileName = parts[2];
        int fileSize = Integer.parseInt(parts[3]);

        System.out.println("Recebendo arquivo '" + fileName + "' de " + fromUser + " (" + fileSize + " bytes).");
        
        byte[] fileData = new byte[fileSize];
        dataIn.readFully(fileData, 0, fileSize);
        
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(fileData);
            System.out.println("Arquivo '" + fileName + "' recebido e salvo com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo recebido: " + e.getMessage());
        }
    }
}
