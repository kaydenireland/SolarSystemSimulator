
/*
    Two-Dimensional Vector Class

    @author Kayden Ireland
    @version 1.0


 */

public class Vector2 {

    public float x;
    public float y;


    public Vector2(){
        this.x = 0;
        this.y = 0;
    }

    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2(float[] arr){
        this.x = arr[0];
        this.y = arr[1];
    }

    public Vector2(Vector2 vect){
        this.x = vect.x;
        this.y = vect.y;
    }

    public float distance(Vector2 other){ // assume all values are positive
        float dist;
        float a = Math.abs(this.x - other.x);
        float b = Math.abs(this.y - other.y);
        a = (float) Math.pow(a, 2);
        b = (float) Math.pow(b, 2f);
        dist = a + b;
        return (float) Math.sqrt(dist);

    }

    @Override
    public String toString(){
        return "<" + this.x + ", " + this.y + ">";
    }


}
