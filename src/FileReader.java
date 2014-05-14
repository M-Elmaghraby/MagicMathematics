import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader {

	// the file to read from
	private File file;

	// constructor
	public FileReader(String fileName) {
		file = new File(fileName);
	}

	// read a function from a file
	public MathematicalExpression readFunction() throws IOException,
			InvalidInputException {
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			String function = dis.readLine();
			Parser p = new Parser(function);
			return p.parse();
		} catch (Exception e) {
			throw new InvalidInputException("invalid input from file");
		}
	}

	// read a set of points from a file
	public Double[][] readPoints() throws IOException, InvalidInputException {
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			String currentLine = dis.readLine();
			int length = Integer.parseInt(currentLine);
			Double[][] result = new Double[length][2];
			for (int i = 0; i < length; i++) {
				currentLine = dis.readLine();
				String[] res = currentLine.replaceAll(" ", "").split(",");
				result[i][0] = Double.parseDouble(res[0]);
				result[i][1] = Double.parseDouble(res[1]);
			}
			return result;
		} catch (Exception e) {
			throw new InvalidInputException("invalid input from file");
		}
	}

}
