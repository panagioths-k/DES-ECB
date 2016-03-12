
public class Int2DArray {
	int rows;
	int columns;
	int[][] array;
	
	public Int2DArray(int _rows, int _columns) //creates empty
	{
		rows= _rows;
		columns = _columns;
		array = new int[rows][columns];
	}
	
	public Int2DArray(int[][] _array, int _rows, int _columns)         //creates with values
	{
		rows= _rows;
		columns = _columns;
		array = _array;
	}
	
	
	public static void printI2DArray(int[][] array, int grammes, int sthles)
	{
		System.out.println("Result:");
		for(int i = 0; i<grammes; i++)
		{
		    for(int j = 0; j<sthles; j++)
		    {
		        System.out.print(array[i][j]);
		    }
		    System.out.println();
		}
	}
	
	public static String GetArrayAsText(int[][] array, int rows, int collumns)
	{
		String str="";
		for(int i = 0; i<rows; i++)
		{
		    for(int j = 0; j<collumns; j++)
		       str+= array[i][j]+" ";
		   str+="\n";
		}
		return str;
	}
	
	//We need to print B[1], B[2],...,B[8] 
	public static String Get8OneDimensionArraysAsText(int[] b1,int[] b2,int[] b3,int[] b4,int[] b5,int[] b6,int[] b7,int[] b8, int column)
	{
		String str="";
        //b1
		str+= "B1=";
		for(int i = 0; i<column; i++)
		{
			str +=b1[i]+" ";
		}
		str+="\n";

		//b2
		str+= "B2=";
		for(int i = 0; i<column; i++)
		{
			str +=b2[i]+" ";
		}
		str+="\n";

		//b3
		str+= "B3=";
		for(int i = 0; i<column; i++)
		{
			str +=b3[i]+" ";
		}
		str+="\n";

		//b4
		str+= "B4=";
		for(int i = 0; i<column; i++)
		{
			str +=b4[i]+" ";
		}
		str+="\n";

		//b5
		str+= "B5=";
		for(int i = 0; i<column; i++)
		{
			str +=b5[i]+" ";
		}
		str+="\n";

		//b6
		str+= "B6=";
		for(int i = 0; i<column; i++)
		{
			str +=b6[i]+" ";
		}
		str+="\n";

		//b7
		str+= "B7=";
		for(int i = 0; i<column; i++)
		{
			str +=b7[i]+" ";
		}
		str+="\n";

		//b8
		str+= "B8=";
		for(int i = 0; i<column; i++)
		{
			str +=b8[i]+" ";
		}
		str+="\n";
		

		return str;
	}

	public static int[][] Merge_8x4_arrays(int[][] arr1, int[][] arr2)
	{
		int[][] MergedArr = new int [8][8];
		Int2DArray.printI2DArray(arr1, 8, 4);
		Int2DArray.printI2DArray(arr2, 8, 4);
		
		for(int i = 0; i<8; i++)
			for(int j = 0; j<4; j++)
			{
				MergedArr[i][j]=arr1[i][j];
				MergedArr[i][j+4 ]=arr2[i][j];

			}
		return MergedArr;
	}
	
	public static String Convert_Back_To_ASCII(int[][] arr, int rows, int columns)
	{
		String totalStr="";
		for(int i =0;i< rows ;i++)
		{
			//Convert from binary to ascii
			String from_array_to_string="";
			for(int j =0;j< columns ;j++)
			{ 
				from_array_to_string += arr[i][j];
			}
			int from_string_to_binary_int = Integer.parseInt(from_array_to_string, 2);

			//Then if you want the corresponding character as a string:
			String str = new Character((char)from_string_to_binary_int).toString();
		    totalStr+=str;
		}
		return totalStr;
	}
	
	public static int[][] Convert_plain_text_to_binary(String str)//Returns the int[][] array, one character converted at a row
	{
		int[][] converted = new int[8][8];
		if(str.length() !=8)    //Wrong Input
		{
			System.out.println("Input must be 8 characters");
			return converted;
		}
		
		for(int i =0;i< 8 ;i++)   //Initialize in case some binary has less than 8 digits
			for(int j =0;j< 8 ;j++)
				converted[i][j]=0;
		
		//System.out.println("Converting input: "+str+" to binary:"  );

		char[] charArray = str.toCharArray();                                     //from String to charArray
		for(int i =0;i< charArray.length ;i++)                                    //For each character to convert
		{
			String translatedString = Integer.toBinaryString((int) charArray[i]); //get current character to convert
			//System.out.println(charArray[i]+" to binary is:" +translatedString);
			
			int translatedStringIndex=translatedString.length()-1; //starting from the end of charArray and save to the end of converted 2d array
			for(int j=str.length()-1;j>=0;j--)
			{
				try
				{
					converted[i][j] = Integer.parseInt( String.valueOf( translatedString.charAt(  translatedStringIndex) ) );
					translatedStringIndex--;
				}catch(Exception e)
				{
					//do nothing
				};
			}
		}
		return converted;
	}	
}
