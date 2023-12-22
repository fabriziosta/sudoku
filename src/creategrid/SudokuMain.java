package creategrid;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SudokuMain{
	public static void main(String[] args) { //Starting point of my project.
		CreateGrid myGrid = new CreateGrid();
		//-----------------------------------CREATE MY GUI.--------------------------------------
		//First of all I need to set the Look and Feel of my window. I need to declare it BEFORE loading
		//any GUI element or it will not work.
		 try { 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		 
		//-----Starting loading all my GUI elements.------
		//Frame
		JFrame frame = new JFrame ("SUPSudoku");
		//Top side panel of my Sudoku Game, which will contains combo box, label and Generate button.
		JPanel p2 = new JPanel();
		//Middle side panel of our game which will contains 9x9 grid
		JPanel p1 = new JPanel();
		//I need to set my layout container to "null" because I need to manage buttons inside the game by myself.
		p1.setLayout(null);
		JLabel text = new JLabel("Choose your Level: ");
		
		JComboBox<String> chooseDifficulty = new JComboBox<String>();
		chooseDifficulty.addItem("Easy");
		chooseDifficulty.addItem("Medium");
		chooseDifficulty.addItem("Hard");
		
		//Generate button and his EVENT.
		JButton generate = new JButton ("Generate");
		generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//0. ENABLE CLICK ON ALL BUTTONS. (THEY ARE DISABLED when you open the game)
				for(int a = 0; a < 9; a++){
					for(int b = 0; b < 9; b++){
						myGrid.allButtons[a][b].setEnabled(true); 
					}
				}
				//1. CLEAN USER GRID. Every time I click on generate it will start from scratch again.
				myGrid.CleanUserGrid();
				//2. CLEAN SOLUTION GRID.
				myGrid.CleanSolutionGrid();
				//3. -----CREATE SOLUTION IN BACKGROUND-----
				//3.1 RANDOMIZE FIRST ROW
				myGrid.FirstRandomRow();
				//3.2 FOR EACH ROW, EXTRACT NUMBERS AND CHECK IF I CAN INSERT IT.
				boolean falseRow = false;
				//It must run 8 times because my first row was already created.
				for (int rows = 1; rows < 9; rows++){ 
					falseRow = false;
					myGrid.insertRows(rows);
					falseRow = myGrid.checkRow(rows);
					if (falseRow == true){ //If there is a 0 inside my line...
						rows = rows -2; /*in this way it has to repeat the same line. Why 2 lines and not 1? 
						Because sometimes shuffle only 1 line is not enough to respect all sudoku rules. So when my program can't find a
						solution, go back of 2 lines and randomize them again. In this way he has infinite chance to get a good random combination.*/
					}
				}
				//4. PRINT IN CONSOLE SOLUTION GRID. (UNNECESSARY STEP BUT USEFUL)
				myGrid.printMatrix(); 
				//5. PRINT SOME NUMBERS GRAPHICALLY FOR THE USER. (depending on the choosen difficulty)
				if (chooseDifficulty.getSelectedItem().toString() == "Easy"){
					myGrid.ShowNumbers(35);
				}
				else if (chooseDifficulty.getSelectedItem().toString() == "Medium"){
					myGrid.ShowNumbers(30);
				}
				else if (chooseDifficulty.getSelectedItem().toString() == "Hard"){
					myGrid.ShowNumbers(25);
				}
			}
		}); //END OF "GENERATE" EVENT.
		//--------------------------------------------------------------------------------------------------------------------//
		//This 2 "for" creates 81 buttons to play the game! I'll store all my button inside a Matrix so I can work with all of them.
		int positionX = 40; //these are used to manage pixels to draw my buttons in the right place.
		int positionY = 0;
		
		//Create, insert all in the matrix, add to the frame, set size, set enabled or not, all my buttons in 2 "for".
		for(int i = 0; i < 9; i++){
			for(int e = 0; e < 9; e++){
				JButton newButton = new JButton();
				myGrid.allButtons[i][e] = newButton;
				
				//This if is really important to draw buttons in the right place inside my frame.
				if (e == 3 || e == 6){
					positionX += 30;
				}
				myGrid.allButtons[i][e].setBounds(positionX,positionY,60,60);
				positionX += 60;
				

				p1.add(newButton);
				//All disabled until the user click on "Generate".
				myGrid.allButtons[i][e].setEnabled(false); 
				//Set my font size.
				newButton.setFont(new Font("Arial", Font.PLAIN, 40));
				
				//OPEN COMBO EVENT This event trigger when the user click on a button.
				newButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFrame frameSelection = new JFrame ();
						JPanel top = new JPanel();
						JPanel center = new JPanel();
						JPanel bottom = new JPanel();
						JLabel text2 = new JLabel("Enter a Number: ");
						
						JComboBox<Integer> combo = new JComboBox<Integer>(); 
						//This for is used to populate my combo box.
						for (int cont = 1; cont < 10; cont++ ){
							combo.addItem(cont);
						}
					    //Button to confirm which number insert in userSudokuGrid.
						JButton confirmButton = new JButton ("Confirm");
		
						top.add(text2);
						center.add(combo);
						bottom.add(confirmButton);
						frameSelection.add(top,BorderLayout.NORTH);
					    frameSelection.add(center,BorderLayout.CENTER);
					    frameSelection.add(bottom,BorderLayout.SOUTH);
						frameSelection.setVisible(true);
						frameSelection.setBounds(700, 300, 150, 200);
						frameSelection.setResizable(false);
						frameSelection.setAlwaysOnTop(true);
						
						//This event triggers when the user select a number from 1 to 9 and click on CONFIRM.
						confirmButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								//Get selection from my combo box and put it as text inside the right button.
								newButton.setText(combo.getSelectedItem().toString());
								//Close the window
								frameSelection.dispose();
								//Convert the selected value to string and then to an integer so we can put it inside userSudokuGrid.
								Integer convertedNumber = Integer.valueOf(combo.getSelectedItem().toString());
								//I need to find out which button the user have clicked.
								for(int q = 0; q < 9; q++){
									for(int w = 0; w < 9; w++){
										//If the button clicked is equal to one of my 81 buttons...
										if(newButton.equals(myGrid.allButtons[q][w])){
											//...then insert the value added by the user inside user matrix to check victory or lose.
											myGrid.userSudokuGrid[q][w] = convertedNumber;
										}
									}
								}
								//Every time I insert a number I will check if the user wins or not.
								myGrid.win();
							}
						}); //END of CONFIRM BUTTON EVENT Action Listener.
					}
				}); //End of the Action Listener.
			} //End of the internal "for".
			if (i == 2 || i == 5){ //Leave more space to create an user-friendly interface to play.
				positionY = positionY + 90; 
			}
			else{
				positionY = positionY + 60; 
			}
			positionX = 40;
		} //End of the external "for".
		
		p2.add(text);
		p2.add(chooseDifficulty);
		p2.add(generate);
		
		frame.add(p1,BorderLayout.CENTER);
		frame.add(p2,BorderLayout.NORTH);
		frame.setVisible(true);
		frame.setBounds(450, 100, 700, 700);  
		frame.setResizable(false);
	} //End of my main.
} //End of the class.
