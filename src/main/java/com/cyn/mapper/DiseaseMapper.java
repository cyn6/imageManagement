package com.cyn.mapper;

import com.cyn.pojo.Disease;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DiseaseMapper {
    /**
     * 查询所有,用分页查询代替
     * @return
     */
    @Select("select * from plant_disease where id <=24")
    @ResultMap("diseaseResultMap")
    List<Disease> selectAll();

    /**
     * 导入数据
     * @param disease
     */
    @Insert("insert into plant_disease values(null,#{cropName},#{diseaseName},#{imagePath},CURRENT_TIMESTAMP)")
//    @ResultMap("diseaseResultMap")
    void add(Disease disease);

    /**
     * 多条件查询
     * @return
     */
    List<Disease> selectByCropDiseaseName(Disease disease);

    /**
     * 批量删除
     * @param ids
     */
    void deleteByIds(@Param("ids") int[] ids);

    /**
     * 分页查询，代替查询所有
     * @param begin
     * @param size
     */
    @Select("select * from plant_disease limit #{begin},#{size}")
    @ResultMap("diseaseResultMap")
    List<Disease> selectByPage(@Param("begin") int begin,@Param("size") int size);

    /**
     * 查询总记录数
     * @return
     */
    @Select("select count(*) from plant_disease")
    int selectTotalCount();

    /**
     * 分页查询+条件查询
     * @param begin
     * @param size
     */
    List<Disease> selectByPageAndCondition(@Param("begin") int begin,@Param("size") int size,@Param("disease") Disease disease);

    /**
     * 查询总记录数+根据条件
     * @return
     */
    int selectTotalCountByCondition(Disease disease);
}
