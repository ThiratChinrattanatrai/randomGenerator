package th.com;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Application {
	
	public static void main(String[] args) throws Exception {
		List<BigDecimal> usedList = new ArrayList<BigDecimal>();
		
		Comparator<BigDecimal> comparator = new Comparator<BigDecimal>() {
			@Override
			public int compare(BigDecimal o1, BigDecimal o2) {
				return o1.compareTo(o2);
			}
		};
		
//		double[] a = {7,4,5,6,6,7,9,1,8,3};
//		double[] a = {9,9,9,9,9,9,9,9,9,9};
//		double[] a1 = {0,0,0};
//		double[] a2 = {0,0,1};
//		double[] a3 = {0,0,2};
//		double[] a4 = {0,1,0};
//		double[] a5 = {0,1,1};
//		double[] a6 = {0,1,2};
//		double[] a7 = {0,2,0};
//		double[] a8 = {0,2,1};
//		double[] a9 = {0,2,2};
//		double[] a10 = {1,0,0};
//		double[] a11 = {1,0,1};
//		double[] a12 = {1,0,2};
//		double[] a13 = {1,1,0};
//		double[] a14 = {1,1,1};
//		double[] a15 = {1,1,2};
//		double[] a16 = {1,2,0};
//		double[] a17 = {1,2,1};
//		double[] a18 = {1,2,2};
//		double[] a19 = {2,0,0};
//		double[] a20 = {2,0,1};
//		double[] a21 = {2,0,2};
//		double[] a22 = {2,1,0};
//		double[] a23 = {2,1,1};
//		double[] a24 = {2,1,2};
//		double[] a25 = {2,2,0};
//		double[] a26 = {2,2,1};
//		double[] a27 = {2,2,2};
		
//		test();
				
		int digit = 1;
		int fractionDigit = 2;
		
//		System.out.println(validateInRange(new BigDecimal(0.0),new BigDecimal(0.1),new BigDecimal(9.99)));
		
//		double min = 1.0;
//		double max = 10.0;
		
		BigDecimal min = findMin(fractionDigit);
		BigDecimal max = findMax(digit, fractionDigit);
		int numRandom = 990;
		
		BigDecimal addNum = findAddition(fractionDigit);
		int maxSize = getMaxSize(min, max, fractionDigit);
		
		for(int i = 0;i < numRandom;i++) {
			BigDecimal randomNumber = BigDecimal.valueOf(Math.random() * Math.pow(10, digit));	
//			Double randomNumber  = a[i];
			randomNumber = randomNumber.setScale(fractionDigit,RoundingMode.HALF_UP);
			System.out.println("round random : " + randomNumber);
			//validateSize();
			if(usedList.size() >= maxSize) {
				usedList.sort(comparator);
				System.out.println("----------------");
				
				for(BigDecimal j : usedList) {
					System.out.println(j);
				}
				
				System.out.println("size : " + usedList.size());
				
				throw new Exception("Exceed size : " + maxSize);
			}
			
			if(! usedList.contains(randomNumber)) {
				System.out.println("put : " + randomNumber);
				usedList.add(randomNumber);	
			}else {
				boolean positiveFlag = true;
				BigDecimal tempAddNum = addNum;
				
				randomNumber = randomNumber.add(tempAddNum);
				
				while((!validateInRange(randomNumber, min, max)) || usedList.contains(randomNumber)) {
//					System.out.println("random : " + randomNumber);

					// problem when negative value
					if(positiveFlag == true) {
						randomNumber = randomNumber.add(tempAddNum);
					}else{
						randomNumber = randomNumber.subtract(tempAddNum);
					}
					
					tempAddNum = tempAddNum.add(addNum);
					positiveFlag = !positiveFlag;
				}
				System.out.println("add closet Number : " + randomNumber);
				usedList.add(randomNumber);
			}
			
		}
		usedList.sort(comparator);
		System.out.println("----------------");
		
		for(BigDecimal i : usedList) {
			System.out.println(i);
		}
		
		System.out.println("size : " + usedList.size());

	}
	
	private static int getMaxSize(BigDecimal min,BigDecimal max,int digit) {
		BigDecimal range = max.subtract(min);
		BigDecimal digitSize = BigDecimal.valueOf(Math.pow(10, digit));
		BigDecimal size = (range.multiply(digitSize)).add(new BigDecimal(1));
		System.out.println("max size : " + size.intValue());
		return size.intValue();
	}
	
	private static BigDecimal findMax(int digit,int fraction) {
		String digitString = "";
		for(int i=0;i < digit;i++) {
			digitString += 9;
		}
		String fractionString = "";
		for(int i=0;i < fraction;i++) {
			fractionString += 9;
		}
		String maxStr = (digitString + "." + fractionString);
		System.out.println("max value : " + Double.parseDouble(maxStr));
		return BigDecimal.valueOf(Double.parseDouble(maxStr));
	}
	
	private static BigDecimal findMin(int digit) {
		BigDecimal minDigit = BigDecimal.valueOf(0.0);
		if(digit > 1) {
			minDigit = BigDecimal.valueOf(1/Math.pow(10, digit-1));
		}
		System.out.println("min value : " + minDigit);
		return minDigit;
	}
	
	private static BigDecimal findAddition(int fractionDigit) {
		BigDecimal n = new BigDecimal(1);
		if(fractionDigit != 0) {
			n = BigDecimal.valueOf(1/(Math.pow(10, fractionDigit)));
		}	
		System.out.println("addition : " + n);
		return n;
	}
	
	private static boolean validateInRange(BigDecimal randomNumber,BigDecimal min,BigDecimal max) {
		boolean valid = false;
		if(randomNumber.compareTo(max) < 0 && randomNumber.compareTo(min) >= 0) {
			valid = true;
		}
		return valid;
	}
	
	private static void test() {
		BigDecimal randomNumber = new BigDecimal(1);
		BigDecimal max = new BigDecimal(2);
		System.out.println(randomNumber.compareTo(max));
		
	}
	

}
