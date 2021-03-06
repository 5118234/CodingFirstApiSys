package team.fjut.cf.service;

import team.fjut.cf.pojo.vo.response.BorderHonorRankVO;

import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
public interface BorderHonorRankService {
    /**
     * 分页查询榜单
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<BorderHonorRankVO> pages(Integer pageNum, Integer pageSize, String sort,
                                  String realName, Integer awardLevel, Integer contestLevel);

    /**
     * 按条件查询记录数
     *
     * @return
     * @author zhongml [2020/4/26]
     */
    Integer countByCondition(String realName, Integer awardLevel, Integer contestLevel);

    /**
     * 根据ID删除记录
     *
     * @param id
     * @return
     * @author zhongml [2020/4/26]
     */
    int deleteHonor(Integer id);

    /**
     * 修改记录
     *
     * @param borderHonorRankVO
     * @return
     * @author zhongml [2020/4/26]
     */
    int updateHonor(BorderHonorRankVO borderHonorRankVO);

    /**
     * 新增一条记录
     *
     * @param borderHonorRankVO
     * @return
     * @author zhongml [2020/4/26]
     */
    int insertHonor(BorderHonorRankVO borderHonorRankVO);

    /**
     * 根据ID查询记录
     *
     * @param id
     * @return
     * @author zhongml [2020/4/26]
     */
    BorderHonorRankVO selectById(Integer id);

    /**
     * 查询榜单记录数
     *
     * @return
     */
    Integer selectAllCount();

    /**
     * 根据用户名查询用户参与榜单字符串
     *
     * @param username
     * @return
     */
    List<String> selectByUsername(String username);

}
