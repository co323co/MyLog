package com.mylog.dao;

import com.mylog.dto.post.selectpost.QSelectPostOutput;
import com.mylog.dto.post.selectpost.SelectPostInput;
import com.mylog.dto.post.selectpost.SelectPostOutput;
import com.mylog.entity.QComment;
import com.mylog.entity.QPost;
import com.mylog.entity.QSeries;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    QPost qPost = QPost.post;
    QComment qComment = QComment.comment;
    QSeries qSeries = QSeries.series;

    @Override
    public Page<SelectPostOutput> findByDynamicQuery(SelectPostInput selectPostInput, Pageable pageable) {
        QueryResults<SelectPostOutput> queryResult = queryFactory
                .select(new QSelectPostOutput(
                        qPost.series.id,
                        qSeries.name,
                        qPost.id,
                        qPost.title,
                        qPost.content,
                        qPost.createdAt,
                        qPost.updatedAt,
                        //comment count
                        JPAExpressions.select(qComment.count().castToNum(Integer.class)).from(qComment)
                                .where(qComment.post.id.eq(qPost.id))
                ))
                .from(qPost)
                .leftJoin(qSeries)
                .on(qPost.series.id.eq(qSeries.id))
                .where(eqSearch(selectPostInput.getSearch()), eqSeries(selectPostInput.getSeriesId()),
                        eqHasSeries(selectPostInput.getHasSeries()))
                .orderBy(qPost.createdAt.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        long totalCount = queryResult.getTotal();
        List<SelectPostOutput> content = queryResult.getResults();

        return new PageImpl<>(content, pageable, totalCount);
    }

    private BooleanExpression eqSearch(String search) {
        if (StringUtils.isEmpty(search)) {
            return null;
        }
        return qPost.title.contains(search).or(qPost.content.contains(search));
    }

    private BooleanExpression eqSeries(Integer seriesId) {
        if (StringUtils.isEmpty(seriesId)) {
            return null;
        }
        return qPost.series.id.eq(seriesId);
    }

    private BooleanExpression eqHasSeries(Boolean hasSeries) {
        if (StringUtils.isEmpty(hasSeries))
            return null;
        else if(hasSeries)
            return qPost.series.id.isNotNull();
        else
            return qPost.series.id.isNull();
    }
}

