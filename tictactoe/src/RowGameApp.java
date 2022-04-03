import controller.RowGameController;

public class RowGameApp 
{
    public static void main(String[] args) {
        System.out.println("Creating board of size (" + args[0] + ", " + args[1] + ")...");
        
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        int toWin = Integer.parseInt(args[2]);

        RowGameController game = new RowGameController(width, height, toWin);
        game.setGUIVisibility(true);
    }
}
