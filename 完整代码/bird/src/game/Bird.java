package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * 小鸟
 */
class Bird {

    // 图片
    BufferedImage image;
    // 位置
    int x, y;
    // 宽高
    int width, height;
    // 大小（用于碰撞检测）
    int size;

    // 重力加速度
    double g;
    // 位移的间隔时间
    double t;
    // 最初上抛速度
    double v0;
    // 当前上抛速度
    double speed;
    // 经过时间t之后的位移
    double s;
    // 小鸟的倾角（弧度）
    double alpha;

    // 一组图片，记录小鸟的动画帧
    BufferedImage[] images;
    // 动画帧数组的下标
    int index;

    // 初始化小鸟
    public Bird() throws Exception {
        // 初始化基本参数
        image = ImageIO.read(getClass().getResource("/resources/0.png"));
        width = image.getWidth();
        height = image.getHeight();
        x = 132;
        y = 280;
        size = 40;

        // 初始化位移参数
        g = 4;
        v0 = 20;
        t = 0.25;
        speed = v0;
        s = 0;
        alpha = 0;

        // 初始化动画帧参数
        images = new BufferedImage[8];
        for (int i = 0; i < 8; i++) {
            images[i] = ImageIO.read(getClass().getResource("/resources/" + i + ".png"));
        }
        index = 0;
    }

    // 飞行动作（变化一帧）
    public void fly() {
        index++;
        image = images[(index / 12) % 8];
    }

    // 移动一步
    public void step() {
        double v0 = speed;
        // 计算上抛运动位移
        s = v0 * t + g * t * t / 2;
        // 计算鸟的坐标位置
        y = y - (int) s;
        // 计算下次移动速度
        double v = v0 - g * t;
        speed = v;
        // 计算倾角（反正切函数）
        alpha = Math.atan(s / 8);
    }

    // 向上飞行
    public void flappy() {
        // 重置速度
        speed = v0;
    }

    // 检测小鸟是否碰撞到地面
    public boolean hit(Ground ground) {
        boolean hit = y + size / 2 > ground.y;
        if (hit) {
            y = ground.y - size / 2;
            alpha = -3.14159265358979323 / 2;
        }
        return hit;
    }

    // 检测小鸟是否撞到柱子
    public boolean hit(Column column) {
        // 先检测是否在柱子的范围内
        if (x > column.x - column.width / 2 - size / 2
                && x < column.x + column.width / 2 + size / 2) {
            // 再检测是否在柱子的缝隙中
            if (y > column.y - column.gap / 2 + size / 2
                    && y < column.y + column.gap / 2 - size / 2) {
                return false;
            }
            return true;
        }
        return false;
    }
}