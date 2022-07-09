package sisyphus.focus.core.utils;

import sisyphus.focus.core.entity.BatchFile;
import sisyphus.focus.core.entity.Employee;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

class FileUtilsTest {
    public static void main(String[] args) throws IOException {
        String summaryLine = "1|%s";
        Function<Employee, BigDecimal> queryAmount = x -> BigDecimal.valueOf(x.getSalary());
        int batchObjNum = 500;
        Function<Employee, String> objToStr = x -> {
            StringBuilder sb = new StringBuilder();
            sb.append("2").append("|").
                    append(Objects.nonNull(x.getId()) ? x.getId() : "").append('|').
                    append(Objects.nonNull(x.getName()) ? x.getName() : "").append('|').
                    append(Objects.nonNull(x.getAge()) ? x.getAge() : "").append('|').
                    append(Objects.nonNull(x.getSalary()) ? x.getSalary() : "");
            return sb.toString();
        };
        String path = "d:\\";
        String fileName = "20220709";
        BatchFile<Employee> batchFile = new BatchFile<>(summaryLine, queryAmount, batchObjNum, objToStr, path, fileName);
        List<Employee> employees = new ArrayList<>();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 1; i < 49_998; i++) {
            Integer id = i;
            String name = UUID.randomUUID().toString();
            Integer age = secureRandom.nextInt(100);
            Double salary = secureRandom.nextDouble() * 10000;
            Employee employee = new Employee(id, name, age, salary);
            employees.add(employee);
            if (i % 500 == 0) {// match BatchFile#batchObjNum
                batchFile.setObjs(employees);
                FileUtils.batchGenerateFiles(batchFile);
            }
        }
        if (!employees.isEmpty()) {
            batchFile.setObjs(employees);
            FileUtils.batchGenerateFiles(batchFile);
        }
    }
}