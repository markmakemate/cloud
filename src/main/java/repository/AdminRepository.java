package repository;
import model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Mapper
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @ResultMap(value = "admin")
    @Select("select * from Admin where username = #{username}")
    Admin findByName(@Param("username") String username );

    @ResultMap(value = "admin")
    @Select("select * from Admin where userId = #{userId}")
    Admin findByUserId(@Param("userId") Long userId);

}
