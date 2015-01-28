package com.kids.fun2learn.utils;

public class RandomNumberGenerator {
	public static int genarateRandomNumber(int range) {// 3
		int randomNumber = 0;
		for (int i = 0; i < range; i++)
			randomNumber = (int) (Math.random() * range);
		return randomNumber;
	}

	public static int genarateRandomNumber1(int range) {// 3
		int randomNumber = 1;
		for (int i = 1; i <= range; i++)
			randomNumber = (int) (Math.random() * range);
		return randomNumber;
	}
}
