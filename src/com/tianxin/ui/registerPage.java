package com.tianxin.ui;

import com.tianxin.Constants;
import com.tianxin.domain.ResultInfo;
import com.tianxin.net.ImageRequestUtils;
import com.tianxin.net.PostUtils;
import com.tianxin.utils.JsonUtils;
import com.tianxin.utils.PathUtils;
import com.tianxin.view.BackgroundView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class registerPage extends JFrame {
    public registerPage() throws IOException {
        super("注册");
        final int height = 300;
        final int width = 500;

        setBounds((Toolkit.getDefaultToolkit().getScreenSize().width-width)/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height-height)/2,width,height);

        setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));

        BackgroundView backgroundView = new BackgroundView(ImageIO.read(new File(PathUtils.getRealPath("register.jpg"))));
        backgroundView.setBounds(0,0,width,height);

        Box vBox = Box.createVerticalBox();
        backgroundView.add(vBox);

        Box userBox = Box.createHorizontalBox();
        JLabel userLabel = new JLabel("用  户  名：");
        JTextField userTextField = new JTextField(15);
        userBox.add(userLabel);
        userBox.add(Box.createHorizontalStrut(10));
        userBox.add(userTextField);

        Box passwordBox = Box.createHorizontalBox();
        JLabel passWordLabel = new JLabel("密        码：");
        JPasswordField passwordField = new JPasswordField(15);
        passwordBox.add(passWordLabel);
        passwordBox.add(Box.createHorizontalStrut(10));
        passwordBox.add(passwordField);

        Box checkPasswordBox = Box.createHorizontalBox();
        JLabel checkPasswordLabel = new JLabel("确认密码：");
        JPasswordField checkPasswordField = new JPasswordField(15);
        checkPasswordBox.add(checkPasswordLabel);
        checkPasswordBox.add(Box.createHorizontalStrut(10));
        checkPasswordBox.add(checkPasswordField);

        Box phoneBox = Box.createHorizontalBox();
        JLabel phoneLabel = new JLabel("电        话：");
        JTextField phoneTextField = new JTextField(15);
        phoneBox.add(phoneLabel);
        phoneBox.add(Box.createHorizontalStrut(10));
        phoneBox.add(phoneTextField);

        Box sexBox = Box.createHorizontalBox();
        JLabel sexLabel = new JLabel("性         别：");
        JRadioButton manRadio = new JRadioButton("男",true);
        JRadioButton womanRadio = new JRadioButton("女",false);
        ButtonGroup sexGroup = new ButtonGroup();
        sexGroup.add(manRadio);
        sexGroup.add(womanRadio);
        sexBox.add(sexLabel);
        sexBox.add(Box.createHorizontalStrut(10));
        sexBox.add(manRadio);
        sexBox.add(womanRadio);
        sexBox.add(Box.createHorizontalStrut(100));

        Box checkCodeBox = Box.createHorizontalBox();
        JLabel checkCodeLabel = new JLabel("验  证  码：");
        JTextField checkCodeField = new JTextField();
        JLabel checkImageLabel = new JLabel(new ImageIcon(ImageRequestUtils.getImage("http://localhost:8080/code/getCheckCode")));
        checkCodeBox.add(checkCodeLabel);
        checkCodeBox.add(Box.createHorizontalStrut(10));
        checkCodeBox.add(checkCodeField);
        checkCodeBox.add(checkImageLabel);

        Box btnBox = Box.createHorizontalBox();
        JButton register = new JButton("注   册");
        btnBox.add(register);

        checkImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkImageLabel.setIcon(new ImageIcon(ImageRequestUtils.getImage("http://localhost:8080/code/getCheckCode")));
                checkImageLabel.updateUI();
            }
        });

        register.addActionListener(e -> {
            String username = userTextField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String checkPassword = new String(checkPasswordField.getPassword()).trim();
            String phone = phoneTextField.getText().trim();
            String sex = sexGroup.isSelected(manRadio.getModel())?manRadio.getText():womanRadio.getText();
            String checkCode = checkCodeField.getText().trim();

            if (username.length()<1){
                JOptionPane.showMessageDialog(this,"请输入用户名！");
            }else if (password.length()<1){
                JOptionPane.showMessageDialog(this,"请输入密码！");
            }else if (checkPassword.length()<1){
                JOptionPane.showMessageDialog(this,"请输入确认密码！");
            }else if (phone.length()<1){
                JOptionPane.showMessageDialog(this,"请输入电话！");
            }else if (checkCode.length()<1){
                JOptionPane.showMessageDialog(this,"请输入验证码！");
            }else if(!password.equals(checkPassword)){
                JOptionPane.showMessageDialog(this,"两次密码输入不一致！");
            } else {
                Map<String,String> data = new HashMap<>();
                data.put("username",username);
                data.put("password",password);
                data.put("phone",phone);
                data.put("gender",sex);
                data.put("checkCode",checkCode);

                PostUtils.postWithParams(Constants.REGISTER_URL,data,result -> {
                    ResultInfo resultInfo = JsonUtils.parseResult(result);
                    if (resultInfo.isFlag()){
                        new mainPage();
                        dispose();
                    }else {
                        String msg = resultInfo.getMessage();
                        JOptionPane.showMessageDialog(this,msg);
                    }
                    }, () -> {
                    JOptionPane.showMessageDialog(this, "操作失败！");
                });
            }
        });

        add(backgroundView);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(userBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(passwordBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(checkPasswordBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(phoneBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(sexBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(checkCodeBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(btnBox);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new registerPage();
    }
}
