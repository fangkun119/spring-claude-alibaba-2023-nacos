package org.nacosdemo.tlmallorder.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.nacosdemo.tlmallorder.entity.Order;

import java.util.List;


@Mapper
@Repository
public interface OrderMapper {
	@Select("select * from `order` where user_id = #{userId} order by id desc")
	List<Order> getOrderByUserId(@Param("userId") String userId);

	@Select("select * from `order` where id = #{id}")
	Order getOrderById(@Param("id") Integer id);
}
