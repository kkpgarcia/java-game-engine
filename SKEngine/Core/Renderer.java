package SKEngine.Core;

import SKEngine.Collision.Polygon;
import SKEngine.Collision.Circle;
import SKEngine.Collision.BoundingBox2D;
import SKEngine.Physics.Rigidbody;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * <h2>Renderer</h2>
 * This is a component of a game object. This handls the drawing of an image to a screen.
 * <p>
 * 
 * @see Sprite - SKEngine.Core
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-02-02
 * */
public class Renderer {
    public Sprite sprite;
    public boolean flipped;

    private AffineTransform originalTransform;
    
    /**
     * Constructs an empty renderer.
     * */
    public Renderer() {
        this.sprite = null;
        this.flipped = false;
    }

    /**
     * Constructs a renderer with a sprite.
     * @param Sprite sprite to be rendered
     * */
    public Renderer(Sprite sprite) {
        this.sprite = sprite;
        this.flipped = false;
    }

    /**
     * Render class called by the game object class.
     * @param Transform transform information of the game object
     * @param Graphics2D the screen where this should draw
     * @param BoundingBox2D for debug purposes
     * @param Rigidbody for debug purposes
     * */
    public void render(Transform transform, Graphics2D g, BoundingBox2D bb, Rigidbody rigidbody) {
        originalTransform = g.getTransform();
        g.translate((Screen.width/2), (Screen.height/2));

        if(rigidbody != null) {
            if (rigidbody.shape instanceof Circle)
            {
                Circle c = (Circle)rigidbody.shape;

                float rx = (float)StrictMath.cos( rigidbody.orient ) * c.radius;
                float ry = (float)StrictMath.sin( rigidbody.orient ) * c.radius;

                g.setColor( Color.red );
                g.draw( new Ellipse2D.Float( rigidbody.position.x - c.radius - Camera.x , rigidbody.position.y - c.radius - Camera.y, c.radius * 2, c.radius * 2 ) );
                g.draw( new Line2D.Float( rigidbody.position.x- Camera.x , rigidbody.position.y- Camera.y, rigidbody.position.x- Camera.x  + rx, rigidbody.position.y- Camera.y + ry ) );
            }
            else if (rigidbody.shape instanceof Polygon)
            {
                Polygon p = (Polygon)rigidbody.shape;

                Path2D.Float path = new Path2D.Float();
                for (int i = 0; i < p.vertexCount; i++)
                {
                    Vector2 v = new Vector2( p.vertices[i] );
                    rigidbody.shape.u.muli( v );
                    v.addi( rigidbody.position );

                    if (i == 0)
                    {
                        path.moveTo( v.x- Camera.x , v.y - Camera.y);
                    }
                    else
                    {
                        path.lineTo( v.x- Camera.x, v.y- Camera.y);
                    }
                }
                path.closePath();

                g.setColor( Color.blue );
                g.draw( path );
            }
        }

        if(bb != null) {
            g.setColor (Color.BLACK);
            g.drawRect((int)((bb.min.x + bb.max.x)/2 - 50)-(int)Camera.x, (int)((bb.min.y + bb.max.y)/2 - 50)-(int)Camera.y, 100, 100);
        }

        if(sprite == null || sprite.image == null) {
            reset(g);
            return;
        }

        int width = sprite.width * (int)transform.scale.x;
        int height = sprite.height * (int)transform.scale.y;
        
        int x = (int)((transform.position.x - Camera.x - (width)/2));
        int y = (int)((transform.position.y - Camera.y - (height)/2));
        if(!flipped) {
            g.drawImage(
                sprite.image,
                x,
                y,
                width,
                height,
                null
            );
        } else {
            g.drawImage(
                sprite.image,
                x + width,
                y,
                -width,
                height,
                null
            );
        }
        reset(g);
    }

    public void reset(Graphics2D g) {
        g.setTransform(originalTransform);
    }
}