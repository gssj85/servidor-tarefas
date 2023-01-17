package br.com.alura.servidor;

import br.com.alura.cliente.DistribuirTarefas;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {
    private final ServerSocket servidor;

    private final ExecutorService threadPool;

    private boolean estaRodando;

    public ServidorTarefas() throws IOException {
        System.out.println("--- Iniciando servidor ---");
        this.servidor = new ServerSocket(12345);

        // Pool com número fixo de threads
        // ExecutorService threadPool = Executors.newFixedThreadPool(2);

        // Cresce/diminui dinamicamente, caso uma thread fique ociosa por mais de 60seg, a mesma é removida
        // do pool
        this.threadPool = Executors.newCachedThreadPool();
        this.estaRodando = true;
    }

    public static void main(String[] args) throws Exception {
        ServidorTarefas servidor = new ServidorTarefas();
        servidor.rodar();
        servidor.parar();
    }

    public void rodar() throws IOException {
        try {
            while (this.estaRodando) {
                Socket socket = servidor.accept();
                int portaDoCliente = socket.getPort();
                System.out.println("Aceitando novo cliente " + portaDoCliente);

                DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket, this);
                threadPool.execute(distribuirTarefas);
            }
        } catch (SocketException e) {
            System.out.println("SocketException, Está rodando? " + this.estaRodando);
        }
    }

    public void parar() throws IOException {
        this.estaRodando = false;
        servidor.close();
        threadPool.shutdown();
    }
}
