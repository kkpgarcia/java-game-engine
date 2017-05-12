import javax.swing.JPanel;

import java.awt.RenderingHints;
import java.awt.Dimension;

import java.util.Arrays;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.concurrent.locks.ReentrantLock;

public class Screen extends JPanel {

    public static int width;
    public static int height;
	private GameObject[] objects;
	private ReentrantLock lock;

	public Screen(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
        this.width = width;
        this.height = height;
		setFocusable(true);
		requestFocus();
		lock = new ReentrantLock();
		objects = new GameObject[0];
		//objects = new ArrayList<>();

	}

	public void addDrawingComponents(GameObject obj) {
		/*if(objects == null) {
			objects = new ArrayList<>();
		}
		objects.add(dc);*/
		lock.lock();

		try {
            GameObject[] elements = objects;
            int length = elements.length;
            GameObject[] newElements = Arrays.copyOf(elements, length+1);
            newElements[length] = obj;
            objects = newElements;
        } finally {
            lock.unlock();
        }
	}

	public void removeDrawingComponents(GameObject obj) {
		lock.lock();
        
        try {
            GameObject[] elements = objects;
            ArrayList<GameObject> newElements = new ArrayList<GameObject>(Arrays.asList(elements));
            newElements.remove(obj);
            objects = newElements.toArray(new GameObject[elements.length-1]);
        } finally {
            lock.unlock();
        }
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