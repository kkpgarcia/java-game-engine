import javax.swing.JPanel;

import java.awt.RenderingHints;
import java.awt.Dimension;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Screen extends JPanel {

    public static int width;
    public static int height;
	private ArrayList<GameObject> objects;

	public Screen(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
        this.width = width;
        this.height = height;
		setFocusable(true);
		requestFocus();
		objects = new ArrayList<>();
	}

	public void addDrawingComponents(GameObject dc) {
		if(objects == null) {
			objects = new ArrayList<>();
		}
		objects.add(dc);
	}

	public void update() {
        width = getSize().width;
        height = getSize().height;
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D screen2D = (Graphics2D) g;

        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        screen2D.addRenderingHints(renderingHints);

		for(GameObject obj : objects) {
			obj.render(screen2D);
		}
	}
}