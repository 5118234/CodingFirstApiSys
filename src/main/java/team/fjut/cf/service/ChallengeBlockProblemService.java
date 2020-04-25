package team.fjut.cf.service;

import team.fjut.cf.pojo.vo.ChallengeBlockProblemAdminVO;
import team.fjut.cf.pojo.vo.ChallengeBlockProblemVO;

import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
public interface ChallengeBlockProblemService {
    /**
     * 分页查询模块内的题目
     *
     * @param username
     * @param blockId
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ChallengeBlockProblemVO> pagesByBlockId(String username,
                                                 Integer blockId,
                                                 Integer pageNum,
                                                 Integer pageSize);

    /**
     * 查询模块内题目的总数量
     *
     * @param blockId
     * @return
     */
    Integer selectCountByBlockId(Integer blockId);


    /**
     * @author zhongml [2020/4/24]
     * 根据ID查询挑战模块题目列表
     *
     * @param pageNum
     * @param pageSize
     * @param sort
     * @param blockId
     * @return
     */
    List<ChallengeBlockProblemVO> selectProblemByBlockId(Integer pageNum, Integer pageSize, String sort, Integer blockId);

    /**
     * @author zhongml [2020/4/24]
     * 查询挑战模块题目数量
     *
     * @param blockId
     * @return
     */
    int countProblemByBlockId(Integer blockId);

    /**
     * @author zhongml [2020/4/24]
     * 插入模块题目
     *
     * @return
     */
    int insertProblems(Integer blockId, List<ChallengeBlockProblemAdminVO> challengeProblems);

    /**
     * @author zhongml [2020/4/25]
     * 按blockId删除所有模块题目
     *
     * @param blockId
     * @return
     */
    int deleteProblems(Integer blockId);
}
