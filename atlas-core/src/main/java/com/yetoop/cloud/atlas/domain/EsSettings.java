package com.yetoop.cloud.atlas.domain;

public class EsSettings {
    private Integer id;

    private String code;

    private String cfgValue;

    private String cfgGroup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCfgValue() {
        return cfgValue;
    }

    public void setCfgValue(String cfgValue) {
        this.cfgValue = cfgValue;
    }

    public String getCfgGroup() {
        return cfgGroup;
    }

    public void setCfgGroup(String cfgGroup) {
        this.cfgGroup = cfgGroup;
    }
}