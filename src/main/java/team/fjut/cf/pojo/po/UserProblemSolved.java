package team.fjut.cf.pojo.po;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author axiang [2019/11/11]
 */
@Data
@Table(name = "t_user_problem_solved")
public class UserProblemSolved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    Integer id;
    String username;
    Integer problemId;
    Integer tryCount;
    Integer solvedCount;
    Date lastTryTime;
    Date firstSolvedTime;
}
