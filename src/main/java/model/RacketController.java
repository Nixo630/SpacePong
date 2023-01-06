package model;

public interface RacketController {
    enum State {GOING_UP, IDLE, GOING_DOWN, GOING_RIGHT, GOING_LEFT, PARALYSED}
    //STRENGHT = balle plus puissante
    //STRENGHTACTIVATE = état quand la raquette va renvoyer une balle plus puissante au prochain coup
    //STRENGHTACTIVATED = état de la raquette qui vient de renvoyer une balle plus puissante au dernier coup
    //ELECTRICAL = paralyse le joueur adverse dès que la balle touche la ballPower
    //WIND = fait rebondir la balle dans le vent
    //INVISIBLE = rend invisible la balle
    //BIGGER = agrandit la raquette du joueur dès que la balle touche la ballPower
    //SMALLER = rapetissit la raquette du joueur dès que la balle touche la ballPower
    //SLOWER = diminue la vitesse de la raquette du joueur dès que la balle touche la ballPower
    //FASTER = augmente la vitesse de la raquette du joueur dès que la balle touche la ballPower
    //IDLE = symbolise le fait de n'avoir aucun pouvoirs
    enum Power {STRENGHT, STRENGHTACTIVATE, STRENGHTACTIVATED, ELECTRICAL, WIND, INVISIBLE, BIGGER, SMALLER, SLOWER, FASTER, IDLE}
    State getState();
    Power getPower();
    void setState(State s);
    void setPower(Power p);
}
