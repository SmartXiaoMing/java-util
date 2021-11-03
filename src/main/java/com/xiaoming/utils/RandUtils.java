package com.xiaoming.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandUtils {
    public static final Random random = new Random();

    public static<T> T pick(List<T> list) { // 从list等概率选择一个元素。如果有重复元素，就可以得到加权
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(random.nextInt(list.size()));
    }

    public static<T> List<T> pick(List<T> list, int count) { // 从list等概率选择n个元素。尽可能保证抽样结果与list中权重一致, 结果顺序随机
        List<T> result = new ArrayList<>(count);
        if (list == null || list.isEmpty()) {
            return result;
        }
        while (count >= list.size()) {
            result.addAll(list);
            count -= list.size();
        }
        int i = random.nextInt(list.size());
        while (count --> 0) {
            result.add(list.get(i));
            i = (i + 1) % list.size();
        }
        Collections.shuffle(result);
        return result;
    }

    public static int pick(int min, int max) { // 均匀分布：从[min, max]等概率选出一个整数
        int diff = max - min;
        if (diff <= 0) {
            return min;
        }
        diff++;
        return random.nextInt(diff) + min;
    }

    public static boolean hit(double r) { // 随机数是否命中区间[0, r), r <= 1
        return random.nextDouble() < r;
    }

    public static int calcCount(int n, double r) { // 计算n的r比例，返回此次的数量
        if (r >= 1) {
            return n;
        }
        if (r <= 0) {
            return 0;
        }
        double nr = n * r;
        int count = (int) nr; // 至少出现count次
        double fraction = nr - count; // 小数部分的百分比，代表多1次的概率
        if (fraction > 0 && hit(fraction)) {
            count++;
        }
        return count;
    }

    public static boolean[] selectAsBool(int n, int count) { // 座位随机分配：n个座位分给count个人，返回指定座位是否有人
        boolean[] array = new boolean[n];
        while (count > 0 && n > 0) {
            int j = random.nextInt(n);
            n--;
            if (j < count) {
                count--;
                array[n] = true;
            }
        }
        return array;
    }

    public static int[] select(int n, int count) { // 座位随机分配：n个座位分给count个人，返回指定有人座位的索引
        int[] result = new int[count];
        while (count > 0 && n > 0) {
            int j = random.nextInt(n);
            n--;
            if (j < count) {
                count--;
                result[count] = n;
            }
        }
        return result;
    }

    public static int[] select(int n, double r) { // 座位随机分配：n个座位分给r比例的人，返回指定座位的索引
        int count = calcCount(n, r);
        return select(n, count);
    }

    public static int playRoulette(int[] weight) { // 轮盘抽奖： 按照轮盘设置的权重比例中奖, 返回命中的索引
        int sum = 0;
        for (int i = 0; i < weight.length; ++i) {
            sum += weight[i];
        }
        if (sum <= 0) {
            return weight.length - 1;
        }
        int r = random.nextInt(sum);
        for (int i = 0; i < weight.length; ++i) {
            if (r < weight[i]) {
                return i;
            }
            r -= weight[i];
        }
        return weight.length - 1;
    }

    public static int divide(int moneySum, int userCount) { // 抢红包，总数数moneySum, 人数是userCount, 每人至少为1
        return divide(moneySum, userCount, 1);
    }

    public static int divide(int moneySum, int userCount, int moneyMin) { // 抢红包，总数数moneySum, 人数是userCount, 每人至少为moneyMin
        if (userCount == 1) {
            return moneySum;
        }
        moneySum -= moneyMin * userCount;
        int average = moneySum / userCount;
        return moneyMin + random.nextInt((average + 1) * 2);
    }
}
