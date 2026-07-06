package com.ly659.easybackup3.ConnectHelper.Packages;

import java.io.Serial;
import java.io.Serializable;

/**
 * 本类用于App端和PC端建立连接时的握手操作，并传递封装握手信息。
 */
public class HandPackage implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;

    // 手机端信息
    private final String phoneName;
    private final String systemVersion;
    // 连接信息（用于PC端发送请求）
    private final String phoneIp;       // 手机端IP地址
    private final int appPort;          // 手机端应用端口号

    public HandPackage(String phoneName, String systemVersion,  String phoneIp, int appPort) {
        this.phoneName = phoneName;
        this.systemVersion = systemVersion;
        this.phoneIp = phoneIp;
        this.appPort = appPort;
    }

    /**
     * 获取手机名称信息。
     * @return 手机名称
     */
    public String getPhoneName() {
        return phoneName;
    }

    /**
     * 获取手机系统（Android）版本。如：<code>Android 9.0</code>
     * @return 系统版本号
     */
    public String getSystemVersion() {
        return systemVersion;
    }

    /**
     * 返回当前手机端IP地址（IPV4）。
     * @return IP地址（xxx.xxx.xxx.xxx）
     */
    public String getPhoneIp() {
        return phoneIp;
    }

    /**
     * 获取当前手机端App通信端口号。
     * @return 端口号
     */
    public int getAppPort() {
        return appPort;
    }
}
