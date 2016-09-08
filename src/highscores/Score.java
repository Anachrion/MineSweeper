package highscores;

import java.io.Serializable;

public class Score  implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private int time;
    private String name;

    public Score(String name, int time) {
        this.time = time;
        this.name = name;
    }
    
    public int getTime() {
        return this.time;
    }

    public String getName() {
        return this.name;
    }

}