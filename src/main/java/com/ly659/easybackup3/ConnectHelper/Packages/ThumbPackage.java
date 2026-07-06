package com.ly659.easybackup3.ConnectHelper.Packages;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;

/**
 * 此类用于封装一个图片信息和缩略图，序列化后可在App端和PC端之间传输，也可保存在PC端等待读取。
 */
public class ThumbPackage implements FilePackage, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    // 文件基本信息
    private final String fileName;        // 文件名（含后缀）
    private final int[] date;             // 修改时间（yyyy, mm, dd）
    private final byte[] thumb;           // 缩略图数据
    // 路径
    private final String sourcePath;      // 该文件在源手机上的路径
    private final String destPath;        // 该文件将被备份到的路径

    /**
     * 初始化缩略图封装对象。
     * @param fileName 文件名
     * @param date 修改日期（yyyy, mm, dd），长度必须为3
     * @param thumb 缩略图数据
     * @param sourcePath 该文件在手机端的存储路径
     * @param destPath  该文件将要在PC端备份的路径
     */
    public ThumbPackage(String fileName, int[] date, byte[] thumb, String sourcePath, String destPath) {
        if (fileName == null) {
            throw new NullPointerException("fileName不能为null！");
        }
        if (date == null || date.length != 3) {
            throw new NullPointerException("date数组长度不正确或为null！");
        }
        this.fileName = fileName;
        this.date = date;
        this.thumb = thumb;
        this.sourcePath = sourcePath;
        this.destPath = destPath;
    }

    /**
     * 初始化缩略图封装对象。
     * <br>注：此构造方法不指定缩略图数据，将使用默认缩略图。也就是说，当调用<code>getInputStream()</code>时，将返回<code>null</code>。
     * @param fileName 文件名
     * @param date 修改日期（yyyy, mm, dd），长度必须为3
     * @param sourcePath 该文件在手机端的存储路径
     * @param destPath  该文件将要在PC端备份的路径
     */
    public ThumbPackage(String fileName, int[] date, String sourcePath, String destPath) {
        if (fileName == null) {
            throw new NullPointerException("fileName不能为null！");
        }
        if (date == null || date.length != 3) {
            throw new NullPointerException("date数组长度不正确或为null！");
        }
        this.fileName = fileName;
        this.date = date;
        this.thumb = null;
        this.sourcePath = sourcePath;
        this.destPath = destPath;
    }

    /**
     * 返回一个缩略图数据的输入流，可用于读取缩略图。
     * <br>注：当该封装未指定缩略图数据时，此方法会返回<code>null</code>。
     * @return 输入流
     */
    @Deprecated
    public InputStream getInputStream() {
        if (thumb != null) {
            return new ByteArrayInputStream(thumb);
        } else {
            return null;
        }
    }

    /**
     * 返回缩略图文件数据。
     * @return 文件数据（字节数组）
     */
    public byte[] getThumb() {
        return thumb;
    }

    /**
     * 返回该文件的完整文件名（带后缀）。
     * @return 完整文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 返回该文件的修改日期<code>{yyyy, mm, dd}</code>
     * @return 表示修改日期的数组
     */
    public int[] getDate() {
        return date;
    }

    /**
     * 返回源文件（在手机端保存的原位置）的路径。
     * <br>Example: <code>/storage/emulated/0/DCIM/Camera/MyPicture.jpg</code>
     * @return 源文件路径
     */
    public String getSourcePath() {
        return sourcePath;
    }

    /**
     * 返回目标保存（用于备份文件的PC端）路径。
     * <br>Example: <code>D:\MyBackup\MyPicture.jpg</code>
     * @return 目标路径
     */
    public String getDestPath() {
        return destPath;
    }
}
