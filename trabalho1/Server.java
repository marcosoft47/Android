import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static final int PORT = 12345;
    private static final String LOG_FILE = "server_log.txt";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    
    private Map<String, ClientHandler> connectedClients = new ConcurrentHashMap<>();

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);
            log("Servidor iniciado.");

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Novo cliente conectado: " + clientSocket.getInetAddress().getHostAddress());
                    
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                    new Thread(clientHandler).start();
                } catch (IOException e) {
                    System.err.println("Erro ao aceitar conexão de cliente: " + e.getMessage());
                    log("Erro ao aceitar conexão: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Não foi possível iniciar o servidor na porta " + PORT + ": " + e.getMessage());
            log("Falha ao iniciar o servidor: " + e.getMessage());
        }
    }

    public synchronized void addClient(String username, ClientHandler clientHandler) {
        connectedClients.put(username, clientHandler);
        String clientIp = clientHandler.getClientSocket().getInetAddress().getHostAddress();
        log("Cliente conectado: " + username + " (IP: " + clientIp + ")");
    }

    public synchronized void removeClient(String username) {
        if (username != null && connectedClients.containsKey(username)) {
            connectedClients.remove(username);
            log("Cliente desconectado: " + username);
            System.out.println("Cliente " + username + " desconectado.");
        }
    }

    public synchronized void routeMessage(String fromUser, String toUser, String message) {
        ClientHandler recipientHandler = connectedClients.get(toUser);
        if (recipientHandler != null) {
            recipientHandler.sendMessage(fromUser + ": " + message);
        } else {
            ClientHandler senderHandler = connectedClients.get(fromUser);
            if (senderHandler != null) {
                senderHandler.sendMessage("Servidor: Usuário '" + toUser + "' não encontrado ou offline.");
            }
        }
    }
    
    public synchronized void routeFile(String fromUser, String toUser, String fileName, byte[] fileData) {
        ClientHandler recipientHandler = connectedClients.get(toUser);
        if (recipientHandler != null) {
            try {
                recipientHandler.sendFile(fromUser, fileName, fileData);
            } catch (IOException e) {
                System.err.println("Erro ao enviar arquivo para " + toUser + ": " + e.getMessage());
            }
        } else {
            ClientHandler senderHandler = connectedClients.get(fromUser);
            if (senderHandler != null) {
                senderHandler.sendMessage("Servidor: Não foi possível enviar o arquivo. Usuário '" + toUser + "' não encontrado.");
            }
        }
    }

    public synchronized String getConnectedUsers() {
        if (connectedClients.isEmpty()) {
            return "Nenhum usuário conectado.";
        }
        return "Usuários conectados: " + String.join(", ", connectedClients.keySet());
    }

    public synchronized boolean isUsernameTaken(String username) {
        return connectedClients.containsKey(username);
    }
    
    private void log(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(dtf.format(LocalDateTime.now()) + " - " + message);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}