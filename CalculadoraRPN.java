import java.util.ArrayList;

public class CalculadoraRPN {
    private ArrayList<String> operadores = new ArrayList<>();
    private ArrayList<String> output = new ArrayList<>();

    public String separaPilhas(String exp) {
        String[] splitExp = exp.split("(?=[-+*/()])|(?<=[^-+*/][-+*/])|(?<=[()])");
        this.operadores.clear();
        this.output.clear();
        this.operadores.add(" ");
        for (int i = 0; i < splitExp.length; i++) {
            if (splitExp[i].matches("[\\+\\-\\*\\/\\(\\)]")) {
                this.verificarPilhaOperacao(splitExp[i]);
            } else {
                this.output.add(splitExp[i]);
            }
        }
        for (int i = this.operadores.size(); i >= 1; i--) {
            this.output.add(this.operadores.remove(i - 1));
        }
        return this.calcExpressao();
    }

    private void verificarPilhaOperacao(String aux) {
        if (!this.operadores.isEmpty()) {
            if (aux.equals("+") || aux.equals("-")) {
                if (!this.operadores.contains("+") || !this.operadores.contains("-") || !this.operadores.contains("*")
                        || !this.operadores.contains("/") || !this.operadores.contains("^")) {
                    while ((this.operadores.get(this.operadores.size() - 1).equals("-")
                            || this.operadores.get(this.operadores.size() - 1).equals("+")
                            || this.operadores.get(this.operadores.size() - 1).equals("*")
                            || this.operadores.get(this.operadores.size() - 1).equals("/")
                            || this.operadores.get(this.operadores.size() - 1).equals("^"))
                            && !this.operadores.get(this.operadores.size() - 1).equals("(")) {
                        this.output.add(this.operadores.remove(this.operadores.size() - 1));
                    }
                }
                this.operadores.add(aux);
            } else if (aux.equals("*") || aux.equals("/")) {
                if (!this.operadores.contains("*") || !this.operadores.contains("/")
                        || !this.operadores.contains("^")) {
                    while ((this.operadores.get(this.operadores.size() - 1).equals("*")
                            || this.operadores.get(this.operadores.size() - 1).equals("/")
                            || this.operadores.get(this.operadores.size() - 1).equals("^"))
                            && !this.operadores.get(this.operadores.size() - 1).equals("(")) {
                        this.output.add(this.operadores.remove(this.operadores.size() - 1));
                    }
                }
                this.operadores.add(aux);
            } else if (aux.equals("^")) {
                if (!this.operadores.contains("^")) {
                    while ((this.operadores.get(this.operadores.size() - 1).equals("^"))
                            && !this.operadores.get(this.operadores.size() - 1).equals("(")) {
                        this.output.add(this.operadores.remove(this.operadores.size() - 1));
                    }
                }
                this.operadores.add(aux);
            } else if (aux.equals(")")) {
                while (!this.operadores.get(this.operadores.size() - 1).equals("(")) {
                    this.output.add(this.operadores.remove(this.operadores.size() - 1));
                }
                this.operadores.remove(this.operadores.size() - 1);
            } else {
                this.operadores.add(aux);
            }
        }
    }

    private String calcExpressao() {
        ArrayList<String> sumAux = new ArrayList<>();
        Integer soma = 0;
        int op1, op2;
        for (String elemento : output) {
            switch (elemento) {
            case "+":
                soma = Integer.parseInt(sumAux.remove(sumAux.size() - 1))
                        + Integer.parseInt(sumAux.remove(sumAux.size() - 1));
                sumAux.add(Integer.toString(soma));
                break;
            case "-":
                op2 = Integer.parseInt(sumAux.remove(sumAux.size() - 1));
                op1 = Integer.parseInt(sumAux.remove(sumAux.size() - 1));
                soma = op1 - op2;
                sumAux.add(Integer.toString(soma));
                break;
            case "*":
                soma = Integer.parseInt(sumAux.remove(sumAux.size() - 1))
                        * Integer.parseInt(sumAux.remove(sumAux.size() - 1));
                sumAux.add(Integer.toString(soma));
                break;
            case "/":
                op2 = Integer.parseInt(sumAux.remove(sumAux.size() - 1));
                op1 = Integer.parseInt(sumAux.remove(sumAux.size() - 1));
                soma = op1 / op2;
                sumAux.add(Integer.toString(soma));
                break;
            case "^":
                soma = Integer.parseInt(sumAux.remove(sumAux.size() - 1))
                        ^ Integer.parseInt(sumAux.remove(sumAux.size() - 1));
                break;
            default:
                sumAux.add(elemento);
                break;
            }
        }
        System.out.print("Postfix:");
        for (String el : output) {
            System.out.print(" " + el);
        }
        System.out.println("\nCalculo realizado, retornando resultado ao cliente");
        return sumAux.get(0).toString();
    }
}