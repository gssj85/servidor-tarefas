package br.com.alura.servidor;

import br.com.alura.cliente.DistribuirTarefas;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {
    public static void main(String[] args) throws Exception {
        System.out.println("--- Iniciando servidor ---");
        ServerSocket servidor = new ServerSocket(12345);
        // Pool com número fixo de threads
        // ExecutorService threadPool = Executors.newFixedThreadPool(2);

        // Cresce/diminui dinamicamente, caso uma thread fique ociosa por mais de 60seg, a mesma é removida
        // do pool
        ExecutorService threadPool = Executors.newCachedThreadPool();

        while (true) {
            Socket socket = servidor.accept();
            int portaDoCliente = socket.getPort();
            System.out.println("Aceitando novo cliente " + portaDoCliente);

            DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
            threadPool.execute(distribuirTarefas);
        }
    }
}
