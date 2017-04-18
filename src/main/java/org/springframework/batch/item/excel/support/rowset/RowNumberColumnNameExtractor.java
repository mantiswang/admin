package org.springframework.batch.item.excel.support.rowset;

import org.springframework.batch.item.excel.Sheet;
import org.springframework.batch.item.excel.poi.PoiSheet;

/**
 * Created by wangyunfei on 16/9/24.
 */
public class RowNumberColumnNameExtractor implements ColumnNameExtractor {

    private int headerRowNumber;

    @Override
    public String[] getColumnNames(final Sheet sheet) {
        PoiSheet poiSheet = (PoiSheet)sheet;
        return poiSheet.getHeaderRow(headerRowNumber);
    }

    public void setHeaderRowNumber(int headerRowNumber) {
        this.headerRowNumber = headerRowNumber;
    }
}