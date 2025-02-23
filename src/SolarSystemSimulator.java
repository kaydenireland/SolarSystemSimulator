import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SolarSystemSimulator extends JPanel {

    // Frame Dimensions
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private float time = 0;
    private boolean runSim = false;
    private boolean started = false;

    private final CelestialBody[] bodies = new CelestialBody[2];


    public SolarSystemSimulator(){
        setBackground(Color.BLACK);


        // Add Celestial Bodies
        CelestialBody planet = new CelestialBody(
                "Earth",
                250, 10,
                Color.BLUE,
                0, 0,
                300, 300
        );

        CelestialBody moon = new CelestialBody(
                "Luna",
                10, 3,
                Color.WHITE,
                0, -5,
                350, 300
        );




        bodies[0] = planet;
        bodies[1] = moon;


        for(CelestialBody body : bodies){
            body.awake();
        }


        // Animation Timer
        Timer timer = new Timer(100, e -> {
            if(runSim) {
                for (CelestialBody body : bodies) {
                    body.updateVelocity(bodies, time);
                }
                for (CelestialBody body : bodies) {
                    body.updatePosition(time);
                }
                repaint();
                time += Universe.TIMESTEP;
                System.out.println(time);
            }
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
                (int) body.currentPosition.x - body.radius,
                (int) body.currentPosition.y + body.radius,
                body.radius * 2,
                body.radius * 2
        );
    }

    private void resetSimulator(){
        runSim = false;
        started = false;
        time = 0;
        for(CelestialBody body : bodies){
            System.out.println(body.currentPosition);
            System.out.println(body.initialPosition);
            body.awake();
            System.out.println(body.currentPosition);
        }
        repaint();
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Solar System Simulator");
        SolarSystemSimulator simulator = new SolarSystemSimulator();

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(100, 40));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulator.runSim = true;
                simulator.started = true;
            }
        });
        JButton pauseButton = new JButton("Pause");
        pauseButton.setPreferredSize(new Dimension(100, 40));
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulator.runSim = false;
            }
        });
        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(100, 40));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulator.resetSimulator();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align button to the right
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);

        frame.setLayout(new BorderLayout());
        frame.add(simulator, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

}
