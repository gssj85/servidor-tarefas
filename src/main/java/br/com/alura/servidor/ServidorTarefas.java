package br.com.alura.servidor;

import br.com.alura.cliente.DistribuirTarefas;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class ServidorTarefas {
    public static void main(String[] args) throws Exception {
        System.out.println("--- Iniciando servidor ---");
        ServerSocket servidor = new ServerSocket(12345);

        while (true) {
            Socket socket = servidor.accept();
            int portaDoCliente = socket.getPort();
            System.out.println("Aceitando novo cliente " + portaDoCliente);

            DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
            Thread threadCliente = new Thread(distribuirTarefas);
            threadCliente.start();
        }
    }
}
