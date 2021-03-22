package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class RadixSortEjerc
{
	public static void radixSort(int []arr)
	{
		String[] strArr = StringUtil.toStringArray(arr);
		StringUtil.lNormalize(strArr, '0');
		int len = strArr[0].length();

		for (int i = len - 1; i >= 0; i--) {
			HashMap<Character, ArrayList<String>> map = new HashMap<Character, ArrayList<String>>();

			for (int j = 0; j < strArr.length; j++) {
				Character c = strArr[j].toCharArray()[i];

				if(!map.containsKey(c)){
					map.put(c, new ArrayList<>());
				}

				map.get(c).add(strArr[j]);
			}

			ArrayList<String> arrayListAux = new ArrayList();
			for (char c = '0'; c <= '9' ; c++) {
				if(map.containsKey(c)) {
					arrayListAux.addAll(map.get(c));
				}
			}

			strArr = arrayListAux.toArray(new String[strArr.length]);
		}

		int[] arrSort = StringUtil.toIntArray(strArr);
		for (int i = 0; i < arrSort.length ; i++) {
			arr[i] = arrSort[i];
		}
	}

	public static void main(String[] args)
	{
		int arr[]={16223,898,13,906,235,23,9,1532,6388,2511,8};
		radixSort(arr);
		
		for(int i=0; i<arr.length;i++)
		{
			System.out.print(arr[i]+(i<arr.length-1?",":""));
		}
	}
}
