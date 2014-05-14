import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.naming.spi.DirStateFactory.Result;

//that class is used to parse a function to it's mathematical expression
public class Parser {

	// the STring that needs parsing
	private String expression;

	// constructor
	public Parser(String s) {
		expression = s;
	}

	// the splitting method
	// the method splits the string by a special group of characters at any
	// occurrence of any of them but ignores an
	// occurrence of that character with in the brackets()
	private String[] splitString(String toSplit, char[] splitters) {
		LinkedList<String> result = new LinkedList<String>();
		// array of chars of the String to be split
		char[] charArray = toSplit.toCharArray();
		// the length of that array
		int limit = charArray.length;
		// that integer holds the index of the occurrence of the any of the
		// splitter characters last time
		int lastIndex = 0;
		// Boolean to detect if a bracket were found
		boolean bracketFound = false;
		for (int i = 0; i < limit; i++) {
			if (charArray[i] == '(')
				bracketFound = true;
			if (!bracketFound && contains(charArray[i], splitters)) {
				result.add(toSplit.substring(lastIndex, i));
				lastIndex = i;
			}
			if (charArray[i] == ')')
				bracketFound = false;
		}
		result.add(toSplit.substring(lastIndex, limit));
		String[] res = new String[result.size()];
		result.toArray(res);
		return res;

	}

	// this method checks if a given array contains a given char or not
	private boolean contains(char c, char[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == c)
				return true;
		}
		return false;
	}

	// the main parsing method
	// the input String is in form of SUM OF PRODUCTS
	public MathematicalExpression parse() throws InvalidInputException {
		try {
			ArrayList<Object> result = new ArrayList<Object>();
			// erasing all the spaces from the input String
			expression = expression.replaceAll(" ", "");
			// getting the products of the input
			String[] products = splitString(expression, new char[] { '+', '-' });
			// the length of the products
			int limit = products.length;
			// building the Mathematical Expression
			for (int i = 0; i < limit; i++) {
				// hold the main parts of each product
				String[] elements = splitString(products[i], new char[] { '*' });
				// adding each element to the result
				int elementsLength = elements.length;
				for (int j = 0; j < elementsLength; j++) {
					// the first character in each element
					char first = elements[j].charAt(0);
					// adding the operator before each element
					if (first == '+' || first == '-' || first == '*') {
						if (first != '*')
							result.add(new Operator(first));
						elements[j] = elements[j].substring(1);
						first = elements[j].charAt(0);
					}
					// checking which kind of available functions is that
					if (first == 'S' || first == 's') {
						// for Sin
						result.add(new Sin(polyParse(elements[j].substring(4,
								elements[j].length() - 1))));
					} else if (first == 'C' || first == 'c') {
						// for Cos
						result.add(new Cos(polyParse(elements[j].substring(4,
								elements[j].length() - 1))));
					} else if (first == 'E' || first == 'e') {
						// //for e
						result.add(new e(polyParse(elements[j].substring(3,
								elements[j].length() - 1))));
					} else if (first == '(') {
						// for a Polynomial with brackets
						result.add(polyParse(elements[j].substring(1,
								elements[j].length() - 1)));
					} else {
						// for a polynomial without Brackets
						result.add(polyParse(elements[j].substring(0,
								elements[j].length())));
					}
				}
			}

			return new MathematicalExpression(result);
		} catch (Exception e) {
			throw new InvalidInputException("Invalid Input");
		}
	}

	// the method to validate an input Polynomial
	private boolean check(String input) {
		String exp = input.toLowerCase().replace(" ", "");
		String pattern = "((-\\d+|\\d+|-|\\d*\\.\\d+|)(x\\^\\d+|x|)(\\+(-\\d+|\\d+|-|\\d*\\.\\d+|)|\\-(-\\d+|\\d+|-|\\d*\\.\\d+|)|))*";
		if (exp.matches(pattern)) {
			return true;
		}
		return false;
	}

	// the method is used to parse a polynomial String
	private Polynomial polyParse(String poly) throws InvalidInputException {
		// validating the String
		if (!check(poly))
			throw new InvalidInputException("INVALID POLYNOMIAL " + poly);
		// the polynomial terms
		String[] polyTerms = splitString(poly, new char[] { '+', '-' });
		// the negative numbers like -1
		// the Polynomial result
		Hashtable<Integer, Double> result = new Hashtable<Integer, Double>();
		// that integer holds the polynomial degree
		int degree = 0;
		for (int i = 0; i < polyTerms.length; i++) {
			if (polyTerms[i].length() != 0) {
				String curretTerm = polyTerms[i];
				// getting the coefficient and the power of the Polynomial terms
				char xChar;
				if (curretTerm.contains("X")) {
					xChar = 'X';
				} else {
					xChar = 'x';
				}
				String[] stuff = curretTerm.split(xChar + "\\^");
				if (stuff.length != 1) {
					result.put(
							Integer.parseInt(stuff[1].charAt(0) == '+' ? stuff[1]
									.substring(1) : stuff[1]), Double
									.parseDouble(stuff[0].length() == 0 ? "1"
											: stuff[0]));
					if (Integer.parseInt(stuff[1].charAt(0) == '+' ? stuff[1]
							.substring(1) : stuff[1]) > degree)
						degree = Integer.parseInt(stuff[1]);
				} else {
					if ((stuff[0].contains(xChar + "") ? 1 : 0) > degree)
						degree = stuff[0].contains(xChar + "") ? 1 : 0;
					result.put(
							stuff[0].contains("" + xChar) ? 1 : 0,
							Double.parseDouble(stuff[0].contains(xChar + "") ? stuff[0]
									.length() == 1 ? "1"
									: stuff[0].charAt(0) == '-' ? "-1"
											: stuff[0].substring(0,
													stuff[0].length() - 1)
									: stuff[0]));
				}
			}
		}
		// filling the coefficient array for the Polynomial constructor
		double[] coeff = new double[degree + 1];
		// assigning the proper values
		for (int i = 0; i < coeff.length; i++) {
			if (result.containsKey(new Integer(i)))
				coeff[i] = result.get(new Integer(i));
			else
				coeff[i] = 0.0;
		}
		return new Polynomial(coeff);
	}
}
