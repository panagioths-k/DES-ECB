//In DES(ECB)Permutation is always done between 2 arrays that are 8x8 or 8x7
//And the result is an 8x8 or 8x7 array

public  class Permutation {




	public  Permutation ()
	{

	}

	public  int[][] Permute(int[][] _prices, int[][] _indices, int rows, int columns )//Permuting rows x columns arrays
	{
		//System.out.println("i= "+rows);
		//System.out.println("j= "+columns);

		int[][] result = new int[rows][columns];
		for(int i =0;i< rows ;i++)
			for(int j =0;j< columns ;j++)
			{                             //row: _indices/rows    collumn: _indices mod columns
				try
				{
					result[i][j] = _prices[(_indices[i][j]-1)/rows ][ (_indices[i][j]-1)%rows ] ;
				}
				catch(Exception e)
				{
					System.out.println("ERROR: i: "+i+",j: "+j+" index: "+(_indices[i][j])+" price: "+(_prices[i][j])+" row: "+
							(_indices[i][j]/rows) +" collumn: "+( _indices[i][j]%columns-1) );
				}; 
			}
		return result;
	}

	public  int[][] Permute8x6(int[][] _prices, int[][] _indices )//Permuting 8x7 arrays
	{
		int rows = 8; //we only scan indices array
		int columns = 6;
		//System.out.println("i= "+rows);
		//System.out.println("j= "+columns);

		int[][] result = new int[rows][columns];
		for(int i =0;i< rows ;i++)
			for(int j =0;j< columns ;j++)
			{                             //row: _indices/8    collumn: _indices mod 8
				//System.out.println("i: "+i+",j: "+j+" index: "+(_indices[i][j]-1)+" price: "+( _prices[(_indices[i][j]-1)/8 ][ (_indices[i][j]-1)%7 ] )+" row: "+((_indices[i][j]-1)/8) +" collumn: "+( (_indices[i][j]-1)%7) );
				try
				{
					result[i][j] = _prices[(_indices[i][j]-1)/7 ][ (_indices[i][j]-1)%7] ;

				}
				catch(Exception e)
				{
					System.out.println("EROOR: i: "+i+",j: "+j+" index: "+(_indices[i][j]-1)+" price: "+( _prices[(_indices[i][j]-1)/8 ][ (_indices[i][j]-1)%8 ] )+" row: "+
							(_indices[i][j]/8) +" collumn: "+( _indices[i][j]%8-1) );
				}; 
			}
		//Int2DArray.printI2DArray(result, 8, 6);
		return result;
	}


/**	
	public  void Permute8x4(int[][] _prices, int[][] _indices )////Permuting 8x4 arrays
	{
		this.setVisible(true);
		int rows = 8; //we only scan indices array
		int columns = 4;
		System.out.println("i= "+rows);
		System.out.println("j= "+columns);


		this.setVisible(true); 

	}
 **/
}
