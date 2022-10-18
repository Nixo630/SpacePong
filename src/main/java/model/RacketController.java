package model;

public interface RacketController {
    enum State { GOING_UP, IDLE, GOING_DOWN, GOING_RIGHT, GOING_LEFT }
    State getState();
}
