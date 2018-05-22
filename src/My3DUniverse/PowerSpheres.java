package My3DUniverse;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.*;
import javax.swing.JFrame;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 * Devido a zueira, atualmente esta classe cria Esferas, ou seja, tem nome de
 * quadrado com formato de bola
 *
 * @author Rodolfo Berlezi
 */
public class PowerSpheres extends JFrame {

    private SimpleUniverse u = null;
    private final Transform3D rota1 = new Transform3D();
    private final Transform3D rota2 = new Transform3D();

    Appearance ap = new Appearance();
    Appearance op = new Appearance();
    Appearance ip = new Appearance();

    Material corVermelha = new Material(new Color3f(1.0f, 0.6f, 0.3f), new Color3f(1.0f, 0.6f, 0.3f), new Color3f(1.0f, 0.6f, 0.3f), new Color3f(1.0f, 0.6f, 0.3f), 105.0f);
    Material corAzul = new Material(new Color3f(0.3f, 0.6f, 1.0f), new Color3f(0.3f, 0.6f, 1.0f), new Color3f(0.3f, 0.6f, 1.0f), new Color3f(0.3f, 0.6f, 1.0f), 95.0f);
    Material corVerde = new Material(new Color3f(0.0f, 0.3f, 1.0f), new Color3f(0.0f, 0.3f, 1.0f), new Color3f(0.0f, 0.3f, 1.0f), new Color3f(0.0f, 0.3f, 1.0f), 85.0f);
    //color3f = c1,c2,c3 onde c1 é red color, c2 é green color e c3 é blue color

    public PowerSpheres() {
        Container ctn = getContentPane();
        ctn.setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        ctn.add("Center", canvas);

        ap.setMaterial(corAzul);
        BranchGroup LargeOne = createACube(0.3f, 0.2, 0.1, 0.3, 0.4, 0.3, ap);
        op.setMaterial(corVermelha);
        BranchGroup TinyOne = createACube(0.3f, -0.5, -0.2, 0.0, 0.3, 0.4, op);
        ip.setMaterial(corVerde);
        BranchGroup HideOne = createACube(0.3f, -0.2, 0.3, -0.6, 0.5, 0.5, ip);
        //BranchGroup LargeOne = createACube(0.3f, 0.2, 0.1, 0.3, 0.4, 0.3, skinFree("https://blendertotal.files.wordpress.com/2008/04/hairrender.jpg?w=497", ap, corAzul));
        //BranchGroup HideOne = createACube(0.3f, -0.2, 0.3, -0.6, 0.5, 0.5, skinFree("http://ensinarevt.com/conteudos/textura/imagens/naturais/Doghair.jpg", op, corVerde));
        //BranchGroup TinyOne = createACube(0.3f, -0.5, -0.2, 0.0, 0.3, 0.4, skinFree("http://agrega.educacion.es/galeriaimg/e7/es_20071227_1_5030620/es_20071227_1_5030620_captured.jpg", ip, corVermelha));

        u = new SimpleUniverse(canvas);
        ViewingPlatform vp = u.getViewingPlatform();
        vp.setNominalViewingTransform();//desloca a tela para trás
        vp.setViewPlatformBehavior(aqueleqmexe(canvas));

        u.addBranchGraph(LargeOne);
        u.addBranchGraph(TinyOne);
        u.addBranchGraph(HideOne);
        u.addBranchGraph(pintafundo());
        u.addBranchGraph(iluminacaoAmb());
        //u.addBranchGraph(iluminacaoPoint());
        //u.addBranchGraph(iluminacaoSpot());
        u.addBranchGraph(writeThis("Eu S2 Computação Gráfica <3", 0.0, 0.0, -5.0, 0.1, 0.2, op));

        setTitle("Sphere's Universe");
        setSize(800, 700);
        setVisible(true);
    }

    public BranchGroup createACube(float cubeSize, double spaceX, double spaceY, double spaceZ, double rotX, double rotY, Appearance ap) {
        BranchGroup material = new BranchGroup();//material para a criação da cena objeto
        TransformGroup molde = new TransformGroup();//molde indefinido da cena  
        molde.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); //seta capability para behaviors trabalharem
        material.addChild(molde);//coloca o molde por cima do material
        molde.addChild(new Sphere(cubeSize, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 14, ap));//molde.addChild(new ColorCube(cubeSize));//define o molde

        Transform3D fixacao = new Transform3D();
        Transform3D eixoX = new Transform3D();
        Transform3D eixoY = new Transform3D();

        fixacao.setTranslation(new Vector3d(spaceX, spaceY, spaceZ));//fixa o objeto criado pelo molde no determinado vetor
        eixoX.rotX(rotX);
        eixoY.rotY(rotY);
        fixacao.mul(eixoX);
        fixacao.mul(eixoY);

        molde.setTransform(fixacao);
        molde.addChild(fazgirar(molde, fixacao));
        //molde.addChild(morfar(molde));

        material.compile();
        return material;
    }

    public RotationInterpolator fazgirar(TransformGroup molde, Transform3D fixacao) {
        Alpha alpha = new Alpha(-1, 5000);//onde escolhe o número de ciclos para o behavior
        RotationInterpolator aqueleqgira = new RotationInterpolator(alpha, molde, fixacao, 0.0f, (float) Math.PI * 2.0f);//será um behavior de rotação
        //aqueleqgira.setTransformAxis(rotacao());
        BoundingSphere grudando = new BoundingSphere();//serve para colar o behaivor no molde, sem ela, não gira
        aqueleqgira.setSchedulingBounds(grudando);
        return aqueleqgira;
    }

    public Transform3D rotacao() { //seta o translado de rotação
        rota1.rotX(Math.PI / 4.0d);
        rota2.rotY(Math.PI / 3.0d);
        rota1.mul(rota2);
        return rota1;
    }

    public BranchGroup pintafundo() {
        BranchGroup galho = new BranchGroup();
        BoundingSphere boundground = new BoundingSphere(new Point3d(0, 0, 0), 100.0); //cria um bound para o background
        Color3f bgcolor = new Color3f(0.0f, 1.0f, 1.0f);//escolhe a cor
        Background bg = new Background(bgcolor);
        bg.setApplicationBounds(boundground);
        galho.addChild(bg);
        return galho;
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

    public BranchGroup iluminacaoPoint() {
        Color3f corluz = new Color3f(0.9f, 0.9f, 0.9f);
        Point3f posicaoluz = new Point3f(0.0f, 2.0f, 0.0f);
        Point3f atenuacaoluz = new Point3f(0.2f, 0.2f, 0.2f);
        Color3f corAmb = new Color3f(0.2f, 0.2f, 0.2f);

        BranchGroup galho = new BranchGroup();
        BoundingSphere grudando = new BoundingSphere();

        AmbientLight luzAmb = new AmbientLight(corAmb);
        luzAmb.setInfluencingBounds(grudando);
        PointLight luzPoint = new PointLight(corluz, posicaoluz, atenuacaoluz);
        luzPoint.setInfluencingBounds(grudando);

        galho.addChild(luzAmb);
        galho.addChild(luzPoint);

        return galho;
    }

    public BranchGroup iluminacaoSpot() {
        Color3f corluz = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f direcaoluz = new Vector3f(0.0f, -1.0f, 0.0f);
        Point3f posicaoluz = new Point3f(0.0f, 2.0f, 0.0f);
        Point3f atenuacaoluz = new Point3f(0.1f, 0.1f, 0.1f);
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

    public OrbitBehavior aqueleqmexe(Canvas3D canvas) {
        OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
        BoundingSphere cola = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        orbit.setSchedulingBounds(cola);
        return orbit;
    }

    public BranchGroup writeThis(String frase, double spaceX, double spaceY, double spaceZ, double rotX, double rotY, Appearance p) {
        BranchGroup material = new BranchGroup();
        TransformGroup molde = new TransformGroup();
        Font3D f = new Font3D(new Font("Helvetica", Font.ROMAN_BASELINE, 1), new FontExtrusion());
        Text3D t = new Text3D(f, frase);
        t.setAlignment(Text3D.ALIGN_CENTER);
        Shape3D s = new Shape3D();
        s.setGeometry(t);
        s.setAppearance(p);

        Transform3D fixacao = new Transform3D();
        Transform3D eixoX = new Transform3D();
        Transform3D eixoY = new Transform3D();

        fixacao.setTranslation(new Vector3d(spaceX, spaceY, spaceZ));//fixa o objeto criado pelo molde no determinado vetor
        eixoX.rotX(rotX);
        eixoY.rotY(rotY);
        fixacao.mul(eixoX);
        fixacao.mul(eixoY);

        molde.setTransform(fixacao);

        molde.addChild(s);
        material.addChild(molde);
        return material;
    }

//compila, mas não funciona ainda
//    public Morph morfar(TransformGroup molde) {
//        GeometryArray[] emcubada = new GeometryArray[1];
//
//        IndexedQuadArray indexedCube = new IndexedQuadArray(8, IndexedQuadArray.COORDINATES | IndexedQuadArray.NORMALS, 24);
//        Point3f[] cubeCoordinates = {new Point3f(1.0f, 1.0f, 1.0f),
//            new Point3f(-1.0f, 1.0f, 1.0f),
//            new Point3f(-1.0f, -1.0f, 1.0f),
//            new Point3f(1.0f, -1.0f, 1.0f), new Point3f(1.0f, 1.0f, -1.0f),
//            new Point3f(-1.0f, 1.0f, -1.0f),
//            new Point3f(-1.0f, -1.0f, -1.0f),
//            new Point3f(1.0f, -1.0f, -1.0f)};
//        Vector3f[] cubeNormals = {new Vector3f(0.0f, 0.0f, 1.0f),
//            new Vector3f(0.0f, 0.0f, -1.0f),
//            new Vector3f(1.0f, 0.0f, 0.0f),
//            new Vector3f(-1.0f, 0.0f, 0.0f),
//            new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f(0.0f, -1.0f, 0.0f)};
//        int cubeCoordIndices[] = {0, 1, 2, 3, 7, 6, 5, 4, 0, 3, 7, 4, 5, 6, 2, 1, 0, 4, 5, 1, 6, 7, 3, 2};
//        int cubeNormalIndices[] = {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5};
//        indexedCube.setCoordinates(0, cubeCoordinates);
//        indexedCube.setNormals(0, cubeNormals);
//        indexedCube.setCoordinateIndices(0, cubeCoordIndices);
//        indexedCube.setNormalIndices(0, cubeNormalIndices);
//
//        emcubada[1] = indexedCube;
//
//        double[] weights = {1.0, 0.0};
//        Alpha morphAlpha = new Alpha(-1, 5000);
//        Morph morfar = new Morph(emcubada);
//        morfar.setWeights(weights);
//        morfar.setCapability(Morph.ALLOW_WEIGHTS_WRITE);
//        SimpleMorphBehaviour morfe = new SimpleMorphBehaviour(morphAlpha, molde, morfar);
//        BoundingSphere bounds = new BoundingSphere();
//        morfe.setSchedulingBounds(bounds);
//        return morfar;
//    }
    
    public static void main(String[] args) {
        PowerSpheres ps = new PowerSpheres();
    }

}
