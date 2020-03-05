package team.fjut.cf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.fjut.cf.mapper.*;
import team.fjut.cf.pojo.enums.ProblemType;
import team.fjut.cf.pojo.po.ProblemInfo;
import team.fjut.cf.pojo.po.ProblemSamplePO;
import team.fjut.cf.pojo.po.ProblemTypeCountPO;
import team.fjut.cf.pojo.po.ProblemViewPO;
import team.fjut.cf.pojo.vo.UserRadarVO;
import team.fjut.cf.service.ProblemService;

import java.util.*;

/**
 * @author axiang [2019/10/22]
 */
@Service
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    ProblemInfoMapper problemInfoMapper;

    @Autowired
    ProblemViewMapper problemViewMapper;

    @Autowired
    ProblemSampleMapper problemSampleMapper;

    @Autowired
    ProblemDifficultMapper problemDifficultMapper;

    @Autowired
    UserProblemSolvedMapper userProblemSolvedMapper;

    @Autowired
    ProblemTagRecordMapper problemTagRecordMapper;


    @Override
    public ProblemInfo selectProblemInfoByProblemId(Integer problemId) {
        return problemInfoMapper.selectByProblemId(problemId);
    }

    @Override
    public ProblemViewPO selectProblemViewByProblemId(Integer problemId) {
        return problemViewMapper.selectByProblemId(problemId);
    }

    @Override
    public List<ProblemSamplePO> selectProblemSampleByProblemId(Integer problemId) {
        return problemSampleMapper.selectByProblemId(problemId);
    }


    @Override
    public List<UserRadarVO> selectUserProblemRadarByUsername(String username) {
        List<ProblemTypeCountPO> problemTypeCounts = problemDifficultMapper.selectCountType();
        List<ProblemTypeCountPO> userProblemTypeCounts = userProblemSolvedMapper.selectCountTypeByUsername(username);

        List<UserRadarVO> results = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        map.put(1, 0);
        map.put(2, 0);
        map.put(3, 0);
        map.put(4, 0);
        map.put(5, 0);
        map.put(6, 0);
        for (ProblemTypeCountPO userPtc : userProblemTypeCounts) {
            map.put(userPtc.getProblemType(), userPtc.getTotalCount() == null ? 0 : userPtc.getTotalCount());
        }

        for (ProblemTypeCountPO ptc : problemTypeCounts) {
            UserRadarVO userRadarVO = new UserRadarVO();
            userRadarVO.setType(ProblemType.getNameByID(ptc.getProblemType()));
            userRadarVO.setScore(100 * map.get(ptc.getProblemType()) / ptc.getTotalCount());
            results.add(userRadarVO);
        }
        return results;
    }

    @Override
    public List<ProblemInfo> selectRecommendProblemsByUsername(String username) {
        List<ProblemInfo> problemInfos = problemInfoMapper.selectUnSolvedProblemsByUsername(username);
        List<ProblemInfo> results = new ArrayList<>();
        int totalUnsolved = problemInfos.size();
        // 如果用户未解决题目小于3，则直接返回推荐题目内容
        if (totalUnsolved <= 3) {
            return problemInfos;
        }
        // 如果大于3道，随机推荐3道
        // TODO: 可以做更多的操作
        Random random = new Random();
        Set<Integer> set = new TreeSet<>();
        while (true) {
            int randomInt = random.nextInt(totalUnsolved);
            set.add(randomInt);
            if (set.size() == 3) {
                break;
            }
        }
        for (Integer i : set) {
            results.add(problemInfos.get(i));
        }
        return results;

    }
}
