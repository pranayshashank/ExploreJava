package cache.dto;

import java.io.Serializable;
import java.util.Optional;

public class CacheDto {

    private final String testCaseId;
    private final String qualifier;
    private Serializable value;

    public CacheDto (String testCaseId, String qualifier, Serializable value){
        this.testCaseId = testCaseId;
        this.qualifier = qualifier;
        this.value = Optional.ofNullable(value).orElse("");
    }

    public String getTestCaseId() {
        return testCaseId;
    }

    public String getQualifier() {
        return qualifier;
    }

    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = Optional.ofNullable(value).orElse("");
    }
}
