package de.thomas.chess.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

/**
 * Utility class with methods for the GUI
 * @author Thomas Opitz
 *
 */
public class GUIMethods {
	public static final BufferedImage makeTransparent(final BufferedImage image, final Color color) {
		final ImageFilter filter = new RGBImageFilter() {
			private static final int ALPHA = 0xFF000000;
			private final int markerRGB = color.getRGB() | ALPHA;

			@Override
			public int filterRGB(final int x, final int y, final int rgb) {
				if ((rgb | ALPHA) == markerRGB) {
					// Mark the alpha bits as zero - transparent
					return 0x00FFFFFF & rgb;
				} else {
					// nothing to do
					return rgb;
				}
			}
		};
		final ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
		final Image img = Toolkit.getDefaultToolkit().createImage(ip);
		final BufferedImage b = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2 = b.createGraphics();

		g2.drawImage(img, 0, 0, null);
		g2.dispose();

		return b;
	}

}
