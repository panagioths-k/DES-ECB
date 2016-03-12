import java.util.*;

public class Key {
	String key_as_hex;
	int[][] Key_K_64 ; //array version: 64 bit
	int[][] Key_K_56 = new int[8][7];; //array version 56 bits after removing parity bits
	int[][] Key_K_56_N = new int[8][7];; //array version 56 bits AFTER permutation with PC1
	
	int[][] L0 = new int[4][7];; //save values after the first split
	int[][] R0 = new int[4][7];; 
	
	
	ArrayList<Int2DArray> L ;
	ArrayList<Int2DArray> R ;
	ArrayList<Int2DArray> K_Subkeys ;
	
	int[][] K_Temp = new int[8][7]; //K'

	public Key(String k)
	{	
		key_as_hex = k;
		L = new ArrayList<Int2DArray>();
		R = new ArrayList<Int2DArray>();
		K_Subkeys = new ArrayList<Int2DArray>();
		
		//Convert hex key to array
		Key_K_64= Convert_16_bits_key_to_2d_array(key_as_hex);
	/**	Key_K_64 = new int[][]{ //array from example
				{1,1,1,0,0,0,0,1 },
				{1,0,1,0,0,1,0,0},
				{1,0,1,1,1,0,1,1 },
			    {1,0,0,1,1,0,1,1},
				{1,1,0,0,0,0,1,0 },
				{1,1,1,0,0,0,1,1 },
				{0,0,1,0,0,1,0,0 },
				{1,0,1,1,1,0,1,1}
				
		};
		**/
	}

	public void Remove_Parity_Bits_From_64_to_56()
	{
		for(int i = 0; i<8; i++)
		{
			for(int j = 0; j<7; j++)
			{
				Key_K_56[i][j] = Key_K_64[i][j];
			}
		}
	}

	public void Split_Key_K_56_N_to_L0_and_R0()
	{
		for(int i = 0; i<4; i++)
			for(int j = 0; j<7; j++)
			{
				L0[i][j] = Key_K_56_N[i][j];
				R0[i][j] = Key_K_56_N [i+4][j];
			}
	}
	
	public int[][] ShiftLeft(int[][] array, int times)
	{
		int[][] shifted = new int[4][7];
		shifted = array;
		for(int l=0;l<times;l++)
		{
			int x00 = shifted[0][0];
			int x10 = shifted[1][0];
			int x20 = shifted[2][0];
			int x30 = shifted[3][0];

			for(int i = 0; i<4; i++)
				for(int j = 0; j<6; j++)
				{
					shifted[i][j] = shifted[i][j+1];
				} 
			shifted[3][6]= x00;  //In case shifting is different
			shifted[2][6]= x30;
			shifted[1][6]= x20;
			shifted[0][6]= x10;	

			//shifted[0][6]= x00;  //bring first column in the end
		//	shifted[1][6]= x10;
			//shifted[2][6]= x20;
			//shifted[3][6]= x30;	
		}
		return shifted;
	}
	
	
	public void Merge_L_and_R_to_K_Temp(int[][] l, int[][] r)
	{
	 
		System.arraycopy(l, 0, K_Temp, 0, l.length);
		System.arraycopy(r, 0, K_Temp, l.length, r.length);
		
		//System.out.println("K_Temp(MERGED):");    
		//Int2DArray.printI2DArray(K_Temp, 8, 7);
	}
	
	
	
	public int[][] Convert_16_bits_key_to_2d_array(String hex_key)//Returns the int[][] array, that contains the key
	{
		int[][] key_arr = new int[8][8];
		if(hex_key.length() !=16)    //Wrong Input
		{
			System.out.println("Input must be 8 characters");
			return key_arr;
		}
		
		for(int i =0;i< 8 ;i++)   //Initialize in case some binary has less than 8 digits
			for(int j =0;j< 8 ;j++)
				key_arr[i][j]=0;
		
		//System.out.println("Converting hex key : "+hex_key+" to array K:"  );

		char[] charArray = hex_key.toCharArray();                                 //from String to charArray
		int row=0;//cant use i because step=+2
		for(int i =0;i< charArray.length ;i=i+2)    //step: +2                   //For each character to convert
		{
			//take a pair of 2 hexadecimal from the key
			String tempstr=String.valueOf(charArray[i])+String.valueOf(charArray[i+1]);        //add pair
			String translatedString = Integer.toBinaryString( Integer.parseInt(tempstr, 16)); //convert pair to binary


			
			int translatedStringIndex=translatedString.length()-1; //start from the end of charArray, save to the end of key_arr
			for(int j=7;j>=0;j--)
			{
				try{
					key_arr[row][j]= Integer.parseInt( String.valueOf( translatedString.charAt(translatedStringIndex)) );	
					translatedStringIndex--;

				}catch(Exception e)
				{
					//System.out.println(e +" at i="+i+ " j="+j +" row="+row);
				};
			}
			row++;
		}
		System.out.println("DONE");
		return key_arr;
	}
	
	
	
	
	
	
}
