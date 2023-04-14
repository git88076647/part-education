package com.czyl.common.utils.easyexcel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.czyl.common.utils.DateUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.feign.AuthServerClient;
import com.czyl.project.system.domain.SysBilltplItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * excel 操作工具
 * @author tanghx
 * @date 2021/1/20 10:16
 */
public class EasyExcelUtils {

    int pageSize = 1000;

    public int getPageSize() {
        return pageSize;
    }

    public EasyExcelUtils setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public static class ExportParams extends HashMap {
        public ExportParams(int pageNum, int pageSize, boolean count, Object queryEntity) {
            this.put("pageNum", pageNum);
            this.put("pageSize", pageSize);
            this.put("count", count);
            this.put("queryEntity", queryEntity);
        }

        ExportParams setPageNum(int pageNum) {
            this.put("pageNum", pageNum);
            return this;
        }

        ExportParams setPageSize(int pageSize) {
            this.put("pageSize", pageSize);
            return this;
        }

        ExportParams setCount(boolean count) {
            this.put("count", count);
            return this;
        }

        ExportParams setQueryEntity(Object queryEntity) {
            this.put("queryEntity", queryEntity);
            return this;
        }

        int getPageNum() {
            return (int) this.get("pageNum");
        }

        int getPageSize() {
            return (int) this.get("pageSize");
        }

        boolean getCount() {
            return (boolean) this.get("count");
        }

        Object getQueryEntity() {
            return this.get("queryEntity");
        }

    }


    /**
     * 导出excel
     *
     * @param response    response渲染
     * @param queryEntity 查询数据的参数对象
     * @param billtplcode 单据模版编码
     * @param fileName    导出文件名.xlsx
     * @param function    分页查询导出数据的函数
     * @throws IOException
     */
    public void export(HttpServletResponse response, Object queryEntity, String billtplcode, String fileName, Function<ExportParams, List> function) throws IOException {
        final List<SysBilltplItem> items = SpringUtils.getBean(AuthServerClient.class).getBillItems(billtplcode).stream().filter(o -> o.getExport() && o.getExport().booleanValue()).collect(Collectors.toList());
        final List<List<String>> header = items.stream().map(o -> ListUtil.toList(o.getLabel())).collect(Collectors.toList());
        if (StringUtils.isBlank(fileName)) {
            fileName = System.currentTimeMillis() + ".xlsx";
        }
        if (!(fileName.toLowerCase().endsWith(".xlsx") || fileName.toLowerCase().endsWith(".xls"))) {
            fileName += ".xlsx";
        }
        //count查询
        PageHelper.startPage(1, -1, true);
        List<?> list = function.apply(new ExportParams(1, -1, true, queryEntity));
        long total = new PageInfo(list).getTotal();
        if(total == 0){
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("<html><body><h1 align='center'>未查询到需导出的数据</h1></body></html>");
            return ;
//            throw new CustomException("未查询到需导出的数据");
        }else{
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        }
        // 这里注意 如果同一个sheet只要创建一次
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).head(header).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();

        LinkedList<Object> rowSum = new LinkedList<>();
        rowSum.add("合计");
        for (int i = 0, len = items.size() - 1; i < len; i++) {
            rowSum.add(null);
        }
        AtomicBoolean needSum = new AtomicBoolean(false);
        int pageNum = 1;
        while (total > (pageNum - 1) * pageSize) {
            PageHelper.startPage(pageNum, pageSize, false);
            list = function.apply(new ExportParams(pageNum, pageSize, false, queryEntity));
            pageNum++;
            if (list == null || list.isEmpty()) {
                break;
            }
            List<LinkedList<Object>> datas = list.stream().map(o -> {
                //与header顺序一致
                LinkedList<Object> data = new LinkedList<>();
                for (int i = 0, len = items.size(); i < len; i++) {
                    SysBilltplItem item = items.get(i);
                    Object value = BeanUtil.getFieldValue(o, item.getProp());
                    //小数位
                    int scale = -1;
                    if (item.getType() != null && item.getType().startsWith("number") && value != null && value instanceof BigDecimal) {
                        switch (item.getType()) {
                            case "number4":
                                scale = 4;
                                break;
                            case "number3":
                                scale = 3;
                                break;
                            case "number2":
                                scale = 2;
                                break;
                            case "number1":
                                scale = 1;
                                break;
                            default:
                                scale = 0;
                                break;
                        }
                        value = ((BigDecimal) value).setScale(scale, RoundingMode.HALF_UP);
                    }
                    if (value != null && "date".equals(item.getType()) && value instanceof Date) {
                        data.add(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, (Date) value));
                    } else if (value != null && value instanceof Date) {
                        data.add(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, (Date) value));
                    } else if (value != null && value instanceof BigDecimal) {
                        data.add(((BigDecimal) value).doubleValue());
                    } else if (value != null && value instanceof Long) {
                        //主键字段 为0 的导出
                        data.add(value.equals(0L) ? "" : value.toString());
                    } else {
                        data.add(StringUtils.trim(value));
                    }
                    //计算合计
                    if (i > 0 && scale > -1) {
                        BigDecimal sum = (rowSum.get(i) == null ? BigDecimal.ZERO : (BigDecimal) rowSum.get(i)).add((BigDecimal) value);
                        //保留小数位数，BigDecimal 原始值不会变，而是返回一个新对象
                        sum = sum.setScale(scale, RoundingMode.HALF_UP);
                        rowSum.set(i, sum);
                        needSum.set(true);
                    }
                }
                return data;
            }).collect(Collectors.toList());
            excelWriter.write(datas, writeSheet);
            if (list.size() < pageSize) {
                break;
            }
        }
        if (needSum.get() && total > 0) {
            LinkedList datas = new LinkedList();
            datas.add(rowSum);
            excelWriter.write(datas, writeSheet);
        }
        excelWriter.finish();
    }

}
