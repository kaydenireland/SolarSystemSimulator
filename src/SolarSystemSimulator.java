import javax.swing.*;
import java.awt.*;

public class SolarSystemSimulator extends JPanel {

    // Frame Dimensions
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private int time = 0;

    private CelestialBody[] bodies = new CelestialBody[2];


    public SolarSystemSimulator(){
        setBackground(Color.BLACK);


        // Add Celestial Bodies
        CelestialBody planet = new CelestialBody(
                "Earth",
                20, 10,
                Color.BLUE,
                10, 10,
                300, 300
        );

        CelestialBody moon = new CelestialBody(
                "Luna",
                5, 3,
                Color.WHITE,
                -500, 0,
                325, 300
        );


        bodies[0] = planet;
        bodies[1] = moon;


        for(CelestialBody body : bodies){
            body.awake();
        }


        // Animation Timer
        Timer timer = new Timer(10, e -> {
            for(CelestialBody body : bodies){
               body.updateVelocity(bodies, time);
               body.updatePosition(time);
            }
            repaint();
            time++;
            System.out.println(time);
        });
        timer.start();
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw Earth
        for(CelestialBody body : bodies){
            draw(body, g2d);
        }

    }

    public static void draw(CelestialBody body, Graphics2D g2d){
        g2d.setColor(body.color);
        g2d.fillOval(
                (int) body.position.x - body.radius,
                (int) body.position.y + body.radius,
                body.radius * 2,
                body.radius * 2
        );
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Solar System Simulator");
        SolarSystemSimulator simulator = new SolarSystemSimulator();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(simulator);
        frame.setVisible(true);
    }

}
