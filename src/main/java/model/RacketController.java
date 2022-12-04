package model;

public interface RacketController {
    enum State {GOING_UP, IDLE, GOING_DOWN, GOING_RIGHT, GOING_LEFT, PARALYSED}
    //STRENGHT = balle plus puissante
    //STRENGHTACTIVATED = test si la raquette est actuellement plus puissante
    //ELECTRICAL = paralyse le joueur adverse dès que la balle touche la ballPower
    //WIND = fait rebondir la balle dans le vent
    //INVISIBLE = rend invisible la balle
    //BIGGER = agrandit la raquette du joueur dès que la balle touche la ballPower
    //SMALLER = rapetissit la raquette du joueur dès que la balle touche la ballPower
    //SLOWER = diminue la vitesse de la raquette du joueur dès que la balle touche la ballPower
    //FASTER = augmente la vitesse de la raquette du joueur dès que la balle touche la ballPower
    //IDLE = symbolise le fait de n'avoir aucun pouvoirs
    enum Power {STRENGHT, STRENGHTACTIVATED, ELECTRICAL, WIND, INVISIBLE, BIGGER, SMALLER, SLOWER, FASTER, IDLE}
    State getState();
    Power getPower();
    void setState(State s);
    void setPower(Power p);
}
