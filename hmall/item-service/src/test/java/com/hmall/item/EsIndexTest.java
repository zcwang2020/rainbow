package com.hmall.item;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EsIndexTest {

    private RestHighLevelClient client;

    /**
     * 创建索引
     */
    @Test
    void testESCreateIndexRequest() {
        CreateIndexRequest request = new CreateIndexRequest("rainbow_items");
        request.mapping(MAP_SOURCE, XContentType.JSON);
        try {
            client.indices().create(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取索引
     */
    @Test
    void testESGetIndexRequest() {
        GetIndexRequest request = new GetIndexRequest("rainbow_items");
        try {
            boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
            System.out.println("exists = " + exists);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void testESDeleteIndexRequest() {
        DeleteIndexRequest request = new DeleteIndexRequest("rainbow_items");
        try {
            client.indices().delete(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
        client = new RestHighLevelClient(RestClient.builder(HttpHost.create("http://116.62.157.97:9200")));
    }

    @AfterEach
    void tearDown() {
        try {
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String MAP_SOURCE = "{\n" +
            "    \"dynamic\": \"strict\",\n" +
            "    \"properties\": {\n" +
            "      \"id\": {\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"ignore_above\": 256\n" +
            "      },\n" +
            "      \"name\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"price\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"image\": {\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"category\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"brand\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"sold\": {\n" +
            "        \"type\": \"long\"\n" +
            "      },\n" +
            "      \"commentCount\": {\n" +
            "        \"type\": \"integer\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"isAD\": {\n" +
            "        \"type\": \"boolean\"\n" +
            "      },\n" +
            "      \"updateTime\": {\n" +
            "        \"type\": \"date\"\n" +
            "      }\n" +
            "    }\n" +
            "  }";
}