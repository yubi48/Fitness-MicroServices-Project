package com.fitness.activityservice;

import java.util.Arrays;

public class AlgoMaster {
    public static void moveZeroes(int[] nums) {
        int iLeft = 0;
        int iRight = nums.length - 1;
        int indexHolder = 0;
        for(int i = 0; iLeft<iRight; i++) {
            //0,1,0,3,12
            if(nums[i] == 0) {
                iLeft = i;
                if(nums[iRight] != 0) {
                    nums[iLeft] = nums[iRight];
                    nums[iRight] = 0;
                    iRight--;
                }
            } else {
                iLeft++;
            }
        }
        System.out.println(Arrays.toString(Arrays.stream(nums).toArray()));
    }

}
