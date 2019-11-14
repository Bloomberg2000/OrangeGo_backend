package com.dbcourse.curriculum_design.mapper;

import com.dbcourse.curriculum_design.model.LongComments;
import com.dbcourse.curriculum_design.model.LongCommentsExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LongCommentsMapper {
    long countByExample(LongCommentsExample example);

    int deleteByExample(LongCommentsExample example);

    int deleteByPrimaryKey(Integer nId);

    int insert(LongComments record);

    int insertSelective(LongComments record);

    List<LongComments> selectByExample(LongCommentsExample example);

    LongComments selectByPrimaryKey(Integer nId);

    int updateByExampleSelective(@Param("record") LongComments record, @Param("example") LongCommentsExample example);

    int updateByExample(@Param("record") LongComments record, @Param("example") LongCommentsExample example);

    int updateByPrimaryKeySelective(LongComments record);

    int updateByPrimaryKey(LongComments record);

    int batchInsert(@Param("list") List<LongComments> list);
}