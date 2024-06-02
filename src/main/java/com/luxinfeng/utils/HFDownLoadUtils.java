package com.luxinfeng.utils;

import com.luxinfeng.entity.HuggingFaceEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;

public class HFDownLoadUtils {

    private static final String HF_MODEL_DOWNLOAD_URL = "https://huggingface.co/%s/resolve/main/onnx/model.onnx?download=true";

    private static final String HF_VOCAB_DOWNLOAD_URL = "https://huggingface.co/%s/resolve/main/vocab.txt?download=true";

    private static final HttpClient client = HttpClient.newHttpClient();


    /**
     * 从HF镜像站下载模型文件
     * @param modelName 模型路径名称 类似BAAI/bge-small-en-v1.5
     * @return 返回模型文件和词汇表文件
     */
    public static HuggingFaceEntity download(String modelName) {

        HuggingFaceEntity vo = new HuggingFaceEntity();
        String vocabName = getVocabFileName(modelName);
        try {

            String modelDownloadUrl = String.format(HF_MODEL_DOWNLOAD_URL, modelName);
            String vocabDownloadUrl = String.format(HF_VOCAB_DOWNLOAD_URL, modelName);


            HttpRequest modelRequest = HttpRequest.newBuilder()
                    .uri(URI.create(modelDownloadUrl))
                    .timeout(Duration.ofMinutes(10))
                    .build();

            HttpResponse<InputStream> modelResponse = client.send(modelRequest, HttpResponse.BodyHandlers.ofInputStream());
            if (modelResponse.statusCode() == 200) {
                File modelFile = convertInputStreamToFile(modelResponse.body(), modelName);
                System.out.println("Model downloaded successfully!");
                vo.setModelFile(modelFile);
            }else {
                throw new RuntimeException("Failed to download model!");
            }

            HttpRequest vocabRequest = HttpRequest.newBuilder()
                    .uri(URI.create(vocabDownloadUrl))
                    .timeout(Duration.ofMinutes(10))
                    .build();

            HttpResponse<InputStream> vocabResponse = client.send(vocabRequest, HttpResponse.BodyHandlers.ofInputStream());
            if (vocabResponse.statusCode() == 200) {
                File vocabFile = convertInputStreamToFile(vocabResponse.body(), vocabName);
                System.out.println("Vocab downloaded successfully!");
                vo.setVocabFile(vocabFile);
            } else {
                throw new RuntimeException("Failed to download vocab!");
            }

            return vo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static File convertInputStreamToFile(InputStream inputStream, String fileName) throws IOException {

        // 获取当前工作目录
        String currentDirectory = Paths.get("").toAbsolutePath().toString();
        File file = new File(currentDirectory, fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }

        // 创建一个文件输出流
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            // 将输入流的内容写入文件
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return file;
    }

    public static String getVocabFileName(String modelName) {
        if (modelName == null) {
            return modelName;
        }
        return modelName + "-vocab";
    }

}
