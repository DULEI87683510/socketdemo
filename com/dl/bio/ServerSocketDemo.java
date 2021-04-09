package com.dl.socketdemo.com.dl.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author DL
 * @description 传统BIO
 * @date 2020/11/5
 */
public class ServerSocketDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        while (true) {
            //accept会阻塞
            final Socket socket = serverSocket.accept();

            System.out.println(socket.getInetAddress() + "连接了服务器");
            new Thread(() -> handler(socket)).start();

        }


    }


    private static void handler(Socket socket) {
        byte[] bytes = new byte[1024 * 1024];
        try {
            InputStream inputStream = socket.getInputStream();
            while (true) {
                //red方法也会阻塞
                int read = inputStream.read(bytes);
                System.out.println(read);
                if (read != -1) {
                    socket.getOutputStream().write(bytes, 0, read);
                    System.out.println(new String(bytes, 0, read));
                } else {
                    System.out.println("执行了break");
                    break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
