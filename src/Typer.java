import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
public class Typer {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	
	public JLabel currentWord;
	public JLabel nextWord;
	public String original;
	public String original2;
	int sec = 60;
	Timer t;
	
	public ArrayList<String> level1 = new ArrayList<>();
	public ArrayList<String> level2 = new ArrayList<>();
	public ArrayList<String> level3 = new ArrayList<>();
	public ArrayList<String> level4 = new ArrayList<>();
	public ArrayList<String> level5 = new ArrayList<>();
	public ArrayList<String> level6 = new ArrayList<>();
	public ArrayList<String> level7 = new ArrayList<>();
	public ArrayList<String> level8 = new ArrayList<>();
	
	public ArrayList<ArrayList<String>> allLevels = new ArrayList<ArrayList<String>>();
	
	int count = 0;
	int currentScore = 0;
	int currentLevel = 0;
	int wrong = 0;
	private JTextField userInput;
	private JButton enterBtn;
	private JLabel scoreLabel;
	private JLabel timerLabel;
	private JButton startBtn;
	ImageIcon play, again;
	private JButton restartBtn;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Typer window = new Typer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Typer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		timerLabel = new JLabel("Time: " + sec);
		GridBagConstraints gbc_timerLabel = new GridBagConstraints();
		gbc_timerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_timerLabel.gridx = 0;
		gbc_timerLabel.gridy = 0;
		panel.add(timerLabel, gbc_timerLabel);
		
		scoreLabel = new JLabel("Click start to start typing !!");
		GridBagConstraints gbc_scoreLabel = new GridBagConstraints();
		gbc_scoreLabel.insets = new Insets(0, 0, 5, 0);
		gbc_scoreLabel.gridx = 6;
		gbc_scoreLabel.gridy = 0;
		panel.add(scoreLabel, gbc_scoreLabel);
		
		currentWord = new JLabel("\"\"");
		currentWord.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_currentWord = new GridBagConstraints();
		gbc_currentWord.insets = new Insets(0, 0, 5, 5);
		gbc_currentWord.gridx = 3;
		gbc_currentWord.gridy = 2;
		panel.add(currentWord, gbc_currentWord);
		
		nextWord = new JLabel("\"\"");
		nextWord.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_nextWord = new GridBagConstraints();
		gbc_nextWord.insets = new Insets(0, 0, 5, 5);
		gbc_nextWord.gridx = 3;
		gbc_nextWord.gridy = 3;
		panel.add(nextWord, gbc_nextWord);
		
		
		enterBtn = new JButton("Enter");
		enterBtn.setEnabled(false);
		enterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveToTheNextWord();
			}
		});
		
		userInput = new JTextField();
		GridBagConstraints gbc_userInput = new GridBagConstraints();
		gbc_userInput.insets = new Insets(0, 0, 5, 0);
		gbc_userInput.fill = GridBagConstraints.HORIZONTAL;
		gbc_userInput.gridx = 6;
		gbc_userInput.gridy = 4;
		panel.add(userInput, gbc_userInput);
		userInput.setColumns(10);
		userInput.setEnabled(false);
		userInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    moveToTheNextWord();
                }
            }
        });
		
		
		GridBagConstraints gbc_enterBtn = new GridBagConstraints();
		gbc_enterBtn.insets = new Insets(0, 0, 5, 5);
		gbc_enterBtn.gridx = 3;
		gbc_enterBtn.gridy = 5;
		panel.add(enterBtn, gbc_enterBtn);
		
		startBtn = new JButton("Start");
		play = new ImageIcon("Icons/play.png");
		Image img1 = play.getImage();
		Image newPlay = img1.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		play = new ImageIcon(newPlay);
		startBtn.setIcon(play);
		startBtn.setBackground(Color.GREEN);
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				time();
				scoreLabel.setText("Score: 0");
				startBtn.setEnabled(false);
				userInput.setEnabled(true);
				enterBtn.setEnabled(true);
				frame.getRootPane().setDefaultButton(enterBtn);
				userInput.requestFocusInWindow();
				startBtn.setBackground(null);
			}
		});
		GridBagConstraints gbc_startBtn = new GridBagConstraints();
		gbc_startBtn.insets = new Insets(0, 0, 5, 5);
		gbc_startBtn.gridx = 3;
		gbc_startBtn.gridy = 6;
		panel.add(startBtn, gbc_startBtn);
		
		
		restartBtn = new JButton("Restart");
		again = new ImageIcon("Icons/again.png");
		Image img = again.getImage();
		Image newAgain = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		again = new ImageIcon(newAgain);	
		restartBtn.setIcon(again);
		restartBtn.setBackground(Color.lightGray);
		restartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				endGame();	
				resetGame();
			}
		});
		
		GridBagConstraints gbc_restartBtn = new GridBagConstraints();
		gbc_restartBtn.gridx = 6;
		gbc_restartBtn.gridy = 7;
		panel.add(restartBtn, gbc_restartBtn);
		
		allLevels.add(level1);
		allLevels.add(level2);
		allLevels.add(level3);
		allLevels.add(level4);
		allLevels.add(level5);
		allLevels.add(level6);
		allLevels.add(level7);
		allLevels.add(level8);
		
		for(int i =0; i < 8; i++) {
			readText("Words/level" + (i+1)+".txt", allLevels.get(i));
		}
		
		firstGame();
	}
	

	public void readText(String path, ArrayList<String> currentList){
		try {
			BufferedReader buf = new BufferedReader(new FileReader(path));
			String word = buf.readLine();
			while(word != null) {
				currentList.add(word);
				word = buf.readLine();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void levelCounter() {
		count++;
		if(count == 9) {
			count = 0;
			currentLevel++;
		}
	}
	
	public String selectRandomWord() {
		ArrayList<String> current = allLevels.get(currentLevel%8);
		Random r = new Random();
		int randomNumber = r.nextInt(current.size());
		String randomStr = current.get(randomNumber);
		levelCounter();
		return randomStr;
	}
	
	public void firstGame() {
		original = selectRandomWord();
		currentWord.setText(original);
		original2 = selectRandomWord();
		nextWord.setText(original2);
	}
	
	public void game() {
		original2 = selectRandomWord();
		original = nextWord.getText();
		currentWord.setText(original);
		nextWord.setText(original2);
	}
	
	public void moveToTheNextWord() {
		String userWord = userInput.getText().trim();
		if(userWord.contentEquals(original)) {
			updateScore();
		}else {
			wrong++;
		}
		userInput.setText(null);
		userInput.requestFocusInWindow();
		game();
	}
	
	public void updateScore() {
		currentScore++;
		scoreLabel.setText("Score: "+ currentScore);
	}

	public void endGame() {
		String msg = String.format("Game over!\n%d WPM",currentScore);
		JOptionPane.showMessageDialog(null,msg);	
		userInput.setEnabled(false);
		enterBtn.setEnabled(false);
		sec = 60;
		resetGame();
	}
	
	public void time() {
		
		t = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(sec == 0) {
					t.stop();
					endGame();
				}	
				sec--;
				timerLabel.setText("Time: " + sec);
			}
		});
		
		t.start();
    }
	
	public void resetGame() {
		for(int i = 0; i < allLevels.size(); i++) {
			allLevels.get(i).clear();
		}
		for(int i = 0; i < 8; i++) {
			readText("Words/level" + (i+1) + ".txt", allLevels.get(i));
		}
		count = 0;
		currentLevel = 0;
		currentScore = 0;
		wrong = 0;
		scoreLabel.setText("Score: " + currentScore);
		original = "";
		if(sec != 60) {
			t.stop();
		}
		sec = 60;
		timerLabel.setText("Time: " + sec);
		userInput.setEnabled(false);
		startBtn.setEnabled(true);
		startBtn.setBackground(Color.green);
		enterBtn.setEnabled(false);
		frame.getRootPane().setDefaultButton(startBtn);
		frame.repaint();
		frame.revalidate();
		userInput.setText("");
		userInput.requestFocusInWindow();
		firstGame();
	}
	
	
}
