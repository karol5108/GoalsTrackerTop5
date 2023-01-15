public class Player {
    String name, lastname, club, league;
    protected int numbersOfGoals, matchesPlayed;
    double AvrgPerMatch;
    Player(){

    }

    Player(String name, String lastname){
        this.name = name;
        this.lastname = lastname;

    }

    Player(String name, String lastname, String club, String league, int numbersOfGoals, int matchesPlayed ) {
        this.name = name;
        this.lastname = lastname;
        this.club = club;
        this.league = league;
        this.numbersOfGoals = numbersOfGoals;
        this.matchesPlayed = matchesPlayed;
        setAvrg();
    }

    public void addGoals(int n){
        this.numbersOfGoals += n;
    }

    public void addMatch(int n){
        this.matchesPlayed += n;
    }

    public void setAvrg(){
        this.AvrgPerMatch = (double)numbersOfGoals/(double)matchesPlayed;
    }

    public void setGoals(int n){
        this.numbersOfGoals += n;
    }
    public void setMatches(int n){
        this.matchesPlayed += n;
    }

    public String getName(){
        return name;
    }
    public String getLastName(){
        return lastname;
    }
    public String getClub(){
        return club;
    }
    public String getLeague(){
        return league;
    }
    public int getNumbersOfGoals(){
        return numbersOfGoals;
    }
    public int getMatchesPlayed(){
        return matchesPlayed;
    }


    public String stats(){
        StringBuilder s = new StringBuilder();
        s.append("Goals: " + numbersOfGoals +" | Matches: " + matchesPlayed + " | AvrgPerMatch: " + AvrgPerMatch );
        return s.toString();

    }
    public String info(){
        StringBuilder s = new StringBuilder();
        s.append("Club and League -> "+ club +" | "+ league);
        return s.toString();

    }


    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(name + " " + lastname + " -> " + numbersOfGoals);
        return s.toString();
    }



}
