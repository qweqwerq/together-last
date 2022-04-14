package com.project.together.controller;

import com.project.together.VO.UserVO;
import com.project.together.entity.User;
import com.project.together.repository.UserMapper;
import com.project.together.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    //Mybatis Mapper
    private final UserMapper userMapper;

    @GetMapping("/users/new")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        log.info("회원가입 페이지");
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(@Valid UserForm form, BindingResult result) {

        if(result.hasErrors()) {
            return "users/createUserForm";
        }

        User user = new User();
        user.setUserId(form.getUserId());
        user.setUserPw(form.getUserPw());
        user.setUserName(form.getUserName());
        user.setUserPhone(form.getUserPhone());
        user.setCreatedAt(LocalDateTime.now());
        userService.join(user);
        log.info("회원가입 성공");
        return "redirect:/";
    }

    //updateUserForm 오버로딩 메소드
    /***
     * @Desc : 화면 호출 및 유저 정보 조회
     * 회원정보 수정
     */
    @GetMapping("/updateUserForm")
    public String updateUserForm(
            HttpServletRequest request,
            @SessionAttribute(name = SessionConstants.LOGIN_USER, required = false) User loginUser
            , Model model) throws Exception{

        UserVO userVO = new UserVO();
        userVO.setUserId(loginUser.getUserId());
        List<UserVO> userVOList = userMapper.selectUser(userVO);

        //userID 는 무조건 하나이니깐 List 0번째에서 가져오면 됨.
        model.addAttribute("user", userVOList.get(0));

        HttpSession session = request.getSession();                         // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
        session.setAttribute(SessionConstants.LOGIN_USER, loginUser);

        return "users/updateUserForm";
    }

    /***
     * @Desc : 화면 호출 및 유저 정보 조회
     * 회원정보 수정
     */
    @PostMapping("/updateUserForm")
    public String updateUserForm(@ModelAttribute UserVO userVO, Model model) throws Exception{
        System.out.println(userVO.toString());
        List<UserVO> originUserVO = userMapper.selectUser(userVO);
        if(!originUserVO.get(0).getUserPw().equals(userVO.getUserPw())){
            model.addAttribute("user", originUserVO.get(0));
            model.addAttribute("incorrectPW", "비밀번호가 틀렸습니다.");
            return "users/updateUserForm";
        }
        int check = userMapper.updateUser(userVO);
        System.out.println(check);
        return "redirect:/";
    }


    /***
     * @Desc : postman 전용, 서버개발
     * 회원정보 조회
     */
    @PostMapping("/selectUser")
    @ResponseBody
    public List<UserVO> selectUser(@RequestBody UserVO userVO) throws Exception{
        return userMapper.selectUser(userVO);
    }

    /***
     * @Desc : postman 전용, 서버개발
     * 회원정보 수정
     */
    @PostMapping("/updateUser")
    @ResponseBody
    public int updateUser(@RequestBody UserVO userVO) throws Exception{
        return userMapper.updateUser(userVO);
    }

    /***
     * @Desc : postman 전용, 서버개발
     * 회원가입
     */
    @PostMapping("/joinUser")
    @ResponseBody
    public int joinUser(@RequestBody UserVO userVO) throws Exception{
        return userMapper.joinUser(userVO);
    }
}
