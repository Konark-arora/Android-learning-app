package com.kids.fun2learn.utils;


public class RandomNumberGenerator {

	public static int genarateRandomNumber(int range) {

		 int randomNumber = 0;
		 for (int i = 0; i < range; i++)
		 randomNumber = (int) (Math.random() * range);
		 return randomNumber;

		
		
	}

	// {
	// int randomNumber=0;
	// for(int i=0;i<3;i++)
	// randomNumber = (int)(Math.random()*3);
	// return randomNumber;//generates 0,1,2,

	// }

}
