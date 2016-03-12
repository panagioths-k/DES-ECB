public class Function {
	
	int[][] R_i_minus_1 = new int[8][4];//R(i-1)
	int[][] Expansion = new int[][]{
			{32, 1, 2, 3, 4, 5},
			{ 4, 5, 6, 7, 8, 9},
			{8, 9, 10, 11, 12, 13},
			{12, 13, 14, 15, 16, 17},
			{16, 17, 18, 19, 20, 21},
			{20, 21, 22, 23, 24, 25},
			{24, 25, 26, 27, 28, 29},
			{28, 29, 30, 31, 32, 1},
			};
	int[][] E_R_i_minus_1 = new int[8][6];//expanded R(i-1)
	int[][] Ki = new int[8][6];
	
	int[] B1 = new int[6]; int[] B1_New = new int[4];
	int[] B2 = new int[6]; int[] B2_New = new int[4];
	int[] B3 = new int[6]; int[] B3_New = new int[4];
	int[] B4 = new int[6]; int[] B4_New = new int[4];
	int[] B5 = new int[6]; int[] B5_New = new int[4];
	int[] B6 = new int[6]; int[] B6_New = new int[4];
	int[] B7 = new int[6]; int[] B7_New = new int[4];
	int[] B8 = new int[6]; int[] B8_New = new int[4];
	//B arrays hold the result of XOR
	//B_New arrays hold the result of S-Boxes
	 
	
	int[][] S_Box_1 = new int[][]{
		  { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
		  {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
		  {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
	      {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13},
		};
	
	int[][] S_Box_2 = new int[][]{
			  { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
		      { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
			  { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
			  { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9},
			};
	
	
	int[][] S_Box_3 = new int[][]{
			  { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
			  { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
			  { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
			  { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12},
			};
	
	int[][] S_Box_4 = new int[][]{
			  { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
			  { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
			  { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
			  { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14},
			};
	
	int[][] S_Box_5 = new int[][]{
			  { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
			  { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
			  { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
			  { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3},
			};
	
	int[][] S_Box_6 = new int[][]{
			  { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
			  { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
			  { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
			  { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13},
			};
	
	
	int[][] S_Box_7 = new int[][]{
			  { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
			  { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
			  { 1, 4 ,11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
			  { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12},
			};


	int[][] S_Box_8 = new int[][]{
			  { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
			  { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
			  { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
			  { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11},
			};
	
	
	int[][] R = new int[8][4];// the concatenation of B_8_parts in one array[8][4]
	int[][] P = new int[][]{
			{16, 7, 20, 21},
			{29, 12, 28, 17},
			{1,  15, 23, 26},
			{5,  18, 31, 10},
			{2,  8, 24, 14},
			{32, 27, 3, 9},
			{19, 13, 30, 6},
			{22, 11, 4, 25},
			};
	
	int[][] result = new int[8][4];//permutation between R and P
	
	public Function(int[][] _r_i_minus_1, int[][] _ki )
	{
		R_i_minus_1 = _r_i_minus_1;
		Ki = _ki;
	}
	
	public int[][] GenerateResult()//calls the correct methods with the right order to create the result array
	{
		//Set prices to E_R_i_minus_1 (convert R[i] from 32 to 48 bits)
		Expand_R_from_32_to_48_bits();
		System.out.println("Expansion R -1:");
		Int2DArray.printI2DArray(E_R_i_minus_1, 8, 6); 
		
		//Perform XOR between Expanded R(i-1) and Ki 
		XOR_E_R_minus_1_With_Ki();
		
		//S-Boxes
		Substitution_boxes();
		
		//Permutation R with P
		int[][] returnarray = new int[8][4];//array returned from the permutation of R with P 
		returnarray= Permute_R_with_P_8x4();
		return returnarray;
	}
	
	public void Expand_R_from_32_to_48_bits()
	{
		//arrays we need: E_R(i-1), R(i-1), Expansion
         //TO APOTELESMA MAS GRAFETAI ston pinaka E_R_i_minus_i pou einai 8x6
		for(int i =0;i< 8 ;i++)
			for(int j =0;j< 6 ;j++)
			{                             //row: _indices/4    collumn: _indices mod 4
				//System.out.println("i: "+i+",j: "+j+" index: "+(Expansion[i][j]-1)+" price: "+
			     //   ( R_i_minus_1[(Expansion[i][j]-1)/4 ][ (Expansion[i][j]-1)%4 ] )+" row: "+
						    //   ((Expansion[i][j]-1)/4) +" collumn: "+( (Expansion[i][j]-1)%4) );
               try{
				E_R_i_minus_1[i][j] = R_i_minus_1[(Expansion[i][j]-1)/4 ][ (Expansion[i][j]-1)%4] ;
               }
               catch(Exception e){
            	   System.out.println("RRROR !!! i: "+i+",j: "+j+" index: "+(Expansion[i][j]-1)+" price: "+
       			        ( R_i_minus_1[(Expansion[i][j]-1)/4 ][ (Expansion[i][j]-1)%4 ] )+" row: "+
       						       ((Expansion[i][j]-1)/4) +" collumn: "+( (Expansion[i][j]-1)%4) );
               }

			}
	}
	
	
	public void XOR_E_R_minus_1_With_Ki()
	{      
		 
		
		for(int j = 0; j<6; j++)  //1rst octal
			B1[j]= E_R_i_minus_1[0][j] ^ Ki[0][j];

		for(int j = 0; j<6; j++)  //2nd octal
			B2[j]= E_R_i_minus_1[1][j] ^ Ki[1][j];
		
		for(int j = 0; j<6; j++)  //3rd octal
			B3[j]= E_R_i_minus_1[2][j] ^ Ki[2][j];
		
		for(int j = 0; j<6; j++)  //4rth octal
			B4[j]= E_R_i_minus_1[3][j] ^ Ki[3][j];
		
		for(int j = 0; j<6; j++)  //5fth octal
			B5[j]= E_R_i_minus_1[4][j] ^ Ki[4][j];
		
		for(int j = 0; j<6; j++)  //6th octal
			B6[j]= E_R_i_minus_1[5][j] ^ Ki[5][j];
		
		for(int j = 0; j<6; j++)  //7th octal
			B7[j]= E_R_i_minus_1[6][j] ^ Ki[6][j];
		
		for(int j = 0; j<6; j++)  //8th octal
			B8[j]= E_R_i_minus_1[7][j] ^ Ki[7][j];
	
		System.out.println("Octals:");
		for(int j = 0; j<6; j++)
			System.out.println(B8[j]);
	}
	
	
	public void Substitution_boxes()
	{
		int digit1;    
		int digit2;
		int digit3;     
		int digit4;
		String binaryStr;
		int row, column;//the row and column on the current S-Box
		
		for(int k=0;k<4;k++) //arxikopoioume giati den exoyn ola ta dyadika idio megethos 
		{
			B1_New[k]=0;
			B2_New[k]=0;
			B3_New[k]=0;
			B4_New[k]=0;
			B5_New[k]=0;
			B6_New[k]=0;
			B7_New[k]=0;
			B8_New[k]=0;	
		}

		
		//For S-Box1
		 digit1= B1[0]*((int) Math.pow(2, 1));    //Get Row
		 digit2= B1[5]*((int) Math.pow(2, 0));
		 row   = digit1+digit2;
		 		
		 digit1= B1[1]*((int) Math.pow(2, 3));    //Get Column
		 digit2= B1[2]*((int) Math.pow(2, 2));
		 digit3= B1[3]*((int) Math.pow(2, 1));    
		 digit4= B1[4]*((int) Math.pow(2, 0)); 
		 column = digit1+digit2+digit3+digit4;
		 
		 binaryStr = Integer.toString(S_Box_1[row][column] , 2); //Get number of S-Box array in 0 and 1 in integer
		// System.out.println ("BinStr "+binaryStr);
		 
         //SAVE IN ARRAY BY READING FROM THE LEFT SIDE OF binaryStr
		 for(int k=binaryStr.length()-1;k>=0;k--)                  //save in array
			 B1_New[binaryStr.length()-1 -k]= Integer.parseInt( String.valueOf( binaryStr.charAt(k) ));
		 
		// System.out.println("row: "+row+" column: "+column+" value: "+S_Box_1[row][column]+" binary: "+binaryStr);
		// for(int k=0;k<B1_New.length;k++)
			// System.out.println (k+": "+B1_New[k] );
			
		 
		//For S-Box2
		 digit1= B2[0]*((int) Math.pow(2, 1));    //Get Row
		 digit2= B2[5]*((int) Math.pow(2, 0));
		 row   = digit1+digit2;
		 		
		 digit1= B2[1]*((int) Math.pow(2, 3));    //Get Column
		 digit2= B2[2]*((int) Math.pow(2, 2));
		 digit3= B2[3]*((int) Math.pow(2, 1));    
		 digit4= B2[4]*((int) Math.pow(2, 0)); 
		 column = digit1+digit2+digit3+digit4;
		 
		 binaryStr = Integer.toString(S_Box_2[row][column] , 2); //Get number of S-Box array in 0 and 1 in integer
		 
		 for(int k=binaryStr.length()-1;k>=0;k--)                  //save in array
			 B2_New[binaryStr.length()-1 -k]= Integer.parseInt( String.valueOf( binaryStr.charAt(k) ));
		 
		// System.out.println("row: "+row+" column: "+column+" value: "+S_Box_2[row][column]+" binary: "+binaryStr);
		// for(int k=0;k<B2_New.length;k++)
			// System.out.println (k+": "+B2_New[k] );
		 
		 
		 
		//For S-Box3
		 digit1= B3[0]*((int) Math.pow(2, 1));    //Get Row
		 digit2= B3[5]*((int) Math.pow(2, 0));
		 row   = digit1+digit2;
		 		
		 digit1= B3[1]*((int) Math.pow(2, 3));    //Get Column
		 digit2= B3[2]*((int) Math.pow(2, 2));
		 digit3= B3[3]*((int) Math.pow(2, 1));    
		 digit4= B3[4]*((int) Math.pow(2, 0)); 
		 column = digit1+digit2+digit3+digit4;
		 
		 binaryStr = Integer.toString(S_Box_3[row][column] , 2); //Get number of S-Box array in 0 and 1 in integer
		 
		 for(int k=binaryStr.length()-1;k>=0;k--)                  //save in array
			 B3_New[binaryStr.length()-1 -k]= Integer.parseInt( String.valueOf( binaryStr.charAt(k) ));
		 
		// System.out.println("row: "+row+" column: "+column+" value: "+S_Box_3[row][column]+" binary: "+binaryStr);
		// for(int k=0;k<B3_New.length;k++)
		//	 System.out.println (k+": "+B3_New[k] );
		 
		 
		 
		//For S-Box4
		 digit1= B4[0]*((int) Math.pow(2, 1));    //Get Row
		 digit2= B1[5]*((int) Math.pow(2, 0));
		 row   = digit1+digit2;
		 		
		 digit1= B4[1]*((int) Math.pow(2, 3));    //Get Column
		 digit2= B4[2]*((int) Math.pow(2, 2));
		 digit3= B4[3]*((int) Math.pow(2, 1));    
		 digit4= B4[4]*((int) Math.pow(2, 0)); 
		 column = digit1+digit2+digit3+digit4;
		 
		 binaryStr = Integer.toString(S_Box_4[row][column] , 2); //Get number of S-Box array in 0 and 1 in integer
		 
		 for(int k=binaryStr.length()-1;k>=0;k--)                  //save in array
			 B4_New[binaryStr.length()-1 -k]= Integer.parseInt( String.valueOf( binaryStr.charAt(k) ));
		 
		// System.out.println("row: "+row+" column: "+column+" value: "+S_Box_4[row][column]+" binary: "+binaryStr);
		// for(int k=0;k<B4_New.length;k++)
		//	 System.out.println (k+": "+B4_New[k] );
		 
		 
		 
		//For S-Box5
		 digit1= B5[0]*((int) Math.pow(2, 1));    //Get Row
		 digit2= B5[5]*((int) Math.pow(2, 0));
		 row   = digit1+digit2;
		 		
		 digit1= B5[1]*((int) Math.pow(2, 3));    //Get Column
		 digit2= B5[2]*((int) Math.pow(2, 2));
		 digit3= B5[3]*((int) Math.pow(2, 1));    
		 digit4= B5[4]*((int) Math.pow(2, 0)); 
		 column = digit1+digit2+digit3+digit4;
		 
		 binaryStr = Integer.toString(S_Box_5[row][column] , 2); //Get number of S-Box array in 0 and 1 in integer
		 
		 for(int k=binaryStr.length()-1;k>=0;k--)                  //save in array
			 B5_New[binaryStr.length()-1 -k]= Integer.parseInt( String.valueOf( binaryStr.charAt(k) ));
		 
		// System.out.println("row: "+row+" column: "+column+" value: "+S_Box_5[row][column]+" binary: "+binaryStr);
		// for(int k=0;k<B5_New.length;k++)
		//	 System.out.println (k+": "+B5_New[k] );
		 
		 
		 
		//For S-Box6
		 digit1= B6[0]*((int) Math.pow(2, 1));    //Get Row
		 digit2= B6[5]*((int) Math.pow(2, 0));
		 row   = digit1+digit2;
		 		
		 digit1= B6[1]*((int) Math.pow(2, 3));    //Get Column
		 digit2= B6[2]*((int) Math.pow(2, 2));
		 digit3= B6[3]*((int) Math.pow(2, 1));    
		 digit4= B6[4]*((int) Math.pow(2, 0)); 
		 column = digit1+digit2+digit3+digit4;
		 
		 binaryStr = Integer.toString(S_Box_6[row][column] , 2); //Get number of S-Box array in 0 and 1 in integer
		 
		 for(int k=binaryStr.length()-1;k>=0;k--)                  //save in array
			 B6_New[binaryStr.length()-1 -k]= Integer.parseInt( String.valueOf( binaryStr.charAt(k) ));
		 
		// System.out.println("row: "+row+" column: "+column+" value: "+S_Box_6[row][column]+" binary: "+binaryStr);
		// for(int k=0;k<B6_New.length;k++)
		//	 System.out.println (k+": "+B6_New[k] );
		 
		 
		 
		 
		//For S-Box7
		 digit1= B7[0]*((int) Math.pow(2, 1));    //Get Row
		 digit2= B7[5]*((int) Math.pow(2, 0));
		 row   = digit1+digit2;
		 		
		 digit1= B7[1]*((int) Math.pow(2, 3));    //Get Column
		 digit2= B7[2]*((int) Math.pow(2, 2));
		 digit3= B7[3]*((int) Math.pow(2, 1));    
		 digit4= B7[4]*((int) Math.pow(2, 0)); 
		 column = digit1+digit2+digit3+digit4;
		 
		 binaryStr = Integer.toString(S_Box_7[row][column] , 2); //Get number of S-Box array in 0 and 1 in integer
		 
		 for(int k=binaryStr.length()-1;k>=0;k--)                  //save in array
			 B7_New[binaryStr.length()-1 -k]= Integer.parseInt( String.valueOf( binaryStr.charAt(k) ));
		 
		// System.out.println("row: "+row+" column: "+column+" value: "+S_Box_7[row][column]+" binary: "+binaryStr);
		// for(int k=0;k<B7_New.length;k++)
		//	 System.out.println (k+": "+B7_New[k] );
		 
		 
		//For S-Box8
		 digit1= B8[0]*((int) Math.pow(2, 1));    //Get Row
		 digit2= B8[5]*((int) Math.pow(2, 0));
		 row   = digit1+digit2;
		 		
		 digit1= B8[1]*((int) Math.pow(2, 3));    //Get Column
		 digit2= B8[2]*((int) Math.pow(2, 2));
		 digit3= B8[3]*((int) Math.pow(2, 1));    
		 digit4= B8[4]*((int) Math.pow(2, 0)); 
		 column = digit1+digit2+digit3+digit4;
		 
		 binaryStr = Integer.toString(S_Box_8[row][column] , 2); //Get number of S-Box array in 0 and 1 in integer
		 
		 for(int k=binaryStr.length()-1;k>=0;k--)                  //save in array
			 B8_New[binaryStr.length()-1 -k]= Integer.parseInt( String.valueOf( binaryStr.charAt(k) ));
		 
		 //System.out.println("row: "+row+" column: "+column+" value: "+S_Box_8[row][column]+" binary: "+binaryStr);
		// for(int k=0;k<B8_New.length;k++)
		//	 System.out.println (k+": "+B8_New[k] );
		 
		 
		Concatenate_B_New_8_parts_in_R();
	}
	

	public void Concatenate_B_New_8_parts_in_R ()
	{
	    
		System.out.println ("Concatination");
		//For index 0
		for(int k=0;k<4;k++)  //write from the right (side of binary) to the left (side of the array)
			R[0][k]=B1_New[3-k];

		
		//For index 1
		for(int k=0;k<4;k++)
			R[1][k]=B2_New[3-k];

		//For index 2
		for(int k=0;k<4;k++)
			R[2][k]=B3_New[3-k];

		//For index 3
		for(int k=0;k<4;k++)
			R[3][k]=B4_New[3-k];

		//For index 4
		for(int k=0;k<4;k++)
			R[4][k]=B5_New[3-k];

		//For index 5
		for(int k=0;k<4;k++)
			R[5][k]=B6_New[3-k];

		//For index 6
		for(int k=0;k<4;k++)
			R[6][k]=B7_New[3-k];

		//For index 7
		for(int k=0;k<4;k++)
			R[7][k]=B8_New[3-k];
		
		//Int2DArray.printI2DArray(R, 8, 4);
	}
	
	
	public int[][] Permute_R_with_P_8x4()
	{
		//prices: R   indices: P
		int[][] result = new int[8][4];
		for(int i =0;i< 8 ;i++)
			for(int j =0;j< 4 ;j++)
			{                             //row: _indices/4    collumn: _indices mod 4
				//System.out.println("i: "+i+",j: "+j+" index: "+(P[i][j]-1)+" price: "+
					//	( R[(P[i][j]-1)/4 ][ (P[i][j]-1)%4 ] )+" row: "+
					//	((P[i][j]-1)/4) +" collumn: "+( (P[i][j]-1)%4) );
				try{
					result[i][j] = R[(P[i][j]-1)/4 ][ (P[i][j]-1)%4] ;
				}
				catch(Exception e){
					System.out.println("ERROR !!! i: "+i+",j: "+j+" index: "+(P[i][j]-1)+" price: "+
							( R[(P[i][j]-1)/4 ][ (P[i][j]-1)%4 ] )+" row: "+
							((P[i][j]-1)/4) +" collumn: "+( (P[i][j]-1)%4) );
				}

			}
		System.out.println("R permutation P:");
		Int2DArray.printI2DArray(result, 8, 4);
		return result;
	}

	public int[][] XOR_Between_8x4_int_arrays(int[][] arr1, int[][] arr2)
	{
		int[][] XORresult = new int[8][4];
		for(int i =0;i< 8 ;i++)
			for(int j =0;j< 4 ;j++) 
				XORresult[i][j] = arr1[i][j] ^ arr2[i][j];
		
		return XORresult;
	}
}
