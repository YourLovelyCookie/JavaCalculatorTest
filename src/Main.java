public class Main {
    public static void main(String[] args) {
        String title = "Calculator";
        int width = 600, height = 800;

        if (args.length >= 1) title = args[0];
        if (args.length == 2) {
            title = args[0];
            width = Integer.parseInt(args[1]);
            height = Math.round(Float.parseFloat(args[1]) / 0.75f );
        }
        if (args.length == 3) {
            title = args[0];
            width = Integer.parseInt(args[1]);
            height = Integer.parseInt(args[2]);
        }

        Window win = new Window(title, width, height);
    }
}