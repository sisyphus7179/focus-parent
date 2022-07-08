package sisyphus.focus.core.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static sisyphus.focus.core.utils.FileUtils.BATCH_FILE_ALLOW_MAX_BYTES;

@Data
public class BatchFile<T> {

    private String summaryLine;
    private int summaryLineBytes = 0;
    private boolean needSummary = false;
    private BigDecimal total = BigDecimal.ZERO;
    private Function<T, BigDecimal> queryAmount;

    private List<T> objs = new ArrayList<>();
    private List<String> lines = new ArrayList<>();
    private int batchObjNum;
    private boolean hasMoreElement = true;
    private int reminderBytes = BATCH_FILE_ALLOW_MAX_BYTES;

    private Function<T, String> objToStr;
    private String path;
    private String fileName;
    private int batchNo = 0;

    public BatchFile(String summaryLine, Function<T, BigDecimal> queryAmount, int batchObjNum, Function<T, String> objToStr, String path, String fileName) {
        this(batchObjNum, objToStr, path, fileName);
        this.summaryLine = summaryLine;
        this.summaryLineBytes = 50;
        this.reminderBytes -= this.summaryLineBytes;
        this.needSummary = true;
        this.queryAmount = queryAmount;
    }

    public BatchFile(int batchObjNum, Function<T, String> objToStr, String path, String fileName) {
        this.batchObjNum = batchObjNum;
        this.objToStr = objToStr;
        this.path = path;
        this.fileName = fileName;
    }

    public void setObjs(List<T> objs) {
        this.objs.addAll(objs);
        if (objs.size() < this.batchObjNum) this.hasMoreElement = false;
        objs.clear();

    }

}
