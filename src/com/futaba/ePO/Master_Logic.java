package com.futaba.ePO;

import java.io.File;
import java.util.ArrayList;


public class Master_Logic {
	
	public static int numberoffiles;
	
	
	public Master_Logic() throws Exception{
		
		ArrayList<ePOData> ePODataList = new ArrayList<ePOData>();		
		
		
		for (File file : new File(GUI.inputdirectoryname).listFiles()) //iterate through files in selected directory
		{
			ePODataList.add(new ePOData(file)); //Add ePO Data to array list for iteration
			numberoffiles++;
		}
		
		
		for (ePOData data : ePODataList)
		{						
			new GenerateFoldersAndMove(data);		
		}
		
		new GenerateExcelDocument(ePODataList);
		
		
		System.out.println("DONE!");
		
	}

}
