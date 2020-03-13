package team.fjut.cf.controller.vj;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import team.fjut.cf.component.judge.vjudge.VirtualJudgeHttpClient;
import team.fjut.cf.component.judge.vjudge.pojo.ProblemDescription;
import team.fjut.cf.component.judge.vjudge.pojo.RequestProblemHtmlParams;
import team.fjut.cf.component.judge.vjudge.pojo.RequestProblemListParams;
import team.fjut.cf.pojo.enums.ResultJsonCode;
import team.fjut.cf.pojo.po.VjProblemInfo;
import team.fjut.cf.pojo.vo.ResultJsonVO;
import team.fjut.cf.service.SystemInfoService;
import team.fjut.cf.service.VjProblemInfoService;

import java.util.Date;
import java.util.Objects;

/**
 * VJudge 题目相关API
 *
 * @author axiang [2020/2/10]
 */
@RestController
@CrossOrigin
@RequestMapping("/vj/problem")
public class VjProblemController {
    @Autowired
    VirtualJudgeHttpClient virtualJudgeHttpClient;

    @Autowired
    VjProblemInfoService vjProblemInfoService;

    @Autowired
    SystemInfoService systemInfoService;

    @PostMapping("/list")
    public ResultJsonVO getVJProblemList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize,
                                         @RequestParam(value = "OJId", required = false) String OJId,
                                         @RequestParam(value = "category", required = false) String category,
                                         @RequestParam(value = "probNum", required = false) String probNum,
                                         @RequestParam(value = "title", required = false) String title,
                                         @RequestParam(value = "source", required = false) String source) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        RequestProblemListParams params = new RequestProblemListParams();
        params.setStart((pageNum - 1) * pageSize);
        params.setLength(pageSize);
        params.setOJId(StringUtils.isEmpty(OJId) ? "All" : OJId);
        params.setCategory(StringUtils.isEmpty(category) ? "all" : category);
        if (!Objects.isNull(probNum)) {
            params.setProbNum(probNum);
        }
        if (!Objects.isNull(title)) {
            params.setTitle(title);
        }
        if (!Objects.isNull(source)) {
            params.setSource(source);
        }
        JSONObject jsonObject = virtualJudgeHttpClient.postProblemList(params);
        if (Objects.isNull(jsonObject)) {
            resultJsonVO.setStatus(ResultJsonCode.RESOURCE_NOT_EXIST);
        } else {
            resultJsonVO.setStatus(ResultJsonCode.REQUIRED_SUCCESS);
            resultJsonVO.addInfo(jsonObject);
            resultJsonVO.addInfo(new Date());
        }
        return resultJsonVO;
    }

    /**
     * 获取题目信息
     * 执行逻辑如下：
     * 1. 先到本地查找是否有缓存的记录
     * 2. 如果没有，则到VJ上获取数据并返回，插入新的数据库记录
     * 3. 如果有，且缓存时间不超过15分钟，则直接返回数据库中记录
     * 4. 如果有，但缓存时间超过15分钟，重新到VJ上获取数据并返回，同时更新数据库记录
     *
     * @param oJId
     * @param probNum
     * @param username
     * @return
     */
    @PostMapping("/info")
    public ResultJsonVO getProblemInfo(@RequestParam("OJId") String oJId,
                                       @RequestParam("probNum") String probNum,
                                       @RequestParam("username") String username) {
        ResultJsonVO resultJsonVO = new ResultJsonVO(ResultJsonCode.REQUIRED_SUCCESS);
        RequestProblemHtmlParams params = new RequestProblemHtmlParams();
        params.setOJId(oJId);
        params.setProbNum(probNum);
        VjProblemInfo vjProblemInfo = vjProblemInfoService.select(oJId, probNum);
        if (Objects.isNull(vjProblemInfo)) {
            vjProblemInfo = new VjProblemInfo();
            ProblemDescription problemDescription = (ProblemDescription) virtualJudgeHttpClient.getProblemInfo(params);
            resultJsonVO.addInfo(problemDescription);
            String data = JSONObject.toJSONString(problemDescription);
            vjProblemInfo.setOjId(oJId);
            vjProblemInfo.setProbNum(probNum);
            vjProblemInfo.setTime(new Date());
            vjProblemInfo.setData(data);
            vjProblemInfoService.insert(vjProblemInfo);
        } else {
            // 如果超过了15分钟，重新获取并更新数据
            if (System.currentTimeMillis() - vjProblemInfo.getTime().getTime()
                    >= 1000 * 60 * 15) {
                ProblemDescription problemDescription = (ProblemDescription) virtualJudgeHttpClient.getProblemInfo(params);
                resultJsonVO.addInfo(problemDescription);
                String data = JSONObject.toJSONString(problemDescription);
                vjProblemInfo.setData(data);
                vjProblemInfo.setTime(new Date());
                vjProblemInfoService.update(vjProblemInfo);
            } else {
                String data = vjProblemInfo.getData();
                Object obj = JSONObject.parse(data);
                resultJsonVO.addInfo(obj);
            }
        }
        return resultJsonVO;
    }

}
