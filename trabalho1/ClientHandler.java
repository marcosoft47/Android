import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private Server server;
    private String username;
    private DataOutputStream dataOut;
    private DataInputStream dataIn;

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            dataIn = new DataInputStream(clientSocket.getInputStream());
            dataOut = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                dataOut.writeUTF("SUBMIT_USERNAME");
                this.username = dataIn.readUTF();
                if (username == null || username.trim().isEmpty() || server.isUsernameTaken(username)) {
                    dataOut.writeUTF("USERNAME_TAKEN");
                } else {
                    dataOut.writeUTF("USERNAME_ACCEPTED");
                    server.addClient(this.username, this);
                    break;
                }
            }
            
            String command;
            while ((command = dataIn.readUTF()) != null) {
                handleCommand(command);
            }

        } catch (SocketException | EOFException e) {
            System.out.println("Cliente " + (username != null ? username : "") + " desconectou-se.");
        } catch (IOException e) {
            System.err.println("Erro no handler do cliente " + (username != null ? username : "") + ": " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    private void handleCommand(String command) throws IOException {
        String[] parts = command.split(" ", 4);
        String action = parts[0];

        switch (action) {
            case "/users":
                sendMessage(server.getConnectedUsers());
                break;
            case "/send":
                if (parts.length >= 4 && parts[1].equals("message")) {
                    String recipient = parts[2];
                    String message = parts[3];
                    server.routeMessage(this.username, recipient, message);
                } else if (parts.length >= 4 && parts[1].equals("file")) {
                    String recipient = parts[2];
                    String fileName = parts[3];
                    
                    long fileSize = dataIn.readLong();
                    byte[] fileData = new byte[(int) fileSize];
                    dataIn.readFully(fileData, 0, (int) fileSize);
                    
                    server.routeFile(this.username, recipient, fileName, fileData);
                } else {
                    sendMessage("Servidor: Comando '/send' inválido. Use '/send message <dest> <msg>' ou '/send file <dest> <path>'.");
                }
                break;
            case "/sair":
                break;
            default:
                sendMessage("Servidor: Comando desconhecido '" + action + "'");
                break;
        }
    }

    public void sendMessage(String message) {
        try {
            dataOut.writeUTF("MSG:" + message);
            dataOut.flush();
        } catch (IOException e) {
            System.err.println("Erro ao enviar mensagem para " + username + ": " + e.getMessage());
        }
    }
    
    public void sendFile(String fromUser, String fileName, byte[] fileData) throws IOException {
        dataOut.writeUTF("FILE:" + fromUser + ":" + fileName + ":" + fileData.length);
        dataOut.write(fileData);
        dataOut.flush();
    }
    
    private void cleanup() {
        server.removeClient(this.username);
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Erro ao fechar o socket do cliente: " + e.getMessage());
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}