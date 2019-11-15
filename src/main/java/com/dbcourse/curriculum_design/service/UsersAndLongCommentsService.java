package com.dbcourse.curriculum_design.service;

import com.dbcourse.curriculum_design.model.UsersAndLongComments;
import com.dbcourse.curriculum_design.model.UsersAndLongCommentsExample;

import java.util.List;

public interface UsersAndLongCommentsService {


    long countByExample(UsersAndLongCommentsExample example);

    int deleteByExample(UsersAndLongCommentsExample example);

    int insert(UsersAndLongComments record);

    int insertSelective(UsersAndLongComments record);

    List<UsersAndLongComments> selectByExample(UsersAndLongCommentsExample example);

    int updateByExampleSelective(UsersAndLongComments record, UsersAndLongCommentsExample example);

    int updateByExample(UsersAndLongComments record, UsersAndLongCommentsExample example);

    int batchInsert(List<UsersAndLongComments> list);



    /**
     *
     * @param MovieId
     * @return a list of UsersAndLongComments
     */
    List<UsersAndLongComments> selectByMovieId(Integer MovieId);

    /**
     * lrc
     * 显示长评详细信息
     * @param LongCommentId
     * @return
     */
     UsersAndLongComments selectLongCommentsByLongCommentId(Integer LongCommentId);
}
