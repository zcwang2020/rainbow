package com.hmall.item;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmall.common.utils.BeanUtils;
import com.hmall.item.domain.doc.ItemDoc;
import com.hmall.item.domain.po.Item;
import com.hmall.item.service.IItemService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@SpringBootTest(properties = {"spring.profiles.active=dev"})
class EsDocTest {

    private RestHighLevelClient client;

    @Autowired
    private IItemService itemService;

    @Test
    void testESBulkDocRequest() {
        int pageNo = 1, pageSize = 500;
        while (true) {
            Page<Item> page = itemService.lambdaQuery().eq(Item::getStatus, 1)
                    .page(Page.of(pageNo, pageSize));
            List<Item> records = page.getRecords();
            if (CollectionUtils.isEmpty(records)) {
                return;
            }
            BulkRequest bulkRequest = new BulkRequest();
            for (Item item : records) {
                // String jsonString = JSON.toJSONString(item);
                // ItemDoc itemDoc = JSON.parseObject(jsonString, ItemDoc.class);
                bulkRequest.add(new IndexRequest("rainbow_items").id(item.getId().toString())
                        .source(JSON.toJSONString(BeanUtils.copyProperties(item, ItemDoc.class)), XContentType.JSON));
            }
            try {
                client.bulk(bulkRequest, RequestOptions.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            pageNo++;
        }
    }

    /**
     * 创建索引
     */
    @Test
    void testESCreateDocRequest() {
        IndexRequest indexRequest = new IndexRequest();
        ItemDoc itemDoc = JSON.parseObject(ITEM_DTO, ItemDoc.class);
        indexRequest.index("rainbow_items").id(itemDoc.getId()).source(JSON.toJSONString(itemDoc), XContentType.JSON);
        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取索引
     */
    @Test
    void testESGetDocRequest() {
        GetRequest request = new GetRequest("rainbow_items").id("317578");
        try {
            String itemDocJson = client.get(request, RequestOptions.DEFAULT).getSourceAsString();
            System.out.println("itemDocJson = " + itemDocJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void testESDeleteDocRequest() {
        DeleteRequest request = new DeleteRequest("rainbow_items").id("317578");
        try {
            client.delete(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testESUpdateDocRequest() throws IOException {
        UpdateRequest request = new UpdateRequest("rainbow_items", "317578");
        request.doc("price", 399.00);
        try {
            client.update(request, RequestOptions.DEFAULT);
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

    private static final String ITEM_DTO = "{\n" +
            "  \"id\": 317578,\n" +
            "  \"name\": \"RIMOWA 21寸托运箱拉杆箱 SALSA AIR系列果绿色 820.70.36.4\",\n" +
            "  \"price\": 289.00,    // 价格单位转换（分转元）\n" +
            "  \"stock\": 10000,\n" +
            "  \"image\": \"https://m.360buyimg.com/mobilecms/s720x720_jfs/t6934/364/1195375010/84676/e9f2c55f/597ece38N0ddcbc77.jpg!q70.jpg.webp\",\n" +
            "  \"category\": \"拉杆箱\",\n" +
            "  \"brand\": \"RIMOWA\",\n" +
            "  \"spec\": {           // JSON 对象替代原始字符串\n" +
            "    \"颜色\": \"红色\",\n" +
            "    \"尺码\": \"21寸\"\n" +
            "  },\n" +
            "  \"sold\": 0,\n" +
            "  \"comment_count\": 0,\n" +
            "  \"isAD\": 1,\n" +
            "  \"status\": 1,\n" +
            "  \"create_time\": \"2019-05-01 00:00:00\",  // ISO 8601 时间格式\n" +
            "  \"update_time\": \"2023-05-06 11:06:17\",\n" +
            "  \"creater\": null,\n" +
            "  \"updater\": 100\n" +
            "}";
}