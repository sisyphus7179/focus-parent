package sisyphus.focus.core.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sisyphus.focus.core.utils.FileUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static sisyphus.focus.core.utils.FileUtils.BATCH_FILE_ALLOW_MAX_BYTES;

@Slf4j
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

    public BatchFile(String summaryLine, Function<T, BigDecimal> queryAmount, int batchObjNum, Function<T, String> objToStr, String path, String fileName) throws IOException {
        this(batchObjNum, objToStr, path, fileName);
        this.summaryLine = summaryLine;
        this.summaryLineBytes = summaryLine.getBytes(StandardCharsets.UTF_8).length + 15 + FileUtils.EOL_LEN;
        this.reminderBytes -= this.summaryLineBytes;
        this.needSummary = true;
        this.queryAmount = queryAmount;
    }

    public BatchFile(int batchObjNum, Function<T, String> objToStr, String path, String fileName) throws IOException {
        this.batchObjNum = batchObjNum;
        this.objToStr = objToStr;
        this.path = path;
        Path directoryPath = Paths.get(this.path);
        if (!directoryPath.toFile().exists()) {
            try {
                if (!directoryPath.toFile().createNewFile()) {
                    throw new IOException();
                }
            } catch (IOException ex) {
                log.error("Create directory[{}] error", directoryPath.getFileName(), ex);
                throw ex;
            }
        }
        this.fileName = fileName;
    }

    public void setObjs(List<T> objs) {
        this.objs.addAll(objs);
        if (objs.size() < this.batchObjNum) this.hasMoreElement = false;
        objs.clear();
    }

}
