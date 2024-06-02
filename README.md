
# SemanticSearch

SemanticSearch 是一个用于计算语义相似度的 Java SDK，开源且免费。它使用 Hugging Face 的模型来生成句子的向量表示，并计算字符串之间的语义相似度。

## 特性

- 计算两个字符串之间的语义相似度
- 计算一个字符串的向量表示
- 计算一个字符串与目标字符串列表中每个字符串的语义相似度
- 支持多种距离度量类型（默认使用余弦相似度）

## 安装

将以下依赖添加到你的 `pom.xml` 文件中：

```xml
<dependency>
    <groupId>io.github.luxinfeng</groupId>
    <artifactId>SemanticSearch</artifactId>
    <version>0.1.0</version>
</dependency>
```

## 使用方法

### 初始化 `EmbeddingModel`

```java
import io.github.luxinfeng.SemanticSearch.EmbeddingModel;
import ai.onnxruntime.OrtException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            EmbeddingModel model = new EmbeddingModel("nomic-ai/nomic-embed-text-v1");
            // 其他代码...
        } catch (IOException | OrtException e) {
            e.printStackTrace();
        }
    }
}
```

### 计算两个字符串之间的语义相似度

```java
import ai.onnxruntime.OrtException;

public class Main {
    public static void main(String[] args) {
        try {
            EmbeddingModel model = new EmbeddingModel("nomic-ai/nomic-embed-text-v1");
            double similarity = model.calDistance("Hello, world!", "Hi, there!");
            System.out.println("相似度: " + similarity);
        } catch (IOException | OrtException e) {
            e.printStackTrace();
        }
    }
}
```

### 计算一个字符串的向量表示

```java
import ai.onnxruntime.OrtException;

public class Main {
    public static void main(String[] args) {
        try {
            EmbeddingModel model = new EmbeddingModel("nomic-ai/nomic-embed-text-v1");
            float[] vector = model.calVector("Hello, world!");
            System.out.println("向量表示: " + Arrays.toString(vector));
        } catch (IOException | OrtException e) {
            e.printStackTrace();
        }
    }
}
```

### 计算源字符串与目标字符串列表中每个字符串的语义相似度

```java
import ai.onnxruntime.OrtException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            EmbeddingModel model = new EmbeddingModel("nomic-ai/nomic-embed-text-v1");
            List<String> targetList = Arrays.asList("Hi, there!", "Greetings!", "Hello!");
            List<Double> similarities = model.calVectorList("Hello, world!", targetList);
            System.out.println("相似度列表: " + similarities);
        } catch (IOException | OrtException e) {
            e.printStackTrace();
        }
    }
}
```

## 方法

### `EmbeddingModel(String modelName)`

构造函数，初始化模型。

- `modelName` - 模型名称。如果为 `null` 或空字符串，默认使用 `nomic-ai/nomic-embed-text-v1`。

### `double calDistance(String strA, String strB)`

计算两个字符串之间的语义相似度，默认使用余弦相似度。

- `strA` - 第一个字符串
- `strB` - 第二个字符串

返回值：两个字符串之间的语义相似度。

### `double calDistance(String strA, String strB, Integer vecDistanceType)`

计算两个字符串之间的语义相似度，使用指定的距离度量类型。

- `strA` - 第一个字符串
- `strB` - 第二个字符串
- `vecDistanceType` - 距离度量类型的枚举值

返回值：两个字符串之间的语义相似度。

### `float[] calVector(String str)`

计算一个字符串的向量表示。

- `str` - 输入字符串

返回值：字符串的向量表示。

### `List<Double> calVectorList(String sourceStr, List<String> targetStrList)`

计算源字符串与目标字符串列表中每个字符串的语义相似度，默认使用余弦相似度。

- `sourceStr` - 源字符串
- `targetStrList` - 目标字符串列表

返回值：源字符串与目标字符串列表中每个字符串的语义相似度列表。

### `List<Double> calVectorList(String sourceStr, List<String> targetStrList, Integer vecDistanceType)`

计算源字符串与目标字符串列表中每个字符串的语义相似度，使用指定的距离度量类型。

- `sourceStr` - 源字符串
- `targetStrList` - 目标字符串列表
- `vecDistanceType` - 距离度量类型的枚举值

返回值：源字符串与目标字符串列表中每个字符串的距离列表。


# [English]
# SemanticSearch

SemanticSearch is an open-source and free Java SDK for calculating semantic similarity. It uses models from Hugging Face to generate sentence vector representations and calculate the semantic similarity between strings.

## Features

- Calculate semantic similarity between two strings
- Compute the vector representation of a string
- Calculate the semantic similarity between a source string and a list of target strings
- Support for multiple distance metrics (default is cosine similarity)

## Installation

Add the following dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>io.github.luxinfeng</groupId>
    <artifactId>SemanticSearch</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Usage

### Initializing `EmbeddingModel`

```java
import io.github.luxinfeng.SemanticSearch.EmbeddingModel;
import ai.onnxruntime.OrtException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            EmbeddingModel model = new EmbeddingModel("nomic-ai/nomic-embed-text-v1");
            // other code...
        } catch (IOException | OrtException e) {
            e.printStackTrace();
        }
    }
}
```

### Calculating Semantic Similarity Between Two Strings

```java
import ai.onnxruntime.OrtException;

public class Main {
    public static void main(String[] args) {
        try {
            EmbeddingModel model = new EmbeddingModel("nomic-ai/nomic-embed-text-v1");
            double similarity = model.calDistance("Hello, world!", "Hi, there!");
            System.out.println("Similarity: " + similarity);
        } catch (IOException | OrtException e) {
            e.printStackTrace();
        }
    }
}
```

### Computing the Vector Representation of a String

```java
import ai.onnxruntime.OrtException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            EmbeddingModel model = new EmbeddingModel("nomic-ai/nomic-embed-text-v1");
            float[] vector = model.calVector("Hello, world!");
            System.out.println("Vector representation: " + Arrays.toString(vector));
        } catch (IOException | OrtException e) {
            e.printStackTrace();
        }
    }
}
```

### Calculating Semantic Similarity Between a Source String and a List of Target Strings

```java
import ai.onnxruntime.OrtException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            EmbeddingModel model = new EmbeddingModel("nomic-ai/nomic-embed-text-v1");
            List<String> targetList = Arrays.asList("Hi, there!", "Greetings!", "Hello!");
            List<Double> similarities = model.calVectorList("Hello, world!", targetList);
            System.out.println("Similarity list: " + similarities);
        } catch (IOException | OrtException e) {
            e.printStackTrace();
        }
    }
}
```

## Methods

### `EmbeddingModel(String modelName)`

Constructor, initializes the model.

- `modelName` - The model name. If `null` or empty, defaults to `nomic-ai/nomic-embed-text-v1`.

### `double calDistance(String strA, String strB)`

Calculates the semantic similarity between two strings using the default cosine similarity.

- `strA` - The first string
- `strB` - The second string

Returns: The semantic similarity between the two strings.

### `double calDistance(String strA, String strB, Integer vecDistanceType)`

Calculates the semantic similarity between two strings using the specified distance metric.

- `strA` - The first string
- `strB` - The second string
- `vecDistanceType` - The distance metric type as an enum value

Returns: The semantic similarity between the two strings.

### `float[] calVector(String str)`

Computes the vector representation of a string.

- `str` - The input string

Returns: The vector representation of the string.

### `List<Double> calVectorList(String sourceStr, List<String> targetStrList)`

Calculates the semantic similarity between a source string and a list of target strings using the default cosine similarity.

- `sourceStr` - The source string
- `targetStrList` - The list of target strings

Returns: A list of semantic similarities between the source string and each target string.

### `List<Double> calVectorList(String sourceStr, List<String> targetStrList, Integer vecDistanceType)`

Calculates the semantic similarity between a source string and a list of target strings using the specified distance metric.

- `sourceStr` - The source string
- `targetStrList` - The list of target strings
- `vecDistanceType` - The distance metric type as an enum value

Returns: A list of distances between the source string and each target string.