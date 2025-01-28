import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SolarSystemSimulator extends JPanel {

    // Frame Dimensions
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private float time = 0;
    private boolean runSim = false;

    private final CelestialBody[] bodies = new CelestialBody[2];

    private CelestialBody selectedBody;


    public SolarSystemSimulator(){
        setBackground(Color.BLACK);


        // Add Celestial Bodies
        CelestialBody planet = new CelestialBody(
                "Earth",
                82, 10,
                Color.BLUE,
                0, 0,
                300, 300,
                BodyType.PLANET
        );

        CelestialBody moon = new CelestialBody(
                "Luna",
                1, 3,
                Color.WHITE,
                0, -3,
                500, 350,
                BodyType.MOON
        );




        bodies[0] = planet;
        bodies[1] = moon;



        for(CelestialBody body : bodies){
            body.awake();
        }

        // Mouse listener for selecting planets
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (CelestialBody body : bodies) {
                    if (body.isClicked(e.getX(), e.getY())) {
                        selectedBody = body;
                        editBodyWindow(body);
                        repaint();
                        return;
                    }
                }
                selectedBody = null;
                repaint();
            }
        });


        // Animation Timer
        Timer timer = new Timer(20, e -> {
            if(runSim) {
                for (CelestialBody body : bodies) {
                    body.updateVelocity(bodies, time);
                }
                for (CelestialBody body : bodies) {
                    body.updatePosition(time);
                }
                repaint();
                time += Universe.TIMESTEP;
                //System.out.println(time);
            }
        });
        timer.start();
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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

    private void editBodyWindow(CelestialBody body) {
        JFrame bodyFrame = new JFrame("Edit " + body.name);
        bodyFrame.setSize(300, 150);
        bodyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel sizeLabel = new JLabel("Adjust size of " + body.name + ":");
        JSlider sizeSlider = new JSlider(1, 50, body.radius);

        JLabel massLabel = new JLabel("Adjust size of " + body.name + ":");
        JSlider massSlider = new JSlider(1, 500, (int) body.mass);

        sizeSlider.addChangeListener(e -> {
            body.radius = sizeSlider.getValue();
            repaint();
        });

        massSlider.addChangeListener(e -> {
            body.mass = massSlider.getValue();
            repaint();
        });

        panel.add(sizeLabel, BorderLayout.NORTH);
        panel.add(sizeSlider, BorderLayout.NORTH);

        panel.add(massLabel, BorderLayout.CENTER);
        panel.add(massSlider, BorderLayout.CENTER);

        bodyFrame.add(panel);
        bodyFrame.setVisible(true);
    }


    private void resetSimulator(){
        runSim = false;
        time = 0;
        for(CelestialBody body : bodies){
            body.awake();
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
