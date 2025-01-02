import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        // Read JSON Input
        JSONTokener tokener = new JSONTokener(new FileReader("input.json"));
        JSONObject json = new JSONObject(tokener);

        // Extract n, k
        JSONObject keys = json.getJSONObject("keys");
        int n = keys.getInt("n");
        int k = keys.getInt("k");

        // Decode roots (x, y)
        List<int[]> points = new ArrayList<>();
        for (String key : json.keySet()) {
            if (!key.equals("keys")) {
                JSONObject root = json.getJSONObject(key);
                int x = Integer.parseInt(key);
                int y = Integer.parseInt(root.getString("value"), Integer.parseInt(root.getString("base")));
                points.add(new int[]{x, y});
            }
        }

        // Solve for constant term 'c' using Lagrange Interpolation
        double c = findConstant(points, k);
        System.out.println("Secret (c): " + (int) c);
    }

    private static double findConstant(List<int[]> points, int k) {
        double c = 0.0;

        for (int i = 0; i < k; i++) {
            double li = 1.0;
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    li *= (0.0 - points.get(j)[0]) / (points.get(i)[0] - points.get(j)[0]);
                }
            }
            c += li * points.get(i)[1];
        }

        return c;
    }
}