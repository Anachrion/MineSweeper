package highscores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class HighscoreManager {

	private ArrayList<Score> scores;

	private String path;
	public final static int MAX_SAVED_SCORES = 5; 
	
	private ObjectOutputStream outputStream = null;
	private ObjectInputStream inputStream = null;

	
	public HighscoreManager(String difficulty) {
		this.scores = new ArrayList<Score>(MAX_SAVED_SCORES);
		this.path = System.getProperty("user.dir") + 
				File.separator + "data" + File.separator + difficulty + "HighScore.dat";
		
		this.scores.add(new Score ("Alexis", 200));
		this.scores.add(new Score ("Jean Claude", 300));
		this.scores.add(new Score ("Kevin", 400));
		this.scores.add(new Score ("Jean Louis", 500));
		this.scores.add(new Score ("Guillaume", 600));
	}

	@SuppressWarnings("unchecked")
	public void loadScoreFile() {
		try {
			this.inputStream = new ObjectInputStream(new FileInputStream(this.path));
			this.scores = (ArrayList<Score>) inputStream.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("/!\\ File not found ! Details :"+ e.getMessage());
		} catch (IOException e) {
			System.out.println("/!\\ IO Error ! Details : " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("/!\\ Class Not Found Error ! Details : " + e.getMessage());
		} finally {
			try {
				if (this.inputStream != null) {
					this.inputStream.close();
				}
			} catch (IOException e) {
				System.out.println("/!\\ IO Error ! Details : " + e.getMessage());
			}
		}
	}


	public void updateScoreFile() {
		try {
			this.outputStream = new ObjectOutputStream(new FileOutputStream(this.path));
			this.outputStream.writeObject(this.scores);
		} catch (FileNotFoundException e) {
			System.out.println("/!\\ File not found. Creating a new fille at " +this.path+"\nDetails :"+ e.getMessage());
		} catch (IOException e) {
			System.out.println("/!\\ IO Error ! Details : " + e.getMessage());
		} finally {
			try {
				if (this.outputStream != null) {
					this.outputStream.flush();
					this.outputStream.close();
				}
			} catch (IOException e) {
				System.out.println("/!\\ IO Error ! Details : " + e.getMessage());
			}
		}
	}



	public ArrayList<Score> getScores() {
		loadScoreFile();
		sort();
		return this.scores;
	}


	public void addScore(String name, int score) {
		loadScoreFile();
		this.scores.add(new Score(name, score));
		sort();
		this.scores.remove(MAX_SAVED_SCORES);
		updateScoreFile();
	}

	public void sort() {
		ScoreComparator comparator = new ScoreComparator();
		Collections.sort(this.scores, comparator);
	}


	public String[] getHighscoreString() {
		
		String[] results = new String[MAX_SAVED_SCORES*2];
		ArrayList<Score> sc = getScores();

		for (int i = 0; i < MAX_SAVED_SCORES; i++) {
			results[i*2] = Integer.toString(sc.get(i).getTime());
			results[(i*2)+1] = sc.get(i).getName();
		}
		return results;
	}

	/** This method returns the rank of the input time amongst the saved high score **/
	public int ranksAmongstTop (int time) {
		loadScoreFile();
		Score sc = new Score ("Name", time);
		this.scores.add(sc);
		ScoreComparator comparator = new ScoreComparator();
		Collections.sort(this.scores, comparator);
		int i = this.scores.indexOf(sc);
		this.scores.remove(i);
		return i;
	}
}