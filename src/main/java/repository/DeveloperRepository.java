package repository;


import model.Developer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    @ResultMap("developer")
    @Select("select * from developer where id = #{id}")
    Developer findByUserId(@Param("id") Long id);
}
