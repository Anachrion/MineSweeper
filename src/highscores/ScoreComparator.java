package highscores;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
	
        public int compare(Score score1, Score score2) {

            int t1 = score1.getTime();
            int t2 = score2.getTime();

            if (t1 < t2){
                return -1;
            }else if (t1 > t2){
                return 1;
            }else{
                return 0;
            }
        }
}