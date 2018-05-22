package My3DUniverse;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 * Imaginação... Imaginaçãoo... Imaginaçãooo!
 *
 * @author Rodolfo Berlezi
 */
public class FigureOutImagination extends JFrame {

    private SimpleUniverse u = null;

    Appearance ap = new Appearance();
    Appearance ep = new Appearance();
    Appearance ip = new Appearance();
    Appearance op = new Appearance();

    Material corAzul = new Material(new Color3f(0.3f, 0.6f, 1.0f), new Color3f(0.3f, 0.6f, 1.0f), new Color3f(0.3f, 0.6f, 1.0f), new Color3f(0.3f, 0.6f, 1.0f), 95.0f);

    public FigureOutImagination() {
        Container ctn = getContentPane();
        ctn.setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        ctn.add("Center", canvas);

        BranchGroup coneBracoD = createACone(4.0, 4.0, -5.0, 5.0, 4.0, skinFree("http://rafaelgobara.com/img/portfolio/2.jpg", ap, corAzul));
        BranchGroup coneBracoE = createACone(-4.0, 4.0, -5.0, -5.0, -4.0, skinFree("http://rafaelgobara.com/img/portfolio/2.jpg", ep, corAzul));
        BranchGroup conePernaD = createACone(4.0, -5.0, 5.0, 5.0, 4.0, skinFree("http://rafaelgobara.com/img/portfolio/2.jpg", ip, corAzul));
        BranchGroup conePernaE = createACone(-4.0, -5.0, 5.0, -5.0, -4.0, skinFree("http://rafaelgobara.com/img/portfolio/2.jpg", op, corAzul));
        //BranchGroup cuboCorpo = createACube(4);
        //BranchGroup cilindroBody = createACylinder();
        BranchGroup boxVongola = createABox(2, 2, 2, ap);

        u = new SimpleUniverse(canvas);
        ViewingPlatform vp = u.getViewingPlatform();
        vp.setNominalViewingTransform();
        vp.setViewPlatformBehavior(aqueleqmexe(canvas));

        //u.addBranchGraph(cuboCorpo);
        //u.addBranchGraph(cilindroBody);
        u.addBranchGraph(boxVongola);
        u.addBranchGraph(coneBracoD);
        u.addBranchGraph(coneBracoE);
        u.addBranchGraph(conePernaD);
        u.addBranchGraph(conePernaE);
        u.addBranchGraph(iluminacaoSpot());
        u.addBranchGraph(pintafundo());

        setTitle("Cone Robo");
        setSize(800, 700);
        setVisible(true);
    }

    public BranchGroup createACone(double spaceX, double spaceY, double spaceZ, double rotX, double rotY, Appearance p) {
        BranchGroup material = new BranchGroup();
        TransformGroup molde = new TransformGroup();
        molde.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        material.addChild(molde);
        molde.addChild(new Cone(3, 8, p));

        Transform3D fixacao = new Transform3D();
        Transform3D eixoX = new Transform3D();
        Transform3D eixoY = new Transform3D();

        fixacao.setTranslation(new Vector3d(spaceX, spaceY, spaceZ));//fixa o objeto criado pelo molde no determinado vetor posição do universo
        eixoX.rotX(rotX);//angulo do objeto
        eixoY.rotY(rotY);
        fixacao.mul(eixoX);
        fixacao.mul(eixoY);

        molde.setTransform(fixacao);
        //molde.addChild(fazgirar(molde, fixacao));

        material.compile();
        return material;
    }

    public BranchGroup createACube(double cubeSize) {
        BranchGroup material = new BranchGroup();
        TransformGroup molde = new TransformGroup();
        molde.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        material.addChild(molde);
        molde.addChild(new ColorCube(cubeSize));
        //molde.addChild(new Box(2));

        material.compile();
        return material;
    }

    public BranchGroup createACylinder() {
        BranchGroup material = new BranchGroup();
        TransformGroup molde = new TransformGroup();
        molde.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        material.addChild(molde);
        molde.addChild(new Cylinder(2, 2));

        material.compile();
        return material;
    }

    public BranchGroup createABox(float x, float y, float z, Appearance ap) {
        BranchGroup material = new BranchGroup();
        TransformGroup molde = new TransformGroup();
        molde.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        material.addChild(molde);
        molde.addChild(new Box(x, y, z, ap));

        material.compile();
        return material;
    }

    public RotationInterpolator fazgirar(TransformGroup molde, Transform3D fixacao) {
        Alpha alpha = new Alpha(-1, 5000);//onde escolhe o número de ciclos para o behavior
        RotationInterpolator aqueleqgira = new RotationInterpolator(alpha, molde, fixacao, 0.0f, (float) Math.PI * 2.0f);//será um behavior de rotação
        BoundingSphere grudando = new BoundingSphere();//serve para colar o behaivor no molde, sem ela, não gira
        aqueleqgira.setSchedulingBounds(grudando);
        return aqueleqgira;
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

    public BranchGroup iluminacaoSpot() {
        Color3f corluz = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f direcaoluz = new Vector3f(0.2f, -1.0f, 0.2f);
        Point3f posicaoluz = new Point3f(0.0f, 0.0f, 0.0f);
        Point3f atenuacaoluz = new Point3f(0.5f, 0.5f, 0.5f);
        Color3f corAmb = new Color3f(0.2f, 0.2f, 0.2f);

        BranchGroup galho = new BranchGroup();
        BoundingSphere grudando = new BoundingSphere();

        AmbientLight luzAmb = new AmbientLight(corAmb);
        luzAmb.setInfluencingBounds(grudando);
        SpotLight luzSpot = new SpotLight(corluz, posicaoluz, atenuacaoluz, direcaoluz, (float) Math.toRadians(12), 60.0f);
        luzSpot.setInfluencingBounds(grudando);

        galho.addChild(luzAmb);
        galho.addChild(luzSpot);

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

//    public static void main(String[] args) {
//        FigureOutImagination foi = new FigureOutImagination();
//    }
}
