package th.com;

import java.math.BigDecimal;
import java.math.BigInteger;
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
				
//		test();
				
//		int digit = 1;
//		int fractionDigit = 2;
		
		BigDecimal min = BigDecimal.valueOf(0);
		BigDecimal max = BigDecimal.valueOf(100);
		
//		BigDecimal min = findMin(fractionDigit);
//		BigDecimal max = findMax(digit, fractionDigit);
		int numRandom = 991;
		
		int digit = findDigit(max);
		int fractionDigit = findFraction(min,max);
		
		BigDecimal addNum = findAddition(fractionDigit);
		int maxSize = getMaxSize(min, max, fractionDigit);
		
		long start = System.currentTimeMillis();
		for(int i = 0;i < maxSize;i++) {
			
			BigDecimal randomNumber = getRandomNumber(digit, fractionDigit);
			System.out.println("first roll : " + randomNumber);
			while(!validateInRange(randomNumber, min, max)) {
				System.out.println("re-roll");
				randomNumber = getRandomNumber(digit, fractionDigit);
			}
			
			//validateSize();
			if(usedList.size() >= maxSize) {
				usedList.sort(comparator);
				System.out.println("----------------");
				
				for(BigDecimal j : usedList) {
					System.out.println(j);
				}
				
				System.out.println("size : " + usedList.size());
				
				usedList.clear();
//				throw new Exception("Exceed size : " + maxSize);
			}
			
			boolean positiveFlag = true;
			BigDecimal tempAddNum = addNum;
								
			while(!validateInRange(randomNumber, min, max) || usedList.contains(randomNumber)) {
				System.out.println("random : " + randomNumber);

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
		
		
		usedList.sort(comparator);
		System.out.println("----------------");
		
		for(BigDecimal i : usedList) {
			System.out.println(i);
		}
		
		System.out.println("size : " + usedList.size());
		long end = System.currentTimeMillis();
		long total = end-start;
		System.out.println("total : " + total + "ms");
		System.out.println("total : " + total/(1000) + "s");

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
		if(randomNumber.compareTo(max) <= 0 && randomNumber.compareTo(min) >= 0) {
			valid = true;
		}
		return valid;
	}
	
	private static int findDigit(BigDecimal max){
		BigInteger temp = max.toBigInteger();
		System.out.println("Digit : " + BigDecimal.valueOf(temp.toString().length()));

		return temp.toString().length();
	}
	
	private static int findFraction(BigDecimal min,BigDecimal max) {
		BigDecimal fractionMin = min.remainder(BigDecimal.ONE);
		BigDecimal fractionMax = max.remainder(BigDecimal.ONE);
		if(fractionMin.equals(BigDecimal.ZERO) && fractionMax.equals(BigDecimal.ZERO)) {
			return 0;
		}
		int minLength = fractionMin.toString().length() - 2;
		int maxLength = fractionMax.toString().length() - 2;
		if(maxLength - minLength >=0) {
			System.out.println("fraction size : " + maxLength);
			return maxLength;
		}else {
			System.out.println("fraction size : " + minLength);
			return minLength;
		}
	}
	
	private static BigDecimal getRandomNumber(int digit,int fractionDigit) {
		Double random = Math.random();
		if(random.equals(Double.valueOf(1.0))) {
			random = Double.sum(random, -Math.random());
		}
		BigDecimal randomNumber = BigDecimal.valueOf(random * Math.pow(10, digit));	
		System.out.println("plain : " + randomNumber);
		randomNumber = randomNumber.setScale(fractionDigit,RoundingMode.DOWN);
		System.out.println("round random : " + randomNumber);
		return randomNumber;
	}
	
	private static void test() {
		Double random = 1.0;
		if(random.equals(Double.valueOf(1.0))) {
			System.out.println("b4 : " + random);
			random = Double.sum(random, -Math.random());
			System.out.println("af : " + random);
		}
		BigDecimal randomNumber = BigDecimal.valueOf(random * Math.pow(10, 1));	
		System.out.println("plain : " + randomNumber);
		randomNumber = randomNumber.setScale(1,RoundingMode.DOWN);
		System.out.println("round random : " + randomNumber);
		
	}
	

}
