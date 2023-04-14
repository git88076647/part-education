package com.czyl.project.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.czyl.project.domain.OrderDetailEntity;
import com.czyl.project.domain.OrderEntity;

public interface ShardingXXMapper {
	
	@Select("select id,billdate,vnote from t_order where billdate = #{billdate}")
    List<OrderEntity> selectList(@Param("billdate") Date billdate);
	
	@Select("select id,billdate,vnote from t_order where billdate between #{beginTime} and #{endTime}")
	List<OrderEntity> selectListBetween(@Param("beginTime") Date beginTime,@Param("endTime") Date endTime);

	@Select("select id,billdate,vnote from t_order where billdate >= #{beginTime} and billdate <= #{endTime}")
	List<OrderEntity> selectListRange(@Param("beginTime") Date beginTime,@Param("endTime") Date endTime);

	
	@Select("select id,billdate,vnote,version from t_order where id = #{id}")
	OrderEntity selectById(@Param("id") Long id);
	
	@Select("select d.id,d.billdate,d.material_id,d.order_id from t_orderdetail d join t_order h on d.order_id=h.id where h.id = #{id}")
	OrderDetailEntity selectByPId(@Param("id") Long id);
	
	@Insert("insert into t_order(id,billdate,vnote) values(#{id},#{billdate},#{vnote})")
    int insert(OrderEntity order);
	
	@Update("update t_order set vnote=#{vnote},version=#{version} where id=#{id}")
	int update(OrderEntity order);
}
