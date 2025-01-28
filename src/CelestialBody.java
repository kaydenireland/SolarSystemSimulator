public class CelestialBody {

    public String name;
    public float mass;
    public float radius;
    public Vector2 initialVelocity;
    public Vector2 currentVelocity;
    public Vector2 position;


    public CelestialBody(String name, float mass, float radius, Vector2 initialVelocity, Vector2 initialPosition){
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.initialVelocity = initialVelocity;
        this.position = initialPosition;
    }

    public CelestialBody(String name, float mass, float radius, float xVelo, float yVelo, float xPos, float yPos){
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.initialVelocity = new Vector2(xVelo, yVelo);
        this.position = new Vector2(xPos, yPos);
    }

    public void awake(){
        this.currentVelocity = initialVelocity;
    }

    public void updateVelocity(CelestialBody[] allBodies, float time){
        for(CelestialBody body : allBodies){
            if(body != this){
                float sqrDist = (float) Math.pow(this.position.distance(body.position), 2);
                Vector2 forceDir = new Vector2(
                        Math.abs(this.position.x - body.position.x),
                        Math.abs(this.position.y - body.position.y)
                );
                Vector2 force = new Vector2(
                        (forceDir.x * Universe.GRAVITATIONAL_CONSTANT * this.mass * body.mass) / sqrDist,
                        (forceDir.y * Universe.GRAVITATIONAL_CONSTANT * this.mass * body.mass) / sqrDist
                );
                Vector2 acceleration = new Vector2(
                        force.x / mass,
                        force.y / mass
                );
                currentVelocity.x = acceleration.x * time;
                currentVelocity.y = acceleration.y;
            }
        }
    }

    public void updatePosition(float time){
        this.position.x += currentVelocity.x * time;
        this.position.y += currentVelocity.y * time;
    }

    public String velocityToString(){
        return name + "'s Position: " + initialVelocity.toString();
    }

    public String positionToString(){
        return name + "'s Position: " + position.toString();
    }



}
