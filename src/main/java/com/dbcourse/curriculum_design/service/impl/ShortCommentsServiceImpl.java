package com.dbcourse.curriculum_design.service.impl;

import com.dbcourse.curriculum_design.mapper.ShortCommentsMapper;
import com.dbcourse.curriculum_design.model.Movies;
import com.dbcourse.curriculum_design.model.MoviesExample;
import com.dbcourse.curriculum_design.model.ShortComments;
import com.dbcourse.curriculum_design.model.ShortCommentsExample;
import com.dbcourse.curriculum_design.service.MoviesService;
import com.dbcourse.curriculum_design.service.ShortCommentsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ShortCommentsServiceImpl implements ShortCommentsService {

    @Resource
    private ShortCommentsMapper shortCommentsMapper;

    @Resource
    private MoviesService moviesService;

    @Override
    public long countByExample(ShortCommentsExample example) {
        return shortCommentsMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(ShortCommentsExample example) {
        return shortCommentsMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer nId) {
        return shortCommentsMapper.deleteByPrimaryKey(nId);
    }

    @Override
    public int insert(ShortComments record) {
        return shortCommentsMapper.insert(record);
    }

    @Override
    public int insertSelective(ShortComments record) {
        return shortCommentsMapper.insertSelective(record);
    }

    @Override
    public List<ShortComments> selectByExample(ShortCommentsExample example) {
        return shortCommentsMapper.selectByExample(example);
    }

    @Override
    public ShortComments selectByPrimaryKey(Integer nId) {
        return shortCommentsMapper.selectByPrimaryKey(nId);
    }

    @Override
    public int updateByExampleSelective(ShortComments record, ShortCommentsExample example) {
        return shortCommentsMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(ShortComments record, ShortCommentsExample example) {
        return shortCommentsMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(ShortComments record) {
        return shortCommentsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ShortComments record) {
        return shortCommentsMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<ShortComments> list) {
        return shortCommentsMapper.batchInsert(list);
    }

    @Override
    public ShortComments getShortCommentByUserIdAndMovieId(int userId, int movieId) {
        ShortCommentsExample example = new ShortCommentsExample();
        example.createCriteria().andNUserIdEqualTo(userId).andNMovieIdEqualTo(movieId);
        List<ShortComments> shortComments = shortCommentsMapper.selectByExample(example);
        if (shortComments.size() > 0) {
            return shortComments.get(0);
        } else {
            return null;
        }
    }


    @Override
    public long countShortCommentsByScore(int movieId, short score) {
        ShortCommentsExample example = new ShortCommentsExample();
        example.createCriteria().andNMovieIdEqualTo(movieId).andNScoreEqualTo(score);
        return shortCommentsMapper.countByExample(example);
    }

    public List<ShortComments> getShortCommentsByMovieID(Integer nMovieId) {
        ShortCommentsExample example = new ShortCommentsExample();
        ShortCommentsExample.Criteria criteria = example.createCriteria();
        criteria.andNMovieIdEqualTo(nMovieId);
        return shortCommentsMapper.selectByExample(example);
    }

    @Override
    public ShortComments PutShortComments(ShortComments record) {
        // 先检查有没有，有则更新，无则插入
        ShortCommentsExample example = new ShortCommentsExample();
        example.createCriteria().andNMovieIdEqualTo(record.getNMovieId()).andNUserIdEqualTo(record.getNUserId());

        if (record.getNScore() > 5) {
            record.setNScore((short) 5);
        } else if (record.getNScore() < 1) {
            record.setNScore((short) 1);
        }

        if (shortCommentsMapper.selectByExample(example).size() > 0) {
            shortCommentsMapper.updateByExampleSelective(ShortComments.builder().dCreateTime(record.getDCreateTime())
                    .nMovieId(record.getNMovieId()).nScore(record.getNScore()).nType(record.getNType()).nUserId(record.getNUserId())
                    .cContent(record.getCContent()).build(), example);
            return record;
        }

        shortCommentsMapper.insert(record);

        int id = record.getNMovieId();
        long star5Num = this.countShortCommentsByScore(id, (short) 5);
        long star4Num = this.countShortCommentsByScore(id, (short) 4);
        long star3Num = this.countShortCommentsByScore(id, (short) 3);
        long star2Num = this.countShortCommentsByScore(id, (short) 2);
        long star1Num = this.countShortCommentsByScore(id, (short) 1);

        long starSum = star5Num + star4Num + star3Num + star2Num + star1Num;
        long d = starSum;
        if (d == 0) {
            d = 1;
        }

        float score = (star1Num / d) * 2 + (star2Num / d) * 4 + (star3Num / d) * 6 + (star4Num / d) * 8 + (star5Num / d) * 10;

        Movies movies = Movies.builder().fMovieScore(score).dEditTime(new Date()).build();

        MoviesExample movieExample = new MoviesExample();
        movieExample.createCriteria().andNIdEqualTo(record.getNMovieId());
        moviesService.updateByExampleSelective(movies, movieExample);

        return record;
    }

    @Override
    public long countShortCommentsByMovieId(int movieId) {
        ShortCommentsExample example = new ShortCommentsExample();
        example.createCriteria().andNMovieIdEqualTo(movieId);
        return shortCommentsMapper.countByExample(example);
    }
}
