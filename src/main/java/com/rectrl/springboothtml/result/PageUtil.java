package com.rectrl.springboothtml.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Page及其实现类PageImpl没有无参构造, 其超类Chunk也没有无参构造; 导致反序列化失败.-jackson
 *
 * @author hongen.zhang
 * time: 2019/11/20 16:57
 * email: hongen.zhang@things-matrix.com
 */
public class PageUtil<T> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = -5838738544521945177L;

    // payload
    private List<T> content = new ArrayList<>();

    // 当前页码
    private Integer number;

    // 当前页可显示载体总个数
    private Integer size;

    // 数据总数
    private Long totalElements;

//    private List<String> orders;

    // 排序方案
    private Sort sort;

    public PageUtil() {
    }

    public PageUtil(List<T> content) {
        this.content = content;
    }

    public PageUtil(List<T> content, Integer number, Integer size, Long totalElements) {
        this.content = content;
        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
    }

    public PageUtil(List<T> content, Integer number, Integer size, Long totalElements, Sort sort) {
        this.content = content;
        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
        this.sort = sort;
    }

    @JsonProperty
    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

//    @JsonProperty("sort")
//    public List<String> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<String> orders) {
//        this.orders = orders;
//    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = SortSerializer.class)
    @JsonDeserialize(using = SortDeserializer.class)
    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    // 分页总数
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getTotalPages() {
        if(null == getSize()) {
            return null;
        }
        return getSize() == 0 ? 1 : (int) Math.ceil((double) this.totalElements / (double) getSize());
    }

    // 当前页载体数量
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getNumberOfElements() {
        if(null == getSize()){
            return null;
        }
        return content.size();
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean isFirst() {
        if(null == hasPrevious()){
            return null;
        }
        return !hasPrevious();
    }

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean isLast() {
        if(null == hasNext()){
            return null;
        }
        return !hasNext();
    }

    @JsonIgnore
    public Boolean hasContent() {
        if(null == getSize()){
            return null;
        }
        return !content.isEmpty();
    }

    @JsonIgnore
    public Boolean hasNext() {
        if(null == getNumber()){
            return null;
        }
        return getNumber() + 1 < getTotalPages();
    }

    @JsonIgnore
    public Boolean hasPrevious() {
        if(null == getNumber()){
            return null;
        }
        return getNumber() > 0;
    }

    public <R> PageUtil<R> map(Function<? super T, ? extends R> converter) {
        return new PageUtil<>(this.getContent().stream().map(converter::apply).collect(Collectors.toList()), getNumber(), getSize(), getTotalElements(), getSort());
    }

    @Override
    public Iterator<T> iterator() {
        return getContent().iterator();
    }
}