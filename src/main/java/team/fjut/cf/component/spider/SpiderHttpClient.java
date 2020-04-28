package team.fjut.cf.component.spider;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 爬虫通讯类
 *
 * @author axiang [2020/4/28]
 */
@Component
@Slf4j
public class SpiderHttpClient {
    @Resource
    RestTemplate restTemplate;

    @Value("${cf.config.spider.daemonStatusUrl}")
    private String daemonStatusUrl;

    @Value("${cf.config.spider.listProjectsUrl}")
    private String listProjectsUrl;

    @Value("${cf.config.spider.listSpidersUrl}")
    private String listSpidersUrl;

    @Value("${cf.config.spider.listJobsUrl}")
    private String listJobsUrl;

    /**
     * 初始化设置头部
     */
    public SpiderHttpClient() {
        //入参设置头部
    }

    /**
     * 获取scrapyd服务器运行状态
     *
     * @return
     */
    public JSONObject getDaemonStatus() {
        return restTemplate.getForObject(daemonStatusUrl, JSONObject.class);
    }

    /**
     * 获取scrapyd服务器已部署爬虫项目列表
     *
     * @return
     */
    public JSONObject getListProjects() {
        return restTemplate.getForObject(listProjectsUrl, JSONObject.class);
    }

    /**
     * 获取爬虫列表
     *
     * @return
     */
    public JSONObject getListSpiders() {
        String currentUrl = String.format(listSpidersUrl, "CodingFirstSpider");
        return restTemplate.getForObject(currentUrl, JSONObject.class);
    }

    /**
     * 获取任务列表
     *
     * @return
     */
    public JSONObject getListJobs() {
        String currentUrl = String.format(listJobsUrl, "CodingFirstSpider");
        return restTemplate.getForObject(currentUrl, JSONObject.class);
    }

}
