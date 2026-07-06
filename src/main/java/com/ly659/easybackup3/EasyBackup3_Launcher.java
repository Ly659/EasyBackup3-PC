package com.ly659.easybackup3;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 本类是EasyBackup3软件的启动程序，负责储存全局常量、启动UI界面。
 * <br>这些全局常量是静态的，可以通过类直接访问。
 */
public class EasyBackup3_Launcher {
    // 基本UI组件
    public static Display display;
    public static Shell shell;

    static void main() {
        display = new Display();
        EasyBackup3 easyBackup3 = new EasyBackup3(display);
        shell = easyBackup3.shell;

        // 启动程序UI界面
        easyBackup3.open();
    }
}