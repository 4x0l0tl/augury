package com.augury.core.structure;

import com.augury.model.Directory;
import com.augury.model.DirectoryNode;
import com.augury.model.File;

public class GenericTestStructure 
{
    private static final String IOTA 	= "iota.java";
    private static final String MU 		= "mu.java";
    private static final String LAMBDA 	= "lambda.java";
    private static final String ALPHA 	= "alpha.java";
    private static final String BETA 	= "beta.java";
    private static final String CHI 	= "chi.java";
    private static final String GAMMA	= "gamma.java";
    private static final String PI 		= "pi.java";
    private static final String RHO 	= "rho.java";
    private static final String TAU 	= "tau.java";
    private static final String OMEGA 	= "omega.java";
    private static final String PSI 	= "psi.java";
    
    
	Directory root;
	
	public GenericTestStructure()
	{
		root = new Directory("A", "/");
	}
	
	public void createDirectoryStructure()
	{
		root.add(new File(ALPHA,"/A"));
		
		Directory b = new Directory("B", "/A");
		root.add(b);
		File beta = new File(BETA, "/A/B");
		b.add(beta);
		
		Directory c = new Directory("C", "/A");
		root.add(c);
		File chi = new File(CHI, "/A/C");
		c.add(chi);
		
		Directory d = new Directory("D", "/A/B");
		b.add(d);
		File iota = new File(IOTA, "/A/B/D/");
		d.add(iota);
		
		Directory e = new Directory("E", "/A/C");
		c.add(e);
		File lambda = new File(LAMBDA, "/A/C/E");
		e.add(lambda);
	}
	
	public DirectoryNode getTree()
	{
		return root;
	}
}
