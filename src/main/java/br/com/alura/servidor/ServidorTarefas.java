package br.com.alura.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {
    private final ServerSocket servidor;

    private final ExecutorService threadPool;

    private AtomicBoolean estaRodando;

    public ServidorTarefas() throws IOException {
        System.out.println("--- Iniciando servidor ---");
        this.servidor = new ServerSocket(12345);
        this.threadPool = Executors.newFixedThreadPool(4, new FabricaDeThreads());
        this.estaRodando = new AtomicBoolean(true);
    }

    public static void main(String[] args) throws Exception {
        ServidorTarefas servidor = new ServidorTarefas();
        servidor.rodar();
        servidor.parar();
    }

    public void rodar() throws IOException {
        try {
            while (this.estaRodando.get()) {
                Socket socket = servidor.accept();
                int portaDoCliente = socket.getPort();
                System.out.println("Aceitando novo cliente " + portaDoCliente);

                DistribuirTarefas distribuirTarefas = new DistribuirTarefas(threadPool, socket, this);
                threadPool.execute(distribuirTarefas);
            }
        } catch (SocketException e) {
            System.out.println("SocketException, Está rodando? " + this.estaRodando);
        }
    }

    public void parar() throws IOException {
        this.estaRodando.set(false);
        servidor.close();
        threadPool.shutdown();
    }
}
