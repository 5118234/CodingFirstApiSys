package team.fjut.cf.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.fjut.cf.config.interceptor.annotation.PermissionRequired;
import team.fjut.cf.pojo.enums.PermissionType;
import team.fjut.cf.pojo.vo.ResultJson;

/**
 * 根路径下的 Controller
 *
 * @author axiang [2020/2/21]
 */
@RestController
@CrossOrigin
@RequestMapping("/")
public class RootController {

    @PermissionRequired(permissions = {PermissionType.TEST_1, PermissionType.BASE_ADMIN})
    @GetMapping("/")
    public ResultJson getRootInfo() {
        return new ResultJson();
    }
}
