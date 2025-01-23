import java.util.*;
public class IntegerArrayListExample {
    public static void lines(int x) {
        for(int z = 1; z<= x; z++) {
			System.out.print("-");
		}
		System.out.print(" ");
	}
	public static void main(String[] args) {
		ArrayList<String> names = new ArrayList<>();
		
		names.add("President");
		names.add("Vice President");
		names.add("Secretary");
		names.add("Treasurer");
		names.add("Muse");
		names.add("Escort");
		for (int i=0; i<= names.size(); i++) {
			System.out.println(names.get(i));
		}
		System.out.println("Programmed by: Bautista");
	}
}

