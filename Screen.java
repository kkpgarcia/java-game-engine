import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Screen extends JPanel {

    public static int width;
    public static int height;
	private GameObject[] objects;
	private ReentrantLock lock;

	public Screen(String frameName, int width, int height) {
		JFrame window = new JFrame(frameName);
		this.setPreferredSize(new Dimension(width, height));
        this.width = width;
        this.height = height;
		setFocusable(true);
		requestFocus();
		lock = new ReentrantLock();
		objects = new GameObject[0];

		window.add(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
	}

	public void addDrawingComponents(GameObject obj) {
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

        //RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //screen2D.addRenderingHints(renderingHints);

		for(GameObject obj : objects) {
			if(obj.animator != null)
				obj.animator.updateAnimator();
			
			obj.render(screen2D);
		}
	}
}