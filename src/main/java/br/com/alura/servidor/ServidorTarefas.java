package br.com.alura.servidor;

import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTarefas {
    public static void main(String[] args) throws Exception {
        System.out.println("--- Iniciando servidor ---");
        ServerSocket servidor = new ServerSocket(12345);

        while (true) {
            Socket socket = servidor.accept();
            System.out.println("Aceitando novo cliente " + socket.getPort());

            Thread.sleep(20000);
        }
    }
}
