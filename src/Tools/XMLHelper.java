package Tools;

import ConnectHelper.Packages.HandPackage;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;

/**
 * 此类用于管理XML文件的读取和写入，简化XML的使用流程。
 */
public class XMLHelper {
    /**
     * 读取握手信息封装，并写入握手信息到<code>HandInfo.xml</code>文件。
     * @param handPackage 握手信息封装对象
     * @param targetPath 指定保存XML的目录（必须是个目录，若不存在会自动创建）
     * @return 创建的XML文件的File对象
     */
    public static File writeHandInfo(HandPackage handPackage, File targetPath) throws IOException {
        // 判断传参是否合法
        if (handPackage == null || targetPath == null) {
            throw new NullPointerException("HandPackage or targetPath is null");
        } else if (targetPath.isFile()) {
            throw  new IllegalArgumentException("targetPath cannot be a file");
        } else if (!targetPath.exists()) {
            if (!targetPath.mkdirs()) throw new IOException("Could not create target path");
        }

        // 新建XML文件
        Document xmlFile = DocumentHelper.createDocument();
        xmlFile.addComment("Handshake Info");       // 注释

        // 创建根节点
        Element root = xmlFile.addElement("HandPackage");
        xmlFile.setRootElement(root);

        // 手机名称节点
        Element phoneName = root.addElement("PhoneName");
        phoneName.setText(handPackage.getPhoneName());

        // 手机系统信息节点
        Element systemVersion = root.addElement("SystemVersion");
        systemVersion.setText(handPackage.getSystemVersion());

        // 连接信息节点
        Element connectHelper = root.addElement("ConnectHelper");
        Element phoneIp = connectHelper.addElement("PhoneIp");
        phoneIp.setText(handPackage.getPhoneIp());
        Element appPort = connectHelper.addElement("AppPort");
        appPort.setText(Integer.toString(handPackage.getAppPort()));

        // 将文件保存到指定位置
        File xmlFileSave = new File(targetPath, "HandInfo.xml");
        try (FileOutputStream out = new FileOutputStream(xmlFileSave); Writer writer = new OutputStreamWriter(out)) {
            xmlFile.write(writer);
        }

        return xmlFileSave;
    }
}
