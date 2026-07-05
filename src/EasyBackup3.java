import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;

public class EasyBackup3 {
    // 程序基础UI界面
    private final Display display;
    public Shell shell;

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
        
        Label welcomeLabel = new Label(welcomeBoard, SWT.NONE);         // 欢迎文字
        welcomeLabel.setText("Welcome to EasyBackup3!");
        welcomeLabel.setAlignment(SWT.CENTER);
        welcomeLabel.setFont(new Font(display, "Arial", 12, SWT.BOLD));
        FormData fdLabel = new FormData();
        fdLabel.top = new FormAttachment(0, 20);
        fdLabel.left = new FormAttachment(0, 20);
        fdLabel.right = new FormAttachment(100, -20);
        welcomeLabel.setLayoutData(fdLabel);

        Button startButton = new Button(welcomeBoard, SWT.PUSH);        // 开始按钮
        startButton.setText("Let's start");
        FormData fdButton = new FormData();
        fdButton.top = new FormAttachment(welcomeLabel, 20);
        fdButton.left = new FormAttachment(0, 20);
        fdButton.right = new FormAttachment(100, -20);
        startButton.setLayoutData(fdButton);
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

    static void main() {
        // 启动主程序UI界面
        Display display = new Display();
        EasyBackup3 easyBackup3 = new EasyBackup3(display);
        easyBackup3.open();
    }
}
