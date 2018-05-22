package My3DUniverse;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class ReadABoxCoordinate extends JFrame {

    private SimpleUniverse u = null;

    Appearance ap = new Appearance();
    Material corAzul = new Material(new Color3f(0.3f, 0.6f, 1.0f), new Color3f(0.3f, 0.6f, 1.0f), new Color3f(0.3f, 0.6f, 1.0f), new Color3f(0.3f, 0.6f, 1.0f), 95.0f);

    private float x;
    private float y;
    private float z;
    private double spaceX;
    private double spaceY;
    private double spaceZ;
    private double rotX;
    private double rotY;
    private ArrayList coordinates;

    Scanner in = new Scanner(new File("C:/Users/asus/Documents/NetBeansProjects/Java3D/coordenadas de box.txt"));

    public ReadABoxCoordinate() throws FileNotFoundException {
        Container ctn = getContentPane();
        ctn.setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        ctn.add("Center", canvas);

        ap.setMaterial(corAzul);
        BranchGroup boxTeste = createABox(scanCoorF(), scanCoorF(), scanCoorF(), scanCoorD(), scanCoorD(), scanCoorD(), scanCoorD(), scanCoorD(), ap);

        u = new SimpleUniverse(canvas);
        ViewingPlatform vp = u.getViewingPlatform();
        vp.setNominalViewingTransform();
        vp.setViewPlatformBehavior(aqueleqmexe(canvas));

        u.addBranchGraph(boxTeste);
        u.addBranchGraph(pintafundo());
        u.addBranchGraph(iluminacaoAmb());

        setTitle("Box Coordinate");
        setSize(800, 700);
        setVisible(true);
    }

    public void readCoordinates() throws FileNotFoundException, IOException {
        FileReader file = new FileReader("C:/Users/asus/Documents/NetBeansProjects/Java3D/coordenadas de box.txt");
        BufferedReader reader = new BufferedReader(file);
        String linha = null;
        while ((linha = reader.readLine()) != null) {
            coordinates = new ArrayList<String>();
            coordinates.add(linha);
        }
    }

    public float scanCoorF() {
        if (in.hasNextFloat() == true) {
            return in.nextFloat();
        }
        return 0;
    }

    public double scanCoorD() {
        if (in.hasNextDouble() == true) {
            return in.nextDouble();
        }
        return 0;
    }

    public BranchGroup createABox(float x, float y, float z, double spaceX, double spaceY, double spaceZ, double rotX, double rotY, Appearance ap) {
        BranchGroup material = new BranchGroup();
        TransformGroup molde = new TransformGroup();
        molde.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        material.addChild(molde);
        molde.addChild(new Box(x, y, z, ap));

        Transform3D fixacao = new Transform3D();
        Transform3D eixoX = new Transform3D();
        Transform3D eixoY = new Transform3D();

        fixacao.setTranslation(new Vector3d(spaceX, spaceY, spaceZ));//fixa o objeto criado pelo molde no determinado vetor
        eixoX.rotX(rotX);
        eixoY.rotY(rotY);
        fixacao.mul(eixoX);
        fixacao.mul(eixoY);

        molde.setTransform(fixacao);

        material.compile();
        return material;
    }

    public BranchGroup pintafundo() {
        BranchGroup galho = new BranchGroup();
        BoundingSphere boundground = new BoundingSphere(new Point3d(0, 0, 0), 100.0); //cria um bound para o background
        Color3f bgcolor = new Color3f(1.0f, 1.0f, 0.0f);//escolhe a cor
        Background bg = new Background(bgcolor);
        bg.setApplicationBounds(boundground);
        galho.addChild(bg);
        return galho;
    }

    public OrbitBehavior aqueleqmexe(Canvas3D canvas) {
        OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
        BoundingSphere cola = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        orbit.setSchedulingBounds(cola);
        return orbit;
    }

    public Appearance skinFree(String skin, Appearance p, Material c) {
        java.net.URL textImage = null;
        try {
            textImage = new java.net.URL((skin));
        } catch (java.net.MalformedURLException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        TextureLoader loader = new TextureLoader(textImage, this);
        p.setTexture(loader.getTexture());
        p.setMaterial(c);
        return p;
    }

    public BranchGroup iluminacaoAmb() {
        Color3f corluz = new Color3f(0.9f, 0.9f, 0.9f);
        Vector3f direcaoluz = new Vector3f(-1.0f, -1.0f, -1.0f);
        Color3f corAmb = new Color3f(0.2f, 0.2f, 0.2f);

        BranchGroup galho = new BranchGroup();
        BoundingSphere grudando = new BoundingSphere();

        AmbientLight luzAmb = new AmbientLight(corAmb);
        luzAmb.setInfluencingBounds(grudando);
        DirectionalLight luzDir = new DirectionalLight(corluz, direcaoluz);
        luzDir.setInfluencingBounds(grudando);

        galho.addChild(luzAmb);
        galho.addChild(luzDir);

        return galho;
    }

//    public static void main(String[] args) {
//        try {
//            ReadABoxCoordinate c = new ReadABoxCoordinate();
//        } catch (FileNotFoundException ex) {
//            System.out.println("Texto não encontrado" + ex);
//        }
//    }
}
