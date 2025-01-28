import java.awt.*;

public class CelestialBody {

    public String name;
    public float mass;
    public int radius;
    public Color color;
    public Vector2 initialVelocity;
    public Vector2 currentVelocity;
    public Vector2 initialPosition;
    public Vector2 currentPosition;


    public CelestialBody(String name, float mass, int radius, Color color, Vector2 initialVelocity, Vector2 initialPosition){
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.color = color;
        this.initialVelocity = initialVelocity;
        this.initialPosition = initialPosition;
    }

    public CelestialBody(String name, float mass, int radius, Color color, float xVelo, float yVelo, int xPos, int yPos){
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.color = color;
        this.initialVelocity = new Vector2(xVelo, yVelo);
        this.initialPosition = new Vector2(xPos, yPos);
    }

    public void awake(){
        this.currentVelocity = new Vector2(initialVelocity);
        this.currentPosition = new Vector2(initialPosition);
    }

    public void updateVelocity(CelestialBody[] allBodies, float time){
        for(CelestialBody body : allBodies){
            if(body != this){
                float sqrDist = (float) Math.pow(this.currentPosition.distance(body.currentPosition), 2);
                Vector2 forceDir = new Vector2(
                        (body.currentPosition.x - this.currentPosition.x),
                        (body.currentPosition.y - this.currentPosition.y)
                );
                Vector2 force = new Vector2(
                        (forceDir.x * Universe.GRAVITATIONAL_CONSTANT * this.mass * body.mass) / sqrDist,
                        (forceDir.y * Universe.GRAVITATIONAL_CONSTANT * this.mass * body.mass) / sqrDist
                );
                Vector2 acceleration = new Vector2(
                        force.x / mass,
                        force.y / mass
                );
                currentVelocity.x += acceleration.x * time;
                currentVelocity.y += acceleration.y * time;
            }
        }
    }

    public void updatePosition(float time){
        this.currentPosition.x += currentVelocity.x * time;
        this.currentPosition.y += currentVelocity.y * time;
    }

    public String initialVelocityToString(){
        return name + "'s Initial Velocity: " + initialVelocity.toString();
    }

    public String currentVelocityToString(){
        return name + "'s Current Velocity: " + initialVelocity.toString();
    }

    public String positionToString(){
        return name + "'s Position: " + currentPosition.toString();
    }



}
