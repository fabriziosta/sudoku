package creategrid;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class CreateGrid {
	//An ArrayList from 1 to 9. I use it to extract random numbers for every single row.
	public ArrayList<Integer> numbers = new ArrayList<Integer>();
	//This matrix contains all my 9x9 buttons.
	public JButton allButtons [][] = new JButton[9][9];
	//I'm creating solution matrix and user grid. In sudokuGrid there will be my solution, in userSudokuGrid there will be stored user numbers.
	public int sudokuGrid [][] = new int[9][9];
	public Integer userSudokuGrid [][] = new Integer[9][9];
	
	//I need to randomize the first row (solution grid) and from there I'll randomize everything.
	public void FirstRandomRow(){ 
		populateArray();
		for (int y = 0; y < 9; y++){
			sudokuGrid [0][y] = numbers.remove(0);  //"remove" returns the removed element.
		}
	}
	
	//This method clean and populate my ArrayList every time.
	private void populateArray(){ 
		//1.CLEAN IT.
		while (numbers.size() != 0){
			numbers.remove(0);
		}
		//2.POPULATE IT.
		for (int y = 1; y < 10 ;y++){ 
			numbers.add(y);
		}
		Collections.shuffle(numbers);
	}
	
	//Called 8 times. It must extract numbers from 1 to 9 and for each number check if I can insert it. 
	public void insertRows(int row){
		int selection = 0;
		populateArray();
		
		for (int col = 0; col < 9; col++){ //I'll use this "col" for moving inside the matrix.
			for (int PositionArray = 0; PositionArray < numbers.size();PositionArray++ ){
				selection = numbers.get(PositionArray);
				if (checkCol(selection, col) == false && checkSquare(selection, row, col) == false){
					sudokuGrid [row][col] = numbers.remove(PositionArray);
					PositionArray = 10; //To exit my "for", only if I found already a number to put in my Grid.
				}
			}
		}
	}
	
	//Method to check columns for each number is selected by insertRows.
	private boolean checkCol(int numberToCheck, int column){
		for (int i = 0; i < 9; i++){
			if (numberToCheck == sudokuGrid[i][column]){
				return true;
			}
		}
		return false;
	}
	
	//Method to check squares for each number is selected by insertRows.
	private boolean checkSquare(int numberToCheck, int line, int column){ 
		//TOP-LEFT SQUARE
		if (line >= 0 && line < 3 && column >= 0 && column < 3){ 
			for (int x = 0; x < 3; x++){
				for (int y = 0; y < 3; y++){
					if (numberToCheck == sudokuGrid[x][y]){
						return true;
					}
				}
			}
		}
		//TOP SQUARE
		else if (line >= 0 && line < 3 && column >= 3 && column < 6){ 
			for (int x = 0; x < 3; x++){
				for (int y = 3; y < 6; y++){
					if (numberToCheck == sudokuGrid[x][y]){
						return true;
					}
				}
			}
		}
		//TOP-RIGHT SQUARE
		else if (line >= 0 && line < 3 && column >= 6 && column < 9){ 
			for (int x = 0; x < 3; x++){
				for (int y = 6; y < 9; y++){
					if (numberToCheck == sudokuGrid[x][y]){
						return true;
					}
				}
			}
		}	
		//LEFT SQUARE
		else if (line >= 3 && line < 6 && column >= 0 && column < 3){ 
			for (int x = 3; x < 6; x++){
				for (int y = 0; y < 3; y++){
					if (numberToCheck == sudokuGrid[x][y]){
						return true;
					}
				}
			}
		}
		//CENTER SQUARE
		else if (line >= 3 && line < 6 && column >= 3 && column < 6){
			for (int x = 3; x < 6; x++){
				for (int y = 3; y < 6; y++){
					if (numberToCheck == sudokuGrid[x][y]){
						return true;
					}
				}
			}
		}
		//RIGHT SQUARE
		else if (line >= 3 && line < 6 && column >= 6 && column < 9){
			for (int x = 3; x < 6; x++){
				for (int y = 6; y < 9; y++){
					if (numberToCheck == sudokuGrid[x][y]){
						return true;
					}
				}
			}
		}
		//BOTTOM-LEFT SQUARE
		else if (line >= 6 && line < 9 && column >= 0 && column < 3){
			for (int x = 6; x < 9; x++){
				for (int y = 0; y < 3; y++){
					if (numberToCheck == sudokuGrid[x][y]){
						return true;
					}
				}
			}
		}
		//RIGHT SQUARE
		else if (line >= 6 && line < 9 && column >= 3 && column < 6){
			for (int x = 6; x < 9; x++){
				for (int y = 3; y < 6; y++){
					if (numberToCheck == sudokuGrid[x][y]){
						return true;
					}
				}
			}
		}
		//BOTTOM-RIGHT SQUARE
		else if (line >= 6 && line < 9 && column >= 6 && column < 9){
			for (int x = 6; x < 9; x++){
				for (int y = 6; y < 9; y++){
					if (numberToCheck == sudokuGrid[x][y]){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//Method to check my full row after I have already fill it with numbers. This method must check if inside my row there is a 0
	//and in this case I'll return true.
	public boolean checkRow(int row){
		for (int i = 0; i < 9; i++){
			//if 1 number inside my grid is == 0 then put all numbers to 0 for the previous 2 rows. Because if i don't do that,
			//when I call again my function to populate the rows, it will populate them in a wrong way. 
			if (sudokuGrid [row][i] == 0){
				for (int z = 0; z < 9; z++){
					sudokuGrid[row][z] = 0;
					sudokuGrid[row-1][z] = 0;
				}
				return true;
			}
		}
		return false;
	}

	//This method is used to show on the grid numbers to help the user start playing.
	public void ShowNumbers(int difficulty){
		//I need indexes from 0 to 8 to select random numbers to show random numbers when the user click on "generate".
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		//Populating my ArrayList
		for (int i = 0; i < 9; i++){
			indexes.add(i);
		}
		
		//Each loop shuffle my ArrayList and extract 2 indexes to select a random number to show.
		//If I have extracted same numbers twice, decrease my "z" so I need to do 1 more loop.
		for (int z = 0; z < difficulty; z++){
			Collections.shuffle(indexes);
			if (userSudokuGrid[indexes.get(0)][indexes.get(1)] != 0){
				z = z - 1;
			}
			else{
				userSudokuGrid[indexes.get(0)][indexes.get(1)] = sudokuGrid[indexes.get(0)][indexes.get(1)];
				String prova = userSudokuGrid[indexes.get(0)][indexes.get(1)].toString();
				
				allButtons[indexes.get(0)][indexes.get(1)].setText(prova);
				allButtons[indexes.get(0)][indexes.get(1)].setEnabled(false); 
			}
		}
	}
	
	//Call it every time the user click on "Generate". Clean my Solution Grid.
	public void CleanSolutionGrid(){
		for (int x = 0; x < 9;x++){
			for (int y = 0; y < 9; y++){
				sudokuGrid[x][y] = 0;
			}	
		}
	}
	
	//Call it every time the user click on "Generate".
	public void CleanUserGrid(){
		for (int x = 0; x < 9;x++){
			for (int y = 0; y < 9; y++){
				userSudokuGrid[x][y] = 0;
				allButtons[x][y].setText("");
			}	
		}
	}
	
	//This simple method is used to check if the user wins. It is invoked every single time the user insert a number.
	public void win(){
		boolean result = true;
		for (int x = 0; x < 9;x++){
			for (int y = 0; y < 9; y++){
				if(userSudokuGrid[x][y] != sudokuGrid[x][y]){
					result = false;
				}
			}	
		}
		if (result == true){
			JOptionPane.showMessageDialog(null,"Congratulation, you WIN!!");
			for (int x = 0; x < 9;x++){
				for (int y = 0; y < 9; y++){
					allButtons[x][y].setEnabled(false);
				}
			}
		}
	}
	
	//Print Solution Grid
	public void printMatrix(){ 
		System.out.print("SUDOKU GRID");
		System.out.print("\n");
		for (int x = 0; x < 9; x++){
			for (int y = 0; y < 9; y++){
				if (y == 0){
					System.out.print("| ");
				}
				 System.out.print(sudokuGrid[x][y] + " ");
				 if (y == 2 || y == 5){
					 System.out.print(" | ");
				 }
				 if (y == 8){
					 System.out.print(" |");
				 }
			}
			System.out.print("\n");
			if (x == 2 || x == 5){
				System.out.print(" - - - - - - - - - - - - - ");
				System.out.print("\n");
			}	
		}
		System.out.print("\n"); //To print a lot of matrix one after the other
	}
	
}//End of the class.