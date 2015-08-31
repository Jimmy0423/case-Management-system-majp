package se.majp.caseManagement.main;

import java.util.Arrays;

import se.majp.caseManagement.util.IdGenerator;

public class Main
{
	public static void main(String[] args)
	{
		IdGenerator generator = IdGenerator.getBuilder().characters('0', 'z').length(8).build();
		String[] generatedIds = new String[10000000];
		
		for(int i = 0; i < generatedIds.length; i++)
		{
			generatedIds[i] = generator.getNextId();
		}
		
		System.out.println("Sorting");
		Arrays.sort(generatedIds);
		System.out.println("Done sorting");
		String lastId = "";
		int i = 0;
		
		for(String id : generatedIds) 
		{
			if(id.equals(lastId)) 
			{
				System.out.println("id: " + id + "\nlast id: " + lastId);
				i++;
			}
			
			lastId = id;
		}
		
		System.out.println(i);
	}
}
