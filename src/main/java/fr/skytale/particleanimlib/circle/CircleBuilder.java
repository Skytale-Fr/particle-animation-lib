package fr.skytale.particleanimlib.circle;

import fr.skytale.particleanimlib.parent.AAnimation;
import fr.skytale.particleanimlib.parent.AAnimationBuilder;
import org.bukkit.util.Vector;

public class CircleBuilder extends AAnimationBuilder {
    private Circle circle;

    public CircleBuilder() {
        super();
        circle = new Circle(); //TODO Ajouter les valeurs par défaut dans le constructeur
    }

    /*********SETTERS des éléments spécifiques au cercle ***********/
    public void setDirectorVectors(Vector u, Vector v) {
        //Valeurs par défaut
        //TODO N'exécuter que si pas null (les valeurs par défaut sont déjà gérées)
        if(u==null)
            u=new Vector(1, 0, 0);
        if(v==null)
            v=new Vector(0, 1, 0);

        //TODO On rend les vecteurs unitaires => déplacer dans les setters du circle ?
        u.normalize();
        v.normalize();

        circle.setU(u);
        circle.setV(v);
    }

    public void setRadius(double r) {
        //TODO Ajouter un max possible (c'est une idée c'est pas obligé) (throw IllegalArgumentException)
        circle.setRadius(r);
    }

    public void setNbPoints(int n) {
        circle.setNbPoints(n);
    }

    public void setAxis(Vector axis) {
        circle.setAxis(axis);
    }

    public void setStepAngleAlpha(double s){ circle.setStepAngleAlpha(s);}

    @Override
    public AAnimation getAnimation() {
        //TODO Si on doit faire des check de plusieurs attributs à la fois, on les fait ici
        //     (par exemple, pour une sphère, nbPointPerCircle x nbCircle)
        return circle;
    }
}
