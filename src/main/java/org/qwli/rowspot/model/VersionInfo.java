package org.qwli.rowspot.model;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/2/9 8:33
 * 功能：VersionInfo
 **/
public class VersionInfo implements Serializable {

    private String version;

    private String useJdkVersion;

    private String buildTime;

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUseJdkVersion() {
        return useJdkVersion;
    }

    public void setUseJdkVersion(String useJdkVersion) {
        this.useJdkVersion = useJdkVersion;
    }
}
