import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraServidor {

    private CalculadoraRPN calculadoraRPN = new CalculadoraRPN();

    public static void main(String[] args) {
        new CalculadoraServidor().iniciarServidor();
    }

    private void iniciarServidor() {
        try {
            ServerSocket servidor = new ServerSocket(5000);
            System.out.println("Servidor iniciado na porta 5000");
            Socket cliente = servidor.accept();
            System.out.printf("Cliente Conectado\nIp cliente: %s\n", cliente.getInetAddress().getHostAddress());

            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(cliente.getOutputStream());
            try {
                Object obj = entrada.readObject();
                System.out.println("Dados recebidos");
                outputStream.writeObject(this.calculadoraRPN.separaPilhas((String) obj));
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
            servidor.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}