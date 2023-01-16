package br.com.alura.cliente;

import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {
    private Socket socket;

    public DistribuirTarefas(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Distribuindo tarefas para " + socket);
            Scanner entrada = new Scanner(socket.getInputStream());

            while (entrada.hasNextLine()) {
                String comando = entrada.nextLine();
                System.out.println(comando);
            }

            Thread.sleep(5000);
            System.out.println("Finalizando conexão " + socket.getPort());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
