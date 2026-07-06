package com.ly659.easybackup3;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * 本类是EasyBackup3软件的启动程序，负责储存全局常量、启动UI界面。
 * <br>这些全局常量是静态的，可以通过类直接访问。
 */
public class EasyBackup3_Launcher {
    // 基本UI组件
    public static Display display;
    public static Shell shell;

    // 路径变量
    public static File tempDir;     // 临时目录

    /**
     * 清理临时文件夹中的所有文件和目录。临时文件夹本身不会被删除。
     * @param tempDir 临时文件夹路径
     */
    static void cleanupTempDir(File tempDir) {
        // 循环遍历：该目录下的所有文件和文件夹
        for (File tempFile : Objects.requireNonNull(tempDir.listFiles())) {
            if (tempFile.isFile()) {
                // 若是一个文件，则删除
                if (!tempFile.delete()) {
                    System.err.println("Could not delete temp file " + tempFile.getAbsolutePath());
                }
            } else if (tempFile.isDirectory()) {
                // 若是一个文件夹，则递归删除其中的所有文件后，再删除其本身
                cleanupTempDir(tempFile);
                if (!tempFile.delete()) {
                    System.err.println("Could not delete temp folder " + tempFile.getAbsolutePath());
                }
            }
        }
    }

    static void main() {
        // 给变量赋值
        tempDir = new File(System.getProperty("java.io.tmpdir"));       // 临时目录

        display = new Display();
        EasyBackup3 easyBackup3 = new EasyBackup3(display);
        shell = easyBackup3.shell;

        // 启动程序UI界面
        easyBackup3.open();

        // 程序结束后，清理临时文件
        cleanupTempDir(tempDir);
    }
}