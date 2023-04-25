package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int num) {
        boolean flag = true;
        if (num < 2)
            throw new IllegalNumberException("Number must be positive and bigger than 1.");
            for (int i = 2; i * i <= num; i++) {
                if ((num % i) == 0) {
                    flag = false;
                    break;
                }
            }
        return flag;
    }



    public int digitSum(int num) {
        if (num < 0)
            num = num * -1;
        int digit = 0;
        int res = 0;
        while (num != 0) {
            digit = num % 10;
            res = res + digit;
            num = num / 10;
        }
        return res;
    }

    public class IllegalNumberException extends RuntimeException {
        public IllegalNumberException(String errorMessage) {
            super(errorMessage);
        }
    }

}



