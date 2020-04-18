package com.rectrl.springboothtml.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author hongen.zhang
 * time: 2019/11/25 11:48
 * email: hongen.zhang@things-matrix.com
 */
public class PageOrderUtils {
    /**
     * pageable分页查询排序orders
     *
     * @param pageable
     * @param root
     * @param query
     * @param cb
     */
    public static void pageableOrder(Pageable pageable, Root root, CriteriaQuery query, CriteriaBuilder cb) {
        List<Order> orders = new ArrayList<>();

        Iterator<Sort.Order> iterator = pageable.getSort().iterator();
        if (iterator.hasNext()) {
            Sort.Order next = iterator.next();
            String property = next.getProperty();
            String direction = next.getDirection().name();

            orders.add(direction.equals(Sort.Direction.ASC.name()) ? cb.asc(root.get(property)) : cb.asc(root.get(property)));
        }

        query.orderBy(orders);
    }


}
