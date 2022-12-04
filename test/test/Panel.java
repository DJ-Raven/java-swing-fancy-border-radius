package test;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import raven.fbr.FancyAnimation;
import raven.fbr.FancyBorderRadius;

/**
 *
 * @author RAVEN
 */
public class Panel extends JComponent {

    public Icon getImage() {
        return image;
    }

    public void setImage(Icon image) {
        this.image = image;
        repaint();
    }

    private Animator animator;
    private Animator animatorRotate;
    private float animate;
    private float rotate;
    private FancyAnimation fancyAnimation;
    private Icon image;

    public Panel() {
        image = new ImageIcon(getClass().getResource("/test/img 1.jpg"));
        init();
    }

    private void init() {
        String start = "40% 60% 60% 40% / 60% 30% 70% 40%";
        String end = "40% 60%";
        fancyAnimation = new FancyAnimation(start, end);
        animator = new Animator(5000, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                animate = fraction;
                repaint();
            }
        });
        animator.setResolution(5);
        animator.setRepeatCount(-1);
        animator.setRepeatBehavior(Animator.RepeatBehavior.REVERSE);

        animatorRotate = new Animator(10000, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                rotate = fraction;
            }
        });
        animatorRotate.setResolution(5);
        animatorRotate.setRepeatCount(-1);
        animatorRotate.setRepeatBehavior(Animator.RepeatBehavior.LOOP);
    }

    public void start() {
        if (!animator.isRunning()) {
            animator.start();
            animatorRotate.start();
        }
    }

    public void stop() {
        animator.stop();
        animatorRotate.stop();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        double width = getWidth() * 0.8f;
        double height = getHeight() * 0.8f;
        double x = (getWidth() - width) / 2;
        double y = (getHeight() - height) / 2;
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        FancyBorderRadius border = new FancyBorderRadius(width, height, fancyAnimation.getAnimate(animate));
        AffineTransform tran = g2.getTransform();
        g2.translate(x, y);
        g2.rotate(Math.toRadians(360 * rotate), width / 2, height / 2);
        g2.fill(border.getShape());
        if (image != null) {
            g2.setTransform(tran);
            g2.setComposite(AlphaComposite.SrcIn);
            Rectangle rec = getAutoSize(image);
            g2.drawImage(toImage(image), rec.x, rec.y, rec.width, rec.height, null);
        }
        g2.dispose();
        grphcs.drawImage(img, 0, 0, null);
        super.paintComponent(grphcs);
    }

    private Rectangle getAutoSize(Icon image) {
        int w = getWidth();
        int h = getHeight();
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.max(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }
        int x = (w - width) / 2;
        int y = (h - height) / 2;
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }

    private Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }
}
