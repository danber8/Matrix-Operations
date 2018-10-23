
import java.util.LinkedList;
import java.util.List;

class SparseMatrix implements SparseInterface{
//The name of the current linked list is list1
	List<Point> list1 = new LinkedList<>();

	int rowSize;
	int colSize;
//I am initializing the default constructor to be a 5x5 matrix
	SparseMatrix(){
		rowSize = 5;
		colSize = 5;
	}

	SparseMatrix(int row, int col){
		rowSize = row;
		colSize = col;
	}
/*
        Should clear the matrix of all entries (make all entries 0)
     */
    	public void clear(){
		list1.clear();

	}


    /*
        Sets  size of the matrix.  Number of rows, number of columns. 
     */
//setting the size of a matrix empties it first
    	public void setSize(int numRows, int numCols){
		clear();
		rowSize = numRows;
		colSize = numCols;

	}

//getter method
    	public int getNumRows(){
		return rowSize;
	}
//getter method
    	public int getNumCols(){
		return colSize;
	}

    /*
        Adds an element to the row and column passed as arguments (overwrites if element is already present at that position).
        Throws an error if row/column combination is out of bounds.
        Checks to see if element has a value of zero before creating
     */
    	public void addElement(int row, int col, int data){
//check if input is valid
		if(row > rowSize -1 || col > colSize -1|| row < 0 || col < 0){
			//System.out.println("You are trying to add a data point outside of the matrix");                        
		}
//if the matrix is empty and the data is valid, make it the first node
		else if(list1.size() == 0 && data != 0){
			Point dataPoint = new Point(row, col, data);
			list1.add(dataPoint);
		}
//if the data being entered is 0, remove that element from the matrix
		else if (data == 0){
			removeElement(row,col);
		}
//otherwise insert the node at its appropriate position
//the big O for this of O(n)
		else if(data != 0){
//remove element to avoid duplicates
			removeElement(row,col);
			Point dataPoint = new Point(row, col, data);

			for (int i = 0; i < list1.size(); i++){
//traverse the linked list and check conditions 

//Point temp is the current node I am comparing with in the linked list
				Point temp = list1.get(i);

//if the row is less that the current row, it should clearly be inserted before the current element
				if(row < temp.row){
					list1.add(i, dataPoint);
					break;
				}
//if the row is the same as the current element, but the col is less that the current element, the new dataPoint should come before the current element
				else if(row == temp.row && col < temp.col){
					list1.add(i, dataPoint);
					break;
				}
//if the element being added is the first to be added to a row, add it to the end of the linked list
				else if((i + 2) > list1.size()){
					list1.add(dataPoint);
					break;
				}
			}
		}
	}


    /*
        Remove (make 0) the element at the specified row and column.
        Throws an error if row/column combination is out of bounds.
     */
    	public void removeElement(int row, int col){
//check if the row and col are valid inputs
                if(row > rowSize -1|| col > rowSize -1|| row < 0 || col < 0){
                        //System.out.println("You are trying to remove a data point outside of the matrix");
                }
//if so, iterate through the linked list looking for a Point with the required row and col
		else{
			for(int i = 0; i < list1.size(); i++){
				Point temp = list1.get(i);
				if(temp.col  == col && temp.row == row){
					list1.remove(temp);
					break;
				}
			}
		}


	}


    /*
        Return the element at the specified row and column
        Throws an error if row/column combination is out of bounds.
     */
    	public int getElement(int row, int col){
		if(row > rowSize -1 || col > colSize -1|| row < 0 || col < 0){
			//System.out.println("You are trying to add a a data point outside of the matrix");                        
		}
		else{
//The big O if O(n)
			for(int i = 0; i< list1.size(); i++){
				Point temp = list1.get(i);
				if(temp.row  == row && temp.col == col){
					return temp.data;
				}
			}
		}
		return 0;
	}

           
    	public String toString(){
		String result = "";
		for(int i = 0; i< list1.size(); i++){
			Point temp = list1.get(i);
			result += temp.row  + " " + temp.col + " " + temp.data + "\n";
		}		
		return result;
	}


    
    /*takes another matrix as input and returns the sum of the two matrices*/
    /*return NULL if sizes incompatible*/
    	public SparseInterface addMatrices(SparseInterface matrixToAdd){

//check if the matrices sizes are compatible
		if(getNumRows() != matrixToAdd.getNumRows() || getNumCols() != matrixToAdd.getNumCols()){
			//System.out.println("The sizes are incompatible");
			return null;
		}
		SparseMatrix add = new SparseMatrix(getNumRows(), getNumCols());
//add elements of the same index
//the big O is O(n^3) because there are 2 for loops and getElemtns i o(n) 
		for(int i = 0; i < getNumRows(); i++){
			for(int j = 0; j < getNumCols(); j++){
				int result = getElement(i,j) + matrixToAdd.getElement(i,j);
				add.addElement(i,j,result);
			}
		}

		return add;
	}
    
    /*takes another matrix as input and returns the product of the two matrices*/
    /*return NULL if sizes incompatible*/
	public SparseInterface multiplyMatrices(SparseInterface matrixToMultiply){
//check if the matrices sizes are compatible
		if(getNumRows() != matrixToMultiply.getNumCols() || getNumCols() != matrixToMultiply.getNumRows()){
			//System.out.println("The sizes are incompatible");			
			return null;
		}


		SparseMatrix multiply = new SparseMatrix(getNumRows(), getNumRows());
//the big O if O(n^4) because there a 3 for loops and getElement is O(n)	
		for(int i = 0; i < multiply.getNumRows(); i++){
			for(int j = 0; j < multiply.getNumRows(); j++){
				int result = 0;
					for(int k = 0; k < multiply.getNumRows(); k++){
						result += getElement(i,k) * matrixToMultiply.getElement(k,j);
									//multiply.addElement(i,j,result);
					}
					
			multiply.addElement(i,j,result);
			}
		}

		return multiply;
	}




	public static void main(String[] args){



        SparseInterface myTest = new SparseMatrix();

	myTest.setSize(3, 3);

        System.out.println("Num Rows is 3: " + (myTest.getNumRows() == 3));
 	System.out.println("Num Cols is 3: " + (myTest.getNumCols() == 3));

        myTest.addElement(0, 0, 16);

        myTest.addElement(0, 1, 4);

        myTest.removeElement(0,1);

        String correctString = "0 0 16\n";

        System.out.println("toString is correct: " + correctString.equals(myTest.toString()));

	myTest.removeElement(0,0);
        

        myTest.addElement(2,2,4);

        myTest.addElement(1,0,-3);

        correctString = "1 0 -3\n2 2 4\n";

        System.out.println("toString is correct: " + correctString.equals(myTest.toString()));
         

	myTest.removeElement(2, 2);

	myTest.removeElement(1, 0);

        myTest.addElement(0, 0, 0);

        correctString = "";

        //Because we are not storing 0 values in the matrix the toString should reflect an "empty" (all 0) matrix.
        System.out.println("toString is correct: " + correctString.equals(myTest.toString()));


        myTest.addElement(0, 1, 3);

        myTest.addElement(0, 1, 0);

        correctString = "";

        //Note that adding 0 to the matrix overwrites the data at that position to 0 as defined in the interface description
        //Because we are not storing 0 values, we can remove the element at that position.
        System.out.println("toString is correct: " + correctString.equals(myTest.toString()));

	System.out.println(myTest.toString());

        myTest.addElement(0, 0, 16);
        myTest.addElement(0, 1, 4);
        myTest.addElement(1, 1, 9);
        myTest.addElement(2, 2, 7);
        
        SparseInterface addTest1 = new SparseMatrix();
	addTest1.setSize(3, 3);
        addTest1.addElement(0, 0, 1);
        addTest1.addElement(1, 1, 2);
        addTest1.addElement(2, 2, 3);
        
        SparseInterface addTest2 = new SparseMatrix();
	addTest2.setSize(3, 3);
        addTest2.addElement(0, 0, 3);
        addTest2.addElement(0, 0, 2);
        addTest2.addElement(0, 0, 1);
        
        SparseInterface addTest3 = addTest1.addMatrices(addTest2);
        
        System.out.println("added matrix: "+addTest3.toString());
        
        SparseInterface multiplyTest1 = new SparseMatrix();
        multiplyTest1.setSize(3, 3);
        multiplyTest1.addElement(0, 0, 1);
        multiplyTest1.addElement(0, 1, 1);
        multiplyTest1.addElement(0, 2, 1);

        SparseInterface multiplyTest2 = new SparseMatrix();
        multiplyTest2.setSize(3, 3);
        multiplyTest2.addElement(0, 0, 1);
        multiplyTest2.addElement(1, 0, 1);
        multiplyTest2.addElement(2, 0, 1);
        
        multiplyTest2.addElement(0, 1, 2);
        multiplyTest2.addElement(1, 1, 1);
        multiplyTest2.addElement(2, 1, 1);
        
        multiplyTest2.addElement(0, 2, 3);
        multiplyTest2.addElement(1, 2, 1);
        multiplyTest2.addElement(2, 2, 1);

        SparseInterface multiplyTest3 = multiplyTest1.multiplyMatrices(multiplyTest2);
        
        System.out.println("multiplied matrix: "+multiplyTest3.toString());

	SparseInterface test1 = new SparseMatrix();
        test1.setSize(3, 4);
	SparseInterface test2 = new SparseMatrix();
        test2.setSize(4, 5);

	SparseInterface test3 = test1.addMatrices(test2);  //should return null
	SparseInterface test4 = test1.multiplyMatrices(test2); //should work
	SparseInterface test5 = test2.multiplyMatrices(test1); //should return null

        
  
/*

		SparseInterface myTest = new SparseMatrix(4,3);
		SparseInterface myTest1 = new SparseMatrix(3,4);
		//SparseInterface myTest2 = new SparseMatrix(2,2);
	
		//myTest.addMatrices(myTest1);
		

		myTest.addElement(0, 0, 1);
		myTest.addElement(0, 1, 2);
		myTest.addElement(0, 2, 3);
		myTest.addElement(1, 0, 4);
		myTest.addElement(1, 1, 5);
		myTest.addElement(1, 2, 6);
		myTest.addElement(2, 0, 7);
		myTest.addElement(2, 1, 8);
		myTest.addElement(2, 2, 9);
		myTest.addElement(3, 0, 10);
		myTest.addElement(3, 1, 0);
		myTest.addElement(3, 2, 0);

		//myTest.addElement(10000, 100000, 5);
		
		myTest1.addElement(0,0,11);
		myTest1.addElement(0,1,12);
		myTest1.addElement(0,2,13);
		myTest1.addElement(0,3,14);
		myTest1.addElement(1,0,15);
		myTest1.addElement(1,1,16);
		myTest1.addElement(1,2,16);
		myTest1.addElement(1,3,0);
		myTest1.addElement(2,0,0);
		myTest1.addElement(2,7,0);
		myTest1.addElement(2,2,8);
		myTest1.addElement(2,3,1);

		//myTest1.addElement(10000, 10000, 5 );

		System.out.println(myTest.toString());
		System.out.println("\n");
		System.out.println(myTest1.toString());

		//System.out.println(myTest.toString());
		SparseInterface myTest2 = myTest.multiplyMatrices(myTest1);
		System.out.println(myTest2.toString());
*/
		

	}

}
