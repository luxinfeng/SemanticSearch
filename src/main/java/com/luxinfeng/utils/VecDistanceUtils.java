package com.luxinfeng.utils;

public class VecDistanceUtils {

    /**
     * 计算向量的余弦相似度 值介于-1和1之间，越接近1，向量越相似
     */
    public static double cosineSimilarity(float[] vecA, float[] vecB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vecA.length; i++) {
            dotProduct += vecA[i] * vecB[i];
            normA += Math.pow(vecA[i], 2);
            normB += Math.pow(vecB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    /**
     * 闵可夫斯基距离是欧几里得距离和曼哈顿距离的推广，包含了不同的距离度量。 当p=1 时，为曼哈顿距离；当p=2 时，为欧几里得距离。
     * 值越小表示向量越相似
     */
    public static double minkowskiDistance(float[] vecA, float
            [] vecB, int p) {
        double sum = 0.0;
        for (int i = 0; i < vecA.length; i++) {
            sum += Math.pow(Math.abs(vecA[i] - vecB[i]), p);
        }
        return Math.pow(sum, 1.0 / p);
    }



}
