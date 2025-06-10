package com.sistema.carteirarelacionamento.JComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class botaoBorda extends JButton{
    
    public botaoBorda() {
        setContentAreaFilled(false);

        setCor(Color.WHITE);
        corBorda = new Color(2, 104, 176);
        corClick = new Color(165, 210, 255);
        corHover = new Color(216, 235, 255);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(corHover);
                over = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(cor);
                over = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(corClick);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (over) {
                } else {
                    setBackground(cor);
                }
            }
        });
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
        setBackground(cor);
    }

    public Color getCorHover() {
        return corHover;
    }

    public void setCorHover(Color corHover) {
        this.corHover = corHover;
    }

    public Color getCorClick() {
        return corClick;
    }

    public void setCorClick(Color corClick) {
        this.corClick = corClick;
    }

    public Color getCorBorda() {
        return corBorda;
    }

    public void setCorBorda(Color corBorda) {
        this.corBorda = corBorda;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    private boolean over;
    private Color cor;
    private Color corHover;
    private Color corClick;
    private Color corBorda;
    private int radius = 0;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(corBorda);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.setColor(getBackground());
        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius);

        super.paintComponent(g);
    }
}
