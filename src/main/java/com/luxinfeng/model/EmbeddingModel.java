package com.luxinfeng.model;

import ai.onnxruntime.OrtException;
import com.luxinfeng.entity.HuggingFaceEntity;
import com.luxinfeng.entity.VecDistanceEnum;
import com.luxinfeng.utils.HFDownLoadUtils;
import opennlp.dl.vectors.SentenceVectorsDL;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class EmbeddingModel {

    private HuggingFaceEntity entity;

    private SentenceVectorsDL documentVecDL;

    public EmbeddingModel(String modelName) throws IOException, OrtException {
        entity = new HuggingFaceEntity();
        if (modelName == null || modelName.isEmpty()) {
            modelName = "nomic-ai/nomic-embed-text-v1";
        }
        String vocabFileName = HFDownLoadUtils.getVocabFileName(modelName);

        // 获取当前工作目录
        String currentDirectory = Paths.get("").toAbsolutePath().toString();
        File modelFile = new File(currentDirectory, modelName);
        File vocabFile = new File(currentDirectory, vocabFileName);

        //如果目标文件存在，则直接使用无需下载
        if (!modelFile.exists() || !vocabFile.exists()) {
            entity = HFDownLoadUtils.download(modelName);
        } else {
            entity.setModelFile(modelFile);
            entity.setVocabFile(vocabFile);
        }

        documentVecDL = new SentenceVectorsDL(entity.getModelFile(), entity.getVocabFile());

    }

    /**
     * 计算两个字符串之间的语义相似度，默认使用余弦相似度。
     *
     * @param strA 第一个字符串
     * @param strB 第二个字符串
     * @return 两个字符串之间的语义相似度
     * @throws OrtException 如果计算过程中发生错误
     */
    public double calDistance(String strA, String strB) throws OrtException {
        return calDistance(strA, strB, VecDistanceEnum.COSINE_SIMILARITY.getType());
    }

    /**
     * 计算两个字符串之间的语义相似度，使用指定的距离度量类型。
     *
     * @param strA 第一个字符串
     * @param strB 第二个字符串
     * @param vecDistanceType 距离度量类型的枚举值
     * @return 两个字符串之间的语义相似度
     * @throws OrtException 如果计算过程中发生错误
     * @throws IllegalArgumentException 如果输入字符串为NULL
     */
    public double calDistance(String strA, String strB, Integer vecDistanceType) throws OrtException {
        if (strA == null || strB == null) {
            throw new IllegalArgumentException("The input parameter cannot be NULL");
        }

        float[] vecA = documentVecDL.getVectors(strA);
        float[] vecB = documentVecDL.getVectors(strB);

        VecDistanceEnum distanceType = VecDistanceEnum.fromType(vecDistanceType);

        return distanceType.calculate(vecA, vecB);
    }

    /**
     * 计算一个字符串的向量表示。
     *
     * @param str 输入字符串
     * @return 字符串的向量表示
     * @throws OrtException 如果计算过程中发生错误
     * @throws IllegalArgumentException 如果输入字符串为NULL
     */
    public float[] calVector(String str) throws OrtException {
        if (str == null) {
            throw new IllegalArgumentException("The input parameter cannot be NULL");
        }

        return documentVecDL.getVectors(str);
    }

    /**
     * 计算源字符串与目标字符串列表中每个字符串的语义相似度，默认使用余弦相似度。
     *
     * @param sourceStr 源字符串
     * @param targetStrList 目标字符串列表
     * @return 源字符串与目标字符串列表中每个字符串的语义相似度列表
     * @throws OrtException 如果计算过程中发生错误
     * @throws IllegalArgumentException 如果输入参数为NULL
     */
    public List<Double> calVectorList(String sourceStr, List<String> targetStrList) throws OrtException {
        return calVectorList(sourceStr, targetStrList, VecDistanceEnum.COSINE_SIMILARITY.getType());
    }

    /**
     * 计算源字符串与目标字符串列表中每个字符串的语义相似度，使用指定的距离度量类型。
     *
     * @param sourceStr 源字符串
     * @param targetStrList 目标字符串列表
     * @param vecDistanceType 距离度量类型的枚举值
     * @return 源字符串与目标字符串列表中每个字符串的距离列表
     * @throws OrtException 如果计算过程中发生错误
     * @throws IllegalArgumentException 如果输入参数为NULL
     */
    public List<Double> calVectorList(String sourceStr, List<String> targetStrList, Integer vecDistanceType) throws OrtException {
        if (sourceStr == null || targetStrList == null) {
            throw new IllegalArgumentException("The input parameter cannot be NULL");
        }

        VecDistanceEnum distanceType = VecDistanceEnum.fromType(vecDistanceType);

        float[] sourceVec = documentVecDL.getVectors(sourceStr);

        List<Double> scoreList = targetStrList.stream().map(p -> {
            try {
                float[] targetVec = documentVecDL.getVectors(p);
                return distanceType.calculate(sourceVec, targetVec);
            } catch (OrtException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return scoreList;
    }




}