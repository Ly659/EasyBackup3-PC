package com.ly659.easybackup3;

import com.ly659.easybackup3.ConnectHelper.ConnectHelper;
import com.ly659.easybackup3.ConnectHelper.Packages.ThumbPackage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.io.*;
import java.util.HashMap;

public class EasyBackup3 {
    /**
     * 此类用于创建并显示一个带时间标题的列表控件。
     * <br>以下常用方法：
     * <br>·<code>setTitle(String title)</code> - 设置分组标题（日期）
     * <br>·<code>addItem(int width, int height, String id)</code> - 添加一个空项目
     * <br>·<code>drawThumb(File thumbPack, String id)</code> - 给指定项目渲染缩略图
     * <br>·<code>disposeThumb(String id)</code> - 清除指定的缩略图缓存
     */
    class timeView {
        /**
         * 用于封装缩略图缓存数据的静态类。
         */
        static class ThumbCache {
            public Image thumb;
        }

        // 父控件
        private final Composite board;
        // 时间标题控件
        private final Label title;
        // 列表项目展板
        private final Composite list;

        // 保存所有Image封装类的泛型集合
        private final HashMap<String, ThumbCache> images = new HashMap<>();

        /**
         * 此构造用于第一个控件，无需指定上一个控件。
         * 注意：parent子控件必须使用FormLayout！
         * @param parent 要创建此控件的子控件
         */
        public timeView(Composite parent) {
            // 初始化控件
            this.board = new Composite(parent, SWT.BORDER);
            board.setVisible(false);            // 暂时隐藏起来，通过调用showView方法显示
            // 设置布局，该控件在子控件中的位置
            FormData fdBoard = new FormData();
            fdBoard.top = new FormAttachment(0, 10);
            fdBoard.left = new FormAttachment(0, 10);
            fdBoard.right = new FormAttachment(100, -10);
            board.setLayoutData(fdBoard);

            // 设置布局，用于在该控件中摆放title和list
            board.setLayout(new FormLayout());

            // 初始化title控件，并设置布局数据
            this.title = new Label(board, SWT.BORDER);
            FormData fdTitle = new FormData();
            fdTitle.top = new FormAttachment(0, 10);
            fdTitle.left = new FormAttachment(0, 10);
            fdTitle.right = new FormAttachment(100, -10);
            this.title.setLayoutData(fdTitle);
            this.title.setFont(new Font(display, "微软雅黑", 14, SWT.BOLD));    // 字体样式

            // 初始化list控件，并设置布局数据
            this.list = new Composite(board, SWT.BORDER);
            list.setLayout(new RowLayout());            // 使用RowLayout进行项目布局（关键！）
            FormData fdList = new FormData();
            fdList.top = new FormAttachment(title, 10);             // 顶部距离title控件10像素
            fdList.left = new FormAttachment(0, 10);      // 左侧距离父控件边缘10像素
            fdList.right = new FormAttachment(100, -10);  // 右侧距离父控件边缘10像素
            this.list.setLayoutData(fdList);
        }
        /**
         * 注意：parent子控件必须使用FormLayout！
         * @param previous 上一个控件
         * @param parent 要创建此控件的子控件
         */
        public timeView(Composite parent, Composite previous) {
            // 初始化控件
            this.board = new Composite(parent, SWT.BORDER);
            board.setVisible(false);            // 暂时隐藏起来，通过调用showView方法显示
            // 设置布局，该控件在子控件中的位置
            FormData fdBoard = new FormData();
            fdBoard.top = new FormAttachment(previous, 10);         // 顶部距离上一个控件10像素
            fdBoard.left = new FormAttachment(previous, 0);         // 左侧与上一个控件对齐
            fdBoard.right = new FormAttachment(previous, 0);        // 右侧与上一个控件对齐
            board.setLayoutData(fdBoard);

            // 设置布局，用于在该控件中摆放title和list
            board.setLayout(new FormLayout());

            // 初始化title控件，并设置布局数据
            this.title = new Label(board, SWT.BORDER);
            FormData fdTitle = new FormData();
            fdTitle.top = new FormAttachment(0, 10);
            fdTitle.left = new FormAttachment(0, 10);
            fdTitle.right = new FormAttachment(100, -10);
            this.title.setLayoutData(fdTitle);
            this.title.setFont(new Font(display, "微软雅黑", 14, SWT.BOLD));    // 字体样式

            // 初始化list控件，并设置布局数据
            this.list = new Composite(board, SWT.BORDER);
            RowLayout rowLayout = new RowLayout();
            rowLayout.wrap = true;
            rowLayout.spacing = 5;
            list.setLayout(rowLayout);            // 使用RowLayout进行项目布局（关键！）
            FormData fdList = new FormData();
            fdList.top = new FormAttachment(title, 10);             // 顶部距离title控件10像素
            fdList.left = new FormAttachment(0, 10);      // 左侧距离父控件边缘10像素
            fdList.right = new FormAttachment(100, -10);  // 右侧距离父控件边缘10像素
            this.list.setLayoutData(fdList);
        }

        /**
         * 设置该组控件的标题。
         * @param title 标题文字
         */
        public void setTitle(String title) {

            this.title.setText(title);
        }

        /**
         * 向该列表中添加一个<u>空的</u>项目。
         * @param width 项目宽度（像素）
         * @param height 项目高度（像素）
         * @param id 指定该项目的唯一标识ID。稍后渲染、删除缩略图缓存时，需根据此ID寻找对应数据。
         */
        public void addItem(int width, int height, String id) {
            // 创建控件
            Label label = new Label(list, SWT.BORDER);
            label.setData(id);          // 设置唯一的标识ID

            // 设置控件的布局数据（宽度和高度）
            RowData rowData = new RowData();
            rowData.width = width;
            rowData.height = height;
            label.setLayoutData(rowData);
        }

        /**
         * 给指定项目绘制缩略图。
         * <br> ·若内存中已有缩略图缓存：直接使用缩略图缓存进行渲染；
         * <br> ·若内存中无缩略图缓存：从硬盘上读取指定封装对象并保存缩略图缓存到内存后再渲染。
         * @param thumbPack 缩略图数据封装对象
         * @param id 指定项目的标识ID
         */
        public void drawThumb(File thumbPack, String id) {
            // 查找需要渲染缩略图的目标控件
            for (Control item: list.getChildren()) {
                if (item instanceof Label label && label.getData().equals(id)) {

                    // 如果集合中没有找到要渲染的缓存数据
                    // （可能是第一次渲染或之前调用了disposeThumb方法删除了缓存），就从硬盘重新读取缩略图数据
                    if (!images.containsKey(id)) {
                        ThumbCache thumbCache = new ThumbCache();       // 封装缩略图缓存的对象
                        try (FileInputStream fis = new FileInputStream(thumbPack);
                             ObjectInputStream ois = new ObjectInputStream(fis)) {
                            if (ois.readObject() instanceof ThumbPackage thumbPackage) {
                                // 将读取的缩略图数据缓存到内存中
                                try (ByteArrayInputStream bais = new ByteArrayInputStream(thumbPackage.getThumb())) {
                                    thumbCache.thumb = new Image(display, bais);
                                    images.put(id, thumbCache);         // 将读取的Image保存到泛型集合中
                                }
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            LogRecorder.error(e.getMessage());
                        }
                    }

                    // 用集合中缓存的数据来渲染缩略图，避免每次渲染都重复读取硬盘（当窗口大小改变时会重新渲染图片）
                    label.addPaintListener(paintEvent -> paintEvent.gc.drawImage(images.get(id).thumb, 0, 0, label.getSize().x, label.getSize().y));
                }
            }
        }

        /**
         * 清除内存中指定的缩略图数据缓存，以释放系统内存资源。
         * <br><b>注：清除该缩略图缓存后，下一次渲染该缩略图（调用<code>drawThumb()</code>方法）将重新读取硬盘并保存新缩略图缓存。</b>
         * @param id 要清除的缩略图ID
         */
        public void disposeThumb(String id) {
            if (images.containsKey(id)) {
                images.get(id).thumb.dispose();
                images.remove(id);      // 从集合中移除
            }
        }

        /**
         * 显示当前控件。
         */
        public void showView() {
            board.setVisible(true);
        }

        /**
         * 隐藏当前控件。
         */
        public void hideView() {
            board.setVisible(false);
        }
    }

    // 程序基础UI界面
    private final Display display;
    public Shell shell;

    private final Label welcomeLabel;           // 欢迎文字
    private final Button startButton;           // 开始按钮

    public EasyBackup3(Display display) {
        this.display = display;
        this.shell = new Shell(display);

        // UI界面基本设置
        shell.setText("EasyBackup3 - Internal test only");     // 窗口标题
        shell.setSize(800, 600);                 // 窗口大小
        shell.setLayout(new FormLayout());          // 根窗口使用FormLayout布局方式

        // UI：欢迎界面
        Composite welcomeBoard = new Composite(shell, SWT.BORDER);     // 放置欢迎控件的组件板
        welcomeBoard.setLayout(new FormLayout());
        FormData fdWelcomeBoard = new FormData();
        fdWelcomeBoard.top = new FormAttachment(0, 60);
        fdWelcomeBoard.bottom = new FormAttachment(100, -120);
        fdWelcomeBoard.left = new FormAttachment(0, 80);
        fdWelcomeBoard.right = new FormAttachment(100, -80);
        welcomeBoard.setLayoutData(fdWelcomeBoard);

        this.welcomeLabel = new Label(welcomeBoard, SWT.NONE);
        welcomeLabel.setText("Welcome to EasyBackup3!");
        welcomeLabel.setAlignment(SWT.CENTER);
        welcomeLabel.setFont(new Font(display, "Arial", 14, SWT.BOLD));
        FormData fdLabel = new FormData();
        fdLabel.top = new FormAttachment(0, 20);
        fdLabel.left = new FormAttachment(0, 20);
        fdLabel.right = new FormAttachment(100, -20);
        welcomeLabel.setLayoutData(fdLabel);

        this.startButton = new Button(welcomeBoard, SWT.PUSH);
        startButton.setText("Let's start");
        FormData fdButton = new FormData();
        fdButton.top = new FormAttachment(welcomeLabel, 20);
        fdButton.left = new FormAttachment(0, 20);
        fdButton.right = new FormAttachment(100, -20);
        startButton.setLayoutData(fdButton);

        startButton.addListener(SWT.Selection, _ -> {
            try {
                letsStart();
            } catch (IOException e) {
                LogRecorder.error(e.getMessage());
            }
        });   // 点击按钮执行letsStart方法
    }

    /**
     * 开始等待App端的连接请求。
     */
    private void letsStart() throws IOException {
        // 修改UI界面
        welcomeLabel.setText("Waiting for connection...");
        startButton.setEnabled(false);
        // 启动网络连接

    }

    /**
     * 启动主程序并阻塞，直到窗口关闭。
     */
    public void open() {
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        shell.dispose();
    }
}
