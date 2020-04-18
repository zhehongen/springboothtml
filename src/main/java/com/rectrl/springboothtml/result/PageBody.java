package com.rectrl.springboothtml.result;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author huyapeng
 * @date 2019/9/10
 * Email: yapeng.hu@things-matrix.com
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageBody<T> implements Iterable<T>, Serializable {

    @JsonAlias({"totalElements", "total"})
    private Long total;
    @JsonAlias({"totalPages", "pages"})
    private Integer pages;
    @JsonAlias({"size", "pageSize"})
    private Integer pageSize;
    @JsonAlias({"number", "pageNum"})
    private Integer pageNum;
    @JsonAlias({"content", "list"})
    private List<T> list;


    public <U> PageBody<U> map(Function<? super T, ? extends U> mapper) {
        PageBody<U> pageBody = new PageBody<>();
        List<T> list = this.getList();
        List<U> collect = list.stream().map(mapper).collect(Collectors.toList());
        pageBody.setPageNum(pageNum);
        pageBody.setPages(pages);
        pageBody.setTotal(total);
        pageBody.setPageSize(pageSize);
        pageBody.setList(collect);

        return pageBody;
    }


    public PageBody(List<T> list) {
        this.list = list;
        this.pageSize = list.size();
        this.pageNum = 1;
        this.pages = 1;
        this.total = (long) list.size();
    }

    public PageBody(Page<T> page) {
        this.list = page.getContent();
        this.pageNum = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.pages = page.getTotalPages();
        this.total = page.getTotalElements();
    }

    public PageBody(PageUtil<T> page) {
        this.list = page.getContent();
        this.pageNum = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.pages = page.getTotalPages();
        this.total = page.getTotalElements();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
