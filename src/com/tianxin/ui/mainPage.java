package com.tianxin.ui;

import com.tianxin.utils.PathUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class mainPage extends JFrame {
    public mainPage() throws IOException {
        super("图书管理系统");
        final int height = 600;
        final int width = 800;

        setBounds((Toolkit.getDefaultToolkit().getScreenSize().width-width)/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height-height)/2,width,height);

        setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));







        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new mainPage();
    }
}
