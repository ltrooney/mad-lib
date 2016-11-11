import java.io.File;

public class Runner {
	public static void main(String[] args) {
		File f1 = new File("madlib-scripts/in/madlib1.txt");
		MadLib ml = new MadLib(f1);
		ml.compileLib();
	}
}
