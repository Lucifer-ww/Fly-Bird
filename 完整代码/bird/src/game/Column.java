package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 柱子
 */
class Column {

    // 图片
    BufferedImage image;
    // 位置
    int x, y;
    // 宽高
    int width, height;
    // 柱子之间的缝隙
    int gap;
    // 柱子之间的距离
    int distance;
    // 随机数工具
    Random random = new Random();

    /**
     * 初始化第N个柱子
     */
    public Column(int n) throws Exception {
        image = ImageIO.read(getClass().getResource("/resources/column.png"));
        width = image.getWidth();
        height = image.getHeight();
        gap = 144;
        distance = 245;
        x = 550 + (n - 1) * distance;
        y = random.nextInt(218) + 132;
    }

    // 向左移动一步
    public void step() {
        x--;
        if (x == -width / 2) {
            x = distance * 2 - width / 2;
            y = random.nextInt(218) + 132;
        }
    }
}