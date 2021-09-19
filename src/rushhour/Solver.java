package rushhour;

import java.util.*;

public class Solver {

    public static PriorityQueue<RushHour> priorityQueue;

   /** private static boolean bfs_search(RushHour startState) throws Exception {
        LinkedList<RushHour> q = new LinkedList();
        q.addLast(startState);

        Set<Integer> seenBoards = new HashSet<>();
    //    seenBoards.add(startState.getBoard());

        while(!q.isEmpty()){
            RushHour state = q.get(0);
            q.remove(q.get(0));

            for (int i = 0; i < 6; i++) {
                for (int k = 0; k < 6; k++) {
                    System.out.print(state.getBoard()[i][k]);
                }
                System.out.println();
            }
            System.out.println();

            if(state.isSolved())
                return true;

            for(char[][] newState : state.getStates()){
                if(!seenBoards.contains(Arrays.deepHashCode(newState))){
                    seenBoards.add(state.hashBoard());
                    q.addLast(new RushHour(newState));
                }
            }
        }
        return false;
    }
    **/

   private static LinkedList<RushHour> findPath(HashMap<RushHour, RushHour> original, RushHour goal){
       LinkedList<RushHour> path = new LinkedList<>();
       RushHour state = goal;
       path.addFirst(state.clone());
       while(original.get(state) != null){
           RushHour parent = original.get(state);
           path.addFirst(parent.clone());
           state = parent;
       }
       return path;
   }

    private static LinkedList<RushHour> searchAStar(RushHour startState) throws Exception {
        HashMap<RushHour, RushHour> original = new HashMap<>();
        HashMap<Integer, Boolean> visited = new HashMap<>();

        RushHour state = new RushHour(startState.getBoard());
        RushHour goal = null;
        state.setCost(0);

        priorityQueue.add(state);
        visited.put(state.hashBoard(), true);
        while(!priorityQueue.isEmpty()){
            RushHour newState = priorityQueue.poll();
            if(newState.isSolved()){
                goal = newState;
                break;
            }
            for(RushHour nextState : newState.getStates()){
                int cost = newState.getCost() + 1 + nextState.heuristic();
                if(!visited.containsKey(nextState.hashBoard())){
                    nextState.setCost(cost);
                    priorityQueue.add(nextState);
                    original.put(nextState, newState);
                    visited.put(nextState.hashBoard(), true);
                }
            }
        }
        return findPath(original, goal);
    }

    public static void solveFromFile(String input, String output) throws Exception {
       priorityQueue = new PriorityQueue<RushHour>(10, new Comparator<RushHour>() {
           @Override
           public int compare(RushHour o1, RushHour o2) {
               return o1.getCost() - o2.getCost();
           }
       });

        LinkedList<RushHour> path = searchAStar(new RushHour(input));

        for(RushHour r : path)
            r.printBoard();
    }

    public static void main(String[] args) throws Exception {
        String s = "";
        solveFromFile("B11.txt", s);
    }
}
