package service;


import model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.AdminRepository;


@Service(value = "adminService")
@Transactional
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    public Boolean isUserNameExist(String username){
        boolean flag = false;
        if(adminRepository.findByName(username)!=null){
            flag = true;
        }
        return flag;
    }
    public Boolean isPassWordRight(String username, String password){
        boolean flag = false;
        Admin user = adminRepository.findByName(username);
        if(user.getPassword().equals(password)){
            flag = true;
        }
        return flag;
    }

    public void save(Admin newUser){
        adminRepository.save(newUser);
    }

    public Admin findById(Long id){
        return adminRepository.findByUserId(id);
    }
}
