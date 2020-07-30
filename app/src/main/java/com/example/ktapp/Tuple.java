package com.example.ktapp;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp
 * @ClassName: Tuple
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/7/1 9:16
 * @UpdateUser: LiuTao
 */
public class Tuple {
    private double x;
    private double y;

    public Tuple(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
