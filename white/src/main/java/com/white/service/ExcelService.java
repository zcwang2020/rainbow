package com.white.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.white.entity.Location;
import com.white.meta.request.ExportData;
import com.white.meta.request.UploadData;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@Service
public class ExcelService {

    public List<UploadData> importExcel(MultipartFile file) {
        try {
            ImportParams params = new ImportParams();
            // params.setTitleRows(1); // 标题行数
            params.setHeadRows(1);  // 表头行数
            return ExcelImportUtil.importExcel(file.getInputStream(), UploadData.class, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void exportExcel(List<ExportData> dataList, HttpServletResponse response)  {
        try {
            // 导出，并获取工作簿
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "标记位置"), ExportData.class, dataList);
            // 输出
            responseWorkbook(response, workbook, "简单导出.xlsx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void responseWorkbook(HttpServletResponse response, Workbook workbook, String fileName) {
        try {
            response.setHeader("Content-disposition", "attachment;" + "filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/octet-stream; charset=UTF-8");
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
}
