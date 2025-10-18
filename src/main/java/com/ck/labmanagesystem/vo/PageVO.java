package com.ck.labmanagesystem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "分页响应结果")
public class PageVO<T> {

    @Schema(description = "数据列表")
    private List<T> records;

    @Schema(description = "总记录数")
    private Long total;

    @Schema(description = "当前页")
    private Integer current;

    @Schema(description = "每页大小")
    private Integer size;

    public static <T> PageVO<T> of(List<T> records, Long total, Integer current, Integer size) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setRecords(records);
        pageVO.setTotal(total);
        pageVO.setCurrent(current);
        pageVO.setSize(size);
        return pageVO;
    }
}