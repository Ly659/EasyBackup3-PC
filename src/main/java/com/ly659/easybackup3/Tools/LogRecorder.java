package com.ly659.easybackup3.Tools;

import com.ly659.easybackup3.EasyBackup3_Launcher;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

/**
 * 本类负责日志的记录和管理，以及错误信息、提示信息的显示。
 */
public class LogRecorder {
    /**
     * 显示一个错误提示框。
     * @param message 提示内容
     */
    public static void error(String message){
        System.err.println(message);

        // 错误对话框
        MessageBox errDialog = new MessageBox(EasyBackup3_Launcher.shell, SWT.ICON_ERROR | SWT.OK);
        errDialog.setText("Error");
        errDialog.setMessage(message);
        errDialog.open();
    }
}
