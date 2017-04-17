public class Mat2x2 {
    public float m00;
    public float m01;
    public float m10;
    public float m11;

    /*
    public Mat2x2() {

    }

    public Mat2x2(float radians) {
        set(radians);
    }

    public Mat2(float m00, float m01, float m10, float m11) {
        set(m00, m01, m10, m11);
    }

    public void set(float radians) {
        float c = (float)Math.cos(radians);
        float s = (float)Math.sin(radians);

        this.m00 = c;
        this.m01 = -s;
        this.m10 = s;
        this.m11 = c;
    }

    public void set(float m00, float m01, float m10, float m11) {
        this.m00 = m00;
        this.m01 = m01;
        this.m10 = m10;
        this.m11 = m11;
    }

    public void set(Mat2x2 mat) {
        this.m00 = mat.m00;
        this.m01 = mat.m01;
        this.m10 = mat.m10;
        this.m11 = mat.m11;
    }

    public void absolute() {
        absolute(this);
    }

    public Mat2x2 absolute() {
        return absolute(new Mat2x2());
    }

    public Mat2x2 absolute(Mat2x2 out) {
        out.m00 = Math.abs(m00);
        out.m01 = Math.abs(m01);
        out.m10 = Math.abs(m10);
        out.m11 = Math.abs(m11);
        return out;
    }

    public Vector2 getX(Vector2 out) {
        out.x = m00;
        out.y = m10;
        return out;
    }

    public Vector2 getX() {
        return getX(new Vector2());
    }

    public Vector2 getY(Vector2 out) {
        out.x = m01;
        out.y = m11;
    }

    public Vector2 getY() {
        return getY(new Vector2());
    }   

    public void transposeSelf() {
        float t = m01;
        m01 = m10;
        m10 = t;
    }

    public Mat2x2 transpose(Mat2 out) {
        out.m00 = m00;
        out.m01 = m10;
        out.m10 = m01;
        out.m11 = m11;
        return out;
    }

    public Mat2x2 transpose() {
        return transpose(new Mat2x2());
    }

*/
}