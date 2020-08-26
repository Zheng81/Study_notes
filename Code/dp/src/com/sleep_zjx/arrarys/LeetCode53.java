package com.sleep_zjx.arrarys;

import java.util.Scanner;

/**
*@author sleep
*@version 1.0
*@Date 2020年6月14日 下午7:29:34
*LeetCode53
*题目:给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
*示例:
*输入: [-2,1,-3,4,-1,2,1,-5,4],
*输出: 6
*解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
*/
public class LeetCode53 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int[] arrarys = new int[n];
		for (int i = 0; i < n; i++) {
			arrarys[i] = scanner.nextInt();
		}
		System.out.println(MaximumSubarraySum(arrarys));
	}
	public static int MaximumSubarraySum(int[] nums) {
		int[] dp = new int[nums.length];
		dp[0] = nums[0];
		int ans = dp[0];
		for (int i = 1; i < nums.length; i++) {
			dp[i] = Math.max(dp[i - 1], 0) + nums[i];
			ans = Math.max(ans,  dp[i]);
		}
		return ans;
	}
}
