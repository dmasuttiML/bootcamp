package com.company;

public class StringUtil
{
	// Retorna una cadena compuesta por n caracteres c
	// Ejemplo: replicate('x',5) ==> 'xxxxx'
	public static String replicate(char c, int n)
	{
		String str = "";

		for (int i = 0; i < n; i++) {
			str += c;
		}

		return str;
	}

	// Retorna una cadena de longitud n, compuesta por s
	// y precedida de tantos caracteres c como sea necesario
	// para completar la longitud mencionada
	// Ejemplo lpad("5",3,'0') ==> "005"
	public static String lpad(String s, int n, char c)
	{
		int leng = (s.length() < n) ? n - s.length() : 0;
		String str = replicate(c, leng) + s;

		return str;
	}

	// Retorna un String[] conteniendo los elementos de arr
	// representados como cadenas de caracteres
	public static String[] toStringArray(int arr[])
	{
		String[] strArr = new String[arr.length];

		for (int i = 0; i < arr.length; i++) {
			strArr[i] = ((Integer)arr[i]).toString();
		}

		return strArr;
	}

	// Retorna un String[] conteniendo los elementos de arr
	// representados como cadenas de caracteres
	public static int[] toIntArray(String arr[])
	{
		int[] arrInt = new int[arr.length];

		for (int i = 0; i < arr.length; i++) {
			arrInt[i] = Integer.parseInt(arr[i]);
		}

		return arrInt;
	}

	// Retorna la longitud del elemento con mayor cantidad
	// de caracteres del array arr
	public static int maxLength(String arr[])
	{
		int maxLength = 0;

		for (String s: arr) {
			if(s.length() > maxLength) {
				maxLength = s.length();
			}
		}

		return maxLength;
	}

	// Completa los elemento del arr agregando caracteres c
	// a la izquierda, dejando a todos con la longitud del mayor
	public static void lNormalize(String arr[], char c)
	{
		int maxLength = maxLength(arr);

		for (int i = 0; i < arr.length; i++) {
			arr[i] = lpad(arr[i], maxLength, c);
		}
	}

	public static String rpad(String s, int n, char c)
	{
		int leng = (s.length() < n) ? n - s.length() : 0;
		String str = s + replicate(c, leng);

		return str;
	}

	public static String ltrim(String s){
		while (s.length() > 0 && s.charAt(0) == ' '){
			s = s.substring(1);
		}

		return s;
	}

	public static String trim(String s){
		return s.trim();
	}

	public static int indexOfN(String s, char c, int n){
		int index = -1;

		for (int i = 0; i < n; i++) {
			index = s.indexOf(c,index + 1);
			if(index == -1) {
				break;
			}
		}

		return index;
	}

	public static void main(String[] args) {
		System.out.println(rpad("hola",10,'a'));
		System.out.println(ltrim("   hola   ") + "!!!");
		System.out.println(trim("   hola   ") + "!!!");
		System.out.println(indexOfN("John|Paul|George|Ringo", '|', 2));
		System.out.println(indexOfN("John|Paul|George|Ringo", '|', 3));
		System.out.println(indexOfN("John|Paul|George|Ringo", '|', 4));
		System.out.println(indexOfN("John|Paul|George|Ringo", ',', 4));
	}
}
