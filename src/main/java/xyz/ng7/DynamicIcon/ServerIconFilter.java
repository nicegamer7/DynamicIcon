package xyz.ng7.DynamicIcon;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Iterator;

public class ServerIconFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
        return !file.isHidden() && file.getName().endsWith(".png") && sizeCheck(file);
    }

    private boolean sizeCheck(File img) {
        Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix("png");
        while (readers.hasNext()) {
            ImageReader reader = readers.next();
            try {
                ImageInputStream stream = new FileImageInputStream(img);
                reader.setInput(stream);
                return reader.getWidth(reader.getMinIndex()) == 64 && reader.getHeight(reader.getMinIndex()) == 64;
            } catch (IOException ignored) {} finally {
                reader.dispose();
            }
        }

        return false;
    }
}
