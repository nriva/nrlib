package it.nrsoft.nrlib.math;

public class Matrix
{
	private double[][] data=null;
	
	private int row;

	private int col;
	
	
	
	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the col
	 */
	public int getCol() {
		return col;
	}
	
/**
 * 
 * @param row
 * @param col
 */
	public Matrix(int row,int col)
	{
		this.col = col;
		this.row = row;

		data = new double[row][col];
	}

	/**
	 * 
	 * @param n
	 * @return
	 * @throws Exception
	 */
	public static Matrix diagonal(int n) throws Exception
	{
		if(n<=0)
			throw new Exception("Ordine non valido: " + n);

		Matrix m = new Matrix(n,n);
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				if(i==j)
					m.data[i][j] = 1;
				else
					m.data[i][j] = 0;
		return m;
	}

	/**
	 * 
	 * @param data
	 */
	public Matrix(double[][] data)
	{
		if(data==null)
			throw new IllegalArgumentException("Matrice in input nulla");
		
		this.row = data.length;
		this.col = data[0].length;
		set(data);
	}
	
	public void set(double[][] data)
	{
		if(data==null)
			throw new IllegalArgumentException("Matrice in input nulla");
		if(data.length == 0)
			throw new IllegalArgumentException("Matrice in input non valorizzata");
		if(data[0].length == 0)
			throw new IllegalArgumentException("Matrice in input non valorizzata");
		if(row != data.length || col != data[0].length)
			throw new IllegalArgumentException("Matrice in input di dimensioni errate");
		
		for(int i=0;i<row;i++)
			for(int j=0;j<col;j++)
				this.data[i][j] = data[i][j];
	}
	
	public void set(int[][] data)
	{
		if(data==null)
			throw new IllegalArgumentException("Matrice in input nulla");
		if(data.length == 0)
			throw new IllegalArgumentException("Matrice in input non valorizzata");
		if(data[0].length == 0)
			throw new IllegalArgumentException("Matrice in input non valorizzata");
		if(row != data.length || col != data[0].length)
			throw new IllegalArgumentException("Matrice in input di dimensioni errate");
		
		for(int i=0;i<row;i++)
			for(int j=0;j<col;j++)
				this.data[i][j] = data[i][j];
	}	
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static Matrix sum(Matrix a,Matrix b,double sign) throws Exception
	{
		if( a.row != b.row || a.col	!= b.col)
			throw new Exception("Dimensioni non compatibili");
		Matrix c = new Matrix(a.row,a.col);
		for(int i=0;i<a.row;i++)
			for(int j=0;j<a.col;j++)
				c.data[i][j] = a.data[i][j] + sign*b.data[i][j];
		return c;
	
	}


	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public static Matrix mult (Matrix a,Matrix b) throws Exception
	{
		if( a.col != b.row)
			throw new Exception("Dimensioni non compatibili");
		Matrix c = new Matrix(a.row,b.col);
		for(int i=0;i<a.row;i++)
			for(int j=0;j<b.col;j++)
				for(int k=0;k<a.col;k++)
					c.data[i][j] += a.data[i][k] * b.data[k][j];
		return c;

	}


	

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null)
			return false;
		
		if(obj == this)
			return true;
		
		if(!(obj instanceof Matrix))
			return false;
		
		Matrix b = (Matrix)obj;
		
		boolean equal = col == b.col && row == b.row;
		int i=0,j=0;
		while(equal && i < row)
		{
			j=0;
			while(equal && j < col)
			{
				equal = equal && data[i][j] == b.data[i][j];
				j++;
			}
			i++;
		}
		return equal;
	}


	/**
	 * 
	 * @param a
	 * @param data
	 * @return
	 */
	public static boolean equals (Matrix a,double[][] data)
	{
		Matrix b = new Matrix(data);
		return a.equals(b);
	}



	/**
	 * 
	 * @param r
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public double getData(int r,int c) throws Exception
	{

			if(r>row-1 || c > col-1)
				throw new Exception("Indice non valido.");
			return data[r][c]; 
	}
	
	/**
	 * 
	 * @param r
	 * @param c
	 * @param value
	 * @throws Exception
	 */
//	public void setData(int r,int c,double value) throws Exception
//
//	{ 
//			if(r>row-1 || c > col-1)
//				throw new Exception("Indice non valido.");
//			data[r][c] =  value; 
//	}

	@Override
	public String toString()
	{
		String s="",l;
		for(int i=0;i<row;i++)
		{
			l="";
			for(int j=0;j<col;j++)
			{
				if(l!="")
					l += "\t";
				l += String.valueOf(data[i][j]);
			}
			s+=l + "\n";
		}
		return s;
	}

	/**
	 * 
	 * @param r riga da escludere
	 * @param c colonna da escludere
	 * @return
	 */
	public Matrix subMatr(int r,int c)
	{
		Matrix m = new Matrix(row-1,col-1);
		for(int i=0;i<row;i++)
			for(int j=0;j<col;j++)
				if(i!=r && j!=c)
					m.data[i<r?i:i-1][j<c?j:j-1] = data[i][j];
		return m;
	}


	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public double determinant() throws Exception
	{
		if(row!=col)
			throw new Exception("La matrice non � quadrata");
		double d=0;
		if ( row == 1 && col == 1)
			d = data[0][0];
		else if( row == 2 )
			d = data[0][0] * data[1][1] - data[0][1] * data[1][0];
		else
		{
			for(int i=0;i<col;i++)
			{
				Matrix m = subMatr(0,i);

				d += data[0][i]*  (i%2==0?1:-1) * m.determinant();
			}
		}
		return d;
	}


	/**
	 * 
	 * @return
	 */
	public Matrix transposed()
	{
		Matrix m = new Matrix(col,row);
		for(int i=0;i<row;i++)
			for(int j=0;j<col;j++)
				m.data[j][i] = data[i][j];
		return m;
	}

	/**
	 * Matrice inversa 
	 * @return
	 * @throws Exception
	 */
	public Matrix inv() throws Exception
	{
		Matrix m = new Matrix(row,col);
		double d = determinant();
		if(d==0.0)
			throw new Exception("La matrice � singolare");
		
		if(row==1)
			m = new Matrix(new double[][] {{ 1.0/data[0][0] }});
		else
			for(int i=0;i<row;i++)
				for(int j=0;j<col;j++)
					m.data[i][j] = ((i+j)%2==0?1:-1) * subMatr(i,j).determinant() / d;
		return m.transposed();
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @throws IllegalArgumentException se le dimensioni relative
	 * @return l'indice del match
	 */
	public static int match(Matrix a,Matrix b)
	{
		if(a.row!=b.row)
			throw new IllegalArgumentException("Dimensione verticale diversa");
		
		if(a.col<b.col)
			throw new IllegalArgumentException("Dimensioni orizzontali non congruenti (b maggiore di a)");
		int k;
		boolean found = false;
		for(k=0;k<=(a.col-b.col);k++)
		{
			found = true;
			loop:
			for(int i=0;i<b.row;i++)
				for(int j=0;j<b.col;j++)
				{
					if(a.data[i][k+j]!=b.data[i][j])
					{
						found = false;
						break loop;
					}
				}
			if(found)
				break;
		}
		return found?k:-1;
	}	


}