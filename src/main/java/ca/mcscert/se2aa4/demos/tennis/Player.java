package ca.mcscert.se2aa4.demos.tennis;

public class Player {
    int strength;
    int point = 0;
    int set = 0;
    String name;
    Player(String name, int strength) {
        this.name = name;
        this.strength = strength;
    }

    public void scorePoint() {
            this.point++;
    }

    public void scoreSet() {
        if (this.set != 7) {
            this.set++;
        }
    }

    public void setPoint(int point) {
        this.point = point;
    }

}
