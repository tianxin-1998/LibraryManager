package com.tianxin.view;

import javax.swing.*;
import java.awt.*;

public class BackgroundView extends JPanel {

    private Image bgImage;

    public BackgroundView(Image image) {
        this.bgImage = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 绘制背景图片
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
    }
}
