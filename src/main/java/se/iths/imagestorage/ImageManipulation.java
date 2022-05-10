package se.iths.imagestorage;

import org.imgscalr.Scalr;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageManipulation {
    private ImageManipulation() {}

    /**
     * Resizes an Image passed as a byte[]
     * @param content The image as a byte[]
     * @param size The desired size (height if image is PORTRAIT-oriented and width if LANDSCAPE-oriented or SQUARE)
     * @param format The format of the image
     * @return A byte[] with the content of the resized image
     * @throws IOException If the resizing failed an IOException is thrown
     */
    public static byte[] resize(byte[] content, int size, String format) throws IOException {
        BufferedImage bufferedImage = convertToBufferedImage(content);
        BufferedImage resizedImage = resize(bufferedImage, size);
        return convertToByteArray(resizedImage, format);
    }

    public static BufferedImage resize(BufferedImage image, int size){
        return Scalr.resize(image, size);
    }

    public static BufferedImage convertToBufferedImage(byte[] content) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(content);
        return ImageIO.read(inputStream);
    }

    public static byte[] convertToByteArray(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", outputStream);
        return outputStream.toByteArray();
    }
}
