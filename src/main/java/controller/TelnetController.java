package controller;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import model.Admin;
import model.Developer;
import org.apache.sshd.common.util.OsUtils;
import org.apache.sshd.common.util.security.SecurityUtils;
import org.apache.sshd.common.util.security.bouncycastle.BouncyCastleGeneratorHostKeyProvider;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.shell.ProcessShellFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.AdminRepository;
import repository.DeveloperRepository;
import service.AdminService;
import service.ProxyServerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.*;
import java.util.EnumSet;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController("/cloud/dvm")
@ServerEndpoint("/cloud/dvm/open")
public class TelnetController {


    @Resource(name = "developerRepository")
    DeveloperRepository developerRepository;

    @Resource(name = "adminService")
    AdminService adminService;

    @Resource(name = "adminRepository")
    AdminRepository adminRepository;


    @RequestMapping("/init")
    public void check(@RequestParam("id")Long id, HttpServletResponse response, HttpServletRequest request){
        Long userId = (Long)request.getSession().getAttribute("loginuser");
        Admin user = adminService.findById(userId);
        JSONObject jsonObject = new JSONObject();
        if(user!=null){
            jsonObject.put("username", user.getUsername());
            jsonObject.put("userId", user.getUserId());
            try{
                PrintWriter writer = response.getWriter();
                writer.print(jsonObject.toString());
                writer.flush();
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @OnOpen
    void open(@RequestParam("id")Long id, Session session) throws  IOException{
        Developer developer = developerRepository.findByUserId(id);


    }

    @OnMessage
    void channel(String message) throws IOException{

    }
}
