import java.util.Scanner;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private CalculadoraCliente calculadoraCliente = new CalculadoraCliente();

    public static void main(String[] args) {
        new Principal().init();
    }

    private void init() {
        String exp;
        while (true) {
            System.out.print("\n\nInforme a expressao ou <0> para sair:");
            exp = sc.next();
            if (exp.equals("0")) {
                System.exit(0);
            } else {
                System.out.println(this.calculadoraCliente.enviarExpressao(exp));
            }
        }
    }
}