import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

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
