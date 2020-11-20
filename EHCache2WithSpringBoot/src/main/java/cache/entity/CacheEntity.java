package cache.entity;

import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;

@Cacheable (value = "TCCache10")
@Entity
@Table(name = "EE_CACHE",
        uniqueConstraints = {
                @UniqueConstraint(name = "EE_CACHE_UNI_CON",
                        columnNames = {"executionName", "cacheName", "qualifier"})
        })
public class CacheEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String executionName;

    @Column(nullable = false)
    private String cacheName;

    @Column(nullable = false)
    private String qualifier;

    @Column
    private String value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExecutionName() {
        return executionName;
    }

    public void setExecutionName(String executionName) {
        this.executionName = executionName;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
