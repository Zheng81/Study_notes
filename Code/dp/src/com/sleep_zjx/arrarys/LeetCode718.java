package com.sleep_zjx.arrarys;

import java.util.Scanner;

/**
*@author sleep
*@version 1.0
*@Date 2020年6月14日 下午7:30:18
*LeetCode718
*给两个整数数组 s 和 t ，返回两个数组中公共的、长度最长的子数组的长度。例如：
*输入:
*s: [1, 2, 3, 2, 1, 5]
*t: [6，3, 2, 1, 4, 7]
*输出: 3
*解释:
*长度最长的公共子数组是 [3, 2, 1]。
*/
public class LeetCode718 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int[] s = new int[n];
		int[] t = new int[n];
		for (int i = 0; i < n; i++) {
			s[i] = scanner.nextInt();
		}
		for (int i = 0; i < n; i++) {
			t[i] = scanner.nextInt();
		}
		System.out.println(findLength(s, t));
	}
	public static int findLength(int[] A, int[] B) {
		int ans = 0;
		int[][] dp = new int[A.length + 1][B.length + 1];
        for (int i = A.length - 1; i >= 0; i--) {
            for (int j = B.length - 1; j >= 0; j--) {
                if(A[i] == B[j]) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                }
                ans = Math.max(ans, dp[i][j]);
            }
        }
		return ans;
	}
}
