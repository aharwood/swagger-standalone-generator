package com.wotifgroup.swaggerstandalonegenerator;

import com.wordnik.swagger.annotations.ApiProperty;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for unit testing
 */
@XmlRootElement(name="ModelSample")
public class ModelSample {

    private long id;
    private String status;
    private boolean enabled;
    private Date created;
    private Integer quantity;
    private List<String> nestedValues;
    private Locale locale;

    @XmlElement(name="id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement(name = "status")
    @ApiProperty(value = "Model Status", allowableValues = "pending, accepted")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlElement(name="enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @XmlElement(name="created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @XmlElement(name="quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @XmlElementWrapper(name="nestedValues")
    @XmlElement(name="nestedValue")
    public List<String> getNestedValues() {
        return nestedValues;
    }

    public void setNestedValues(List<String> nestedValues) {
        this.nestedValues = nestedValues;
    }

    @XmlElement(name="locale")
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
