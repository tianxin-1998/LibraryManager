package com.tianxin.ui;


import com.tianxin.Constants;
import com.tianxin.domain.ResultInfo;
import com.tianxin.net.FailListener;
import com.tianxin.net.PostUtils;
import com.tianxin.net.SuccessListener;
import com.tianxin.utils.JsonUtils;
import com.tianxin.utils.PathUtils;
import com.tianxin.view.BackgroundView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class loginPage extends JFrame {

    public loginPage() throws IOException {
        super("登录");
        final int height = 300;
        final int width = 500;

        // 1. 设置位置和大小
        setBounds((Toolkit.getDefaultToolkit().getScreenSize().width-width)/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height-height)/2, width, height);
        // 2. 设置Icon
        setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));
        // 3. 背景图片
        BackgroundView backgroundView = new BackgroundView(ImageIO.read(new File(PathUtils.getRealPath("library.jpg"))));
        backgroundView.setBounds(0, 0, width, height);

        Box vBox =Box.createVerticalBox();
        // 4. 用户名
        Box userBox = Box.createHorizontalBox();
        JLabel userLabel = new JLabel("用户名：");
        JTextField userTextField = new JTextField(15);
        userBox.add(userLabel);
        userBox.add(Box.createHorizontalStrut(10));
        userBox.add(userTextField);
        // 5. 密码
        Box passWordBox = Box.createHorizontalBox();
        JLabel passWordLabel = new JLabel("密    码：");
        JPasswordField passwordField = new JPasswordField(15);
        passWordBox.add(passWordLabel);
        passWordBox.add(Box.createHorizontalStrut(10));
        passWordBox.add(passwordField);
        // 6. 登录、注册按钮
        Box btnBox = Box.createHorizontalBox();
        JButton loginBtn = new JButton("登   录");
        JButton register = new JButton("注   册");
        btnBox.add(loginBtn);
        btnBox.add(Box.createHorizontalStrut(30));
        btnBox.add(register);

        // 7. 登录按钮监听器
        loginBtn.addActionListener(e -> {
            String userName = userTextField.getText().trim();   //trim() 方法用于删除字符串的头尾空白符
            String passWord = new String(passwordField.getPassword()).trim();
            // 8.检验是否输入密码
            if (userName.length()<1||passWord.length()<1){
                JOptionPane.showMessageDialog(this,"用户名和密码不能为空！");
                return;
            }
            // 9. 准备提交的数据
            Map<String,String> date =new HashMap<>();
            // username
            date.put("username",userName);
            date.put("password",passWord);
            // 10. 提交数据
            PostUtils.postWithParams(Constants.LOGIN_URL, date,
                    new SuccessListener() {
                        @Override
                        public void success(String result) throws IOException {
                            // 11. 解析结果
                            ResultInfo resultInfo = JsonUtils.parseResult(result);
                            if (resultInfo.isFlag()) {
                                // 12. 跳转到首页并关掉登录窗口
                                new mainPage();
                                dispose();
                            } else {
                                // 13. 登陆失败，输出提示消息
                                String message = resultInfo.getMessage();
                                JOptionPane.showMessageDialog(loginPage.this, message);
                            }
                        }
                    },
                    new FailListener() {
                        @Override
                        public void fail() {

                        }
                    }
            );
        });

        // 14.监听注册按钮
        register.addActionListener(e -> {
            // 跳转到注册界面
            try {
                new registerPage();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            dispose();
        });

        // 15.组装控件
        add(backgroundView);
        backgroundView.add(vBox);
        vBox.add(Box.createVerticalStrut(50));
        vBox.add(userBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(passWordBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(btnBox);
    }
    public static void main(String[] args) throws IOException {
        new loginPage().setVisible(true);
    }
}
