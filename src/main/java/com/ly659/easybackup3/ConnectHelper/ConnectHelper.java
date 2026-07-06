package com.ly659.easybackup3.ConnectHelper;

import com.ly659.easybackup3.ConnectHelper.Packages.HandPackage;
import com.ly659.easybackup3.Tools.LogRecorder;
import com.ly659.easybackup3.Tools.XMLHelper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 此类用于管理TCP网络连接和数据传输，是EasyBackup3的功能核心。
 */
public class ConnectHelper implements Closeable {
    // 网络连接组件
    private final ServerSocket serverSocket;
    private Socket socket;
    // 输入输出流
    private InputStream inputStream;
    private OutputStream outputStream;

    /**
     * 创建一个新连接实例。
     * @param port 自定义服务端端口号
     * @throws IOException IO异常
     */
    public ConnectHelper(int port) throws IOException {
        // 初始化ServerSocket
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * 判断Socket连接和IO流当前是否可用。
     * @return 是否可用（true为不可用！）
     */
    private boolean connectNotAvailable() {
        return serverSocket.isClosed() || socket.isClosed() || inputStream == null || outputStream == null;
    }

    /**
     * 阻塞并等待客户端的连接请求，获取通信Socket套接字，并读取握手信息。
     * @throws IOException 等待或尝试连接时发生IO异常
     * @return 握手信息XML文件对象
     */
    public File waitForConnection() throws IOException {
        // 阻塞，等待App端的连接请求
        socket = serverSocket.accept();
        // 初始化IO流
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        // 读取握手信息
        return handShake();
    }

    /**
     * 读取客户端发送的握手信息，并将握手信息写入到XML文件。
     * @return 储存握手信息的XML文件对象。
     * <br>注：当发生异常时，返回<code>null</code>。
     * @throws IOException 读取握手信息、写入XML文件时发生IO异常
     */
    private File handShake() throws IOException {
        // 若连接未建立，直接退出
        if (connectNotAvailable()) {
            return null;
        }
        // 初始化对象输入流，读取握手封装对象
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            if (objectInputStream.readObject() instanceof HandPackage handPackage) {
                // 将握手信息写入XML文件
                return XMLHelper.writeHandInfo(handPackage, new File(EasyBackup3_Launcher.tempDir, "HandInfo.xml"));
            }
        } catch (ClassNotFoundException e) {
            LogRecorder.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        // 关闭所有资源
        this.outputStream.close();
        this.inputStream.close();
        this.socket.close();
        this.serverSocket.close();
    }
}
