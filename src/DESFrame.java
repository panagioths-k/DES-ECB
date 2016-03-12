import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;


public class DESFrame extends JFrame {

	private JPanel contentPane;
	private JTextArea Info;    //info about current arrays and actions
	private JTextArea Area1;   //areas for arrays and data
	private JTextArea Area2;
	private JTextArea Area3;
	private JButton Next ;
	private JButton Finish;
	private boolean pressed = false;
	private boolean global_flag=true;
	Key key ;
	
	int[][] Input_Block_X;
	
	int[][] Initial_Permutation_matrix = new int[][]{
		  { 58, 50, 42, 34, 26, 18, 10, 2},
		  { 60, 52, 44, 36, 28, 20, 12, 4},
		  { 62, 54, 46, 38, 30, 22, 14, 6},
		  { 64,56, 48, 40, 32, 24,16, 8},
		  { 57, 49, 41, 33, 25, 17, 9, 1},
		  { 59, 51, 43, 35, 27, 19, 11, 3},
		  { 61, 53, 45, 37, 29, 21, 13, 5},
		  { 63, 55,47, 39, 31, 23, 15, 7}
		};
	
	int[][] Permuted_choise1 = new int[][]{
			{ 57, 49, 41, 33, 25, 17, 9},
			  { 1, 58, 50, 42, 34, 26, 18},
			  { 10, 2, 59, 51, 43, 35, 27},
			  { 19, 11, 3, 60, 52, 44,36},
			  { 63, 55, 47, 39, 31, 23, 15},
			  { 7, 62, 54, 46, 38, 30, 22},
			  { 14, 6, 61, 53, 45, 37, 29},
			  { 21, 13, 5, 28, 20, 12, 4}
			};	
	
	int[][] Permuted_choise2 = new int[][]{ //8x6
			{ 14, 17, 11, 24, 1, 5},
			  { 3, 28, 15, 6, 21, 10},
			  { 23, 19, 12, 4, 26, 8},
			  { 16, 7, 27, 20, 13, 2},
			  { 41, 52, 31, 37, 47, 55},
			  { 30, 40, 51, 45, 33, 48},
			  { 44, 49, 39, 56, 34, 53},
			  { 46, 42, 50, 36, 29, 32}
			};	
	
	int[][] Permuted_input = new int [8][8];
	int[][] L = new int[8][4] ;//L and R by Permuted_input( different than Key arrays )
	int[][] R = new int[8][4] ;
	ArrayList<Int2DArray> Llist = new ArrayList<Int2DArray>();//these lists come from the permuted input
	ArrayList<Int2DArray> Rlist = new ArrayList<Int2DArray>();//and they are different from the keys
	
	
	int[] Left_Shifts =   {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
	int[][]Pre_Output = new int[8][8];
	
	
	int[][]Initial_Permutation_Reversed = new int[][]{
			  { 40, 8, 48, 16, 56, 24, 64, 32},
			  { 39, 7, 47, 15, 55, 23, 63, 31},
			  { 38, 6, 46, 14, 54, 22, 62, 30},
			  { 37, 5, 45, 13, 53, 21, 61, 29},
			  { 36, 4, 44, 12, 52, 20, 60, 28},
			  { 35, 3, 43, 11, 51, 19, 59, 27},
			  { 34, 2, 42, 10, 50, 18, 58, 26},
			  { 33, 1, 41, 9, 49, 17, 57, 25 }
			};
	
	int[][] Output_block_Y = new int[8][8];
	public DESFrame() 
	{
		//Stuff about window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		setTitle("DES(ECB)");
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Info = new JTextArea();
		Info.setBackground(Color.LIGHT_GRAY);
		Info.setBounds(0, 0, 584, 32);
		Info.setEditable(false);
		contentPane.add(Info);
		
		Area1 = new JTextArea();
		Area1.setBackground(SystemColor.controlHighlight);
		Area1.setBounds(0, 31, 299, 221);
		contentPane.add(Area1);
		
		Area2 = new JTextArea();
		Area2.setBackground(SystemColor.controlHighlight);
		Area2.setBounds(301, 31, 283, 221);
		contentPane.add(Area2);
		
		Area3 = new JTextArea();
		Area3.setBackground(SystemColor.controlHighlight);
		Area3.setBounds(0, 253, 584, 172);
		contentPane.add(Area3);
		
		Next = new JButton("Next");
		Next.setBounds(232, 427, 89, 23);
		contentPane.add(Next);
		Next.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(global_flag)
					pressed=true;
			}
		} );
		
		Finish = new JButton("Finish");
		Finish.setForeground(Color.WHITE);
		Finish.setBackground(Color.BLACK);
		Finish.setBounds(337, 427, 89, 23);
		contentPane.add(Finish);
		Finish.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				global_flag=false;
				
			}
		});

		this.setVisible(true);

		//Write starting text
		Info.setText("Welcome to DES Encryption. We are going to encrypt: 'Kotsikor' using key: 'F49F989455498554' ");
		StandByButton();
		
		
		//Convert input to binary
		String myText="Kotsikor";
		Input_Block_X = Int2DArray.Convert_plain_text_to_binary(myText);
	/**	Input_Block_X = new int[][]{
				{ 0, 0, 0 ,1 ,0 ,1 ,1 ,0 },
				  { 1,0,1,1,1,1,0,1},
				  { 0,1,0,0,0,0,0,0},
				  { 1,0,0,1,0,1,0,0},
				  { 1,0,1,1,0,1,0,0},
				  { 0,1,0,1,0,0,1,1},
				  { 1,0,0,0,1,0,1,0},
				  { 0,1,1,1,1,0,1,1 }
		};
		
		**/
		
		//Write Input_Block_X in area 1
		Info.setText("Convert: "+myText+" into binary 2 dimensional array");
		Area1.setText("Text:"+myText+" is converted into:\n" +Int2DArray.GetArrayAsText(Input_Block_X, 8, 8) );
		StandByButton();
		
		//Write Initial Permutation in Area2
		Info.setText("Rearrange all bits of input X according to the initial permutation(IP)");
		Area2.setText("Initial Permutation (IP) is:\n" +Int2DArray.GetArrayAsText(Initial_Permutation_matrix, 8, 8) );
		StandByButton();
	
		//Creating permutation object
		Permutation per = new Permutation();
		Permuted_input =  per.Permute(Input_Block_X , Initial_Permutation_matrix, 8,8);
		Int2DArray.printI2DArray(Permuted_input, 8, 8);
		
		//Write Permuted_input in Area3
		Info.setText("Save result in Permuted_input");
		Area3.setText("Permuted input:\n" +Int2DArray.GetArrayAsText(Permuted_input, 8, 8) );
		StandByButton();
		
		
		
		//Dealing with Key
		String initial_key = "F49F989455498554";
		key = new Key(initial_key);

		//Clean board and Write Key_K_64 in area 1
		Area2.setText("");
		Area3.setText("");
		Info.setText("Convert "+initial_key+" into binary 2 dimensional array");
		Area1.setText("Key: "+initial_key+" is converted into:\n" +Int2DArray.GetArrayAsText(key.Key_K_64, 8, 8) );
		StandByButton();

		
		//Remove parity bits
		key.Remove_Parity_Bits_From_64_to_56();
		//System.out.println("KEY without parity:");
		//Int2DArray.printI2DArray(key.Key_K_56, 8, 7);

		//Write Key_K_56 in area 1
		Info.setText("Remove parity bits from key");
		Area1.setText("Key after removing parity bits:\n" +Int2DArray.GetArrayAsText(key.Key_K_56, 8, 7) );
		StandByButton();

		//Write Permuted_choise1 in area 2
		Info.setText("Performing permutation between Key(56 bits) and PC1");
		Area2.setText("Permuted Choise 1:\n" +Int2DArray.GetArrayAsText(Permuted_choise1, 8, 7) );
		StandByButton();

		//Permutation Key_K_56 with PC1
		key.Key_K_56_N = per.Permute(key.Key_K_56, Permuted_choise1,8,7 );
		//System.out.println("KEY after permutation with PC1:");
		//Int2DArray.printI2DArray(key.Key_K_56_N, 8, 7);
		
		//Write Key_K_56_N in area 3
		Info.setText("Performing permutation between Key(56 bits) and PC1.");
		Area3.setText("Result of permutation (56 bit key K') is:\n" +Int2DArray.GetArrayAsText(key.Key_K_56_N, 8, 7) );
		StandByButton();
		
		
		
		//Split to L,R
		key.Split_Key_K_56_N_to_L0_and_R0();
		//System.out.println("L0:");          //L0
		//Int2DArray.printI2DArray(key.L0, 4, 7);
		//System.out.println("R0:");          //RO
		//Int2DArray.printI2DArray(key.R0, 4, 7);
		
		//Write L0 R0 in Area1 and Area2
		Info.setText("Derive 16 48-bit keys K[1]-K[16] from K'\nSplit 56 bit key K' in L0 and R0");
		Area1.setText("L0 is:\n" +Int2DArray.GetArrayAsText(key.L0, 4, 7) );
		Area2.setText("R0 is:\n" +Int2DArray.GetArrayAsText(key.R0, 4, 7) );
		StandByButton();
		
		//Loop starts here (16 SUBKEYS) --------------------
		for(int i =0;i<16;i++)
		{
			System.out.println("Calculating Subkey: ------------------------"+i);
			//Shift Left
			if(i==0)  //First round
			{
				key.L.add(  new Int2DArray(key.ShiftLeft(key.L0, Left_Shifts[i]), 4, 7)); 
				key.R.add(  new Int2DArray(key.ShiftLeft(key.R0, Left_Shifts[i]), 4, 7));
				
				//Write L0 R0 SHIFTED in Area1 and Area2
				Info.setText("According to the 'Left Shifts' table L0 and R0 are shifted: "+Left_Shifts[i]+" time(s)");
				Area1.setText("L0 Shifted is:\n" +Int2DArray.GetArrayAsText(key.L.get(key.L.size()-1).array, 4, 7) );
				Area2.setText("R0 Shifted is:\n" +Int2DArray.GetArrayAsText(key.R.get(key.R.size()-1).array, 4, 7) );
				//StandByButton();
			}
			else
			{
				key.L.add( new Int2DArray(key.ShiftLeft(key.L.get(i-1).array, Left_Shifts[i]  ), 4, 7)); 
				key.R.add( new Int2DArray(key.ShiftLeft(key.R.get(i-1).array, Left_Shifts[i]  ), 4, 7));
				
				//Write L[i] R[i] SHIFTED in Area1 and Area2
				Info.setText("According to the 'Left Shifts' table L["+(i+1)+"] and R["+(i+1)+"] are shifted: "+Left_Shifts[i]+" time(s)");
				Area1.setText("L["+(i+1)+"] Shifted is:\n" +Int2DArray.GetArrayAsText(key.L.get(key.L.size()-1).array, 4, 7) );
				Area2.setText("R["+(i+1)+"] Shifted is:\n" +Int2DArray.GetArrayAsText(key.R.get(key.R.size()-1).array, 4, 7) );
				//StandByButton();
			}
			//System.out.println("L[i] Shifted:");    
			//Int2DArray.printI2DArray(key.L.get(i).array, 4, 7);
			//System.out.println("R[i] Shifted:");    
			//Int2DArray.printI2DArray(key.R.get(i).array, 4, 7);

			//Merging L[i] R[i]
			key.Merge_L_and_R_to_K_Temp(key.L.get(i).array, key.R.get(i).array);
			
			//Write L[i] R[i] Merged in Area1
			Info.setText("Merge the two tables");
			Area2.setText("");
			Area3.setText("");
			Area1.setText("Merged:\n" +Int2DArray.GetArrayAsText(key.K_Temp, 8, 7) );
			//StandByButton();
			
			//Write Permuted_choise2 in Area2
			Info.setText("Perform permutation between the merged array and Pemruted Choise 2");
			Area2.setText("Permuted Choise 2:\n" +Int2DArray.GetArrayAsText(Permuted_choise2, 8, 6) );
			//StandByButton();

			//Permutation : K[i] = K_Temp permutaion PC2
			key.K_Subkeys.add( new Int2DArray( per.Permute8x6(key.K_Temp, Permuted_choise2 ) , 8, 6) );
			System.out.println("K["+i+"]:");
			Int2DArray.printI2DArray(key.K_Subkeys.get(i).array, 8, 6);

			//Write K[i] in Area3
			Info.setText("Save result of permutation in K["+(i+1)+"]");
			Area3.setText("K["+(i+1)+"] is:\n" +Int2DArray.GetArrayAsText(key.K_Subkeys.get(key.K_Subkeys.size()-1).array, 8, 6) );
		//	StandByButton();
			
		}
		//Loop (16 SUBKEYS) ends  here ----------------------
		System.out.println("Loop (16 SUBKEYS) ends  here ----------------------"); 
		
		//Info about Pemuted_Input
		Info.setText("Now we have the 16 subkeys, we are going to split Pemuted Input in two halves \ncalled L and R, each 32 bit wide");
		Area1.setText("Pemuted Input is:\n" +Int2DArray.GetArrayAsText(Permuted_input, 8, 8) );
		Area2.setText("");
		Area3.setText("");
		StandByButton();
		
		//Split Pemuted_Input_in_2 (from 64 to 2x32 bits)
		for(int i = 0; i<8; i++)
			for(int j = 0; j<4; j++)
			{
				L[i][j] =Permuted_input[i][j];
				R[i][j] =Permuted_input[i][j+4];
			}
		System.out.println("Left Permuted:");
		Int2DArray.printI2DArray(L, 8, 4);
		System.out.println("Right Permuted:");
		Int2DArray.printI2DArray(R, 8, 4);
		System.out.println(" Spliting ends  here ----------------------");
		
		//Write L[0] and R[0] in Area1 and Area2
		Info.setText("L[0] and R[0] have the values. Now we are starting 16 DES Rounds");
		Area1.setText("L[0] is:\n" +Int2DArray.GetArrayAsText(L, 8, 4) );
		Area2.setText("R[0] is:\n" +Int2DArray.GetArrayAsText(R, 8, 4) );
		StandByButton();
		
		//Loop starts here (16 DES ROUNDS) ----------------------
		for(int i=0;i<16;i++)
		{
			
			if(i==0)
			{
				//L[i] = R[i-1]
				Llist.add(new Int2DArray(R, 8, 4));//L1= R0
				
				//Write L[i] = R[i-1] -> because we are starting from zero: L[i+1] = R[i]
				Info.setText("L["+(i+1)+"] = R["+(i)+"] \nR["+(i+1)+"] = L["+i+"]   XOR   f( R["+i+"] , K["+(i+1)+"] )");
				Area1.setText("L["+(i+1)+"] is:\n" +Int2DArray.GetArrayAsText(R, 8, 4) );
				StandByButton();
				
				//Create function object
				Function f = new Function( R, key.K_Subkeys.get(i).array  );//create Function object for this round
				
				//Write about function
				Info.setText("Lets take a closer look at the DES core function f( Ri-1 , Ki)");
				Area1.setText("");
			    Area2.setText("");
			    Area3.setText("");
				StandByButton();
				
				//Set prices to E_R_i_minus_1 (convert R[i] from 32 to 48 bits)
				f.Expand_R_from_32_to_48_bits();
				System.out.println("Expansion R -1:");
				//Int2DArray.printI2DArray(E_R_i_minus_1, 8, 6); 
				
				//Write about E_R_i_minus_1 (convert R[i] from 32 to 48 bits)
				Info.setText("Each 32-bit wide Ri-1 must be expanded before it can be XORed  with its corresponding 48-bit Subkey");
				Area1.setText("R["+i+"] is:\n" +Int2DArray.GetArrayAsText(f.R_i_minus_1, 8, 4) );
				Area2.setText(" Using an Array called Expansion:\n" +Int2DArray.GetArrayAsText(f.Expansion, 8, 6) );
				Area3.setText("Expanded R["+i+"] is:\n" +Int2DArray.GetArrayAsText(f.E_R_i_minus_1, 8, 6) );
				StandByButton();
				
				//Perform XOR between Expanded R(i-1) and Ki 
				f.XOR_E_R_minus_1_With_Ki();
				
				//Write about XOR between E_R_i_minus_1 and Ki
				Info.setText("Next step is to XOR Expanded R["+i+"] and K["+(i+1)+"]  ");
				Area1.setText("XOR Expanded R["+i+"] is:\n" +Int2DArray.GetArrayAsText(f.E_R_i_minus_1, 8, 6) );
				Area2.setText("XOR K["+(i+1)+"] is:\n" +Int2DArray.GetArrayAsText(key.K_Subkeys.get(i).array, 8, 6) );
				Area3.setText("The result B is divided into 8 blocks:\n" +Int2DArray.Get8OneDimensionArraysAsText(f.B1, f.B2, f.B3, f.B4, f.B5, f.B6, f.B7, f.B8, 6) );
				StandByButton();
				
				
				//S-Boxes
				f.Substitution_boxes();
				
				//Write about S-Boxes
				Info.setText("Now 8 numbers are extracted from the S-Boxes, one from each box\n"
						    + "Row:1rst and 8th bit  Column:middle 4 bits. Convert number from decimal to  binary and save it to B'" );
				Area1.setText("");
				Area2.setText("");
				Area3.setText("B' is divided into 8 blocks:\n" +Int2DArray.Get8OneDimensionArraysAsText(f.B1_New, f.B2_New, f.B3_New, f.B4_New, f.B5_New, f.B6_New, f.B7_New, f.B8_New, 4) );
				StandByButton();
				
				//Write about passing B' 8 blocks into R
				Info.setText("The result R is the concatination of B'[1] to B'[8]");
				Area1.setText("R is:\n" +Int2DArray.GetArrayAsText(f.R, 8, 4) );
				Area3.setText("");
				StandByButton();
				
				//Write about permutation array P
				Info.setText("Finally, R is passed though the permutation P");
				Area2.setText("P is:\n" +Int2DArray.GetArrayAsText(f.P, 8, 4) );
				StandByButton();
				
				//Permutation R with P
				int[][] middleArray = new int[8][4];//result of function
				middleArray= f.Permute_R_with_P_8x4();
				
				//Write about result of permutation R with P 
				Area3.setText("P permutation R is:\n" +Int2DArray.GetArrayAsText(middleArray, 8, 4) );
				StandByButton();
				
				int[][] FResultArray = f.XOR_Between_8x4_int_arrays(L, middleArray);
				Rlist.add(new Int2DArray(FResultArray,8,4));
				
				
				//Write about final XOR, We are out of function f
				Info.setText("Exit from function and XOR the result with L["+i+"] and save it to R["+(i+1)+"] ");
				Area1.setText("XOR Result from function:\n" +Int2DArray.GetArrayAsText(middleArray, 8, 4));
				Area2.setText("XOR L["+i+"] is:\n" +Int2DArray.GetArrayAsText(L, 8, 4));
				Area3.setText("R["+(i+1)+"] is:\n" +Int2DArray.GetArrayAsText(Rlist.get(Rlist.size()-1).array, 8, 4) );
				StandByButton();
			}
			else
			{
				//L[i] = R[i-1]
				Llist.add(new Int2DArray(Rlist.get(i-1).array, 8, 4));//L1= R0
				
				//Write L[i] = R[i-1] -> because we are starting from zero: L[i+1] = R[i]
				Info.setText("L["+(i+1)+"] = R["+(i)+"] \nR["+(i+1)+"] = L["+i+"]   XOR   f( R["+i+"] , K["+(i+1)+"] )");
				Area1.setText("L["+(i+1)+"] is:\n" +Int2DArray.GetArrayAsText(R, 8, 4) );
				StandByButton();
				
				//Create function object
				Function f = new Function( Rlist.get(i-1).array, key.K_Subkeys.get(i).array  );//create Function object for this round
				
				//Write about function
				Info.setText("Lets take a closer look at the DES core function f( Ri-1 , Ki)");
				Area1.setText("");
			    Area2.setText("");
			    Area3.setText("");
				StandByButton();
				
				//Set prices to E_R_i_minus_1 (convert R[i] from 32 to 48 bits)
				f.Expand_R_from_32_to_48_bits();
				System.out.println("Expansion R -1:");
				//Int2DArray.printI2DArray(E_R_i_minus_1, 8, 6); 
				
				//Write about E_R_i_minus_1 (convert R[i] from 32 to 48 bits)
				Info.setText("Each 32-bit wide Ri-1 must be expanded before it can be XORed  with its corresponding 48-bit Subkey");
				Area1.setText("R["+i+"] is:\n" +Int2DArray.GetArrayAsText(f.R_i_minus_1, 8, 4) );
				Area2.setText(" Using an Array called Expansion:\n" +Int2DArray.GetArrayAsText(f.Expansion, 8, 6) );
				Area3.setText("Expanded R["+i+"] is:\n" +Int2DArray.GetArrayAsText(f.E_R_i_minus_1, 8, 6) );
				StandByButton();
				
				//Perform XOR between Expanded R(i-1) and Ki 
				f.XOR_E_R_minus_1_With_Ki();
				
				//Write about XOR between E_R_i_minus_1 and Ki
				Info.setText("Next step is to XOR Expanded R["+i+"] and K["+(i+1)+"]  ");
				Area1.setText("Expanded R["+i+"] is:\n" +Int2DArray.GetArrayAsText(f.E_R_i_minus_1, 8, 6) );
				Area2.setText(" K["+(i+1)+"] is:\n" +Int2DArray.GetArrayAsText(key.K_Subkeys.get(i).array, 8, 6) );
				Area3.setText("The result B is divided into 8 blocks:\n" +Int2DArray.Get8OneDimensionArraysAsText(f.B1, f.B2, f.B3, f.B4, f.B5, f.B6, f.B7, f.B8, 6) );
				StandByButton();
				
				
				//S-Boxes
				f.Substitution_boxes();
				
				//Write about S-Boxes
				Info.setText("Now 8 numbers are extracted from the S-Boxes, one from each box\n"
						    + "Row:1rst and 8th bit  Column:middle 4 bits. Convert number from decimal to  binary and save it to B'" );
				Area1.setText("");
				Area2.setText("");
				Area3.setText("B' is divided into 8 blocks:\n" +Int2DArray.Get8OneDimensionArraysAsText(f.B1_New, f.B2_New, f.B3_New, f.B4_New, f.B5_New, f.B6_New, f.B7_New, f.B8_New, 4) );
				StandByButton();
				
				//Write about passing B' 8 blocks into R
				Info.setText("The result R is the concatination of B'[1] to B'[8]");
				Area1.setText("R is:\n" +Int2DArray.GetArrayAsText(f.R, 8, 4) );
				Area3.setText("");
				StandByButton();
				
				//Write about permutation array P
				Info.setText("Finally, R is passed though the permutation P");
				Area2.setText("P is:\n" +Int2DArray.GetArrayAsText(f.P, 8, 4) );
				StandByButton();
				
				//Permutation R with P
				int[][] middleArray = new int[8][4];//result of function
				middleArray= f.Permute_R_with_P_8x4();
				
				//Write about result of permutation R with P 
				Area3.setText("P permutation R is:\n" +Int2DArray.GetArrayAsText(middleArray, 8, 4) );
				StandByButton();
				
				int[][] FResultArray = f.XOR_Between_8x4_int_arrays(Llist.get(i-1).array, f.GenerateResult());
				Rlist.add(new Int2DArray(FResultArray,8,4));
				
				//Write about final XOR, We are out of function f
				Info.setText("Exit from function and XOR the result with L["+i+"] and save it to R["+(i+1)+"] ");
				Area1.setText("XOR Result from function:\n" +Int2DArray.GetArrayAsText(middleArray, 8, 4));
				Area2.setText("XOR L["+i+"] is:" +Int2DArray.GetArrayAsText(L, 8, 4));
				Area3.setText("R["+(i+1)+"] is:\n" +Int2DArray.GetArrayAsText(Rlist.get(Rlist.size()-1).array, 8, 4) );
				StandByButton();
			}
		}
		//Loop (16 DES ROUNDS) ends here ----------------------
		//System.out.println(" Loop (16 DES ROUNDS) ends here ----------------------");
		
		//Write about 16 DES ROUNDS end
		Info.setText("Exited from 16 DES Rounds");
		Area1.setText("");
		Area2.setText("");
		Area3.setText("");
		StandByButton();
		
		//Merge last R[i] and last L[i] in Pre_Output ( block R precedes block L this time)
		Pre_Output= Int2DArray.Merge_8x4_arrays(Rlist.get(Rlist.size()-1).array, Llist.get(Llist.size()-1 ).array );
		Int2DArray.printI2DArray(Pre_Output, 8, 8);

		//Write about merging last R[i] and last L[i] in Pre_Output ( block R precedes block L this time)
		Info.setText("Merge  R[16] and L[16] in Pre_Output ( block R precedes block L this time)");
		Area1.setText("R[16] is:\n" +Int2DArray.GetArrayAsText(Rlist.get(Rlist.size()-1).array, 8, 4));
		Area2.setText("L[16] is:\n" +Int2DArray.GetArrayAsText(Llist.get(Llist.size()-1).array, 8, 4));
		Area3.setText("Pre Output is:\n" +Int2DArray.GetArrayAsText(Pre_Output, 8, 8) );
		StandByButton();

		
		//Write about permutation Pre_Output with Initial_Permutation_Reversed
		Info.setText("Pre Output is passed through the inverse initial permutation IP the final step in DES");
		Area1.setText("Pre Output is:\n" +Int2DArray.GetArrayAsText(Pre_Output, 8, 8));
		Area2.setText("Initial Permutation Reversed is:\n" +Int2DArray.GetArrayAsText(Initial_Permutation_Reversed, 8, 4));
		Area3.setText("");
		StandByButton();

		Output_block_Y = per.Permute(Pre_Output, Initial_Permutation_Reversed,8 , 8);
		//System.out.println("Output block:");
		Int2DArray.printI2DArray(Output_block_Y, 8, 8);

		//Write permutation result on Area3
		Area3.setText("Output block Y is:\n" +Int2DArray.GetArrayAsText(Output_block_Y, 8, 8));
		StandByButton();

		String cryptographed= Int2DArray.Convert_Back_To_ASCII(Output_block_Y, 8, 8);
		System.out.println("Cryptographed text:   " +cryptographed);
		
		//Write final result on Area1
		Info.setText("Convert Output block Y into text.");
		Area1.setText(cryptographed);
		StandByButton();

		//Write final result on Area1
		Area2.setText("Finished");
		Area3.setText("Finished");
		
	}
	
	void Sleep_Sec(int sec)
	{
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	void StandByButton()
	{
		while(!pressed && global_flag)
		{
			Sleep_Sec(1);
		}
		pressed=false;
	}
}
