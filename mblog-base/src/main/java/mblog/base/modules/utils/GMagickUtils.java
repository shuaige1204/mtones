/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.modules.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;

/**
 * <p>图片处理类</p>
 * 
 * <p>Description: 工具下载  http://www.graphicsmagick.org</p>
 * @author langhsu
 *
 */
public class GMagickUtils {
    private static Logger log          = Logger.getLogger(GMagickUtils.class);

    public static String  GMAGICK_HOME = "gmagick.home";

    /*
     * GraphicsMagick 安装目录
     * example:
     *  gmHome = "C:\\Program Files\\GraphicsMagick-1.3.20-Q8"
     */
    public static String  gmHome;

    public static void validate(File ori, String dest) throws IOException {
        File destFile = new File(dest);

        if (ori == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (dest == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (ori.exists() == false) {
            throw new FileNotFoundException("Source '" + ori + "' does not exist");
        }
        if (ori.isDirectory()) {
            throw new IOException("Source '" + ori + "' exists but is a directory");
        }
        if (ori.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            throw new IOException("Source '" + ori + "' and destination '" + dest
                                  + "' are the same");
        }
        if (destFile.getParentFile() != null && destFile.getParentFile().exists() == false) {
            if (destFile.getParentFile().mkdirs() == false) {
                throw new IOException("Destination '" + dest + "' directory cannot be created");
            }
        }
        if (destFile.exists() && destFile.canWrite() == false) {
            throw new IOException("Destination '" + dest + "' exists but is read-only");
        }
    }

    public static int[] getSize(String dest) throws IOException {
        File destFile = new File(dest);
        BufferedImage src = ImageIO.read(destFile); // 读入文件
        int w = src.getWidth();
        int h = src.getHeight();
        return new int[] { w, h };
    }

    private static IMOperation getIMO(Integer width, Integer height) {
        IMOperation op = new IMOperation();
        op.addImage();
        if (height == null) {
            op.resize(width);
        } else {
            op.resize(width, height);
        }
        op.quality(85d);
        op.addImage();

        return op;
    }

    public static void scale(String ori, String dest, int width, int height) throws IOException,
                                                                            InterruptedException,
                                                                            IM4JavaException {
        File destFile = new File(dest);
        if (destFile.exists()) {
            destFile.delete();
        }

        IMOperation imo = getIMO(width, height);
        ConvertCmd cmd = new ConvertCmd(true);
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0) {
            cmd.setSearchPath(getGMagickHome());
        }
        cmd.setErrorConsumer(StandardStream.STDERR);
        cmd.run(imo, ori, dest);
    }

    /**
     * 图片压缩
     * 
     * @param ori 原图位置
     * @param dest 目标位置
     * @param maxSize 指定压缩后最大边长
     * @return boolean
     * 
     * @throws java.io.IOException io异常
     * @throws IM4JavaException    im4j 异常
     * @throws InterruptedException 中断异常
     */
    public static boolean scaleImage(String ori, String dest, int maxSize) throws IOException,
                                                                          InterruptedException,
                                                                          IM4JavaException {
        File oriFile = new File(ori);
        validate(oriFile, dest);

        BufferedImage src = ImageIO.read(oriFile); // 读入文件
        int w = src.getWidth();
        int h = src.getHeight();

        log.debug("origin with/height " + w + "/" + h);

        int size = (int) Math.max(w, h);
        int tow = w;
        int toh = h;

        if (size > maxSize) {
            if (w > maxSize) {
                tow = maxSize;
                toh = h * maxSize / w;
            } else {
                tow = w * maxSize / h;
                toh = maxSize;
            }
        }

        log.debug("scaled with/height : " + tow + "/" + toh);

        scale(ori, dest, tow, toh);

        return true;
    }

    /**
     * 根据最大宽度图片压缩
     * 
     * @param ori 原图位置
     * @param dest 目标位置
     * @param maxWidth 指定压缩后最大边长
     * @return boolean
     * 
     * @throws java.io.IOException io异常
     * @throws IM4JavaException    im4j 异常
     * @throws InterruptedException 中断异常
     */
    public static boolean scaleImageByWidth(String ori, String dest, int maxWidth)
                                                                                  throws IOException,
                                                                                  InterruptedException,
                                                                                  IM4JavaException {
        File oriFile = new File(ori);
        validate(oriFile, dest);

        BufferedImage src = ImageIO.read(oriFile); // 读入文件
        int w = src.getWidth();
        int h = src.getHeight();

        log.debug("origin with/height " + w + "/" + h);

        int tow = w;
        int toh = h;

        if (w > maxWidth) {
            tow = maxWidth;
            toh = h * maxWidth / w;
        }

        log.debug("scaled with/height : " + tow + "/" + toh);

        scale(ori, dest, tow, toh);

        return true;
    }

    /**
     * 自定义宽高缩放图片
     * 
     * @param ori 原图位置
     * @param dest 目标位置
     * @param width 指定压缩后的宽
     * @param height 指定压缩后的高
     * @return boolean
     * 
     * @throws java.io.IOException io异常
     * @throws IM4JavaException    im4j 异常
     * @throws InterruptedException 中断异常
     */
    public static boolean scaleImage(String ori, String dest, int width, int height)
                                                                                    throws IOException,
                                                                                    InterruptedException,
                                                                                    IM4JavaException {
        File oriFile = new File(ori);

        validate(oriFile, dest);

        BufferedImage src = ImageIO.read(oriFile); // 读入文件
        int w = src.getWidth();
        int h = src.getHeight();

        int tow, toh;

        if (width < w && height < h) {
            tow = width;
            toh = height;
        } else {
            tow = w;
            toh = h;
        }

        scale(ori, dest, tow, toh);
        return true;
    }

    /**
     * 裁剪图片
     * 
     * @param ori  源图片路径
     * @param dest 处理后图片路径
     * @param x    起始X坐标
     * @param y    起始Y坐标
     * @param width  裁剪宽度
     * @param height  裁剪高度
     * @return boolean
     * 
     * @throws java.io.IOException io异常
     * @throws IM4JavaException    im4j 异常
     * @throws InterruptedException 中断异常
     */
    public static boolean truncateImage(String ori, String dest, int x, int y, int width, int height)
                                                                                                     throws IOException,
                                                                                                     InterruptedException,
                                                                                                     IM4JavaException {
        File oriFile = new File(ori);

        validate(oriFile, dest);

        IMOperation op = new IMOperation();
        op.addImage(ori);

        /** width：裁剪的宽度 * height：裁剪的高度 * x：裁剪的横坐标 * y：裁剪纵坐标 */
        op.crop(width, height, x, y);
        op.addImage(dest);
        ConvertCmd convert = new ConvertCmd(true);
        convert.run(op);
        return true;
    }

    public static boolean truncateImage(String ori, String dest, int x, int y, int size)
                                                                                        throws IOException,
                                                                                        InterruptedException,
                                                                                        IM4JavaException {
        return truncateImage(ori, dest, x, y, size, size);
    }

    /**
     * 截图图片中心位置
     * 
     * @param ori  源图片路径
     * @param dest 处理后图片路径
     * @param side 边长
     * @return boolean
     * 
     * @throws java.io.IOException io异常
     * @throws IM4JavaException    im4j 异常
     * @throws InterruptedException 中断异常
     */
    public static boolean truncateImageCenter(String ori, String dest, int side)
                                                                                throws IOException,
                                                                                InterruptedException,
                                                                                IM4JavaException {
        return truncateImage(ori, dest, side, side);
    }

    /**
     * 截图图片中心位置
     * 
     * @param ori 源图片路径
     * @param dest 处理后图片路径
     * @param width 指定宽
     * @param height 知道高
     * @return boolean
     * @throws java.io.IOException io异常
     * @throws IM4JavaException    im4j 异常
     * @throws InterruptedException 中断异常
     */
    public static boolean truncateImage(String ori, String dest, int width, int height)
                                                                                       throws IOException,
                                                                                       InterruptedException,
                                                                                       IM4JavaException {
        File oriFile = new File(ori);

        validate(oriFile, dest);

        BufferedImage src = ImageIO.read(oriFile); // 读入文件
        int w = src.getWidth();
        int h = src.getHeight();

        int min = Math.min(w, h);
        int side = Math.min(width, height);

        IMOperation op = new IMOperation();
        op.addImage(ori);

        if (w <= width && h <= height) {
            // Don't do anything
        } else if (min < side) {
            op.gravity("center").extent(width, height);
        } else {
            op.resize(width, height, '^').gravity("center").extent(width, height);
        }

        op.addImage(dest);
        ConvertCmd convert = new ConvertCmd(true);
        convert.run(op);
        return true;
    }

    private static String getGMagickHome() {
        if (gmHome == null) {
            gmHome = System.getProperty(GMAGICK_HOME);
        }
        return gmHome;
    }
}
