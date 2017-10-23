import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Maze {

    private int startX, startY;
    private int endX, endY;
    private int width, height;
    private boolean solved;

    private List<List<Integer>> maze;
    private List<List<Integer>> solvedMaze;

    public Maze(String filename){
        readMaze(filename);
    }

    @Override
    public String toString(){
        if(!solved){
            return "No solution.";
        }
        StringBuilder stringBuilder = new StringBuilder("");
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                switch(solvedMaze.get(i).get(j)){
                    case 0: stringBuilder.append(" "); break;
                    case 1: stringBuilder.append("#"); break;
                    case 2: stringBuilder.append("X"); break;
                    case 3: stringBuilder.append("S"); break;
                    case 9: stringBuilder.append("E"); break;
                    default: break;
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public boolean solveMaze(){
        solved = solve(startX, startY);
        solvedMaze.get(startY).set(startX, 3);
        return solved;
    }

    public void printMaze(){
        System.out.print(this.toString());
    }

    private void readMaze(String fileName){

        BufferedReader bufferedReader = null;
        String line;
        String[] lineArray;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
            lineArray = bufferedReader.readLine().split(" ");
            width = Integer.parseInt(lineArray[0]);
            height = Integer.parseInt(lineArray[1]);

            maze = new ArrayList<>();
            solvedMaze = new ArrayList<>();
            lineArray = bufferedReader.readLine().split(" ");
            startX = Integer.parseInt(lineArray[0]);
            startY = Integer.parseInt(lineArray[1]);

            lineArray = bufferedReader.readLine().split(" ");
            endX = Integer.parseInt(lineArray[0]);
            endY = Integer.parseInt(lineArray[1]);

            while((line = bufferedReader.readLine()) != null){
                lineArray = line.split(" ");
                List<Integer> array = Arrays.stream(lineArray).map(e -> Integer.parseInt(e)).collect(Collectors.toList());
                List<Integer> array2 = new ArrayList<>(array);
                maze.add(array);
                solvedMaze.add(array2);

            }
            bufferedReader.close();
            maze.get(endY).set(endX, 9);
            solvedMaze.get(endY).set(endX, 9);
        }catch (IOException e) {
            System.out.printf("Can't read file %s\n", fileName);
            e.printStackTrace();
        }
    }

    //depth-first search algorithm
    private boolean solve(int x, int y){
        if(maze.get(y).get(x) == 9){
            return true;
        }
        if(maze.get(y).get(x) == 0){
            maze.get(y).set(x, 2); //mark visited

            if(solve(x, y + 1)){ //South
                solvedMaze.get(y).set(x, 2);
                return true;
            }

            if(solve(x + 1, y)){ //East
                solvedMaze.get(y).set(x, 2);
                return true;
            }

            if(solve(x, y - 1)){ //North
                solvedMaze.get(y).set(x, 2);
                return true;
            }

            if(solve(x - 1, y)){ //West
                solvedMaze.get(y).set(x, 2);
                return true;
            }
        }
        return false;
    }
}