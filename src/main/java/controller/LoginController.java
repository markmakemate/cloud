package controller;


import model.Admin;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RestController(value = "/cloud")
public class LoginController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login.html")
    public void login(String username, String password, HttpServletRequest request,
                        HttpServletResponse response){
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        JSONObject jsonObject;
        if(!adminService.isUserNameExist(username)){
            jsonMap.put("msg", "用户名不存在！");
            jsonMap.put("code", -1);
            jsonObject = new JSONObject(jsonMap);
        }
        else if(!adminService.isPassWordRight(username, password)) {
            jsonMap.put("msg", "密码错误");
            jsonMap.put("code", -2);
            jsonObject = new JSONObject(jsonMap);
        }
        else{
            jsonMap.put("code", 1);
            jsonObject = new JSONObject(jsonMap);
        }
        try{
            PrintWriter writer = response.getWriter();
            writer.print(jsonObject.toString());
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/account/signup.html")
    public void signUp(String username, String password, HttpServletRequest request,
                       HttpServletResponse response){
        Admin user = new Admin();
        user.setUsername(username);
        user.setPassword(password);
        adminService.save(user);
    }

    @RequestMapping(value = "/account/user")
    public void show(HttpServletResponse response, @RequestParam("id")Long id){
        Admin user = adminService.findById(id);


    }

}
