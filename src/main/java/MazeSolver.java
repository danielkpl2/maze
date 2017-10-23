
public class MazeSolver {
    public static void main(String[] args){
        if(args.length == 0){
            System.out.println("Usage: java MazeSolver [filename]");
            System.exit(0);
        }
        String filename = args[0];
        Maze maze = new Maze(filename);
        maze.solveMaze();
        maze.printMaze();
    }
}
