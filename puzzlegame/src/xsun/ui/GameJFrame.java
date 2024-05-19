package xsun.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    //跟游戏相关的代码都写在这里面

    //创建一个二维数组，用来管理数据，加载图片的时候会根据二维数据中的数据进行加载
    int[][] data = new int[4][4];

    //记录空白方块在二维数组中的位置
    int x = 0;
    int y = 0;

    //统计步数
    int step = 0;

    //定义一个变量，记录当前展示的图片路径(默认为动物3）
    String path = "puzzlegame\\image\\animal\\animal3\\";

    //定义一个二维数组，储存正确的数据
    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    //创建选项下面的条目
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    JMenuItem accountItem = new JMenuItem("我的微信");
    JMenuItem girlItem = new JMenuItem("美女");
    JMenuItem animalItem = new JMenuItem("动物");
    JMenuItem sportItem = new JMenuItem("运动");

    public GameJFrame() {
        //初始化界面
        initJFrame();

        //初始化菜单
        initJMenuBar();

        //初始化数据（打乱）
        initData();

        //初始化图片
        initImage();

        //让界面显示出来，建议写在最后
        this.setVisible(true);
    }

    //初始化数据（打乱）
    private void initData() {

        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[index];
            tempArr[index] = tempArr[i];
            tempArr[i] = temp;
        }


        int count = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (tempArr[count] == 0) {
                    x = i;
                    y = j;

                }
                data[i][j] = tempArr[count];

                count++;
            }
        }

    }

    //初始化图片
    //添加图片时，按照二维数组中的顺序添加
    private void initImage() {
        //清空原本已经出现的所有图片
        this.getContentPane().removeAll();

        if (victory()) {
            //显示胜利的图标
            JLabel winJLabel = new JLabel(new ImageIcon("puzzlegame\\image\\win.png"));
            winJLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winJLabel);

        }
        //细节：先加载的图片在上方，后加载的图片塞在下面

        JLabel stepCount = new JLabel("步数:" + step);
        stepCount.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepCount);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //获取当前要加载的图片序号
                int num = data[i][j];
                //创建一个JLabel的对象（容器）
                JLabel jLabel = new JLabel(new ImageIcon(path + num + ".jpg"));
                //指定图片位置
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                //给图片添加边框
                //0:让图片凸起来
                //1:让图片凹下去
                jLabel.setBorder(new BevelBorder(1));
                //把管理容器添加到界面中
                this.getContentPane().add(jLabel);

            }
        }

        //添加背景图片
        ImageIcon bg = new ImageIcon("puzzlegame\\image\\background.png");
        JLabel background = new JLabel(bg);
        background.setBounds(40, 40, 508, 560);
        //把背景图片添加到界面中
        this.getContentPane().add(background);

        //刷新界面
        this.getContentPane().repaint();

    }

    public void initJFrame() {
        //设置界面的宽高
        this.setSize(603, 680);
        //设置界面的标题
        this.setTitle("什羊拼图单机版 v1.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认的居中放置，只有取消了才会按照XY轴的形式添加组件
        this.setLayout(null);
        //给整个界面添加监听事件
        this.addKeyListener(this);

    }

    public void initJMenuBar() {
        //创建整个菜单对象
        JMenuBar jMenuBar = new JMenuBar();

        //创建菜单上的两个选项对象
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");

        JMenu changeMenu = new JMenu("更改图片");

        //将每一个选项下面的条目添加到选项中
        functionJMenu.add(changeMenu);
        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(accountItem);

        changeMenu.add(girlItem);
        changeMenu.add(animalItem);
        changeMenu.add(sportItem);

        //给条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);

        girlItem.addActionListener(this);
        animalItem.addActionListener(this);
        sportItem.addActionListener(this);

        //将菜单里面的两个选项添加到菜单当中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //判断游戏是否胜利
        if (victory()) {
            return;
        }

        int code = e.getKeyCode();
        if (code == 65) {
            //把界面中所有的图片全部删除
            this.getContentPane().removeAll();
            //加载第一张完整的图片
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);
            //加载背景图片
            ImageIcon bg = new ImageIcon("puzzlegame\\image\\background.png");
            JLabel background = new JLabel(bg);
            background.setBounds(40, 40, 508, 560);
            //把背景图片添加到界面中
            this.getContentPane().add(background);
            //刷新界面
            this.getContentPane().repaint();

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //判断游戏是否胜利
        if (victory()) {
            return;
        }

        //对上下左右进行判断
        //左：37  上：38  右：39  下：40
        int code = e.getKeyCode();
        if (code == 37) {
            System.out.println("向左移动");
            //将空白方块右方的数字往左移动
            if (y == 3) {
                return;
            }
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;

            //每移动一次，计步器自增
            step++;

            //调用方法按照最新的数字加载图片
            initImage();

        } else if (code == 38) {
            System.out.println("向上移动");
            if (x == 3) {
                return;
            }
            //将空白方块下方的数字往上移动
            //x, y 表示空白方块
            //x+1, y 表示空白方块下方的数字

            //把空白方块下方的数字赋值给空白方块
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            //每移动一次，计步器自增
            step++;
            //调用方法按照最新的数字加载图片
            initImage();

        } else if (code == 39) {
            System.out.println("向右移动");
            //将空白方块左方的数字往右移动
            if (y == 0) {
                return;
            }
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            //每移动一次，计步器自增
            step++;
            //调用方法按照最新的数字加载图片
            initImage();

        } else if (code == 40) {
            System.out.println("向下移动");
            //将空白方块上方的数字往下移动
            if (x == 0) {
                return;
            }
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            //每移动一次，计步器自增
            step++;
            //调用方法按照最新的数字加载图片
            initImage();
        } else if (code == 65) {
            initImage();
        } else if (code == 87) {
            //按w 一键完成
            data = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0}
            };
            initImage();
        }
    }

    //判断data数组中的数据是否跟win数组中的相同
    //如果全部相同，返回true
    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取当前被点击的条目对象
        Object obj = e.getSource();
        //判断
        if (obj == replayItem) {
            System.out.println("重新游戏");

            //再次打乱二维数组中的数据
            initData();

            //计步器清零
            step = 0;

            //重新加载图片
            initImage();


        } else if (obj == reLoginItem) {
            System.out.println("重新登录");
            //关闭当前界面
            this.setVisible(false);
            //打开登录界面
            new LoginJFrame();

        } else if (obj == closeItem) {
            System.out.println("关闭游戏");
            //直接关闭虚拟机
            System.exit(0);

        } else if (obj == accountItem) {
            System.out.println("公众号");

            // 创建一个弹窗对象
            JDialog jDialog = new JDialog();

            //创建一个管理图片的容器对象
            JLabel jLabel = new JLabel((new ImageIcon("puzzlegame\\image\\about.jpg")));

            //设置位置和宽高
            jLabel.setBounds(0, 0, 258, 258);

            //将图片添加到弹框当中
            jDialog.getContentPane().add(jLabel);

            //给弹框设置大小
            jDialog.setSize(344, 344);

            //置顶弹框
            jDialog.setAlwaysOnTop(true);

            //让弹框居中
            jDialog.setLocationRelativeTo(null);

            //弹框不关闭无法操作下面的界面
            jDialog.setModal(true);

            //让弹框显示
            jDialog.setVisible(true);
        } else if (obj == girlItem) {
            System.out.println("更换美女图片");
            Random r = new Random();
            //获取一个随机值
            int num = r.nextInt(11) + 1;
            //更改图片路径
            path = "puzzlegame\\image\\girl\\girl" + num + "\\";

            //重新初始化游戏
            //打乱二维数组中的数据
            initData();

            //计步器清零
            step = 0;

            //重新加载图片
            initImage();

        } else if (obj == animalItem) {
            System.out.println("更换动物图片");
            Random r = new Random();
            //获取一个随机值
            int num = r.nextInt(8) + 1;
            //更改图片路径
            path = "puzzlegame\\image\\animal\\animal" + num + "\\";

            //重新初始化游戏
            //打乱二维数组中的数据
            initData();

            //计步器清零
            step = 0;

            //重新加载图片
            initImage();

        } else if (obj == sportItem) {
            System.out.println("更换运动图片");
            Random r = new Random();
            //获取一个随机值
            int num = r.nextInt(10) + 1;
            //更改图片路径
            path = "puzzlegame\\image\\sport\\sport" + num + "\\";

            //重新初始化游戏
            //打乱二维数组中的数据
            initData();

            //计步器清零
            step = 0;

            //重新加载图片
            initImage();

        }
    }

}
