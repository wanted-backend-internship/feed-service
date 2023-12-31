package com.example.feedservice.domain.post.resolver;

import static com.example.feedservice.domain.hashtag.QHashTag.hashTag1;
import static com.example.feedservice.domain.post.domain.QPost.post;

import com.example.feedservice.domain.post.resolver.dto.reponse.ResolverResponse;
import com.example.feedservice.domain.post.resolver.dto.request.StatisticRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTimeTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.sql.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ResolverRepository {
    private final EntityManager entityManager;

    @Transactional
    public List<ResolverResponse> findPostsByTypeDateAndValue (StatisticRequest request) {
        // LocalDate 로 하면 안됨.
        DateTimeTemplate<Date> dateOnly = Expressions.dateTimeTemplate(Date.class, "DATE({0})", post.createdAt);
        NumberExpression<Long> dynamicField = findDynamicField(request);


        List<ResolverResponse> posts = new JPAQueryFactory(entityManager)
                .select(Projections.constructor(ResolverResponse.class,
                        dateOnly,
                        dynamicField)
                ).from(post)
                .innerJoin(post.hashTags, hashTag1)
                .where(hashTag1.hashTag.eq(request.getHashTag())
                        .and(post.createdAt.between(request.getStartDate(), request.getEndDate())))
                .groupBy(dateOnly)
                .orderBy(dateOnly.desc())
                .fetch();

        return posts;
    }

    @Transactional
    public List<Object[]> findPostsByDateAndHour(StatisticRequest request) {
        String jpql = "SELECT DATE(post.createdAt) as date, " +
                "HOUR(post.createdAt) as hour, " +
                "COUNT(post) as count " +
                "FROM Post post " +
                "JOIN post.hashTags hashTag " +
                "WHERE post.createdAt BETWEEN :startDate AND :endDate " +
                "AND hashTag.hashTag = :hashTagName " +
                "GROUP BY date, hour " +
                "ORDER BY date DESC, hour DESC";

        return entityManager.createQuery(jpql, Object[].class)
                .setParameter("startDate", request.getStartDate())
                .setParameter("endDate", request.getEndDate())
                .setParameter("hashTagName", request.getHashTag())
                .getResultList();
    }

    private NumberExpression<Long> findDynamicField (StatisticRequest request) {
        if (request.getValues() == null || request.getValues().equals("count")) {
            return post.count();
        } else if (request.getValues().equals("heart_count")) {
            return post.heartCount.sum();
        } else if (request.getValues().equals("view_count")) {
            return post.viewCount.sum();
        } else if (request.getValues().equals("share_count")) {
            return post.shareCount.sum();
        }
        return null;
    }
}
