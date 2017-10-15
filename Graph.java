import java.io.*;
//has stack and queue classes
import java.util.*;

public class Graph
{
    //fields
    private Vertex vertexList[];
    private int adjacencyMatrix[][];
    
    //constructor calls methods that read the input files
    public Graph() 
    { 
        try {
            
        readLabels("labels.txt");
        readAdjacencyMatrix("adjacency_matrix.txt");
        
        } catch (IOException exception) 
        {
            System.out.println(exception.getMessage());
	}
    } 

    //using an integer passed in, finds the associated vertex in the vertexList 
    //array and prints out the associated label
    public void displayVertex(int vertexIndex)
    {	
    	System.out.print(this.vertexList[vertexIndex].label);
    }
    
    //reads the adjacency matrix input file and saves it into the adjacencyMatrix 
    //field
    private void readAdjacencyMatrix(String filePath) throws IOException
    {
        // System.getProperty("user.dir") returns the directory the program is
        // executed from,\\input.txt is appended to open our input.txt file
	    FileReader fileReader = new FileReader(System.getProperty("user.dir") + "\\" + filePath); //THIS MAY NOT WORK
	    BufferedReader textReader = new BufferedReader(fileReader);
        
        //we have to run this block of code once out of the while loop in order to initialize our adjacencymatrix to the correct size
        String lineFromFile = textReader.readLine();
        this.adjacencyMatrix = new int[lineFromFile.length()][lineFromFile.length()];
        for (int i = 0; i < lineFromFile.length(); i++)
        {
            int val = (int) (lineFromFile.charAt(i)) - 48; //change the char to an int
            adjacencyMatrix[0][i] = val; //add it to adjacency matrix
        }
	    // the line from our BufferedReader must be assigned immediately to a variable
        // as the reader will move on to the next line afterward
        // while != null ensures we get the entirety of the message
        
        int currentRow = 1; //track the row in the adjacency matrix, we start at 1 because row 0 was already taken care of above
        
        while ((lineFromFile = textReader.readLine()) != null) {

                // we continue to add our lines to our message until we reach the end of the
                // file
                
                //for each column of the currentRow, read in the line of 
                for (int i = 0; i < lineFromFile.length(); i++)
                {
                    int val = (int) (lineFromFile.charAt(i)) - 48; //change the char to an int
                    adjacencyMatrix[currentRow][i] = val; //add it to adjacency matrix
                }
                currentRow++;
        }
        textReader.close();
    }

    //reads the labels input file, creates a new vertex for each label, and saves
    //each new vertex into the vertexList field
    private void readLabels(String filePath) throws IOException
    {
        // System.getProperty("user.dir") returns the directory the program is
        // executed from,\\input.txt is appended to open our input.txt file
	    FileReader fileReader = new FileReader(System.getProperty("user.dir") + "\\" + filePath); //THIS MAY NOT WORK
	    BufferedReader textReader = new BufferedReader(fileReader);
        
        String lineFromFile = textReader.readLine();
        
	    // the line from our BufferedReader must be assigned immediately to a variable
        // as the reader will move on to the next line afterward
        // while != null ensures we get the entirety of the message
        
        //while ((lineFromFile != null)) {
        	this.vertexList = new Vertex[lineFromFile.length()];
            // we continue to add our lines to our message until we reach the end of the
            // file
            for (int i = 0; i < lineFromFile.length(); i++)
            {
                vertexList[i] = new Vertex(lineFromFile.charAt(i));
                this.displayVertex(i);
                
            }
       // }
        textReader.close();
    }
    
    //resets all vertices' wasVisited to false
    public void resetVisitedNodes() {
    	for (int i = 0; i < vertexList.length; i++) {
    		vertexList[i].wasVisited = false;
    	}
    }
    
    //implements the breadth first search algorithm calling
    //getAdjacentUnvisitedVertex() and displayVertex() as needed
    public void breadthFirstSearch()
    {
    	//Queues are made using LinkedLists in java
    	Queue<Vertex> newQueue = new LinkedList<Vertex>();
    	int currentVertexIndex = -1;
    	
    	//start off by visiting, marking, and pushing the first vertex in our list if it exists
    	if (vertexList != null) {
    		displayVertex(0);
    		vertexList[0].wasVisited = true;
    		newQueue.add(vertexList[0]);
    		
    	}
    	
    	while (!newQueue.isEmpty()) {
    		//look at the first in our queue, set it as our current index
    		currentVertexIndex = (int)(newQueue.peek().label - 65);
    		//find that vertexes unvisited neighbors
    		currentVertexIndex = getAdjacentUnvisitedVertex(currentVertexIndex);
    		//if neighbors exist, visit mark push
    		if(currentVertexIndex != -1) {
    			//VISIT
    			displayVertex(currentVertexIndex);
    			//MARK
    			vertexList[currentVertexIndex].wasVisited = true;
    			//INSERT
    			newQueue.add(vertexList[currentVertexIndex]);
    		}
    		else {
    			//remove the vertex if it has no unvisited neighbors
    			newQueue.remove();
    		}
    	}
    	//resets wasVisited for future dfs/bfs/msp
    	resetVisitedNodes();
    }

    //implements the depth first search algorithm calling          
    //getAdjacentUnvisitedVertex() and displayVertex() as needed
    public void depthFirstSearch()
    {
    	//essentially the same as our breadth first search, but with a stack instead of a queue. LIFO vs FIFO
    	Stack<Vertex> newStack = new Stack<Vertex>();
    	int currentVertexIndex = -1;
    	if (vertexList != null) {
    		displayVertex(0);
    		vertexList[0].wasVisited = true;
    		newStack.add(vertexList[0]);
    		
    	}
    	
    	while (!newStack.isEmpty()) {
    		currentVertexIndex = (int)(newStack.peek().label - 65);
    		//Visit unvisited neighbor
    		currentVertexIndex = getAdjacentUnvisitedVertex(currentVertexIndex);
    		if(currentVertexIndex != -1) {
    			//VISIT
    			displayVertex(currentVertexIndex);
    			//MARK
    			vertexList[currentVertexIndex].wasVisited = true;
    			//PUSH
    			newStack.push(vertexList[currentVertexIndex]);
    		}
    		else {
    			//pop the vertex if it has no unvisited neighbors
    			newStack.pop();
    		}
    	}
    	//resets wasVisited for future dfs/bfs/msp
    	resetVisitedNodes();
    }

    //implements the minimum spanning tree algorithm calling      
    //getAdjacentUnvisitedVertex() and displayVertex() as needed
    
    public void minimumSpanningTree()
    {
    	//same as depth first search with a few string methods
    	//start with empty string and add/remove characters as we push/pop
    	String minimumSpanningTree = "";
    	Stack<Vertex> newStack = new Stack<Vertex>();
    	int currentVertexIndex = -1;
    	if (vertexList != null) {
    		vertexList[0].wasVisited = true;
    		newStack.add(vertexList[0]);
    		
    	}
    	
    	while (!newStack.isEmpty()) {
    		//add label after entering while loop again
    		minimumSpanningTree += newStack.peek().label;
    		currentVertexIndex = (int)(newStack.peek().label - 65);
    		//Visit unvisited neighbor
    		currentVertexIndex = getAdjacentUnvisitedVertex(currentVertexIndex);
    		if(currentVertexIndex != -1) {
    			//MARK
    			vertexList[currentVertexIndex].wasVisited = true;
    			//PUSH
    			newStack.push(vertexList[currentVertexIndex]);
    			//add label after pushing new vertex
    			minimumSpanningTree += (newStack.peek().label + " ");
    		}
    		else {
    			//remove the last label from our string when we pop it off the stack
    			minimumSpanningTree = minimumSpanningTree.substring(0, minimumSpanningTree.length() - 1);
    			newStack.pop();
    		}
    	}
    	System.out.println(minimumSpanningTree);
    	resetVisitedNodes();
    } 

    //returns an unvisited vertex adjacent to the vertex indicated by vertexIndex 
    //using the vertexList and adjacenyMatrix fields
    //IMPORTANT NOTE: returns -1 if no unvisited vertex
    public int getAdjacentUnvisitedVertex(int vertexIndex)
    {
        //for each column in the selected row (each other vertex for the selected vertex)
        for (int i = 0; i < vertexList.length; i++)
        {
            //if the vertex is adjacent
            if(adjacencyMatrix[vertexIndex][i] == 1)
            {
                //and if it has not been visited
                if(!vertexList[i].wasVisited)
                {	
                    return i;
                }
            }
        }
        return -1;
    }
}