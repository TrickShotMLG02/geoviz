package geoviz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

	/**
	 * @param file
	 * @return Arraylist of all points loaded from the given file
	 */
	@SuppressWarnings("resource")
	public static List<MyPoint> loadPoints(File file) {
		List<MyPoint> points = new ArrayList<MyPoint>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			while ((line = br.readLine()) != null) {
				// remove blanks
				line = line.replaceAll("\\s+", "");
				// split into coordinates
				String[] wordsInLine = line.split(",");

				points.add(new MyPoint(Double.parseDouble(wordsInLine[0]), Double.parseDouble(wordsInLine[1])));
			}

		} catch (Exception e) {
		}
		return points;
	}
	
	/**
	 * @param path
	 * @return Arraylist of all points loaded from the file with the given path
	 */
	public static List<MyPoint> loadPoints(String path)
	{
		return loadPoints(new File(path));
	}

}
