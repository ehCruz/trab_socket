import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CalculadoraCliente {

    private Socket cliente;
    private String ipServidor;
    private int porta;

    private static final String IP_SERVIDOR = "192.168.24.236";
    private static final int PORTA_SERVIDOR = 8080;

    public CalculadoraCliente() {
        this.ipServidor = IP_SERVIDOR;
        this.porta = PORTA_SERVIDOR;
        try {
            this.cliente = new Socket(this.ipServidor, this.porta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String enviarExpressao(String exp) {
        boolean flag = exp.matches(".*[a-zA-Z]+.*");
        if (flag) {
            return "Expressao invalida, tente novamente apenas usando numeros e operadores";
        }
        return enviarConta(exp);
    }

    public void encerrarConexao() {
        try {
            this.cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String enviarConta(String valores) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(this.cliente.getOutputStream());
            outputStream.writeObject(valores);
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            try {
                Object obj = entrada.readObject();
                StringBuilder sb = new StringBuilder("Expressao: ").append(valores);
                sb.append("\nResultado: ").append((String) obj);
                return sb.toString();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
